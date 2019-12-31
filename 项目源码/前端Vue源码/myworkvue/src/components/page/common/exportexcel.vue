<template>
  <el-button @click="exportExcel" class="btn" size="medium" type="success">导出</el-button>
</template>

<script>
    export default {
        name: "exportexcel",
        props:{
          exportName:{
            type:String,
            required:true
          }
        },
        methods:{

          // 导出表
          exportExcel () {
            let path = null
            if(this.exportName=="work"){
              path = '/exportWorkExcel'
            }
            if(this.exportName=="banji"){
              path = '/exportBanjiExcel'
            }
            if(this.exportName=="course"){
              path = '/exportCourseExcel'
            }
            if(this.exportName=="tchwork"){
              path = '/exportTchWorkExcel'
            }
            if(this.exportName=="teacher"){
              path = '/exportTchExcel'
            }
            if(this.exportName=="student"){
              path = '/exportStuExcel'
            }
            if(this.exportName=="admin"){
              path = '/exportAdminExcel'
            }
            this.$axios.post(path, {}, {
              responseType: 'blob'
            }).then(res => {
              let blob = new Blob([res.data], { type: 'application/octet-stream;charset=utf-8' });
              let downloadElement = document.createElement('a');
              let href = window.URL.createObjectURL(blob); //创建下载的链接
              downloadElement.href = href;
              if(this.exportName=="work" || this.exportName=="tchwork"){
                downloadElement.download = '作业表.xlsx'; //下载后文件名
              }
              if(this.exportName=="banji"){
                downloadElement.download = '班级表.xlsx'; //下载后文件名
              }
              if(this.exportName=="course"){
                downloadElement.download = '课程表.xlsx'; //下载后文件名
              }
              if(this.exportName=="teacher"){
                downloadElement.download = '教师表.xlsx'; //下载后文件名
              }
              if(this.exportName=="student"){
                downloadElement.download = '学生表.xlsx'; //下载后文件名
              }
              if(this.exportName=="admin"){
                downloadElement.download = '管理员表.xlsx'; //下载后文件名
              }
              document.body.appendChild(downloadElement);
              downloadElement.click(); //点击下载
              document.body.removeChild(downloadElement); //下载完成移除元素
              window.URL.revokeObjectURL(href); //释放掉blob对象

              this.$message.success("导出成功！请稍等片刻即可下载")
            })
          },
        }
    }
</script>

<style scoped>

</style>
