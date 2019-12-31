<template>

  <el-upload
    :action="actionUrl()"
    :multiple="false"
    :before-upload="beforeUpload"
    :on-success="onSuccess"
    accept=".xlsx">

    <el-popover
      placement="top-start"
      title="注意"
      width="200"
      trigger="hover"
      content="导入模板为导出表格式(去除空白行与列)，是否确认导入？">
      <el-button slot="reference" size="medium" type="warning">导入</el-button>
    </el-popover>

  </el-upload>

</template>

<script>
    export default {
      name: "importexcel",
      props:{
        who:{
          type:String,
          required:true
        }
      },
      inject:['reload'],

      data(){
          return {
            fileList:[]
          }
        },
        methods:{

          // 上传前对文件的大小的判断
          beforeUpload(file) {
            let type = file.name.split(".")[1] === "xlsx";
            let size = file.size / 1024 / 1024 < 10;  // 10 MB
            if (!type) {
              this.$message.error("上传模板只能是 xlsx 格式!");
            }
            if (!size) {
              this.$message.error("上传模板大小不能超过 10MB!");
            }
            return type && size;
          },

          // 上传路径
          actionUrl(){
            let url = "http://localhost:8443/api/excelImport?who="+this.who
            return url

          },

          // 导入完成回调函数
          onSuccess(response, file, fileList) {
              if(response.code == 200){
                this.reload();
                this.$message.success("导入成功！")
              }else{
                this.$message.error("导入失败，请检查表格数据是否重复！")
              }

          }

        }
    }
</script>

<style scoped>

</style>
