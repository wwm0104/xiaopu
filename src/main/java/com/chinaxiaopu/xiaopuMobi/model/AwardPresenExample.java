package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AwardPresenExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AwardPresenExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNull() {
            addCriterion("real_name is null");
            return (Criteria) this;
        }

        public Criteria andRealNameIsNotNull() {
            addCriterion("real_name is not null");
            return (Criteria) this;
        }

        public Criteria andRealNameEqualTo(String value) {
            addCriterion("real_name =", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotEqualTo(String value) {
            addCriterion("real_name <>", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThan(String value) {
            addCriterion("real_name >", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameGreaterThanOrEqualTo(String value) {
            addCriterion("real_name >=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThan(String value) {
            addCriterion("real_name <", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLessThanOrEqualTo(String value) {
            addCriterion("real_name <=", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameLike(String value) {
            addCriterion("real_name like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotLike(String value) {
            addCriterion("real_name not like", value, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameIn(List<String> values) {
            addCriterion("real_name in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotIn(List<String> values) {
            addCriterion("real_name not in", values, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameBetween(String value1, String value2) {
            addCriterion("real_name between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andRealNameNotBetween(String value1, String value2) {
            addCriterion("real_name not between", value1, value2, "realName");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(Long value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(Long value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(Long value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(Long value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(Long value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(Long value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<Long> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<Long> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(Long value1, Long value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(Long value1, Long value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andOfficialNameIsNull() {
            addCriterion("official_name is null");
            return (Criteria) this;
        }

        public Criteria andOfficialNameIsNotNull() {
            addCriterion("official_name is not null");
            return (Criteria) this;
        }

        public Criteria andOfficialNameEqualTo(String value) {
            addCriterion("official_name =", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameNotEqualTo(String value) {
            addCriterion("official_name <>", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameGreaterThan(String value) {
            addCriterion("official_name >", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameGreaterThanOrEqualTo(String value) {
            addCriterion("official_name >=", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameLessThan(String value) {
            addCriterion("official_name <", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameLessThanOrEqualTo(String value) {
            addCriterion("official_name <=", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameLike(String value) {
            addCriterion("official_name like", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameNotLike(String value) {
            addCriterion("official_name not like", value, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameIn(List<String> values) {
            addCriterion("official_name in", values, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameNotIn(List<String> values) {
            addCriterion("official_name not in", values, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameBetween(String value1, String value2) {
            addCriterion("official_name between", value1, value2, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialNameNotBetween(String value1, String value2) {
            addCriterion("official_name not between", value1, value2, "officialName");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileIsNull() {
            addCriterion("official_mobile is null");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileIsNotNull() {
            addCriterion("official_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileEqualTo(Long value) {
            addCriterion("official_mobile =", value, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileNotEqualTo(Long value) {
            addCriterion("official_mobile <>", value, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileGreaterThan(Long value) {
            addCriterion("official_mobile >", value, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileGreaterThanOrEqualTo(Long value) {
            addCriterion("official_mobile >=", value, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileLessThan(Long value) {
            addCriterion("official_mobile <", value, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileLessThanOrEqualTo(Long value) {
            addCriterion("official_mobile <=", value, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileIn(List<Long> values) {
            addCriterion("official_mobile in", values, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileNotIn(List<Long> values) {
            addCriterion("official_mobile not in", values, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileBetween(Long value1, Long value2) {
            addCriterion("official_mobile between", value1, value2, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andOfficialMobileNotBetween(Long value1, Long value2) {
            addCriterion("official_mobile not between", value1, value2, "officialMobile");
            return (Criteria) this;
        }

        public Criteria andAwardCntIsNull() {
            addCriterion("award_cnt is null");
            return (Criteria) this;
        }

        public Criteria andAwardCntIsNotNull() {
            addCriterion("award_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andAwardCntEqualTo(Integer value) {
            addCriterion("award_cnt =", value, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntNotEqualTo(Integer value) {
            addCriterion("award_cnt <>", value, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntGreaterThan(Integer value) {
            addCriterion("award_cnt >", value, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_cnt >=", value, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntLessThan(Integer value) {
            addCriterion("award_cnt <", value, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntLessThanOrEqualTo(Integer value) {
            addCriterion("award_cnt <=", value, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntIn(List<Integer> values) {
            addCriterion("award_cnt in", values, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntNotIn(List<Integer> values) {
            addCriterion("award_cnt not in", values, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntBetween(Integer value1, Integer value2) {
            addCriterion("award_cnt between", value1, value2, "awardCnt");
            return (Criteria) this;
        }

        public Criteria andAwardCntNotBetween(Integer value1, Integer value2) {
            addCriterion("award_cnt not between", value1, value2, "awardCnt");
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

        public Criteria andAvailableIsNull() {
            addCriterion("available is null");
            return (Criteria) this;
        }

        public Criteria andAvailableIsNotNull() {
            addCriterion("available is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableEqualTo(Byte value) {
            addCriterion("available =", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotEqualTo(Byte value) {
            addCriterion("available <>", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThan(Byte value) {
            addCriterion("available >", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThanOrEqualTo(Byte value) {
            addCriterion("available >=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThan(Byte value) {
            addCriterion("available <", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThanOrEqualTo(Byte value) {
            addCriterion("available <=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableIn(List<Byte> values) {
            addCriterion("available in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotIn(List<Byte> values) {
            addCriterion("available not in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableBetween(Byte value1, Byte value2) {
            addCriterion("available between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotBetween(Byte value1, Byte value2) {
            addCriterion("available not between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
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