package com.zq.mywork.service.banji;

import com.zq.mywork.model.Banji;

import java.util.List;
import java.util.Map;

public interface BanjiService {

    // ------------- 查询 ----------------

    // 所有班级
    List<Banji> findAll();

    // 筛选出有对应教师课程的班级
    List<Banji> findSelectAll();

    // 分页数量
    int findAllPartCount();

    // 某个教师的班级 (二级联动菜单)
    List<Map<String, Object>> findTB(String t_id);

    // 分页详情
    List<Banji> findAllPart(int page);

    // 模糊查询 数量
    int findSearchCount(String banjiany);

    // 模糊查询
    List<Banji> findSearch(String banjiany,int page);

    // ------------- 添加 ----------------
    void addBanji(String b_id,String b_name);

    // ------------- 修改 ----------------
    void updateBanji(String b_name,String b_id);

    // ------------- 删除 ----------------
    void deleteBanji(String b_id);
}
