package com.wisedu.wec.media.biz.service.impl;

import com.google.gson.Gson;
import com.wisedu.cpdaily.core.models.auth.Session;
import com.wisedu.wec.cpdaily.user.dubbo.dto.CpdailyUserBaseDTO;
import com.wisedu.wec.media.biz.manager.UserServiceManager;
import com.wisedu.wec.media.biz.old.service.impl.UserService;
import com.wisedu.wec.media.biz.redis.RedisService;
import com.wisedu.wec.media.common.old.constants.RedisConstants;
import com.wisedu.wec.media.common.old.context.MediaInfoContext;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.CpdailyPerson;
import com.wisedu.wec.media.common.old.po.CpdailyTenant;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyTenantMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zsl
 */
@Service
public class AuthService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService oldUserService;
    @Autowired
    private UserServiceManager userServiceManager;

    @Autowired
    private CpdailyUserMapper cpdailyUserMapper;

    @Autowired
    private CampusMediaMapper campusMediaMapper;

    @Autowired
    private CpdailyTenantMapper tenantMapper;

    public UserInfoContext getLoginUserContextFromCache (String redisKey) {
        if (StringUtils.isEmpty(redisKey)) {
            return null;
        }
        String cacheContent = redisService.getStringValue(redisKey);
        if (StringUtils.isEmpty(cacheContent)) {
            return null;
        }
        UserInfoContext loginUserContext = new Gson().fromJson(cacheContent, UserInfoContext.class);
        return loginUserContext;
    }



    public void setLoginUserContextToCache (String redisKey, UserInfoContext userInfoContext) {

        String cacheContent = JSONObject.fromObject(userInfoContext).toString();
        redisService.setStringValue(redisKey, cacheContent, RedisConstants.DEFAULT_CACHE_TIMEOUT);
    }

    public String getMediaKey (String tenantId, String userId) {
        return "media_key_"  + tenantId + "_" + userId;
    }
    public UserInfoContext getLoginUserContextFromCache (String tenantId, String userId) {
        String redisKey = this.getMediaKey(tenantId, userId);
        return getLoginUserContextFromCache(redisKey);
    }
    public void setLoginUserContextToCache (UserInfoContext userInfoContext) {
        String redisKey = this.getMediaKey(userInfoContext.getTenantId(), userInfoContext.getLoginUserId());
        setLoginUserContextToCache(redisKey,userInfoContext);
    }

    public void delLoginUserContextFromCache (String redisKey) {
        redisService.delByKey(redisKey);
    }

    public UserInfoContext generateContextByPerson(String personId, String userId, String mediaId) {

        CpdailyUserBaseDTO cpdailyUser = userServiceManager.getUserBaseByUserId(userId);
        if (StringUtils.isEmpty(personId) && cpdailyUser != null) {
            personId = cpdailyUser.getPwid();
        }
        CpdailyPerson person = oldUserService.getCpdailyPerson(personId);

        if (cpdailyUser == null) {
            //有用户却没有身份，无法使用校园号
            return null;
        }
        CampusMedia campusMedia = null;
        if (StringUtils.isNotBlank(mediaId)) {
            campusMedia = campusMediaMapper.selectByMediaId(mediaId);
        }

        UserInfoContext context = new UserInfoContext();
        context.setPersonId(personId);
        context.setPersonName(person.getName());
        context.setLoginUserId(cpdailyUser.getUserId());
        context.setLoginUserName(cpdailyUser.getName());

        if (campusMedia != null) {
            context.setMediaId(mediaId);
            context.setTenantId(campusMedia.getTenantId());
            CpdailyTenant tnt = tenantMapper.getTenantById(campusMedia.getTenantId());
            context.setTenantCode(tnt.getTenantCode());
            context.setTenantName(tnt.getTenantName());
            context.setUserId(mediaId);
            context.setUserName(campusMedia.getName());
            if (StringUtils.isNotEmpty(campusMedia.getIcon())) {
                context.setPortrait(ImgUtils.imgUrlsSAddHost(campusMedia.getIcon()));
            }
        } else {
            context.setUserId(cpdailyUser.getUserId());
            context.setUserName(cpdailyUser.getName());
            context.setTenantId(cpdailyUser.getTenantId());
            // 查询学校code
            CpdailyTenant tenant = tenantMapper.getTenantById(cpdailyUser.getTenantId());
            context.setTenantCode(tenant.getTenantCode());
            context.setTenantName(tenant.getTenantName());
            context.setUserType(cpdailyUser.getUserType().toString());
            if (StringUtils.isNotEmpty(cpdailyUser.getAvatar())) {
                context.setPortrait(ImgUtils.imgUrlsSAddHost(cpdailyUser.getAvatar()));
            }
        }

        List<CampusMedia> medias = campusMediaMapper.selectByPersonId(personId);

        Map<String, MediaInfoContext> mediaMap = new HashMap<>();

        for (CampusMedia media : medias) {
            MediaInfoContext mediaInfoContext = new MediaInfoContext();

            mediaInfoContext.setMediaId(media.getWid());
            mediaInfoContext.setMediaName(media.getName());
            mediaInfoContext.setPortraitUrl(media.getIcon());
            mediaInfoContext.setAuthStatus(media.getAuthStatus());
            mediaInfoContext.setMediaType(media.getMediaType());
            mediaInfoContext.setTenantId(media.getTenantId());
            mediaInfoContext.setCanSeeOrgStructure(media.isCanSeeOrgStructure());
            mediaMap.put(media.getWid(), mediaInfoContext);
        }

        context.setMediaMap(mediaMap);

        return context;
    }

}
