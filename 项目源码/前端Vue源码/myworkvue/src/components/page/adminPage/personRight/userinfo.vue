<template>
  <el-card>
    <div slot="header">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item>账号信息</el-breadcrumb-item>
        <el-breadcrumb-item></el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="margin-left: 50px">
      <el-form align="left">

        <el-form-item  label="用户名：">
          {{adminList.a_id}}
        </el-form-item>

        <!--<el-form-item label="密码：">-->
          <!--{{adminList.a_pwd}}-->
        <!--</el-form-item>-->

        <el-form-item label="姓名：">
          {{adminList.a_name}}
        </el-form-item>

        <el-form-item label="手机号：" >
          <el-input placeholder="请输入手机号" v-model.tirm="adminList.a_phone" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item label="邮箱：" >
          <el-input placeholder="请输入邮箱" v-model.tirm="adminList.a_email" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item label="权限（1-超级管理员 0-普通管理员）：" >
          {{adminList.authority}}
        </el-form-item>


        <el-form-item align="center">
          <el-button @click="resetForm()">重 置</el-button>
          <el-button type="primary" @click="updateStudent">保 存</el-button>
        </el-form-item>

      </el-form>
    </div>

  </el-card>
</template>

<script>
  import store from '@/store'

  export default {
        name: "userinfo",
      data(){
        return {
          adminList:[],
          originPhone:'',
          originEmail:''
        }
      },
      mounted() {

        this.$axios.post('/findAdminInfo','a_id='+store.state.user.username).then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.adminList = successResponse.data[0]
            this.originPhone = this.adminList.a_phone
            this.originEmail = this.adminList.a_email
          }
        })


      },

      methods:{

        // 验证手机号
        phoneCheck(){

          let reg=/^1[3456789]\d{9}$/;
          if(!reg.test(this.adminList.a_phone)){
            this.$message.error('请输入有效的手机号码！')
            return false
          }
          return true

        },

        // 验证邮箱
        emailCheck(){

          let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
          if(!reg.test(this.adminList.a_email)){
            this.$message.error('请输入有效的邮箱！')
            return false
          }
          return true

        },

        // 修改信息
        updateStudent(){

          if(!this.adminList.a_phone || !this.adminList.a_email){
            this.$message.error('请补全信息！')
          }else{
            if(this.phoneCheck() && this.emailCheck()){

              this.$axios.post('/updateAdmin','a_id='+this.adminList.a_id+'&a_pwd='+this.adminList.a_pwd+'&a_name='+this.adminList.a_name+'&a_phone='+this.adminList.a_phone+'&a_email='+this.adminList.a_email+'&authority='+this.adminList.authority).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  this.$message.success('修改信息成功！')
                }
              })
            }
          }

        },

        // 重置
        resetForm(){
          this.adminList.a_phone = this.originPhone
          this.adminList.a_email = this.originEmail
        }
      }
    }
</script>

<style scoped>

</style>
