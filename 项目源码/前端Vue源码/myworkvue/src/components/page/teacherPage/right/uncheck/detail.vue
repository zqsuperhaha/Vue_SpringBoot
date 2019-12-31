<template>
    <div>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 10px">
        <el-breadcrumb-item><a @click="reload()">未批改列表</a></el-breadcrumb-item>
        <el-breadcrumb-item>提交详情</el-breadcrumb-item>
      </el-breadcrumb>

      <el-card>
        <div slot="header" align="left">
          <span><i class="el-icon-chat-dot-round">&nbsp;&nbsp;</i>提交详情</span>
        </div>

        <el-form :model="uncheckrow" align="left">

          <div style="float:left;width:350px;margin-left: 50px;">

            <el-form-item label="作业号">
              {{uncheckrow.w_id}}
            </el-form-item>

            <el-form-item label="得分">
              {{uncheckrow.w_score}} 分
            </el-form-item>

            <el-form-item>
              <span>课程：{{uncheckrow.c_name}}</span>
            </el-form-item>

            <el-form-item>
              <span>提交人：{{uncheckrow.s_name}}</span>
            </el-form-item>

            <el-form-item label="提交日期">
              <el-date-picker
                v-model="uncheckrow.subdate"
                :readonly="true"
                type="datetime"
                format="yyyy-MM-dd HH:mm"
                value-format="yyyy-MM-dd HH:mm">
              </el-date-picker>
            </el-form-item>

            <el-form-item align="center">
              <FileDownload :files="files"></FileDownload>
            </el-form-item>

            <el-form-item label="备注">
              <el-input
                :readonly="true"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                v-model="uncheckrow.ps">
              </el-input>
            </el-form-item>

          </div>

          <div style="float:right;width: 400px;margin-right: 50px">
            <Pjcheck :wid="uncheckrow.w_id" :sid="uncheckrow.s_id" :scorelist="scorelist"></Pjcheck>
          </div>

        </el-form>

      </el-card>

    </div>
</template>

<script>
  import Pjcheck from './pjcheck'
  import FileDownload from '@/components/page/common/filedownload'

  export default {
      name: "detail",
      components:{Pjcheck,FileDownload},
      props:{
        uncheckrow:{
          type:Object,
          required:true
        },
        files:{
          type:Array,
          required: true
        },
        scorelist:{}
      },
      inject:['reload']

  }
</script>

<style scoped>

</style>
