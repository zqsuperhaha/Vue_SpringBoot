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

          <el-form-item  label="学号：">
            {{stuList.s_id}}
          </el-form-item>

          <!--<el-form-item label="密码：">-->
            <!--{{stuList.s_pwd}}-->
          <!--</el-form-item>-->

          <el-form-item label="姓名：">
            {{stuList.s_name}}
          </el-form-item>

          <el-form-item label="手机号：" >
            <el-input placeholder="请输入手机号" v-model.tirm="stuList.s_phone" style="width: 300px"></el-input>
          </el-form-item>

          <el-form-item label="邮箱：" >
            <el-input placeholder="请输入邮箱" v-model.tirm="stuList.s_email" style="width: 300px"></el-input>
          </el-form-item>

          <el-form-item label="班级：" >
            {{stuList.b_name}}
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
            stuList:[],
            originPhone:'',
            originEmail:''
          }
        },
        mounted() {

          this.$axios.post('/findStuInfo','s_id='+store.state.user.username).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.stuList = successResponse.data[0]
              this.originPhone = this.stuList.s_phone
              this.originEmail = this.stuList.s_email
            }
          })


        },

        methods:{

          // 验证手机号
          phoneCheck(){

              let reg=/^1[3456789]\d{9}$/;
              if(!reg.test(this.stuList.s_phone)){
                this.$message.error('请输入有效的手机号码！')
                return false
              }
              return true

          },

          // 验证邮箱
          emailCheck(){

              let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
              if(!reg.test(this.stuList.s_email)){
                this.$message.error('请输入有效的邮箱！')
                return false
              }
              return true

          },

          // 修改信息
          updateStudent(){

            if(!this.stuList.s_phone || !this.stuList.s_email){
              this.$message.error('请补全信息！')
            }else{
              if(this.phoneCheck() && this.emailCheck()){

                this.$axios.post('/updateStudent','s_id='+this.stuList.s_id+'&s_pwd='+this.stuList.s_pwd+'&s_name='+this.stuList.s_name+'&s_phone='+this.stuList.s_phone+'&s_email='+this.stuList.s_email+'&b_id='+this.stuList.b_id).then(successResponse => {
                  if (successResponse && successResponse.status === 200) {
                    this.$message.success('保存成功！')
                  }
                })
              }
            }

          },

          // 重置
          resetForm(){
            this.stuList.s_phone = this.originPhone
            this.stuList.s_email = this.originEmail
          }
        }
    }
</script>

<style scoped>

</style>
