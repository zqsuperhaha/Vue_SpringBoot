<template>
  <div>
    <div style="float: left;width: 420px;margin-left: 50px;margin-right: 50px">
      <el-form align="left">
        <el-form-item label="作业号">
          {{originList.w_id}}
        </el-form-item>
        <el-form-item>
          <span style="margin-right: 20px">教师：{{originList.t_name}}</span>
          <span>课程：{{originList.c_name}}</span>
        </el-form-item>

        <el-form-item >
          布置日期
          <el-date-picker
            v-model="originList.begindate"
            :readonly="true"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm">
          </el-date-picker>
        </el-form-item>
        <el-form-item >
          截止日期
          <el-date-picker
            v-model="originList.enddate"
            :readonly="true"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="作业简介">
          <el-input
            :readonly="true"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            v-model="originList.jianjie">
          </el-input>
        </el-form-item>
      </el-form>
    </div>

    <FileDownload :files="originFiles"></FileDownload>
  </div>

</template>

<script>
  import FileDownload from '@/components/page/common/filedownload'
  export default {
      name: "origin",
      props:{
        originList:{
          type:Object,
          required:true
        },
        originFiles:{
          type:Array,
          required:true
        }
      },
      components:{FileDownload},
      data(){
        return {

        }
      }
    }
</script>

<style scoped>

</style>
