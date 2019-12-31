package com.zq.mywork.dao;

import com.zq.mywork.model.SetScore;
import com.zq.mywork.model.Work;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkDao {

    // ------------- 查询 ----------------

    // 所有作业
    @Select("select * from `work`")
    List<Work> findAll();

    // 某项作业详情
    @Select("select w_id,begindate,enddate,jianjie,t_name,c_name from `work`,teacher,course where `work`.t_id = teacher.t_id and `work`.c_id=course.c_id and w_id=#{w_id}")
    List<Map<String, Object>> findWork(String w_id);

    // 某个班的作业
    @Select("select * from `work` where b_id=#{b_id}")
    List<Work> findWorkBj(String b_id);

    // 该作业对应的老师信息
    @Select("select t_phone,t_email from teacher,`work` where `work`.t_id = teacher.t_id and `work`.w_id=#{w_id}")
    List<Map<String, Object>> findWorkTch(String w_id);


    // -------- 管理员 ---------

    // 分页数量
    @Select("select count(w_id) from `work`")
    int findAllPartCount();

    // 分页详情
    @Select("select w_id,`work`.t_id,t_name,`work`.b_id,b_name,`work`.c_id,c_name,begindate,enddate,jianjie from `work`,teacher,banji,course where `work`.t_id=teacher.t_id and `work`.b_id=banji.b_id and `work`.c_id = course.c_id order by begindate DESC limit #{0},8")
    List<Map<String, Object>> findAllPart(int page);

    // 导出表详情
    @Select("select w_id,`work`.t_id,t_name,`work`.b_id,b_name,`work`.c_id,c_name,begindate,enddate,jianjie from `work`,teacher,banji,course where `work`.t_id=teacher.t_id and `work`.b_id=banji.b_id and `work`.c_id = course.c_id order by begindate DESC ")
    List<Map<String, Object>> findAllPartOut();

    // 模糊查询 数量
    @Select("select count(w_id) from `work`,teacher,banji,course " +
            " where `work`.t_id=teacher.t_id and `work`.b_id=banji.b_id and `work`.c_id = course.c_id " +
            " and (w_id like #{0} or `work`.t_id like #{0} or t_name like #{0} or `work`.b_id like #{0} or b_name like #{0} or `work`.c_id like #{0} or c_name like #{0} or begindate like #{0} or enddate like #{0} or jianjie like #{0}) " +
            " order by begindate DESC ")
    int findSearchCount(String workany);

    // 模糊查询详情
    @Select("select w_id,`work`.t_id,t_name,`work`.b_id,b_name,`work`.c_id,c_name,begindate,enddate,jianjie from `work`,teacher,banji,course " +
            " where `work`.t_id=teacher.t_id and `work`.b_id=banji.b_id and `work`.c_id = course.c_id " +
            " and (w_id like #{workany} or `work`.t_id like #{workany} or t_name like #{workany} or `work`.b_id like #{workany} or b_name like #{workany} or `work`.c_id like #{workany} or c_name like #{workany} or begindate like #{workany} or enddate like #{workany} or jianjie like #{workany}) " +
            " order by begindate DESC limit #{page},8")
    List<Map<String, Object>> findSearch(String workany,int page);

    // -------- 教师 ---------

    // 分页数量
    @Select("select count(w_id) from `work` where t_id=#{t_id}")
    int findTchWorkCount(String t_id);

    // 分页详情
    @Select("select w_id,`work`.b_id,b_name,`work`.c_id,c_name,begindate,enddate,jianjie from `work`,banji,course where `work`.t_id=#{t_id} and `work`.b_id=banji.b_id and `work`.c_id = course.c_id order by begindate DESC limit #{page},8")
    List<Map<String, Object>> findTchWork(String t_id,int page);

    // 导出表详情
    @Select("select w_id,`work`.b_id,b_name,`work`.c_id,c_name,begindate,enddate,jianjie from `work`,banji,course where `work`.t_id=#{t_id} and `work`.b_id=banji.b_id and `work`.c_id = course.c_id order by begindate DESC")
    List<Map<String, Object>> findTchWorkOut(String t_id);

    // 模糊查询 数量
    @Select("select count(w_id) from `work`,banji,course " +
            " where `work`.t_id=#{t_id} and `work`.b_id=banji.b_id and `work`.c_id = course.c_id " +
            " and (w_id like #{workany}  or `work`.b_id like #{workany} or b_name like #{workany} or `work`.c_id like #{workany} or c_name like #{workany} or begindate like #{workany} or enddate like #{workany} or jianjie like #{workany}) " +
            " order by begindate DESC ")
    int findTchWorkSHCount(String t_id,String workany);

    // 模糊查询详情
    @Select("select w_id,`work`.b_id,b_name,`work`.c_id,c_name,begindate,enddate,jianjie from `work`,banji,course " +
            " where `work`.t_id=#{t_id} and `work`.b_id=banji.b_id and `work`.c_id = course.c_id " +
            " and (w_id like #{workany}  or `work`.b_id like #{workany} or b_name like #{workany} or `work`.c_id like #{workany} or c_name like #{workany} or begindate like #{workany} or enddate like #{workany} or jianjie like #{workany}) " +
            " order by begindate DESC limit #{page},8")
    List<Map<String, Object>> findTchWorkSH(String t_id,String workany,int page);


    // 分数设置
    @Select("select * from setscore")
    List<SetScore> findScore();


    // -------- 已交 ----------

    // 已交人数 ( 作业号 + 已交人数 )
    @Select("select w_id,count(s_id) as nums from submit where isdo = '1' GROUP BY w_id")
    List<Map<String, Object>> findYjNum();

    // 已交详情 数量
    @Select("select count(submit.s_id) from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '1' and w_id=#{w_id}")
    int findYjCount(String w_id);

    // 已交详情
    @Select("select submit.s_id,s_name,b_name,s_phone,s_email,isdo,ischeck,`status`,w_score from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '1' and w_id=#{w_id} limit #{page},8")
    List<Map<String, Object>> findYj(String w_id,int page);

    // 已交详情 模糊查询 数量
    @Select("select count(submit.s_id) from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '1' and w_id=#{w_id} " +
            " and (submit.s_id like #{ynany} or s_name like  #{ynany} or b_name like  #{ynany} or s_phone like  #{ynany} or s_email like  #{ynany})")
    int findYjSHCount(String w_id,String ynany);

    // 已交详情 模糊查询
    @Select("select submit.s_id,s_name,b_name,s_phone,s_email,isdo,ischeck,`status`,w_score from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '1' and w_id=#{w_id} " +
            " and (submit.s_id like #{ynany} or s_name like  #{ynany} or b_name like  #{ynany} or s_phone like  #{ynany} or s_email like #{ynany} or w_score like #{ynany} ) limit #{page},8")
    List<Map<String, Object>> findYjSH(String w_id,String ynany,int page);


    // -------- 未交 ----------

    // 未交人数 ( 作业号 + 未交人数 )
    @Select("select w_id,count(s_id) as nums from submit where isdo = '0' GROUP BY w_id")
    List<Map<String, Object>> findNjNum();

    // 未交详情 数量
    @Select("select count(submit.s_id) from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '0' and w_id=#{w_id}")
    int findNjCount(String w_id);

    // 未交详情
    @Select("select submit.s_id,s_name,b_name,s_phone,s_email,isdo,ischeck,`status` from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '0' and w_id=#{w_id} limit #{page},8")
    List<Map<String, Object>> findNj(String w_id,int page);

    // 未交详情 模糊查询 数量
    @Select("select count(submit.s_id) from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '0' and w_id=#{w_id} " +
            " and (submit.s_id like #{ynany} or s_name like  #{ynany} or b_name like  #{ynany} or s_phone like  #{ynany} or s_email like  #{ynany})")
    int findNjSHCount(String w_id,String ynany);

    // 未交详情 模糊查询
    @Select("select submit.s_id,s_name,b_name,s_phone,s_email,isdo,ischeck,`status` from submit,student,banji where submit.s_id=student.s_id and student.b_id=banji.b_id and isdo = '0' and w_id=#{w_id} " +
            " and (submit.s_id like #{ynany} or s_name like  #{ynany} or b_name like  #{ynany} or s_phone like  #{ynany} or s_email like  #{ynany}) limit #{page},8")
    List<Map<String, Object>> findNjSH(String w_id,String ynany,int page);




    // ------------- 添加 ----------------
    @Insert("insert into `work`(w_id,t_id,b_id,c_id,begindate,enddate,jianjie) values(#{w_id},#{t_id},#{b_id},#{c_id},#{begindate},#{enddate},#{jianjie})")
    void addWork(String w_id,String t_id,String b_id,String c_id,String begindate,String enddate,String jianjie);


    // ------------- 修改 ----------------

    // ------- 管理员 -------
    @Update("update `work` set t_id=#{t_id},b_id=#{b_id},c_id=#{c_id},begindate=#{begindate},enddate=#{enddate},jianjie=#{jianjie} where w_id=#{w_id}")
    void updateWork(String t_id,String b_id,String c_id,String begindate,String enddate,String jianjie,String w_id);

    // 分数修改
    @Update("update setscore set aj=#{aj},cz=#{cz},qj=#{qj},maxpj=#{maxpj},minpj=#{minpj}")
    void updateScore(SetScore setScore);

//    // ------- 教师 -------
//    @Update("update `work` set b_id=#{b_id},c_id=#{c_id},begindate=#{begindate},enddate=#{enddate},jianjie=#{jianjie} where w_id=#{w_id}")
//    void updateTchWork(String b_id,String c_id,String begindate,String enddate,String jianjie,String w_id);


    // ------------- 删除 ----------------
    @Delete("delete from `work` where w_id=#{w_id}")
    void deleteWork(String w_id);
}
