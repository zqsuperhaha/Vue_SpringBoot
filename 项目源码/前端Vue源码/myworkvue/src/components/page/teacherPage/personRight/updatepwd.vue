<template>
  <el-card>
    <div slot="header">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item>修改密码</el-breadcrumb-item>
        <el-breadcrumb-item></el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="margin-left: 50px">
      <el-form :model="form" ref="form" align="left">

        <el-form-item label="原始密码：" prop="oldpwd" style="width: 300px">
          <el-input type="password" v-model="form.oldpwd" show-password @blur="checkPwd"></el-input>
        </el-form-item>

        <el-form-item label="新密码：" prop="newpwd" style="width: 300px">
          <el-input type="password" v-model="form.newpwd" show-password ></el-input>（密码长度不能小于6位）
        </el-form-item>

        <el-form-item label="确认密码：" prop="verifypwd" style="width: 300px">
          <el-input type="password" v-model="form.verifypwd" show-password ></el-input>
        </el-form-item>


        <el-form-item align="left">
          <el-button @click="resetForm('form')">重 置</el-button>
          <el-button type="primary" @click="updatePwd">确认修改</el-button>
        </el-form-item>

      </el-form>
    </div>

  </el-card>
</template>

<script>
  import store from '@/store'
    export default {
        name: "updatepwd",
      data(){
        return {
          form:{
            oldpwd:'',
            newpwd:'',
            verifypwd:''
          }
        }
      },
      methods:{

        // 对比原始密码
        checkPwd(){
          if(this.form.oldpwd!=store.state.user.password){
            return true
          }
          return false
        },

        // 确认修改
        updatePwd(){
          if(!this.form.oldpwd || !this.form.newpwd ||!this.form.verifypwd){
            this.$message.warning('请补全信息!')
          }else if(this.checkPwd()){
            this.$message.error('原始密码输入错误！')
          } else if(this.form.newpwd.length<6){
            this.$message.error('新密码长度不能小于6位！')
          }else if(this.form.newpwd!=this.form.verifypwd){
            this.$message.warning('两次密码输入不一致！')
          }else {
            this.$axios.post('/updatePwdTch','t_id='+store.state.user.username+'&t_pwd='+this.form.newpwd).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.updatesuccess();
              }
            })
          }
        },

        // 修改成功弹框
        updatesuccess(){

          this.$alert('修改密码成功！', '提示', {
            confirmButtonText: '返回登录页面',
            type: 'success',
            callback: action => {
              window.location.href = '/login'
            }
          });
        },

        // 重置
        resetForm(formName){
          this.$refs[formName].resetFields();
        }
      }
    }
</script>

<style scoped>

</style>
