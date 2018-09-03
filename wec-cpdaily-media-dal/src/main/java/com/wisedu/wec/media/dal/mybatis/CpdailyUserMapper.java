package com.wisedu.wec.media.dal.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.po.CpdailyUserMapperSqlProvider;
import com.wisedu.wec.media.common.old.po.MediaFansCount;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;

public interface CpdailyUserMapper {

	@Select("select wId from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = #{userType}")
	List<String> selectWidsByUserType(@Param("tenantId") String tenantId, @Param("userType") String userType);

	@Select("select USER_ID from t_cpdaily_userrelations where FOLLOW_ID = #{userId}")
	List<String> selectWidsByUserFans(@Param("userId") String userId);

	@Select("select count(*) from t_cpdaily_userrelations where FOLLOW_ID = #{userId}")
	int selectFansNumber(@Param("userId") String userId);

	/**
	 * 批量查询校园号的关注人数/粉丝数
	 * 
	 * @param mediaIds
	 * @return
	 */
	@SelectProvider(type = CpdailyUserMapperSqlProvider.class, method = "batchSelectFansNumber")
	@ResultType(MediaFansCount.class)
	List<MediaFansCount> batchSelectFansNumber(@Param("list") List<String> mediaIds);

	@Select("select user_id from t_cpdaily_media_user_group_relation where group_id = #{groupId}")
	List<String> selectWidsByGroup(@Param("groupId") String groupId);

	@Select("select wId from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'STUDENT' and major_Id = #{majorId}")
	List<String> selectWidsByMajorId(@Param("tenantId") String tenantId, @Param("majorId") String majorId);

	@Select("select wId from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'STUDENT' and academy_Id = #{academyId}")
	List<String> selectWidsByAcademyId(@Param("tenantId") String tenantId, @Param("academyId") String academyId);

	@Select("select wId from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'TEACHER' and depart_Id = #{departId}")
	List<String> selectWidsByDepartId(@Param("tenantId") String tenantId, @Param("departId") String departId);

	@Select("select wId from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'TEACHER' and depart_Id like #{departId}")
	List<String> selectWidsByDepartIdFuzzy(@Param("tenantId") String tenantId, @Param("departId") String departId);

	/**
	 * 根据院系查学生(树结构使用)
	 *
	 * @param academyId
	 * @return
	 */
	@Select("select wId,name,img,class_id,gender,depart_Id,open_id from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'STUDENT' and academy_Id = #{academyId}")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> selectByAcademyId4Group(@Param("tenantId") String tenantId,
			@Param("academyId") String academyId);

	@Select("select wId,name,img,class_id,gender,depart_Id,open_id from t_cpdaily_users "
			+ " where tenant_Id = #{tenantId} and user_Type = 'STUDENT' and academy_Id = #{academyId} "
			+ " and (name like #{name} or open_id like #{name})")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> searchByAcademyIdName4Group(@Param("tenantId") String tenantId,
			@Param("academyId") String academyId, @Param("name") String name);

	@Select("select wId,name,img,class_id,gender,depart_Id,open_id from t_cpdaily_users "
			+ "where tenant_Id = #{tenantId} and user_Type = 'STUDENT' "
			+ "and (name like #{name} or open_id like #{name})")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> searchStudent(@Param("tenantId") String tenantId, @Param("name") String name);

	@Select("select wId,name,img,class_id,gender,depart_Id,open_id from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'STUDENT'")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> selectStudent(String tenantId);

	/**
	 * 根据专业查学生(树结构使用)
	 *
	 * @param majorId
	 * @return
	 */
	@Select("select wId,name,img,class_id,gender,depart_Id,open_id from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'STUDENT' and major_Id = #{majorId}")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> selectByMajorId4Group(@Param("tenantId") String tenantId, @Param("majorId") String majorId);

	@Select("select wId,name,img,class_id,gender,depart_Id,open_id from t_cpdaily_users "
			+ "where tenant_Id = #{tenantId} and user_Type = 'STUDENT' and major_Id = #{majorId} "
			+ "and (name like #{name} or open_id like #{name})")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> searchByMajorIdName4Group(@Param("tenantId") String tenantId, @Param("majorId") String majorId,
			@Param("name") String name);

	@Select("select wId,name,img,depart_Id,gender,class_id,open_id from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'TEACHER' and depart_Id like #{departId}")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> selectByDepartId4Group(@Param("tenantId") String tenantId, @Param("departId") String departId);

	@Select("select wId,name,img,depart_Id,gender,class_id,open_id from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = 'TEACHER'")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> selectTeacher(@Param("tenantId") String tenantId);

	@Select("select wId,name,img,depart_Id,gender,class_id,open_id from t_cpdaily_users "
			+ "where tenant_Id = #{tenantId} and user_Type = 'TEACHER' and depart_Id like #{departId} "
			+ "and (name like #{name} or open_id like #{name})")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> searchByDepartIdName4Group(@Param("tenantId") String tenantId,
			@Param("departId") String departId, @Param("name") String name);

