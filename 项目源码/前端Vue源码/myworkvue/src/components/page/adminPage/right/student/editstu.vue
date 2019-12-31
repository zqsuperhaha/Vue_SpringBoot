<template>
  <div>
    <el-button size="mini" type="primary" plain @click="openEditStu">编辑</el-button>

    <el-dialog title="编辑学生" :visible.sync="dialogFormVisible">

      <el-form :model="sturow" ref="form" align="left">

        <el-form-item  align="center" prop="s_id">学号：
          {{sturow.s_id}}
        </el-form-item>

        <el-form-item align="center" prop="s_pwd">密码
          <label style="color: red">*&nbsp;</label>
          <el-input type="password" placeholder="请输入密码" v-model.tirm="stuRow.s_pwd" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="s_name">姓名
          <label style="color: red">*&nbsp;</label>
          <el-input  placeholder="请输入姓名" v-model.tirm="stuRow.s_name" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="s_phone">手机号
          <label style="color: red">*&nbsp;</label>
          <el-input  placeholder="请输入手机号" v-model.tirm="stuRow.s_phone" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="s_email">邮箱
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入邮箱" v-model.tirm="stuRow.s_email" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="b_id">班级：
          <label style="color: red">*&nbsp;</label>
          <el-select v-model="stuRow.b_id" clearable placeholder="班级">
            <el-option
              v-for="item in bjList"
              :key="item.b_id"
              :label="item.b_name"
              :value="item.b_id">
            </el-option>
          </el-select>
        </el-form-item>

        <!--<el-form-item  align="center" prop="t_email">积分-->
          <!--<el-input :readonly="true"  v-model.tirm="stuRow.oksum" style="width: 300px"></el-input>-->
        <!--</el-form-item>-->


        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="updateStudent">确 定</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "editstu",
      props:['sturow'],
      inject:['reload'],

      data(){
        return {
          dialogFormVisible: false,
          bjList:[],
          stuRow:[]
        }
      },
      methods:{

        // 打开编辑学生
        openEditStu(){

          this.dialogFormVisible=true

          this.stuRow = this.sturow

          // 获取所有班级
          this.$axios.get('/findSelectBanji').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.bjList = successResponse.data
            }
          })
        },

        // 验证手机号
        phoneCheck(){
          if(this.stuRow.s_phone){
            let reg=/^1[3456789]\d{9}$/;
            if(!reg.test(this.stuRow.s_phone)){
              this.$message.error('请输入有效的手机号码！')
              return false
            }
            return true
          }
        },

        // 验证邮箱
        emailCheck(){
          if(this.stuRow.s_email){
            let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if(!reg.test(this.stuRow.s_email)){
              this.$message.error('请输入有效的邮箱！')
              return false
            }
            return true
          }
        },

        // 校验密码长度
        pwdCheck(){
          if(this.stuRow.s_pwd.length<6){
            this.$message.error('密码长度不能小于6位！')
            return false
          }
          return true
        },

        // 修改
        updateStudent(){

          // 检测是否为空，格式是否正确
          if(!this.stuRow.s_id||!this.stuRow.s_name||!this.stuRow.s_pwd||!this.stuRow.s_id||!this.stuRow.s_phone||!this.stuRow.s_email||!this.stuRow.b_id){
            this.$message.warning('请补全信息！')
          }else if(this.pwdCheck() && this.phoneCheck() && this.emailCheck()){

            this.$axios.post('/updateStudent','s_id='+this.stuRow.s_id+'&s_pwd='+this.stuRow.s_pwd+'&s_name='+this.stuRow.s_name+'&s_phone='+this.stuRow.s_phone+'&s_email='+this.stuRow.s_email+'&b_id='+this.stuRow.b_id).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.reload();
                this.$message.success('编辑学生成功！')
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
