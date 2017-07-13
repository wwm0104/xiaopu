package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PkPrizeTakeLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PkPrizeTakeLogExample() {
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

        public Criteria andAwardUserIdIsNull() {
            addCriterion("award_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdIsNotNull() {
            addCriterion("award_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdEqualTo(Integer value) {
            addCriterion("award_user_id =", value, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdNotEqualTo(Integer value) {
            addCriterion("award_user_id <>", value, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdGreaterThan(Integer value) {
            addCriterion("award_user_id >", value, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("award_user_id >=", value, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdLessThan(Integer value) {
            addCriterion("award_user_id <", value, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("award_user_id <=", value, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdIn(List<Integer> values) {
            addCriterion("award_user_id in", values, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdNotIn(List<Integer> values) {
            addCriterion("award_user_id not in", values, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdBetween(Integer value1, Integer value2) {
            addCriterion("award_user_id between", value1, value2, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andAwardUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("award_user_id not between", value1, value2, "awardUserId");
            return (Criteria) this;
        }

        public Criteria andPkIdIsNull() {
            addCriterion("pk_id is null");
            return (Criteria) this;
        }

        public Criteria andPkIdIsNotNull() {
            addCriterion("pk_id is not null");
            return (Criteria) this;
        }

        public Criteria andPkIdEqualTo(Integer value) {
            addCriterion("pk_id =", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdNotEqualTo(Integer value) {
            addCriterion("pk_id <>", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdGreaterThan(Integer value) {
            addCriterion("pk_id >", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pk_id >=", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdLessThan(Integer value) {
            addCriterion("pk_id <", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdLessThanOrEqualTo(Integer value) {
            addCriterion("pk_id <=", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdIn(List<Integer> values) {
            addCriterion("pk_id in", values, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdNotIn(List<Integer> values) {
            addCriterion("pk_id not in", values, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdBetween(Integer value1, Integer value2) {
            addCriterion("pk_id between", value1, value2, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pk_id not between", value1, value2, "pkId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdIsNull() {
            addCriterion("reward_user_id is null");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdIsNotNull() {
            addCriterion("reward_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdEqualTo(Integer value) {
            addCriterion("reward_user_id =", value, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdNotEqualTo(Integer value) {
            addCriterion("reward_user_id <>", value, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdGreaterThan(Integer value) {
            addCriterion("reward_user_id >", value, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("reward_user_id >=", value, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdLessThan(Integer value) {
            addCriterion("reward_user_id <", value, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("reward_user_id <=", value, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdIn(List<Integer> values) {
            addCriterion("reward_user_id in", values, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdNotIn(List<Integer> values) {
            addCriterion("reward_user_id not in", values, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdBetween(Integer value1, Integer value2) {
            addCriterion("reward_user_id between", value1, value2, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andRewardUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("reward_user_id not between", value1, value2, "rewardUserId");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIsNull() {
            addCriterion("take_time is null");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIsNotNull() {
            addCriterion("take_time is not null");
            return (Criteria) this;
        }

        public Criteria andTakeTimeEqualTo(Date value) {
            addCriterion("take_time =", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotEqualTo(Date value) {
            addCriterion("take_time <>", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeGreaterThan(Date value) {
            addCriterion("take_time >", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("take_time >=", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeLessThan(Date value) {
            addCriterion("take_time <", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeLessThanOrEqualTo(Date value) {
            addCriterion("take_time <=", value, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeIn(List<Date> values) {
            addCriterion("take_time in", values, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotIn(List<Date> values) {
            addCriterion("take_time not in", values, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeBetween(Date value1, Date value2) {
            addCriterion("take_time between", value1, value2, "takeTime");
            return (Criteria) this;
        }

        public Criteria andTakeTimeNotBetween(Date value1, Date value2) {
            addCriterion("take_time not between", value1, value2, "takeTime");
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