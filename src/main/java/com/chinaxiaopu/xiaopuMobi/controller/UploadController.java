package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.code.Result;
import com.chinaxiaopu.xiaopuMobi.config.SystemConfig;
import com.chinaxiaopu.xiaopuMobi.service.OSSClientService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by liuwei
 * date: 16/9/30
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class UploadController extends ContextController{

    @Autowired
    private OSSClientService ossClientService;

    @ApiOperation(value = "文件上传", notes = "文件上传，返回图片服务端路径")
    @RequestMapping(value="/fileUpload",method= RequestMethod.POST)
    public Result<String> fileUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String base64Str = request.getParameter("imgStr");
            Date date = new Date();
            String fileName = date.getTime()+ RandomStringUtils.randomNumeric(4)+".jpg";
            if(file!=null && !file.isEmpty()){
                ossClientService.putOSSByByte(fileName,file.getBytes());
            } else if (!StringUtils.isEmpty(base64Str)) {
                byte[] imgByte = Base64.decodeBase64(base64Str.substring(base64Str.indexOf(","),base64Str.length()));
                ossClientService.putOSSByByte(fileName,imgByte);
            }
            String filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
            result.setPath(filePath);
            result.setObj(fileName);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("图片上传错误",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg("图片上传失败");
        }
        return result;
    }

    @ApiIgnore
    @RequestMapping(value="/videoUpload",method= RequestMethod.POST)
    public Result<String> videoUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            String base64Str = request.getParameter("imgStr");
            Date date = new Date();

            int lastIndexOfDot = file.getOriginalFilename().lastIndexOf('.');
            int fileNameLength = file.getOriginalFilename().length();
            final String fileType = file.getOriginalFilename().substring(lastIndexOfDot+1, fileNameLength);
            String fileName = date.getTime()+ RandomStringUtils.randomNumeric(4)+"."+fileType;
            if(file!=null && !file.isEmpty()){
                ossClientService.putOSSByByte(fileName,file.getBytes());
            } else if (!StringUtils.isEmpty(base64Str)) {
                byte[] imgByte = Base64.decodeBase64(base64Str.substring(base64Str.indexOf(","),base64Str.length()));
                ossClientService.putOSSByByte(fileName,imgByte);
            }
            String filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
            result.setPath(filePath);
            result.setObj(fileName);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("视频上传错误",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg("视频上传失败");
        }
        return result;
    }


    @ApiIgnore
    @RequestMapping(value="/audioUpload",method= RequestMethod.POST)
    public Result<String> audioUpload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            Date date = new Date();
            String base64Str = request.getParameter("imgStr");
            int lastIndexOfDot = file.getOriginalFilename().lastIndexOf('.');
            int fileNameLength = file.getOriginalFilename().length();
            final String fileType = file.getOriginalFilename().substring(lastIndexOfDot+1, fileNameLength);
            String fileName = date.getTime()+ RandomStringUtils.randomNumeric(4)+"."+fileType;
            if(file!=null && !file.isEmpty()){
                ossClientService.putOSSByByte(fileName,file.getBytes());
            } else if (!StringUtils.isEmpty(base64Str)) {
                byte[] imgByte = Base64.decodeBase64(base64Str.substring(base64Str.indexOf(","),base64Str.length()));
                ossClientService.putOSSByByte(fileName,imgByte);
            }
            String filePath = SystemConfig.getInstance().getImgsDomain();//图片路径
            result.setPath(filePath);
            result.setObj(fileName);
            result.setResultCode(Result.SUCCESS);
        } catch (Exception e) {
            log.error("音频上传错误",e);
            result.setResultCode(Result.FAILURE);
            result.setMsg("音频上传失败");
        }
        return result;
    }
}
