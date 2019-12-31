<template>
  <div>
    <el-button type="danger" size="medium" class="btn" @click="openAdd">添加</el-button>
    <el-dialog title="添加教师" :visible.sync="dialogFormVisible">

      <el-form :model="form" ref="form" align="left">

        <el-form-item align="center" prop="t_id">教师号
          <label style="color: red">*&nbsp;</label>
          <el-input placeholder="请输入教师号" v-model.tirm="form.t_id" @blur="TBid" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="t_pwd">密码
          <label style="color: red">*&nbsp;</label>
          <el-input type="password" :readonly="read" placeholder="请输入密码" v-model.tirm="form.t_pwd" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="t_name">教师名称
          <label style="color: red">*&nbsp;</label>
          <el-input :readonly="read" placeholder="请输入教师名称" v-model.tirm="form.t_name" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="t_phone">手机号
          <label style="color: red">*&nbsp;</label>
          <el-input :readonly="read" placeholder="请输入手机号" v-model.tirm="form.t_phone" style="width: 300px"></el-input>
        </el-form-item>

        <el-form-item  align="center" prop="t_email">邮箱
          <label style="color: red">*&nbsp;</label>
          <el-input :readonly="read" placeholder="请输入邮箱" v-model.tirm="form.t_email" style="width: 300px"></el-input>
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

        <el-form-item  align="center" prop="c_id">课程：
          <label style="color: red">*&nbsp;</label>
          <el-select v-model="form.c_id" clearable placeholder="课程">
            <el-option
              v-for="item in csList"
              :key="item.c_id"
              :label="item.c_name"
              :value="item.c_id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="addTeacher">确 定</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "addtch",
      props:['tchlist'],
      inject:['reload'],

      data(){
        return {
          form: {
            t_id:'',
            t_name:'',
            t_pwd:'',
            b_id:'',
            b_name:'',
            c_id:'',
            c_name:'',
            t_phone:'',
            t_email:'',
            delivery: false,
          },
          bjList:[],
          csList:[],
          dialogFormVisible: false,
          read:false

        }
      },
      methods:{

        // 打开添加按钮
        openAdd(){

          this.dialogFormVisible = true

          // 获取所有班级
          this.$axios.get('/findAllBanji').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.bjList = successResponse.data
            }
          })

          // 获取所有课程
          this.$axios.get('/findAllCourse').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.csList = successResponse.data
            }
          })

        },

        // 验证手机号
        phoneCheck(){
          if(this.form.t_phone){
            let reg=/^1[3456789]\d{9}$/;
            if(!reg.test(this.form.t_phone)){
              this.$message.error('请输入有效的手机号码！')
              return false
            }
            return true
          }
        },

        // 验证邮箱
        emailCheck(){
          if(this.form.t_email){
            let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if(!reg.test(this.form.t_email)){
              this.$message.error('请输入有效的邮箱！')
              return false
            }
            return true
          }
        },

        // 校验密码长度
        pwdCheck(){
          if(this.form.t_pwd.length<6){
            this.$message.error('密码长度不能小于6位！')
            return false
          }
          return true
        },

        // tid 同步
        TBid(){
          for(let i=0;i<this.tchlist.length;i++){
            if(this.tchlist[i].t_id==this.form.t_id){
              this.form.t_pwd = this.tchlist[i].t_pwd
              this.form.t_name = this.tchlist[i].t_name
              this.form.t_phone = this.tchlist[i].t_phone
              this.form.t_email = this.tchlist[i].t_email
              this.read = true
              break
            }else{
              this.read = false
            }
          }
        },

        // 添加教师
        checkId(){
          // 检测信息是否重复
          for(let i=0;i<this.tchlist.length;i++){
            if(this.tchlist[i].t_id==this.form.t_id && this.tchlist[i].b_id == this.form.b_id && this.tchlist[i].c_id == this.form.c_id){
              return false
            }
          }
          return true
        },
        addTeacher(){

          // 检测是否为空，格式是否正确
          if(!this.form.t_id||!this.form.t_name||!this.form.t_pwd||!this.form.b_id||!this.form.c_id||!this.form.t_phone||!this.form.t_email){
            this.$message.warning('请补全信息！')
          }else if (!this.checkId()) {
            this.$message.error('添加信息重复！')
          }else if(this.pwdCheck() && this.phoneCheck() && this.emailCheck()){

            if(this.read){
              this.$axios.post('/addTBC','t_id='+this.form.t_id+'&b_id='+this.form.b_id+'&c_id='+this.form.c_id).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  this.reload();
                  this.$message.success('添加教师成功！')
                }
              })
            }else {

              this.$axios.post('/addTeacher','t_id='+this.form.t_id+'&t_name='+this.form.t_name+'&t_pwd='+this.form.t_pwd+'&t_phone='+this.form.t_phone+'&t_email='+this.form.t_email).then(successResponse => {
                if (successResponse && successResponse.status === 200) {

                  this.$axios.post('/addTBC','t_id='+this.form.t_id+'&b_id='+this.form.b_id+'&c_id='+this.form.c_id).then(successResponse => {
                    if (successResponse && successResponse.status === 200) {
                      this.reload();
                      this.$message.success('添加教师成功！')
                    }
                  })

                }
              })
            }

            this.dialogFormVisible = false
          }
        },

        // 重置 -- 增加
        resetForm(formName) {
          this.read = false
          this.$refs[formName].resetFields();
        }
      }
    }
</script>

<style scoped>

</style>
