package com.chinaxiaopu.xiaopuMobi.controller.topic;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.constant.Constants;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.*;
import com.chinaxiaopu.xiaopuMobi.dto.topic.*;
import com.chinaxiaopu.xiaopuMobi.exception.UnknownLoginException;
import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import com.chinaxiaopu.xiaopuMobi.model.Prize;
import com.chinaxiaopu.xiaopuMobi.model.UserInfo;
import com.chinaxiaopu.xiaopuMobi.service.topic.*;
import com.chinaxiaopu.xiaopuMobi.util.DateTimeUtil;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.topic.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Wang on 2016/11/1.
 */

@Slf4j
@RestController
@RequestMapping("/topic")
public class TopicController extends ContextController {
    @Autowired
    private TopicsCreateService topicsCreateService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicSearchService topicSearchService;
    @Autowired
    private TopicDownService topicDownService;
    @Autowired
    private PrizeService prizeService;

    @ApiOperation(value = "最新的图文内容列表--Wang", notes = "获取最新的图文内容列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/newList", method = RequestMethod.POST)
    public Result<TopicListVos> newList(@RequestBody TopicListDtos topicListDtos) {
        Result<TopicListVos> result = new Result<>();
        try {
            log.debug("POST请求 最新的图文内容列表");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(topicListDtos);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }

            PageInfo<TopicVo> pageInfo = topicService.newList(topicListDtos, userInfo);
            if (topicListDtos.getPage() > pageInfo.getPages()) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                TopicListVos topicListVo = new TopicListVos();
                topicListVo.setList(pageInfo.getList());
                topicListVo.setTimePoint(topicListDtos.getTimePoint());
                result.setObj(topicListVo);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("POST请求  最新的图文内容列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "最新的图文内容--Wang", notes = "获取最新的图文内容\n" +
            "返回值：-1代表未登录，0代表失败，1代表成功")
    @RequestMapping(value = "/latest", method = RequestMethod.POST)
    public Result<TopicVo> latest(@RequestBody TopicIdDto topicIdDto) {
        Result<TopicVo> result = new Result<>();
        try {
            log.debug("POST请求 最新的图文内容");
            UserInfo userInfo = getCurrentUser(topicIdDto);
            TopicVo topicVo = topicService.latest(topicIdDto, userInfo);
            result.setObj(topicVo);
            result.setResultCode(Result.SUCCESS);
        } catch (UnknownLoginException ue) {
            log.error("最新的图文内容->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
            result.setMsg(Result.NOT_LOGGED_IN);
        } catch (Exception e) {
            log.error("POST请求  最新的图文内容列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "根据奖品id查询图文内容列表--王运豪", notes = "根据奖品id查询图文内容列表\n" +
            "接收值：prizeId奖品id（必填）+page+rows\n" +
            "orderType(1:参与人数升序 2:参与人数降序 3:截止时间升序 4:截止时间降序 其他：默认排序)\n"+
            "topicType(1:图文；2:视频 其他：全部)\n"+
            "isOver( 1:未结束 2:结束 其他：全部)\n"+
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/topicListByPrizeId",method = RequestMethod.POST)
    public Result<TopicListAndPrizeVo> topicListByPrizeId(@RequestBody TopicByPrizeDto topicByPrizeDto)
    {
        Result<TopicListAndPrizeVo> result = new Result<>();
        log.debug("POST请求 根据奖品id查询图文内容列表");
        try {
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(topicByPrizeDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            if(StringUtils.isEmpty(topicByPrizeDto.getPrizeId())){
                result.setMsg("奖品id不能为空！");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            PageInfo<TopicVo> pageInfo = topicService.topicListByPrizeId(topicByPrizeDto, userInfo);//获取图文列表
            //PrizeVo prizeVo= prizeService.getPrizeDetailById(topicByPrizeDto.getPrizeId()); //获取奖品详情
            if (topicByPrizeDto.getPage() > pageInfo.getPages() || pageInfo.getList().size()<=0) {
                result.setMsg(Result.NO_DATA);
                result.setResultCode(Constants.DATA_NO);
            } else {
                TopicListAndPrizeVo topicListAndPrizeVo=new TopicListAndPrizeVo();
                topicListAndPrizeVo.setList(pageInfo.getList());
                //topicListAndPrizeVo.setPrizeVo(prizeVo);
                result.setObj(topicListAndPrizeVo);
                result.setResultCode(Result.SUCCESS);
            }
        }catch (Exception e){
            log.error("POST请求  根据奖品id查询图文内容列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "图文内容点赞-王运豪", notes = "图文内容点赞\n" +
            "接收值：topicId+登录凭证\t" + "返回值：0代表失败，1代表成功，-1没有登录")
    @RequestMapping(value = "/authApi/like", method = RequestMethod.POST)
    public Result like(@RequestBody LikeDto likeDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 请求点赞操作");
            //获取登录用户信息
            UserInfo userInfo = getCurrentUser(likeDto);
            //点赞操作
            return topicDownService.doLike(likeDto, userInfo);
        } catch (UnknownLoginException ue) {
            log.error("用户点赞失败->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
            result.setMsg(Result.NOT_LOGGED_IN);
        } catch (Exception e) {
            log.error("POST请求 用户点赞失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "图文内容收藏-王运豪", notes = "图文内容收藏\n" +
            "接收值：topicId+登录凭证\t" + "返回值：0代表失败，1代表成功，-1没有登录")
    @RequestMapping(value = "/authApi/fav", method = RequestMethod.POST)
    public Result fav(@RequestBody FavDto favDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 收藏操作");
            //获取登录用户信息

            UserInfo userInfo = getCurrentUser(favDto);
            //收藏操作
            return topicDownService.doFav(favDto, userInfo);
        } catch (UnknownLoginException ue) {
            log.error("话题收藏失败->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1:未登录
            result.setMsg(Result.NOT_LOGGED_IN);
        } catch (Exception e) {
            log.error("POST请求 话题收藏失败", e);
            result.setResultCode(Result.FAILURE); //0:失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "图文内容举报-王运豪", notes = "图文内容举报\n" +
            "接收值：topicId+举报内容desc+登录凭证\t" + "返回值：0代表失败，1代表成功，-1没有登录")
    @RequestMapping(value = "/authApi/tipoff", method = RequestMethod.POST)
    public Result tipoff(@RequestBody TipoffDto tipoffDto) {
        Result result = new Result();
        try {
            log.debug("POST请求 举报操作");
            //获取登录用户信息
            UserInfo userInfo = getCurrentUser(tipoffDto);
            //举报操作
            return topicDownService.doTipoff(tipoffDto, userInfo);
        } catch (UnknownLoginException ue) {
            log.error("用户举报失败->用户未登录");
            result.setResultCode(Constants.UNKNOWN_LOGIN);//-1:未登录
            result.setMsg(Result.NOT_LOGGED_IN);
        } catch (Exception e) {
            log.error("POST请求 用户举报失败", e);
            result.setResultCode(Result.FAILURE); //0:失败
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "最热门的图文内容列表--Wang", notes = "最热门的图文内容列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/hotList", method = RequestMethod.POST)
    public Result<List<TopicVo>> hotList(@RequestBody ContextDto contextDto) {
        Result<List<TopicVo>> result = new Result<>();
        try {
            log.debug("POST请求 最热门的图文内容列表");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(contextDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }

            PageInfo<TopicVo> pageInfo = topicService.hotList(contextDto, userInfo);
            if (contextDto.getPage() > pageInfo.getPages()) {
                result.setResultCode(Constants.DATA_NO);
                result.setMsg("没有数据");
            } else {
                result.setObj(pageInfo.getList());
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  最热门的图文内容列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "图文内容获取详情--Wang", notes = "图文内容获取详情\n" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public Result<TopicVo> view(@RequestBody TopicIdDto topicIdDto) {
        Result<TopicVo> result = new Result<>();
        try {
            log.debug("POST请求 图文内容获取详情");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(topicIdDto);
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            TopicVo topicVo = topicService.view(topicIdDto.getTopicId(), userInfo);
            result.setObj(topicVo);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求  图文内容获取详情失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "根据图文标题搜索列表 -武宁", notes = "根据图文标题搜索列表\n  参数  （slogan：搜索的内容）（分页查询）  " +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
    public Result<List<TopicSearchVo>> searchList(@RequestBody TopicSearchDto topicSearchDto) {
        Result<List<TopicSearchVo>> result = new Result<>();
        try {
            log.debug("POST请求 根据图文标题搜索列表");
            UserInfo userInfo;
            try {
                userInfo = getCurrentUser(topicSearchDto); //获取当前登录人信息
            } catch (UnknownLoginException ule) {
                userInfo = null;
            }
            if (!StringUtils.isEmpty(userInfo)) {
                topicSearchDto.setUserId(userInfo.getUserId());
            } else {
                topicSearchDto.setUserId(-1); //如果未登陆 赋值-1
            }
            if (StrUtils.isEmpty(topicSearchDto.getTimePoint())) {
                topicSearchDto.setTimePoint(DateTimeUtil.getOneDayStart(DateTimeUtil.DATE_TIME_Sec));
            }
            List<TopicSearchVo> list = topicSearchService.getSearchList(topicSearchDto);
            PageInfo<TopicSearchVo> pageInfo = new PageInfo<>(list);
            if (topicSearchDto.getPage() > pageInfo.getPages()) {
                result.setResultCode(Constants.DATA_NO);
            } else {
                result.setObj(list);
                result.setResultCode(Result.SUCCESS);
            }
            return result;
        } catch (Exception e) {
            log.error("POST请求  根据图文标题搜索列表", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    @ApiOperation(value = "发布图文视频上下文-乐传阳", notes = "发布图文视频上下文\n" +
            "返回值：0代表失败，1代表成功" +
            "prizeTypeList中取Type属性即可1:现金；2:实物；3:虚拟物品；4:校谱红包" +
            "channelList中取出Id以及名称")
    @RequestMapping(value = "/authApi/createContext", method = RequestMethod.POST)
    public Result createContext() {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> map = new HashMap();
        try {
            List<PkChannel> channelList = topicsCreateService.getPkChannelList();
            MyTopicParam myTopicParam = new MyTopicParam();
            List<Prize> prizeTypeList = topicsCreateService.getPrizesType(myTopicParam);
            map.put("channelList", channelList);
            map.put("prizeTypeList", prizeTypeList);
            result.setObj(map);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("请求PK频道列表及奖励类型失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "发布图文视频-乐传阳", notes = "发布图文视频\n" + "参数说明：eventList-活动标签列表，groupList-活动列表标签，" +
            "periodType-PK时间类型1:当天；2:当周；3:当月；4:当季；5当年；0：自定义（目前需要大于12小时）," +
            "prizeId-奖品id，rewardType-奖励方式 1:最高者得，rule-判定规则（能否获奖），另根据帖子类型不同传入参数不同：" +
            "（普通贴：传入帖子基本信息 其中isPk：0、isChallenger：-1）（PK擂主贴：传入基本信息其中isPK：1、isChallenger：1，另传入以上定义字段）" +
            "（PK挑战者贴：传入基本信息其中isPK：1、iChallenger：0，另传入challengeTopicId 擂主贴id，challengeId 擂主id，challengeNickname 擂主昵称，" +
            "challengeAvatar 擂主头像，channelId 频道id，channelName频道名称）" +
            "返回值：0代表失败，1代表成功")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result create(@RequestBody CreatTopicDto creatTopicDto) {
        Result result = new Result();
        try {
            log.debug("POST请求创建我的图文");
            UserInfo current = getCurrentUser(creatTopicDto);
            creatTopicDto.setUserId(current.getUserId());
            creatTopicDto.setCreatorNickname(current.getNickName());
            creatTopicDto.setCreatorId(current.getUserId());
            creatTopicDto.setCreatorAvatar(current.getAvatarUrl());

            if (creatTopicDto.getIsPk().equals(1) && creatTopicDto.getIsChallenger().equals(1)) {
                if (!creatTopicDto.getPeriodType().equals(1) && !creatTopicDto.getPeriodType().equals(2) && !creatTopicDto.getPeriodType().equals(3)) {
                    result.setMsg("未选择时间类型");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
            }

            if (topicsCreateService.checkUrls(creatTopicDto.getContent()) == false) {
                result.setMsg("图片或视频不能为空");
                result.setResultCode(Result.FAILURE);
                return result;
            }
            if (creatTopicDto.getIsChallenger() == 0) {
                int checkPk = topicsCreateService.checkPk(creatTopicDto);
                if (checkPk == 5) {
                    result.setMsg("用户不可以挑战自己");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
                if (checkPk == 6) {
                    result.setMsg("同一用户不可挑战2次");
                    result.setResultCode(Result.FAILURE);
                    return result;
                }
            }
            topicsCreateService.createTopic(creatTopicDto);
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (UnknownLoginException ue) {
            result.setMsg(Result.NOT_LOGGED_IN);
            log.error("我的图文列表->用户未登录:");
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            result.setMsg(Result.SERVER_EXCEPTION);
            log.error("用户请求 创建图文失败:", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "判断是否能发起挑战--Wang", notes = "判断是否能发起挑战\n" +
            "返回值：-1代表未登录，0代表失败，1代表成功，44代表已加入此挑战")
    @RequestMapping(value = "/authApi/isAttend", method = RequestMethod.POST)
    public Result<TopicVo> isAttend(@RequestBody PkIdDto pkIdDto) {
        Result<TopicVo> result = new Result<>();
        try {
            log.debug("POST请求 判断是否能发起挑战");
            UserInfo userInfo = getCurrentUser(pkIdDto);
            Boolean flag = topicService.isAttend(pkIdDto.getPkId(), userInfo);
            if (flag) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setMsg("已加入此PK");
                result.setResultCode(Constants.CHALLENGE_NAME_EXIST);
            }
        } catch (UnknownLoginException ue) {
            log.error("判断是否能发起挑战->用户未登录:");
            result.setMsg(Result.NOT_LOGGED_IN);
            result.setResultCode(Constants.UNKNOWN_LOGIN);
        } catch (Exception e) {
            log.error("POST请求  判断是否能发起挑战失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @ApiOperation(value = "获取发现轮播图列表-王运豪", notes = "获取发现轮播图列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/bannerList", method = RequestMethod.POST)
    public Result<List<BannerVo>> getBannerImg() {
        Result<List<BannerVo>> result = new Result<List<BannerVo>>();
        try {
            log.debug("POST请求 获取发现轮播图列表");
            List<BannerVo> imgList = topicDownService.getBannerImg(1);  //1代表发现轮播
            if (imgList.size() <= 0) {
                result.setResultCode(Constants.DATA_NO); //41 没有数据
                result.setMsg(Result.NO_DATA);
                return result;
            } else {
                result.setObj(imgList);
                result.setResultCode(Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error("POST请求  获取发现轮播图列表", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;

    }

//    @ApiOperation(value = "获取音频轮播图列表-王运豪", notes = "获取音频轮播图列表\n" +
//            "返回值：0代表失败，1代表成功，41代表没有数据")
//    @RequestMapping(value = "/bannerAudioList", method = RequestMethod.POST)
//    public Result<List<BannerVo>> getAudioBannerImg() {
//        Result<List<BannerVo>> result = new Result<List<BannerVo>>();
//        try {
//            log.debug("POST请求 获取音频轮播图列表");
//            List<BannerVo> imgList = topicDownService.getBannerImg(2);  //2代表音频轮播
//            if (imgList.size() <= 0) {
//                result.setResultCode(Constants.DATA_NO); //41 没有数据
//                result.setMsg(Result.NO_DATA);
//                return result;
//            } else {
//                result.setObj(imgList);
//                result.setResultCode(Result.SUCCESS);
//            }
//        } catch (Exception e) {
//            log.error("POST请求  获取音频轮播图列表", e);
//            result.setResultCode(Result.FAILURE);
//            result.setMsg(Result.SERVER_EXCEPTION);
//        }
//        return result;
//    }
    @ApiOperation(value = "获取首页主题推荐-武宁", notes = "获取首页推荐列表\n" +
            "返回值：0代表失败，1代表成功，41代表没有数据")
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public Result<List<TopicIndexVo>> index() {
        Result<List<TopicIndexVo>> result = new Result<>();
        try {
            log.debug("POST请求 获取首页推荐列表");
            List<TopicIndexVo> indexList = topicService.getIndexList();
            result.setObj(indexList);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求  获取首页推荐列表", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;

    }

}