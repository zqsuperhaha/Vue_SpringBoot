package com.zq.mywork.controller.filework;

import com.zq.mywork.model.*;
import com.zq.mywork.service.admin.AdminService;
import com.zq.mywork.service.banji.BanjiService;
import com.zq.mywork.service.course.CourseService;
import com.zq.mywork.service.student.StudentService;
import com.zq.mywork.service.teacher.TeacherService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// 导入表
@Controller
public class ImportExcelController {

    @Autowired
    private BanjiService banjiService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @CrossOrigin
    @PostMapping(value = "api/excelImport")
    @ResponseBody
    public Result excelImport(@RequestParam MultipartFile file,@RequestParam("who") String who) throws IOException {

        System.out.println("who="+who);
        if (file.isEmpty()) {
            return new Result(400);
        }

        //根据路径获取这个操作excel的实例
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
        //根据页面index 获取sheet页
        XSSFSheet sheet = wb.getSheetAt(0);

        XSSFRow row = null;

        String flag = "yes";
        //循环sesheet页中数据从第二行开始，第一行是标题
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

            //获取每一行数据
            row = sheet.getRow(i);

            // 从第一列开始存

            // 将这行存入数据库
            if(who.equals("banji")){
                for (int j = 0; j < 2; j++) {
                    row.getCell(j).setCellType(CellType.STRING);
                }

                String one = row.getCell(0).getStringCellValue();
                String two = row.getCell(1).getStringCellValue();

                List<Banji> banjiList = banjiService.findAll();
                for (int j = 0; j < banjiList.size(); j++) {
                    if(banjiList.get(j).getB_id().equals(one)){
                        flag="no";
                        break;
                    }
                }
                if(flag.equals("no")){
                    break;
                }else{
                    banjiService.addBanji(one,two);
                }

            }
            if(who.equals("course")){
                for (int j = 0; j < 2; j++) {
                    row.getCell(j).setCellType(CellType.STRING);
                }

                String one = row.getCell(0).getStringCellValue();
                String two = row.getCell(1).getStringCellValue();

                List<Course> courseList = courseService.findAll();
                for (int j = 0; j < courseList.size(); j++) {
                    if(courseList.get(j).getC_id().equals(one)){
                        flag="no";
                        break;
                    }
                }
                if(flag.equals("no")){
                    break;
                }else{
                    courseService.addCourse(one,two);
                }

            }

            if(who.equals("admin")){
                for (int j = 0; j < 6; j++) {
                    row.getCell(j).setCellType(CellType.STRING);
                }

                String one = row.getCell(0).getStringCellValue();   // a_id
                String two = row.getCell(1).getStringCellValue();   // a_pwd
                String three = row.getCell(2).getStringCellValue();   // a_name
                String four = row.getCell(3).getStringCellValue();   // a_phone
                String five = row.getCell(4).getStringCellValue();   // a_email
                String six = row.getCell(5).getStringCellValue();   // authority

                List<Admin> adminList = adminService.findAll();
                for (int j = 0; j < adminList.size(); j++) {
                    if(adminList.get(j).getA_id().equals(one)){
                        flag="no";
                        break;
                    }
                }
                if(flag.equals("no")){
                    break;
                }else{

                    Admin admin = new Admin();
                    admin.setA_id(one);
                    admin.setA_pwd(two);
                    admin.setA_name(three);
                    admin.setA_phone(four);
                    admin.setA_email(five);
                    admin.setAuthority(six);

                    adminService.addAdmin(admin);
                }

            }

            if(who.equals("teacher")){
                for (int j = 0; j < 8; j++) {
                    row.getCell(j).setCellType(CellType.STRING);
                }

                String one = row.getCell(0).getStringCellValue();   // t_id
                String two = row.getCell(1).getStringCellValue();   // t_pwd
                String three = row.getCell(2).getStringCellValue(); // t_name
                String four = row.getCell(3).getStringCellValue();  // t_phone
                String five = row.getCell(4).getStringCellValue();  // t_email
                String six = row.getCell(5).getStringCellValue();   // b_id
                String seven = row.getCell(7).getStringCellValue(); // c_id


                List<Teacher> teacherList = teacherService.findAll();

                for (int j = 0; j < teacherList.size(); j++) {
                    if(teacherList.get(j).getT_id().equals(one)){
                        List<Map<String, Object>> tbclist = teacherService.findAllTBC();
                        for (int k = 0; k < tbclist.size(); k++) {
                            if(one.equals(tbclist.get(k).get("t_id").toString())){
                                flag = "yes2";
                                if(six.equals(tbclist.get(k).get("b_id").toString()) && seven.equals(tbclist.get(k).get("c_id").toString())){
                                    flag = "no";
                                    break;
                                }
                            }
                        }
                        if(flag.equals("no")){
                            break;
                        }
                    }
                }
                if(flag.equals("no")){
                    break;
                }else{

                    if(flag.equals("yes")){
                        Teacher teacher = new Teacher();
                        teacher.setT_id(one);
                        teacher.setT_pwd(two);
                        teacher.setT_name(three);
                        teacher.setT_phone(four);
                        teacher.setT_email(five);

                        teacherService.addTeacher(teacher);

                        teacherService.addTBC(one,six,seven);

                    }else{
                        teacherService.addTBC(one,six,seven);
                    }

                }

            }

            if(who.equals("student")){
                for (int j = 0; j < 8; j++) {
                    row.getCell(j).setCellType(CellType.STRING);
                }

                String one = row.getCell(0).getStringCellValue();   // s_id
                String two = row.getCell(1).getStringCellValue();   // s_pwd
                String three = row.getCell(2).getStringCellValue();   // s_name
                String four = row.getCell(5).getStringCellValue();   // s_phone
                String five = row.getCell(6).getStringCellValue();   // s_email
                String six = row.getCell(3).getStringCellValue();   // b_id
                String seven = "0";   // score

                List<Student> studentList = studentService.findAll();
                for (int j = 0; j < studentList.size(); j++) {
                    if(studentList.get(j).getS_id().equals(one)){
                        flag="no";
                        break;
                    }
                }
                if(flag.equals("no")){
                    break;
                }else{
                    Student student = new Student();
                    student.setS_id(one);
                    student.setS_pwd(two);
                    student.setS_name(three);
                    student.setS_phone(four);
                    student.setS_email(five);
                    student.setB_id(six);
                    student.setScore(Integer.valueOf(seven));

                    studentService.addStudent(student);
                }

            }

        }

        if(flag.equals("no")){
            System.out.println("数据重复！导入失败");
            return new Result(400);
        }else{

            System.out.println("导入成功！");
            return new Result(200);
        }


    }


}

