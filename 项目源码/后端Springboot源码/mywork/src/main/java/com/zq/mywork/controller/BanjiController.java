package com.zq.mywork.controller;

import com.zq.mywork.model.Banji;
import com.zq.mywork.service.banji.BanjiService;
import com.zq.mywork.service.course.CourseService;
import com.zq.mywork.service.teacher.TeacherService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class BanjiController {

    @Autowired
    private BanjiService banjiService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    // -------------------------- 查询 ----------------------------

    // 所有班级
    @CrossOrigin
    @GetMapping(value = "api/findAllBanji")
    @ResponseBody
    public List<Banji> findAllBanji(){

        return banjiService.findAll();
    }

    // 所有班级
    @CrossOrigin
    @GetMapping(value = "api/findSelectBanji")
    @ResponseBody
    public List<Banji> findSelectBanji(){

        return banjiService.findSelectAll();
    }

    // 分页数量
    @CrossOrigin
    @GetMapping(value = "api/findAllBanjiPartCount")
    @ResponseBody
    public int findAllPartBanjiCount(){

        return banjiService.findAllPartCount();
    }

    // 分页详情
    @CrossOrigin
    @PostMapping(value = "api/findAllBanjiPart")
    @ResponseBody
    public List<Banji> findAllPartBanji(@RequestParam("page") int page){

        return banjiService.findAllPart(page);
    }

    // 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findBanjiSearchCount")
    @ResponseBody
    public int findBanjiSearchCount(@RequestParam("banjiany") String banjiany){

        banjiany = "%"+banjiany+"%";
        return banjiService.findSearchCount(banjiany);
    }

    // 模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findBanjiSearch")
    @ResponseBody
    public List<Banji> findBanjiSearch(@RequestParam("banjiany") String banjiany,@RequestParam("page") int page){

        banjiany = "%"+banjiany+"%";
        return banjiService.findSearch(banjiany,page);
    }



    // 三级联菜单（班级-教师-课程）
    @CrossOrigin
    @GetMapping(value = "api/findBTC")
    @ResponseBody
    public List<Map<String,Object>> findBTC(){

        List<Map<String,Object>> listmap = new LinkedList<>();

        List<Banji> banjiList = findAllBanji();

        for (int i = 0; i < banjiList.size(); i++) {
            Map<String,Object> btmap = new HashMap<>();
            btmap.put("value",banjiList.get(i).getB_id());
            btmap.put("label",banjiList.get(i).getB_name());

            List<Map<String,Object>> btlm = new LinkedList<>();   // children

            List<Map<String,Object>> btlist = teacherService.findBT(banjiList.get(i).getB_id());
            for (int j = 0; j < btlist.size(); j++) {
                Map<String,Object> tcmap = new HashMap<>();
                tcmap.put("value",btlist.get(j).get("t_id"));
                tcmap.put("label",btlist.get(j).get("t_name"));

                List<Map<String,Object>> tclm = new LinkedList<>();   // children

                List<Map<String,Object>> tclist = courseService.findTC(btlist.get(j).get("t_id").toString());
                for (int k = 0; k < tclist.size(); k++) {
                    Map<String,Object> cmap = new HashMap<>();
                    cmap.put("value",tclist.get(k).get("c_id"));
                    cmap.put("label",tclist.get(k).get("c_name"));
                    tclm.add(cmap);
                }
                tcmap.put("children",tclm);
                btlm.add(tcmap);
            }
            btmap.put("children",btlm);
            listmap.add(btmap);
        }

        return listmap;
    }

    // 二级联动菜单（班级-课程）
    @CrossOrigin
    @GetMapping(value = "api/findBC")
    @ResponseBody
    public List<Map<String,Object>> findBC(@RequestParam("t_id") String t_id){

        List<Map<String,Object>> listmap = new LinkedList<>();

        List<Map<String,Object>> tblist = banjiService.findTB(t_id);    // 获取对应教师的所有班级
        for (int i = 0; i < tblist.size(); i++) {
            Map<String,Object> bcmap = new HashMap<>();
            bcmap.put("value",tblist.get(i).get("b_id"));
            bcmap.put("label",tblist.get(i).get("b_name"));

            List<Map<String,Object>> bclm = new LinkedList<>();   // children

            List<Map<String,Object>> bclist = courseService.findBC(tblist.get(i).get("b_id").toString(),t_id);
            for (int j = 0; j < bclist.size(); j++) {
                Map<String,Object> tcmap = new HashMap<>();
                tcmap.put("value",bclist.get(j).get("c_id"));
                tcmap.put("label",bclist.get(j).get("c_name"));

                bclm.add(tcmap);
            }
            bcmap.put("children",bclm);
            listmap.add(bcmap);
        }

        return listmap;
    }

    // -------------------------- 添加 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/addBanji")
    @ResponseBody
    public void addBanji(@RequestParam("b_id") String b_id,@RequestParam("b_name") String b_name){

        banjiService.addBanji(b_id,b_name);
    }

    // -------------------------- 修改 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/updateBanji")
    @ResponseBody
    public void updateBanji(@RequestParam("b_id") String b_id,@RequestParam("b_name") String b_name){

        banjiService.updateBanji(b_name,b_id);
    }


    // -------------------------- 删除 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/deleteBanji")
    @ResponseBody
    public void deleteBanji(@RequestBody Object delList){

        List<Map<String,Object>> dellistmap = (List<Map<String, Object>>) delList;
        for (int i = 0; i <dellistmap.size() ; i++) {
            banjiService.deleteBanji(dellistmap.get(i).get("b_id").toString());
        }

    }

    @CrossOrigin
    @PostMapping(value = "api/exportBanjiExcel")
    @ResponseBody
    // ----------------------------- 导出表格 --------------------------------
    public void exportData(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("班级表");

        // 查询作业表数据
        List<Banji> banjiList = banjiService.findAll();

        // 文件名称
        String fileName = "Banji" + ".xlsx";

        // 开始！
        int rowNum = 1;

        // 在 Excel 中添加表头
        String[] headers={"班级号","班级名称"};
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {

            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        // 将其他主体数据放入表格中
        for (int i = 0; i < banjiList.size(); i++) {

            XSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(banjiList.get(i).getB_id());
            row1.createCell(1).setCellValue(banjiList.get(i).getB_name());

            rowNum++;
        }

        // 导出
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());


    }



}
