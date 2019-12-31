package com.zq.mywork.service.admin;

import com.zq.mywork.dao.AdminDao;
import com.zq.mywork.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public List<Admin> findAdminInfo(String a_id) {
        return adminDao.findAdminInfo(a_id);
    }

    @Override
    public int findAllPartCount() {
        return adminDao.findAllPartCount();
    }

    @Override
    public List<Admin> findAllPart(int page) {
        return adminDao.findAllPart(page);
    }

    @Override
    public int findSearchCount(String adminany) {
        return adminDao.findSearchCount(adminany);
    }

    @Override
    public List<Admin> findSearch(String adminany, int page) {
        return adminDao.findSearch(adminany,page);
    }

    @Override
    public void addAdmin(Admin admin) {
        adminDao.addAdmin(admin);
    }

    @Override
    public void updateAdmin(String a_pwd, String a_name, String a_phone, String a_email, String authority, String a_id) {
        adminDao.updateAdmin(a_pwd,a_name,a_phone,a_email,authority,a_id);
    }

    @Override
    public void updatePwd(String a_pwd, String a_id) {
        adminDao.updatePwd(a_pwd,a_id);
    }

    @Override
    public void deleteAdmin(String a_id) {
        adminDao.deleteAdmin(a_id);
    }
}
