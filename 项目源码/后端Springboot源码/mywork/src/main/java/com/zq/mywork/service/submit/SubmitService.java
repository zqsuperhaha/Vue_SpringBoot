package com.zq.mywork.service.submit;

import com.zq.mywork.model.Submit;

import java.util.List;
import java.util.Map;

public interface SubmitService {

    // ----------------------- 添加 ----------------------------
    void addSubmit(Submit submit);

    // ------------------------ 查询 ---------------------------

    // 新作业 -- 0   按时交 -- 1    缺交 -- 2    重做 --- 3

    // 学生 未完成作业
    List<Map<String, Object>> findStuNj(String s_id);

    // 学生 已完成作业
    List<Map<String, Object>> findStuYj(String s_id);

    // 模糊查询 未完成
    List<Map<String, Object>> findStuNjSH(String s_id,String subany);

    // 模糊查询 已完成
    List<Map<String, Object>> findStuYjSH(String s_id,String subany);


    // 未批改(0-按时交 2-重交)  已批改(1)

    // 教师 未批改作业
    List<Map<String, Object>> findTchNp(String t_id);

    // 教师 已批改作业
    List<Map<String, Object>> findTchYp(String t_id);

    // 模糊查询 未批改
    List<Map<String, Object>> findTchNpSH(String t_id,String pgany);

    // 模糊查询 已批改
    List<Map<String, Object>> findTchYpSH(String t_id,String pgany);

    // ------------------------ 修改 ---------------------------

    // 设为 缺交--2
    void updateQj(int w_score);

    // 学生 个人积分 增减
    void updateStuScore(int score,String s_id);


    // 交作业
    void updateAj(String subdate,String ps,String w_id,String s_id);

    // 批改作业
    void updatePj(String pjdate,int w_score,String pingjia,String w_id,String s_id);

    // 重做
    void updateCz(String enddate,String w_id,String s_id);

    // 点击补交按钮
    void updateBj(String enddate,String w_id,String s_id);
}
