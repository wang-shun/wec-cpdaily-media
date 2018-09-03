package com.wisedu.wec.media.common.old.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserGroupExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public UserGroupExample() {
    oredCriteria = new ArrayList<Criteria>();
  }

  public void setOrderByClause(String orderByClause) {
    this.orderByClause = orderByClause;
  }

  public String getOrderByClause() {
    return orderByClause;
  }

  public void setDistinct(boolean distinct) {
    this.distinct = distinct;
  }

  public boolean isDistinct() {
    return distinct;
  }

  public List<Criteria> getOredCriteria() {
    return oredCriteria;
  }

  public void or(Criteria criteria) {
    oredCriteria.add(criteria);
  }

  public Criteria or() {
    Criteria criteria = createCriteriaInternal();
    oredCriteria.add(criteria);
    return criteria;
  }

  public Criteria createCriteria() {
    Criteria criteria = createCriteriaInternal();
    if (oredCriteria.size() == 0) {
      oredCriteria.add(criteria);
    }
    return criteria;
  }

  protected Criteria createCriteriaInternal() {
    Criteria criteria = new Criteria();
    return criteria;
  }

  public void clear() {
    oredCriteria.clear();
    orderByClause = null;
    distinct = false;
  }

  protected abstract static class GeneratedCriteria {
    protected List<Criterion> criteria;

    protected GeneratedCriteria() {
      super();
      criteria = new ArrayList<Criterion>();
    }

    public boolean isValid() {
      return criteria.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
      return criteria;
    }

    public List<Criterion> getCriteria() {
      return criteria;
    }

    protected void addCriterion(String condition) {
      if (condition == null) {
        throw new RuntimeException("Value for condition cannot be null");
      }
      criteria.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
      if (value == null) {
        throw new RuntimeException("Value for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value));
    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
      if (value1 == null || value2 == null) {
        throw new RuntimeException("Between values for " + property + " cannot be null");
      }
      criteria.add(new Criterion(condition, value1, value2));
    }

    public Criteria andGroupIdIsNull() {
      addCriterion("group_id is null");
      return (Criteria) this;
    }

    public Criteria andGroupIdIsNotNull() {
      addCriterion("group_id is not null");
      return (Criteria) this;
    }

    public Criteria andGroupIdEqualTo(String value) {
      addCriterion("group_id =", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdNotEqualTo(String value) {
      addCriterion("group_id <>", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdGreaterThan(String value) {
      addCriterion("group_id >", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdGreaterThanOrEqualTo(String value) {
      addCriterion("group_id >=", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdLessThan(String value) {
      addCriterion("group_id <", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdLessThanOrEqualTo(String value) {
      addCriterion("group_id <=", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdLike(String value) {
      addCriterion("group_id like", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdNotLike(String value) {
      addCriterion("group_id not like", value, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdIn(List<String> values) {
      addCriterion("group_id in", values, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdNotIn(List<String> values) {
      addCriterion("group_id not in", values, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdBetween(String value1, String value2) {
      addCriterion("group_id between", value1, value2, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupIdNotBetween(String value1, String value2) {
      addCriterion("group_id not between", value1, value2, "groupId");
      return (Criteria) this;
    }

    public Criteria andGroupNameIsNull() {
      addCriterion("group_name is null");
      return (Criteria) this;
    }

    public Criteria andGroupNameIsNotNull() {
      addCriterion("group_name is not null");
      return (Criteria) this;
    }

    public Criteria andGroupNameEqualTo(String value) {
      addCriterion("group_name =", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameNotEqualTo(String value) {
      addCriterion("group_name <>", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameGreaterThan(String value) {
      addCriterion("group_name >", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
      addCriterion("group_name >=", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameLessThan(String value) {
      addCriterion("group_name <", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameLessThanOrEqualTo(String value) {
      addCriterion("group_name <=", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameLike(String value) {
      addCriterion("group_name like", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameNotLike(String value) {
      addCriterion("group_name not like", value, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameIn(List<String> values) {
      addCriterion("group_name in", values, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameNotIn(List<String> values) {
      addCriterion("group_name not in", values, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameBetween(String value1, String value2) {
      addCriterion("group_name between", value1, value2, "groupName");
      return (Criteria) this;
    }

    public Criteria andGroupNameNotBetween(String value1, String value2) {
      addCriterion("group_name not between", value1, value2, "groupName");
      return (Criteria) this;
    }

    public Criteria andMediaIdIsNull() {
      addCriterion("media_id is null");
      return (Criteria) this;
    }

    public Criteria andMediaIdIsNotNull() {
      addCriterion("media_id is not null");
      return (Criteria) this;
    }

    public Criteria andMediaIdEqualTo(String value) {
      addCriterion("media_id =", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdNotEqualTo(String value) {
      addCriterion("media_id <>", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdGreaterThan(String value) {
      addCriterion("media_id >", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdGreaterThanOrEqualTo(String value) {
      addCriterion("media_id >=", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdLessThan(String value) {
      addCriterion("media_id <", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdLessThanOrEqualTo(String value) {
      addCriterion("media_id <=", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdLike(String value) {
      addCriterion("media_id like", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdNotLike(String value) {
      addCriterion("media_id not like", value, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdIn(List<String> values) {
      addCriterion("media_id in", values, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdNotIn(List<String> values) {
      addCriterion("media_id not in", values, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdBetween(String value1, String value2) {
      addCriterion("media_id between", value1, value2, "mediaId");
      return (Criteria) this;
    }

    public Criteria andMediaIdNotBetween(String value1, String value2) {
      addCriterion("media_id not between", value1, value2, "mediaId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdIsNull() {
      addCriterion("owner_id is null");
      return (Criteria) this;
    }

    public Criteria andOwnerIdIsNotNull() {
      addCriterion("owner_id is not null");
      return (Criteria) this;
    }

    public Criteria andOwnerIdEqualTo(String value) {
      addCriterion("owner_id =", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdNotEqualTo(String value) {
      addCriterion("owner_id <>", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdGreaterThan(String value) {
      addCriterion("owner_id >", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdGreaterThanOrEqualTo(String value) {
      addCriterion("owner_id >=", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdLessThan(String value) {
      addCriterion("owner_id <", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdLessThanOrEqualTo(String value) {
      addCriterion("owner_id <=", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdLike(String value) {
      addCriterion("owner_id like", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdNotLike(String value) {
      addCriterion("owner_id not like", value, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdIn(List<String> values) {
      addCriterion("owner_id in", values, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdNotIn(List<String> values) {
      addCriterion("owner_id not in", values, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdBetween(String value1, String value2) {
      addCriterion("owner_id between", value1, value2, "ownerId");
      return (Criteria) this;
    }

    public Criteria andOwnerIdNotBetween(String value1, String value2) {
      addCriterion("owner_id not between", value1, value2, "ownerId");
      return (Criteria) this;
    }

    public Criteria andTenantIdIsNull() {
      addCriterion("tenant_id is null");
      return (Criteria) this;
    }

    public Criteria andTenantIdIsNotNull() {
      addCriterion("tenant_id is not null");
      return (Criteria) this;
    }

    public Criteria andTenantIdEqualTo(String value) {
      addCriterion("tenant_id =", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotEqualTo(String value) {
      addCriterion("tenant_id <>", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdGreaterThan(String value) {
      addCriterion("tenant_id >", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdGreaterThanOrEqualTo(String value) {
      addCriterion("tenant_id >=", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdLessThan(String value) {
      addCriterion("tenant_id <", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdLessThanOrEqualTo(String value) {
      addCriterion("tenant_id <=", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdLike(String value) {
      addCriterion("tenant_id like", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotLike(String value) {
      addCriterion("tenant_id not like", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdIn(List<String> values) {
      addCriterion("tenant_id in", values, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotIn(List<String> values) {
      addCriterion("tenant_id not in", values, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdBetween(String value1, String value2) {
      addCriterion("tenant_id between", value1, value2, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotBetween(String value1, String value2) {
      addCriterion("tenant_id not between", value1, value2, "tenantId");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNull() {
      addCriterion("create_time is null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIsNotNull() {
      addCriterion("create_time is not null");
      return (Criteria) this;
    }

    public Criteria andCreateTimeEqualTo(Date value) {
      addCriterion("create_time =", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotEqualTo(Date value) {
      addCriterion("create_time <>", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThan(Date value) {
      addCriterion("create_time >", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("create_time >=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThan(Date value) {
      addCriterion("create_time <", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
      addCriterion("create_time <=", value, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeIn(List<Date> values) {
      addCriterion("create_time in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotIn(List<Date> values) {
      addCriterion("create_time not in", values, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeBetween(Date value1, Date value2) {
      addCriterion("create_time between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
      addCriterion("create_time not between", value1, value2, "createTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNull() {
      addCriterion("update_time is null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNotNull() {
      addCriterion("update_time is not null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeEqualTo(Date value) {
      addCriterion("update_time =", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotEqualTo(Date value) {
      addCriterion("update_time <>", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThan(Date value) {
      addCriterion("update_time >", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("update_time >=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThan(Date value) {
      addCriterion("update_time <", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
      addCriterion("update_time <=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIn(List<Date> values) {
      addCriterion("update_time in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotIn(List<Date> values) {
      addCriterion("update_time not in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeBetween(Date value1, Date value2) {
      addCriterion("update_time between", value1, value2, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
      addCriterion("update_time not between", value1, value2, "updateTime");
      return (Criteria) this;
    }
  }

  public static class Criteria extends GeneratedCriteria {

    protected Criteria() {
      super();
    }
  }

  public static class Criterion {
    private String condition;

    private Object value;

    private Object secondValue;

    private boolean noValue;

    private boolean singleValue;

    private boolean betweenValue;

    private boolean listValue;

    private String typeHandler;

    public String getCondition() {
      return condition;
    }

    public Object getValue() {
      return value;
    }

    public Object getSecondValue() {
      return secondValue;
    }

    public boolean isNoValue() {
      return noValue;
    }

    public boolean isSingleValue() {
      return singleValue;
    }

    public boolean isBetweenValue() {
      return betweenValue;
    }

    public boolean isListValue() {
      return listValue;
    }

    public String getTypeHandler() {
      return typeHandler;
    }

    protected Criterion(String condition) {
      super();
      this.condition = condition;
      typeHandler = null;
      noValue = true;
    }

    protected Criterion(String condition, Object value, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.typeHandler = typeHandler;
      if (value instanceof List<?>) {
        listValue = true;
      } else {
        singleValue = true;
      }
    }

    protected Criterion(String condition, Object value) {
      this(condition, value, null);
    }

    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
      super();
      this.condition = condition;
      this.value = value;
      this.secondValue = secondValue;
      this.typeHandler = typeHandler;
      betweenValue = true;
    }

    protected Criterion(String condition, Object value, Object secondValue) {
      this(condition, value, secondValue, null);
    }
  }
}
