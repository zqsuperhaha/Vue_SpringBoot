package com.zq.mywork.dao;

import com.zq.mywork.model.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseDao {

    // ------------- 查询 ----------------

    // 所有课程
    @Select("select * from course")
    List<Course> findAll();

    // 分页数量
    @Select("select count(c_id) from course")
    int findAllPartCount();

    // 分页详情
    @Select("select * from course order by orderby DESC limit #{0},8")
    List<Course> findAllPart(int page);

    // 模糊查询 数量
    @Select("select count(c_id) from course where c_id like #{0} or c_name like #{0}")
    int findSearchCount(String courseany);

    // 模糊查询
    @Select("select * from course where c_id like #{courseany} or c_name like #{courseany} order by orderby DESC limit #{page},8")
    List<Course> findSearch(String courseany,int page);

    // 三级联菜单（对应教师的课程）
    @Select("select course.c_id,c_name from tbc,course where course.c_id=tbc.c_id and t_id=#{t_id}")
    List<Map<String, Object>> findTC(String t_id);

    // 二级联菜单（对应班级的课程）
    @Select("select course.c_id,c_name from course,tbc where course.c_id=tbc.c_id and tbc.b_id=#{b_id} and t_id=#{t_id}")
    List<Map<String, Object>> findBC(String b_id,String t_id);


    // ------------- 添加 ----------------
    @Insert("insert into course(c_id,c_name) values(#{c_id},#{c_name})")
    void addCourse(String c_id,String c_name);

    // ------------- 修改 ----------------
    @Update("update course set c_name=#{c_name} where c_id=#{c_id}")
    void updateCourse(String c_name,String c_id);

    // ------------- 删除 ----------------
    @Delete("delete from course where c_id=#{c_id}")
    void deleteCourse(String c_id);

}
