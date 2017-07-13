package com.chinaxiaopu.xiaopuMobi.service.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.mapper.*;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.MsgPushService;
import com.chinaxiaopu.xiaopuMobi.vo.topic.BannerVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.TopicCommentVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.UserFanCountsVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.UserFanListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by hao on 2016/11/3.
 */
@Service
public class TopicDownService {
    @Autowired
    private TopicMapper topicMapper;    //话题表mapper
    @Autowired
    private TopicLikeMapper likeMapper;  //点赞表mapper
    @Autowired
    private TopicFavMapper topicFavMapper;  //收藏表mapper
    @Autowired
    private TipoffMapper tipoffMapper;      //举报表mapper
    @Autowired
    private TopicCommentMapper topicCommentMapper;  //评论表mapper
    @Autowired
    private BannerImgMapper bannerImgMapper; //轮播图mapper
    @Autowired
    private UserFansMapper userFansMapper;   //用户关系mapper
    @Autowired
    private TopicPlayMapper topicPlayMapper;  //播放量mapper
    @Autowired
    private MsgPushService msgPushService;  //消息推送service


    /**
     *点赞or已点赞
     * @param likeDto  条件：topicid，userid
     * @param userInfo  当前登录人信息
     * @return
     */
    public Result doLike(LikeDto likeDto, UserInfo userInfo){
        Result result=new Result();
        TopicLike topicLike;
        Topic topic;
        if(StringUtils.isEmpty(likeDto.getTopicId())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("主题id不能为空");
            return result;
        }
        int status=topicMapper.selectStatusByTopicId(likeDto.getTopicId());   //获取图文状态
        if(status!=1){
            result.setResultCode(Result.FAILURE);
            result.setMsg(Constants.MSG_TOPIC_STATUS_IN);
            return result;
        }
        int a=likeMapper.selectCountByTopicIdAndUserId(likeDto.getTopicId(),userInfo.getUserId());
        if(a>0){
            result.setResultCode(Result.FAILURE);
            result.setMsg("已点赞");
            return result;
        }else{
            //执行点赞操作
            topicLike=new TopicLike();
            topicLike.setLikeTime(new Date());//点赞时间
            topicLike.setTopicId(likeDto.getTopicId());//话题id
            topicLike.setUserId(userInfo.getUserId());//用户id
            if(StringUtils.isEmpty(userInfo.getNickName())){
                topicLike.setUserNickname("无名");
            }else{
                topicLike.setUserNickname(userInfo.getNickName());//用户昵称
            }
            int b=likeMapper.insert(topicLike);                            //添加点赞表
            if(b>0){
                topic=new Topic();
                topic=topicMapper.selectByPrimaryKey(likeDto.getTopicId());// 获取当前话题总点赞数
                topic.setLikeCnt(topic.getLikeCnt()+1);                     //点赞数+1
                int c= topicMapper.updateByPrimaryKeySelective(topic);
                if(c>0){
                    result.setResultCode(Result.SUCCESS);
                    result.setMsg("点赞成功");
                    if(topic.getType()!=3) {    //音频暂不实现
                        //消息推送
                        int action = Constants.TOPIC_USER_LIKE;
                        Set<Integer> userIds = new HashSet<>(); //接收者
                        userIds.add(topic.getCreatorId());
                        int sender = userInfo.getUserId();    //发送者
                        MsgFromDto msgFromDto = new MsgFromDto();
                        msgFromDto.setUserId(userInfo.getUserId());
                        msgFromDto.setUserName(userInfo.getNickName());
                        msgFromDto.setTopicId(topic.getId());
                        msgFromDto.setIsPk(topic.getIsPk());
                        Gson gson = new Gson();
                        Map<String, Object> urlsMap = gson.fromJson(topic.getContent(), Map.class);
                        String imgUrl = "";
                        List<String> imgs = (List<String>) urlsMap.get("urls");
                        if (topic.getType() == 1) {
                            imgUrl = imgs.get(0);
                        } else if (topic.getType() == 2) {
                            imgUrl = imgs.get(1);
                        }
                        String topicName="";  //图文名称
                        if(topic.getIsPk()!=1){      //根据是否PK
                            if(topic.getType()==1){
                                topicName=Constants.MSG_TOPIC_TYPE_IMAGE;
                            }else{
                                topicName=Constants.MSG_TOPIC_TYPE_VIDEO;
                            }
                        }else{
                            topicName=topic.getSlogan();
                            msgFromDto.setExpireTime(topic.getExpireTime());
                        }
                        msgFromDto.setRightImgUrl(imgUrl);
                        msgFromDto.setTopicName(topicName);
                        msgFromDto.setTopicType(topic.getType());
                        msgPushService.sendPushMsgByUser(action, userIds, sender, msgFromDto);
                    }
                }else{
                    result.setResultCode(Result.FAILURE);
                    result.setMsg("点赞失败");
                }
            }else{
                result.setResultCode(Result.FAILURE);
            }
        }
        return result;
    }

