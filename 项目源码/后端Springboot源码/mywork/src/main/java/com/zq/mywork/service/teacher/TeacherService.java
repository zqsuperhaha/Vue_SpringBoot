package com.zq.mywork.service.teacher;

import com.zq.mywork.model.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {

    // ------------- 查询 ----------------

    // 所有教师
    List<Teacher> findAll();

    // 某个教师信息
    List<Teacher> findTchInfo(String t_id);


    // tbc 所有数据
    List<Map<String, Object>> findAllTBC();

    // tbc 是否重复数据
    List<Map<String, Object>> findTBC(String t_id,String b_id,String c_id);

    // 级联菜单（对应班级的教师）
    List<Map<String, Object>> findBT(String b_id);

    // 分页数量
    int findAllPartCount();

    // 分页详情
    List<Map<String, Object>> findAllPart(int page);

    // 模糊查询 数量
    int findSearchCount(String tchany);

    // 模糊查询
    List<Map<String, Object>> findSearch(String tchany,int page);

    // ------------- 导出表格 ----------------
    List<Map<String, Object>> ExportTch();

    // ------------- 添加 ----------------
    void addTeacher(Teacher teacher);

    void addTBC(String t_id,String b_id,String c_id);


    // ------------- 修改 ----------------
    void updateTeacher(String t_pwd,String t_name,String t_phone,String t_email,String t_id);

    void updateTBC(String b_id1,String c_id1,String t_id,String b_id,String c_id);

    // 修改密码
    void updatePwd(String t_pwd,String t_id);

    // ------------- 删除 ----------------
    void deleteTeacher(String t_id,String b_id,String c_id);

}
