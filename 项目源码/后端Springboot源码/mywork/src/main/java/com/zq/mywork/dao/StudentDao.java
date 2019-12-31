package com.zq.mywork.dao;

import com.zq.mywork.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDao {

    // ---------------- 查询 ---------------------

    // 所有学生
    @Select("select * from student")
    List<Student> findAll();

    // 某个学生自己的信息
    @Select("select * from student where s_id=#{s_id}")
    List<Student> findOneStu(String s_id);

    // 某个学生信息（包括班级）
    @Select("select s.*,b_name from student as s,banji as b where s.b_id = b.b_id and s.s_id=#{s_id}")
    List<Map<String, Object>> findStuInfo(String s_id);

    // 某个班级所有学生
    @Select("select * from student where b_id=#{b_id}")
    List<Student> findBjAll(String b_id);

    // 学生排名--排序
    @Update("update student set orderby=#{orderby} where s_id=#{s_id}")
    void updateRank(int orderby,String s_id);

    // 学生排名（学生）
    @Select("select s_id,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum,orderby from (" +
            " select DISTINCT s_id,s_name,student.b_id,b_name,s_phone,s_email,score,student.orderby from student,banji where student.b_id = banji.b_id and student.b_id = #{b_id} " +
            " union all " +
            " select DISTINCT student.s_id,s_name,student.b_id,b_name,s_phone,s_email,submit.w_score,student.orderby from student,submit,banji where student.s_id = submit.s_id and student.b_id = banji.b_id and student.b_id = #{b_id} " +
            " ) as ok GROUP BY s_id ORDER BY oksum DESC")
    List<Map<String, Object>> findStuRank(String b_id);

    // 学生排名（学生）模糊查询
    @Select("select s_id,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum,orderby from (" +
            " select DISTINCT s_id,s_name,student.b_id,b_name,s_phone,s_email,score,student.orderby from student,banji where student.b_id = banji.b_id and student.b_id = #{b_id} " +
            " union all " +
            " select DISTINCT student.s_id,s_name,student.b_id,b_name,s_phone,s_email,submit.w_score,student.orderby from student,submit,banji where student.s_id = submit.s_id and student.b_id = banji.b_id and student.b_id = #{b_id} " +
            " ) as ok where (s_id like #{rankany} or s_name like #{rankany} or b_name like #{rankany} or s_phone like #{rankany} or s_email like #{rankany}) GROUP BY s_id ORDER BY oksum DESC")
    List<Map<String, Object>> findStuRankSH(String b_id,String rankany);


    // 学生排名（教师）
    @Select("select s_id,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum,orderby from (" +
            " select DISTINCT s_id,s_name,student.b_id,b_name,s_phone,s_email,score,student.orderby from student,banji,tbc where student.b_id = banji.b_id and tbc.b_id = banji.b_id and tbc.t_id = #{t_id} " +
            " union all " +
            " select DISTINCT student.s_id,s_name,student.b_id,b_name,s_phone,s_email,submit.w_score,student.orderby from student,submit,banji,tbc where student.s_id = submit.s_id and student.b_id = banji.b_id and tbc.b_id = banji.b_id and tbc.t_id = #{t_id} " +
            " ) as ok GROUP BY s_id ORDER BY oksum DESC")
    List<Map<String, Object>> findTchRank(String t_id);

    // 学生排名（教师）模糊查询
    @Select("select s_id,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum,orderby from (" +
            " select DISTINCT s_id,s_name,student.b_id,b_name,s_phone,s_email,score,student.orderby from student,banji,tbc where student.b_id = banji.b_id and tbc.b_id = banji.b_id and tbc.t_id = #{t_id} " +
            " union all " +
            " select DISTINCT student.s_id,s_name,student.b_id,b_name,s_phone,s_email,submit.w_score,student.orderby from student,submit,banji,tbc where student.s_id = submit.s_id and student.b_id = banji.b_id and tbc.b_id = banji.b_id and tbc.t_id = #{t_id} " +
            " ) as ok where (s_id like #{rankany} or s_name like #{rankany} or b_name like #{rankany} or s_phone like #{rankany} or s_email like #{rankany}) GROUP BY s_id ORDER BY oksum DESC")
    List<Map<String, Object>> findTchRankSH(String t_id,String rankany);


    // 分页数量
    @Select("select count(s_id) from student")
    int findAllPartCount();

    // 分页详情
    @Select("select s_id,s_pwd,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum,orderby from ( " +
            "select DISTINCT s_id,s_pwd,s_name,student.b_id,b_name,s_phone,s_email,score,student.orderby from student,banji,tbc where student.b_id = banji.b_id and tbc.b_id = banji.b_id " +
            " union all " +
            " select DISTINCT student.s_id,s_pwd,s_name,student.b_id,b_name,s_phone,s_email,submit.w_score,student.orderby from student,submit,banji,tbc where student.s_id = submit.s_id and student.b_id = banji.b_id and tbc.b_id = banji.b_id " +
            " ) as ok GROUP BY s_id ORDER BY oksum DESC" +
            " limit #{page},8")
    List<Map<String, Object>> findAllPart(int page);

    // 模糊查询 数量
    @Select("select count(s_id) from student as s,banji as b where s.b_id=b.b_id and (s_id like #{stuany} or s_pwd like #{stuany} or s_name like #{stuany} or s_phone like #{stuany} or s_email like #{stuany} or b_name like #{stuany})")
    int findSearchCount(String stuany);

    // 模糊查询
    @Select("select s_id,s_pwd,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum,orderby from ( " +
            "select DISTINCT s_id,s_pwd,s_name,student.b_id,b_name,s_phone,s_email,score,student.orderby from student,banji,tbc where student.b_id = banji.b_id and tbc.b_id = banji.b_id " +
            " union all " +
            " select DISTINCT student.s_id,s_pwd,s_name,student.b_id,b_name,s_phone,s_email,submit.w_score,student.orderby from student,submit,banji,tbc where student.s_id = submit.s_id and student.b_id = banji.b_id and tbc.b_id = banji.b_id " +
            " ) as ok where (s_id like #{stuany} or s_pwd like #{stuany} or s_name like #{stuany} or s_phone like #{stuany} or s_email like #{stuany} or b_name like #{stuany}) GROUP BY s_id ORDER BY oksum DESC limit #{page},8")
    List<Map<String, Object>> findSearch(String stuany,int page);

    // 导出表格
    @Select("select s_id,s_pwd,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum,orderby from ( " +
            "select DISTINCT s_id,s_pwd,s_name,student.b_id,b_name,s_phone,s_email,score,student.orderby from student,banji,tbc where student.b_id = banji.b_id and tbc.b_id = banji.b_id " +
            " union all " +
            " select DISTINCT student.s_id,s_pwd,s_name,student.b_id,b_name,s_phone,s_email,submit.w_score,student.orderby from student,submit,banji,tbc where student.s_id = submit.s_id and student.b_id = banji.b_id and tbc.b_id = banji.b_id " +
            " ) as ok GROUP BY s_id ORDER BY oksum DESC")
    List<Map<String, Object>> findAllOut();

    // ------------- 添加 ----------------
    @Insert("insert into student(s_id,s_pwd,s_name,s_phone,s_email,b_id,score) values(#{s_id},#{s_pwd},#{s_name},#{s_phone},#{s_email},#{b_id},#{score})")
    void addStudent(Student student);

    // ------------- 修改 ----------------

    // 修改所有信息
    @Update("update student set s_pwd=#{s_pwd},s_name=#{s_name},s_phone=#{s_phone},s_email=#{s_email},b_id=#{b_id},score=#{score} where s_id=#{s_id}")
    void updateStudent(String s_pwd,String s_name,String s_phone,String s_email,String b_id,int score,String s_id);

    // 修改密码
    @Update("update student set s_pwd=#{s_pwd} where s_id=#{s_id}")
    void updatePwd(String s_pwd,String s_id);

    // ------------- 删除 ----------------
    @Delete("delete from student where s_id=#{s_id}")
    void deleteStudent(String s_id);




}
