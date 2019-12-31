package com.zq.mywork.service.teacher;

import com.zq.mywork.dao.TeacherDao;
import com.zq.mywork.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public List<Teacher> findTchInfo(String t_id) {
        return teacherDao.findTchInfo(t_id);
    }

    @Override
    public List<Map<String, Object>> findAllTBC() {
        return teacherDao.findAllTBC();
    }

    @Override
    public List<Map<String, Object>> findTBC(String t_id,String b_id,String c_id) {
        return teacherDao.findTBC(t_id,b_id,c_id);
    }

    @Override
    public List<Map<String, Object>> findBT(String b_id) {
        return teacherDao.findBT(b_id);
    }

    @Override
    public int findAllPartCount() {
        return teacherDao.findAllPartCount();
    }

    @Override
    public List<Map<String, Object>> findAllPart(int page) {
        return teacherDao.findAllPart(page);
    }

    @Override
    public int findSearchCount(String tchany) {
        return teacherDao.findSearchCount(tchany);
    }

    @Override
    public List<Map<String, Object>> findSearch(String tchany, int page) {
        return teacherDao.findSearch(tchany,page);
    }

    @Override
    public List<Map<String, Object>> ExportTch() {
        return teacherDao.ExportTch();
    }

    @Override
    public void addTeacher(Teacher teacher) {
        teacherDao.addTeacher(teacher);
    }

    @Override
    public void addTBC(String t_id, String b_id, String c_id) {
        teacherDao.addTBC(t_id,b_id,c_id);
    }

    @Override
    public void updateTeacher(String t_pwd, String t_name, String t_phone, String t_email, String t_id) {
        teacherDao.updateTeacher(t_pwd,t_name,t_phone,t_email,t_id);
    }

    @Override
    public void updateTBC(String b_id1,String c_id1,String t_id,String b_id,String c_id) {
        teacherDao.updateTBC(b_id1,c_id1,t_id,b_id,c_id);
    }

    @Override
    public void updatePwd(String t_pwd, String t_id) {
        teacherDao.updatePwd(t_pwd,t_id);
    }

    @Override
    public void deleteTeacher(String t_id,String b_id,String c_id) {
        teacherDao.deleteTeacher(t_id,b_id,c_id);
    }

}
