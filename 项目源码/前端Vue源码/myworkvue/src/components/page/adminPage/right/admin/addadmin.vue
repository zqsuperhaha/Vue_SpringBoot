<template>
  <div>
    <el-button type="danger" size="medium" class="btn" @click="dialogFormVisible = true">添加</el-button>
    <el-dialog title="添加管理员" :visible.sync="dialogFormVisible">

      <el-form :model="form" ref="form" align="left">

        <el-form-item align="center" prop="a_id">用户名
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入用户名" v-model.tirm="form.a_id" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="a_pwd">密码
          <label style="color: red">*&nbsp;</label>
          <el-input type="password" placeholder="请输入密码" v-model.tirm="form.a_pwd" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="a_name">姓名
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入姓名" v-model.tirm="form.a_name" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="a_phone">手机号
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入手机号" v-model.tirm="form.a_phone" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="a_email">邮箱
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入邮箱" v-model.tirm="form.a_email" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="authority">权限
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入权限" v-model.tirm="form.authority" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="addAdmin">确 定</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "addadmin",
      props:['adminlist'],
      inject:['reload'],

      data(){
        return {
          form: {
            a_id:'',
            a_pwd:'',
            a_name:'',
            a_phone:'',
            a_email:'',
            authority:'0',
            delivery: false,
          },
          dialogFormVisible: false,

        }
      },
      methods:{

        // 添加管理员
        checkId(){
          // 检测id是否重复
          for(let i=0;i<this.adminlist.length;i++){
            if(this.adminlist[i].a_id==this.form.a_id){
              return false
            }
          }
          return true
        },

        // 验证手机号
        phoneCheck(){
          if(this.form.a_phone){
            let reg=/^1[3456789]\d{9}$/;
            if(!reg.test(this.form.a_phone)){
              this.$message.error('请输入有效的手机号码！')
              return false
            }
            return true
          }
        },

        // 验证邮箱
        emailCheck(){
          if(this.form.a_email){
            let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if(!reg.test(this.form.a_email)){
              this.$message.error('请输入有效的邮箱！')
              return false
            }
            return true
          }
        },

        // 校验密码长度
        pwdCheck(){
          if(this.form.a_pwd.length<6){
            this.$message.error('密码长度不能小于6位！')
            return false
          }
          return true
        },


        addAdmin(){

          // 检测是否为空
          if(!this.form.a_id||!this.form.a_pwd||!this.form.a_name||!this.form.a_phone||!this.form.a_email||!this.form.authority){
            this.$message.warning('请补全信息！')
          }else if (!this.checkId()) {
            this.$message.error('用户名重复！')
          }else if(this.pwdCheck() && this.phoneCheck() && this.emailCheck()){
            this.$axios.post('/addAdmin','a_id='+this.form.a_id+'&a_pwd='+this.form.a_pwd+'&a_name='+this.form.a_name+'&a_phone='+this.form.a_phone+'&a_email='+this.form.a_email+'&authority='+this.form.authority).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.reload();
                this.$message.success('添加管理员成功！')
              }
            })

            this.dialogFormVisible = false
          }
        },

        // 重置 -- 增加
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }
      }
    }
</script>

<style scoped>

</style>
