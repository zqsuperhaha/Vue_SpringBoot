package com.zq.mywork.service.filework;

import com.zq.mywork.model.Filework;
import com.zq.mywork.model.SetType;
import com.zq.mywork.model.SubFile;

import java.util.List;

public interface FileworkService {

    // ----------------- 添加 ------------------

    // 作业文件（教师）
    void addFileWork(Filework filework);

    // 提交文件（某个学生）
    void addFileSub(SubFile subFile);

    // 提交作业文件(所有学生)
    List<Filework> findAllSubFiles(String w_id);

    // ----------------- 查询 ------------------

    // 布置作业文件
    List<Filework> findFiles(String w_id);

    // 提交作业文件
    List<Filework> findSubFiles(String w_id,String s_id);

    // 文件类型和大小
    List<SetType> findType();

    // ----------------- 修改 ------------------
    void updateType(String ischeck,String type);

    void updateSU(String size,String unit);

    // ----------------- 删除 ------------------

    void deleteFiles(String w_id,String s_id);


}
