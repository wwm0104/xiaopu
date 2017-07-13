package com.chinaxiaopu.xiaopuMobi.controller;

import com.chinaxiaopu.xiaopuMobi.dto.PrizeTakeLogDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerDetailsDto;
import com.chinaxiaopu.xiaopuMobi.dto.partner.PartnerEventDto;
import com.chinaxiaopu.xiaopuMobi.model.Partner;
import com.chinaxiaopu.xiaopuMobi.service.PartnerService;
import com.chinaxiaopu.xiaopuMobi.service.UserVrActivityService;
import com.chinaxiaopu.xiaopuMobi.service.admin.topics.PrizeTakeLogService;
import com.chinaxiaopu.xiaopuMobi.service.topic.AawardPresenService;
import com.chinaxiaopu.xiaopuMobi.util.DownUtil;
import com.chinaxiaopu.xiaopuMobi.util.ExcelUtil;
import com.chinaxiaopu.xiaopuMobi.util.StrUtils;
import com.chinaxiaopu.xiaopuMobi.vo.partner.GroupPartnerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出 Excel
 * Created by wuning on 2016/11/11.
 */
@Slf4j
@Controller
@RequestMapping("/export")
public class ExportController {
    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PrizeTakeLogService prizeTakeLogService;

    @Autowired
    private AawardPresenService awardPresenService;
    @Autowired
    private UserVrActivityService userVrActivityService;

    /**
     * 校园合伙人详情导出实例
     *
     * @return
     */
    @RequestMapping(value = "/schoolPartnerExport", method = RequestMethod.POST)
    public String testExport(PartnerDetailsDto partnerDetailsDto, HttpServletResponse response) {
        Partner partner = partnerService.getById(partnerDetailsDto.getPartnerid());
        List<List<Object>> resultList = partnerService.selectUserInfoByPartnerExport(partnerDetailsDto);
        /**
         * 封装数据
         */
        List<Object> baseList = new ArrayList<>();
        baseList.add(partner.getRealName());
        baseList.add(partner.getSex());
        baseList.add(partner.getSchoolName());
        baseList.add(partner.getMobile());
        baseList.add(partner.getStarRating());
        baseList.add(partner.getRemarks());


        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";

        String title = "校园合伙人详情";//标题名称
        String[] headerArr = new String[]{"姓名", "性别", "学校", "手机号", "星级", "备注"};//第一行名称定义
        String[] headerArr3 = new String[]{"邀请码", "姓名", "学号", "手机号", "QQ", "学校", "时间"};//第四行名称定义
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"",
                    new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx"); //excel 名称
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        Workbook wb = new XSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        Sheet sheet = wb.createSheet(title);
        // 设置头信息
        Header header = sheet.getHeader();
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        CellRangeAddress region = new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                5  //last column  (0-based)
        );//第一行   前6列
        sheet.addMergedRegion(region);
        XSSFCellStyle titleStyle = ExcelUtil.getCellStyle(wb, false, true, Font.COLOR_NORMAL, (short) 15, "华文楷体", true, null);
        cell.setCellStyle(titleStyle);//样式 放入第一行
        cell.setCellValue(title);//标题放入第一行
        if (StrUtils.isNotEmpty(baseList)) {
            Row row_1 = sheet.createRow(1);
            for (int i = 0; i < baseList.size(); ++i) {
                Cell cell_1 = row_1.createCell(i);
                cell_1.setCellValue(headerArr[i].toString());
            } //创建 第二行  并把创建的子标题放入

            Row row_2 = sheet.createRow(2);
            for (int j = 0; j < baseList.size(); ++j) {
                Cell cell_j = row_2.createCell(j);
                if(null==baseList.get(j)){
                    cell_j.setCellValue("无");
                }else{
                    cell_j.setCellValue(baseList.get(j).toString());
                }
            }//创建 第三行  并把创建的子标题放入
        }

