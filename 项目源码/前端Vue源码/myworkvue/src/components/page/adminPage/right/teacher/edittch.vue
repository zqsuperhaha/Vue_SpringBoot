<template>
    <div>
      <el-button size="mini" type="primary" plain @click="openEditTch">编辑</el-button>
      <el-dialog title="编辑" :visible.sync="dialogFormVisible">

        <el-form :model="tchrow" ref="form" align="left">

          <el-form-item align="center" prop="t_id">教师号
            <el-input :readonly="true" placeholder="请输入教师号" v-model.tirm="tchRow.t_id" style="width: 300px"></el-input>
          </el-form-item>

          <el-form-item align="center" prop="t_pwd">密码
            <label style="color: red">*&nbsp;</label>
            <el-input type="password" placeholder="请输入密码" v-model.tirm="tchRow.t_pwd" style="width: 300px"></el-input>
          </el-form-item>

          <el-form-item align="center" prop="t_name">教师名称
            <label style="color: red">*&nbsp;</label>
            <el-input  placeholder="请输入教师名称" v-model.tirm="tchRow.t_name" style="width: 300px"></el-input>
          </el-form-item>

          <el-form-item  align="center" prop="t_phone">手机号
            <label style="color: red">*&nbsp;</label>
            <el-input  placeholder="请输入手机号" v-model.tirm="tchRow.t_phone" style="width: 300px"></el-input>
          </el-form-item>

          <el-form-item  align="center" prop="t_email">邮箱
            <label style="color: red">*&nbsp;</label>
            <el-input  placeholder="请输入邮箱" v-model.tirm="tchRow.t_email" style="width: 300px"></el-input>
          </el-form-item>

          <el-form-item  align="center" prop="b_id">班级：
            <label style="color: red">*&nbsp;</label>
            <el-select v-model="tchRow.b_id" clearable placeholder="班级">
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
            <el-select v-model="tchRow.c_id" clearable placeholder="课程">
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
      name: "edittch",
      props:['tchrow','tchlist'],
      inject:['reload'],

      data(){
          return {
            tchRow:this.tchrow,
            bid:'',
            cid:'',
            bjList:[],
            csList:[],
            dialogFormVisible: false,

          }
        },

        methods:{
          // 打开添加按钮
          openEditTch(){
            this.tchRow = this.tchrow
            this.bid = this.tchrow.b_id
            this.cid = this.tchrow.c_id
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
            if(this.tchRow.t_phone){
              let reg=/^1[3456789]\d{9}$/;
              if(!reg.test(this.tchRow.t_phone)){
                this.$message.error('请输入有效的手机号码！')
                return false
              }
              return true
            }
          },

          // 验证邮箱
          emailCheck(){
            if(this.tchRow.t_email){
              let reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
              if(!reg.test(this.tchRow.t_email)){
                this.$message.error('请输入有效的邮箱！')
                return false
              }
              return true
            }
          },

          // 校验密码长度
          pwdCheck(){
            if(this.tchRow.t_pwd.length<6){
              this.$message.error('密码长度不能小于6位！')
              return false
            }
            return true
          },

          // 编辑教师信息
          addTeacher(){

            // 检测是否为空，格式是否正确
            if(!this.tchRow.t_id||!this.tchRow.t_name||!this.tchRow.t_pwd||!this.tchRow.b_id||!this.tchRow.c_id||!this.tchRow.t_phone||!this.tchRow.t_email){
              this.$message.warning('请补全信息！')
            }else if(this.pwdCheck() && this.phoneCheck() && this.emailCheck()){
              this.$axios.post('/updateTeacher','t_id='+this.tchRow.t_id+'&t_name='+this.tchRow.t_name+'&t_pwd='+this.tchRow.t_pwd+'&t_phone='+this.tchRow.t_phone+'&t_email='+this.tchRow.t_email).then(successResponse => {
                if (successResponse && successResponse.status === 200) {

                  this.$axios.post('/updateTBC','t_id='+this.tchRow.t_id+'&b_id1='+this.tchRow.b_id+'&c_id1='+this.tchRow.c_id+'&b_id='+this.bid+'&c_id='+this.cid).then(successResponse => {
                    if (successResponse && successResponse.data.code === 200) {
                      this.reload();
                      this.$message.success('编辑成功！')
                      this.dialogFormVisible = false

                    }else{
                      this.$message.error('编辑信息重复！')
                    }
                  })

                }
              })

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
