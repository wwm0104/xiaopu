package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserVrActivityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserVrActivityExample() {
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

        public Criteria andAppointmentIdIsNull() {
            addCriterion("appointment_id is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdIsNotNull() {
            addCriterion("appointment_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdEqualTo(Integer value) {
            addCriterion("appointment_id =", value, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdNotEqualTo(Integer value) {
            addCriterion("appointment_id <>", value, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdGreaterThan(Integer value) {
            addCriterion("appointment_id >", value, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("appointment_id >=", value, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdLessThan(Integer value) {
            addCriterion("appointment_id <", value, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("appointment_id <=", value, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdIn(List<Integer> values) {
            addCriterion("appointment_id in", values, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdNotIn(List<Integer> values) {
            addCriterion("appointment_id not in", values, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdBetween(Integer value1, Integer value2) {
            addCriterion("appointment_id between", value1, value2, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("appointment_id not between", value1, value2, "appointmentId");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateIsNull() {
            addCriterion("appointment_date is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateIsNotNull() {
            addCriterion("appointment_date is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateEqualTo(String value) {
            addCriterion("appointment_date =", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateNotEqualTo(String value) {
            addCriterion("appointment_date <>", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateGreaterThan(String value) {
            addCriterion("appointment_date >", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateGreaterThanOrEqualTo(String value) {
            addCriterion("appointment_date >=", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateLessThan(String value) {
            addCriterion("appointment_date <", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateLessThanOrEqualTo(String value) {
            addCriterion("appointment_date <=", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateLike(String value) {
            addCriterion("appointment_date like", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateNotLike(String value) {
            addCriterion("appointment_date not like", value, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateIn(List<String> values) {
            addCriterion("appointment_date in", values, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateNotIn(List<String> values) {
            addCriterion("appointment_date not in", values, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateBetween(String value1, String value2) {
            addCriterion("appointment_date between", value1, value2, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentDateNotBetween(String value1, String value2) {
            addCriterion("appointment_date not between", value1, value2, "appointmentDate");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeIsNull() {
            addCriterion("appointment_time is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeIsNotNull() {
            addCriterion("appointment_time is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeEqualTo(String value) {
            addCriterion("appointment_time =", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeNotEqualTo(String value) {
            addCriterion("appointment_time <>", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeGreaterThan(String value) {
            addCriterion("appointment_time >", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeGreaterThanOrEqualTo(String value) {
            addCriterion("appointment_time >=", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeLessThan(String value) {
            addCriterion("appointment_time <", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeLessThanOrEqualTo(String value) {
            addCriterion("appointment_time <=", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeLike(String value) {
            addCriterion("appointment_time like", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeNotLike(String value) {
            addCriterion("appointment_time not like", value, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeIn(List<String> values) {
            addCriterion("appointment_time in", values, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeNotIn(List<String> values) {
            addCriterion("appointment_time not in", values, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeBetween(String value1, String value2) {
            addCriterion("appointment_time between", value1, value2, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentTimeNotBetween(String value1, String value2) {
            addCriterion("appointment_time not between", value1, value2, "appointmentTime");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeIsNull() {
            addCriterion("appointment_code is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeIsNotNull() {
            addCriterion("appointment_code is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeEqualTo(String value) {
            addCriterion("appointment_code =", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeNotEqualTo(String value) {
            addCriterion("appointment_code <>", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeGreaterThan(String value) {
            addCriterion("appointment_code >", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeGreaterThanOrEqualTo(String value) {
            addCriterion("appointment_code >=", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeLessThan(String value) {
            addCriterion("appointment_code <", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeLessThanOrEqualTo(String value) {
            addCriterion("appointment_code <=", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeLike(String value) {
            addCriterion("appointment_code like", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeNotLike(String value) {
            addCriterion("appointment_code not like", value, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeIn(List<String> values) {
            addCriterion("appointment_code in", values, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeNotIn(List<String> values) {
            addCriterion("appointment_code not in", values, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeBetween(String value1, String value2) {
            addCriterion("appointment_code between", value1, value2, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andAppointmentCodeNotBetween(String value1, String value2) {
            addCriterion("appointment_code not between", value1, value2, "appointmentCode");
            return (Criteria) this;
        }

        public Criteria andActivityCntIsNull() {
            addCriterion("activity_cnt is null");
            return (Criteria) this;
        }

        public Criteria andActivityCntIsNotNull() {
            addCriterion("activity_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andActivityCntEqualTo(Integer value) {
            addCriterion("activity_cnt =", value, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntNotEqualTo(Integer value) {
            addCriterion("activity_cnt <>", value, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntGreaterThan(Integer value) {
            addCriterion("activity_cnt >", value, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("activity_cnt >=", value, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntLessThan(Integer value) {
            addCriterion("activity_cnt <", value, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntLessThanOrEqualTo(Integer value) {
            addCriterion("activity_cnt <=", value, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntIn(List<Integer> values) {
            addCriterion("activity_cnt in", values, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntNotIn(List<Integer> values) {
            addCriterion("activity_cnt not in", values, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntBetween(Integer value1, Integer value2) {
            addCriterion("activity_cnt between", value1, value2, "activityCnt");
            return (Criteria) this;
        }

        public Criteria andActivityCntNotBetween(Integer value1, Integer value2) {
            addCriterion("activity_cnt not between", value1, value2, "activityCnt");
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