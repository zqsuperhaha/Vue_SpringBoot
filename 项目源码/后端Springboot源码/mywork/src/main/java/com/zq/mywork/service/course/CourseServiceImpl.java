package com.zq.mywork.service.course;

import com.zq.mywork.dao.CourseDao;
import com.zq.mywork.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public int findAllPartCount() {
        return courseDao.findAllPartCount();
    }

    @Override
    public List<Course> findAllPart(int page) {
        return courseDao.findAllPart(page);
    }

    @Override
    public int findSearchCount(String courseany) {
        return courseDao.findSearchCount(courseany);
    }

    @Override
    public List<Course> findSearch(String courseany, int page) {
        return courseDao.findSearch(courseany,page);
    }

    @Override
    public List<Map<String, Object>> findTC(String t_id) {
        return courseDao.findTC(t_id);
    }

    @Override
    public List<Map<String, Object>> findBC(String b_id,String t_id) {
        return courseDao.findBC(b_id,t_id);
    }

    @Override
    public void addCourse(String c_id, String c_name) {
        courseDao.addCourse(c_id,c_name);
    }

    @Override
    public void updateCourse(String c_name, String c_id) {
        courseDao.updateCourse(c_name,c_id);
    }

    @Override
    public void deleteCourse(String c_id) {
        courseDao.deleteCourse(c_id);
    }
}
