<template>
  <div>
    <el-button type="danger" size="medium" class="btn" @click="openAdd">添加</el-button>
    <el-dialog title="添加学生" :visible.sync="dialogFormVisible">

      <el-form :model="form" ref="form" align="left">

        <el-form-item align="center" prop="s_id">学号
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入学号" v-model.tirm="form.s_id" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="s_pwd">密码
          <label style="color: red">*&nbsp;</label>
          <el-input type="password" placeholder="请输入密码" v-model.tirm="form.s_pwd" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="s_name">姓名
          <label style="color: red">*&nbsp;</label>
          <el-input  placeholder="请输入姓名" v-model.tirm="form.s_name" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="s_phone">手机号
          <label style="color: red">*&nbsp;</label>
          <el-input  placeholder="请输入手机号" v-model.tirm="form.s_phone" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="s_email">邮箱
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入邮箱" v-model.tirm="form.s_email" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="b_id">班级：
          <label style="color: red">*&nbsp;</label>
          <el-select v-model="form.b_id" clearable placeholder="班级">
            <el-option
              v-for="item in bjList"
              :key="item.b_id"
              :label="item.b_name"
              :value="item.b_id">
            </el-option>
          </el-select>
        </el-form-item>

        <!--<el-form-item  align="center" prop="t_email">积分-->
          <!--<el-input :readonly="true"  v-model.tirm="form.score" style="width: 300px"></el-input>-->
        <!--</el-form-item>-->


        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="addStudent">确 定</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "addstu",
      props:['stulist'],
      inject:['reload'],

      data(){
        return {
          form: {
            s_id:'',
            s_name:'',
            s_pwd:'',
            b_id:'',
            b_name:'',
            s_phone:'',
            s_email:'',
            score:0,
            delivery: false,
          },
          bjList:[],
          dialogFormVisible: false,

        }
      },
      methods:{

        // 打开添加按钮
        openAdd(){

          this.dialogFormVisible = true

          // 获取所有班级
          this.$axios.get('/findSelectBanji').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.bjList = successResponse.data
            }
          })


        },

        // 验证手机号
        phoneCheck(){
          if(this.form.s_phone){
            let reg=/^1[3456789]\d{9}$/;
            if(!reg.test(this.form.s_phone)){
              this.$message.error('请输入有效的手机号码！')
              return false
            }
            return true
          }
        },

        // 验证邮箱
        emailCheck(){
          if(this.form.s_email){
            let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if(!reg.test(this.form.s_email)){
              this.$message.error('请输入有效的邮箱！')
              return false
            }
            return true
          }
        },

        // 校验密码长度
        pwdCheck(){
          if(this.form.s_pwd.length<6){
            this.$message.error('密码长度不能小于6位！')
            return false
          }
          return true
        },

        // 添加学生
        checkId(){
          // 检测信息是否重复
          for(let i=0;i<this.stulist.length;i++){
            if(this.stulist[i].s_id==this.form.s_id){
              return true
            }
          }
          return false
        },

        addStudent(){

          // 检测是否为空，格式是否正确
          if(!this.form.s_id||!this.form.s_name||!this.form.s_pwd||!this.form.s_id||!this.form.s_phone||!this.form.s_email||!this.form.b_id){
            this.$message.warning('请补全信息！')
          }else if (this.checkId()) {

            this.$message.error('添加信息重复！')

          }else if(this.pwdCheck() && this.phoneCheck() && this.emailCheck()){

            this.$axios.post('/addStudent','s_id='+this.form.s_id+'&s_pwd='+this.form.s_pwd+'&s_name='+this.form.s_name+'&s_phone='+this.form.s_phone+'&s_email='+this.form.s_email+'&b_id='+this.form.b_id).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.reload();
                this.$message.success('添加学生成功！')
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
