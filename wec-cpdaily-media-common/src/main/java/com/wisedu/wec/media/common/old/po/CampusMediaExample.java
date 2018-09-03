package com.wisedu.wec.media.common.old.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CampusMediaExample {
  protected String orderByClause;

  protected boolean distinct;

  protected List<Criteria> oredCriteria;

  public CampusMediaExample() {
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

    public Criteria andWidIsNull() {
      addCriterion("WID is null");
      return (Criteria) this;
    }

    public Criteria andWidIsNotNull() {
      addCriterion("WID is not null");
      return (Criteria) this;
    }

    public Criteria andWidEqualTo(String value) {
      addCriterion("WID =", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidNotEqualTo(String value) {
      addCriterion("WID <>", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidGreaterThan(String value) {
      addCriterion("WID >", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidGreaterThanOrEqualTo(String value) {
      addCriterion("WID >=", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidLessThan(String value) {
      addCriterion("WID <", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidLessThanOrEqualTo(String value) {
      addCriterion("WID <=", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidLike(String value) {
      addCriterion("WID like", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidNotLike(String value) {
      addCriterion("WID not like", value, "wid");
      return (Criteria) this;
    }

    public Criteria andWidIn(List<String> values) {
      addCriterion("WID in", values, "wid");
      return (Criteria) this;
    }

    public Criteria andWidNotIn(List<String> values) {
      addCriterion("WID not in", values, "wid");
      return (Criteria) this;
    }

    public Criteria andWidBetween(String value1, String value2) {
      addCriterion("WID between", value1, value2, "wid");
      return (Criteria) this;
    }

    public Criteria andWidNotBetween(String value1, String value2) {
      addCriterion("WID not between", value1, value2, "wid");
      return (Criteria) this;
    }

    public Criteria andIconIsNull() {
      addCriterion("ICON is null");
      return (Criteria) this;
    }

    public Criteria andIconIsNotNull() {
      addCriterion("ICON is not null");
      return (Criteria) this;
    }

    public Criteria andIconEqualTo(String value) {
      addCriterion("ICON =", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconNotEqualTo(String value) {
      addCriterion("ICON <>", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconGreaterThan(String value) {
      addCriterion("ICON >", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconGreaterThanOrEqualTo(String value) {
      addCriterion("ICON >=", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconLessThan(String value) {
      addCriterion("ICON <", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconLessThanOrEqualTo(String value) {
      addCriterion("ICON <=", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconLike(String value) {
      addCriterion("ICON like", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconNotLike(String value) {
      addCriterion("ICON not like", value, "icon");
      return (Criteria) this;
    }

    public Criteria andIconIn(List<String> values) {
      addCriterion("ICON in", values, "icon");
      return (Criteria) this;
    }

    public Criteria andIconNotIn(List<String> values) {
      addCriterion("ICON not in", values, "icon");
      return (Criteria) this;
    }

    public Criteria andIconBetween(String value1, String value2) {
      addCriterion("ICON between", value1, value2, "icon");
      return (Criteria) this;
    }

    public Criteria andIconNotBetween(String value1, String value2) {
      addCriterion("ICON not between", value1, value2, "icon");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgIsNull() {
      addCriterion("BACKGROUND_IMG is null");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgIsNotNull() {
      addCriterion("BACKGROUND_IMG is not null");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgEqualTo(String value) {
      addCriterion("BACKGROUND_IMG =", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgNotEqualTo(String value) {
      addCriterion("BACKGROUND_IMG <>", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgGreaterThan(String value) {
      addCriterion("BACKGROUND_IMG >", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgGreaterThanOrEqualTo(String value) {
      addCriterion("BACKGROUND_IMG >=", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgLessThan(String value) {
      addCriterion("BACKGROUND_IMG <", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgLessThanOrEqualTo(String value) {
      addCriterion("BACKGROUND_IMG <=", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgLike(String value) {
      addCriterion("BACKGROUND_IMG like", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgNotLike(String value) {
      addCriterion("BACKGROUND_IMG not like", value, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgIn(List<String> values) {
      addCriterion("BACKGROUND_IMG in", values, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgNotIn(List<String> values) {
      addCriterion("BACKGROUND_IMG not in", values, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgBetween(String value1, String value2) {
      addCriterion("BACKGROUND_IMG between", value1, value2, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andBackgroundImgNotBetween(String value1, String value2) {
      addCriterion("BACKGROUND_IMG not between", value1, value2, "backgroundImg");
      return (Criteria) this;
    }

    public Criteria andNameIsNull() {
      addCriterion("NAME is null");
      return (Criteria) this;
    }

    public Criteria andNameIsNotNull() {
      addCriterion("NAME is not null");
      return (Criteria) this;
    }

    public Criteria andNameEqualTo(String value) {
      addCriterion("NAME =", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotEqualTo(String value) {
      addCriterion("NAME <>", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameGreaterThan(String value) {
      addCriterion("NAME >", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameGreaterThanOrEqualTo(String value) {
      addCriterion("NAME >=", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameLessThan(String value) {
      addCriterion("NAME <", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameLessThanOrEqualTo(String value) {
      addCriterion("NAME <=", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameLike(String value) {
      addCriterion("NAME like", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotLike(String value) {
      addCriterion("NAME not like", value, "name");
      return (Criteria) this;
    }

    public Criteria andNameIn(List<String> values) {
      addCriterion("NAME in", values, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotIn(List<String> values) {
      addCriterion("NAME not in", values, "name");
      return (Criteria) this;
    }

    public Criteria andNameBetween(String value1, String value2) {
      addCriterion("NAME between", value1, value2, "name");
      return (Criteria) this;
    }

    public Criteria andNameNotBetween(String value1, String value2) {
      addCriterion("NAME not between", value1, value2, "name");
      return (Criteria) this;
    }

    public Criteria andTenantIdIsNull() {
      addCriterion("TENANT_ID is null");
      return (Criteria) this;
    }

    public Criteria andTenantIdIsNotNull() {
      addCriterion("TENANT_ID is not null");
      return (Criteria) this;
    }

    public Criteria andTenantIdEqualTo(String value) {
      addCriterion("TENANT_ID =", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotEqualTo(String value) {
      addCriterion("TENANT_ID <>", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdGreaterThan(String value) {
      addCriterion("TENANT_ID >", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdGreaterThanOrEqualTo(String value) {
      addCriterion("TENANT_ID >=", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdLessThan(String value) {
      addCriterion("TENANT_ID <", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdLessThanOrEqualTo(String value) {
      addCriterion("TENANT_ID <=", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdLike(String value) {
      addCriterion("TENANT_ID like", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotLike(String value) {
      addCriterion("TENANT_ID not like", value, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdIn(List<String> values) {
      addCriterion("TENANT_ID in", values, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotIn(List<String> values) {
      addCriterion("TENANT_ID not in", values, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdBetween(String value1, String value2) {
      addCriterion("TENANT_ID between", value1, value2, "tenantId");
      return (Criteria) this;
    }

    public Criteria andTenantIdNotBetween(String value1, String value2) {
      addCriterion("TENANT_ID not between", value1, value2, "tenantId");
      return (Criteria) this;
    }

    public Criteria andMediaTypeIsNull() {
      addCriterion("MEDIA_TYPE is null");
      return (Criteria) this;
    }

    public Criteria andMediaTypeIsNotNull() {
      addCriterion("MEDIA_TYPE is not null");
      return (Criteria) this;
    }

    public Criteria andMediaTypeEqualTo(String value) {
      addCriterion("MEDIA_TYPE =", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeNotEqualTo(String value) {
      addCriterion("MEDIA_TYPE <>", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeGreaterThan(String value) {
      addCriterion("MEDIA_TYPE >", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeGreaterThanOrEqualTo(String value) {
      addCriterion("MEDIA_TYPE >=", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeLessThan(String value) {
      addCriterion("MEDIA_TYPE <", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeLessThanOrEqualTo(String value) {
      addCriterion("MEDIA_TYPE <=", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeLike(String value) {
      addCriterion("MEDIA_TYPE like", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeNotLike(String value) {
      addCriterion("MEDIA_TYPE not like", value, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeIn(List<String> values) {
      addCriterion("MEDIA_TYPE in", values, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeNotIn(List<String> values) {
      addCriterion("MEDIA_TYPE not in", values, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeBetween(String value1, String value2) {
      addCriterion("MEDIA_TYPE between", value1, value2, "mediaType");
      return (Criteria) this;
    }

    public Criteria andMediaTypeNotBetween(String value1, String value2) {
      addCriterion("MEDIA_TYPE not between", value1, value2, "mediaType");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNull() {
      addCriterion("UPDATE_TIME is null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIsNotNull() {
      addCriterion("UPDATE_TIME is not null");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeEqualTo(Date value) {
      addCriterion("UPDATE_TIME =", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotEqualTo(Date value) {
      addCriterion("UPDATE_TIME <>", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThan(Date value) {
      addCriterion("UPDATE_TIME >", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
      addCriterion("UPDATE_TIME >=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThan(Date value) {
      addCriterion("UPDATE_TIME <", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
      addCriterion("UPDATE_TIME <=", value, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeIn(List<Date> values) {
      addCriterion("UPDATE_TIME in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotIn(List<Date> values) {
      addCriterion("UPDATE_TIME not in", values, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeBetween(Date value1, Date value2) {
      addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
      return (Criteria) this;
    }

    public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
      addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIsNull() {
      addCriterion("C_USER_ID is null");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIsNotNull() {
      addCriterion("C_USER_ID is not null");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdEqualTo(String value) {
      addCriterion("C_USER_ID =", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotEqualTo(String value) {
      addCriterion("C_USER_ID <>", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdGreaterThan(String value) {
      addCriterion("C_USER_ID >", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
      addCriterion("C_USER_ID >=", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdLessThan(String value) {
      addCriterion("C_USER_ID <", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
      addCriterion("C_USER_ID <=", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdLike(String value) {
      addCriterion("C_USER_ID like", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotLike(String value) {
      addCriterion("C_USER_ID not like", value, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdIn(List<String> values) {
      addCriterion("C_USER_ID in", values, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotIn(List<String> values) {
      addCriterion("C_USER_ID not in", values, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdBetween(String value1, String value2) {
      addCriterion("C_USER_ID between", value1, value2, "createUserId");
      return (Criteria) this;
    }

    public Criteria andCreateUserIdNotBetween(String value1, String value2) {
      addCriterion("C_USER_ID not between", value1, value2, "createUserId");
      return (Criteria) this;
    }

    public Criteria andSortNoIsNull() {
      addCriterion("SORT_NO is null");
      return (Criteria) this;
    }

    public Criteria andSortNoIsNotNull() {
      addCriterion("SORT_NO is not null");
      return (Criteria) this;
    }

    public Criteria andSortNoEqualTo(Integer value) {
      addCriterion("SORT_NO =", value, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoNotEqualTo(Integer value) {
      addCriterion("SORT_NO <>", value, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoGreaterThan(Integer value) {
      addCriterion("SORT_NO >", value, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoGreaterThanOrEqualTo(Integer value) {
      addCriterion("SORT_NO >=", value, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoLessThan(Integer value) {
      addCriterion("SORT_NO <", value, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoLessThanOrEqualTo(Integer value) {
      addCriterion("SORT_NO <=", value, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoIn(List<Integer> values) {
      addCriterion("SORT_NO in", values, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoNotIn(List<Integer> values) {
      addCriterion("SORT_NO not in", values, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoBetween(Integer value1, Integer value2) {
      addCriterion("SORT_NO between", value1, value2, "sortNo");
      return (Criteria) this;
    }

    public Criteria andSortNoNotBetween(Integer value1, Integer value2) {
      addCriterion("SORT_NO not between", value1, value2, "sortNo");
      return (Criteria) this;
    }

    public Criteria andIsDeleteIsNull() {
      addCriterion("IS_DELETE is null");
      return (Criteria) this;
    }

    public Criteria andIsDeleteIsNotNull() {
      addCriterion("IS_DELETE is not null");
      return (Criteria) this;
    }

    public Criteria andIsDeleteEqualTo(String value) {
      addCriterion("IS_DELETE =", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteNotEqualTo(String value) {
      addCriterion("IS_DELETE <>", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteGreaterThan(String value) {
      addCriterion("IS_DELETE >", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteGreaterThanOrEqualTo(String value) {
      addCriterion("IS_DELETE >=", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteLessThan(String value) {
      addCriterion("IS_DELETE <", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteLessThanOrEqualTo(String value) {
      addCriterion("IS_DELETE <=", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteLike(String value) {
      addCriterion("IS_DELETE like", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteNotLike(String value) {
      addCriterion("IS_DELETE not like", value, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteIn(List<String> values) {
      addCriterion("IS_DELETE in", values, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteNotIn(List<String> values) {
      addCriterion("IS_DELETE not in", values, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteBetween(String value1, String value2) {
      addCriterion("IS_DELETE between", value1, value2, "isDelete");
      return (Criteria) this;
    }

    public Criteria andIsDeleteNotBetween(String value1, String value2) {
      addCriterion("IS_DELETE not between", value1, value2, "isDelete");
      return (Criteria) this;
    }

    public Criteria andStatusIsNull() {
      addCriterion("STATUS is null");
      return (Criteria) this;
    }

    public Criteria andStatusIsNotNull() {
      addCriterion("STATUS is not null");
      return (Criteria) this;
    }

    public Criteria andStatusEqualTo(String value) {
      addCriterion("STATUS =", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotEqualTo(String value) {
      addCriterion("STATUS <>", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThan(String value) {
      addCriterion("STATUS >", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusGreaterThanOrEqualTo(String value) {
      addCriterion("STATUS >=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThan(String value) {
      addCriterion("STATUS <", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLessThanOrEqualTo(String value) {
      addCriterion("STATUS <=", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusLike(String value) {
      addCriterion("STATUS like", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotLike(String value) {
      addCriterion("STATUS not like", value, "status");
      return (Criteria) this;
    }

    public Criteria andStatusIn(List<String> values) {
      addCriterion("STATUS in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotIn(List<String> values) {
      addCriterion("STATUS not in", values, "status");
      return (Criteria) this;
    }

    public Criteria andStatusBetween(String value1, String value2) {
      addCriterion("STATUS between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andStatusNotBetween(String value1, String value2) {
      addCriterion("STATUS not between", value1, value2, "status");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordIsNull() {
      addCriterion("LOGIN_PASSWORD is null");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordIsNotNull() {
      addCriterion("LOGIN_PASSWORD is not null");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD =", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordNotEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD <>", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordGreaterThan(String value) {
      addCriterion("LOGIN_PASSWORD >", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordGreaterThanOrEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD >=", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordLessThan(String value) {
      addCriterion("LOGIN_PASSWORD <", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordLessThanOrEqualTo(String value) {
      addCriterion("LOGIN_PASSWORD <=", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordLike(String value) {
      addCriterion("LOGIN_PASSWORD like", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordNotLike(String value) {
      addCriterion("LOGIN_PASSWORD not like", value, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordIn(List<String> values) {
      addCriterion("LOGIN_PASSWORD in", values, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordNotIn(List<String> values) {
      addCriterion("LOGIN_PASSWORD not in", values, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordBetween(String value1, String value2) {
      addCriterion("LOGIN_PASSWORD between", value1, value2, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andLoginPasswordNotBetween(String value1, String value2) {
      addCriterion("LOGIN_PASSWORD not between", value1, value2, "loginPassword");
      return (Criteria) this;
    }

    public Criteria andEmailIsNull() {
      addCriterion("EMAIL is null");
      return (Criteria) this;
    }

    public Criteria andEmailIsNotNull() {
      addCriterion("EMAIL is not null");
      return (Criteria) this;
    }

    public Criteria andEmailEqualTo(String value) {
      addCriterion("EMAIL =", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailNotEqualTo(String value) {
      addCriterion("EMAIL <>", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailGreaterThan(String value) {
      addCriterion("EMAIL >", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailGreaterThanOrEqualTo(String value) {
      addCriterion("EMAIL >=", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailLessThan(String value) {
      addCriterion("EMAIL <", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailLessThanOrEqualTo(String value) {
      addCriterion("EMAIL <=", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailLike(String value) {
      addCriterion("EMAIL like", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailNotLike(String value) {
      addCriterion("EMAIL not like", value, "email");
      return (Criteria) this;
    }

    public Criteria andEmailIn(List<String> values) {
      addCriterion("EMAIL in", values, "email");
      return (Criteria) this;
    }

    public Criteria andEmailNotIn(List<String> values) {
      addCriterion("EMAIL not in", values, "email");
      return (Criteria) this;
    }

    public Criteria andEmailBetween(String value1, String value2) {
      addCriterion("EMAIL between", value1, value2, "email");
      return (Criteria) this;
    }

    public Criteria andEmailNotBetween(String value1, String value2) {
      addCriterion("EMAIL not between", value1, value2, "email");
      return (Criteria) this;
    }

    public Criteria andIsHideIsNull() {
      addCriterion("IS_HIDE is null");
      return (Criteria) this;
    }

    public Criteria andIsHideIsNotNull() {
      addCriterion("IS_HIDE is not null");
      return (Criteria) this;
    }

    public Criteria andIsHideEqualTo(String value) {
      addCriterion("IS_HIDE =", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideNotEqualTo(String value) {
      addCriterion("IS_HIDE <>", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideGreaterThan(String value) {
      addCriterion("IS_HIDE >", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideGreaterThanOrEqualTo(String value) {
      addCriterion("IS_HIDE >=", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideLessThan(String value) {
      addCriterion("IS_HIDE <", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideLessThanOrEqualTo(String value) {
      addCriterion("IS_HIDE <=", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideLike(String value) {
      addCriterion("IS_HIDE like", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideNotLike(String value) {
      addCriterion("IS_HIDE not like", value, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideIn(List<String> values) {
      addCriterion("IS_HIDE in", values, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideNotIn(List<String> values) {
      addCriterion("IS_HIDE not in", values, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideBetween(String value1, String value2) {
      addCriterion("IS_HIDE between", value1, value2, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsHideNotBetween(String value1, String value2) {
      addCriterion("IS_HIDE not between", value1, value2, "isHide");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatIsNull() {
      addCriterion("IS_ASSOCIATED_WECHAT is null");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatIsNotNull() {
      addCriterion("IS_ASSOCIATED_WECHAT is not null");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatEqualTo(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT =", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatNotEqualTo(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT <>", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatGreaterThan(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT >", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatGreaterThanOrEqualTo(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT >=", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatLessThan(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT <", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatLessThanOrEqualTo(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT <=", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatLike(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT like", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatNotLike(String value) {
      addCriterion("IS_ASSOCIATED_WECHAT not like", value, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatIn(List<String> values) {
      addCriterion("IS_ASSOCIATED_WECHAT in", values, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatNotIn(List<String> values) {
      addCriterion("IS_ASSOCIATED_WECHAT not in", values, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatBetween(String value1, String value2) {
      addCriterion("IS_ASSOCIATED_WECHAT between", value1, value2, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andIsAssociatedWechatNotBetween(String value1, String value2) {
      addCriterion("IS_ASSOCIATED_WECHAT not between", value1, value2, "isAssociatedWechat");
      return (Criteria) this;
    }

    public Criteria andWechatIdIsNull() {
      addCriterion("WECHAT_ID is null");
      return (Criteria) this;
    }

    public Criteria andWechatIdIsNotNull() {
      addCriterion("WECHAT_ID is not null");
      return (Criteria) this;
    }

    public Criteria andWechatIdEqualTo(String value) {
      addCriterion("WECHAT_ID =", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdNotEqualTo(String value) {
      addCriterion("WECHAT_ID <>", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdGreaterThan(String value) {
      addCriterion("WECHAT_ID >", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdGreaterThanOrEqualTo(String value) {
      addCriterion("WECHAT_ID >=", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdLessThan(String value) {
      addCriterion("WECHAT_ID <", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdLessThanOrEqualTo(String value) {
      addCriterion("WECHAT_ID <=", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdLike(String value) {
      addCriterion("WECHAT_ID like", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdNotLike(String value) {
      addCriterion("WECHAT_ID not like", value, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdIn(List<String> values) {
      addCriterion("WECHAT_ID in", values, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdNotIn(List<String> values) {
      addCriterion("WECHAT_ID not in", values, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdBetween(String value1, String value2) {
      addCriterion("WECHAT_ID between", value1, value2, "wechatId");
      return (Criteria) this;
    }

    public Criteria andWechatIdNotBetween(String value1, String value2) {
      addCriterion("WECHAT_ID not between", value1, value2, "wechatId");
      return (Criteria) this;
    }

    public Criteria andAuthStatusIsNull() {
      addCriterion("auth_status is null");
      return (Criteria) this;
    }

    public Criteria andAuthStatusIsNotNull() {
      addCriterion("auth_status is not null");
      return (Criteria) this;
    }

    public Criteria andAuthStatusEqualTo(String value) {
      addCriterion("auth_status =", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusNotEqualTo(String value) {
      addCriterion("auth_status <>", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusGreaterThan(String value) {
      addCriterion("auth_status >", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusGreaterThanOrEqualTo(String value) {
      addCriterion("auth_status >=", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusLessThan(String value) {
      addCriterion("auth_status <", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusLessThanOrEqualTo(String value) {
      addCriterion("auth_status <=", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusLike(String value) {
      addCriterion("auth_status like", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusNotLike(String value) {
      addCriterion("auth_status not like", value, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusIn(List<String> values) {
      addCriterion("auth_status in", values, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusNotIn(List<String> values) {
      addCriterion("auth_status not in", values, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusBetween(String value1, String value2) {
      addCriterion("auth_status between", value1, value2, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthStatusNotBetween(String value1, String value2) {
      addCriterion("auth_status not between", value1, value2, "authStatus");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsIsNull() {
      addCriterion("auth_materials is null");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsIsNotNull() {
      addCriterion("auth_materials is not null");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsEqualTo(String value) {
      addCriterion("auth_materials =", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsNotEqualTo(String value) {
      addCriterion("auth_materials <>", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsGreaterThan(String value) {
      addCriterion("auth_materials >", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsGreaterThanOrEqualTo(String value) {
      addCriterion("auth_materials >=", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsLessThan(String value) {
      addCriterion("auth_materials <", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsLessThanOrEqualTo(String value) {
      addCriterion("auth_materials <=", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsLike(String value) {
      addCriterion("auth_materials like", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsNotLike(String value) {
      addCriterion("auth_materials not like", value, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsIn(List<String> values) {
      addCriterion("auth_materials in", values, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsNotIn(List<String> values) {
      addCriterion("auth_materials not in", values, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsBetween(String value1, String value2) {
      addCriterion("auth_materials between", value1, value2, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andAuthMaterialsNotBetween(String value1, String value2) {
      addCriterion("auth_materials not between", value1, value2, "authMaterials");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousIsNull() {
      addCriterion("is_anonymous is null");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousIsNotNull() {
      addCriterion("is_anonymous is not null");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousEqualTo(Integer value) {
      addCriterion("is_anonymous =", value, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousNotEqualTo(Integer value) {
      addCriterion("is_anonymous <>", value, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousGreaterThan(Integer value) {
      addCriterion("is_anonymous >", value, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousGreaterThanOrEqualTo(Integer value) {
      addCriterion("is_anonymous >=", value, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousLessThan(Integer value) {
      addCriterion("is_anonymous <", value, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousLessThanOrEqualTo(Integer value) {
      addCriterion("is_anonymous <=", value, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousIn(List<Integer> values) {
      addCriterion("is_anonymous in", values, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousNotIn(List<Integer> values) {
      addCriterion("is_anonymous not in", values, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousBetween(Integer value1, Integer value2) {
      addCriterion("is_anonymous between", value1, value2, "isAnonymous");
      return (Criteria) this;
    }

    public Criteria andIsAnonymousNotBetween(Integer value1, Integer value2) {
      addCriterion("is_anonymous not between", value1, value2, "isAnonymous");
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
