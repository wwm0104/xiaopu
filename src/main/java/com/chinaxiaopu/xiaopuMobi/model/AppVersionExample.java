package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.List;

public class AppVersionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppVersionExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("app_id like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("app_id not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andVerisonIsNull() {
            addCriterion("verison is null");
            return (Criteria) this;
        }

        public Criteria andVerisonIsNotNull() {
            addCriterion("verison is not null");
            return (Criteria) this;
        }

        public Criteria andVerisonEqualTo(String value) {
            addCriterion("verison =", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonNotEqualTo(String value) {
            addCriterion("verison <>", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonGreaterThan(String value) {
            addCriterion("verison >", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonGreaterThanOrEqualTo(String value) {
            addCriterion("verison >=", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonLessThan(String value) {
            addCriterion("verison <", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonLessThanOrEqualTo(String value) {
            addCriterion("verison <=", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonLike(String value) {
            addCriterion("verison like", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonNotLike(String value) {
            addCriterion("verison not like", value, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonIn(List<String> values) {
            addCriterion("verison in", values, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonNotIn(List<String> values) {
            addCriterion("verison not in", values, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonBetween(String value1, String value2) {
            addCriterion("verison between", value1, value2, "verison");
            return (Criteria) this;
        }

        public Criteria andVerisonNotBetween(String value1, String value2) {
            addCriterion("verison not between", value1, value2, "verison");
            return (Criteria) this;
        }

        public Criteria andUseragentIsNull() {
            addCriterion("useragent is null");
            return (Criteria) this;
        }

        public Criteria andUseragentIsNotNull() {
            addCriterion("useragent is not null");
            return (Criteria) this;
        }

        public Criteria andUseragentEqualTo(String value) {
            addCriterion("useragent =", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentNotEqualTo(String value) {
            addCriterion("useragent <>", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentGreaterThan(String value) {
            addCriterion("useragent >", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentGreaterThanOrEqualTo(String value) {
            addCriterion("useragent >=", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentLessThan(String value) {
            addCriterion("useragent <", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentLessThanOrEqualTo(String value) {
            addCriterion("useragent <=", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentLike(String value) {
            addCriterion("useragent like", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentNotLike(String value) {
            addCriterion("useragent not like", value, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentIn(List<String> values) {
            addCriterion("useragent in", values, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentNotIn(List<String> values) {
            addCriterion("useragent not in", values, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentBetween(String value1, String value2) {
            addCriterion("useragent between", value1, value2, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentNotBetween(String value1, String value2) {
            addCriterion("useragent not between", value1, value2, "useragent");
            return (Criteria) this;
        }

        public Criteria andUseragentNameIsNull() {
            addCriterion("useragent_name is null");
            return (Criteria) this;
        }

        public Criteria andUseragentNameIsNotNull() {
            addCriterion("useragent_name is not null");
            return (Criteria) this;
        }

        public Criteria andUseragentNameEqualTo(String value) {
            addCriterion("useragent_name =", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameNotEqualTo(String value) {
            addCriterion("useragent_name <>", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameGreaterThan(String value) {
            addCriterion("useragent_name >", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameGreaterThanOrEqualTo(String value) {
            addCriterion("useragent_name >=", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameLessThan(String value) {
            addCriterion("useragent_name <", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameLessThanOrEqualTo(String value) {
            addCriterion("useragent_name <=", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameLike(String value) {
            addCriterion("useragent_name like", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameNotLike(String value) {
            addCriterion("useragent_name not like", value, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameIn(List<String> values) {
            addCriterion("useragent_name in", values, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameNotIn(List<String> values) {
            addCriterion("useragent_name not in", values, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameBetween(String value1, String value2) {
            addCriterion("useragent_name between", value1, value2, "useragentName");
            return (Criteria) this;
        }

        public Criteria andUseragentNameNotBetween(String value1, String value2) {
            addCriterion("useragent_name not between", value1, value2, "useragentName");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNull() {
            addCriterion("download_url is null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNotNull() {
            addCriterion("download_url is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlEqualTo(String value) {
            addCriterion("download_url =", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotEqualTo(String value) {
            addCriterion("download_url <>", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThan(String value) {
            addCriterion("download_url >", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThanOrEqualTo(String value) {
            addCriterion("download_url >=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThan(String value) {
            addCriterion("download_url <", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThanOrEqualTo(String value) {
            addCriterion("download_url <=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLike(String value) {
            addCriterion("download_url like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotLike(String value) {
            addCriterion("download_url not like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIn(List<String> values) {
            addCriterion("download_url in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotIn(List<String> values) {
            addCriterion("download_url not in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlBetween(String value1, String value2) {
            addCriterion("download_url between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotBetween(String value1, String value2) {
            addCriterion("download_url not between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andIsUpdateIsNull() {
            addCriterion("is_update is null");
            return (Criteria) this;
        }

        public Criteria andIsUpdateIsNotNull() {
            addCriterion("is_update is not null");
            return (Criteria) this;
        }

        public Criteria andIsUpdateEqualTo(Byte value) {
            addCriterion("is_update =", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateNotEqualTo(Byte value) {
            addCriterion("is_update <>", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateGreaterThan(Byte value) {
            addCriterion("is_update >", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_update >=", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateLessThan(Byte value) {
            addCriterion("is_update <", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateLessThanOrEqualTo(Byte value) {
            addCriterion("is_update <=", value, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateIn(List<Byte> values) {
            addCriterion("is_update in", values, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateNotIn(List<Byte> values) {
            addCriterion("is_update not in", values, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateBetween(Byte value1, Byte value2) {
            addCriterion("is_update between", value1, value2, "isUpdate");
            return (Criteria) this;
        }

        public Criteria andIsUpdateNotBetween(Byte value1, Byte value2) {
            addCriterion("is_update not between", value1, value2, "isUpdate");
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
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
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
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}