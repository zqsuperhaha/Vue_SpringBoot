package com.zq.mywork.service.submit;

import com.zq.mywork.dao.SubmitDao;
import com.zq.mywork.model.Submit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("submitService")
public class SubmitServiceImpl implements SubmitService {

    @Autowired
    private SubmitDao submitDao;

    @Override
    public void addSubmit(Submit submit) {
        submitDao.addSubmit(submit);
    }

    @Override
    public List<Map<String, Object>> findStuNj(String s_id) {
        return submitDao.findStuNj(s_id);
    }

    @Override
    public List<Map<String, Object>> findStuYj(String s_id) {
        return submitDao.findStuYj(s_id);
    }

    @Override
    public List<Map<String, Object>> findStuNjSH(String s_id, String subany) {
        return submitDao.findStuNjSH(s_id,subany);
    }

    @Override
    public List<Map<String, Object>> findStuYjSH(String s_id, String subany) {
        return submitDao.findStuYjSH(s_id,subany);
    }

    @Override
    public List<Map<String, Object>> findTchNp(String t_id) {
        return submitDao.findTchNp(t_id);
    }

    @Override
    public List<Map<String, Object>> findTchYp(String t_id) {
        return submitDao.findTchYp(t_id);
    }

    @Override
    public List<Map<String, Object>> findTchNpSH(String t_id, String pgany) {
        return submitDao.findTchNpSH(t_id,pgany);
    }

    @Override
    public List<Map<String, Object>> findTchYpSH(String t_id, String pgany) {
        return submitDao.findTchYpSH(t_id,pgany);
    }

    @Override
    public void updateQj(int w_score) {
        submitDao.updateQj(w_score);
    }

    @Override
    public void updateStuScore(int score, String s_id) {
        submitDao.updateStuScore(score,s_id);
    }

    @Override
    public void updateAj(String subdate, String ps, String w_id, String s_id) {
        submitDao.updateAj(subdate,ps,w_id,s_id);
    }

    @Override
    public void updatePj(String pjdate, int w_score, String pingjia,String w_id,String s_id) {
        submitDao.updatePj(pjdate,w_score,pingjia,w_id,s_id);
    }

    @Override
    public void updateCz(String enddate, String w_id, String s_id) {
        submitDao.updateCz(enddate,w_id,s_id);
    }

    @Override
    public void updateBj(String enddate, String w_id, String s_id) {
        submitDao.updateBj(enddate,w_id,s_id);
    }
}
