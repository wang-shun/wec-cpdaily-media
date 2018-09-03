package com.wisedu.wec.media.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.po.MediaManager;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import com.wisedu.wec.media.dal.mybatis.MediaPersonRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.po.CpdailyTenant;
import com.wisedu.wec.media.dal.mybatis.CpdailyTenantMapper;
import com.wisedu.wec.media.biz.service.TenantService;
import com.wisedu.wecloud.commons.model.ResponseResult;

@Service
public class TenantServiceImpl implements TenantService{

	@Autowired
	private CpdailyTenantMapper cpdailyTenantMapper;
	@Autowired
	private MediaPersonRelationMapper mediaPersonRelationMapper;
	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;
	
	@Override
	public ResponseResult<?> getUserRelatedTenantList() {
		String personId = ThreadLocalUserInfo.getContext().getPersonId();
		List<CpdailyTenant> tenantList = cpdailyTenantMapper.getPersonTenantList(personId);

		// 补充是管理员情况时的租户
//		List<String> mediaIds = mediaPersonRelationMapper.getMediaIdsByPersonId(personId);
//		if (mediaIds.size() > 0) {
//			List<CpdailyUser> users = cpdailyUserMapper.selectByUserIds(mediaIds);
//			Set<String> tenantIds = users.stream().map(CpdailyUser::getTenantId).filter(tenantId -> tenantList.stream().noneMatch(tenant -> tenant.getTenantId().equals(tenantId))).collect(Collectors.toSet());
//			List<String> tenantListIds = new ArrayList<>();
//			tenantListIds.addAll(tenantIds);
//			if(!tenantIds.isEmpty()){
//				List<CpdailyTenant> newTenantList = cpdailyTenantMapper.selectNameByIds(tenantListIds);
//
//				if (newTenantList.size() > 0) {
//					tenantList.addAll(newTenantList);
//				}
//			}
//		}

		CpdailyTenant publicTenant = null;
		for (int i = 0; i< tenantList.size(); i++) {
			CpdailyTenant tenant = tenantList.get(i);
			if (tenant.getTenantId().equals("public_tenant")) {
				publicTenant = tenantList.get(i);
				tenantList.remove(i);
				i--;
			}
		}
		if (publicTenant != null) {
			tenantList.add(publicTenant);
		}
		return ResponseResult.success(tenantList);
	}

}
