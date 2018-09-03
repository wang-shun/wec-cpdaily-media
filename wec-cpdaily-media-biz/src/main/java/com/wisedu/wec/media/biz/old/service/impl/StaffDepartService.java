package com.wisedu.wec.media.biz.old.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.wisedu.wec.media.common.old.vo.Dept;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import com.wisedu.wec.media.dal.mybatis.StaffDepartMapper;

@Service
public class StaffDepartService {

  @Autowired
  private StaffDepartMapper mapper;

  @Autowired
  private CpdailyUserMapper userMapper;

  /**
   * 创建老师用户组
   *
   * @param tenantId
   * @return
   */
  public List<Dept> createDeptStruct(String tenantId) {

    List<Dept> result = new ArrayList<>();

    List<Dept> depts = mapper.selectByTenantIdConvert2Dept(tenantId);

    // List<Dept> defaultDepts = new ArrayList<>();

    for (Dept dept : depts) {
      if (StringUtils.equals(dept.getId(), dept.getPid())) {
        dept.setPid("teacher");
      }
    }

    result = buildByRecursive(depts);

    return result;

  }

  private List<Dept> buildByRecursive(List<Dept> departs) {
    List<Dept> trees = new ArrayList<>();
    for (Dept depart : departs) {
      if (StringUtils.equals("teacher", depart.getPid())) {
        depart.setPath("老师TEACHER/" + depart.getTitle());
        trees.add(findChildren(depart, departs));
      }
    }
    return trees;
  }

  private Dept findChildren(Dept treeNode, List<Dept> treeNodes) {

    for (Dept dept : treeNodes) {
      if (StringUtils.equals(treeNode.getId(), dept.getPid())) {
        dept.setPath(treeNode.getPath() + "/" + dept.getTitle());
        treeNode.getChildren().add(findChildren(dept, treeNodes));
      }
    }

    return treeNode;
  }

}
