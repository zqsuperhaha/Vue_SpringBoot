<template>

    <el-form :model="form" ref="form" align="left">
      <el-form-item align="left" :label-width="'60px'" prop="ps">作业备注:
        <label style="color: red">*&nbsp;</label>
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 10}"
          placeholder="请输入内容"
          v-model.tirm="form.ps">
        </el-input>
      </el-form-item>

      <el-form-item style="margin-left: 60px">上传附件:
        <Uploadfile ref="child" :who="student" :wid="w_id" :id="s_id"></Uploadfile>
      </el-form-item>

      <el-form-item align="right">
        <el-button @click="resetForm('form')">重 置</el-button>
        <el-button type="primary" @click="addSubmit">确认提交</el-button>
      </el-form-item>
    </el-form>

</template>

<script>
  import Uploadfile from '@/components/page/common/uploadfile'
  import moment from 'moment'
  import store from '@/store'

  export default {
    name: "submit",
    components:{Uploadfile},
    props:['w_id','isAgain'],
    inject:['reload'],

    data() {
         return {
           form:{
             ps:''
           },
           student:store.state.user.who,
           s_id:store.state.user.username
         }
      },
      methods:{
        addSubmit(){
          // 获取当前日期--提交日期
          let subdate = moment().format('YYYY-MM-DD HH:mm')
          if(this.$refs.child.fileList.length==0){
            // 没有上传文件
            if(this.form.ps.split(' ').join('').length == 0){
              this.$message.warning('作业简介或上传文件二者至少选一个！')
            }else{
              this.$axios.post('/updateAj', 'subdate=' + subdate + '&ps=' + this.form.ps + '&w_id=' + this.w_id + '&s_id=' + store.state.user.username + '&isAgain='+this.isAgain).then(successResponse => {
                if (successResponse && successResponse.status === 200) {

                  this.addsuccess();
                }
              })
            }
          }else{
            // 上传了文件
            this.$axios.post('/updateAj', 'subdate=' + subdate + '&ps=' + this.form.ps + '&w_id=' + this.w_id + '&s_id=' + store.state.user.username + '&isAgain='+this.isAgain).then(successResponse => {
              if (successResponse && successResponse.status === 200) {

                // 上传文件
                this.$refs.child.$refs.upload.submit();

                this.addsuccess();
              }
            })
          }

        },

        // 提交成功弹框
        addsuccess(){

          this.$alert('提交作业成功！', '提示', {
            confirmButtonText: '确定',
            type: 'success',
            callback: action => {
              // this.reload('finish')
              if(this.isAgain=='1'){
                // 重新提交
                this.reload()
              }else{
                this.$router.replace('/student/smain/finish')
              }
            }
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
