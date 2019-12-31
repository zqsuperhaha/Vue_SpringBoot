package com.zq.mywork.service.student;

import com.zq.mywork.dao.StudentDao;
import com.zq.mywork.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studentService")
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentDao studentDao;


    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public List<Student> findOneStu(String s_id) {
        return studentDao.findOneStu(s_id);
    }

    @Override
    public List<Map<String, Object>> findStuInfo(String s_id) {
        return studentDao.findStuInfo(s_id);
    }

    @Override
    public List<Student> findBjAll(String b_id) {
        return studentDao.findBjAll(b_id);
    }

    @Override
    public void updateRank(int orderby, String s_id) {
        studentDao.updateRank(orderby,s_id);
    }

    @Override
    public List<Map<String, Object>> findStuRank(String b_id) {
        return studentDao.findStuRank(b_id);
    }

    @Override
    public List<Map<String, Object>> findStuRankSH(String b_id, String rankany) {
        return studentDao.findStuRankSH(b_id,rankany);
    }

    @Override
    public List<Map<String, Object>> findTchRank(String t_id) {
        return studentDao.findTchRank(t_id);
    }

    @Override
    public List<Map<String, Object>> findTchRankSH(String t_id, String rankany) {
        return studentDao.findTchRankSH(t_id,rankany);
    }

    @Override
    public int findAllPartCount() {
        return studentDao.findAllPartCount();
    }

    @Override
    public List<Map<String, Object>> findAllPart(int page) {
        return studentDao.findAllPart(page);
    }

    @Override
    public int findSearchCount(String stuany) {
        return studentDao.findSearchCount(stuany);
    }

    @Override
    public List<Map<String, Object>> findSearch(String stuany, int page) {
        return studentDao.findSearch(stuany,page);
    }

    @Override
    public List<Map<String, Object>> findAllOut() {
        return studentDao.findAllOut();
    }

    @Override
    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }

    @Override
    public void updateStudent(String s_pwd, String s_name, String s_phone, String s_email, String b_id, int score, String s_id) {
        studentDao.updateStudent(s_pwd,s_name,s_phone,s_email,b_id,score,s_id);
    }

    @Override
    public void updatePwd(String s_pwd, String s_id) {
        studentDao.updatePwd(s_pwd,s_id);
    }

    @Override
    public void deleteStudent(String s_id) {
        studentDao.deleteStudent(s_id);
    }
}
