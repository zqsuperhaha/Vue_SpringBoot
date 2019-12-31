package com.zq.mywork.service.banji;


import com.zq.mywork.dao.BanjiDao;
import com.zq.mywork.model.Banji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("banjiService")
public class BanjiServiceImpl implements BanjiService{

    @Autowired
    private BanjiDao banjiDao;

    @Override
    public List<Banji> findAll() {
        return banjiDao.findAll();
    }

    @Override
    public List<Banji> findSelectAll() {
        return banjiDao.findSelectAll();
    }

    @Override
    public int findAllPartCount() {
        return banjiDao.findAllPartCount();
    }

    @Override
    public List<Map<String, Object>> findTB(String t_id) {
        return banjiDao.findTB(t_id);
    }

    @Override
    public List<Banji> findAllPart(int page) {
        return banjiDao.findAllPart(page);
    }

    @Override
    public int findSearchCount(String banjiany) {
        return banjiDao.findSearchCount(banjiany);
    }

    @Override
    public List<Banji> findSearch(String banjiany, int page) {
        return banjiDao.findSearch(banjiany,page);
    }

    @Override
    public void addBanji(String b_id, String b_name) {
        banjiDao.addBanji(b_id,b_name);
    }

    @Override
    public void updateBanji(String b_name, String b_id) {
        banjiDao.updateBanji(b_name,b_id);
    }

    @Override
    public void deleteBanji(String b_id) {
        banjiDao.deleteBanji(b_id);
    }
}
