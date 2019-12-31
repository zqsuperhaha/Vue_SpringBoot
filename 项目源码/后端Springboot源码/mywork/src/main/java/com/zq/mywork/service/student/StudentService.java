package com.zq.mywork.service.student;

import com.zq.mywork.model.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    // ------------- 查询 ----------------

    // 所有学生
    List<Student> findAll();

    // 某个学生自己的信息
    List<Student> findOneStu(String s_id);

    // 某个学生信息（包括班级）
    List<Map<String, Object>> findStuInfo(String s_id);

    // 某个班级所有学生
    List<Student> findBjAll(String b_id);

    // 学生排名--排序
    void updateRank(int orderby,String s_id);

    // 学生排名（学生）
    List<Map<String, Object>> findStuRank(String b_id);

    // 学生排名（学生）模糊查询
    List<Map<String, Object>> findStuRankSH(String b_id,String rankany);

    // 学生排名（教师）
    List<Map<String, Object>> findTchRank(String t_id);

    // 学生排名（教师）模糊查询
    List<Map<String, Object>> findTchRankSH(String t_id,String rankany);

    // 分页数量
    int findAllPartCount();

    // 分页详情
    List<Map<String, Object>> findAllPart(int page);

    // 模糊查询 数量
    int findSearchCount(String stuany);

    // 模糊查询
    List<Map<String, Object>> findSearch(String stuany,int page);

    // 导出表格
    List<Map<String, Object>> findAllOut();

    // ------------- 添加 ----------------
    void addStudent(Student student);

    // ------------- 修改 ----------------

    // 修改所有信息
    void updateStudent(String s_pwd,String s_name,String s_phone,String s_email,String b_id,int score,String s_id);

    // 修改密码
    void updatePwd(String s_pwd,String s_id);

    // ------------- 删除 ----------------
    void deleteStudent(String s_id);
}
