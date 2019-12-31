package com.zq.mywork.controller;

import com.zq.mywork.model.*;
import com.zq.mywork.service.admin.AdminService;
import com.zq.mywork.service.student.StudentService;
import com.zq.mywork.service.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestParam("who") String who,@RequestParam("username") String username,@RequestParam("password") String password ,HttpSession session){

        who = HtmlUtils.htmlEscape(who);
        String id = HtmlUtils.htmlEscape(username);
        String pwd = HtmlUtils.htmlEscape(password);

        // 解密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();

        System.out.println("who:"+who+" id:"+id+" pwd:"+pwd);

        if(who.equals("学生")){

            List<Student> studentList = studentService.findAll();
            for (int i = 0; i < studentList.size(); i++) {
                if(studentList.get(i).getS_id().equals(id) && Encoder.matches(pwd,studentList.get(i).getS_pwd())){

                    loginSuccess(id,session);
                    return new Result(200);
                }
            }
        }
        if(who.equals("教师")){

            List<Teacher> teacherList = teacherService.findAll();
            for (int i = 0; i < teacherList.size(); i++) {
                if(teacherList.get(i).getT_id().equals(id) && Encoder.matches(pwd,teacherList.get(i).getT_pwd())){

                    loginSuccess(id,session);
                    return new Result(200);
                }
            }
        }
        if(who.equals("管理员")){

            List<Admin> adminList = adminService.findAll();
            for (int i = 0; i < adminList.size(); i++) {
                if(adminList.get(i).getA_id().equals(id) && Encoder.matches(pwd,adminList.get(i).getA_pwd())){

                    loginSuccess(id,session);
                    return new Result(200);
                }
            }
        }


        System.out.println("用户名或密码错误！");
        return new Result(400);

    }

    // 循环校验 -- 登录成功
    public void loginSuccess(String id,HttpSession session){

        System.out.println("登录成功！");
        session.setAttribute("id",id);  // 存放好 id 到 session 里面
    }

}
