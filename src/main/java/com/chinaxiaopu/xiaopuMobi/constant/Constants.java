package com.chinaxiaopu.xiaopuMobi.constant;

/**
 * Created by liuwei
 * date: 16/9/27
 */
public class Constants {

    public static final Integer ACCESS_OK=1;// 1：成功；
    public static final Integer ACCESS_ERROR=0;// 0：失败，
    public static final Integer UNKNOWN_LOGIN = -1;//未登陆状态
    public static final Integer UNAUTHORIZED = 401;//没权限

    public static final Integer LOGIN_ERROR=0;// 0：登录失败，
    public static final Integer LOGIN_OK=1;// 1：登录成功；
    public static final Integer SYSTEM_ERROR=2;// 2：系统错误；
    public static final Integer MOBILE_NULL=11;// 11：手机号码为空；
    public static final Integer USER_NULL=12;// 12：用户不存在；
    public static final Integer USER_NOT_NULL=13;// 13：用户存在；
    public static final Integer PASSWORD_NULL=21;// 21：密码为空；
    public static final Integer PASSWORD_ERROR=22;// 22：密码错误；
    public static final Integer CAPTCHA_NULL=31;// 31：验证码为空；
    public static final Integer CAPTCHA_ERROR=32;// 32：验证码错误；
    public static final Integer CODE_ERROR=33;   //33:邀请码无效
    public static final String CAPTCHA_DIGEST = "u6aCD8Agie";//短信验证码key后缀
    public static final String NICK_NAME="校谱_";  //昵称前缀
    /**
     * 审核通过
     * */
    public static final int EVENT_MEMBERS_STATUS_ON = 1;//1.审核通过;
    public static final int EVENT_MEMBERS_STATUS_IN = 2;//2.审核中；
    public static final int EVENT_MEMBERS_STATUS_OFF = 3;//3.审核未通过；
    public static final int EVENT_IS_IN = 4;//已经加入此活动

    //0.待认领（默认）；1.已认领；2.认领中；3.已停止
    public static final int GROUP_STATUS_DEF = 0;//0.待认领（默认）；
    public static final int GROUP_STATUS_ON  = 1;//1.已认领；
    public static final int GROUP_STATUS_IN = 2;//2.认领中；
    public static final int GROUP_STATUS_OFF = 3;//3.已停止

    public static final int DATA_NO = 41;//没有数据
    public static final int HAVE_TO_VOTE = 42;//已投票
    public static final int GROUP_NAME_EXIST = 43;//社团名已存在
    public static final int CHALLENGE_NAME_EXIST = 44;//已加入此挑战
    public static final int EVENT_GROUP_PRESIDENT = 45;//非该活动社长
    public static final int TICKET_NO = 46;//门票发放完毕
    public static final int TICKET_INSUFFICIENT = 47;//门票不足

    /**
     * 抽奖
     */
    public static final int EVENTlOTTERY_100=100; //未抽到获奖者
    public static final String EVENTlOTTERY_100_MSG="未抽到获奖者";
    public static final int EVENTlOTTERY_101=101; //没有抽奖用户
    public static final String EVENTlOTTERY_101_MSG="没有抽奖用户";
    public static final int EVENTlOTTERY_102=102; //抽奖已结束
    public static final String EVENTlOTTERY_102_MSG="抽奖已结束";
    public static final String EVENTlOTTERY_1_MSG="当前活动ID不存在";

    public static final String ROLE_ADMIN="admin";
    public static final String ROLE_PRESIDENT="president";
    public static final String ROLE_PARTNERMANAGE="partnerManage";
    public static final String ROLE_ANCHOR="anchor";

    public static final String SMS_LOGIN = "SMS_17945054";

    public static final int GROUP_PRESIDENT_ROLE_ID = 2;//社长角色id
    public static final int NO_IN_GROUPS = 2;//非社团成员

    public static final String VR_SMS_TEMPLATE_CODE = "SMS_25781057";

    public static final int QR_CODE_TYPE_PRIZE = 1;
    public static final int QR_CODE_TYPE_TICKET = 2;

