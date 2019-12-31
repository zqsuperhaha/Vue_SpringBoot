package com.zq.mywork.dao;

import com.zq.mywork.model.Filework;
import com.zq.mywork.model.SetType;
import com.zq.mywork.model.SubFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileworkDao {

    // ----------------- 添加 ------------------

    // 作业文件（教师）
    @Insert("insert into workfile(f_id,w_id,name,size,url) values(#{f_id},#{w_id},#{name},#{size},#{url})")
    void addFileWork(Filework filework);

    // 提交文件（学生）
    @Insert("insert into subfile(f_id,w_id,s_id,name,size,url) values(#{f_id},#{w_id},#{s_id},#{name},#{size},#{url})")
    void addFileSub(SubFile subFile);

    // ----------------- 查询 ------------------

    // 布置作业文件
    @Select("select * from workfile where w_id=#{w_id}")
    List<Filework> findFiles(String w_id);

    // 提交作业文件(某个学生的)
    @Select("select * from subfile where w_id=#{w_id} and s_id=#{s_id}")
    List<Filework> findSubFiles(String w_id,String s_id);

    // 提交作业文件(所有学生)
    @Select("select * from subfile where w_id=#{w_id}")
    List<Filework> findAllSubFiles(String w_id);

    // 文件类型和大小
    @Select("select * from settype")
    List<SetType> findType();

    // ----------------- 修改 ------------------
    @Update("update settype set ischeck=#{ischeck} where type=#{type}")
    void updateType(String ischeck,String type);

    @Update("update settype set size=#{size},unit=#{unit}")
    void updateSU(String size,String unit);

    // ----------------- 删除 ------------------

    @Delete("delete from subfile where w_id=#{w_id} and s_id=#{s_id}")
    void deleteFiles(String w_id,String s_id);


}
