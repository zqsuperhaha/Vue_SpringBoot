<template>
    <div>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 10px">
        <el-breadcrumb-item><a  @click="reload()">已批改列表</a></el-breadcrumb-item>
        <el-breadcrumb-item>批改详情</el-breadcrumb-item>
      </el-breadcrumb>

      <el-card>
        <div slot="header" align="left">
          <span><i class="el-icon-chat-dot-round">&nbsp;&nbsp;</i>批改详情</span>
        </div>

        <el-form :model="checkedrow" align="left">

          <div style="float:left;width: 320px;margin-left: 45px" v-show="isView">

            <el-form-item label="作业号">
              {{checkedrow.w_id}}
            </el-form-item>

            <el-form-item label="评价日期">
              <el-date-picker
                v-model="checkedrow.pjdate"
                :readonly="true"
                type="datetime"
                format="yyyy-MM-dd HH:mm"
                value-format="yyyy-MM-dd HH:mm">
              </el-date-picker>
            </el-form-item>

            <el-form-item label="得分">
              {{checkedrow.w_score}} 分
            </el-form-item>

            <el-form-item label="评语">
              <el-input
                :readonly="true"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                v-model="checkedrow.pingjia">
              </el-input>
            </el-form-item>

            <el-form-item>
              <el-popover
                placement="top"
                width="160"
                v-model="visible">
                <p>确认重新批改吗？</p>
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                  <el-button type="primary" size="mini" @click="linkSub">确定</el-button>
                </div>
                <el-link slot="reference" icon="el-icon-edit" type="primary" class="gosub">重新批改</el-link>
              </el-popover>
            </el-form-item>

          </div>

          <div style="float:left;width: 320px;margin-top:10px;margin-left: 45px" v-show="!isView">
            <Pjcheck :wid="checkedrow.w_id" :sid="checkedrow.s_id" :scorelist="scorelist"></Pjcheck>
          </div>

          <div style="float:right;width:350px;margin-right: 70px;">

            <el-form-item>
              <span>课程：{{checkedrow.c_name}}</span>
            </el-form-item>

            <el-form-item>
              <span>提交人：{{checkedrow.s_name}}</span>
            </el-form-item>

            <el-form-item label="提交日期">

              <el-date-picker
                v-model="checkedrow.subdate"
                :readonly="true"
                type="datetime"
                format="yyyy-MM-dd HH:mm"
                value-format="yyyy-MM-dd HH:mm">
              </el-date-picker>
            </el-form-item>

            <el-form-item label="提交文件：" align="center">
              <FileDownload :files="files"></FileDownload>
            </el-form-item>

            <el-form-item label="备注">
              <el-input
                :readonly="true"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                v-model="checkedrow.ps">
              </el-input>
            </el-form-item>

          </div>

        </el-form>

      </el-card>

    </div>
</template>

<script>
  import moment from 'moment'
  import Pjcheck from '../uncheck/pjcheck'
  import FileDownload from '@/components/page/common/filedownload'

    export default {
      name: "detail",
      components:{Pjcheck,FileDownload},
      props:{
        checkedrow:{
          type:Object,
          required:true
        },
        files:{
          type:Array,
          required: true
        },
        scorelist:{}
      },
      inject:['reload'],

      data(){
        return {
          isView:true,
          visible:false
        }
      },
      methods:{
        linkSub(){
          this.visible=false
          this.isView = false
        }
      }
    }
</script>

<style scoped>
  .gosub{
    margin-left: 45px;
    margin-top: 20px;
    font-size: 20px;
  }
</style>