    public static final String MSG_TYPE_ANNOUNCE = "1";
    public static final String MSG_TYPE_REMIND = "2";
    public static final String MSG_TYPE_MESSAGE = "3";

    /**跳转到用户*/
    public static final int MSG_USER_TYPE = 1;
    /**跳转到活动*/
    public static final int MSG_EVENT_TYPE = 2;
    /**跳转到社团*/
    public static final int MSG_GROUP_TYPE = 3;
    /**跳转到图文PK*/
    public static final int MSG_PK_IMG_TYPE = 4;
    /**跳转到视频PK*/
    public static final int MSG_PK_VIDEO_TYPE = 5;
    /**跳转到普通图文*/
    public static final int MSG_IMG_TYPE = 6;
    /**跳转到普通视频*/
    public static final int MSG_VIDEO_TYPE = 7;
    /**跳转到奖品*/
    public static final int MSG_PRIZE_TYPE = 8;



    /** 申请加入社团通过*/
    public static final int GROUP_JOIN_AUDIT_OK = 1;
    /** 申请加入社团未通过*/
    public static final int GROUP_JOIN_AUDIT_NO = 2;
    /** 申请加入活动通过*/
    public static final int EVENT_JOIN_AUDIT_OK = 3;
    /** 申请加入活动未通过*/
    public static final int EVENT_JOIN_AUDIT_NO = 4;
    /** 申请申请来战奖品通过*/
    public static final int PK_CREATE_AUDIT_OK = 5;
    /** 申请申请来战奖品未通过*/
    public static final int PK_CREATE_AUDIT_NO = 6;
    /** 申请创建社团通过*/
    public static final int GROUP_CREATE_AUDIT_OK = 7;
    /** 申请创建社团未通过*/
    public static final int GROUP_CREATE_AUDIT_NO = 8;
    /** 申请认领社团通过消息*/
    public static final int GROUP_CLAIM_AUDIT_OK = 9;
    /** 申请认领社团未通过消息*/
    public static final int GROUP_CLAIM_AUDIT_NO = 10;
    /** 申请发布活动通过*/
    public static final int EVENT_CREATE_AUDIT_OK = 11;
    /** 申请发布活动未通过*/
    public static final int EVENT_CREATE_AUDIT_NO = 12;

    /**用户点赞消息*/
    public static final int TOPIC_USER_LIKE = 13;
    /**用户收藏消息*/
    public static final int TOPIC_USER_FAV = 14;
    /**用户评论消息*/
    public static final int TOPIC_USER_COMMENT = 15;
    /**用户投票消息*/
    public static final int TOPIC_USER_VOTE = 16;

    /**PK达到要求,生效*/
    public static final int PK_FINISH_OK = 17;
    /**PK未达到要求,未生效*/
    public static final int PK_FINISH_NO = 18;
    /**用户PK胜利获得奖励*/
    public static final int USER_GET_PK_PRIZE = 19;
    /**PK结束用户未获得奖励消息*/
    public static final int USER_NO_PK_PRIZE = 20;
    /**用户活动被删除*/
    public static final int SYS_EVENT_DEL =21;
    /**用户内容被删除*/
    public static final int SYS_TOPIC_DEL = 22;
    /**用户被关注*/
    public static final int USER_FOUCS = 23;

