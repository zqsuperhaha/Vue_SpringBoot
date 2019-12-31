package com.zq.mywork.dao;

import com.zq.mywork.model.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherDao {

    // ---------------- 查询 ---------------------

    // 所有教师
    @Select("select * from teacher")
    List<Teacher> findAll();

    // 某个教师信息
    @Select("select * from teacher where t_id=#{t_id}")
    List<Teacher> findTchInfo(String t_id);

    // tbc 是否重复数据
    @Select("select * from tbc where t_id!=#{t_id} or b_id!=#{b_id} or c_id!=#{c_id} ")
    List<Map<String, Object>> findTBC(String t_id,String b_id,String c_id);

    // tbc 所有数据
    @Select("select * from tbc")
    List<Map<String, Object>> findAllTBC();

    // 级联菜单（对应班级的教师）
    @Select("select teacher.t_id,t_name from teacher,tbc where teacher.t_id = tbc.t_id and b_id=#{b_id}")
    List<Map<String, Object>> findBT(String b_id);

    // 分页数量
    @Select("select count(t.t_id) from teacher as t,banji as b,course as c,tbc where t.t_id=tbc.t_id and tbc.b_id = b.b_id and tbc.c_id=c.c_id order by t.t_id")
    int findAllPartCount();

    // 分页详情
    @Select("select t.*,b.*,c.* from teacher as t,banji as b,course as c,tbc where t.t_id=tbc.t_id and tbc.b_id = b.b_id and tbc.c_id=c.c_id order by t.orderby DESC limit #{0},8")
    List<Map<String, Object>> findAllPart(int page);

    // 模糊查询 数量
    @Select("select count(t.t_id) from teacher as t,banji as b,course as c,tbc where t.t_id=tbc.t_id and tbc.b_id = b.b_id and tbc.c_id=c.c_id " +
            " and (t.t_id like #{tchany} or t_pwd like #{tchany} or t_phone like #{tchany} or t_email like #{tchany} or b_name like #{tchany} or c_name like #{tchany})")
    int findSearchCount(String tchany);

    // 模糊查询
    @Select("select t.*,b.*,c.* from teacher as t,banji as b,course as c,tbc where t.t_id=tbc.t_id and tbc.b_id = b.b_id and tbc.c_id=c.c_id " +
            " and (t.t_id like #{tchany} or t_pwd like #{tchany} or t_phone like #{tchany} or t_email like #{tchany} or b_name like #{tchany} or c_name like #{tchany}) order by t.orderby DESC limit #{page},8")
    List<Map<String, Object>> findSearch(String tchany,int page);

    // ------------- 导出表格 ----------------
    @Select("select t.*,b.*,c.* from teacher as t,banji as b,course as c,tbc where t.t_id=tbc.t_id and tbc.b_id = b.b_id and tbc.c_id=c.c_id order by t.t_id")
    List<Map<String, Object>> ExportTch();

    // ------------- 添加 ----------------
    @Insert("insert into teacher(t_id,t_pwd,t_name,t_phone,t_email) values(#{t_id},#{t_pwd},#{t_name},#{t_phone},#{t_email})")
    void addTeacher(Teacher teacher);

    @Insert("insert into tbc(t_id,b_id,c_id) values(#{t_id},#{b_id},#{c_id})")
    void addTBC(String t_id,String b_id,String c_id);


    // ------------- 修改 ----------------
    @Update("update teacher set t_pwd=#{t_pwd},t_name=#{t_name},t_phone=#{t_phone},t_email=#{t_email} where t_id=#{t_id}")
    void updateTeacher(String t_pwd,String t_name,String t_phone,String t_email,String t_id);

    @Update("update tbc set b_id=#{b_id1},c_id=#{c_id1} where t_id=#{t_id} and b_id=#{b_id} and c_id=#{c_id}")
    void updateTBC(String b_id1,String c_id1,String t_id,String b_id,String c_id);

    // 修改密码
    @Update("update teacher set t_pwd=#{t_pwd} where t_id=#{t_id}")
    void updatePwd(String t_pwd,String t_id);


    // ------------- 删除 ----------------
    @Delete("delete from tbc where t_id=#{t_id} and b_id=#{b_id} and c_id=#{c_id}")
    void deleteTeacher(String t_id,String b_id,String c_id);

}