    /**
     *举报or已举报
     * @param tipoffDto 条件：topicid,userid,desc举报内容
     * @param userInfo
     * @return
     */
    public Result doTipoff(TipoffDto tipoffDto,UserInfo userInfo){
        Result result=new Result();
        Tipoff tipoff;
        if(StringUtils.isEmpty(tipoffDto.getTipoffId())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("TipoffId不能为空");
            return result;
        }
        if(StringUtils.isEmpty(tipoffDto.getTipoffType())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("举报类型不能为空");
            return result;
        }
        if(StringUtils.isEmpty(tipoffDto.getDesc())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("举报内容不能为空");
            return result;
        }
        int a = tipoffMapper.selectCountByTipoffIdAndUserIdAndTipoffType(tipoffDto.getTipoffId(), userInfo.getUserId(),tipoffDto.getTipoffType());
        if (a > 0) {
            result.setResultCode(Result.FAILURE);
            result.setMsg("已举报");
            return result;
        } else {
            tipoff = new Tipoff();
            tipoff.setTipoffId(tipoffDto.getTipoffId());//举报id
            tipoff.setTipoffTime(new Date());//举报时间
            tipoff.setTipoffUserId(userInfo.getUserId());//举报人id
            tipoff.setDesc(tipoffDto.getDesc());//举报内容
            tipoff.setTipoffType(tipoffDto.getTipoffType()); //举报类型
            int b = tipoffMapper.insert(tipoff);
            if (b > 0) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("举报成功");
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("举报失败");
            }
        }
        return result;
    }

    /**
     * 收藏or取消收藏
     * @param favDto
     * @param userInfo 当前登录人信息
     * @return
     */
    public Result doFav(FavDto favDto,UserInfo userInfo){
        Result result=new Result();
        TopicFav topicFav;
        Topic topic;
        if(StringUtils.isEmpty(favDto.getTopicId())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("主题id不能为空");
            return result;
        }
        int status=topicMapper.selectStatusByTopicId(favDto.getTopicId());   //获取图文状态
        if(status!=1){
            result.setResultCode(Result.FAILURE);
            result.setMsg(Constants.MSG_TOPIC_STATUS_IN);
            return result;
        }
        int a = topicFavMapper.selectCountByTopicIdAndUserId(favDto.getTopicId(), userInfo.getUserId());
        if (a > 0) {
            //取消收藏操作
            int b = topicFavMapper.deleteByTopicIdAndUserId(favDto.getTopicId(), userInfo.getUserId());//删除收藏表记录
            if (b > 0) {
                topic = new Topic();
                topic = topicMapper.selectByPrimaryKey(favDto.getTopicId());
                if(topic.getFavoriteCnt()==0){   //如果等于0默认0
                    topic.setFavoriteCnt(topic.getFavoriteCnt());
                }else{
                    topic.setFavoriteCnt(topic.getFavoriteCnt() - 1);    //话题表收藏数-1
                }
                int c = topicMapper.updateByPrimaryKeySelective(topic);
                if (c > 0) {
                    result.setResultCode(Result.SUCCESS);
                    result.setMsg("取消收藏成功");
                } else {
                    result.setResultCode(Result.FAILURE);
                    result.setMsg("取消收藏失败");
                }
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("取消收藏失败");
            }

        } else {
            //收藏操作
            topicFav = new TopicFav();
            topicFav.setFavTime(new Date());//收藏时间
            topicFav.setTopicId(favDto.getTopicId());//话题id
            topicFav.setUserId(userInfo.getUserId());//用户id
            if (StringUtils.isEmpty(userInfo.getNickName())) {
                topicFav.setUserNickname("无名");
            } else {
                topicFav.setUserNickname(userInfo.getNickName());//用户昵称
            }
            int b = topicFavMapper.insert(topicFav);  //添加收藏表记录
            if (b > 0) {
                topic = new Topic();
                topic = topicMapper.selectByPrimaryKey(favDto.getTopicId());
                topic.setFavoriteCnt(topic.getFavoriteCnt() + 1);   //话题表收藏数+1
                int c = topicMapper.updateByPrimaryKeySelective(topic);
                if (c > 0) {
                    result.setResultCode(Result.SUCCESS);
                    result.setMsg("收藏成功");

                    if(topic.getType()!=3) {    //音频暂不实现
                        //消息推送
                        int action = Constants.TOPIC_USER_FAV;
                        Set<Integer> userIds = new HashSet<>(); //接收者
                        userIds.add(topic.getCreatorId());
                        int sender = userInfo.getUserId();    //发送者
                        MsgFromDto msgFromDto = new MsgFromDto();
                        msgFromDto.setUserId(userInfo.getUserId());
                        msgFromDto.setUserName(userInfo.getNickName());
                        msgFromDto.setTopicId(topic.getId());
                        msgFromDto.setIsPk(topic.getIsPk());
                        Gson gson = new Gson();
                        Map<String, Object> urlsMap = gson.fromJson(topic.getContent(), Map.class);
                        String imgUrl = "";  //图片地址
                        List<String> imgs = (List<String>) urlsMap.get("urls");
                        if (topic.getType() == 1) {
                            imgUrl = imgs.get(0);
                        } else if (topic.getType() == 2) {
                            imgUrl = imgs.get(1);
                        }
                        String topicName="";  //图文名称
                        if(topic.getIsPk()!=1){      //根据是否PK
                            if(topic.getType()==1){
                                topicName=Constants.MSG_TOPIC_TYPE_IMAGE;
                            }else{
                                topicName=Constants.MSG_TOPIC_TYPE_VIDEO;
                            }
                        }else{
                            topicName=topic.getSlogan();
                            msgFromDto.setExpireTime(topic.getExpireTime());
                        }
                        msgFromDto.setRightImgUrl(imgUrl);
                        msgFromDto.setTopicName(topicName);
                        msgFromDto.setTopicType(topic.getType());
                        msgPushService.sendPushMsgByUser(action, userIds, sender, msgFromDto);
                    }
                } else {
                    result.setResultCode(Result.FAILURE);
                    result.setMsg("收藏失败");
                }
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("收藏失败");
            }

        }

        return result;
    }

    /**
     * 添加评论
     * @param commentDto 条件：topicid，userid，comment评论内容
     * @param userInfo 当前登录人信息
     * @return
     */
    public Result doComment(CommentDto commentDto,UserInfo userInfo){
        Result result=new Result();
        Topic topic;
        TopicComment topicComment;
        if(StringUtils.isEmpty(commentDto.getTopicId())){
            result.setResultCode(Result.FAILURE);
            result.setMsg("主题id不能为空");
            return result;
        }
        int status=topicMapper.selectStatusByTopicId(commentDto.getTopicId());   //获取图文状态
        if(status!=1){
            result.setResultCode(Result.FAILURE);
            result.setMsg(Constants.MSG_TOPIC_STATUS_IN);
            return result;
        }
        topicComment = new TopicComment();
        topicComment.setTopicId(commentDto.getTopicId());//话题id
        topicComment.setActionTime(new Date());//评论时间
        topicComment.setComment(commentDto.getComment());//评论内容
        topicComment.setUserAvatarUrl(userInfo.getAvatarUrl());//用户图像地址
        topicComment.setUserId(userInfo.getUserId());//用户id
        if(StringUtils.isEmpty(userInfo.getNickName())){
            topicComment.setUserNickname("无名");
        }else {
            topicComment.setUserNickname(userInfo.getNickName());
        }
        topicComment.setLikeCnt(0);//初始化0
        topicComment.setParentId(0);//暂不实现
        int a = topicCommentMapper.insert(topicComment);  //添加评论表记录
        if (a > 0) {
            topic = new Topic();
            topic = topicMapper.selectByPrimaryKey(commentDto.getTopicId());
            topic.setCommentCnt(topic.getCommentCnt() + 1);                                    //话题表评论数+1
            int b = topicMapper.updateByPrimaryKeySelective(topic);
            if (b > 0) {
                result.setResultCode(Result.SUCCESS);
                result.setMsg("评论成功");
                if(topic.getType()!=3) {    //音频暂不实现
                    //消息推送
                    int action = Constants.TOPIC_USER_COMMENT;
                    Set<Integer> userIds = new HashSet<>(); //接收者
                    userIds.add(topic.getCreatorId());
                    int sender = userInfo.getUserId();    //发送者
                    MsgFromDto msgFromDto = new MsgFromDto();
                    msgFromDto.setUserId(userInfo.getUserId());
                    msgFromDto.setUserName(userInfo.getNickName());
                    msgFromDto.setTopicId(topic.getId());
                    msgFromDto.setIsPk(topic.getIsPk());
                    Gson gson = new Gson();
                    Map<String, Object> urlsMap = gson.fromJson(topic.getContent(), Map.class);
                    String imgUrl = "";
                    List<String> imgs = (List<String>) urlsMap.get("urls");
                    if (topic.getType() == 1) {
                        imgUrl = imgs.get(0);
                    } else if (topic.getType() == 2) {
                        imgUrl = imgs.get(1);
                    }
                    String topicName="";  //图文名称
                    if(topic.getIsPk()!=1){      //根据是否PK
                        if(topic.getType()==1){
                            topicName=Constants.MSG_TOPIC_TYPE_IMAGE;
                        }else{
                            topicName=Constants.MSG_TOPIC_TYPE_VIDEO;
                        }
                    }else{
                        topicName=topic.getSlogan();
                        msgFromDto.setExpireTime(topic.getExpireTime());
                    }
                    msgFromDto.setRightImgUrl(imgUrl);
                    msgFromDto.setTopicName(topicName);
                    msgFromDto.setTopicType(topic.getType());
                    msgPushService.sendPushMsgByUser(action, userIds, sender, msgFromDto);
                }
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("评论失败");
            }
        } else {
            result.setResultCode(Result.FAILURE);
            result.setMsg("评论失败");
        }
        return result;

    }

    /**
     * 获取评论列表
     * @param commentDto
     * @return
     */
    public Result<List<TopicCommentVo>> getCommentList(CommentDto commentDto){
        Result<List<TopicCommentVo>> result=new Result<>();
        Integer count=topicCommentMapper.selectCntByTopicId(commentDto.getTopicId());  //查询总条数
        if (!StringUtils.isEmpty(commentDto.getPage()) && !StringUtils.isEmpty(commentDto.getRows())) {
            PageHelper.startPage(commentDto.getPage(), commentDto.getRows());
        }
        List<TopicComment> topicCommentList=topicCommentMapper.selectByTopicId(commentDto.getTopicId());
        PageInfo<TopicComment> pageInfo=new PageInfo<>(topicCommentList);
        if(commentDto.getPage()>pageInfo.getPageNum()||topicCommentList.size()<=0){
            result.setResultCode(Constants.DATA_NO); //41 没有数据
            result.setMsg("没有数据");
            return result;
        }else{
            List<TopicCommentVo> topicCommentVoList = new ArrayList<>();
            for (TopicComment topicComment:topicCommentList) {
                TopicCommentVo topicCommentVo = new TopicCommentVo();
                BeanUtils.copyProperties(topicComment,topicCommentVo);
                topicCommentVo.setShowTime(topicCommentVo.getActionTime());
                topicCommentVo.setCommentCnt(count);  //设置总条数
                topicCommentVoList.add(topicCommentVo);
            }
            result.setObj(topicCommentVoList);
            result.setResultCode(Result.SUCCESS);
            return result;
        }
    }

    /**
     * 根据用户id查询粉丝列表
     * @param contextDto
     * @param userInfo
     * @return
     */
    public List<UserFanListVo> getFanList(ContextDto contextDto,UserInfo userInfo){
        if (!StringUtils.isEmpty(contextDto.getPage()) && !StringUtils.isEmpty(contextDto.getRows())) {
            PageHelper.startPage(contextDto.getPage(), contextDto.getRows());
        }
        List<UserFanListVo> userFanListVoList=userFansMapper.getFanListByUserId(userInfo.getUserId());
        return userFanListVoList;
    }

    /**
     * 根据用户id查询关注列表
     * @param contextDto
     * @param userInfo
     * @return
     */
    public List<UserFanListVo> getFollowList(ContextDto contextDto,UserInfo userInfo){
        if (!StringUtils.isEmpty(contextDto.getPage()) && !StringUtils.isEmpty(contextDto.getRows())) {
            PageHelper.startPage(contextDto.getPage(), contextDto.getRows());
        }
        List<UserFanListVo> userFanListVoList=userFansMapper.getFollowListByUserId(userInfo.getUserId());
        return userFanListVoList;
    }

    /**
     * 获取个人中心4个总数量（印记，收藏，关注，粉丝）
     * @param userInfo
     * @return
     */
    public UserFanCountsVo getUserFanCounts(UserInfo userInfo)
    {
        UserFanCountsVo userFanCountsVo=new UserFanCountsVo();
        if(StringUtils.isEmpty(userInfo)){
            return userFanCountsVo;
        }
        userFanCountsVo.setMyTopicCnt(topicMapper.selectCountByUserId(userInfo.getUserId())); //设置印记总数量
        userFanCountsVo.setMyFanCnt(userFansMapper.selectByUserId(userInfo.getUserId()));     //设置粉丝总数量
        userFanCountsVo.setMyFollowCnt(userFansMapper.selectByFanId(userInfo.getUserId()));    //设置关注总数量
        userFanCountsVo.setMyFavCnt(topicFavMapper.selectCountByUserId(userInfo.getUserId()));     //设置粉丝总数量
        return userFanCountsVo;
    }

    /**
     * app获取图片轮播图
     * @return
     */
    public List<BannerVo> getBannerImg(Integer bannerType){
        List<BannerVo> bannerVoList=new ArrayList<BannerVo>();
        List<BannerImg> bannerImgList= bannerImgMapper.selectBySort(bannerType);
        for (BannerImg bannerImg:bannerImgList) {
            BannerVo bannerVo=new BannerVo();
            BeanUtils.copyProperties(bannerImg,bannerVo);
            bannerVo.setUrlMap(bannerImg.getDescription());
            bannerVoList.add(bannerVo);
        }
        return bannerVoList;
    }


    /**
     * 后台获取轮播图列表（分页）
     * @param bannerImg
     * @return
     */
    public PageInfo<BannerVo> getBannerImgByPage(BannerImg bannerImg){
        if (!StringUtils.isEmpty(bannerImg.getPage()) && !StringUtils.isEmpty(bannerImg.getRows())) {
            PageHelper.startPage(bannerImg.getPage(), bannerImg.getRows());
        }
        List<BannerVo> bannerImgList= bannerImgMapper.selectBySort1(bannerImg); //待完善
        PageInfo<BannerVo> pageInfo = new PageInfo<>(bannerImgList);
        return pageInfo;
    }

    /**
     * 添加图片（推荐）
     * @param bannerImg
     * @return
     */
    public Boolean addBanner(BannerImg bannerImg){
        Boolean ishas=false;
        bannerImg.setCreateTime(new Date());
        if(bannerImg.getSort()!=0){
            BannerImgExample example = new BannerImgExample();
            BannerImgExample.Criteria c = example.createCriteria();
            c.andSortEqualTo(bannerImg.getSort());
            c.andBannerTypeEqualTo(bannerImg.getBannerType());
            List<BannerImg> list = bannerImgMapper.selectByExample(example);
            if (!list.isEmpty()) {   //查询当前推荐位是否存在  存在：修改存在的推荐位替换为被替换的推荐位
                BannerImg oldBanner1 = list.get(0);
                oldBanner1.setSort(0);
                bannerImgMapper.updateByPrimaryKeySelective(oldBanner1);  //替换之前的
            }
        }
        int a=bannerImgMapper.insertSelective(bannerImg);
        if(a>0){
            ishas=true;
        }
        return ishas;
    }

    /**
     * 修改图片（推荐）
     * @param bannerImg
     * @return
     */
    public Boolean updateBanner(BannerImg bannerImg){   // old修改前的信息 banner修改后的信息 old1替换的
        Boolean ishas=false;
        if(bannerImg.getSort()!=0) { //需要修改推荐位
            BannerImg oldBanner = bannerImgMapper.selectByPrimaryKey(bannerImg.getId());
            BannerImgExample example = new BannerImgExample();
            BannerImgExample.Criteria c = example.createCriteria();
            c.andSortEqualTo(bannerImg.getSort());
            c.andBannerTypeEqualTo(bannerImg.getBannerType());  //图片类型
            List<BannerImg> list = bannerImgMapper.selectByExample(example);
            if (!list.isEmpty()) {   //查询当前推荐位是否存在  存在：修改存在的推荐位替换为被替换的推荐位
                BannerImg oldBanner1 = list.get(0);
                oldBanner1.setSort(oldBanner.getSort());
                bannerImgMapper.updateByPrimaryKeySelective(oldBanner1);  //替换之前的
            }
        }
        int a=bannerImgMapper.updateByPrimaryKeySelective(bannerImg);  //修改推荐位
        if(a>0) {
            ishas = true;
        }
        return ishas;
    }

    /**
     * 停用启用图片
     * @param bannerImg
     * @return
     */
    public Boolean availableBanner(BannerImg bannerImg){

        Boolean ishas=false;
        int a=bannerImgMapper.updateByPrimaryKeySelective(bannerImg);
        if(a>0){
            ishas=true;
        }
        return ishas;
    }

    /**
     * 删除图片
     * @param bannerImg
     * @return
     */
    public Boolean delBanner(BannerImg bannerImg){

        Boolean ishas=false;
        if(!StringUtils.isEmpty(bannerImg)){
            int a=bannerImgMapper.deleteByPrimaryKey(bannerImg.getId());
            if(a>0){
                ishas=true;
            }
        }
        return ishas;
    }

    /**
     * 根据图片id查询图片详情
     * @param id
     * @return
     */
    public BannerImg getBannerById(Integer id){
        return bannerImgMapper.selectByPrimaryKey(id);
    }

    /**
     * 点击音频增加播放量
     * @param topicId
     * @return
     */
    public boolean updatePlayCntByTopicId(Integer topicId){
        boolean ishas=false;
        int a=topicPlayMapper.updatePlayCntByTopicId(topicId);
        if(a>0){
            ishas=true;
        }
        return ishas;
    }

}
