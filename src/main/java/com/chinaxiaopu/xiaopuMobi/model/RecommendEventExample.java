package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecommendEventExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RecommendEventExample() {
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

        public Criteria andEventIdIsNull() {
            addCriterion("event_id is null");
            return (Criteria) this;
        }

        public Criteria andEventIdIsNotNull() {
            addCriterion("event_id is not null");
            return (Criteria) this;
        }

        public Criteria andEventIdEqualTo(Integer value) {
            addCriterion("event_id =", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotEqualTo(Integer value) {
            addCriterion("event_id <>", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdGreaterThan(Integer value) {
            addCriterion("event_id >", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("event_id >=", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdLessThan(Integer value) {
            addCriterion("event_id <", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdLessThanOrEqualTo(Integer value) {
            addCriterion("event_id <=", value, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdIn(List<Integer> values) {
            addCriterion("event_id in", values, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotIn(List<Integer> values) {
            addCriterion("event_id not in", values, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdBetween(Integer value1, Integer value2) {
            addCriterion("event_id between", value1, value2, "eventId");
            return (Criteria) this;
        }

        public Criteria andEventIdNotBetween(Integer value1, Integer value2) {
            addCriterion("event_id not between", value1, value2, "eventId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdIsNull() {
            addCriterion("organize_id is null");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdIsNotNull() {
            addCriterion("organize_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdEqualTo(Integer value) {
            addCriterion("organize_id =", value, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdNotEqualTo(Integer value) {
            addCriterion("organize_id <>", value, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdGreaterThan(Integer value) {
            addCriterion("organize_id >", value, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("organize_id >=", value, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdLessThan(Integer value) {
            addCriterion("organize_id <", value, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdLessThanOrEqualTo(Integer value) {
            addCriterion("organize_id <=", value, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdIn(List<Integer> values) {
            addCriterion("organize_id in", values, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdNotIn(List<Integer> values) {
            addCriterion("organize_id not in", values, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdBetween(Integer value1, Integer value2) {
            addCriterion("organize_id between", value1, value2, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("organize_id not between", value1, value2, "organizeId");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameIsNull() {
            addCriterion("organize_name is null");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameIsNotNull() {
            addCriterion("organize_name is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameEqualTo(String value) {
            addCriterion("organize_name =", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameNotEqualTo(String value) {
            addCriterion("organize_name <>", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameGreaterThan(String value) {
            addCriterion("organize_name >", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameGreaterThanOrEqualTo(String value) {
            addCriterion("organize_name >=", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameLessThan(String value) {
            addCriterion("organize_name <", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameLessThanOrEqualTo(String value) {
            addCriterion("organize_name <=", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameLike(String value) {
            addCriterion("organize_name like", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameNotLike(String value) {
            addCriterion("organize_name not like", value, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameIn(List<String> values) {
            addCriterion("organize_name in", values, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameNotIn(List<String> values) {
            addCriterion("organize_name not in", values, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameBetween(String value1, String value2) {
            addCriterion("organize_name between", value1, value2, "organizeName");
            return (Criteria) this;
        }

        public Criteria andOrganizeNameNotBetween(String value1, String value2) {
            addCriterion("organize_name not between", value1, value2, "organizeName");
            return (Criteria) this;
        }

        public Criteria andEventSubjectIsNull() {
            addCriterion("event_subject is null");
            return (Criteria) this;
        }

        public Criteria andEventSubjectIsNotNull() {
            addCriterion("event_subject is not null");
            return (Criteria) this;
        }

        public Criteria andEventSubjectEqualTo(String value) {
            addCriterion("event_subject =", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectNotEqualTo(String value) {
            addCriterion("event_subject <>", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectGreaterThan(String value) {
            addCriterion("event_subject >", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectGreaterThanOrEqualTo(String value) {
            addCriterion("event_subject >=", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectLessThan(String value) {
            addCriterion("event_subject <", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectLessThanOrEqualTo(String value) {
            addCriterion("event_subject <=", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectLike(String value) {
            addCriterion("event_subject like", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectNotLike(String value) {
            addCriterion("event_subject not like", value, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectIn(List<String> values) {
            addCriterion("event_subject in", values, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectNotIn(List<String> values) {
            addCriterion("event_subject not in", values, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectBetween(String value1, String value2) {
            addCriterion("event_subject between", value1, value2, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andEventSubjectNotBetween(String value1, String value2) {
            addCriterion("event_subject not between", value1, value2, "eventSubject");
            return (Criteria) this;
        }

        public Criteria andPosterImgIsNull() {
            addCriterion("poster_img is null");
            return (Criteria) this;
        }

        public Criteria andPosterImgIsNotNull() {
            addCriterion("poster_img is not null");
            return (Criteria) this;
        }

        public Criteria andPosterImgEqualTo(String value) {
            addCriterion("poster_img =", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgNotEqualTo(String value) {
            addCriterion("poster_img <>", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgGreaterThan(String value) {
            addCriterion("poster_img >", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgGreaterThanOrEqualTo(String value) {
            addCriterion("poster_img >=", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgLessThan(String value) {
            addCriterion("poster_img <", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgLessThanOrEqualTo(String value) {
            addCriterion("poster_img <=", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgLike(String value) {
            addCriterion("poster_img like", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgNotLike(String value) {
            addCriterion("poster_img not like", value, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgIn(List<String> values) {
            addCriterion("poster_img in", values, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgNotIn(List<String> values) {
            addCriterion("poster_img not in", values, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgBetween(String value1, String value2) {
            addCriterion("poster_img between", value1, value2, "posterImg");
            return (Criteria) this;
        }

        public Criteria andPosterImgNotBetween(String value1, String value2) {
            addCriterion("poster_img not between", value1, value2, "posterImg");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeIsNull() {
            addCriterion("recommend_time is null");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeIsNotNull() {
            addCriterion("recommend_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeEqualTo(Date value) {
            addCriterion("recommend_time =", value, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeNotEqualTo(Date value) {
            addCriterion("recommend_time <>", value, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeGreaterThan(Date value) {
            addCriterion("recommend_time >", value, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("recommend_time >=", value, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeLessThan(Date value) {
            addCriterion("recommend_time <", value, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeLessThanOrEqualTo(Date value) {
            addCriterion("recommend_time <=", value, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeIn(List<Date> values) {
            addCriterion("recommend_time in", values, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeNotIn(List<Date> values) {
            addCriterion("recommend_time not in", values, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeBetween(Date value1, Date value2) {
            addCriterion("recommend_time between", value1, value2, "recommendTime");
            return (Criteria) this;
        }

        public Criteria andRecommendTimeNotBetween(Date value1, Date value2) {
            addCriterion("recommend_time not between", value1, value2, "recommendTime");
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