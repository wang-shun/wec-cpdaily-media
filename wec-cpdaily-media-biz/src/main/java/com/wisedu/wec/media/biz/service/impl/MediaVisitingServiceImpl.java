package com.wisedu.wec.media.biz.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wisedu.cpdaily.score.api.request.IncIntegralRequest;
import com.wisedu.cpdaily.score.api.service.IntegralService;
import com.wisedu.wec.cpdaily.user.dubbo.dto.CpdailyUserBaseDTO;
import com.wisedu.wec.media.biz.manager.UserServiceManager;
import com.wisedu.wec.media.biz.service.MediaVisitingService;
import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.exception.ValidateException;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.po.MediaVisitingRecord;
import com.wisedu.wec.media.common.param.MediaVisitingRecordParam;
import com.wisedu.wec.media.common.utils.AssertUtils;
import com.wisedu.wec.media.common.vo.CpdailyUserWithTagTopknotVo;
import com.wisedu.wec.media.common.vo.MediaVisitingRecordVo;
import com.wisedu.wec.media.dal.mybatis.dao.MediaVisitingRecordDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MediaVisitingServiceImpl implements MediaVisitingService {
    @Autowired
    private MediaVisitingRecordDao mediaVisitingRecordDao;
    @Autowired
    private UserServiceManager userServiceManager;
    @Reference(version = "1.0.0")
    private IntegralService integralService;

    /**
     * 添加访问记录
     *
     * @param visitingParam
     */
    @Override
    public void addMediaVisiting(MediaVisitingRecordParam visitingParam) {
        MediaVisitingRecord visitingRecord = new MediaVisitingRecord();
        BeanUtils.copyProperties(visitingParam, visitingRecord);
        String loginUserId = ThreadLocalUserInfo.getContext().getLoginUserId();
        visitingRecord.setVisitorId(loginUserId);
        mediaVisitingRecordDao.insertSelective(visitingRecord);
    }

    @Override
    public long countMediaVisitingRecord(String mediaId) {
        if (StringUtils.isEmpty(mediaId)) {
            throw new ValidateException("校园号编号不能为空");
        }
        return mediaVisitingRecordDao.countMediaVisitingRecord(mediaId);
    }

    @Override
    public List<MediaVisitingRecordVo> listMediaVisitingRecord(String mediaId, Page page) {
        if (StringUtils.isEmpty(mediaId)) {
            throw new ValidateException("校园号编号不能为空");
        }
        List<MediaVisitingRecord> mediaVisitingRecords = mediaVisitingRecordDao.listMediaVisitingRecord(mediaId, page);

        List<MediaVisitingRecordVo> visitingRecordVos = mediaVisitingRecords.stream().map(bean -> {
            MediaVisitingRecordVo vo = new MediaVisitingRecordVo();
            BeanUtils.copyProperties(bean, vo);
            return vo;
        }).collect(Collectors.toList());
        setUserInfo(visitingRecordVos);
        return visitingRecordVos;
    }

    @Override
    @Transactional
    public void deleteMediaVisiting(Long id) {
        String loginUserId = ThreadLocalUserInfo.getContext().getLoginUserId();
        AssertUtils.notBlank(loginUserId,"请先登录");
        AssertUtils.notNull(id,"请选择记录");

        MediaVisitingRecord visitingRecord = mediaVisitingRecordDao.selectByPrimaryKey(id);
        boolean hasPermission = visitingRecord != null && Objects.equals(visitingRecord.getVisitorId(), loginUserId);
        AssertUtils.requireTrue(hasPermission,"访问记录删除失败");

        int delete = mediaVisitingRecordDao.deleteById(id);
        AssertUtils.requireTrue(delete>0,"访问记录删除失败");
        //扣除今币
        CpdailyUserBaseDTO user = userServiceManager.getUserBaseByUserId(loginUserId);
       integralService.incIntegral(new IncIntegralRequest(user.getTenantId(), user.getUserId(), -1, "消除访问记录消耗", System.currentTimeMillis()));

    }

    /**
     * 设置用户信息
     *
     * @param visitingRecordVos
     */
    private void setUserInfo(List<MediaVisitingRecordVo> visitingRecordVos) {
        if (CollectionUtils.isEmpty(visitingRecordVos)) {
            return;
        }
        List<String> userIds = visitingRecordVos.stream().map(MediaVisitingRecordVo::getVisitorId)
                .filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());

        List<CpdailyUserWithTagTopknotVo> userVos = userServiceManager.listUserWithTagTopknotByUserIdList(userIds);

        for (MediaVisitingRecordVo recordVo : visitingRecordVos) {
            for (CpdailyUserWithTagTopknotVo userVo : userVos) {
                if (Objects.equals(recordVo.getVisitorId(), userVo.getUserId())) ;
                recordVo.setVisitor(userVo);
            }
        }
    }


}
