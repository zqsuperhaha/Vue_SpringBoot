package com.zq.mywork.controller;

import com.zq.mywork.model.Banji;
import com.zq.mywork.model.Result;
import com.zq.mywork.model.Teacher;
import com.zq.mywork.service.teacher.TeacherService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // -------------------------- 查询 ----------------------------


    // 所有教师
    @CrossOrigin
    @GetMapping(value = "api/findAllTeacher")
    @ResponseBody
    public List<Teacher> findAllTeacher(){

        return teacherService.findAll();
    }

    // 某个教师信息
    @CrossOrigin
    @PostMapping(value = "api/findTchInfo")
    @ResponseBody
    public List<Teacher> findTchInfo(@RequestParam("t_id") String t_id){

        return teacherService.findTchInfo(t_id);
    }

    // 分页数量
    @CrossOrigin
    @GetMapping(value = "api/findAllTchPartCount")
    @ResponseBody
    public int findAllTchPartCount(){

        return teacherService.findAllPartCount();
    }

    // 分页详情
    @CrossOrigin
    @PostMapping(value = "api/findAllTchPart")
    @ResponseBody
    public List<Map<String,Object>> findAllTchPart(@RequestParam("page") int page){

        return teacherService.findAllPart(page);
    }

    // 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findTchSearchCount")
    @ResponseBody
    public int findTchSearchCount(@RequestParam("tchany") String tchany){

        tchany = "%"+tchany+"%";
        return teacherService.findSearchCount(tchany);
    }

    // 模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findTchSearch")
    @ResponseBody
    public List<Map<String,Object>> findTchSearch(@RequestParam("tchany") String tchany,@RequestParam("page") int page){

        tchany = "%"+tchany+"%";
        return teacherService.findSearch(tchany,page);
    }


    // -------------------------- 添加 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/addTeacher")
    @ResponseBody
    public void addTeacher(@RequestParam("t_id") String t_id,
                           @RequestParam("t_pwd") String t_pwd,
                           @RequestParam("t_name") String t_name,
                           @RequestParam("t_phone") String t_phone,
                           @RequestParam("t_email") String t_email){

        Teacher teacher = new Teacher();
        teacher.setT_id(t_id);

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(t_pwd);

        System.out.println("加密后的密码："+Mpwd);
        System.out.println("加密后的长度："+Mpwd.length());
        teacher.setT_pwd(Mpwd);
        teacher.setT_name(t_name);
        teacher.setT_phone(t_phone);
        teacher.setT_email(t_email);

        teacherService.addTeacher(teacher);
    }

    @CrossOrigin
    @PostMapping(value = "api/addTBC")
    @ResponseBody
    public void addTBC(@RequestParam("t_id") String t_id,
                           @RequestParam("b_id") String b_id,
                           @RequestParam("c_id") String c_id){

        teacherService.addTBC(t_id,b_id,c_id);
    }

    // -------------------------- 修改 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/updateTeacher")
    @ResponseBody
    public void updateTeacher(@RequestParam("t_id") String t_id,
                           @RequestParam("t_pwd") String t_pwd,
                           @RequestParam("t_name") String t_name,
                           @RequestParam("t_phone") String t_phone,
                           @RequestParam("t_email") String t_email){

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(t_pwd);

        teacherService.updateTeacher(Mpwd,t_name,t_phone,t_email,t_id);
    }

    @CrossOrigin
    @PostMapping(value = "api/updateTBC")
    @ResponseBody
    public Result updateTBC(@RequestParam("t_id") String t_id,
                            @RequestParam("b_id1") String b_id1,
                            @RequestParam("c_id1") String c_id1,
                            @RequestParam("b_id") String b_id,
                            @RequestParam("c_id") String c_id){

        List<Map<String, Object>> tbclist = teacherService.findTBC(t_id,b_id,c_id);

        String flag = "yes";
        for (int i = 0; i < tbclist.size(); i++) {
            if(t_id.equals(tbclist.get(i).get("t_id")) && b_id1.equals(tbclist.get(i).get("b_id").toString()) && c_id1.equals(tbclist.get(i).get("c_id").toString())){
                flag = "no";
                break;
            }
        }
        if(flag.equals("no")){
            // 编辑重复！
            System.out.println("编辑重复!");
            return new Result(400);
        }else{
            teacherService.updateTBC(b_id1,c_id1,t_id,b_id,c_id);
            return new Result(200);
        }

    }

    // 修改密码
    @CrossOrigin
    @PostMapping(value = "api/updatePwdTch")
    @ResponseBody
    public void updatePwdTch(@RequestParam("t_id") String t_id,
                             @RequestParam("t_pwd") String t_pwd){

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(t_pwd);

        teacherService.updatePwd(Mpwd,t_id);
    }


    // -------------------------- 删除 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/deleteTeacher")
    @ResponseBody
    public void deleteTeacher(@RequestBody Object delList){

        List<Map<String,Object>> dellistmap = (List<Map<String, Object>>) delList;
        for (int i = 0; i <dellistmap.size() ; i++) {
            teacherService.deleteTeacher(dellistmap.get(i).get("t_id").toString(),dellistmap.get(i).get("b_id").toString(),dellistmap.get(i).get("c_id").toString());
        }

    }

    @CrossOrigin
    @PostMapping(value = "api/exportTchExcel")
    @ResponseBody
    // ----------------------------- 导出表格 --------------------------------
    public void exportTchExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("教师表");

        // 查询作业表数据
        List<Map<String, Object>> teacherList = teacherService.ExportTch();

        // 文件名称
        String fileName = "Teacher" + ".xlsx";

        // 开始！
        int rowNum = 1;

        // 在 Excel 中添加表头
        String[] headers={"教师号","密码","教师名称","手机号","邮箱","班级号","班级名称","课程号","课程名称",};
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {

            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        // 将其他主体数据放入表格中
        for (int i = 0; i < teacherList.size(); i++) {

            XSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(teacherList.get(i).get("t_id").toString());
            row1.createCell(1).setCellValue(teacherList.get(i).get("t_pwd").toString());
            row1.createCell(2).setCellValue(teacherList.get(i).get("t_name").toString());
            row1.createCell(3).setCellValue(teacherList.get(i).get("t_phone").toString());
            row1.createCell(4).setCellValue(teacherList.get(i).get("t_email").toString());
            row1.createCell(5).setCellValue(teacherList.get(i).get("b_id").toString());
            row1.createCell(6).setCellValue(teacherList.get(i).get("b_name").toString());
            row1.createCell(7).setCellValue(teacherList.get(i).get("c_id").toString());
            row1.createCell(8).setCellValue(teacherList.get(i).get("c_name").toString());

            rowNum++;
        }

        // 导出
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());


    }

}
