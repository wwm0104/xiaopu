package com.chinaxiaopu.xiaopuMobi.controller.admin.topics;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.CreatTopicDto;
import com.chinaxiaopu.xiaopuMobi.dto.MyTopicParam;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.ChannelListDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.PkChannelDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicIndexDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.topics.TopicsListDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.AllChallengeDto;
import com.chinaxiaopu.xiaopuMobi.dto.topic.BannerDto;
import com.chinaxiaopu.xiaopuMobi.mapper.EventMapper;
import com.chinaxiaopu.xiaopuMobi.model.*;
import com.chinaxiaopu.xiaopuMobi.service.admin.topics.AdminTopickCreateService;
import com.chinaxiaopu.xiaopuMobi.service.admin.topics.TopicsService;
import com.chinaxiaopu.xiaopuMobi.service.topic.EventRankService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicDownService;
import com.chinaxiaopu.xiaopuMobi.service.topic.TopicsCreateService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.admin.topics.*;
import com.chinaxiaopu.xiaopuMobi.vo.topic.AllTopickInSamePkVo;
import com.chinaxiaopu.xiaopuMobi.vo.topic.BannerVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 内容管理
 * Created by Administrator on 2016/11/7.
 */
@Slf4j
@Controller
@RequestMapping("/admin/topics")
public class TopicsConttroller extends ContextController {
    @Autowired
    private TopicsService topicsService;

    @Autowired
    private TopicsCreateService topicsCreateService;

    @Autowired
    private AdminTopickCreateService adminTopickCreateService;

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private TopicDownService topicDownService;

    /**
     * topicCreate
     */
    @RequestMapping(value = "/toCreate", method = RequestMethod.GET)
    public String toCreate(Model model) {
        try {
            List<PkChannel> channelsList = topicsCreateService.selectOfficalPkChannel();
            MyTopicParam myTopicParam = new MyTopicParam();
            List<Prize> prizeTypeList = topicsCreateService.getPrizesType(myTopicParam);
            List<AdminPrizeVo> finList = new ArrayList<AdminPrizeVo>();
            for (Prize p : prizeTypeList) {
                AdminPrizeVo prizeVo = new AdminPrizeVo();
                BeanUtils.copyProperties(p, prizeVo);
                if (p.getType() == 1) {
                    prizeVo.setTypeName("现金");
                }
                if (p.getType() == 2) {
                    prizeVo.setTypeName("实物");
                }
                if (p.getType() == 3) {
                    prizeVo.setTypeName("虚礼物品");
                }
                if (p.getType() == 4) {
                    prizeVo.setTypeName("校谱红包");
                }
            }
            List<Event> list = eventMapper.selectAllEvent();
            model.addAttribute("eventList", list);
            model.addAttribute("channelsList", channelsList);
            model.addAttribute("prizeTypeList", prizeTypeList);
        } catch (Exception e) {
            log.error("查询頻道列表失败", e);
        }
        return "admin/topic/createOfficialTopick";
    }

