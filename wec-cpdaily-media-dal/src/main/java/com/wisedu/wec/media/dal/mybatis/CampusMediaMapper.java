package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.CampusMediaExample;


public interface CampusMediaMapper {
  long countByExample(CampusMediaExample example);

  int deleteByExample(CampusMediaExample example);

  int deleteByPrimaryKey(String wid);

  int insert(CampusMedia record);

  int insertSelective(CampusMedia record);

  List<CampusMedia> selectByExample(CampusMediaExample example);

  CampusMedia selectByPrimaryKey(String wid);


  /**
   * 根据人ID获取用户拥有的校园号
   * @param personId
   * @return
   */
  @Select("select media.* from t_cpdaily_campus_media media "
  		+ " inner join t_cpdaily_media_person_relation rel on media.wid=rel.media_id  "
  		+ " where rel.person_id = #{personId} and media.IS_DELETE = 'ENABLE'  and rel.manage_type='OWNER' and rel.is_deleted = 0 "
  		+ " order by media.UPDATE_TIME desc,media.CREATE_TIME desc")
  @ResultType(CampusMedia.class)
  List<CampusMedia> selectMyOwnMedia(@Param("personId")String personId);

  @Select("select count(1) from t_cpdaily_campus_media media  inner join t_cpdaily_media_person_relation rel on media.wid=rel.media_id  where rel.person_id = #{personId} and media.IS_DELETE = 'ENABLE' and media.auth_status <> 'AUTH_FAIL'   and rel.manage_type='OWNER' and rel.is_deleted = 0 ")
  int selectMyMediaCount(@Param("personId")String personId);

  @Select("select * from t_cpdaily_campus_media where C_USER_ID = #{ownerId}")
  @ResultType(CampusMedia.class)
  List<CampusMedia> selectByOwnerId4Header(String ownerId);

  @Select("select * from t_cpdaily_campus_media where WID = #{wid}")
  @ResultType(CampusMedia.class)
  CampusMedia selectByMediaId(String wid);

  int updateByExampleSelective(@Param("record") CampusMedia record,
      @Param("example") CampusMediaExample example);

  int updateByExampleWithBLOBs(@Param("record") CampusMedia record,
      @Param("example") CampusMediaExample example);

  int updateByExample(@Param("record") CampusMedia record,
      @Param("example") CampusMediaExample example);

  int updateByPrimaryKeySelective(CampusMedia record);

  int updateByPrimaryKeyWithBLOBs(CampusMedia record);

  int updateByPrimaryKey(CampusMedia record);
  

  /**
   * 根据用户ID获取用户拥有的以及有管理权限的校园号
   * @param pid
   * @return
   */
  @Select("select media.*, rel.manage_type from t_cpdaily_campus_media media "
  		+ " inner join t_cpdaily_media_person_relation rel on media.wid=rel.media_id  "
  		+ " where rel.person_id = #{pid} and media.IS_DELETE = 'ENABLE'  and rel.is_deleted = 0 order by media.UPDATE_TIME")
  @ResultType(CampusMedia.class)
  List<CampusMedia> selectByPersonId(String pid);

  @Insert(" INSERT INTO t_cpdaily_media_person_relation(media_id,person_id) "
    +" (SELECT media.`WID` AS media_id,#{personId} AS person_id FROM t_cpdaily_campus_media media "
    + " WHERE media.c_user_id IN ( SELECT u.`wId` FROM t_cpdaily_users u WHERE u.`pwid`=#{personId})"
 	+" AND media.`WID` NOT IN (SELECT rel.media_id FROM t_cpdaily_media_person_relation rel WHERE rel.`person_id`=#{personId}))")
  int inserMediaPersonRelation(String personId);

  @Update(" update t_cpdaily_campus_media set sms_remain = sms_remain - #{consumeCount} where wid=#{mediaId}")
  void udpateSmsRemain(@Param("mediaId")String mediaId, @Param("consumeCount")int consumeCount);

  @Update(" update t_cpdaily_campus_media set last_read_comment_time = now() where wid=#{mediaId}")
  void updateLastReadCommentTime(@Param("mediaId")String mediaId);
}
