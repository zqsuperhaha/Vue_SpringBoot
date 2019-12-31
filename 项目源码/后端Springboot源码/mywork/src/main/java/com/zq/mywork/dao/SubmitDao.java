package com.zq.mywork.dao;

import com.zq.mywork.model.Submit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubmitDao {

    // ----------------------- 添加 ----------------------------
    @Insert("insert into submit(w_id,s_id,subdate,enddate,pjdate,ps,pingjia,w_score,isdo,ischeck,`status`) values(#{w_id},#{s_id},#{subdate},#{enddate},#{pjdate},#{ps},#{pingjia},#{w_score},#{isdo},#{ischeck},#{status})")
    void addSubmit(Submit submit);

    // ------------------------ 查询 ---------------------------

    //  status : 正常 -- 1    过期 -- 2    重做 --- 3    补交 --- 4

    // 学生 未完成作业
    @Select("select `work`.w_id,teacher.t_id,t_name,course.c_id,c_name,begindate,submit.enddate,jianjie,isdo,ischeck,`status` from teacher,course,`work`,submit where s_id=#{s_id} and isdo = '0' " +
            " and `work`.c_id=course.c_id and `work`.t_id=teacher.t_id and `work`.w_id = submit.w_id order by begindate DESC ")
    List<Map<String, Object>> findStuNj(String s_id);

    // 学生 已完成作业
    @Select("select `work`.w_id,teacher.t_id,t_name,course.c_id,c_name,subdate,pjdate,ps,pingjia,w_score,`status`,ischeck from teacher,course,`work`,submit where s_id=#{s_id} and isdo = '1' " +
            " and `work`.c_id=course.c_id and `work`.t_id=teacher.t_id and `work`.w_id = submit.w_id  order by subdate DESC ")
    List<Map<String, Object>> findStuYj(String s_id);

    // 模糊查询 未完成
    @Select("select `work`.w_id,teacher.t_id,t_name,course.c_id,c_name,begindate,submit.enddate,jianjie,isdo,ischeck,`status` from teacher,course,`work`,submit where s_id=#{s_id} and isdo = '0' " +
            " and `work`.c_id=course.c_id and `work`.t_id=teacher.t_id and `work`.w_id = submit.w_id and (`work`.w_id like #{subany} or c_name like #{subany} or begindate like #{subany} or `work`.enddate like #{subany}) order by begindate DESC ")
    List<Map<String, Object>> findStuNjSH(String s_id,String subany);

    // 模糊查询 已完成
    @Select("select `work`.w_id,teacher.t_id,t_name,course.c_id,c_name,subdate,pjdate,ps,pingjia,w_score,isdo,ischeck,`status` from teacher,course,`work`,submit where s_id=#{s_id} and isdo = '1' " +
            " and `work`.c_id=course.c_id and `work`.t_id=teacher.t_id and `work`.w_id = submit.w_id " +
            " and (`work`.w_id like #{subany} or c_name like #{subany} or subdate like #{subany} or pjdate like #{subany}) order by subdate DESC ")
    List<Map<String, Object>> findStuYjSH(String s_id,String subany);


    // 未批改(0)  已批改(1)

    // 教师 未批改作业
    @Select("select submit.w_id,submit.s_id,s_name,c_name,subdate,submit.enddate,ps,pingjia,w_score,isdo,ischeck,`status` from submit,`work`,student,course " +
            " where isdo = '1' and ischeck = '0' and t_id = #{t_id} " +
            " and submit.w_id = `work`.w_id and `work`.c_id = course.c_id and submit.s_id = student.s_id order by subdate DESC")
    List<Map<String, Object>> findTchNp(String t_id);

    // 教师 已批改作业
    @Select("select submit.w_id,submit.s_id,s_name,c_name,subdate,submit.enddate,pjdate,ps,pingjia,w_score,isdo,ischeck,`status` from submit,`work`,student,course " +
            " where isdo = '1' and ischeck = '1' and t_id = #{t_id} " +
            " and submit.w_id = `work`.w_id and `work`.c_id = course.c_id and submit.s_id = student.s_id order by subdate DESC")
    List<Map<String, Object>> findTchYp(String t_id);

    // 模糊查询 未批改
    @Select("select submit.w_id,submit.s_id,s_name,c_name,subdate,submit.enddate,ps,pingjia,w_score,isdo,ischeck,`status` from submit,`work`,student,course " +
            " where isdo = '1' and ischeck = '0' and t_id = #{t_id} " +
            " and submit.w_id = `work`.w_id and `work`.c_id = course.c_id and submit.s_id = student.s_id " +
            " and (submit.w_id like #{pgany} or s_name like #{pgany} or c_name like #{pgany} or subdate like #{pgany}) order by subdate DESC")
    List<Map<String, Object>> findTchNpSH(String t_id,String pgany);

    // 模糊查询 已批改
    @Select("select submit.w_id,submit.s_id,s_name,c_name,subdate,submit.enddate,pjdate,ps,pingjia,w_score,isdo,ischeck,`status` from submit,`work`,student,course " +
            " where isdo = '1' and ischeck = '1' and t_id = #{t_id} " +
            " and submit.w_id = `work`.w_id and `work`.c_id = course.c_id and submit.s_id = student.s_id " +
            " and (submit.w_id like #{pgany} or s_name like #{pgany} or c_name like #{pgany} or subdate like #{pgany} or w_score like #{pgany}) order by subdate DESC")
    List<Map<String, Object>> findTchYpSH(String t_id,String pgany);


    // ------------------------ 修改 ---------------------------

    // 设为（过期）--2
    @Update("update submit set `status` = '2',w_score=w_score+#{w_score} where enddate <= now() and isdo = '0'  and `status`!='2'")
    void updateQj(int w_score);

    // 学生 个人积分 增减
    @Update("update student set score=score+#{score} where s_id=#{s_id}")
    void updateStuScore(int score,String s_id);

    // 交作业
    @Update("update submit set subdate=#{subdate},ps=#{ps},isdo = '1' where w_id=#{w_id} and s_id=#{s_id}")
    void updateAj(String subdate,String ps,String w_id,String s_id);

    // 批改作业
    @Update("update submit set pjdate=#{pjdate},w_score=#{w_score},pingjia=#{pingjia},ischeck='1' where w_id=#{w_id} and s_id=#{s_id}")
    void updatePj(String pjdate,int w_score,String pingjia,String w_id,String s_id);

    // 重做 --- status = '4'
    @Update("update submit set enddate=#{enddate},ischeck='0',isdo='0',`status`='4' where w_id=#{w_id} and s_id=#{s_id}")
    void updateCz(String enddate,String w_id,String s_id);

    // 点击补交按钮 --- status = '3' (补交)
    @Update("update submit set enddate=#{enddate},`status`='3' where w_id=#{w_id} and s_id=#{s_id}")
    void updateBj(String enddate,String w_id,String s_id);

}
