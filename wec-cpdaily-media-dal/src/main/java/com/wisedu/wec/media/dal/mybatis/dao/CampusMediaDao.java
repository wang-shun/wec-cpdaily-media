package com.wisedu.wec.media.dal.mybatis.dao;


import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.MediaPersonRelation;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.MediaPersonRelationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class CampusMediaDao {
    @Autowired
    private CampusMediaMapper campusMediaMapper;
    @Autowired
    private MediaPersonRelationMapper mediaPersonRelationMapper;


    public boolean isManager(String personId, String mediaId) {
        if (StringUtils.isEmpty(personId) || StringUtils.isEmpty(mediaId)) {
            return false;
        }
        List<MediaPersonRelation> mediaManagers = mediaPersonRelationMapper.getMediaManagersAndOwner(mediaId);
        return mediaManagers.stream().anyMatch(mediaPersonRelation -> Objects.equals(mediaPersonRelation.getPersonId(), personId));
    }

    public void updateMediaVersion(String mediaId, String newVersion) {
        CampusMedia campusMedia = new CampusMedia();
        campusMedia.setWid(mediaId);
        campusMedia.setVersion(newVersion);
        campusMediaMapper.updateByPrimaryKeySelective(campusMedia);
    }

    public CampusMedia getMedia(String mediaId) {
       return campusMediaMapper.selectByMediaId(mediaId);
    }
}
