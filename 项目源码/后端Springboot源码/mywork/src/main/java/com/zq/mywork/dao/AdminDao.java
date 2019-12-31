package com.zq.mywork.dao;

import com.zq.mywork.model.Admin;
import com.zq.mywork.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminDao {

    // ---------------- 查询 ---------------------

    // 所有管理员
    @Select("select * from admin")
    List<Admin> findAll();

    // 某个管理员
    @Select("select * from admin where a_id=#{a_id}")
    List<Admin> findAdminInfo(String a_id);

    // 分页数量
    @Select("select count(a_id) from admin")
    int findAllPartCount();

    // 分页详情
    @Select("select * from admin order by orderby DESC limit #{0},8")
    List<Admin> findAllPart(int page);

    // 模糊查询 数量
    @Select("select count(a_id) from admin where a_id like #{adminany} or a_pwd like #{adminany} or a_name like #{adminany} or a_phone like #{adminany} or a_email like #{adminany} or authority like #{adminany}")
    int findSearchCount(String adminany);

    // 模糊查询
    @Select("select * from admin where a_id like #{adminany} or a_pwd like #{adminany} or a_name like #{adminany} or a_phone like #{adminany} or a_email like #{adminany} or authority like #{adminany} order by orderby DESC limit #{page},8")
    List<Admin> findSearch(String adminany,int page);


    // ------------- 添加 ----------------
    @Insert("insert into admin(a_id,a_pwd,a_name,a_phone,a_email,authority) values(#{a_id},#{a_pwd},#{a_name},#{a_phone},#{a_email},#{authority})")
    void addAdmin(Admin admin);

    // ------------- 修改 ----------------

    // 修改所有信息
    @Update("update admin set a_pwd=#{a_pwd},a_name=#{a_name},a_phone=#{a_phone},a_email=#{a_email},authority=#{authority} where a_id=#{a_id}")
    void updateAdmin(String a_pwd,String a_name,String a_phone,String a_email,String authority,String a_id);

    // 修改密码
    @Update("update admin set a_pwd=#{a_pwd} where a_id=#{a_id}")
    void updatePwd(String a_pwd,String a_id);

    // ------------- 删除 ----------------
    @Delete("delete from admin where a_id=#{a_id}")
    void deleteAdmin(String a_id);

}
