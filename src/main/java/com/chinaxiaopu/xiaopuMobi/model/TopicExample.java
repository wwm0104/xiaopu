package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TopicExample() {
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

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Integer value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Integer value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Integer value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Integer value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Integer value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Integer> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Integer> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Integer value1, Integer value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameIsNull() {
            addCriterion("creator_nickname is null");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameIsNotNull() {
            addCriterion("creator_nickname is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameEqualTo(String value) {
            addCriterion("creator_nickname =", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameNotEqualTo(String value) {
            addCriterion("creator_nickname <>", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameGreaterThan(String value) {
            addCriterion("creator_nickname >", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("creator_nickname >=", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameLessThan(String value) {
            addCriterion("creator_nickname <", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameLessThanOrEqualTo(String value) {
            addCriterion("creator_nickname <=", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameLike(String value) {
            addCriterion("creator_nickname like", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameNotLike(String value) {
            addCriterion("creator_nickname not like", value, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameIn(List<String> values) {
            addCriterion("creator_nickname in", values, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameNotIn(List<String> values) {
            addCriterion("creator_nickname not in", values, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameBetween(String value1, String value2) {
            addCriterion("creator_nickname between", value1, value2, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorNicknameNotBetween(String value1, String value2) {
            addCriterion("creator_nickname not between", value1, value2, "creatorNickname");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarIsNull() {
            addCriterion("creator_avatar is null");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarIsNotNull() {
            addCriterion("creator_avatar is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarEqualTo(String value) {
            addCriterion("creator_avatar =", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarNotEqualTo(String value) {
            addCriterion("creator_avatar <>", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarGreaterThan(String value) {
            addCriterion("creator_avatar >", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("creator_avatar >=", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarLessThan(String value) {
            addCriterion("creator_avatar <", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarLessThanOrEqualTo(String value) {
            addCriterion("creator_avatar <=", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarLike(String value) {
            addCriterion("creator_avatar like", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarNotLike(String value) {
            addCriterion("creator_avatar not like", value, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarIn(List<String> values) {
            addCriterion("creator_avatar in", values, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarNotIn(List<String> values) {
            addCriterion("creator_avatar not in", values, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarBetween(String value1, String value2) {
            addCriterion("creator_avatar between", value1, value2, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andCreatorAvatarNotBetween(String value1, String value2) {
            addCriterion("creator_avatar not between", value1, value2, "creatorAvatar");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNull() {
            addCriterion("school_id is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNotNull() {
            addCriterion("school_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdEqualTo(Integer value) {
            addCriterion("school_id =", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotEqualTo(Integer value) {
            addCriterion("school_id <>", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThan(Integer value) {
            addCriterion("school_id >", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("school_id >=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThan(Integer value) {
            addCriterion("school_id <", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThanOrEqualTo(Integer value) {
            addCriterion("school_id <=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIn(List<Integer> values) {
            addCriterion("school_id in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotIn(List<Integer> values) {
            addCriterion("school_id not in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdBetween(Integer value1, Integer value2) {
            addCriterion("school_id between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotBetween(Integer value1, Integer value2) {
            addCriterion("school_id not between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIsNull() {
            addCriterion("school_name is null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIsNotNull() {
            addCriterion("school_name is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolNameEqualTo(String value) {
            addCriterion("school_name =", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotEqualTo(String value) {
            addCriterion("school_name <>", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThan(String value) {
            addCriterion("school_name >", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameGreaterThanOrEqualTo(String value) {
            addCriterion("school_name >=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThan(String value) {
            addCriterion("school_name <", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLessThanOrEqualTo(String value) {
            addCriterion("school_name <=", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameLike(String value) {
            addCriterion("school_name like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotLike(String value) {
            addCriterion("school_name not like", value, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameIn(List<String> values) {
            addCriterion("school_name in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotIn(List<String> values) {
            addCriterion("school_name not in", values, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameBetween(String value1, String value2) {
            addCriterion("school_name between", value1, value2, "schoolName");
            return (Criteria) this;
        }

        public Criteria andSchoolNameNotBetween(String value1, String value2) {
            addCriterion("school_name not between", value1, value2, "schoolName");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdIsNull() {
            addCriterion("challenge_topic_id is null");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdIsNotNull() {
            addCriterion("challenge_topic_id is not null");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdEqualTo(Integer value) {
            addCriterion("challenge_topic_id =", value, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdNotEqualTo(Integer value) {
            addCriterion("challenge_topic_id <>", value, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdGreaterThan(Integer value) {
            addCriterion("challenge_topic_id >", value, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("challenge_topic_id >=", value, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdLessThan(Integer value) {
            addCriterion("challenge_topic_id <", value, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdLessThanOrEqualTo(Integer value) {
            addCriterion("challenge_topic_id <=", value, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdIn(List<Integer> values) {
            addCriterion("challenge_topic_id in", values, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdNotIn(List<Integer> values) {
            addCriterion("challenge_topic_id not in", values, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdBetween(Integer value1, Integer value2) {
            addCriterion("challenge_topic_id between", value1, value2, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("challenge_topic_id not between", value1, value2, "challengeTopicId");
            return (Criteria) this;
        }

        public Criteria andIsChallengerIsNull() {
            addCriterion("is_challenger is null");
            return (Criteria) this;
        }

        public Criteria andIsChallengerIsNotNull() {
            addCriterion("is_challenger is not null");
            return (Criteria) this;
        }

        public Criteria andIsChallengerEqualTo(Integer value) {
            addCriterion("is_challenger =", value, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerNotEqualTo(Integer value) {
            addCriterion("is_challenger <>", value, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerGreaterThan(Integer value) {
            addCriterion("is_challenger >", value, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_challenger >=", value, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerLessThan(Integer value) {
            addCriterion("is_challenger <", value, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerLessThanOrEqualTo(Integer value) {
            addCriterion("is_challenger <=", value, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerIn(List<Integer> values) {
            addCriterion("is_challenger in", values, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerNotIn(List<Integer> values) {
            addCriterion("is_challenger not in", values, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerBetween(Integer value1, Integer value2) {
            addCriterion("is_challenger between", value1, value2, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andIsChallengerNotBetween(Integer value1, Integer value2) {
            addCriterion("is_challenger not between", value1, value2, "isChallenger");
            return (Criteria) this;
        }

        public Criteria andChallengeIdIsNull() {
            addCriterion("challenge_id is null");
            return (Criteria) this;
        }

        public Criteria andChallengeIdIsNotNull() {
            addCriterion("challenge_id is not null");
            return (Criteria) this;
        }

        public Criteria andChallengeIdEqualTo(Integer value) {
            addCriterion("challenge_id =", value, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdNotEqualTo(Integer value) {
            addCriterion("challenge_id <>", value, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdGreaterThan(Integer value) {
            addCriterion("challenge_id >", value, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("challenge_id >=", value, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdLessThan(Integer value) {
            addCriterion("challenge_id <", value, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdLessThanOrEqualTo(Integer value) {
            addCriterion("challenge_id <=", value, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdIn(List<Integer> values) {
            addCriterion("challenge_id in", values, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdNotIn(List<Integer> values) {
            addCriterion("challenge_id not in", values, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdBetween(Integer value1, Integer value2) {
            addCriterion("challenge_id between", value1, value2, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("challenge_id not between", value1, value2, "challengeId");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameIsNull() {
            addCriterion("challenge_nickname is null");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameIsNotNull() {
            addCriterion("challenge_nickname is not null");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameEqualTo(String value) {
            addCriterion("challenge_nickname =", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameNotEqualTo(String value) {
            addCriterion("challenge_nickname <>", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameGreaterThan(String value) {
            addCriterion("challenge_nickname >", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("challenge_nickname >=", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameLessThan(String value) {
            addCriterion("challenge_nickname <", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameLessThanOrEqualTo(String value) {
            addCriterion("challenge_nickname <=", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameLike(String value) {
            addCriterion("challenge_nickname like", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameNotLike(String value) {
            addCriterion("challenge_nickname not like", value, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameIn(List<String> values) {
            addCriterion("challenge_nickname in", values, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameNotIn(List<String> values) {
            addCriterion("challenge_nickname not in", values, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameBetween(String value1, String value2) {
            addCriterion("challenge_nickname between", value1, value2, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeNicknameNotBetween(String value1, String value2) {
            addCriterion("challenge_nickname not between", value1, value2, "challengeNickname");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarIsNull() {
            addCriterion("challenge_avatar is null");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarIsNotNull() {
            addCriterion("challenge_avatar is not null");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarEqualTo(String value) {
            addCriterion("challenge_avatar =", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarNotEqualTo(String value) {
            addCriterion("challenge_avatar <>", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarGreaterThan(String value) {
            addCriterion("challenge_avatar >", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("challenge_avatar >=", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarLessThan(String value) {
            addCriterion("challenge_avatar <", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarLessThanOrEqualTo(String value) {
            addCriterion("challenge_avatar <=", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarLike(String value) {
            addCriterion("challenge_avatar like", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarNotLike(String value) {
            addCriterion("challenge_avatar not like", value, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarIn(List<String> values) {
            addCriterion("challenge_avatar in", values, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarNotIn(List<String> values) {
            addCriterion("challenge_avatar not in", values, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarBetween(String value1, String value2) {
            addCriterion("challenge_avatar between", value1, value2, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChallengeAvatarNotBetween(String value1, String value2) {
            addCriterion("challenge_avatar not between", value1, value2, "challengeAvatar");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNull() {
            addCriterion("channel_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelIdIsNotNull() {
            addCriterion("channel_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelIdEqualTo(Integer value) {
            addCriterion("channel_id =", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotEqualTo(Integer value) {
            addCriterion("channel_id <>", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThan(Integer value) {
            addCriterion("channel_id >", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel_id >=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThan(Integer value) {
            addCriterion("channel_id <", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdLessThanOrEqualTo(Integer value) {
            addCriterion("channel_id <=", value, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdIn(List<Integer> values) {
            addCriterion("channel_id in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotIn(List<Integer> values) {
            addCriterion("channel_id not in", values, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdBetween(Integer value1, Integer value2) {
            addCriterion("channel_id between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("channel_id not between", value1, value2, "channelId");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNull() {
            addCriterion("channel_name is null");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("channel_name is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("channel_name =", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("channel_name <>", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("channel_name >", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_name >=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("channel_name <", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("channel_name <=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("channel_name like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("channel_name not like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameIn(List<String> values) {
            addCriterion("channel_name in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotIn(List<String> values) {
            addCriterion("channel_name not in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("channel_name between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("channel_name not between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSloganIsNull() {
            addCriterion("slogan is null");
            return (Criteria) this;
        }

        public Criteria andSloganIsNotNull() {
            addCriterion("slogan is not null");
            return (Criteria) this;
        }

        public Criteria andSloganEqualTo(String value) {
            addCriterion("slogan =", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotEqualTo(String value) {
            addCriterion("slogan <>", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganGreaterThan(String value) {
            addCriterion("slogan >", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganGreaterThanOrEqualTo(String value) {
            addCriterion("slogan >=", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganLessThan(String value) {
            addCriterion("slogan <", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganLessThanOrEqualTo(String value) {
            addCriterion("slogan <=", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganLike(String value) {
            addCriterion("slogan like", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotLike(String value) {
            addCriterion("slogan not like", value, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganIn(List<String> values) {
            addCriterion("slogan in", values, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotIn(List<String> values) {
            addCriterion("slogan not in", values, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganBetween(String value1, String value2) {
            addCriterion("slogan between", value1, value2, "slogan");
            return (Criteria) this;
        }

        public Criteria andSloganNotBetween(String value1, String value2) {
            addCriterion("slogan not between", value1, value2, "slogan");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNull() {
            addCriterion("recommend is null");
            return (Criteria) this;
        }

        public Criteria andRecommendIsNotNull() {
            addCriterion("recommend is not null");
            return (Criteria) this;
        }

        public Criteria andRecommendEqualTo(Integer value) {
            addCriterion("recommend =", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotEqualTo(Integer value) {
            addCriterion("recommend <>", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThan(Integer value) {
            addCriterion("recommend >", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendGreaterThanOrEqualTo(Integer value) {
            addCriterion("recommend >=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThan(Integer value) {
            addCriterion("recommend <", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendLessThanOrEqualTo(Integer value) {
            addCriterion("recommend <=", value, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendIn(List<Integer> values) {
            addCriterion("recommend in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotIn(List<Integer> values) {
            addCriterion("recommend not in", values, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendBetween(Integer value1, Integer value2) {
            addCriterion("recommend between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andRecommendNotBetween(Integer value1, Integer value2) {
            addCriterion("recommend not between", value1, value2, "recommend");
            return (Criteria) this;
        }

        public Criteria andLikeCntIsNull() {
            addCriterion("like_cnt is null");
            return (Criteria) this;
        }

        public Criteria andLikeCntIsNotNull() {
            addCriterion("like_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andLikeCntEqualTo(Integer value) {
            addCriterion("like_cnt =", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntNotEqualTo(Integer value) {
            addCriterion("like_cnt <>", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntGreaterThan(Integer value) {
            addCriterion("like_cnt >", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("like_cnt >=", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntLessThan(Integer value) {
            addCriterion("like_cnt <", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntLessThanOrEqualTo(Integer value) {
            addCriterion("like_cnt <=", value, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntIn(List<Integer> values) {
            addCriterion("like_cnt in", values, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntNotIn(List<Integer> values) {
            addCriterion("like_cnt not in", values, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntBetween(Integer value1, Integer value2) {
            addCriterion("like_cnt between", value1, value2, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andLikeCntNotBetween(Integer value1, Integer value2) {
            addCriterion("like_cnt not between", value1, value2, "likeCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntIsNull() {
            addCriterion("favorite_cnt is null");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntIsNotNull() {
            addCriterion("favorite_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntEqualTo(Integer value) {
            addCriterion("favorite_cnt =", value, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntNotEqualTo(Integer value) {
            addCriterion("favorite_cnt <>", value, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntGreaterThan(Integer value) {
            addCriterion("favorite_cnt >", value, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorite_cnt >=", value, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntLessThan(Integer value) {
            addCriterion("favorite_cnt <", value, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntLessThanOrEqualTo(Integer value) {
            addCriterion("favorite_cnt <=", value, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntIn(List<Integer> values) {
            addCriterion("favorite_cnt in", values, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntNotIn(List<Integer> values) {
            addCriterion("favorite_cnt not in", values, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntBetween(Integer value1, Integer value2) {
            addCriterion("favorite_cnt between", value1, value2, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andFavoriteCntNotBetween(Integer value1, Integer value2) {
            addCriterion("favorite_cnt not between", value1, value2, "favoriteCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntIsNull() {
            addCriterion("comment_cnt is null");
            return (Criteria) this;
        }

        public Criteria andCommentCntIsNotNull() {
            addCriterion("comment_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCntEqualTo(Integer value) {
            addCriterion("comment_cnt =", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntNotEqualTo(Integer value) {
            addCriterion("comment_cnt <>", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntGreaterThan(Integer value) {
            addCriterion("comment_cnt >", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_cnt >=", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntLessThan(Integer value) {
            addCriterion("comment_cnt <", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntLessThanOrEqualTo(Integer value) {
            addCriterion("comment_cnt <=", value, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntIn(List<Integer> values) {
            addCriterion("comment_cnt in", values, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntNotIn(List<Integer> values) {
            addCriterion("comment_cnt not in", values, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntBetween(Integer value1, Integer value2) {
            addCriterion("comment_cnt between", value1, value2, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andCommentCntNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_cnt not between", value1, value2, "commentCnt");
            return (Criteria) this;
        }

        public Criteria andIsOfficialIsNull() {
            addCriterion("is_official is null");
            return (Criteria) this;
        }

        public Criteria andIsOfficialIsNotNull() {
            addCriterion("is_official is not null");
            return (Criteria) this;
        }

        public Criteria andIsOfficialEqualTo(Integer value) {
            addCriterion("is_official =", value, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialNotEqualTo(Integer value) {
            addCriterion("is_official <>", value, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialGreaterThan(Integer value) {
            addCriterion("is_official >", value, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_official >=", value, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialLessThan(Integer value) {
            addCriterion("is_official <", value, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialLessThanOrEqualTo(Integer value) {
            addCriterion("is_official <=", value, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialIn(List<Integer> values) {
            addCriterion("is_official in", values, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialNotIn(List<Integer> values) {
            addCriterion("is_official not in", values, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialBetween(Integer value1, Integer value2) {
            addCriterion("is_official between", value1, value2, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsOfficialNotBetween(Integer value1, Integer value2) {
            addCriterion("is_official not between", value1, value2, "isOfficial");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Integer value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Integer value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Integer value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Integer value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Integer value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Integer> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Integer> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Integer value1, Integer value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Integer value1, Integer value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
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

        public Criteria andExpireTimeIsNull() {
            addCriterion("expire_time is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("expire_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(Date value) {
            addCriterion("expire_time =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(Date value) {
            addCriterion("expire_time <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(Date value) {
            addCriterion("expire_time >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expire_time >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(Date value) {
            addCriterion("expire_time <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(Date value) {
            addCriterion("expire_time <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<Date> values) {
            addCriterion("expire_time in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<Date> values) {
            addCriterion("expire_time not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(Date value1, Date value2) {
            addCriterion("expire_time between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(Date value1, Date value2) {
            addCriterion("expire_time not between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andIsPkIsNull() {
            addCriterion("is_pk is null");
            return (Criteria) this;
        }

        public Criteria andIsPkIsNotNull() {
            addCriterion("is_pk is not null");
            return (Criteria) this;
        }

        public Criteria andIsPkEqualTo(Integer value) {
            addCriterion("is_pk =", value, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkNotEqualTo(Integer value) {
            addCriterion("is_pk <>", value, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkGreaterThan(Integer value) {
            addCriterion("is_pk >", value, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_pk >=", value, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkLessThan(Integer value) {
            addCriterion("is_pk <", value, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkLessThanOrEqualTo(Integer value) {
            addCriterion("is_pk <=", value, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkIn(List<Integer> values) {
            addCriterion("is_pk in", values, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkNotIn(List<Integer> values) {
            addCriterion("is_pk not in", values, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkBetween(Integer value1, Integer value2) {
            addCriterion("is_pk between", value1, value2, "isPk");
            return (Criteria) this;
        }

        public Criteria andIsPkNotBetween(Integer value1, Integer value2) {
            addCriterion("is_pk not between", value1, value2, "isPk");
            return (Criteria) this;
        }

        public Criteria andFurtherIsNull() {
            addCriterion("further is null");
            return (Criteria) this;
        }

        public Criteria andFurtherIsNotNull() {
            addCriterion("further is not null");
            return (Criteria) this;
        }

        public Criteria andFurtherEqualTo(String value) {
            addCriterion("further =", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherNotEqualTo(String value) {
            addCriterion("further <>", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherGreaterThan(String value) {
            addCriterion("further >", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherGreaterThanOrEqualTo(String value) {
            addCriterion("further >=", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherLessThan(String value) {
            addCriterion("further <", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherLessThanOrEqualTo(String value) {
            addCriterion("further <=", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherLike(String value) {
            addCriterion("further like", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherNotLike(String value) {
            addCriterion("further not like", value, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherIn(List<String> values) {
            addCriterion("further in", values, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherNotIn(List<String> values) {
            addCriterion("further not in", values, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherBetween(String value1, String value2) {
            addCriterion("further between", value1, value2, "further");
            return (Criteria) this;
        }

        public Criteria andFurtherNotBetween(String value1, String value2) {
            addCriterion("further not between", value1, value2, "further");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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