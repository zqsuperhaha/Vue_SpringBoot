package com.zq.mywork.service.course;

import com.zq.mywork.model.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {

    // ------------- 查询 ----------------

    // 所有课程
    List<Course> findAll();

    // 分页数量
    int findAllPartCount();

    // 分页详情
    List<Course> findAllPart(int page);

    // 模糊查询 数量
    int findSearchCount(String courseany);

    // 模糊查询
    List<Course> findSearch(String courseany,int page);

    // 三级联菜单（对应教师的课程）
    List<Map<String, Object>> findTC(String t_id);

    // 二级联菜单（对应班级的课程）
    List<Map<String, Object>> findBC(String b_id,String t_id);

    // ------------- 添加 ----------------
    void addCourse(String c_id,String c_name);

    // ------------- 修改 ----------------
    void updateCourse(String c_name,String c_id);

    // ------------- 删除 ----------------
    void deleteCourse(String c_id);
}
