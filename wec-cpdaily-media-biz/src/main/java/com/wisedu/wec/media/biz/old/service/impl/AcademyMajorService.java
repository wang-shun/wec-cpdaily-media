package com.wisedu.wec.media.biz.old.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.wisedu.wec.media.common.old.po.AcademyMajor;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.vo.Dept;
import com.wisedu.wec.media.dal.mybatis.AcademyMajorMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;

@Service
public class AcademyMajorService {

  @Autowired
  private AcademyMajorMapper mapper;

  @Autowired
  private CpdailyUserMapper userMapper;


  /**
   * 创建学生组织树
   *
   * @param tenantId
   */
  public List<Dept> createDeptStruct(String tenantId) {

    List<Dept> result = new ArrayList<>();

    List<AcademyMajor> majors = mapper.selectByTenantId(tenantId);

    Multimap<String, AcademyMajor> multiMajorMap = HashMultimap.create();

    for (AcademyMajor m : majors) {
      multiMajorMap.put(m.getAcademyId(), m);
    }

//    Multiset<String> academyMultiSet = academyStatistic(tenantId);
//    Multiset<String> majorMultiSet = majorStatistic(tenantId);

    for (String academyId : multiMajorMap.keySet()) {

      Dept academy = new Dept();
      academy.setId(academyId);
      academy.setPid("student");
//      academy.setQuantity(academyMultiSet.count(academyId));

      List<Dept> children = new ArrayList<>();
      for (AcademyMajor m : multiMajorMap.get(academyId)) {

        if (StringUtils.isEmpty(m.getAcademyName()) || StringUtils.isEmpty(m.getMajorName())) {
          continue;
        }

        // 院系名称
        if (StringUtils.isEmpty(academy.getTitle())) {
          academy.setTitle(m.getAcademyName());
          academy.setPath("学生STUDENT/" + m.getAcademyName());
        }

        if (children.stream().anyMatch(dept -> dept.getId().equals(m.getMajorId()))) {
          continue;
        }

        Dept sd = new Dept();
        sd.setId(m.getMajorId());
        sd.setPid(m.getAcademyId());
        sd.setTitle(m.getMajorName());
//        sd.setQuantity(majorMultiSet.count(m.getMajorId()));
        sd.setPath("学生STUDENT/" + m.getAcademyName() + "/" + m.getMajorName());
        children.add(sd);
      }

      academy.setChildren(children);

      result.add(academy);

    }

    return result;

  }

  /**
   * 按学院统计人数
   *
   * @param tenantId
   * @return
   */
  private Multiset<String> academyStatistic(String tenantId) {

    List<CpdailyUser> students = userMapper.selectAcademyAndMajors(tenantId);

    Multiset<String> academyMultiSet = HashMultiset.create();

    for (CpdailyUser student : students) {
      if (student != null && StringUtils.isNotEmpty(student.getAcademyId())) {
          academyMultiSet.add(student.getAcademyId());
      }
    }

    return academyMultiSet;

  }

  /**
   * 按专业统计人数
   *
   * @param tenantId
   * @return
   */
  private Multiset<String> majorStatistic(String tenantId) {

    List<CpdailyUser> students = userMapper.selectAcademyAndMajors(tenantId);

    Multiset<String> majorMultiSet = HashMultiset.create();

    for (CpdailyUser student : students) {
        if (StringUtils.isNotEmpty(student.getMajorId())) {
            majorMultiSet.add(student.getMajorId());
        }
    }

    return majorMultiSet;

  }

}
