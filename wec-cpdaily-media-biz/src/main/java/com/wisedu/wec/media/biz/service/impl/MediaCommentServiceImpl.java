package com.wisedu.wec.media.biz.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.wisedu.cpdaily.message.api.InnerMailApi;
import com.wisedu.cpdaily.message.entity.request.InnerMailData;
import com.wisedu.cpdaily.message.entity.request.InnerMailRequest;
import com.wisedu.wec.cpdaily.user.dubbo.dto.CpdailyUserOftenDTO;
import com.wisedu.wec.cpdaily.user.dubbo.service.CpdailyUserReadService;
import com.wisedu.wec.media.biz.service.MediaCommentService;
import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.exception.ValidateException;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.MediaComment;
import com.wisedu.wec.media.common.param.MediaCommentParam;
import com.wisedu.wec.media.common.vo.MediaCommentUserVo;
import com.wisedu.wec.media.common.vo.MediaCommentVo;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.MediaPersonRelationMapper;
import com.wisedu.wec.media.dal.mybatis.dao.MediaCommentDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MediaCommentServiceImpl implements MediaCommentService {
    private static Logger logger = LoggerFactory.getLogger(MediaCommentServiceImpl.class);

    @Autowired
    private MediaCommentDao mediaCommentDao;
    @Autowired
    private CampusMediaMapper campusMediaMapper;
    @Autowired
    private MediaPersonRelationMapper mediaPersonRelationMapper;
    @Reference(version = "1.0.0")
    private CpdailyUserReadService userService;
    @Reference(version = "1.0.0")
    private InnerMailApi innerMailApi;


    @Override
    public int addMediaComment(MediaCommentParam mediaCommentParam) {
        String mediaId = mediaCommentParam.getMediaId();
        CampusMedia campusMedia = campusMediaMapper.selectByMediaId(mediaId);
        if (campusMedia == null) {
            throw new ValidateException("校园号不存在");
        }
        String userId = ThreadLocalUserInfo.getContext().getLoginUserId();

        MediaComment mediaComment = new MediaComment();
        BeanUtils.copyProperties(mediaCommentParam, mediaComment);
        mediaComment.setUserId(userId);
        int res = mediaCommentDao.insertSelective(mediaComment);

        boolean isManager = isManager(mediaId);
        //校园好回复留言，发送小黑板提醒用户
        if (isManager && mediaComment.getParentId() != null) {
            InnerMailRequest request = new InnerMailRequest();
            request.setUserId(mediaComment.getReplyUserId());
            request.setTenantId(campusMedia.getTenantId());

            InnerMailData innerMailData = new InnerMailData();
            innerMailData.setContent("校园号“" + campusMedia.getName() + "”回复了你的留言");
            innerMailData.setNoticeType(7);
            innerMailData.setLinkUrl("mamp://mediaNote?id=" + mediaComment.getParentId());
            request.setData(innerMailData);
            innerMailApi.innerMail(request);
        }
        return res;
    }


    @Override
    public List<MediaCommentVo> listMediaComment(String mediaId, Page page) {
        if (StringUtils.isEmpty(mediaId)) {
            throw new ValidateException("校园号不能为空");
        }

        if (isManager(mediaId)) {
            campusMediaMapper.updateLastReadCommentTime(mediaId);
        }
        //查询一级评论
        List<MediaComment> mediaComments = mediaCommentDao.listMediaComment(mediaId, 0L, page);
        if (CollectionUtils.isEmpty(mediaComments)) {
            return Lists.newArrayList();
        }

        //转换为vo对象
        List<MediaCommentVo> commentVos = listToVo(mediaComments);

        //查询二级评论
        commentVos.forEach(mediaCommentVo -> {
            Page childPage = new Page();
            childPage.setPageSize(5);
            List<MediaComment> childComments = mediaCommentDao.listChildComments(mediaId, mediaCommentVo.getId(), page);
            mediaCommentVo.setChildComments(listToVo(childComments));
        });

        //设置用户头像昵称院系
        setUserInfo(commentVos);

        return commentVos;
    }

    @Override
    public List<MediaCommentVo> listChildMediaComment(Long id, String mediaId, Page page) {
        if (id == null) {
            throw new ValidateException("留言编号不能为空");
        }
        List<MediaComment> childComments = mediaCommentDao.listChildComments(mediaId, id, page);
        List<MediaCommentVo> mediaCommentVos = listToVo(childComments);
        setUserInfo(mediaCommentVos);
        return mediaCommentVos;
    }

    @Override
    public MediaCommentVo getMediaComment(Long id) {
        if (id == null || id == 0) {
            throw new ValidateException("留言编号不能为空");
        }
        MediaComment mediaComment = mediaCommentDao.getMediaComment(id);
        if (mediaComment == null) {
            throw new ValidateException("留言不存在或已被删除");
        }
        MediaCommentVo mediaCommentVo = mediaCommentToVo(mediaComment);

        Page childPage = new Page();
        childPage.setPageSize(5);
        List<MediaComment> childComments = mediaCommentDao.listChildComments(mediaComment.getMediaId(), mediaCommentVo.getId(), childPage);
        mediaCommentVo.setChildComments(listToVo(childComments));

        //设置用户头像昵称院系
        setUserInfo(Arrays.asList(mediaCommentVo));
        return mediaCommentVo;

    }

    @Override
    public int deleteMediaComment(Long id) {
        if (id == null) {
            throw new ValidateException("留言编号不能为空");
        }
        MediaComment mediaComment = mediaCommentDao.getMediaComment(id);
        if (mediaComment == null) {
            throw new ValidateException("留言不存在或已被删除");
        }
        String userId = ThreadLocalUserInfo.getContext().getLoginUserId();

        boolean hasPermission = false;
        if (mediaComment.getUserId().equals(userId)) {
            hasPermission = true;
        } else {
            hasPermission = isManager(mediaComment.getMediaId());
        }
        if (!hasPermission) {
            throw new ValidateException("留言删除失败");
        }

        return mediaCommentDao.deleteByPrimaryKey(id);

    }

    private boolean isManager(String mediaId) {
        return Objects.equals(mediaId, ThreadLocalUserInfo.getContext().getMediaId());
        /*String userId = ThreadLocalUserInfo.getContext().getLoginUserId();
        if (StringUtils.isEmpty(userId)) {
            return false;
        }
        List<MediaPersonRelation> mediaManagers = mediaPersonRelationMapper.getMediaManagersAndOwner(mediaId);
        return mediaManagers.stream().anyMatch(mediaPersonRelation -> mediaPersonRelation.getUserId().equals(userId));*/
    }

    private MediaCommentVo mediaCommentToVo(MediaComment mediaComment) {
        MediaCommentVo vo = new MediaCommentVo();
        BeanUtils.copyProperties(mediaComment, vo);
        return vo;
    }

    private List<MediaCommentVo> listToVo(List<MediaComment> mediaComments) {
        if (CollectionUtils.isEmpty(mediaComments)) {
            return Lists.newArrayList();
        }
        return mediaComments.stream().map(this::mediaCommentToVo).collect(Collectors.toList());
    }

    private void setUserInfo(List<MediaCommentVo> commentVos) {
        if (CollectionUtils.isEmpty(commentVos)) {
            return;
        }

        //将留言和回复加到同一个列表
        List<MediaCommentVo> commentVosWithChilds = new ArrayList<>(commentVos);
        for (MediaCommentVo vo : commentVos) {
            if (vo.getChildComments() != null) {
                commentVosWithChilds.addAll(vo.getChildComments());
            }
        }
        commentVos = commentVosWithChilds;

        //查询回复者和被回复者基本信息
        List<String> userIds = new ArrayList<>();
        for (MediaCommentVo vo : commentVos) {
            userIds.add(vo.getUserId());
            userIds.add(vo.getReplyUserId());
        }
        userIds = userIds.stream().filter(id->StringUtils.isNotEmpty(id)).distinct().collect(Collectors.toList());

        List<CpdailyUserOftenDTO> cpdailyUserOftenDTOS = userService.listUserOftenByUserIdList(userIds);
        if (CollectionUtils.isEmpty(cpdailyUserOftenDTOS)) {
            return;
        }

        for (MediaCommentVo commentVo : commentVos) {
            for (CpdailyUserOftenDTO user : cpdailyUserOftenDTOS) {
                //昵称不存在时展示昵称
                if (StringUtils.isEmpty(user.getAlias())) {
                    user.setAlias(user.getName());
                }
                //设置留言人信息
                if (Objects.equals(commentVo.getUserId(), user.getUserId())) {
                    commentVo.setCommenter(new MediaCommentUserVo(user.getUserId(), user.getAlias(), user.getAvatar(), user.getBranchName()));
                }
                //设置被回复人信息
                if (Objects.equals(commentVo.getReplyUserId(), user.getUserId())) {
                    commentVo.setCommentee(new MediaCommentUserVo(user.getUserId(), user.getAlias(), user.getAvatar(), user.getBranchName()));
                }
            }
        }
    }

    @Override
    public void exception() {
        //throw new RuntimeException("访问出错");
    }

}
