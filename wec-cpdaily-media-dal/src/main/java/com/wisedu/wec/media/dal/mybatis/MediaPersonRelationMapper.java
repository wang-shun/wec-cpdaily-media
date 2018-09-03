package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.MediaManager;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.wisedu.wec.media.common.old.po.MediaPersonRelation;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;


/**
 * 校园号与人的关系关联表mapper
 * @author mdmo
 *
 */
public interface MediaPersonRelationMapper {

    int insert(MediaPersonRelation record);

    int insertSelective(MediaPersonRelation record);

    int insertBatch(List<MediaPersonRelation> record);

    int deleteByColumn(@Param("columnName") String columnName, @Param("columnValue") Object columnValue);

    int deleteManagerByMediaId(@Param("mediaId") String mediaId);

    @Delete("<script> update t_cpdaily_media_person_relation " +
            " set is_deleted = 1 " +
            " where media_id = #{mediaId,jdbcType=VARCHAR} and manage_type='MANAGE' and person_id in (" +
            " <foreach collection =\"admins\" item=\"admin\" separator=\",\" >" +
            " #{admin,jdbcType=VARCHAR}" +
            " </foreach>" +
            ") </script>")
    int deleteManagerByMediaIdAndPersonIds(@Param("mediaId") String mediaId, @Param("admins") List<String> admins);

    int insertManager(@Param("mediaPersonRelations") List<MediaPersonRelation> mediaPersonRelations);

    @Select("SELECT * FROM t_cpdaily_media_person_relation " +
            "where is_deleted = 0 and manage_type = 'MANAGE' and media_id = #{mediaId} ")
    @ResultType(MediaPersonRelation.class)
    List<MediaPersonRelation> getMediaManagers(@Param("mediaId") String mediaId);

    @Select("SELECT * FROM t_cpdaily_media_person_relation " +
            "where is_deleted = 0  and media_id = #{mediaId} ")
    @ResultType(MediaPersonRelation.class)
    List<MediaPersonRelation> getMediaManagersAndOwner(@Param("mediaId") String mediaId);

    @Select("SELECT * FROM t_cpdaily_media_person_relation where is_deleted = 0 and manage_type = 'OWNER' and media_id = #{mediaId}")
    @ResultType(MediaPersonRelation.class)
    MediaPersonRelation getMediaOwner(@Param("mediaId") String mediaId);


    @Select("select media_id from t_cpdaily_media_person_relation where person_id = #{personId} " +
            "and manage_type = 'MANAGE' and is_deleted = 0")
    @ResultType(java.lang.String.class)
    List<String> getMediaIdsByPersonId(@Param("personId") String personId);
}