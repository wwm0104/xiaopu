package com.chinaxiaopu.xiaopuMobi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PkResultExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PkResultExample() {
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

        public Criteria andRankingIsNull() {
            addCriterion("ranking is null");
            return (Criteria) this;
        }

        public Criteria andRankingIsNotNull() {
            addCriterion("ranking is not null");
            return (Criteria) this;
        }

        public Criteria andRankingEqualTo(Byte value) {
            addCriterion("ranking =", value, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingNotEqualTo(Byte value) {
            addCriterion("ranking <>", value, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingGreaterThan(Byte value) {
            addCriterion("ranking >", value, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingGreaterThanOrEqualTo(Byte value) {
            addCriterion("ranking >=", value, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingLessThan(Byte value) {
            addCriterion("ranking <", value, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingLessThanOrEqualTo(Byte value) {
            addCriterion("ranking <=", value, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingIn(List<Byte> values) {
            addCriterion("ranking in", values, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingNotIn(List<Byte> values) {
            addCriterion("ranking not in", values, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingBetween(Byte value1, Byte value2) {
            addCriterion("ranking between", value1, value2, "ranking");
            return (Criteria) this;
        }

        public Criteria andRankingNotBetween(Byte value1, Byte value2) {
            addCriterion("ranking not between", value1, value2, "ranking");
            return (Criteria) this;
        }

        public Criteria andVoteCntIsNull() {
            addCriterion("vote_cnt is null");
            return (Criteria) this;
        }

        public Criteria andVoteCntIsNotNull() {
            addCriterion("vote_cnt is not null");
            return (Criteria) this;
        }

        public Criteria andVoteCntEqualTo(Byte value) {
            addCriterion("vote_cnt =", value, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntNotEqualTo(Byte value) {
            addCriterion("vote_cnt <>", value, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntGreaterThan(Byte value) {
            addCriterion("vote_cnt >", value, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntGreaterThanOrEqualTo(Byte value) {
            addCriterion("vote_cnt >=", value, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntLessThan(Byte value) {
            addCriterion("vote_cnt <", value, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntLessThanOrEqualTo(Byte value) {
            addCriterion("vote_cnt <=", value, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntIn(List<Byte> values) {
            addCriterion("vote_cnt in", values, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntNotIn(List<Byte> values) {
            addCriterion("vote_cnt not in", values, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntBetween(Byte value1, Byte value2) {
            addCriterion("vote_cnt between", value1, value2, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andVoteCntNotBetween(Byte value1, Byte value2) {
            addCriterion("vote_cnt not between", value1, value2, "voteCnt");
            return (Criteria) this;
        }

        public Criteria andIsFinishIsNull() {
            addCriterion("is_finish is null");
            return (Criteria) this;
        }

        public Criteria andIsFinishIsNotNull() {
            addCriterion("is_finish is not null");
            return (Criteria) this;
        }

        public Criteria andIsFinishEqualTo(Byte value) {
            addCriterion("is_finish =", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotEqualTo(Byte value) {
            addCriterion("is_finish <>", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishGreaterThan(Byte value) {
            addCriterion("is_finish >", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_finish >=", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishLessThan(Byte value) {
            addCriterion("is_finish <", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishLessThanOrEqualTo(Byte value) {
            addCriterion("is_finish <=", value, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishIn(List<Byte> values) {
            addCriterion("is_finish in", values, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotIn(List<Byte> values) {
            addCriterion("is_finish not in", values, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishBetween(Byte value1, Byte value2) {
            addCriterion("is_finish between", value1, value2, "isFinish");
            return (Criteria) this;
        }

        public Criteria andIsFinishNotBetween(Byte value1, Byte value2) {
            addCriterion("is_finish not between", value1, value2, "isFinish");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
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