    /** 申请加入社团通过消息*/
    public static final String MSG_GROUP_JOIN_AUDIT_OK = "恭喜您！您加入“[###]”的申请已通过审核！";
    /** 申请加入社团未通过消息*/
    public static final String MSG_GROUP_JOIN_AUDIT_NO = "您申请加入“[###]”被驳回，驳回原因：{0}。";
    /** 申请加入活动通过消息*/
    public static final String MSG_EVENT_JOIN_AUDIT_OK = "恭喜您！您加入“[###]”的申请已通过审核！";
    /** 申请加入活动未通过消息*/
    public static final String MSG_EVENT_JOIN_AUDIT_NO = "您申请加入“[###]”被驳回，驳回原因：{0}。";
    /** 申请申请来战奖品通过消息*/
    public static final String MSG_PK_CREATE_AUDIT_OK = "恭喜您！您发布的来战“[###]”申请的奖品已获批准，内容已发布。";
    /** 申请申请来战奖品未通过消息*/
    public static final String MSG_PK_CREATE_AUDIT_NO = "您发布的来战“[###]”申请的奖品未获批准，请优化您要发布的内容。";
    /** 申请创建社团通过消息*/
    public static final String MSG_GROUP_CREATE_AUDIT_OK = "恭喜您！您创建的“[###]”已通过审核！";
    /** 申请创建建社团未通过消息*/
    public static final String MSG_GROUP_CREATE_AUDIT_NO = "您创建的“[###]”被驳回，驳回原因：{0}。";
    /** 申请认领社团通过消息*/
    public static final String MSG_GROUP_CLAIM_AUDIT_OK = "恭喜您！您认领的“[###]”已通过审核！";
    /** 申请认领社团未通过消息*/
    public static final String MSG_GROUP_CLAIM_AUDIT_NO = "您认领的“[###]”被驳回，驳回原因：{0}。";
    /** 申请发布活动通过消息*/
    public static final String MSG_EVENT_CREATE_AUDIT_OK = "恭喜您！您发布的“[###]”已通过审核！";
    /** 申请发布活动未通过消息*/
    public static final String MSG_EVENT_CREATE_AUDIT_NO = "您发布的“[###]”被驳回，驳回原因：{0}。";

    /**用户点赞消息消息*/
    public static final String MSG_TOPIC_USER_LIKE = "[###]点赞了您的“[###]”。";
    /**用户收藏消息消息*/
    public static final String MSG_TOPIC_USER_FAV = "[###]收藏了您的“[###]”。";
    /**用户评论消息消息*/
    public static final String MSG_TOPIC_USER_COMMENT = "[###]评论了您的“[###]”。";
    /**用户投票消息消息*/
    public static final String MSG_TOPIC_USER_VOTE = "[###]给您的“[###]”投票了。";

    /**PK达到要求,生效消息*/
    public static final String MSG_PK_FINISH_OK = "恭喜您！您参加的来战[###]达到规定参加人数和投票人数，奖励已生效";
    /**PK未达到要求,未生效消息*/
    public static final String MSG_PK_FINISH_NO = "您发布的来战[###]未达到规定参加人数或投票人数，奖励未生效。";
    /**用户PK胜利获得奖励消息*/
    public static final String MSG_USER_GET_PK_PRIZE = "恭喜您！您在来战[###]中获得了[###]x{0}，请在个人中心“我的奖励”中查看。";
    /**PK结束用户未获得奖励消息*/
    public static final String MSG_USER_NO_PK_PRIZE = "您参加的来战[###]投票已结束，获奖者[###]。";

    /**用户活动被删除消息*/
    public static final String MSG_SYS_EVENT_DEL = "您发布的[###]被管理员删除";
    /**用户内容被删除消息*/
    public static final String MSG_SYS_TOPIC_DEL = "您发布的[###]被管理员删除";
    /**用户被关注消息*/
    public static final String MSG_USER_FOUCS = "[###]关注了您。";

    /**
     * 图文类型
     */
    public static final String MSG_TOPIC_TYPE_IMAGE="图片";
    public static final String MSG_TOPIC_TYPE_VIDEO="视频";
    public static final String MSG_TOPIC_TYPE_AUDIO="音频";

    public static final String MSG_TOPIC_STATUS_DEF="图文待审核";
    public static final String MSG_TOPIC_STATUS_NO="图文审核未通过";
    public static final String MSG_TOPIC_STATUS_OFF="图文审核通过";
    public static final String MSG_TOPIC_STATUS_IN="图文审核中";

    public static final String APP_USER = "app_user:";
    public static final String APP_REGISTRATION = "app_registration:";
    public static final String APP_TOKEN = "app_token:";


}
