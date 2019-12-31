<template>
  <div>
    <el-button size="mini" type="primary" plain @click="openEditAdmin">编辑</el-button>

    <el-dialog title="编辑管理员" :visible.sync="dialogFormVisible">

      <el-form :model="adminRow" ref="form" align="left">

        <el-form-item  align="center" prop="s_id">用户名：
          {{adminRow.a_id}}
        </el-form-item>

        <el-form-item align="center" prop="s_pwd">密码
          <label style="color: red">*&nbsp;</label>
          <el-input  type="password" placeholder="请输入密码" v-model.tirm="adminRow.a_pwd" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="s_name">姓名
          <label style="color: red">*&nbsp;</label>
          <el-input  placeholder="请输入姓名" v-model.tirm="adminRow.a_name" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="s_phone">手机号
          <label style="color: red">*&nbsp;</label>
          <el-input  placeholder="请输入手机号" v-model.tirm="adminRow.a_phone" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="s_email">邮箱
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入邮箱" v-model.tirm="adminRow.a_email" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="t_email">权限
          <el-input v-model.tirm="adminRow.authority" style="width: 300px"></el-input>
        </el-form-item>


        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="updateAdmin">确 定</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "editadmin",
      props:['adminrow','adminlist'],
      data(){
        return {
          dialogFormVisible: false,
          bjList:[],
          adminRow:[]
        }
      },
      methods:{

        // 打开编辑管理员
        openEditAdmin(){

          this.dialogFormVisible=true

          this.adminRow = this.adminrow

        },

        // 验证手机号
        phoneCheck(){
          if(this.adminRow.a_phone){
            let reg=/^1[3456789]\d{9}$/;
            if(!reg.test(this.adminRow.a_phone)){
              this.$message.error('请输入有效的手机号码！')
              return false
            }
            return true
          }
        },

        // 验证邮箱
        emailCheck(){
          if(this.adminRow.a_email){
            let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if(!reg.test(this.adminRow.a_email)){
              this.$message.error('请输入有效的邮箱！')
              return false
            }
            return true
          }
        },
        // 校验密码长度
        pwdCheck(){
          if(this.adminRow.a_pwd.length<6){
            this.$message.error('密码长度不能小于6位！')
            return false
          }
          return true
        },

        // 修改
        updateAdmin(){

          // 检测是否为空
          if(!this.adminRow.a_pwd||!this.adminRow.a_name||!this.adminRow.a_phone||!this.adminRow.a_email||!this.adminRow.authority){
            this.$message.warning('请补全信息！')
          }else if(this.pwdCheck() && this.phoneCheck() && this.emailCheck()){
            this.$axios.post('/updateAdmin','a_id='+this.adminRow.a_id+'&a_pwd='+this.adminRow.a_pwd+'&a_name='+this.adminRow.a_name+'&a_phone='+this.adminRow.a_phone+'&a_email='+this.adminRow.a_email+'&authority='+this.adminRow.authority).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.$message.success('修改管理员成功！')
              }
            })

            this.dialogFormVisible = false
          }
        },

        // 重置 -- 修改
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }

      }
    }
</script>

<style scoped>

</style>
