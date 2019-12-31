package com.zq.mywork.controller;

import com.zq.mywork.model.Banji;
import com.zq.mywork.model.Student;
import com.zq.mywork.model.Submit;
import com.zq.mywork.model.Work;
import com.zq.mywork.service.banji.BanjiService;
import com.zq.mywork.service.student.StudentService;
import com.zq.mywork.service.work.WorkService;
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
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private WorkService workService;

    @Autowired
    private BanjiService banjiService;

    // 某个学生信息（包括班级）
    @CrossOrigin
    @PostMapping(value = "api/findStuInfo")
    @ResponseBody
    public List<Map<String,Object>> findStuInfo(@RequestParam("s_id") String s_id){

        return studentService.findStuInfo(s_id);
    }

    // 学生排名（学生）
    @CrossOrigin
    @PostMapping(value = "api/findStuRank")
    @ResponseBody
    public List<Map<String, Object>> findStuRank(@RequestParam("s_id") String s_id){

        List<Student> studentList = studentService.findOneStu(s_id);
        String b_id = studentList.get(0).getB_id();

        List<Map<String, Object>> listMap = studentService.findStuRank(b_id);
        for (int i = 0; i < listMap.size(); i++) {
            studentService.updateRank((i+1),listMap.get(i).get("s_id").toString());
        }
        // 更新完排名后，再次查询排名列表
        listMap = studentService.findStuRank(b_id);

        return listMap;
    }

    // 学生排名（学生）模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findStuRankSH")
    @ResponseBody
    public List<Map<String, Object>> findStuRankSH(@RequestParam("s_id") String s_id,@RequestParam("rankany") String rankany){

        List<Student> studentList = studentService.findOneStu(s_id);
        String b_id = studentList.get(0).getB_id();
        rankany = "%"+rankany+"%";
        return studentService.findStuRankSH(b_id,rankany);
    }

    // 学生排名（教师）
    @CrossOrigin
    @PostMapping(value = "api/findTchRank")
    @ResponseBody
    public List<Map<String, Object>> findTchRank(@RequestParam("t_id") String t_id){

        List<Map<String, Object>> listMap = studentService.findTchRank(t_id);
        for (int i = 0; i < listMap.size(); i++) {
            studentService.updateRank((i+1),listMap.get(i).get("s_id").toString());
        }
        return listMap;
    }

    // 学生排名（教师）模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findTchRankSH")
    @ResponseBody
    public List<Map<String, Object>> findTchRankSH(@RequestParam("t_id") String t_id,@RequestParam("rankany") String rankany){

        rankany = "%"+rankany+"%";
        return studentService.findTchRankSH(t_id,rankany);
    }


    // 分页数量
    @CrossOrigin
    @GetMapping(value = "api/findAllStuPartCount")
    @ResponseBody
    public int findAllStuPartCount(){

        return studentService.findAllPartCount();
    }

    // 分页详情
    @CrossOrigin
    @PostMapping(value = "api/findAllStuPart")
    @ResponseBody
    public List<Map<String,Object>> findAllStuPart(@RequestParam("page") int page){

        return studentService.findAllPart(page);
    }

    // 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findStuSearchCount")
    @ResponseBody
    public int findStuSearchCount(@RequestParam("stuany") String stuany){

        stuany = "%"+stuany+"%";
        return studentService.findSearchCount(stuany);
    }

    // 模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findStuSearch")
    @ResponseBody
    public List<Map<String,Object>> findStuSearch(@RequestParam("stuany") String stuany,@RequestParam("page") int page){

        stuany = "%"+stuany+"%";
        return studentService.findSearch(stuany,page);
    }

    // -------------------------- 添加 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/addStudent")
    @ResponseBody
    public void addStudent(@RequestParam("s_id") String s_id,
                           @RequestParam("s_pwd") String s_pwd,
                           @RequestParam("s_name") String s_name,
                           @RequestParam("s_phone") String s_phone,
                           @RequestParam("s_email") String s_email,
                           @RequestParam("b_id") String b_id){

        int score = 0;
        // 添加学生
        Student student = new Student();
        student.setS_id(s_id);

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(s_pwd);

        student.setS_pwd(Mpwd);
        student.setS_name(s_name);
        student.setS_phone(s_phone);
        student.setS_email(s_email);
        student.setB_id(b_id);
        student.setScore(score);

        studentService.addStudent(student);

    }

    // -------------------------- 修改 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/updateStudent")
    @ResponseBody
    public void updateStudent(@RequestParam("s_id") String s_id,
                              @RequestParam("s_pwd") String s_pwd,
                              @RequestParam("s_name") String s_name,
                              @RequestParam("s_phone") String s_phone,
                              @RequestParam("s_email") String s_email,
                              @RequestParam("b_id") String b_id){
        int score = 0;

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(s_pwd);

        studentService.updateStudent(Mpwd,s_name,s_phone,s_email,b_id,score,s_id);
    }

    // 修改密码
    @CrossOrigin
    @PostMapping(value = "api/updatePwdStu")
    @ResponseBody
    public void updatePwdStu(@RequestParam("s_id") String s_id,
                              @RequestParam("s_pwd") String s_pwd){

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(s_pwd);

        studentService.updatePwd(Mpwd,s_id);
    }


    // -------------------------- 删除 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/deleteStudent")
    @ResponseBody
    public void deleteStudent(@RequestBody Object delList){

        List<Map<String,Object>> dellistmap = (List<Map<String, Object>>) delList;
        for (int i = 0; i <dellistmap.size() ; i++) {
            studentService.deleteStudent(dellistmap.get(i).get("s_id").toString());
        }

    }

    @CrossOrigin
    @PostMapping(value = "api/exportStuExcel")
    @ResponseBody
    // ----------------------------- 导出表格 --------------------------------
    public void exportStuExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("班级表");

        // 查询作业表数据
        List<Map<String,Object>> studentList = studentService.findAllOut();

        // 文件名称
        String fileName = "Student" + ".xlsx";

        // 开始！
        int rowNum = 1;

        // 在 Excel 中添加表头
        String[] headers={"学号","密码","姓名","班级号","班级名称","手机号","邮箱","个人积分"};
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {

            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        // 将其他主体数据放入表格中
        for (int i = 0; i < studentList.size(); i++) {

            XSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(studentList.get(i).get("s_id").toString());
            row1.createCell(1).setCellValue(studentList.get(i).get("s_pwd").toString());
            row1.createCell(2).setCellValue(studentList.get(i).get("s_name").toString());
            row1.createCell(3).setCellValue(studentList.get(i).get("b_id").toString());
            row1.createCell(4).setCellValue(studentList.get(i).get("b_name").toString());
            row1.createCell(5).setCellValue(studentList.get(i).get("s_phone").toString());
            row1.createCell(6).setCellValue(studentList.get(i).get("s_email").toString());
            row1.createCell(7).setCellValue(studentList.get(i).get("oksum").toString());

            rowNum++;
        }

        // 导出
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());


    }
}
