package com.wisedu.wec.media.biz.old.service.impl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.wisedu.wec.media.common.old.constants.MsgConstants;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.vo.AcademyMajor;
import com.wisedu.wec.media.dal.mybatis.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.utils.ArraySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.wisedu.wec.media.common.old.constants.UserType;
import com.wisedu.wec.media.common.old.context.MediaInfoContext;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.po.StudentClass;
import com.wisedu.wec.media.common.old.po.UserGroup;
import com.wisedu.wec.media.common.old.po.UserGroupRelation;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.Dept;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;

@Service
public class UserTreeService {

	private static final Logger logger = LoggerFactory.getLogger(UserTreeService.class);

	@Autowired
	private UserGroupMapper userGroupMapper;
	@Autowired
	private UserGroupRelationMapper userGroupRelationMapper;
	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;
	@Autowired
	private AcademyMajorService academyMajorService;
	@Autowired
	private AcademyMajorMapper academyMajorMapper;
	@Autowired
	private StaffDepartService staffDepartService;
	@Autowired
	private StaffDepartMapper departMapper;
	@Autowired
	private StudentClassMapper classMapper;
	@Autowired
	private CampusMediaMapper campusMediaMapper;

	@Autowired
	private CpdailyAcademyMajorMapper cpdailyAcademyMajorMapper;
	
	/**
	 * 查询媒体号自定义的用户组
	 *
	 * @return
	 */
	public Dept queryUserGroup() {

		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

		MediaInfoContext mediaInfoContext = ThreadLocalUserInfo.getContext().getMediaMap().get(mediaId);
		String tenantId = mediaInfoContext.getTenantId();
		
		List<Dept> depts = userGroupMapper.selectMineGroup(mediaId, tenantId);

		List<UserGroup> countList = userGroupRelationMapper.selectGroupIdUserCount(mediaId);
		Map<String, Integer> countMap = new HashMap<>();
		for (UserGroup ut : countList) {
			countMap.put(ut.getGroupId(), ut.getUserCount());
		}

		for (Dept dept : depts) {
			Integer q = countMap.get(dept.getId());
			if (q != null) {
				dept.setQuantity(countMap.get(dept.getId()));
			} else {
				dept.setQuantity(0);
			}
		}

		for (Dept dept : depts) {
			dept.setPid("root");
			dept.setPath(dept.getTitle());
		}

		// 默认的粉丝用户组
		Dept defaultDept = new Dept();
		defaultDept.setId("fans");

		int fansCount = cpdailyUserMapper.selectFansNumber(mediaId);
		defaultDept.setQuantity(fansCount);

		defaultDept.setPid("root");
		defaultDept.setTitle("关注我的用户");
		defaultDept.setPath("关注我的用户");

		// root
		Dept rootDept = new Dept();
		rootDept.setId("root");
		List<Dept> rootChildren = new ArrayList<>();
		rootChildren.add(defaultDept);
		rootChildren.addAll(depts);
		rootDept.setChildren(rootChildren);

		return rootDept;

	}

	/**
	 * 查询公共用户组
	 *
	 * @return
	 */
	public Dept queryCommonUserGroup() {

		UserInfoContext userInfoContext = ThreadLocalUserInfo.getContext();

		String tenantId = userInfoContext.getTenantId();
		String mediaId = userInfoContext.getMediaId();

//		MediaInfoContext media = ThreadLocalUserInfo.getContext().getMediaMap().get(mediaId);
		CampusMedia media = campusMediaMapper.selectByMediaId(mediaId);
		Dept root = new Dept();

		if (media.isCanSeeOrgStructure()) {
			List<Dept> studentGroup = academyMajorService.createDeptStruct(tenantId);
			List<Dept> teacherGroup = staffDepartService.createDeptStruct(tenantId);

			Dept teacher = new Dept();
			teacher.setId("teacher");
			teacher.setPid("root");
			teacher.setTitle("老师");
			teacher.setPath("老师TEACHER");
			teacher.setChildren(teacherGroup);

			Dept student = new Dept();
			student.setId("student");
			student.setPid("root");
			student.setTitle("学生");
			student.setPath("学生STUDENT");
			student.setChildren(studentGroup);

			Dept newStudent = new Dept();
			newStudent.setId("new");
			newStudent.setPid("root");
			newStudent.setTitle("新生");
			newStudent.setPath("新生NEW");
			newStudent.setChildren(new ArrayList<>());

			List<Dept> rootChildren = new ArrayList<>();
			rootChildren.add(newStudent);
			rootChildren.add(student);
			rootChildren.add(teacher);

			root.setChildren(rootChildren);
			root.setId("root");
		}

		return root;
	}