        if (StrUtils.isNotEmpty(resultList)) {
            Row row_3 = sheet.createRow(4);
            for (int j = 0; j < resultList.get(0).size(); ++j) {
                Cell cell_j = row_3.createCell(j);
                cell_j.setCellValue(headerArr3[j].toString());
            }//创建 第四行  并把创建的子标题放入

            for (int j = 0; j < resultList.size(); ++j) {
                //创建多少行
                Row row_j = sheet.createRow(j + 5);
                for (int i = 0; i < resultList.get(j).size(); ++i) {
                    if (j < 3) {
                        Cell cell_j = row_j.createCell(i);
                        if(null==resultList.get(j).get(i)){
                            cell_j.setCellValue("无");
                        }else{
                            cell_j.setCellValue(resultList.get(j).get(i).toString());
                        }
                    }

                }
            }
        }

        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        long end = System.currentTimeMillis();
        // 生成Excel
        try {
            // 创建字节数组输出流
            out = new ByteArrayOutputStream();
            // 将workbook写入输出流
            wb.write(out);
            // 将输出流转成字节数组
            byte[] buf = out.toByteArray();
            // 读入输入流
            in = new ByteArrayInputStream(buf);
            // 下载
            DownUtil.doDownload(response, in, contentType, headerKey, headerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 社团合伙人详情导出实例
     *
     * @return
     */
    @RequestMapping(value = "/groupPartnerExport", method = RequestMethod.POST)
    public String testExport(PartnerEventDto partnerEventDto, HttpServletResponse response) {
        GroupPartnerVo groupPartnerVo=partnerService.getByGroupid(partnerEventDto.getOrganizeId());
        List<List<Object>> resultList = partnerService.selectEventByPartnerExport(partnerEventDto);
        /**
         * 封装数据
         */
        List<Object> baseList = new ArrayList<>();
        baseList.add(groupPartnerVo.getGroupName());
        baseList.add(groupPartnerVo.getSchoolName());
        baseList.add(groupPartnerVo.getRealName());
        baseList.add(groupPartnerVo.getMobile());
        baseList.add(groupPartnerVo.getStarRating());
        baseList.add(groupPartnerVo.getRemarks());


        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";

        String title = "社团合伙人详情";//标题名称
        String[] headerArr = new String[]{"社团", "学校", "社长", "手机号", "星级", "备注"};//第一行名称定义
        String[] headerArr3 = new String[]{"活动名称", "报名人数", "标签引用数", "活动状态", "活动时间"};//第四行名称定义
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"",
                    new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx"); //excel 名称
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        Workbook wb = new XSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        Sheet sheet = wb.createSheet(title);
        // 设置头信息
        Header header = sheet.getHeader();
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") +
                HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        CellRangeAddress region = new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                5  //last column  (0-based)
        );//第一行   前6列
        sheet.addMergedRegion(region);
        XSSFCellStyle titleStyle = ExcelUtil.getCellStyle(wb, false, true, Font.COLOR_NORMAL, (short) 15, "华文楷体", true, null);
        cell.setCellStyle(titleStyle);//样式 放入第一行
        cell.setCellValue(title);//标题放入第一行
        if (StrUtils.isNotEmpty(baseList)) {
            Row row_1 = sheet.createRow(1);
            for (int i = 0; i < baseList.size(); ++i) {
                Cell cell_1 = row_1.createCell(i);
                cell_1.setCellValue(headerArr[i].toString());
            } //创建 第二行  并把创建的子标题放入

            Row row_2 = sheet.createRow(2);
            for (int j = 0; j < baseList.size(); ++j) {
                Cell cell_j = row_2.createCell(j);
                if(null==baseList.get(j)){
                    cell_j.setCellValue("无");
                }else{
                    cell_j.setCellValue(baseList.get(j).toString());
                }

            }//创建 第三行  并把创建的子标题放入
        }

        if (StrUtils.isNotEmpty(resultList)) {
            Row row_3 = sheet.createRow(4);
            for (int j = 0; j < resultList.get(0).size(); ++j) {
                Cell cell_j = row_3.createCell(j);
                cell_j.setCellValue(headerArr3[j].toString());
            }//创建 第四行  并把创建的子标题放入

            for (int j = 0; j < resultList.size(); ++j) {
                //创建多少行
                Row row_j = sheet.createRow(j + 5);
                for (int i = 0; i < resultList.get(j).size(); ++i) {
                    if (j < 3) {
                        Cell cell_j = row_j.createCell(i);
                        if(null==resultList.get(j).get(i)){
                            cell_j.setCellValue("无");
                        }else{
                            cell_j.setCellValue(resultList.get(j).get(i).toString());
                        }

                    }

                }
            }
        }

        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        long end = System.currentTimeMillis();
        // 生成Excel
        try {
            // 创建字节数组输出流
            out = new ByteArrayOutputStream();
            // 将workbook写入输出流
            wb.write(out);
            // 将输出流转成字节数组
            byte[] buf = out.toByteArray();
            // 读入输入流
            in = new ByteArrayInputStream(buf);
            // 下载
            DownUtil.doDownload(response, in, contentType, headerKey, headerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/prizeTakeExport", method = RequestMethod.POST)
    public String prizeTakeExport(PrizeTakeLogDto prizeTakeLogDto, HttpServletResponse response) throws ParseException {
        /**
         * 处理日期类
         */
        String startTime = "";
        String endTime = "";
        if (StrUtils.isNotEmpty(prizeTakeLogDto.getTakeTime())) {
            String time = prizeTakeLogDto.getTakeTime();
            startTime = time.split("/")[0];
            endTime = time.split("/")[1];
            prizeTakeLogDto.setStartTime(startTime);
            prizeTakeLogDto.setEndTime(endTime);
        }
        List<List<Object>> baseList = prizeTakeLogService.getPrizeTakeExportInfo(prizeTakeLogDto);
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-mm-dd");

        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";

        String title = "";//标题名称"发奖记录"
        if (StrUtils.isNotEmpty(prizeTakeLogDto.getTakeTime())) {
            title = sf.format(sf.parse(startTime)) + "TO" + sf.format(sf.parse(endTime)) + "发奖记录";
        } else {
            title = "发奖记录";
        }
        String[] headerArr = new String[]{"id", "昵称", "姓名", "学校", "社团", "主题类型", "来战标题", "结束时间", "名次", "奖品", "是否领取", "发奖人", "领取时间"};//第一行名称定义
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"", new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx"); //excel 名称
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        Workbook wb = new XSSFWorkbook();//创建HSSFWorkbook对象(excel的文档对象)
        Sheet sheet = wb.createSheet(title);//建立新的sheet对象（excel的表单）
        Header header = sheet.getHeader();// 设置头信息
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        CellRangeAddress region = new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                12  //last column  (0-based)
        );//第一行
        sheet.addMergedRegion(region);
        XSSFCellStyle titleStyle = ExcelUtil.getCellStyle(wb, false, true, Font.COLOR_NORMAL, (short) 15, "华文楷体", true, null);
        cell.setCellStyle(titleStyle);//样式 放入第一行
        cell.setCellValue(title);//标题放入第一行
        if (StrUtils.isNotEmpty(baseList)) {
            Row row_1 = sheet.createRow(1);
            for (int i = 0; i < baseList.get(0).size(); ++i) {
                Cell cell_1 = row_1.createCell(i);
                cell_1.setCellValue(headerArr[i].toString());
            } //创建 第二行  并把创建的子标题放入
            for (int j = 0; j < baseList.size(); ++j) {
                //创建多少行
                Row row_j = sheet.createRow(j + 2);
                for (int i = 0; i < baseList.get(j).size(); ++i) {
                    Cell cell_j = row_j.createCell(i);
                    cell_j.setCellValue(StrUtils.emptyOrString(baseList.get(j).get(i)));
                }
            }
        }

        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        // 生成Excel
        try {
            // 创建字节数组输出流
            out = new ByteArrayOutputStream();
            // 将workbook写入输出流
            wb.write(out);
            // 将输出流转成字节数组
            byte[] buf = out.toByteArray();
            // 读入输入流
            in = new ByteArrayInputStream(buf);
            // 下载
            DownUtil.doDownload(response, in, contentType, headerKey, headerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发奖人管理导出
     *
     * @return
     */
    @RequestMapping(value = "/awardPersonExport", method = RequestMethod.POST)
    public String awardPersonExport(HttpServletResponse response) {
        List<List<Object>> baseList = awardPresenService.awardPersonExport();

        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";

        String title = "发奖人";

        String[] headerArr = new String[]{"id", "发奖人姓名", "发奖人手机", "官方负责人", "负责人手机", "备注", "发奖次数", "启用状态"};//第一行名称定义
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"", new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx"); //excel 名称
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        Workbook wb = new XSSFWorkbook();//创建HSSFWorkbook对象(excel的文档对象)
        Sheet sheet = wb.createSheet(title);//建立新的sheet对象（excel的表单）
        Header header = sheet.getHeader();// 设置头信息
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        CellRangeAddress region = new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                7  //last column  (0-based)
        );//第一行
        sheet.addMergedRegion(region);
        XSSFCellStyle titleStyle = ExcelUtil.getCellStyle(wb, false, true, Font.COLOR_NORMAL, (short) 15, "华文楷体", true, null);
        cell.setCellStyle(titleStyle);//样式 放入第一行
        cell.setCellValue(title);//标题放入第一行
        if (StrUtils.isNotEmpty(baseList)) {
            Row row_1 = sheet.createRow(1);
            for (int i = 0; i < baseList.get(0).size(); ++i) {
                Cell cell_1 = row_1.createCell(i);
                cell_1.setCellValue(headerArr[i].toString());
            } //创建 第二行  并把创建的子标题放入
            for (int j = 0; j < baseList.size(); ++j) {
                //创建多少行
                Row row_j = sheet.createRow(j + 2);
                for (int i = 0; i < baseList.get(j).size(); ++i) {
                    Cell cell_j = row_j.createCell(i);
                    cell_j.setCellValue(StrUtils.emptyOrString(baseList.get(j).get(i)));
                }
            }
        }

        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        // 生成Excel
        try {
            // 创建字节数组输出流
            out = new ByteArrayOutputStream();
            // 将workbook写入输出流
            wb.write(out);
            // 将输出流转成字节数组
            byte[] buf = out.toByteArray();
            // 读入输入流
            in = new ByteArrayInputStream(buf);
            // 下载
            DownUtil.doDownload(response, in, contentType, headerKey, headerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出vr用户体检列表
     * @param response
     * @return
     */
    @RequestMapping(value = "/vrUserInfoExport", method = RequestMethod.POST)
    public String vrUserInfoExport(HttpServletResponse response) {
        List<List<Object>> baseList = userVrActivityService.exportVrList();

        String contentType = "application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerKey = "Content-Disposition";

        String title = "VR用户体验列表";

        String[] headerArr = new String[]{"姓名", "手机", "学校", "学号", "预约时间", "体验次数"};//第一行名称定义
        String headerValue = null;
        try {
            headerValue = String.format("attachment; filename=\"%s\"", new String(title.getBytes(Charset.forName("UTF-8")), "ISO8859-1") + ".xlsx"); //excel 名称
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码错误", e);
        }
        Workbook wb = new XSSFWorkbook();//创建HSSFWorkbook对象(excel的文档对象)
        Sheet sheet = wb.createSheet(title);//建立新的sheet对象（excel的表单）
        Header header = sheet.getHeader();// 设置头信息
        header.setCenter("Center Header");
        header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
        Row row = sheet.createRow(0);// 创建首行
        Cell cell = row.createCell(0);// 创建首列
        CellRangeAddress region = new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                7  //last column  (0-based)
        );//第一行
        sheet.addMergedRegion(region);
        XSSFCellStyle titleStyle = ExcelUtil.getCellStyle(wb, false, true, Font.COLOR_NORMAL, (short) 15, "华文楷体", true, null);
        cell.setCellStyle(titleStyle);//样式 放入第一行
        cell.setCellValue(title);//标题放入第一行
        if (StrUtils.isNotEmpty(baseList)) {
            Row row_1 = sheet.createRow(1);
            for (int i = 0; i < baseList.get(0).size(); ++i) {
                Cell cell_1 = row_1.createCell(i);
                cell_1.setCellValue(headerArr[i].toString());
            } //创建 第二行  并把创建的子标题放入
            for (int j = 0; j < baseList.size(); ++j) {
                //创建多少行
                Row row_j = sheet.createRow(j + 2);
                for (int i = 0; i < baseList.get(j).size(); ++i) {
                    Cell cell_j = row_j.createCell(i);
                    cell_j.setCellValue(StrUtils.emptyOrString(baseList.get(j).get(i)));
                }
            }
        }

        ByteArrayInputStream in = null;
        ByteArrayOutputStream out = null;
        // 生成Excel
        try {
            // 创建字节数组输出流
            out = new ByteArrayOutputStream();
            // 将workbook写入输出流
            wb.write(out);
            // 将输出流转成字节数组
            byte[] buf = out.toByteArray();
            // 读入输入流
            in = new ByteArrayInputStream(buf);
            // 下载
            DownUtil.doDownload(response, in, contentType, headerKey, headerValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
