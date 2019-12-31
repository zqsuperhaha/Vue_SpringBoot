package com.zq.mywork.service.admin;

import com.zq.mywork.model.Admin;

import java.util.List;

public interface AdminService {

    // ------------- 查询 ----------------

    // 所有管理员
    List<Admin> findAll();

    // 某个管理员
    List<Admin> findAdminInfo(String a_id);

    // 分页数量
    int findAllPartCount();

    // 分页详情
    List<Admin> findAllPart(int page);

    // 模糊查询 数量
    int findSearchCount(String adminany);

    // 模糊查询
    List<Admin> findSearch(String adminany,int page);


    // ------------- 添加 ----------------
    void addAdmin(Admin admin);

    // ------------- 修改 ----------------

    // 修改所有信息
    void updateAdmin(String a_pwd,String a_name,String a_phone,String a_email,String authority,String a_id);

    // 修改密码
    void updatePwd(String a_pwd,String a_id);


    // ------------- 删除 ----------------
    void deleteAdmin(String a_id);
}