	@Select("select wId,name,img,depart_Id,gender,class_id,open_id from t_cpdaily_users "
			+ "where tenant_Id = #{tenantId} and user_Type = 'TEACHER' and (name like #{name} or open_id like #{name})")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id") })
	List<UserWithGroup> searchTeacher(@Param("tenantId") String tenantId, @Param("name") String name);

	@Select("select wId,name,img,depart_Id,gender,class_id,open_id,pwid,academy_Id,major_Id,user_type from t_cpdaily_users "
			+ "where tenant_Id = #{tenantId} and user_Type = #{userType} and grade = #{grade} and (name like #{name} or open_id like #{name})")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id"), @Result(property = "personId", column = "pwid"),
			@Result(property = "userType", column = "user_type"), @Result(property = "academyId", column = "academy_Id"),
			@Result(property = "majorId", column = "major_Id")})
	List<UserWithGroup> selectUsersByUserTypeAndNameAndGrade(@Param("tenantId") String tenantId, @Param("userType") String userType, @Param("name") String name, @Param("grade") String grade);

	@Select("select wId,name,img,depart_Id,gender,class_id,open_id,pwid,academy_Id,major_Id,user_type from t_cpdaily_users "
			+ "where tenant_Id = #{tenantId} and user_Type = #{userType} and grade = #{grade} ")
	@Results({ @Result(property = "userId", column = "wId"), @Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"), @Result(property = "classId", column = "class_id"),
			@Result(property = "gender", column = "gender"), @Result(property = "departId", column = "depart_Id"),
			@Result(property = "openId", column = "open_id"), @Result(property = "personId", column = "pwid"),
			@Result(property = "userType", column = "user_type"), @Result(property = "academyId", column = "academy_Id"),
			@Result(property = "majorId", column = "major_Id")})
	List<UserWithGroup> selectUsersByUserTypeAndGrade(@Param("tenantId") String tenantId, @Param("userType") String userType, @Param("grade") String grade);

	List<UserWithGroup> selectByUserIds4Group(List<String> userIds);

	List<UserWithGroup> selectByUserIdsAndName4Group(Map<String, Object> searchMap);

	List<UserWithGroup> selectByUserIdsAndName4GroupNoOwner(Map<String, Object> searchMap);

	CpdailyUser selectByUserId(String userId);

	List<CpdailyUser> selectByPersonId(String pwid);

	List<CpdailyUser> selectByPersonIds(@Param("personIds") List<String> personIds);

	List<CpdailyUser> selectByUserIds(@Param("userIds") List<String> userIds);

	/**
	 * 统计学生各院系人数使用
	 *
	 * @param tenantId
	 * @return
	 */
	@Select("select academy_Id, major_Id from t_cpdaily_users where user_Type = 'STUDENT' and tenant_Id = #{tenantId}")
	@Results({ @Result(property = "academyId", column = "academy_Id"),
			@Result(property = "majorId", column = "major_Id") })
	List<CpdailyUser> selectStudentByTenantId4Count(String tenantId);

	/**
	 * 统计学生院系专业信息
	 *
	 * @param tenantId
	 * @return
	 */
	@Select("select academy_Id, major_Id from t_cpdaily_users where user_Type = 'STUDENT' and tenant_Id = #{tenantId} and academy_Id is not null ")
	@Results({ @Result(property = "academyId", column = "academy_Id"),
			@Result(property = "majorId", column = "major_Id") })
	List<CpdailyUser> selectAcademyAndMajors(String tenantId);

	/**
	 * 统计老师各部门人数使用
	 *
	 * @param tenantId
	 * @return
	 */
	@Select("select depart_Id from t_cpdaily_users where user_Type = 'TEACHER' and tenant_Id = #{tenantId}")
	List<String> selectTeacherByTenantId4Count(String tenantId);

	int insert(CpdailyUser record);

	@Update("update t_cpdaily_users set img = #{img} where wId = #{wid}")
	int updateUserImg(@Param("img") String img, @Param("wid") String wid);

	@Update("update t_cpdaily_users set signature = #{signature} where wId = #{wid}")
	int updateUserSignature(@Param("signature") String signature, @Param("wid") String wid);

	@Update("update t_cpdaily_users set status='DISABLE' where wId = #{wid}")
	int deleteUser(@Param("wid")String userId);
	
	@Update("update t_cpdaily_users set name=#{name} where wId = #{wid}")
	int updateUserName(@Param("wid")String wid, @Param("name")String name);

	@Select("select wId from t_cpdaily_users where tenant_Id = #{tenantId} and user_Type = #{userType} and grade = #{year}")
	List<String> selectWidsByYear(@Param("tenantId") String tenantId, @Param("userType") String userType, @Param("year") String year);
}
