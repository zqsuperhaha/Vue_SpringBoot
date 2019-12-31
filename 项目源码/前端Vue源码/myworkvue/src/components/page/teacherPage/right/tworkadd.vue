<template>
    <div>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>布置作业</span>
        </div>
        <div>
          <el-form :model="form" ref="form" align="left">

            <el-form-item label="作业号:" :label-width="formLabelWidth">
              {{w_id}}
            </el-form-item>

            <el-form-item label="布置日期" :label-width="formLabelWidth">
              <div class="block" >
                <el-date-picker
                  v-model="form.begindate"
                  :readonly="true"
                  type="datetime"
                  format="yyyy-MM-dd HH:mm"
                  value-format="yyyy-MM-dd HH:mm">
                </el-date-picker>
              </div>
            </el-form-item>

            <el-form-item label="截止日期" :label-width="formLabelWidth" prop="enddate">
              <div class="block" >
                <label style="color: red">*&nbsp;</label>
                <el-date-picker
                  v-model="form.enddate"
                  type="datetime"
                  @blur="onBlur"
                  placeholder="选择日期时间"
                  default-time="12:00:00"
                  format="yyyy-MM-dd HH:mm"
                  value-format="yyyy-MM-dd HH:mm">
                </el-date-picker>
              </div>
            </el-form-item>

            <el-form-item label="班级-课程" :label-width="formLabelWidth" prop="value">
              <label style="color: red">*&nbsp;</label>
              <el-cascader
                v-model="form.value"
                :options="options">
              </el-cascader>
            </el-form-item>

            <el-form-item align="left" :label-width="'60px'" prop="jianjie">作业简介:
              <label style="color: red">*&nbsp;</label>
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 10}"
                placeholder="请输入内容"
                v-model.tirm="form.jianjie">
              </el-input>
            </el-form-item>

            <el-form-item style="margin-left: 60px">上传附件:
              <Uploadfile ref="child" :who="teacher" :wid="w_id" :id="t_id"></Uploadfile>
            </el-form-item>

            <el-form-item align="right">
              <el-button @click="resetForm('form')">重置</el-button>
              <el-button type="primary" @click="addWork">确 定</el-button>
            </el-form-item>

          </el-form>
        </div>
      </el-card>
    </div>
</template>

<script>
    import store from '@/store'
    import moment from 'moment'
    import Uploadfile from '@/components/page/common/uploadfile'

    export default {
      name: "tworkadd",
      components:{Uploadfile},
      inject:['reload'],

      data() {

        return {
          form: {
            t_id:'',
            b_id:'',
            c_id:'',
            date:'',
            value:[],
            begindate:'',
            enddate:'',
            jianjie:'',
            delivery: false,
          },
          formLabelWidth: '130px',
          options:[],
          w_id:'',
          teacher:store.state.user.who,
          t_id:store.state.user.username
        }
      },
      mounted:function () {

        // 获取作业号
        this.w_id = this.getWorkId()

        // 获取 班级-课程 二级联动菜单
        this.$axios.get('/findBC?t_id='+store.state.user.username).then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.options = successResponse.data
          }
        })

        // 获取当前时间
        this.form.begindate = moment().format('YYYY-MM-DD HH:mm')
      },

      methods:{

        // 获取作业号
        getWorkId(){
          let text = "";
          let possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

          for( let i = 0; i < 7; i++ )
            text += possible.charAt(Math.floor(Math.random() * possible.length));

          return text;
        },

        // 判断日期是否符合要求
        onBlur(){
          // if((new Date(this.form.enddate)-new Date(this.form.begindate))/3600000 <=5){
          //   this.$message.error('截止日期必须至少比现在多5个小时！')
          //   this.form.enddate=''  // 清空日期
          // }

          if((new Date(this.form.enddate)<=new Date(this.form.begindate))){
            this.$message.error('截止日期必须大于布置日期！')
            this.form.enddate=''  // 清空日期
          }
        },

        // 添加作业
        addWork(){

          // 检测是否为空
          this.form.b_id=this.form.value[0]
          this.form.c_id=this.form.value[1]
          if(!this.form.b_id||!this.form.c_id|| !this.form.enddate){
            this.$message.warning('请补全信息！')
          }else{

            if(this.$refs.child.fileList.length==0){
              // 没有上传文件
              if(this.form.jianjie.split(' ').join('').length == 0){
                this.$message.warning('作业简介或上传文件二者至少选一个！')
              }else{
                this.$axios.post('/addWork', 'w_id=' + this.w_id + '&t_id=' + store.state.user.username + '&b_id=' + this.form.b_id + '&c_id=' + this.form.c_id + '&begindate=' + this.form.begindate + '&enddate=' + this.form.enddate + '&jianjie=' + this.form.jianjie).then(successResponse => {
                  if (successResponse && successResponse.status === 200) {
                    this.addsuccess();
                  }
                })
              }
            }else{
              // 上传了文件
              if(this.form.jianjie.split(' ').join('').length == 0){
                this.form.jianjie = '暂无'
              }
              this.$axios.post('/addWork', 'w_id=' + this.w_id + '&t_id=' + store.state.user.username + '&b_id=' + this.form.b_id + '&c_id=' + this.form.c_id + '&begindate=' + this.form.begindate + '&enddate=' + this.form.enddate + '&jianjie=' + this.form.jianjie).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  // 上传文件
                  this.$refs.child.$refs.upload.submit()
                  this.addsuccess();
                }
              })
            }


          }
        },

        // 添加成功弹框
        addsuccess(){

          this.$confirm('布置作业成功！', '提示', {
            confirmButtonText: '继续布置作业',
            cancelButtonText: '返回作业列表',
            type: 'success'
          }).then(() => {

            // 继续布置作业
            this.w_id = this.getWorkId()  // 获取新作业号
            this.resetForm('form')        // 重置

          }).catch(() => {

            this.$router.replace('/teacher/tmain/tworklist')

          });
        },

        // 重置
        resetForm(formName) {
          this.$refs[formName].resetFields();
          this.$refs.child.$refs.upload.clearFiles();

        }

      }
    }
</script>

<style scoped>

</style>
