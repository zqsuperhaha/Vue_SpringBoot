package com.zq.mywork.controller;

import com.zq.mywork.model.Banji;
import com.zq.mywork.model.Course;
import com.zq.mywork.service.course.CourseService;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    // -------------------------- 查询 ----------------------------

    // 所有课程
    @CrossOrigin
    @GetMapping(value = "api/findAllCourse")
    @ResponseBody
    public List<Course> findAllCourse(){

        return courseService.findAll();
    }

    // 分页数量
    @CrossOrigin
    @GetMapping(value = "api/findAllCoursePartCount")
    @ResponseBody
    public int findAllCoursePartCount(){

        return courseService.findAllPartCount();
    }

    // 分页详情
    @CrossOrigin
    @PostMapping(value = "api/findAllCoursePart")
    @ResponseBody
    public List<Course> findAllCoursePart(@RequestParam("page") int page){

        return courseService.findAllPart(page);
    }

    // 模糊查询 数量
    @CrossOrigin
    @PostMapping(value = "api/findCourseSearchCount")
    @ResponseBody
    public int findCourseSearchCount(@RequestParam("courseany") String courseany){

        courseany = "%"+courseany+"%";
        return courseService.findSearchCount(courseany);
    }

    // 模糊查询
    @CrossOrigin
    @PostMapping(value = "api/findCourseSearch")
    @ResponseBody
    public List<Course> findCourseSearch(@RequestParam("courseany") String courseany,@RequestParam("page") int page){

        courseany = "%"+courseany+"%";
        return courseService.findSearch(courseany,page);
    }


    // -------------------------- 添加 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/addCourse")
    @ResponseBody
    public void addCourse(@RequestParam("c_id") String c_id,@RequestParam("c_name") String c_name){

        courseService.addCourse(c_id,c_name);
    }

    // -------------------------- 修改 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/updateCourse")
    @ResponseBody
    public void updateCourse(@RequestParam("c_id") String c_id,@RequestParam("c_name") String c_name){

        courseService.updateCourse(c_name,c_id);
    }


    // -------------------------- 删除 ----------------------------

    @CrossOrigin
    @PostMapping(value = "api/deleteCourse")
    @ResponseBody
    public void deleteCourse(@RequestBody Object delList){

        List<Map<String,Object>> dellistmap = (List<Map<String, Object>>) delList;
        for (int i = 0; i <dellistmap.size() ; i++) {
            courseService.deleteCourse(dellistmap.get(i).get("c_id").toString());
        }

    }

    @CrossOrigin
    @PostMapping(value = "api/exportCourseExcel")
    @ResponseBody
    // ----------------------------- 导出表格 --------------------------------
    public void exportCourseExcel(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("课程表");

        // 查询作业表数据
        List<Course> courseList = courseService.findAll();

        // 文件名称
        String fileName = "Course" + ".xlsx";

        // 开始！
        int rowNum = 1;

        // 在 Excel 中添加表头
        String[] headers={"课程号","课程名称"};
        XSSFRow row = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {

            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }


        // 将其他主体数据放入表格中
        for (int i = 0; i < courseList.size(); i++) {

            XSSFRow row1 = sheet.createRow(rowNum);

            row1.createCell(0).setCellValue(courseList.get(i).getC_id());
            row1.createCell(1).setCellValue(courseList.get(i).getC_name());

            rowNum++;
        }

        // 导出
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());


    }

}
