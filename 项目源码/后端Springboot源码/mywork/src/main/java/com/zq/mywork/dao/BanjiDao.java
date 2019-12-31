package com.zq.mywork.dao;

import com.zq.mywork.model.Banji;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BanjiDao {

    // ------------- 查询 ----------------

    // 所有班级
    @Select("select * from banji")
    List<Banji> findAll();

    // 筛选出有对应教师课程的班级
    @Select("select * from banji where b_id in (select b_id from tbc)")
    List<Banji> findSelectAll();


    // 某个教师的班级 (二级联动菜单)
    @Select("select distinct banji.b_id,b_name from banji,tbc where banji.b_id=tbc.b_id and t_id=#{t_id}")
    List<Map<String, Object>> findTB(String t_id);

    // 分页数量
    @Select("select count(b_id) from banji")
    int findAllPartCount();

    // 分页详情
    @Select("select * from banji order by orderby DESC limit #{0},8")
    List<Banji> findAllPart(int page);

    // 模糊查询 数量
    @Select("select count(b_id) from banji where b_id like #{0} or b_name like #{0}")
    int findSearchCount(String banjiany);

    // 模糊查询
    @Select("select * from banji where b_id like #{banjiany} or b_name like #{banjiany} order by orderby DESC limit #{page},8")
    List<Banji> findSearch(String banjiany,int page);


    // ------------- 添加 ----------------
    @Insert("insert into banji(b_id,b_name) values(#{b_id},#{b_name})")
    void addBanji(String b_id,String b_name);

    // ------------- 修改 ----------------
    @Update("update banji set b_name=#{b_name} where b_id=#{b_id}")
    void updateBanji(String b_name,String b_id);

    // ------------- 删除 ----------------
    @Delete("delete from banji where b_id=#{b_id}")
    void deleteBanji(String b_id);


}
