package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PkPrizeResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PkPrizeResultExample() {
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIsNull() {
            addCriterion("prize_name is null");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIsNotNull() {
            addCriterion("prize_name is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeNameEqualTo(String value) {
            addCriterion("prize_name =", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotEqualTo(String value) {
            addCriterion("prize_name <>", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameGreaterThan(String value) {
            addCriterion("prize_name >", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameGreaterThanOrEqualTo(String value) {
            addCriterion("prize_name >=", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLessThan(String value) {
            addCriterion("prize_name <", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLessThanOrEqualTo(String value) {
            addCriterion("prize_name <=", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameLike(String value) {
            addCriterion("prize_name like", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotLike(String value) {
            addCriterion("prize_name not like", value, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameIn(List<String> values) {
            addCriterion("prize_name in", values, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotIn(List<String> values) {
            addCriterion("prize_name not in", values, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameBetween(String value1, String value2) {
            addCriterion("prize_name between", value1, value2, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeNameNotBetween(String value1, String value2) {
            addCriterion("prize_name not between", value1, value2, "prizeName");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeIsNull() {
            addCriterion("prize_type is null");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeIsNotNull() {
            addCriterion("prize_type is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeEqualTo(Byte value) {
            addCriterion("prize_type =", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeNotEqualTo(Byte value) {
            addCriterion("prize_type <>", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeGreaterThan(Byte value) {
            addCriterion("prize_type >", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("prize_type >=", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeLessThan(Byte value) {
            addCriterion("prize_type <", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("prize_type <=", value, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeIn(List<Byte> values) {
            addCriterion("prize_type in", values, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeNotIn(List<Byte> values) {
            addCriterion("prize_type not in", values, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeBetween(Byte value1, Byte value2) {
            addCriterion("prize_type between", value1, value2, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("prize_type not between", value1, value2, "prizeType");
            return (Criteria) this;
        }

        public Criteria andPrizeNumIsNull() {
            addCriterion("prize_num is null");
            return (Criteria) this;
        }

        public Criteria andPrizeNumIsNotNull() {
            addCriterion("prize_num is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeNumEqualTo(Integer value) {
            addCriterion("prize_num =", value, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumNotEqualTo(Integer value) {
            addCriterion("prize_num <>", value, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumGreaterThan(Integer value) {
            addCriterion("prize_num >", value, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_num >=", value, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumLessThan(Integer value) {
            addCriterion("prize_num <", value, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumLessThanOrEqualTo(Integer value) {
            addCriterion("prize_num <=", value, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumIn(List<Integer> values) {
            addCriterion("prize_num in", values, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumNotIn(List<Integer> values) {
            addCriterion("prize_num not in", values, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumBetween(Integer value1, Integer value2) {
            addCriterion("prize_num between", value1, value2, "prizeNum");
            return (Criteria) this;
        }

        public Criteria andPrizeNumNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_num not between", value1, value2, "prizeNum");
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

        public Criteria andChallengeTopicSloganIsNull() {
            addCriterion("challenge_topic_slogan is null");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganIsNotNull() {
            addCriterion("challenge_topic_slogan is not null");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganEqualTo(String value) {
            addCriterion("challenge_topic_slogan =", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganNotEqualTo(String value) {
            addCriterion("challenge_topic_slogan <>", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganGreaterThan(String value) {
            addCriterion("challenge_topic_slogan >", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganGreaterThanOrEqualTo(String value) {
            addCriterion("challenge_topic_slogan >=", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganLessThan(String value) {
            addCriterion("challenge_topic_slogan <", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganLessThanOrEqualTo(String value) {
            addCriterion("challenge_topic_slogan <=", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganLike(String value) {
            addCriterion("challenge_topic_slogan like", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganNotLike(String value) {
            addCriterion("challenge_topic_slogan not like", value, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganIn(List<String> values) {
            addCriterion("challenge_topic_slogan in", values, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganNotIn(List<String> values) {
            addCriterion("challenge_topic_slogan not in", values, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganBetween(String value1, String value2) {
            addCriterion("challenge_topic_slogan between", value1, value2, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andChallengeTopicSloganNotBetween(String value1, String value2) {
            addCriterion("challenge_topic_slogan not between", value1, value2, "challengeTopicSlogan");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameIsNull() {
            addCriterion("reward_user_nickname is null");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameIsNotNull() {
            addCriterion("reward_user_nickname is not null");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameEqualTo(String value) {
            addCriterion("reward_user_nickname =", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameNotEqualTo(String value) {
            addCriterion("reward_user_nickname <>", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameGreaterThan(String value) {
            addCriterion("reward_user_nickname >", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("reward_user_nickname >=", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameLessThan(String value) {
            addCriterion("reward_user_nickname <", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameLessThanOrEqualTo(String value) {
            addCriterion("reward_user_nickname <=", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameLike(String value) {
            addCriterion("reward_user_nickname like", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameNotLike(String value) {
            addCriterion("reward_user_nickname not like", value, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameIn(List<String> values) {
            addCriterion("reward_user_nickname in", values, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameNotIn(List<String> values) {
            addCriterion("reward_user_nickname not in", values, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameBetween(String value1, String value2) {
            addCriterion("reward_user_nickname between", value1, value2, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserNicknameNotBetween(String value1, String value2) {
            addCriterion("reward_user_nickname not between", value1, value2, "rewardUserNickname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameIsNull() {
            addCriterion("reward_user_realname is null");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameIsNotNull() {
            addCriterion("reward_user_realname is not null");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameEqualTo(String value) {
            addCriterion("reward_user_realname =", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameNotEqualTo(String value) {
            addCriterion("reward_user_realname <>", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameGreaterThan(String value) {
            addCriterion("reward_user_realname >", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("reward_user_realname >=", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameLessThan(String value) {
            addCriterion("reward_user_realname <", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameLessThanOrEqualTo(String value) {
            addCriterion("reward_user_realname <=", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameLike(String value) {
            addCriterion("reward_user_realname like", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameNotLike(String value) {
            addCriterion("reward_user_realname not like", value, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameIn(List<String> values) {
            addCriterion("reward_user_realname in", values, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameNotIn(List<String> values) {
            addCriterion("reward_user_realname not in", values, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameBetween(String value1, String value2) {
            addCriterion("reward_user_realname between", value1, value2, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserRealnameNotBetween(String value1, String value2) {
            addCriterion("reward_user_realname not between", value1, value2, "rewardUserRealname");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarIsNull() {
            addCriterion("reward_user_avatar is null");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarIsNotNull() {
            addCriterion("reward_user_avatar is not null");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarEqualTo(String value) {
            addCriterion("reward_user_avatar =", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarNotEqualTo(String value) {
            addCriterion("reward_user_avatar <>", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarGreaterThan(String value) {
            addCriterion("reward_user_avatar >", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarGreaterThanOrEqualTo(String value) {
            addCriterion("reward_user_avatar >=", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarLessThan(String value) {
            addCriterion("reward_user_avatar <", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarLessThanOrEqualTo(String value) {
            addCriterion("reward_user_avatar <=", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarLike(String value) {
            addCriterion("reward_user_avatar like", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarNotLike(String value) {
            addCriterion("reward_user_avatar not like", value, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarIn(List<String> values) {
            addCriterion("reward_user_avatar in", values, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarNotIn(List<String> values) {
            addCriterion("reward_user_avatar not in", values, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarBetween(String value1, String value2) {
            addCriterion("reward_user_avatar between", value1, value2, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andRewardUserAvatarNotBetween(String value1, String value2) {
            addCriterion("reward_user_avatar not between", value1, value2, "rewardUserAvatar");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeIsNull() {
            addCriterion("effective_time is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeIsNotNull() {
            addCriterion("effective_time is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeEqualTo(Date value) {
            addCriterion("effective_time =", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeNotEqualTo(Date value) {
            addCriterion("effective_time <>", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeGreaterThan(Date value) {
            addCriterion("effective_time >", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("effective_time >=", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeLessThan(Date value) {
            addCriterion("effective_time <", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("effective_time <=", value, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeIn(List<Date> values) {
            addCriterion("effective_time in", values, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeNotIn(List<Date> values) {
            addCriterion("effective_time not in", values, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeBetween(Date value1, Date value2) {
            addCriterion("effective_time between", value1, value2, "effectiveTime");
            return (Criteria) this;
        }

        public Criteria andEffectiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("effective_time not between", value1, value2, "effectiveTime");
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

        public Criteria andHasTakeIsNull() {
            addCriterion("has_take is null");
            return (Criteria) this;
        }

        public Criteria andHasTakeIsNotNull() {
            addCriterion("has_take is not null");
            return (Criteria) this;
        }

        public Criteria andHasTakeEqualTo(Byte value) {
            addCriterion("has_take =", value, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeNotEqualTo(Byte value) {
            addCriterion("has_take <>", value, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeGreaterThan(Byte value) {
            addCriterion("has_take >", value, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeGreaterThanOrEqualTo(Byte value) {
            addCriterion("has_take >=", value, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeLessThan(Byte value) {
            addCriterion("has_take <", value, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeLessThanOrEqualTo(Byte value) {
            addCriterion("has_take <=", value, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeIn(List<Byte> values) {
            addCriterion("has_take in", values, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeNotIn(List<Byte> values) {
            addCriterion("has_take not in", values, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeBetween(Byte value1, Byte value2) {
            addCriterion("has_take between", value1, value2, "hasTake");
            return (Criteria) this;
        }

        public Criteria andHasTakeNotBetween(Byte value1, Byte value2) {
            addCriterion("has_take not between", value1, value2, "hasTake");
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