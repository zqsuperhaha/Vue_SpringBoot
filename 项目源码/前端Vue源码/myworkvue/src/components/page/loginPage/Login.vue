<template>
  <div>
    <div style="background-color: rgba(44,172,255,0.81);height: 50px;line-height: 50px">
      <span style="font-size: 30px;color: white">&nbsp;&nbsp;&nbsp;&nbsp;作&nbsp;&nbsp;业&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;评&nbsp;&nbsp;价&nbsp;&nbsp;系&nbsp;&nbsp;统</span>
    </div>
    <el-tabs type="border-card" class="tab"  @tab-click="handleClick">
      <el-tab-pane v-for="wl in wholist" v-bind:label="wl.who" style="padding-right: 1.5vw">
        <el-form :model="loginForm" status-icon :rules="rules"  ref="loginForm" label-width="5vw" class="demo-loginForm">

          <el-form-item v-bind:label="wl.whoname" prop="username" class="formitem">
            <el-input type="text" v-model.trim="loginForm.username" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="password"  class="formitem">
            <el-input type="password" v-model.trim="loginForm.password" show-password autocomplete="off"></el-input>
          </el-form-item>


        </el-form>

      </el-tab-pane>

      <div style="margin-top: 20px">
        <Verifycode ref="child"></Verifycode>
      </div>

      <div>
        <el-button type="primary" @click="login('loginForm',loginwho,index)" class="formbtn">登录</el-button>
      </div>


    </el-tabs>

  </div>

</template>

<script>
  import Verifycode from '@/components/page/common/verifycode'
  export default {
      name: "Login",
      components:{Verifycode},
      data(){

        return {
          wholist:[
            {who:'学生',whoname:'学号'},
            {who:'教师',whoname:'教师号'},
            {who:'管理员',whoname:'用户名'}
          ],
          loginForm:{
            who:'学生',
            username:'',
            password:''
          },
          loginwho:'学生',
          index:0,
          rules: {
            username: [
              { required: true, message: '不能为空！', trigger: 'blur' },
            ],
            password: [
              { required: true, message: '不能为空！', trigger: 'blur' },
            ],

          }
        }
      },
      methods:{
        // 切换选项卡
        handleClick(tab, event){

          this.loginwho = tab.$options.propsData.label

          if(this.loginwho=='学生'){
            this.index = 0
          }
          if(this.loginwho=='教师'){
            this.index = 1
          }
          if(this.loginwho=='管理员'){
            this.index = 2
          }

        },

        login: function (loginForm,who,index) {

          this.loginForm.who = who

          var _this = this

          this.$refs.loginForm[index].validate((valid) => {

            if (valid) {

              if(this.$refs['child'].suc=='yes'){

                this.$axios
                  .post('/login','who='+this.loginForm.who+'&username='+this.loginForm.username+'&password='+this.loginForm.password)
                  .then(successResponse => {

                    if (successResponse.data.code === 200) {

                      // this.loginForm.who = who

                      _this.$store.commit('login', _this.loginForm)

                      var path = this.$route.query.redirect
                      // 执行完 main.js 的 next()，就会执行下面这句
                      if(this.loginForm.who=="学生"){
                        this.$router.replace({path: path === '/' || path === undefined ? '/student' : path})
                      }
                      if(this.loginForm.who=="教师"){
                        this.$router.replace({path: path === '/' || path === undefined ? '/teacher' : path})
                      }
                      if(this.loginForm.who=="管理员"){
                        this.$router.replace({path: path === '/' || path === undefined ? '/admin' : path})
                      }

                    } else {
                      this.$message.error('用户名或密码错误');
                    }
                  })
                  .catch(failResponse => {
                  })
              }else{
                this.$message.warning('请拖动滑块验证信息！')
              }

            } else {

              return false;
            }
          })

        }


      }
  }

</script>

<style scoped>
  *{
    margin: 0 auto;
  }
  .tab{
    width: 450px;
    height: 340px;
    margin-top: 100px;
  }
  .formitem{
    width: 300px;
    margin-top:20px;

  }
  .formbtn{
    width: 150px;
    margin-top: 3px;
    margin-top: 20px;
    margin-left: 30px;
  }

</style>
