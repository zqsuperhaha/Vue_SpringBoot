package com.zq.mywork.service.work;

import com.zq.mywork.model.SetScore;
import com.zq.mywork.model.Work;

import java.util.List;
import java.util.Map;

public interface WorkService {

    // ------------- 查询 ----------------

    // 所有作业
    List<Work> findAll();

    // 某项作业详情
    List<Map<String, Object>> findWork(String w_id);

    // 某个班的作业
    List<Work> findWorkBj(String b_id);

    // 该作业对应的老师信息
    List<Map<String, Object>> findWorkTch(String w_id);

    // -------- 管理员 --------

    // 分页数量
    int findAllPartCount();

    // 分页详情
    List<Map<String, Object>> findAllPart(int page);

    // 导出表详情
    List<Map<String, Object>> findAllPartOut();

    // 模糊查询 数量
    int findSearchCount(String workany);

    // 模糊查询详情
    List<Map<String, Object>> findSearch(String workany,int page);


    // -------- 教师 ---------

    // 分页数量
    int findTchWorkCount(String t_id);

    // 分页详情
    List<Map<String, Object>> findTchWork(String t_id,int page);

    // 导出表详情
    List<Map<String, Object>> findTchWorkOut(String t_id);

    // 模糊查询 数量
    int findTchWorkSHCount(String t_id,String workany);

    // 模糊查询详情
    List<Map<String, Object>> findTchWorkSH(String t_id,String workany,int page);



    // 分数设置
    List<SetScore> findScore();

    // -------- 已交 ----------

    // 已交人数 ( 作业号 + 已交人数 )
    List<Map<String, Object>> findYjNum();

    // 已交详情 数量
    int findYjCount(String w_id);

    // 已交详情
    List<Map<String, Object>> findYj(String w_id,int page);

    // 已交详情 模糊查询 数量
    int findYjSHCount(String w_id,String ynany);

    // 已交详情 模糊查询
    List<Map<String, Object>> findYjSH(String w_id,String ynany,int page);


    // -------- 未交 ----------

    // 未交人数 ( 作业号 + 未交人数 )
    List<Map<String, Object>> findNjNum();

    // 未交详情 数量
    int findNjCount(String w_id);

    // 未交详情
    List<Map<String, Object>> findNj(String w_id,int page);

    // 未交详情 模糊查询 数量
    int findNjSHCount(String w_id,String ynany);

    // 未交详情 模糊查询
    List<Map<String, Object>> findNjSH(String w_id,String ynany,int page);


    // ------------- 添加 ----------------
    void addWork(String w_id,String t_id,String b_id,String c_id,String begindate,String enddate,String jianjie);

    // ------------- 修改 ----------------
    void updateWork(String t_id,String b_id,String c_id,String begindate,String enddate,String jianjie,String w_id);

    // 分数修改
    void updateScore(SetScore setScore);

    // ------------- 删除 ----------------
    void deleteWork(String w_id);

}
