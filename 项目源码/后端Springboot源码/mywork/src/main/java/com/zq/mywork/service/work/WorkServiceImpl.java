package com.zq.mywork.service.work;

import com.zq.mywork.dao.WorkDao;
import com.zq.mywork.model.SetScore;
import com.zq.mywork.model.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("workService")
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkDao workDao;

    @Override
    public List<Work> findAll() {
        return workDao.findAll();
    }

    @Override
    public  List<Map<String, Object>> findWork(String w_id) {
        return workDao.findWork(w_id);
    }

    @Override
    public List<Work> findWorkBj(String b_id) {
        return workDao.findWorkBj(b_id);
    }

    @Override
    public List<Map<String, Object>> findWorkTch(String w_id) {
        return workDao.findWorkTch(w_id);
    }

    @Override
    public int findAllPartCount() {
        return workDao.findAllPartCount();
    }

    @Override
    public List<Map<String, Object>> findAllPart(int page) {
        return workDao.findAllPart(page);
    }

    @Override
    public List<Map<String, Object>> findAllPartOut() {
        return workDao.findAllPartOut();
    }

    @Override
    public int findSearchCount(String workany) {
        return workDao.findSearchCount(workany);
    }

    @Override
    public List<Map<String, Object>> findSearch(String workany, int page) {
        return workDao.findSearch(workany,page);
    }

    @Override
    public int findTchWorkCount(String t_id) {
        return workDao.findTchWorkCount(t_id);
    }

    @Override
    public List<Map<String, Object>> findTchWork(String t_id, int page) {
        return workDao.findTchWork(t_id,page);
    }

    @Override
    public List<Map<String, Object>> findTchWorkOut(String t_id) {
        return workDao.findTchWorkOut(t_id);
    }

    @Override
    public int findTchWorkSHCount(String t_id, String workany) {
        return workDao.findTchWorkSHCount(t_id,workany);
    }

    @Override
    public List<Map<String, Object>> findTchWorkSH(String t_id, String workany,int page) {
        return workDao.findTchWorkSH(t_id,workany,page);
    }

    @Override
    public List<SetScore> findScore() {
        return workDao.findScore();
    }

    @Override
    public List<Map<String, Object>> findYjNum() {
        return workDao.findYjNum();
    }

    @Override
    public int findYjCount(String w_id) {
        return workDao.findYjCount(w_id);
    }

    @Override
    public List<Map<String, Object>> findYj(String w_id, int page) {
        return workDao.findYj(w_id,page);
    }

    @Override
    public int findYjSHCount(String w_id, String ynany) {
        return workDao.findYjSHCount(w_id,ynany);
    }

    @Override
    public List<Map<String, Object>> findYjSH(String w_id, String ynany, int page) {
        return workDao.findYjSH(w_id,ynany,page);
    }

    @Override
    public List<Map<String, Object>> findNjNum() {
        return workDao.findNjNum();
    }

    @Override
    public int findNjCount(String w_id) {
        return workDao.findNjCount(w_id);
    }

    @Override
    public List<Map<String, Object>> findNj(String w_id, int page) {
        return workDao.findNj(w_id,page);
    }

    @Override
    public int findNjSHCount(String w_id, String ynany) {
        return workDao.findNjSHCount(w_id,ynany);
    }

    @Override
    public List<Map<String, Object>> findNjSH(String w_id, String ynany, int page) {
        return workDao.findNjSH(w_id,ynany,page);
    }

    @Override
    public void addWork(String w_id,String t_id, String b_id, String c_id, String begindate, String enddate, String jianjie) {
        workDao.addWork(w_id,t_id,b_id,c_id,begindate,enddate,jianjie);
    }

    @Override
    public void updateWork(String t_id,String b_id, String c_id, String begindate, String enddate, String jianjie, String w_id) {
        workDao.updateWork(t_id,b_id,c_id,begindate,enddate,jianjie,w_id);
    }

    @Override
    public void updateScore(SetScore setScore) {
        workDao.updateScore(setScore);
    }

    @Override
    public void deleteWork(String w_id) {
        workDao.deleteWork(w_id);
    }
}
