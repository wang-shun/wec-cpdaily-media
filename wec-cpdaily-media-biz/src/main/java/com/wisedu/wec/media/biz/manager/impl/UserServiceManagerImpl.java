package com.wisedu.wec.media.biz.manager.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wisedu.wec.cpdaily.common.enumeration.CpdailyStatusEnum;
import com.wisedu.wec.cpdaily.user.dubbo.dto.CpdailyUserBaseDTO;
import com.wisedu.wec.cpdaily.user.dubbo.dto.CpdailyUserShowDTO;
import com.wisedu.wec.cpdaily.user.dubbo.query.CpdailyUserUpdateAttributesQuery;
import com.wisedu.wec.cpdaily.user.dubbo.service.CpdailyUserExtReadService;
import com.wisedu.wec.cpdaily.user.dubbo.service.CpdailyUserReadService;
import com.wisedu.wec.cpdaily.user.dubbo.service.CpdailyUserWriteService;
import com.wisedu.wec.media.biz.manager.UserServiceManager;
import com.wisedu.wec.media.common.vo.CpdailyTagVo;
import com.wisedu.wec.media.common.vo.CpdailyTopknotVo;
import com.wisedu.wec.media.common.vo.CpdailyUserWithTagTopknotVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceManagerImpl implements UserServiceManager {
    @Reference(version = "1.0.0")
    private CpdailyUserReadService userReadService;
    @Reference(version = "1.0.0")
    private CpdailyUserWriteService userWriteService;
    @Reference(version = "1.0.0")
    private CpdailyUserExtReadService userExtReadService;


    @Override
    public List<String> listFansIdByUserId(String userId){
        return userExtReadService.listFansIdByUserId(userId);
    }
    @Override
    public List<String> listVisitIdByUserId(String userId){
        return userExtReadService.listVisitIdByUserId(userId);
    }
    @Override
    public CpdailyUserBaseDTO getUserBaseByUserId(String userId){
        return userReadService.getUserBaseByUserId(userId);
    }


    @Override
    public void disableUser(String userId){

        CpdailyUserUpdateAttributesQuery updateParam = new CpdailyUserUpdateAttributesQuery();
        updateParam.setUserId(userId);
        updateParam.setStatus(CpdailyStatusEnum.DISABLE);
        userWriteService.updateUserByAttributes(updateParam);
    }

    @Override
    public List<CpdailyUserBaseDTO> listUserBaseByPwid(String personId){
        return userReadService.listUserBaseByPwid(personId);
    }


    /**
     * 获取带用户头像的信息
     *
     * @param userIds
     * @return
     */
    @Override
    public List<CpdailyUserWithTagTopknotVo> listUserWithTagTopknotByUserIdList(List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        List<CpdailyUserShowDTO> dtoList = userReadService.listUserShowByUserIdList(userIds);
        if (CollectionUtils.isEmpty(dtoList)) {
            return new ArrayList<>();
        }
        return dtoList.stream().map(dto -> userDto2Vo(dto)).collect(Collectors.toList());
    }

    private CpdailyUserWithTagTopknotVo userDto2Vo(CpdailyUserShowDTO dto) {

        CpdailyUserWithTagTopknotVo vo = new CpdailyUserWithTagTopknotVo();
        BeanUtils.copyProperties(dto, vo);

        if (!CollectionUtils.isEmpty(dto.getTagDTOs())) {
            List<CpdailyTagVo> cpdailyTagVos = dto.getTagDTOs().stream().map(cpdailyTagDTO -> {
                CpdailyTagVo cpdailyTagVo = new CpdailyTagVo();
                BeanUtils.copyProperties(dto.getTagDTOs(), cpdailyTagVo);
                return cpdailyTagVo;
            }).collect(Collectors.toList());
            vo.setTagDTOs(cpdailyTagVos);
        }

        if (dto.getTopknotDTO() != null) {
            CpdailyTopknotVo topknotVo = new CpdailyTopknotVo();
            BeanUtils.copyProperties(dto.getTopknotDTO(), topknotVo);
            vo.setTopknotDTO(topknotVo);
        }
        return vo;

    }
}
