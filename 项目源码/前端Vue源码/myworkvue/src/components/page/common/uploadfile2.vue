<template>

  <el-form-item>

    <el-upload
      name="files"
      ref="upload"
      drag
      action=""
      :http-request="uploadFile"
      :on-change="changeFiles"
      :file-list="fileList"
      :multiple="false"
      :limit="2"
      :on-exceed="handleExceed"
      :auto-upload="false"
      :on-success="onSuccess"
      :accept="oktype">

      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传{{oktype}}文件，且每个大小不超过{{maxSize}}{{maxUnit}}</div>

    </el-upload>

  </el-form-item>

</template>

<script>
    export default {
      name: "uploadfile2",
      props:['who','id','wid','filelist'],
      data(){
        return {
          filetype:{
            type:this.type,
            ischeck:this.ischeck,
            size:this.size,
            unit:this.unit
          },
          oktype:'',
          maxSize:0,
          maxUnit:'',
          fileList:this.filelist
        }
      },
      mounted:function(){

        // 查询文件类型
        this.$axios.get('/findType').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.filetype = successResponse.data
            for (let i = 0; i < this.filetype.length; i++) {
              if(this.filetype[i].ischeck == '1'){
                let t = '.'+this.filetype[i].type+','
                this.oktype += t
              }
            }
            this.maxSize = this.filetype[0].size
            this.maxUnit = this.filetype[0].unit
          }
        })
      },
      methods:{

        // 判断文件是否超出1个
        handleExceed(files, fileList){
          this.$message.warning(`当前限制选择 1 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },

        // 获取文件列表
        changeFiles(files,fileList){

          this.fileList = fileList

          // 判断类型
          let houzui = files.name.split(".")[1]
          let flag1 = false    // 这里 admin 页面注意：需要至少勾选一个类型哦~
          for (let i = 0; i <this.filetype.length ; i++) {
            if(this.filetype[i].ischeck == '1'){
              if(this.filetype[i].type == houzui){
                flag1 = true
                break
              }
            }
          }

          // 判断单位
          let size = files.size;  // B
          let flag2 = true
          let maxsize = 0
          if(this.filetype[0].unit == 'GB'){
            maxsize = this.filetype[0].size * 1024 * 1024 * 1024
          }
          if(this.filetype[0].unit == 'MB'){
            maxsize = this.filetype[0].size * 1024 * 1024
          }
          if(this.filetype[0].unit == 'KB'){
            maxsize = this.filetype[0].size * 1024
          }
          if(this.filetype[0].unit == 'B'){
            maxsize = this.filetype[0].size
          }

          if(size>maxsize){
            flag2 = false
          }

          if (!flag1) {
            this.$message.error("上传文件格式不正确!");
            this.fileList.length = 0

          }
          if (!flag2) {
            this.$message.error("上传文件大小不能超过!"+this.maxSize+this.maxUnit);
            this.fileList.length = 0
          }
        },


        // 上传文件
        uploadFile(fileObj){

          // alert(fileObj.file.name)
          let formData = new FormData();
          formData.set("file", fileObj.file);
          formData.set("w_id",this.wid);
          formData.set("who",this.who);
          formData.set("id",this.id)
          let config = {
            headers: {
              'Content-Type': 'multipart/form-data;boundary = ' + new Date().getTime()
            }
          }

          this.$axios.post('http://localhost:8443/api/uploadFile',formData,config).then(successResponse => {
            if(successResponse && successResponse.status === 200) {

              this.$refs.upload.clearFiles();
            }
          }).catch();


        },
        // 上传完成回调函数
        onSuccess(response, file, fileList) {
          if(response.code == 200){
            this.$message.success("上传成功！")
          }else{
            this.$message.error("上传失败，请再次上传！")
          }

        }

      }
    }
</script>

<style scoped>

</style>