	/**
	 * 分页查询用户组下的用户(0-用户组, 1-学生, 2-老师)
	 *
	 * @param groupId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageVo<UserWithGroup> queryUsersByGroupId(String groupId, int pageNum, int pageSize, String optType) {

		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

		List<UserWithGroup> users = new ArrayList<>();
		long total = 0L;

		Page<Object> page = null;
		
		if (StringUtils.equals("0", optType)) {
			if ("fans".equals(groupId)) {
				List<String> wids = cpdailyUserMapper.selectWidsByUserFans(mediaId);

				if (CollectionUtils.isNotEmpty(wids)) {
					if(pageNum>0 && pageSize>0){
						page = PageHelper.startPage(pageNum, pageSize);
					}
					users = cpdailyUserMapper.selectByUserIds4Group(wids);
				}

			} else {
				if(pageNum>0 && pageSize>0){
					page = PageHelper.startPage(pageNum, pageSize);
				}
				users = userGroupRelationMapper.selectByGroupId(groupId);
			}
		} else if (StringUtils.equals("2", optType)) {

			if(pageNum>0 && pageSize>0){
				page = PageHelper.startPage(pageNum, pageSize);
			}
			if ("teacher".equals(groupId)) {
				users = cpdailyUserMapper.selectTeacher(tenantId);
			} else {
				// 默认
				if (groupId.endsWith("_other")) {
					groupId = groupId.replaceAll("_other", "");
					users = cpdailyUserMapper.selectByDepartId4Group(tenantId, groupId);
				} else {
					groupId = groupId + "%";
					users = cpdailyUserMapper.selectByDepartId4Group(tenantId, groupId);
				}
			}

		} else if (StringUtils.equals("1", optType)) {

			if(pageNum>0 && pageSize>0){
				page = PageHelper.startPage(pageNum, pageSize);
			}
			if ("student".equals(groupId)) {
				users = cpdailyUserMapper.selectStudent(tenantId);
			}else if ("new".equals(groupId)) {
				users = cpdailyUserMapper.selectUsersByUserTypeAndGrade(tenantId, MsgConstants.STUDENT.toUpperCase(), String.valueOf(LocalDate.now().getYear()));
			} else {
				boolean isAcademy = academyMajorMapper.selectWidByAcademyId(tenantId, groupId) > 0;
				page = PageHelper.startPage(pageNum, pageSize);
				if (isAcademy) {
					users = cpdailyUserMapper.selectByAcademyId4Group(tenantId, groupId);
				} else {
					users = cpdailyUserMapper.selectByMajorId4Group(tenantId, groupId);
				}
			}

		} else {
			throw new BadRequestException("illegal for param optType : " + optType, "-1");
		}

//		List<UserGroupRelation> relationList = userGroupRelationMapper.selectByMediaId(mediaId);

//		Map<String, String> groupIdNameMap = new HashMap<>();
//		List<UserGroup> groups = userGroupMapper.selectGroupIdNamesByMediaId(mediaId);
//
//		for (UserGroup t : groups) {
//			groupIdNameMap.put(t.getGroupId(), t.getGroupName());
//		}

		// 添加url前缀
		for (UserWithGroup ut : users) {
			String url = ut.getImgUrl();
			if (StringUtils.isNotEmpty(url)) {
				String url4Head = ImgUtils.imgUrlsSAddHost(url);
				ut.setImgUrl(url4Head);
			}
		}

//		queryGroups(users, relationList, groupIdNameMap);
		
		setClassAndDepartment(users, tenantId);
		
		total = page==null?users.size():page.getTotal();
		
		PageVo<UserWithGroup> pageVo = new PageVo<>();
		pageVo.setTotalSize(Long.valueOf(total).intValue());
		pageVo.setRows(users);
		pageVo.setPageNumber(pageNum);
		pageVo.setPageSize(pageSize);

		return pageVo;
	}

	/**
	 * 查询用户组属性
	 *
	 * @param users
	 * @param relationList
	 */
	private void queryGroups(List<UserWithGroup> users, List<UserGroupRelation> relationList, Map<String, String> groupIdNameMap) {

		if (CollectionUtils.isEmpty(users)) {
			return;
		}

		List<String> userIds = users.stream().map(UserWithGroup::getUserId).collect(Collectors.toList());

		Multimap<String, String> multiGroupMap = HashMultimap.create();

		for (UserGroupRelation r : relationList) {
			String userId = r.getUserId();
			if (userIds.contains(userId)) {
				multiGroupMap.put(r.getUserId(), r.getGroupId());
			}
		}

		for (UserWithGroup u : users) {
			String uid = u.getUserId();

			List<UserGroup> groups = new ArrayList<>();

			for (String groupId : multiGroupMap.get(uid)) {
				UserGroup t = new UserGroup();
				t.setGroupId(groupId);
				t.setGroupName(groupIdNameMap.get(groupId));

				groups.add(t);
			}

			u.setGroups(groups);
		}

	}



