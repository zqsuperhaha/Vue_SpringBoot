package com.zq.mywork.controller;

import com.zq.mywork.model.Admin;
import com.zq.mywork.model.Banji;
import com.zq.mywork.model.Student;
import com.zq.mywork.service.admin.AdminService;
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
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ---------------------- 查询 --------------------------


    // 所有教师
    @CrossOrigin
    @GetMapping(value = "api/findAllAdmin")
    @ResponseBody
    public List<Admin> findAllAdmin(){

        return adminService.findAll();
    }

    // 某个教师信息
    @CrossOrigin
    @PostMapping(value = "api/findAdminInfo")
    @ResponseBody
    public List<Admin> findTchInfo(@RequestParam("a_id") String a_id){

        return adminService.findAdminInfo(a_id);
    }

    // 分页数量
    @CrossOrigin
    @GetMapping(value = "api/findAllAdminPartCount")
    @ResponseBody
    public int findAllAdminPartCount(){

        return adminService.findAllPartCount();
    }

    // 分页详情
    @CrossOrigin
    @PostMapping(value = "api/findAllAdminPart")
    @ResponseBody
    public List<Admin> findAllAdminPart(@RequestParam("page") int page){

        return adminService.findAllPart(page);
    }

    // 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findAdminSearchCount")
    @ResponseBody
    public int findAdminSearchCount(@RequestParam("adminany") String adminany){

        adminany = "%"+adminany+"%";
        return adminService.findSearchCount(adminany);
    }

    // 模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findAdminSearch")
    @ResponseBody
    public List<Admin> findAdminSearch(@RequestParam("adminany") String adminany,@RequestParam("page") int page){

        adminany = "%"+adminany+"%";
        return adminService.findSearch(adminany,page);
    }

    // -------------------------- 添加 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/addAdmin")
    @ResponseBody
    public void addAdmin(@RequestParam("a_id") String a_id,
                           @RequestParam("a_pwd") String a_pwd,
                           @RequestParam("a_name") String a_name,
                           @RequestParam("a_phone") String a_phone,
                           @RequestParam("a_email") String a_email,
                           @RequestParam("authority") String authority){

        Admin admin = new Admin();
        admin.setA_id(a_id);

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(a_pwd);

        admin.setA_pwd(Mpwd);
        admin.setA_name(a_name);
        admin.setA_phone(a_phone);
        admin.setA_email(a_email);
        admin.setAuthority(authority);

        adminService.addAdmin(admin);
    }

    // -------------------------- 修改 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/updateAdmin")
    @ResponseBody
    public void updateAdmin(@RequestParam("a_id") String a_id,
                              @RequestParam("a_pwd") String a_pwd,
                              @RequestParam("a_name") String a_name,
                              @RequestParam("a_phone") String a_phone,
                              @RequestParam("a_email") String a_email,
                              @RequestParam("authority") String authority){

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(a_pwd);

        adminService.updateAdmin(Mpwd,a_name,a_phone,a_email,authority,a_id);
    }


    // 修改密码
    @CrossOrigin
    @PostMapping(value = "api/updatePwdAdmin")
    @ResponseBody
    public void updatePwdAdmin(@RequestParam("a_id") String a_id,
                             @RequestParam("a_pwd") String a_pwd){

        // 加密密码
        BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
        String Mpwd = Encoder.encode(a_pwd);

        adminService.updatePwd(Mpwd,a_id);
    }

    // -------------------------- 删除 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/deleteAdmin")
    @ResponseBody
    public void deleteAdmin(@RequestBody Object delList){

        List<Map<String,Object>> dellistmap = (List<Map<String, Object>>) delList;
        for (int i = 0; i <dellistmap.size() ; i++) {
            adminService.deleteAdmin(dellistmap.get(i).get("a_id").toString());
        }

    }

    @CrossOrigin
    @PostMapping(value = "api/exportAdminExcel")
    @ResponseBody
    // ----------------------------- 导出表格 --------------------------------
    public void exportStudentExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("管理员表");

        // 查询作业表数据
        List<Admin> adminList = adminService.findAll();

        // 文件名称
        String fileName = "Admin" + ".xlsx";

        // 开始！
        int rowNum = 1;

        // 在 Excel 中添加表头
        String[] headers={"用户名","密码","姓名","手机号","邮箱","权限"};
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {

            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        // 将其他主体数据放入表格中
        for (int i = 0; i < adminList.size(); i++) {

            XSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(adminList.get(i).getA_id());
            row1.createCell(1).setCellValue(adminList.get(i).getA_pwd());
            row1.createCell(2).setCellValue(adminList.get(i).getA_name());
            row1.createCell(3).setCellValue(adminList.get(i).getA_phone());
            row1.createCell(4).setCellValue(adminList.get(i).getA_email());
            row1.createCell(5).setCellValue(adminList.get(i).getAuthority());

            rowNum++;
        }

        // 导出
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());


    }
}
