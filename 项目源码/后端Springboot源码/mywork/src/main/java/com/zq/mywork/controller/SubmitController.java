package com.zq.mywork.controller;

import com.zq.mywork.model.SetScore;
import com.zq.mywork.model.Submit;
import com.zq.mywork.service.filework.FileworkService;
import com.zq.mywork.service.submit.SubmitService;
import com.zq.mywork.service.work.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class SubmitController {

    @Autowired
    private SubmitService submitService;

    @Autowired
    private WorkService workService;

    @Autowired
    private FileworkService fileworkService;
    // -------------------------- 查询 ----------------------------

    // 学生 未完成作业
    @CrossOrigin
    @PostMapping(value = "api/findStuNj")
    @ResponseBody
    public List<Map<String, Object>> findStuNj(@RequestParam("s_id") String s_id){

        updateQj(); // 如果有作业缺交--先设为缺交--2
        return submitService.findStuNj(s_id);
    }

    // 学生 已完成作业
    @CrossOrigin
    @PostMapping(value = "api/findStuYj")
    @ResponseBody
    public List<Map<String, Object>> findStuYj(@RequestParam("s_id") String s_id){

        return submitService.findStuYj(s_id);
    }

    // 模糊查询 未完成作业
    @CrossOrigin
    @PostMapping(value = "api/findStuNjSH")
    @ResponseBody
    public List<Map<String, Object>> findStuNjSH(@RequestParam("s_id") String s_id,@RequestParam("subany") String subany){

        subany = "%"+subany+"%";
        return submitService.findStuNjSH(s_id,subany);
    }

    // 模糊查询 已完成作业
    @CrossOrigin
    @PostMapping(value = "api/findStuYjSH")
    @ResponseBody
    public List<Map<String, Object>> findStuYjSH(@RequestParam("s_id") String s_id,@RequestParam("subany") String subany){

        subany = "%"+subany+"%";
        return submitService.findStuYjSH(s_id,subany);
    }


    // 教师 未批改作业
    @CrossOrigin
    @PostMapping(value = "api/findTchNp")
    @ResponseBody
    public List<Map<String, Object>> findTchNp(@RequestParam("t_id") String t_id){
        return submitService.findTchNp(t_id);
    }

    // 教师 已批改作业
    @CrossOrigin
    @PostMapping(value = "api/findTchYp")
    @ResponseBody
    public List<Map<String, Object>> findTchYp(@RequestParam("t_id") String t_id){
        return submitService.findTchYp(t_id);
    }

    // 模糊查询 未批改
    @CrossOrigin
    @PostMapping(value = "api/findTchNpSH")
    @ResponseBody
    public List<Map<String, Object>> findTchNpSH(@RequestParam("t_id") String t_id,@RequestParam("pgany") String pgany){

        pgany = "%"+pgany+"%";
        return submitService.findTchNpSH(t_id,pgany);
    }

    // 模糊查询 已批改
    @CrossOrigin
    @PostMapping(value = "api/findTchYpSH")
    @ResponseBody
    public List<Map<String, Object>> findTchYpSH(@RequestParam("t_id") String t_id,@RequestParam("pgany") String pgany){

        pgany = "%"+pgany+"%";
        return submitService.findTchYpSH(t_id,pgany);
    }

    // -------------------------- 修改 ----------------------------

    // 交作业
    @CrossOrigin
    @PostMapping(value = "api/updateAj")
    @ResponseBody
    public void updateAj(@RequestParam("subdate") String subdate,
                         @RequestParam("ps") String ps,
                         @RequestParam("w_id") String w_id,
                         @RequestParam("s_id") String s_id,
                         @RequestParam("isAgain") String isAgain){

        // 如果有作业，就先将该作业的文件删除
        fileworkService.deleteFiles(w_id,s_id);

        if(isAgain.equals("0")){
            // 按时交
            List<SetScore> setScores = workService.findScore();
            int w_score = setScores.get(0).getAj();
            submitService.updateStuScore(w_score,s_id); // 加个人积分
        }

        submitService.updateAj(subdate,ps,w_id,s_id);

    }


    // 设为缺交 -- 直接扣作业分
    public void updateQj(){
        List<SetScore> setScores = workService.findScore();
        int w_score = setScores.get(0).getQj();
        submitService.updateQj(w_score);
    }

    // 批改作业
    @CrossOrigin
    @PostMapping(value = "api/updatePj")
    @ResponseBody
    public void updatePj(@RequestParam("pjdate") String pjdate,
                         @RequestParam("w_score") int w_score,
                         @RequestParam("pingjia") String pingjia,
                         @RequestParam("w_id") String w_id,
                         @RequestParam("s_id") String s_id){

        submitService.updatePj(pjdate,w_score,pingjia,w_id,s_id);
    }

    // 重做
    @CrossOrigin
    @PostMapping(value = "api/updateCz")
    @ResponseBody
    public void updateCz(@RequestParam("enddate") String enddate,
                         @RequestParam("w_id") String w_id,
                         @RequestParam("s_id") String s_id){

        List<SetScore> setScores = workService.findScore();
        int w_score = setScores.get(0).getCz();
        submitService.updateStuScore(w_score,s_id); // 减个人积分

        submitService.updateCz(enddate,w_id,s_id);
    }

    // 点击补交按钮
    @CrossOrigin
    @PostMapping(value = "api/updateBj")
    @ResponseBody
    public void updateBj(@RequestParam("enddate") String enddate,
                         @RequestParam("w_id") String w_id,
                         @RequestParam("s_id") String s_id){

        submitService.updateBj(enddate,w_id,s_id);
    }
}
