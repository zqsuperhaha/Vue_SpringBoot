<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 10px">
      <el-breadcrumb-item class="breadcrumbitem"><a @click="reload()">作业列表</a></el-breadcrumb-item>
      <el-breadcrumb-item class="breadcrumbitem">作业详情</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <div style="float: left;width: 500px;margin-left: 50px;margin-right: 50px">
        <el-form :model="workrow" ref="form" align="left">

          <div v-show="isShow2">
            <el-form-item label="作业号">
              {{workrow.w_id}}
            </el-form-item>
            <el-form-item>
              <span style="margin-right: 20px">教师：{{workrow.t_name}}</span>
              <span>课程：{{workrow.c_name}}</span>
            </el-form-item>

            <el-form-item >
              布置日期
              <el-date-picker
                v-model="workrow.begindate"
                :readonly="true"
                type="datetime"
                format="yyyy-MM-dd HH:mm"
                value-format="yyyy-MM-dd HH:mm">
              </el-date-picker>
            </el-form-item>
            <el-form-item >
              截止日期
              <el-date-picker
                v-model="workrow.enddate"
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
                v-model="workrow.jianjie">
              </el-input>
            </el-form-item>

            <el-form-item label="作业文件:" align="center">
              <FileDownload :files="files"></FileDownload>
            </el-form-item>
          </div>

          <div v-show="!isShow2">

              <el-form-item label="作业号:">
                {{workrow1.w_id}}
              </el-form-item>

              <el-form-item label="班级-教师-课程"  prop="value">
                <el-cascader
                  v-model="value1"
                  :options="options">
                </el-cascader>
              </el-form-item>

              <el-form-item align="left" prop="date">
                <div class="block">日期&nbsp;&nbsp;&nbsp;
                  <label style="color: red">*&nbsp;</label>
                  <el-date-picker
                    v-model.tirm="date"
                    type="datetimerange"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :default-time="['12:00:00', '24:00:00']">
                  </el-date-picker>
                </div>
              </el-form-item>

              <el-form-item align="left" prop="jianjie">作业简介:
                <el-input
                  type="textarea"
                  :autosize="{ minRows: 2, maxRows: 4}"
                  placeholder="请输入内容(默认为无)"
                  v-model.tirm="workrow1.jianjie">
                </el-input>
              </el-form-item>

            <el-form-item>上传附件:
              <Uploadfile ref="child" :who="admin" :wid="workrow1.w_id" :id="t_id" :filelist="files"></Uploadfile>
            </el-form-item>

              <el-form-item align="right">
                <el-button @click="resetForm('form')">重置</el-button>
                <el-button type="primary" @click="updateWork">确 定</el-button>
              </el-form-item>

          </div>

        </el-form>
      </div>

      <div v-show="!doshow">
        <div style="text-align: left;margin-bottom: 10px;font-size: 15px">提交文件：</div>
        <FileDownload :files="subfiles"></FileDownload>
      </div>

      <div v-show="doshow">
        <el-link type="primary" @click="linkEdit" class="gosub">{{editText}}</el-link>
        <el-link type="primary" @click="linkEdit2" class="gosub">{{cancelEdit}}</el-link>
      </div>

    </el-card>

  </div>
</template>

<script>
  import store from '@/store'
  import moment from 'moment'
  import Uploadfile from '@/components/page/common/uploadfile'
  import FileDownload from '@/components/page/common/filedownload'

  export default {
      name: "workdetail",
      components:{FileDownload,Uploadfile},
      props:{
        workrow:{
          type:Object,
          required:true
        },
        files:{
          type:Array,
          required: true
        },
        subfiles:{
          type:Array,
          required: true
        }
      },
    inject:['reload'],

    computed:{
        doshow:function () {
          if(this.workrow.yjnums == 0){
            return true
          }else{
            return false
          }
        }
      },
      data(){
        return {
          workrow1:this.workrow,
          isShow2:true,
          date:[],
          value1:[],
          options:[],
          value2:[],
          originOption:[],
          jianjie2:'',
          admin:store.state.user.who,
          t_id:this.workrow.t_id,
          editText:'>> 编辑该项作业',
          cancelEdit:''
        }
      },
      methods:{

        // 点击编辑链接
        linkEdit(){
          this.editText = ''
          this.cancelEdit = '>> 取消编辑'
          this.workrow1 = this.workrow,
          this.isShow2 = false
          this.date = [this.workrow.begindate,this.workrow.enddate]

          this.value1 = [this.workrow.b_id,this.workrow.t_id,this.workrow.c_id]

          this.value2 = this.value1

          this.jianjie2 = this.workrow1.jianjie


          // 获取 班级-教师-课程 三级联动菜单
          this.$axios.get('/findBTC').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.options = successResponse.data
              this.originOption = this.options

            }
          })
        },

        // 取消编辑
        linkEdit2(){
          this.editText = '>> 编辑该作业'
          this.cancelEdit = ''
          this.isShow2 = true
        },

        // 修改
        updateWork(){

          // 检测是否为空
          this.workrow1.b_id=this.value1[0]
          this.workrow1.t_id=this.value1[1]
          this.workrow1.c_id=this.value1[2]

          if(!this.date){
            this.$message.warning('请补全信息！')
          }else{
            // 日期格式
            this.workrow1.begindate = moment(this.date[0]).format('YYYY-MM-DD hh:mm')
            this.workrow1.enddate = moment(this.date[1]).format('YYYY-MM-DD hh:mm')

            if(this.$refs.child.fileList.length==0){
              // 没有上传文件
              if(this.workrow1.jianjie.split(' ').join('').length == 0){
                this.$message.warning('作业简介或上传文件二者至少选一个！')
              }else{
                this.$axios.post('/updateWork','w_id='+this.workrow1.w_id+'&t_id='+this.workrow1.t_id+'&b_id='+this.workrow1.b_id+'&c_id='+this.workrow1.c_id+'&begindate='+this.workrow1.begindate+'&enddate='+this.workrow1.enddate+'&jianjie='+this.workrow1.jianjie).then(successResponse => {
                  if (successResponse && successResponse.status === 200) {
                    this.editsuccess();
                  }
                })
              }
            }else{
              // 上传了文件
              if(this.workrow1.jianjie.split(' ').join('').length == 0){
                this.form.jianjie = '暂无'
              }
              this.$axios.post('/updateWork','w_id='+this.workrow1.w_id+'&t_id='+this.workrow1.t_id+'&b_id='+this.workrow1.b_id+'&c_id='+this.workrow1.c_id+'&begindate='+this.workrow1.begindate+'&enddate='+this.workrow1.enddate+'&jianjie='+this.workrow1.jianjie).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  // 上传文件
                  this.$refs.child.$refs.upload.submit()
                  this.editsuccess();
                }
              })
            }


          }
        },

        // 编辑成功弹框
        editsuccess(){

          this.$alert('编辑作业成功！', '提示', {
            confirmButtonText: '确定',
            type: 'success',
            callback: action => {
              this.reload('worklist')
            }
          });
        },


        // 重置
        resetForm(formName) {
          this.date = [this.workrow.begindate,this.workrow.enddate]
          this.value1 = this.value2
          this.options = this.originOption
          this.workrow1.jianjie = this.jianjie2
        }

      }
    }
</script>

<style scoped>
.breadcrumbitem{
  margin-left: 10px;
  margin-top: 10px;
}
.gosub{
  margin-right: 150px;
  margin-top: 50px;
  font-size: 20px;
}
</style>