	/**
	 * 分页搜索用户组下的用户(0-用户组, 2-老师, 1-学生)
	 *
	 * @param groupId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageVo<UserWithGroup> searchUsersByName(String groupId, Integer pageNum, Integer pageSize, String optType, String name) {

		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

		if (StringUtils.isEmpty(name)) {
			return queryUsersByGroupId(groupId, pageNum, pageSize, optType);
		}

		name = "%" + name + "%";

		List<UserWithGroup> users = new ArrayList<>();
		long total = 0L;

		if (StringUtils.equals("0", optType)) { // 自定义用户组
			if ("fans".equals(groupId)) {

				Map<String, Object> searchMap = new HashMap<String, Object>();

				List<String> wids = cpdailyUserMapper.selectWidsByUserFans(mediaId);

				if (CollectionUtils.isNotEmpty(wids)) {
					searchMap.put("list", wids);
					searchMap.put("name", name);

					Page<Object> page = PageHelper.startPage(pageNum, pageSize);
					users = cpdailyUserMapper.selectByUserIdsAndName4Group(searchMap);
					total = page.getTotal();
				}

			} else {
				Page<Object> page = PageHelper.startPage(pageNum, pageSize);
				users = userGroupRelationMapper.searchByName(groupId, name);
				total = page.getTotal();
			}
		} else if (StringUtils.equals("2", optType)) { // 老师

			Page<Object> page = PageHelper.startPage(pageNum, pageSize);

			if ("teacher".equals(groupId)) {
				users = cpdailyUserMapper.searchTeacher(tenantId, name);
			} else {
				if (groupId.endsWith("_other")) {
					// 默认
					groupId = groupId.replaceAll("_other", "");
					users = cpdailyUserMapper.searchByDepartIdName4Group(tenantId, groupId, name);
				} else {
					groupId = groupId + "%";
					users = cpdailyUserMapper.searchByDepartIdName4Group(tenantId, groupId, name);
				}
			}

			total = page.getTotal();

		} else if (StringUtils.equals("1", optType)) { // 学生

			Page<Object> page = PageHelper.startPage(pageNum, pageSize);

			if ("student".equals(groupId)) {
				users = cpdailyUserMapper.searchStudent(tenantId, name);
			} else if ("new".equals(groupId)) {
				users = cpdailyUserMapper.selectUsersByUserTypeAndNameAndGrade(tenantId, MsgConstants.STUDENT.toUpperCase(), name, String.valueOf(LocalDate.now().getYear()));
			} else {
				boolean isAcademy = academyMajorMapper.selectWidByAcademyId(tenantId, groupId) > 0;
				page = PageHelper.startPage(pageNum, pageSize);
				if (isAcademy) {
					users = cpdailyUserMapper.searchByAcademyIdName4Group(tenantId, groupId, name);
				} else {
					users = cpdailyUserMapper.searchByMajorIdName4Group(tenantId, groupId, name);
				}
			}

			total = page.getTotal();

		} else {
			throw new BadRequestException("illegal for param optType : " + optType, "-1");
		}

//		List<UserGroupRelation> relationList = userGroupRelationMapper.selectByMediaId(mediaId);

		Map<String, String> groupIdNameMap = new HashMap<>();
		List<UserGroup> groups = userGroupMapper.selectGroupIdNamesByMediaId(mediaId);

		for (UserGroup t : groups) {
			groupIdNameMap.put(t.getGroupId(), t.getGroupName());
		}

		// 添加url前缀
		for (UserWithGroup ut : users) {
			String url = ut.getImgUrl();
			if (StringUtils.isNotEmpty(url)) {
				String url4Head = ImgUtils.imgUrlsSAddHost(url);
				ut.setImgUrl(url4Head);
			}
		}

//		queryGroups(users, relationList, groupIdNameMap);

		setClassAndDepartment(users, tenantId);

		PageVo<UserWithGroup> pageVo = new PageVo<>();
		pageVo.setTotalSize(Long.valueOf(total).intValue());
		pageVo.setRows(users);
		pageVo.setPageNumber(pageNum);
		pageVo.setPageSize(pageSize);

		return pageVo;
	}

	/**
	 * 设置用户用户类型，以及班级或者部门信息
	 * @param users
	 * @param tenantId
	 */
	public void setClassAndDepartment(List<UserWithGroup> users, String tenantId) {
		if(CollectionUtils.isEmpty(users)){
			return;
		}
		List<Dept> deptList = departMapper.selectByTenantIdConvert2Dept(tenantId);
		Map<String,Dept> deptMap = new HashMap<>();
		for(Dept dept : deptList){
			deptMap.put(dept.getId(), dept);
		}

		List<StudentClass> classList = classMapper.getAllClasses(tenantId);
		Map<String,StudentClass> classMap = new HashMap<>();
		for(StudentClass class_ : classList){
			classMap.put(class_.getClassId(), class_);
		}
		for(UserWithGroup user : users){
			if(StringUtils.isNotEmpty(user.getClassId())){
				user.setUserType(UserType.STUDENT.toString());
				StudentClass classInfo = classMap.get(user.getClassId());
				if(classInfo!=null){
					user.setClassName(classInfo.getClassName());
				}else{
					logger.error("班级有错误数据，找不到班级信息，班级ID：{}",user.getClassId());
				}
			}else if(StringUtils.isNotEmpty(user.getDepartId())){
				user.setUserType(UserType.TEACHER.toString());
				Dept dept = deptMap.get(user.getDepartId());
				if(dept!=null){
					user.setDepartName(dept.getTitle());
				}else{
					logger.error("部门有错误数据，找不到部门信息，部门ID：{}",user.getDepartId());
				}
			}
		}
	}
}
