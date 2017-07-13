package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.List;

public class VrActivityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VrActivityExample() {
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

        public Criteria andAppointmentCntIsNull() {
            addCriterion("appointment_cnt is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntIsNotNull() {
            addCriterion("appointment_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntEqualTo(Integer value) {
            addCriterion("appointment_cnt =", value, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntNotEqualTo(Integer value) {
            addCriterion("appointment_cnt <>", value, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntGreaterThan(Integer value) {
            addCriterion("appointment_cnt >", value, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("appointment_cnt >=", value, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntLessThan(Integer value) {
            addCriterion("appointment_cnt <", value, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntLessThanOrEqualTo(Integer value) {
            addCriterion("appointment_cnt <=", value, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntIn(List<Integer> values) {
            addCriterion("appointment_cnt in", values, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntNotIn(List<Integer> values) {
            addCriterion("appointment_cnt not in", values, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntBetween(Integer value1, Integer value2) {
            addCriterion("appointment_cnt between", value1, value2, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentCntNotBetween(Integer value1, Integer value2) {
            addCriterion("appointment_cnt not between", value1, value2, "appointmentCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntIsNull() {
            addCriterion("appointment_max_cnt is null");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntIsNotNull() {
            addCriterion("appointment_max_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntEqualTo(Integer value) {
            addCriterion("appointment_max_cnt =", value, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntNotEqualTo(Integer value) {
            addCriterion("appointment_max_cnt <>", value, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntGreaterThan(Integer value) {
            addCriterion("appointment_max_cnt >", value, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("appointment_max_cnt >=", value, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntLessThan(Integer value) {
            addCriterion("appointment_max_cnt <", value, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntLessThanOrEqualTo(Integer value) {
            addCriterion("appointment_max_cnt <=", value, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntIn(List<Integer> values) {
            addCriterion("appointment_max_cnt in", values, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntNotIn(List<Integer> values) {
            addCriterion("appointment_max_cnt not in", values, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntBetween(Integer value1, Integer value2) {
            addCriterion("appointment_max_cnt between", value1, value2, "appointmentMaxCnt");
            return (Criteria) this;
        }

        public Criteria andAppointmentMaxCntNotBetween(Integer value1, Integer value2) {
            addCriterion("appointment_max_cnt not between", value1, value2, "appointmentMaxCnt");
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

        public Criteria andAvailableEqualTo(Integer value) {
            addCriterion("available =", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotEqualTo(Integer value) {
            addCriterion("available <>", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThan(Integer value) {
            addCriterion("available >", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableGreaterThanOrEqualTo(Integer value) {
            addCriterion("available >=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThan(Integer value) {
            addCriterion("available <", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableLessThanOrEqualTo(Integer value) {
            addCriterion("available <=", value, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableIn(List<Integer> values) {
            addCriterion("available in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotIn(List<Integer> values) {
            addCriterion("available not in", values, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableBetween(Integer value1, Integer value2) {
            addCriterion("available between", value1, value2, "available");
            return (Criteria) this;
        }

        public Criteria andAvailableNotBetween(Integer value1, Integer value2) {
            addCriterion("available not between", value1, value2, "available");
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