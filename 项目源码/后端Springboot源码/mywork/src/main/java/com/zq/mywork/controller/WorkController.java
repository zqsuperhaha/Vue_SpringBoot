package com.zq.mywork.controller;

import com.zq.mywork.model.SetScore;
import com.zq.mywork.model.Student;
import com.zq.mywork.model.Submit;
import com.zq.mywork.model.Work;
import com.zq.mywork.service.student.StudentService;
import com.zq.mywork.service.submit.SubmitService;
import com.zq.mywork.service.work.WorkService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WorkController {

    @Autowired
    private WorkService workService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubmitService submitService;

    @Autowired
    private SubmitController submitController;

    // -------------------------- 查询 ----------------------------

    // 所有作业
    @CrossOrigin
    @GetMapping(value = "api/findAllWork")
    @ResponseBody
    public List<Work> findAllWork(){

        return workService.findAll();
    }

    // 某项作业详情
    @CrossOrigin
    @PostMapping(value = "api/findWork")
    @ResponseBody
    public List<Map<String, Object>> findWork(@RequestParam("w_id") String w_id){

        return workService.findWork(w_id);
    }

    // 该作业对应的老师信息
    @CrossOrigin
    @PostMapping(value = "api/findWorkTch")
    @ResponseBody
    public List<Map<String, Object>> findWorkTch(@RequestParam("w_id") String w_id){

        return workService.findWorkTch(w_id);
    }

    // ------------ 管理员 -------------

    // 分页数量
    @CrossOrigin
    @GetMapping(value = "api/findAllWorkPartCount")
    @ResponseBody
    public int findAllWorkPartCount(){

        return workService.findAllPartCount();
    }

    // 分页详情
    @CrossOrigin
    @PostMapping(value = "api/findAllWorkPart")
    @ResponseBody
    public List<Map<String, Object>> findAllWorkPart(@RequestParam("page") int page){

        List<Map<String, Object>> listMap = workService.findAllPart(page);
        findYNnum(listMap);
        return listMap;
    }

    // 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findWorkSearchCount")
    @ResponseBody
    public int findWorkSearchCount(@RequestParam("workany") String workany){

        workany = "%"+workany+"%";
        return workService.findSearchCount(workany);
    }

    // 模糊查询详情
    @CrossOrigin
    @PostMapping(value = "api/findWorkSearch")
    @ResponseBody
    public List<Map<String, Object>> findWorkSearch(@RequestParam("workany") String workany,@RequestParam("page") int page){

        workany = "%"+workany+"%";
        List<Map<String, Object>> listMap = workService.findSearch(workany,page);
        findYNnum(listMap);
        return listMap;
    }

    // ------------ 教师 -------------

    // 分页数量
    @CrossOrigin
    @GetMapping(value = "api/findTchWorkCount")
    @ResponseBody
    public int findTchWorkCount(@RequestParam("t_id") String t_id){

        return workService.findTchWorkCount(t_id);
    }

    // 分页详情
    @CrossOrigin
    @PostMapping(value = "api/findTchWork")
    @ResponseBody
    public List<Map<String, Object>> findTchWork(@RequestParam("t_id") String t_id,@RequestParam("page") int page){

        List<Map<String, Object>> listMap = workService.findTchWork(t_id,page);
        findYNnum(listMap);
        return listMap;
    }

    // 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findTchWorkSHCount")
    @ResponseBody
    public int findTchWorkSHCount(@RequestParam("t_id") String t_id,@RequestParam("workany") String workany){

        workany = "%"+workany+"%";
        return workService.findTchWorkSHCount(t_id,workany);
    }

    // 模糊查询详情
    @CrossOrigin
    @PostMapping(value = "api/findTchWorkSH")
    @ResponseBody
    public List<Map<String, Object>> findTchWorkSH(@RequestParam("t_id") String t_id,@RequestParam("workany") String workany,@RequestParam("page") int page){

        workany = "%"+workany+"%";
        List<Map<String, Object>> listMap = workService.findTchWorkSH(t_id,workany,page);
        findYNnum(listMap);
        return listMap;
    }

    // 查询分数设置
    @CrossOrigin
    @GetMapping(value = "api/findScore")
    @ResponseBody
    public List<SetScore> findScore(){

        return workService.findScore();
    }

    // 修改分数设置
    @CrossOrigin
    @PostMapping(value = "api/updateScore")
    @ResponseBody
    public void updateScore(@RequestParam("aj") int aj,
                            @RequestParam("cz") int cz,
                            @RequestParam("qj") int qj,
                            @RequestParam("maxpj") int maxpj,
                            @RequestParam("minpj") int minpj){

        SetScore setScore = new SetScore();
        setScore.setAj(aj);
        setScore.setCz(cz);
        setScore.setQj(qj);
        setScore.setMaxpj(maxpj);
        setScore.setMinpj(minpj);

        workService.updateScore(setScore);

    }



    // ----------------- 已交，未交 人数 -------------------

    public void findYNnum(List<Map<String, Object>> listMap){

        // 已交人数
        List<Map<String, Object>> yjnum = workService.findYjNum();

        // 未交人数
        List<Map<String, Object>> njnum = workService.findNjNum();

        String yf;
        String nf;

        for (int i = 0; i < listMap.size(); i++) {

            yf = "no";
            nf = "no";

            for (int j = 0; j < yjnum.size(); j++) {
                if(listMap.get(i).get("w_id").equals(yjnum.get(j).get("w_id"))){
                    listMap.get(i).put("yjnums",yjnum.get(j).get("nums"));
                    yf = "yes";
                    break;
                }
            }
            if(yf.equals("no")){
                listMap.get(i).put("yjnums",0);
            }

            for (int j = 0; j < njnum.size(); j++) {
                if(listMap.get(i).get("w_id").equals(njnum.get(j).get("w_id"))){
                    listMap.get(i).put("njnums",njnum.get(j).get("nums"));
                    nf = "yes";
                    break;
                }
            }

            if(nf.equals("no")){
                listMap.get(i).put("njnums",0);
            }
        }

    }

    // ----------------- 已交，未交 详情 -------------------

    // ----------------- 已交 -----------------

    // 已交详情 数量
    @CrossOrigin
    @PostMapping(value = "api/findYjCount")
    @ResponseBody
    public int findYjCount(@RequestParam("w_id") String w_id){

        return workService.findYjCount(w_id);
    }

    // 已交详情
    @CrossOrigin
    @PostMapping(value = "api/findYj")
    @ResponseBody
    public List<Map<String, Object>> findYj(@RequestParam("w_id") String w_id,@RequestParam("page") int page){

        return workService.findYj(w_id,page);
    }

    // 已交详情 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findYjSHCount")
    @ResponseBody
    public int findYjSHCount(@RequestParam("w_id") String w_id,@RequestParam("ynany") String ynany){

        ynany = "%"+ynany+"%";
        return workService.findYjSHCount(w_id,ynany);
    }

    // 已交详情 模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findYjSH")
    @ResponseBody
    public List<Map<String, Object>> findYjSH(@RequestParam("w_id") String w_id,@RequestParam("ynany") String ynany,@RequestParam("page") int page){

        ynany = "%"+ynany+"%";
        return workService.findYjSH(w_id,ynany,page);
    }


    // ----------------- 未交 -----------------

    // 未交详情 数量
    @CrossOrigin
    @PostMapping(value = "api/findNjCount")
    @ResponseBody
    public int findNjCount(@RequestParam("w_id") String w_id){

        return workService.findNjCount(w_id);
    }

    // 未交详情
    @CrossOrigin
    @PostMapping(value = "api/findNj")
    @ResponseBody
    public List<Map<String, Object>> findNj(@RequestParam("w_id") String w_id,@RequestParam("page") int page){

        submitController.updateQj(); // 如果有作业缺交--先设为缺交--2
        return workService.findNj(w_id,page);
    }

    // 未交详情 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findNjSHCount")
    @ResponseBody
    public int findNjSHCount(@RequestParam("w_id") String w_id,@RequestParam("ynany") String ynany){

        ynany = "%"+ynany+"%";
        return workService.findNjSHCount(w_id,ynany);
    }

    // 未交详情 模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findNjSH")
    @ResponseBody
    public List<Map<String, Object>> findNjSH(@RequestParam("w_id") String w_id,@RequestParam("ynany") String ynany,@RequestParam("page") int page){

        ynany = "%"+ynany+"%";
        return workService.findNjSH(w_id,ynany,page);
    }



    // -------------------------- 添加 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/addWork")
    @ResponseBody
    public void addWork(@RequestParam("w_id") String w_id,
                        @RequestParam("t_id") String t_id,
                        @RequestParam("b_id") String b_id,
                        @RequestParam("c_id") String c_id,
                        @RequestParam("begindate") String begindate,
                        @RequestParam("enddate") String enddate,
                        @RequestParam("jianjie") String jianjie){

        workService.addWork(w_id,t_id,b_id,c_id,begindate,enddate,jianjie);

        // 分发作业
        List<Student> studentList = studentService.findBjAll(b_id);

        Submit submit = new Submit();
        submit.setW_id(w_id);
        submit.setSubdate(enddate);
        submit.setEnddate(enddate);
        submit.setPjdate("5000-01-01 00:00:00");
        submit.setPs("");
        submit.setPingjia("");
        submit.setW_score(0);
        submit.setIsdo("0");
        submit.setIscheck("0");
        submit.setStatus("1");  // 1-正常

        // 分发给每个学生
        for (int i = 0; i < studentList.size(); i++) {
            submit.setS_id(studentList.get(i).getS_id());
            submitService.addSubmit(submit);
            System.out.println("分发："+studentList.get(i).getS_id());
        }

    }

    // -------------------------- 修改 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/updateWork")
    @ResponseBody
    public void updateWork(@RequestParam("w_id") String w_id,
                           @RequestParam("t_id") String t_id,
                        @RequestParam("b_id") String b_id,
                        @RequestParam("c_id") String c_id,
                        @RequestParam("begindate") String begindate,
                        @RequestParam("enddate") String enddate,
                        @RequestParam("jianjie") String jianjie){

//        workService.updateWork(t_id,b_id,c_id,begindate,enddate,jianjie,w_id);

        // 先删除这项作业
        workService.deleteWork(w_id);

        // 再添加（分发）
        this.addWork(w_id,t_id,b_id,c_id,begindate,enddate,jianjie);
    }


    // -------------------------- 删除 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/deleteWork")
    @ResponseBody
    public void deleteWork(@RequestBody Object delList){

        List<Map<String,Object>> dellistmap = (List<Map<String, Object>>) delList;
        for (int i = 0; i <dellistmap.size() ; i++) {
            workService.deleteWork(dellistmap.get(i).get("w_id").toString());
        }

    }

    // ----------------------------- 导出表格 --------------------------------

    // ---------- 管理员 -----------
    @CrossOrigin
    @PostMapping(value = "api/exportWorkExcel")
    @ResponseBody
    public void exportData(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("作业表");

        // 查询作业表数据
        List<Map<String, Object>> worklist = workService.findAllPartOut();

        // 插入已交，未交人数
        findYNnum(worklist);

        // 文件名称
        String fileName = "Work" + ".xlsx";

        // 开始！
        int rowNum = 1;

        // 在 Excel 中添加表头
        String[] headers={"作业号","教师号","教师","班级号","班级","课程号","课程","布置日期","截止日期","已交人数","未交人数","详情"};
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        // 将其他主体数据放入表格中
        for (int i = 0; i < worklist.size(); i++) {

            XSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(worklist.get(i).get("w_id").toString());
            row1.createCell(1).setCellValue(worklist.get(i).get("t_id").toString());
            row1.createCell(2).setCellValue(worklist.get(i).get("t_name").toString());
            row1.createCell(3).setCellValue(worklist.get(i).get("b_id").toString());
            row1.createCell(4).setCellValue(worklist.get(i).get("b_name").toString());
            row1.createCell(5).setCellValue(worklist.get(i).get("c_id").toString());
            row1.createCell(6).setCellValue(worklist.get(i).get("c_name").toString());
            row1.createCell(7).setCellValue(worklist.get(i).get("begindate").toString().substring(0,16));
            row1.createCell(8).setCellValue(worklist.get(i).get("enddate").toString().substring(0,16));
            row1.createCell(9).setCellValue(worklist.get(i).get("yjnums").toString());
            row1.createCell(10).setCellValue(worklist.get(i).get("njnums").toString());
            row1.createCell(11).setCellValue(worklist.get(i).get("jianjie").toString());

            rowNum++;
        }

        // 导出
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    // ---------- 教师 -----------
    @CrossOrigin
    @PostMapping(value = "api/exportTchWorkExcel")
    @ResponseBody
    public void exportTchWorkExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("作业表");

        // 查询作业表数据
        List<Map<String, Object>> worklist = workService.findAllPartOut();

        // 插入已交，未交人数
        findYNnum(worklist);

        // 文件名称
        String fileName = "Work" + ".xlsx";

        // 开始！
        int rowNum = 1;

        // 在 Excel 中添加表头
        String[] headers={"作业号","班级号","班级","课程号","课程","布置日期","截止日期","已交人数","未交人数","详情"};
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        // 将其他主体数据放入表格中
        for (int i = 0; i < worklist.size(); i++) {

            XSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(worklist.get(i).get("w_id").toString());
            row1.createCell(1).setCellValue(worklist.get(i).get("b_id").toString());
            row1.createCell(2).setCellValue(worklist.get(i).get("b_name").toString());
            row1.createCell(3).setCellValue(worklist.get(i).get("c_id").toString());
            row1.createCell(4).setCellValue(worklist.get(i).get("c_name").toString());
            row1.createCell(5).setCellValue(worklist.get(i).get("begindate").toString().substring(0,16));
            row1.createCell(6).setCellValue(worklist.get(i).get("enddate").toString().substring(0,16));
            row1.createCell(7).setCellValue(worklist.get(i).get("yjnums").toString());
            row1.createCell(8).setCellValue(worklist.get(i).get("njnums").toString());
            row1.createCell(9).setCellValue(worklist.get(i).get("jianjie").toString());

            rowNum++;
        }

        // 导出
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }


}
