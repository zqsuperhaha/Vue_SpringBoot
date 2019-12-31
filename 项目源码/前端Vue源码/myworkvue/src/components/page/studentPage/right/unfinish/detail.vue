<template>
    <div>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 10px">
        <el-breadcrumb-item><a  @click="reload()">作业列表</a></el-breadcrumb-item>
        <el-breadcrumb-item v-model="nowAddress">{{nowAddress}}</el-breadcrumb-item>
      </el-breadcrumb>

      <el-tabs type="border-card" v-model="activeName" ref="tabs" @tab-click="handleClick">

        <el-tab-pane name="first">

          <span slot="label"><i class="el-icon-chat-dot-round">&nbsp;&nbsp;</i>作业详情</span>

          <div style="float: left;width: 420px;margin-left: 50px;margin-right: 50px">
            <el-form :model="unfinishrow" ref="form" align="left">
              <el-form-item label="作业号">
                {{unfinishrow.w_id}}
              </el-form-item>
              <el-form-item>
                <span style="margin-right: 20px">教师：{{unfinishrow.t_name}}</span>
                <span>课程：{{unfinishrow.c_name}}</span>
              </el-form-item>

              <el-form-item >
                布置日期
                <el-date-picker
                  v-model="unfinishrow.begindate"
                  :readonly="true"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm"
                  value-format="yyyy-MM-dd HH:mm">
                </el-date-picker>
              </el-form-item>
              <el-form-item >
                截止日期
                <el-date-picker
                  v-model="unfinishrow.enddate"
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
                  v-model="unfinishrow.jianjie">
                </el-input>
              </el-form-item>
            </el-form>
          </div>

          <FileDownload :files="files"></FileDownload>

          <div v-show="doshow">
            <el-link icon="el-icon-edit" type="primary" @click="linkSub" class="gosub">交作业</el-link>
          </div>

          <div v-show="!doshow" style="margin-top: 50px;margin-right: 100px">
            <div class="tchclass">请联系相应教师进行补交：</div>
            <i class="el-icon-eleme">&nbsp;&nbsp;邮箱：{{tchinfo.t_email}}</i>
            <div class="tchclass"></div>
            <i class="el-icon-phone-outline">&nbsp;&nbsp;联系电话：{{tchinfo.t_phone}}</i>
          </div>

        </el-tab-pane>

        <el-tab-pane name="second">
          <span slot="label"><i class="el-icon-edit">&nbsp;&nbsp;</i>交作业</span>
          <Submit :w_id="unfinishrow.w_id" :isAgain="isAgain"></Submit>
        </el-tab-pane>

      </el-tabs>


    </div>

</template>

<script>
    import Submit from '@/components/page/common/submit'
    import FileDownload from '@/components/page/common/filedownload'
    export default {
      name: "detail",
      components:{Submit,FileDownload},
      props:{
          unfinishrow:{
            type:Object,
            required:true
          },
          files:{
            type:Array,
            required: true
          },
          tchinfo:{}
        },
      inject:['reload'],

      computed:{
        doshow:function () {
          if(this.unfinishrow.status=='2'){
            this.$refs.tabs.$children[0].$refs.tabs[1].style.display = 'none'
            return false
          }else{
            return true
          }
        }
      },
      data(){
        return {
          formLabelWidth: '130px',
          linkherf:'',
          activeName:'first',
          nowAddress:'作业详情',
          isAgain:'0'
        }
      },
      methods:{

        handleClick(tab, event) {
          if(this.activeName=='first'){
            this.nowAddress = '作业详情'
          }else{
            this.nowAddress = '交作业'
          }
        },
        linkSub() {
          this.activeName = 'second'
          this.nowAddress = '交作业'
        }

      }
    }
</script>

<style scoped>
  .gosub{
    margin-right: 150px;
    margin-top: 50px;
    font-size: 20px;
  }
  .tchclass{
    margin-top: 20px;
    margin-bottom: 20px;
    font-size: 18px;
    color: rgba(255, 114, 26, 0.73);
  }
</style>
