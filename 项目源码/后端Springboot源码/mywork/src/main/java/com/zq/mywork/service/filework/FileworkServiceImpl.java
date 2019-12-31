package com.zq.mywork.service.filework;

import com.zq.mywork.dao.FileworkDao;
import com.zq.mywork.model.Filework;
import com.zq.mywork.model.SetType;
import com.zq.mywork.model.SubFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fileworkService")
public class FileworkServiceImpl implements FileworkService{

    @Autowired
    private FileworkDao fileworkDao;


    @Override
    public void addFileWork(Filework filework) {
        fileworkDao.addFileWork(filework);
    }

    @Override
    public void addFileSub(SubFile subFile) {
        fileworkDao.addFileSub(subFile);
    }

    @Override
    public List<Filework> findAllSubFiles(String w_id) {
        return fileworkDao.findAllSubFiles(w_id);
    }

    @Override
    public List<Filework> findFiles(String w_id) {
        return fileworkDao.findFiles(w_id);
    }

    @Override
    public List<Filework> findSubFiles(String w_id,String s_id) {
        return fileworkDao.findSubFiles(w_id,s_id);
    }

    @Override
    public List<SetType> findType() {
        return fileworkDao.findType();
    }

    @Override
    public void updateType(String ischeck,String type) {
        fileworkDao.updateType(ischeck,type);
    }

    @Override
    public void updateSU(String size, String unit) {
        fileworkDao.updateSU(size,unit);
    }

    @Override
    public void deleteFiles(String w_id, String s_id) {
        fileworkDao.deleteFiles(w_id,s_id);
    }
}
