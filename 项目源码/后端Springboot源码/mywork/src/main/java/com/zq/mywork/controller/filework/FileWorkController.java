package com.zq.mywork.controller.filework;

import com.zq.mywork.model.Filework;
import com.zq.mywork.model.Result;
import com.zq.mywork.model.SetType;
import com.zq.mywork.model.SubFile;
import com.zq.mywork.service.filework.FileworkService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class FileWorkController {

    @Autowired
    private FileworkService fileworkService;

    // ---------------------- 查询 --------------------------

    // 某项 布置作业 的所有文件
    @CrossOrigin
    @GetMapping(value = "api/findFiles")
    @ResponseBody
    public List<Filework> findFiles(@RequestParam("w_id") String w_id){
        return fileworkService.findFiles(w_id);
    }

    // 某项 提交作业 某个学生 的所有文件
    @CrossOrigin
    @GetMapping(value = "api/findSubFiles")
    @ResponseBody
    public List<Filework> findSubFiles(@RequestParam("w_id") String w_id,@RequestParam("s_id") String s_id){
        return fileworkService.findSubFiles(w_id,s_id);
    }

    // 某项 提交作业 所有学生 所有文件
    @CrossOrigin
    @GetMapping(value = "api/findAllSubFiles")
    @ResponseBody
    public List<Filework> findAllSubFiles(@RequestParam("w_id") String w_id){
        return fileworkService.findAllSubFiles(w_id);
    }

    // 文件类型
    @CrossOrigin
    @GetMapping(value = "api/findType")
    @ResponseBody
    public List<SetType> findType(){
        return fileworkService.findType();
    }

    // ---------------------- 上传、下载 --------------------------

    // 上传单个文件
    @CrossOrigin
    @PostMapping(value = "api/uploadFile")
    @ResponseBody
    public Result uploadFile(@RequestParam("file") MultipartFile[] uploadFiles,
                             @RequestParam("w_id") String w_id,
                             @RequestParam("who") String who,
                             @RequestParam("id") String id)
            throws IOException {

        if(uploadFiles==null){
            System.out.println("文件不存在！");
            return new Result(400);
        }
        // 生成随机数字拼接新文件名
        String val = "";
        Random random = new Random();
        for (int i = 0; i < 8 ; i++) {
            val += String.valueOf(random.nextInt(10));
        }

        for (MultipartFile uploadFile:uploadFiles) {

            // 文件名
            String fileName = uploadFile.getOriginalFilename();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");  //设置日期格式
            String newfileName = df.format(new Date())+val;

            // 文件后缀
            String suffix = fileName.substring(fileName.lastIndexOf("."));

            // 文件大小
            Double size = uploadFile.getSize()/1024.0;  // KB

            // 保存文件

            // 创建文件路径
            String uploadPath = "C:\\D\\project\\SpringBoot\\mywork\\files";
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            File path = new File(uploadDir,newfileName);
            uploadFile.transferTo(path);

            System.out.println("上传成功！");

            // 保存到数据库
            String mysql_path = "C:\\D\\project\\SpringBoot\\mywork\\files\\";

            if(who.equals("教师")||who.equals("管理员")){
                Filework filework = new Filework();
                filework.setF_id(newfileName);
                filework.setW_id(w_id);
                filework.setName(fileName);
                filework.setSize(size);
                filework.setUrl(mysql_path);
                fileworkService.addFileWork(filework);
            }else{
                // 学生
                SubFile subFile = new SubFile();
                subFile.setF_id(newfileName);
                subFile.setW_id(w_id);
                subFile.setS_id(id);
                subFile.setName(fileName);
                subFile.setSize(size);
                subFile.setUrl(mysql_path);
                fileworkService.addFileSub(subFile);
            }


        }

        return new Result(200);
    }


    // 下载单个文件
    @CrossOrigin
    @PostMapping(value = "api/download")
    @ResponseBody
    public void fileDownLoad(HttpServletResponse response,
                           @RequestParam("f_id") String f_id,@RequestParam("f_name") String f_name) throws IOException {


        // 获取项目文件夹 /files 绝对路径（项目在哪里，就获得项目的那里的绝对路径）
        String path = "C:\\D\\project\\SpringBoot\\mywork\\files\\"+f_id;

        // 获取要下载的文件对象（地址）
        File file = new File(path);

        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + f_name);
        byte[] buff = new byte[1024];
        OutputStream os = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int i = bis.read(buff);
        while (i!=-1){
            os.write(buff,0,buff.length);
            os.flush();
            i=bis.read(buff);
        }
        if(bis!=null){
            bis.close();
        }
        System.out.println("下载成功！");

    }

    // ---------------------- 修改 --------------------------

    @CrossOrigin
    @PostMapping(value = "api/updateType")
    @ResponseBody
    public void updateType(@RequestParam("typelist") String typelist,@RequestParam("size") String size,@RequestParam("unit") String unit){

        String[] typelist1 = typelist.split(",");

        List<SetType> setTypes = fileworkService.findType();

        String flag = "";
        for (int i = 0; i < setTypes.size(); i++) {
            flag = "no";
            for (int j = 0; j < typelist1.length; j++) {
                if(setTypes.get(i).getType().equals(typelist1[j])){
                    fileworkService.updateType("1",typelist1[i]);
                    flag = "yes";
                    break;
                }
            }
            if(flag.equals("no")){
                fileworkService.updateType("0",setTypes.get(i).getType());
            }
        }


        fileworkService.updateSU(size, unit);

        System.out.println("修改文件 类型、大小、单位 成功！");
    }

    // ---------------------- 删除 --------------------------

    // 删除单个文件
    @CrossOrigin
    @PostMapping(value = "api/deleteFile")
    @ResponseBody
    public Result deleteFile(HttpServletRequest request,
                             @RequestParam("filename") String filename ,
                             @RequestParam("who") String who){

        String filepath = "C:\\D\\project\\SpringBoot\\mywork\\files\\"+filename;

        File path = new File(filepath);
        if(path.exists()){
            path.delete();
            System.out.println(filename+"删除成功！");
            return new Result(200);
        }else{
            System.out.println(filename+"文件不存在");
            return new Result(400);

        }
    }
}
