<template>
    <div>

      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 10px">
        <el-breadcrumb-item><a  @click="reload()">提交列表</a></el-breadcrumb-item>
        <el-breadcrumb-item v-model="nowAddress">{{nowAddress}}</el-breadcrumb-item>

      </el-breadcrumb>

      <el-tabs type="border-card" v-model="activeName" ref="tabs" @tab-click="handleClick">

        <el-tab-pane name="first">

          <span slot="label"><i class="el-icon-chat-dot-round">&nbsp;&nbsp;</i>提交详情</span>

          <el-form :model="finishrow" ref="form" align="left">

            <div style="float:left;width:350px;margin-left: 50px;">

              <el-form-item label="作业号">
                {{finishrow.w_id}}
              </el-form-item>

              <el-form-item label="得分">
                {{finishrow.w_score}} 分
              </el-form-item>

              <el-form-item>
                <span style="margin-right: 20px">教师：{{finishrow.t_name}}</span>
                <span>课程：{{finishrow.c_name}}</span>
              </el-form-item>

              <el-form-item >
                提交日期
                <el-date-picker
                  v-model="finishrow.subdate"
                  :readonly="true"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm"
                  value-format="yyyy-MM-dd HH:mm">
                </el-date-picker>
              </el-form-item>

              <el-form-item label="备注">
                <el-input
                  :readonly="true"
                  type="textarea"
                  :autosize="{ minRows: 2, maxRows: 4}"
                  v-model="finishrow.ps">
                </el-input>
              </el-form-item>

              <el-form-item align="center">
                <FileDownload :files="files"></FileDownload>
              </el-form-item>

            </div>

            <div style="float:right;width: 400px;margin-right: 50px" v-show="pjshow">
              <el-form-item >
                评价日期
                <el-date-picker
                v-model="finishrow.pjdate"
                :readonly="true"
                type="datetime"
                format="yyyy-MM-dd HH:mm"
                value-format="yyyy-MM-dd HH:mm">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="评语：">
                <el-input
                  :readonly="true"
                  type="textarea"
                  :autosize="{ minRows: 2, maxRows: 4}"
                  v-model="finishrow.pingjia">
                </el-input>
              </el-form-item>
            </div>

            <div style="float:right;width: 400px;margin-right: 50px">
              <el-link icon="el-icon-chat-dot-round" type="primary" @click="linkOrigin" class="gosub">查看原作业</el-link>
            </div>

            <div style="float:right;width: 400px;margin-right: 50px" v-show="!pjshow">
              <el-link icon="el-icon-edit" type="primary" @click="linkSub" class="gosub">重新提交</el-link>
            </div>

          </el-form>

        </el-tab-pane>

        <el-tab-pane name="second">
          <span slot="label"><i class="el-icon-chat-dot-round">&nbsp;&nbsp;</i>查看原作业</span>
          <Origin :originList="originList" :originFiles="originFiles"></Origin>
        </el-tab-pane>

        <el-tab-pane name="third">
          <span slot="label"><i class="el-icon-edit">&nbsp;&nbsp;</i>重新提交</span>
          <Submit :w_id="finishrow.w_id" :isAgain="isAgain"></Submit>
        </el-tab-pane>

      </el-tabs>


    </div>
</template>

<script>
  import FileDownload from '@/components/page/common/filedownload'
  import Submit from '@/components/page/common/submit'
  import Origin from './origin'
    export default {
      name: "detail",
      components:{FileDownload,Submit,Origin},
      props:{
        finishrow:{
          type:Object,
          required:true
        },
        files:{
          type:Array,
          required: true
        }
      },
      inject:['reload'],

      computed:{
        pjshow:function () {
          if(!this.finishrow.pingjia){
            return false
          }else{
            this.$refs.tabs.$children[0].$refs.tabs[2].style.display = 'none'
            return true
          }
        }
      },
      data(){
        return {
          activeName:'first',
          nowAddress:'提交详情',
          isAgain:'1',
          originList:{
            type:Object
          },
          originFiles:[],
        }
      },

      methods:{

        // 切换 Tab
        handleClick(tab, event) {
          if(this.activeName=='first'){
            this.nowAddress = '提交详情'
          }else if(this.activeName == 'second'){
            this.nowAddress = '查看原作业'
            this.getWorkFile()
          }else{
            this.nowAddress = '重新提交'
          }
        },
        linkOrigin(){
          this.activeName = 'second'
          this.nowAddress = '查看原作业'
          this.getWorkFile()
        },
        linkSub() {
          this.activeName = 'third'
          this.nowAddress = '重新提交'
        },

        // 获取原作业详情和文件
        getWorkFile(){

          // 查询这项作业详情
          this.$axios.post('/findWork','w_id='+this.finishrow.w_id).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              let list = successResponse.data
              this.originList = list[0]
            }
          })

          this.originFiles.length = 0
          // 查询这项作业的文件
          this.$axios.get('/findFiles?w_id='+this.finishrow.w_id).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              let files = successResponse.data
              for (let i = 0; i < files.length; i++) {
                this.originFiles.push(files[i])
              }
            }
          })
        }

      }
    }
</script>

<style scoped>
  .gosub{
    margin-top: 50px;
    font-size: 20px;
  }

</style>
