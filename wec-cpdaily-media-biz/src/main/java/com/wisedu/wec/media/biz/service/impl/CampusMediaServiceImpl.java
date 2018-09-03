package com.wisedu.wec.media.biz.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.wisedu.wec.cpdaily.common.enumeration.CpdailyUserTypeEnum;
import com.wisedu.wec.cpdaily.config.provider.api.TenantsService;
import com.wisedu.wec.cpdaily.config.provider.api.dto.TenantsDto;
import com.wisedu.wec.cpdaily.user.dubbo.dto.CpdailyUserBaseDTO;
import com.wisedu.wec.media.biz.manager.UserServiceManager;
import com.wisedu.wec.media.biz.service.CampusMediaService;
import com.wisedu.wec.media.common.constants.MediaVersionConst;
import com.wisedu.wec.media.common.exception.BusinessException;
import com.wisedu.wec.media.common.exception.ValidateException;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.CampusMediaVo;
import com.wisedu.wec.media.common.utils.AssertUtils;
import com.wisedu.wec.media.common.vo.CampusMediaHomeVo;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import com.wisedu.wec.media.dal.mybatis.MediaPersonRelationMapper;
import com.wisedu.wec.media.dal.mybatis.dao.CampusMediaDao;
import com.wisedu.wec.media.dal.mybatis.dao.MediaFansDao;
import com.wisedu.wec.media.dal.mybatis.dao.MediaVisitingRecordDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CampusMediaServiceImpl implements CampusMediaService {
    @Autowired
    private CampusMediaMapper campusMediaMapper;
    @Autowired
    private CampusMediaDao campusMediaDao;
    @Autowired
    private UserServiceManager userServiceManager;
    @Reference(version = "1.0.0")
    private TenantsService tenantsService;
    @Autowired
    private AuthService authService;
    @Autowired
    private MediaFansDao mediaFansDao;
    @Autowired
    private MediaVisitingRecordDao mediaVisitingRecordDao;

    @Override
    public List<CampusMediaVo> listManageMedia(String personId) {
        if (StringUtils.isEmpty(personId)) {
            throw new ValidateException("用户Id不存在，请检查登录状态");
        }
        List<CampusMedia> medias = campusMediaMapper.selectByPersonId(personId);

        // 过滤禁用租户的校园号
        medias = filterMediaByTenantStatus(medias);

        List<CampusMediaVo> mediaVos = medias.stream().filter(media -> Objects.equals(media.getVersion(), MediaVersionConst.NEW_VERSION)).map(bean -> {
            bean.setIcon(ImgUtils.imgUrlsSAddHost(bean.getIcon()));
            CampusMediaVo vo = new CampusMediaVo();
            BeanUtils.copyProperties(bean, vo);
            return vo;
        }).collect(Collectors.toList());


        return mediaVos;
    }

    /**
     * 根据租户过滤校园号
     *
     * @param medias
     * @return
     */
    private List<CampusMedia> filterMediaByTenantStatus(List<CampusMedia> medias) {
        List<String> tenantIds = medias.stream().map(CampusMedia::getTenantId).collect(Collectors.toList());
        Set<String> enableTenantIds = tenantsService.listByWids(tenantIds)
                .stream().map(TenantsDto::getWid).collect(Collectors.toSet());
        medias = medias.stream().filter(campusMedia -> enableTenantIds.contains(campusMedia.getTenantId())).collect(Collectors.toList());
        return medias;
    }

    @Transactional
    @Override
    public void mediaUpgrade(String mediaId, String personId, String userId) {
        if (StringUtils.isEmpty(personId) || StringUtils.isEmpty(mediaId)) {
            throw new ValidateException("请使用校园号管理员账号进行升级");
        }
        boolean manager = campusMediaDao.isManager(personId, mediaId);
        if (!manager) {
            throw new BusinessException("请使用校园号管理员账号进行升级");
        }

        CampusMedia campusMedia = campusMediaMapper.selectByMediaId(mediaId);
        if (campusMedia == null) {
            throw new BusinessException("校园号不存在");
        }
        Boolean canUpgrade = campusMedia.getCanUpgrade();
        String version = campusMedia.getVersion();
        if (Objects.equals(canUpgrade, false) || Objects.equals(version, MediaVersionConst.NEW_VERSION)) {
            throw new BusinessException("该校园号不能升级");
        }

        //FIXME 用户表作废
        //校园号对应用户的userId等于校园号的mediaId
        CpdailyUserBaseDTO mediaUser = userServiceManager.getUserBaseByUserId(mediaId);
        AssertUtils.requireTrue(mediaUser!=null&&Objects.equals(mediaUser.getUserType(), CpdailyUserTypeEnum.MEDIA),"校园号身份不存在");

        userServiceManager.disableUser( mediaUser.getUserId());

        //更改校园号版本号
        campusMediaDao.updateMediaVersion(mediaId, MediaVersionConst.NEW_VERSION);

        //FIXME 用户对应粉丝迁移到新表
        List<String> fansIds = userServiceManager.listFansIdByUserId(userId);
        mediaFansDao.batchInsertFans(mediaId,fansIds);

        //FIXME 用户对应访客迁移到新表
        List<String> visitorIds = userServiceManager.listVisitIdByUserId(userId);
        mediaVisitingRecordDao.batchInsertVisitor(mediaId,visitorIds);

    }

    @Override
    public void mediaSwitch(String mediaId, UserInfoContext userInfoContext) {
        String personId = userInfoContext.getPersonId();
        if (StringUtils.isEmpty(personId) || StringUtils.isEmpty(mediaId)
                || !campusMediaDao.isManager(personId, mediaId)) {
            throw new ValidateException("请使用校园号管理员账号进行操作");
        }
        CampusMedia media = campusMediaDao.getMedia(mediaId);
        AssertUtils.requireTrue(Objects.equals(media.getVersion(), MediaVersionConst.NEW_VERSION),"只能选择新版校园号");
        TenantsDto tenantsDto = tenantsService.getByWid(media.getTenantId());

        userInfoContext.setMediaId(media.getWid());
        userInfoContext.setPortrait(media.getIcon());
        if (tenantsDto != null) {
            userInfoContext.setTenantCode(tenantsDto.getTenantCode());
            userInfoContext.setTenantId(tenantsDto.getWid());
            userInfoContext.setTenantName(tenantsDto.getTenantName());
        }

        authService.setLoginUserContextToCache(userInfoContext);
        ThreadLocalUserInfo.setContext(userInfoContext);
    }

    @Override
    public CampusMediaHomeVo mediaInfo(String mediaId, String personId, String loginUserId) {
        AssertUtils.notBlank(mediaId,"参数错误");
        CampusMedia media = campusMediaDao.getMedia(mediaId);
        AssertUtils.notNull(media,"校园号不存在");

        CampusMediaHomeVo mediaHomeVo = new CampusMediaHomeVo();
        BeanUtils.copyProperties(media,mediaHomeVo);

        mediaHomeVo.setFansCount(mediaFansDao.countFans(mediaId));
        mediaHomeVo.setVisitingCount(mediaVisitingRecordDao.countMediaVisitingRecord(mediaId));

        if (StringUtils.isNotEmpty(personId)) {
            mediaHomeVo.setManager(campusMediaDao.isManager(personId, mediaId));
        }
        if(StringUtils.isNotEmpty(loginUserId)){
            mediaHomeVo.setFans(mediaFansDao.isFans(mediaId,loginUserId));
        }
        return mediaHomeVo;
    }
}
