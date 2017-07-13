package com.chinaxiaopu.xiaopuMobi.controller.admin.channel;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.controller.ContextController;
import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelListDto;
import com.chinaxiaopu.xiaopuMobi.dto.admin.channel.ChannelRecommend;
import com.chinaxiaopu.xiaopuMobi.model.Channel;
import com.chinaxiaopu.xiaopuMobi.model.ChannelAssociated;
import com.chinaxiaopu.xiaopuMobi.model.PkChannel;
import com.chinaxiaopu.xiaopuMobi.service.admin.channel.ChannelService;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.admin.channel.ChannelListVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.channel.ChannelsVo;
import com.chinaxiaopu.xiaopuMobi.vo.admin.channel.SortVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 频道管理Controller
 * Created by 武宁 on 2016/12/1.
 */
@Slf4j
@Controller
@RequestMapping("/admin/channel")
public class ChannelsController extends ContextController {

    @Autowired
    private ChannelService channelService;
    private static final String TOCHANNELLIST = "admin/channel/channelList";//跳转频道主表列表
    private static final String TOCHANNELFORM = "admin/channel/channelForm";//跳转到频道编辑页面


    private static final String TOCHLIST = "admin/channel/chanList";//跳转频道管理列表
    private static final String TOCHFORM = "admin/channel/chanForm";//跳转到频道编辑页面

    /**
     * 武宁
     * 跳转到频道字典管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toList", method = RequestMethod.GET)
    public String toList(Model model) {
        return TOCHANNELLIST;
    }

    /**
     * 武宁
     * 跳转到频道管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/toChannelList", method = RequestMethod.GET)
    public String toChannelList(Model model) {
        return TOCHLIST;
    }


    /**
     * 武宁
     * 查询频道字典列表
     *
     * @param channelListDto
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Result<PageInfo<ChannelListVo>> list(@RequestBody ChannelListDto channelListDto) {
        Result<PageInfo<ChannelListVo>> result = new Result<>();
        try {
            result.setObj(channelService.list(channelListDto));
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 查询频道字典列表 失败", e);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 更新 频道字典 排序
     *
     * @param sortVo
     * @return
     */
    @RequestMapping(value = "/updateSort", method = RequestMethod.POST)
    @ResponseBody
    public Result updateSort(@RequestBody SortVo sortVo) {
        Result result = new Result<>();
        try {
            int row = channelService.updateSort(sortVo);

            if (row > 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 更新 频道字典 排序  失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;

    }

    /**
     * 删除  频道主表  和中间表信息
     *
     * @param channel
     * @return
     */
    @RequestMapping(value = "/deleteChl", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteChl(@RequestBody Channel channel) {
        Result result = new Result<>();
        try {
            int row = channelService.deleteChl(channel);
            if (row > 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 频道主表  和中间表信息 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }

    /**
     * 删除  中间表信息
     *
     * @param ChannelAssociated
     * @return
     */
    @RequestMapping(value = "/deleteChildChl", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteChildChl(@RequestBody ChannelAssociated ChannelAssociated) {
        Result result = new Result<>();
        try {
            int row = channelService.deleteChildChl(ChannelAssociated);
            if (row > 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 中间表信息 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


    /**
     * 武宁
     * 跳转添加页面
     *
     * @return
     */
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(Channel channel, Model model) throws Exception {
        Channel e = new Channel();
        List<PkChannel> allChannelList = null;
        List<PkChannel> userChannelList = null;
       /* List<ChannelAssociated> caList = new ArrayList<>();*/
        if (StrUtils.isNotEmpty(channel.getId())) {
            e = channelService.selectOneById(channel);
           /* caList = channelService.selectChildList(channel);*/
            // 查询所有未添加的
            allChannelList =channelService.selectAllChannelList(channel.getId());
            //查询所有已添加的
            userChannelList=channelService.selectUserChannelList(channel.getId());
        }else{
            allChannelList = channelService.selectPkChannelList();
        }
      /*  List<PkChannel> pkChannelList = channelService.selectPkChannelList();*/
        model.addAttribute("allChannelList",allChannelList);
        model.addAttribute("userChannelList",userChannelList);
        model.addAttribute("e", e);
       /* model.addAttribute("caList", caList);*/
        model.addAttribute("path", getImgsHostDomain());
       /* model.addAttribute("pkChannelList", pkChannelList);*/
        return TOCHANNELFORM;
    }

    /**
     * 编辑  频道信息
     *
     * @param pkChannel
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/chanForm", method = RequestMethod.GET)
    public String chanForm(PkChannel pkChannel, Model model) throws Exception {
        PkChannel e = new PkChannel();
        if (StrUtils.isNotEmpty(pkChannel.getId())) {
            e = channelService.selectChannelById(pkChannel);
        }

        model.addAttribute("e", e);
        model.addAttribute("path", getImgsHostDomain());
        return TOCHFORM;
    }


    /**
     * 保存  频道字典信息
     *
     * @param channel
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Channel channel, HttpServletRequest request) {
       /* String str = request.getParameter("channelList");*/
        String  channelId=request.getParameter("channelId");
        String  channelSort=request.getParameter("channelSort");
        String  removeCid=request.getParameter("removeCid");
        try {
            channelService.save(channel, channelId,channelSort,removeCid);
        } catch (Exception e) {
            log.error("保存字典数据 失败", e);
        }
        return "redirect:/admin/channel/toList";
    }


    /**
     * 保存  频道信息
     *
     * @param pkChannel
     * @return
     */
    @RequestMapping(value = "/saveChan", method = RequestMethod.POST)
    public String saveChan(PkChannel pkChannel, HttpServletRequest request) {
        try {
            pkChannel.setCreateId(getCurrentId());
            pkChannel.setCreateRealname(getCurrentName());
            channelService.saveChan(pkChannel);
        } catch (Exception e) {
            log.error("频道信息 失败", e);
        }
        return "redirect:/admin/channel/toChannelList";
    }

    /**
     * 频道  添加子频道
     * @param channelsVo
     * @return
     */
    @RequestMapping(value = "/saveChildChan", method = RequestMethod.POST)
    @ResponseBody
    public Result saveChildChan(@RequestBody ChannelsVo channelsVo) {
        Result result = new Result<>();
        try {
            int row = channelService.saveChildChan(channelsVo);
            if (row > 1) {
                result.setResultCode(Result.SUCCESS);
            } else {
                result.setResultCode(Result.FAILURE);
            }
            result.setResultCode(Result.SUCCESS);
            return result;
        } catch (Exception e) {
            log.error(" 中间表信息 失败", e);
            result.setMsg(Result.SERVER_EXCEPTION);
            result.setResultCode(Result.FAILURE);
        }
        return result;
    }


}