    /**
     * 跳转到图文列表
     *
     * @return
     */
    @RequestMapping(value = "/toList", method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            List<PkChannel> channelsList = topicsCreateService.selectOfficalPkChannel();
            model.addAttribute("channelsList", channelsList);
        } catch (Exception e) {
            log.error("查询学校列表失败", e);
        }
        return "topics/topicsList";
    }

    /**
     * 查询 图文视频列表
     *
     * @param topicsListDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<TopicsListDto>> groupList(@RequestBody TopicsListDto topicsListDto) {
        Result<PageInfo<TopicsListDto>> result = new Result<PageInfo<TopicsListDto>>();
        try {
            String startTime = "";
            String endTime = "";
            if (StrUtils.isNotEmpty(topicsListDto.getTakeTime())) {
                String time = topicsListDto.getTakeTime();
                startTime = time.split("/")[0];
                endTime = time.split("/")[1];
                topicsListDto.setStartTime(startTime);
                topicsListDto.setEndTime(endTime);
            }
            result.setObj(topicsService.selectTopicsList(topicsListDto));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 查询 图文视频列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping(value = "/adminCreate", method = RequestMethod.POST)
    @ResponseBody
    public Result create(@RequestBody CreatTopicDto creatTopicDto) {
        Result result = new Result();
        try {
            log.debug("POST请求创建我的图文");
            creatTopicDto.setUserId(0);
            creatTopicDto.setCreatorNickname("校谱官方");
            creatTopicDto.setCreatorId(0);
            creatTopicDto.setCreatorAvatar("avatar.png");
            adminTopickCreateService.createPkTopic(creatTopicDto);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("创建图文失败:" + e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查询 频道列表
     *
     * @return
     */
    @RequestMapping(value = "/channelList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<ChannelListDto>> channelList(@RequestBody  ChannelListDto channelListDto) {
        Result<PageInfo<ChannelListDto>> result = new Result<>();
        try {
            result.setObj(topicsService.channelList(channelListDto));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 查询 频道列表失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 添加 频道
     *
     * @return
     */
    @RequestMapping(value = "/saveChannel", method = RequestMethod.POST)
    @ResponseBody
    public Result<PkChannel> saveChannel(@RequestBody PkChannelDto pkChannelDto) {
        Result result = new Result<>();
        PkChannel pk = new PkChannel();
        try {
            pk.setCreateId(getCurrentId());
            pk.setCreateRealname(getCurrentName());
            pk.setName(pkChannelDto.getName());
            pk.setDesc(pkChannelDto.getName());
            pk.setSlogan(pkChannelDto.getName());
            pk.setPosterImg(pkChannelDto.getImages());
            pk.setIsOfficial(pkChannelDto.getIsOfficial());
            if (StrUtils.isNotEmpty(pkChannelDto.getId())) {
                pk.setId(pkChannelDto.getId());
            }
            int row = topicsService.saveChannel(pk);

            if (row > 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 添加 频道失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;

    }


    @RequestMapping(value = "/goAllChanllengeList/{topicId}", method = RequestMethod.GET)
    public String toAllChanllengeList(@PathVariable("topicId") final int topicId, Model model) {
        try {
            Map<String, Object> map = topicsService.getPkLeizhu(topicId);
            String faqiName = (String) map.get("name");
            Date endTime = (Date) map.get("endTime");
            int joinCnt = (int) map.get("joinCnt");
            String status = "";
            if (endTime.getTime() >= new Date().getTime()) {
                status = "进行中";
            } else {
                status = "已结束";
            }
            model.addAttribute("joinCnt", joinCnt);
            model.addAttribute("topicId", topicId);
            model.addAttribute("faqiName", faqiName);
            model.addAttribute("status", status);

        } catch (Exception e) {
            log.error("获取擂主贴失败", e);
        }
        return "topics/AllChallengeList";
    }

    /**
     * 查询所有来战者
     *
     * @return
     */
    @RequestMapping(value = "/AllChanllengeList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<AllTopickInSamePkVo>> queryAllChanllenge(@RequestBody AllChallengeDto allChallengeDto, Model model) {
        Result result = new Result();
        try {
            log.debug("POST请求所有来战者");
            PageInfo<AllTopickInSamePkVo> pageInfo = topicsService.getAllTopickInSamePkVo(allChallengeDto);
            result.setResultCode(Result.SUCCESS);
            result.setObj(pageInfo);
        } catch (Exception e) {
            log.error("POST请求所有来战者失败:" + e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 修改推荐值
     *
     * @param topic
     * @return
     */
    @RequestMapping(value = "/updateRecoment", method = RequestMethod.POST)
    @ResponseBody
    public Result<Topic> updateRecoment(@RequestBody Topic topic) {
        Result result = new Result<>();
        try {
            int row = topicsService.updateRecoment(topic);

            if (row > 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 添加 频道失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;

    }


    /**
     * 查询所有来战者
     *
     * @return
     */
    @RequestMapping(value = "/deleteTopic", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAllChanllenge(@RequestBody Topic topic, Model model) {
        Result result = new Result();
        try {
            log.debug("POST请求删除topic");
            topicsService.deleteTopic(topic);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求删除topic失败:" + e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 查看主题详情
     *
     * @return
     */
    @RequestMapping(value = "/selectTopicsDetail/{topicId}", method = RequestMethod.GET)
    public String selectTopicsDetail(@PathVariable("topicId") final Integer topicId, Model model) {
        try {
            TopicDetaVo vo = topicsService.selectTopicsDetail(topicId);

            TopicDetaVo e = topicsService.selectTopicsDetail1(topicId);

            List<TipoffVo> tipoffLsit = topicsService.selectTipOffList(topicId);  //投诉列表
            List<TopicComment> commentList = topicsService.selectCommentList(topicId); //评论列表

            if (StrUtils.isNotEmpty(vo)) {
                vo.setContentMap(vo.getContent());
                if (vo.getIsOfficial() == 1) {
                    vo.setRealName("校普官方");
                }
            }
            if (StrUtils.isNotEmpty(e)) {
                if (StrUtils.isNotEmpty(e.getPrize())) {
                    e.setContentMap(e.getPrize());
                }
                if (StrUtils.isNotEmpty(e.getContentMap())) {
                    e.setRewardMap(StrUtils.emptyOrString(e.getContentMap().get("rewardRule")));
                    int challengeCnt = (int) StrUtils.str2Double(StrUtils.emptyOrString(e.getRewardMap().get("challengeCnt")));
                    int voteCnt = (int) StrUtils.str2Double(StrUtils.emptyOrString(e.getRewardMap().get("voteCnt")));
                    if (challengeCnt <= e.getChallenteCount() && voteCnt <= e.getVoteCnt()) {
                        e.setIsFinish(1);
                    } else {
                        e.setIsFinish(0);
                    }
                    e.setChallengeCnt(challengeCnt);
                    e.setVCote(voteCnt);
                    e.setDesc(StrUtils.emptyOrString(e.getContentMap().get("desc")));
                }
            }
            vo.setPath(imagesPath());
            model.addAttribute("vo", vo);
            model.addAttribute("e", e);
            model.addAttribute("tipoffLsit", tipoffLsit);
            model.addAttribute("commentList", commentList);
        } catch (Exception e) {
            log.error("获取主题详情失败", e);
        }
        return "topics/topicsDetail";
    }


    /**
     * 删除 评论
     *
     * @return
     */
    @RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteComment(@RequestBody TopicComment tc, Model model) {
        Result result = new Result();
        try {
            log.debug("POST请求删除评论");
            int row = topicsService.deleteComment(tc);
            if (row > 0) {

                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("POST请求删除评论失败:" + e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 不显示  处理
     *
     * @param tc
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateComment", method = RequestMethod.POST)
    @ResponseBody
    public Result updateComment(@RequestBody TopicComment tc, Model model) {
        Result result = new Result();
        try {
            log.debug("POST请求不显示");
            int row = topicsService.updateComment(tc);
            if (row > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } catch (Exception e) {
            log.error("POST请求不显示失败:" + e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 删除 投诉内容
     *
     * @param tc
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteTipOff", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTipOff(@RequestBody Tipoff tc, Model model) {
        Result result = new Result();
        try {
            log.debug("删除 投诉内容");
            int row = topicsService.deleteTipOff(tc);
            if (row > 0) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
        } catch (Exception e) {
            log.error("删除 投诉内容失败:" + e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    @RequestMapping("/toBannerList")
    public String bannerList() {
        return "topics/bannerList";
    }

    @RequestMapping("/toAddBanner")
    public String toAddBanner() {
        return "topics/bannerCreate";
    }

    /**
     * 获取轮播图列表（分页）
     *
     * @param bannerImg
     * @return
     */
    @RequestMapping(value = "/getBannerList", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<BannerVo>> getBannerList(@RequestBody BannerImg bannerImg) {
        Result result = new Result();
        try {
            result.setObj(topicDownService.getBannerImgByPage(bannerImg));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error("POST请求 获取图片列表失败", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }
        return result;
    }

    /**
     * 根据id查询banner图信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/getBannerById/{id}")
    public String getBannerById(@PathVariable("id") final Integer id, Model model) {
        BannerImg bannerImg = topicDownService.getBannerById(id);
        model.addAttribute("b", bannerImg);
        return "topics/bannerUpdate";
    }

    /**
     * 图片修改
     *
     * @param bannerImg
     * @return
     */
    @RequestMapping(value = "/updateBanner", method = RequestMethod.POST)
    @ResponseBody
    public Result updateBanner(@RequestBody BannerImg bannerImg) {
        Result result = new Result();
        try {
            boolean ishas = topicDownService.updateBanner(bannerImg);
            if (ishas) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg(Result.SERVER_EXCEPTION);
            }
        } catch (Exception e) {
            log.error("POST请求 修改轮播图", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }

        return result;
    }

    /**
     * 图片停用启用
     *
     * @param bannerImg
     * @return
     */
    @RequestMapping(value = "/availableBanner", method = RequestMethod.POST)
    @ResponseBody
    public Result availableBanner(@RequestBody BannerImg bannerImg) {
        Result result = new Result();
        try {
            boolean ishas = topicDownService.availableBanner(bannerImg);
            if (ishas) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("修改失败");
            }
        } catch (Exception e) {
            log.error("POST请求 修改轮播图", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }

        return result;
    }

    @RequestMapping(value = "/delBanner", method = RequestMethod.POST)
    @ResponseBody
    public Result delBanner(@RequestBody BannerImg bannerImg) {
        Result result = new Result();
        try {
            boolean ishas = topicDownService.delBanner(bannerImg);
            if (ishas) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("删除失败");
            }
        } catch (Exception e) {
            log.error("POST请求 修改轮播图", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }

        return result;
    }

    /**
     * 图片添加
     *
     * @param bannerDto
     * @return
     */
    @RequestMapping(value = "/addBanner", method = RequestMethod.POST)
    @ResponseBody
    public Result addBanner(@RequestBody BannerDto bannerDto) {
        Result result = new Result();
        try {
            BannerImg bannerImg = new BannerImg();
            BeanUtils.copyProperties(bannerDto, bannerImg);
            boolean ishas = topicDownService.addBanner(bannerImg);
            if (ishas) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
                result.setMsg("添加成功");
            }
        } catch (Exception e) {
            log.error("POST请求 添加轮播图", e);
            result.setResultCode(Result.FAILURE);
            result.setMsg(Result.SERVER_EXCEPTION);
        }

        return result;
    }

    @Autowired
    private EventRankService eventRankService;

    /**
     * 手动运行定时器
     *
     * @return
     */
    @RequestMapping(value = "/quartz", method = RequestMethod.GET)
    public String Quartz() {
        eventRankService.eventDay();
        eventRankService.eventWeek();
        eventRankService.eventMonth();

        eventRankService.votesDay();
        eventRankService.votesWeek();
        eventRankService.votesMonth();

        eventRankService.userDaren();

        eventRankService.userBufu();

        eventRankService.VotesPool();

        return "";
    }


    /**
     * 删除频道
     * 如果主题表中有频道 —— 提示删除主题
     *
     * @return
     */
    @RequestMapping(value = "/deleteChannel", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteChannel(@RequestBody PkChannelDto pkChannelDto) {
        Result result = new Result<>();
        PkChannel pk = new PkChannel();
        try {
            pk.setId(pkChannelDto.getId());
            /**
             * 判断 当前频道下 主题数
             */
            int row = topicsService.deleteChannel(pk);
            if (row == 1) {//删除成功
                result.setResultCode(Result.SUCCESS);
            } else if (row == 2) {
                result.setResultCode(2);
            }
            return result;
        } catch (Exception e) {
            log.error(" 删除 频道失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;

    }
}
