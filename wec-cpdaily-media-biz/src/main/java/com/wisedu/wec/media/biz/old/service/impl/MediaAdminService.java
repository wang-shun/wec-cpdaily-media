package com.wisedu.wec.media.biz.old.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wisedu.wec.media.biz.old.config.MediaSettings;
import com.wisedu.wec.media.biz.redis.RedisService;
import com.wisedu.wec.media.common.old.constants.*;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.exception.NotFoundException;
import com.wisedu.wec.media.common.old.po.*;
import com.wisedu.wec.media.common.old.util.DesUtil;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.*;
import com.wisedu.wec.media.dal.mybatis.*;
import com.wisedu.wecloud.commons.util.UniqueIdentifierUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.utils.ArraySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wisedu.wec.media.common.old.vo.AcademyMajor;
import javax.print.attribute.standard.Media;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class MediaAdminService {

	private static final Logger logger = LoggerFactory.getLogger(MediaAdminService.class);

	@Autowired
	private CampusMediaMapper campusMediaMapper;

	@Autowired
	private CpdailyPersonMapper cpdailyPersonMapper;
	
	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;
	
	@Autowired
	private CpdailyTenantMapper cpdailyTenantMapper;
	
	@Autowired
	private MediaApplyLimitMapper applyLimitMapper;

	@Autowired
	private MediaPersonRelationMapper mediaPersonRelationMapper;

	@Autowired
	private MediaSettings settings;
	@Autowired
	private RedisService redisService;

	@Autowired
	private StaffDepartMapper departMapper;
	@Autowired
	private StudentClassMapper classMapper;

	@Autowired
	private CpdailyUserMapper userMapper;

	@Autowired
	private CpdailyAcademyMajorMapper cpdailyAcademyMajorMapper;


	@Value("${media.own.count.limit:5}")
	private Integer personOwnMediaCountLimit;


	public List<MediaManager> queryAdminsByMediaId(String mediaId) {
		List<MediaManager> list = new ArrayList<>();

		List managerList = getMediaManagerList(mediaId);
		if (managerList != null && !managerList.isEmpty()) {
			list.addAll(managerList);
		}

		MediaManager mediaOwner = getMediaOwner(mediaId);
		if (mediaOwner != null) {
			list.add(mediaOwner);
		}

		return list;
	}

	/**
	 * 分页查询校园号粉丝
	 *
	 * @param mediaId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageVo<UserWithGroup> queryFansByMediaId(String mediaId, String name, int pageNum, int pageSize) {
		List<UserWithGroup> users = new ArrayList<>();
		CampusMedia media = campusMediaMapper.selectByMediaId(mediaId);
		List<String> wids = cpdailyUserMapper.selectWidsByUserFans(mediaId);

		MediaManager owner = this.getMediaOwner(mediaId);

		Page<Object> page = null;
		long total = 0;

		Map<String, Object> searchMap = new HashMap<String, Object>();

		if (CollectionUtils.isNotEmpty(wids)) {
			searchMap.put("list", wids);
			searchMap.put("name", name);
			searchMap.put("pwid", owner.getPersonId());

			if(pageNum>0 && pageSize>0){
				page = PageHelper.startPage(pageNum, pageSize);
			}
			users = cpdailyUserMapper.selectByUserIdsAndName4GroupNoOwner(searchMap);
			total = page.getTotal();
		}

		// 添加url前缀
		for (UserWithGroup ut : users) {
			String url = ut.getImgUrl();
			if (StringUtils.isNotEmpty(url)) {
				String url4Head = ImgUtils.imgUrlsSAddHost(url);
				ut.setImgUrl(url4Head);
			}
		}

		setClassAndDepartment(users, media.getTenantId());

		total = (page==null?users.size():page.getTotal());

		PageVo<UserWithGroup> pageVo = new PageVo<>();
		pageVo.setTotalSize(Long.valueOf(total).intValue());
		pageVo.setRows(users);
		pageVo.setPageNumber(pageNum);
		pageVo.setPageSize(pageSize);

		return pageVo;
	}


	public void setMediaAdmins (MediaAdmins mediaAdmins) {

		// 删除原管理员
		mediaPersonRelationMapper.deleteManagerByMediaId(mediaAdmins.getMediaId());
		// 设置新管理员
		addMediaAdmins(mediaAdmins);
	}


	public void addMediaAdmins (MediaAdmins mediaAdmins) {

		List<String> managers = mediaAdmins.getAdmins(); // userIds 转 personIds
		if (managers != null && !managers.isEmpty()) {
			List<CpdailyUser> users = userMapper.selectByUserIds(managers);
			// 原有管理员
			List<MediaManager> existAdmins = this.getMediaManagerList(mediaAdmins.getMediaId());
			List<CpdailyUser> toAddUsers = new ArrayList<>();
			for (CpdailyUser u : users) {
				if (u.getPwid() != null && !u.getPwid().equals("") && existAdmins.stream().noneMatch(adm -> adm.getPersonId().equals(u.getPwid()))) {
					toAddUsers.add(u);
				}
			}

			if (toAddUsers.size() > 0) {
				List<MediaPersonRelation> mediaPersonRelations = new ArrayList<>();
				for (CpdailyUser user : toAddUsers) {
					if (mediaPersonRelations.stream().noneMatch(rel -> rel.getPersonId().equals(user.getPwid()))) {
						MediaPersonRelation mpr = new MediaPersonRelation();
						mpr.setPersonId(user.getPwid());
						mpr.setUserId(user.getWid());
						mpr.setMediaId(mediaAdmins.getMediaId());
						mpr.setManageType(MediaPersonManageType.MANAGE.toString());
						mediaPersonRelations.add(mpr);
					}
				}
				mediaPersonRelationMapper.insertManager(mediaPersonRelations);
			} else {
				throw new RuntimeException("用户为空，或新增用户没有PersonId");
			}
		}
	}


	public void delMediaAdmins (MediaAdmins mediaAdmins) {
		mediaPersonRelationMapper.deleteManagerByMediaIdAndPersonIds(mediaAdmins.getMediaId(), mediaAdmins.getAdmins());
	}


	/**
	 * 设置用户用户类型，以及班级或者部门信息
	 * @param users
	 * @param tenantId
	 */
	private void setClassAndDepartment(List<UserWithGroup> users, String tenantId) {
		if(CollectionUtils.isEmpty(users)){
			return ;
		}
		List<Dept> deptList = departMapper.selectByTenantIdConvert2Dept(tenantId);
		Map<String,Dept> deptMap = new HashMap<>();
		for(Dept dept : deptList){
			deptMap.put(dept.getId(), dept);
		}

		for(UserWithGroup user : users){
			if(!user.getUserType().toUpperCase().equals("STUDENT") && StringUtils.isNotEmpty(user.getDepartId())){
				user.setUserType(UserType.TEACHER.toString());
				Dept dept = deptMap.get(user.getDepartId());
				if(dept!=null){
					String[] names = dept.getFullName().split("/");
					user.setDepartName(names[0]);
				}else{
					logger.error("部门有错误数据，找不到部门信息，部门ID：{}",user.getDepartId());
				}
			}
		}

		Set<String> majorIdss = new ArraySet<>(java.lang.String.class);
		for (UserWithGroup user : users) {
			if (StringUtils.isNotEmpty(user.getUserType()) && user.getUserType().equals("STUDENT")) {
				majorIdss.add(user.getMajorId());
			}
		}
		if (majorIdss.size() > 0) {
			List<AcademyMajor> cademyss = cpdailyAcademyMajorMapper.selectAcademyMajorsByMajorIds(new ArrayList(majorIdss), tenantId);
			for (UserWithGroup user : users) {
				if (user.getUserType().equals("STUDENT")) {
					for (AcademyMajor major : cademyss) {
						if (major.getMajorsId().equals(user.getMajorId())) {
							user.setDepartName(major.getAcademyName());
						}
					}
				}
			}
		}
	}

	/**
	 * 根据校园号id获取所有校园号管理人员信息
	 *
	 * @param mediaId
	 * @return
	 */
	protected List<MediaManager> getMediaManagerList(String mediaId) {
		List<MediaPersonRelation> mediaPersonRelations = mediaPersonRelationMapper.getMediaManagers(mediaId);

		List<MediaManager> list = new ArrayList<>();
		for (MediaPersonRelation relation : mediaPersonRelations) {
			MediaManager mediaManager = new MediaManager();
			mediaManager.setManageType(relation.getManageType());
			mediaManager.setPersonId(relation.getPersonId());
			if (StringUtils.isNotEmpty(relation.getUserId())) { // 有userId
				CpdailyUser user = cpdailyUserMapper.selectByUserId(relation.getUserId());
				BeanUtils.copyProperties(user, mediaManager);
			} else { // 么有userId
				List<CpdailyUser> userList = cpdailyUserMapper.selectByPersonId(relation.getPersonId());

				Optional<CpdailyUser> optionalUser = userList.stream().filter(user -> user.getTenantId().equals(ThreadLocalUserInfo.getContext().getTenantId())).findFirst();
				if (optionalUser.isPresent()) {
					BeanUtils.copyProperties(optionalUser.get(), mediaManager);
				}else if(!userList.isEmpty()){
					BeanUtils.copyProperties(userList.get(0), mediaManager);
				}
			}
			list.add(mediaManager);
		}

		return list;
	}

	/**
	 * 根据校园号获取拥有者
	 *
	 * @param mediaId
	 * @return
	 */
	public MediaManager getMediaOwner(String mediaId) {
		try {
			MediaPersonRelation mediaPersonRelation = mediaPersonRelationMapper.getMediaOwner(mediaId);
			if (mediaPersonRelation != null) {

				MediaManager mediaManager = new MediaManager();
				mediaManager.setPersonId(mediaPersonRelation.getPersonId());
				mediaManager.setManageType(mediaPersonRelation.getManageType());

				if (StringUtils.isNotEmpty(mediaPersonRelation.getUserId())) {
					CpdailyUser user = cpdailyUserMapper.selectByUserId(mediaPersonRelation.getUserId());
					BeanUtils.copyProperties(user, mediaManager);
				} else {
					List<CpdailyUser> userList = cpdailyUserMapper.selectByPersonId(mediaPersonRelation.getPersonId());
					Optional<CpdailyUser> optionalUser = userList.stream().filter(user -> user.getTenantId().equals(ThreadLocalUserInfo.getContext().getTenantId())).findFirst();
					if (optionalUser.isPresent()) {
						BeanUtils.copyProperties(optionalUser.get(), mediaManager);
					}
				}

				return mediaManager;
			}
		} catch (Exception e) {
			logger.warn("数据异常，没有拥有者", e);
			return null;
		}
		return null;
	}
}
