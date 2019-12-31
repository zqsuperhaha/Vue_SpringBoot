<template>
  <div>
  <el-button type="danger" size="medium" @click="openAdd">添加</el-button>

  <el-dialog title="添加作业" :visible.sync="dialogFormVisible">

    <el-form :model="form" ref="form" align="left">

      <el-form-item label="作业号:">
        {{w_id}}
      </el-form-item>

      <el-form-item label="班级-教师-课程" prop="value">
        <label style="color: red">*&nbsp;</label>
        <el-cascader
          v-model="form.value"
          :options="options">
        </el-cascader>
      </el-form-item>

      <el-form-item  prop="date">
        <div class="block">日期&nbsp;&nbsp;&nbsp;
          <label style="color: red">*&nbsp;</label>
          <el-date-picker
            v-model.tirm="form.date"
            type="datetimerange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm"
            :default-time="['12:00:00', '24:00:00']">
          </el-date-picker>
        </div>
      </el-form-item>

      <el-form-item  prop="jianjie">作业简介
        <el-input
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4}"
          placeholder="请输入内容(默认为无)"
          v-model.tirm="form.jianjie">
        </el-input>
      </el-form-item>

      <el-form-item>上传附件:
        <Uploadfile ref="child" :who="admin" :wid="w_id" :id="form.value[1]"></Uploadfile>
      </el-form-item>

      <el-form-item align="right">
        <el-button @click="resetForm('form')">重置</el-button>
        <el-button type="primary" @click="addWork">确 定</el-button>
      </el-form-item>

    </el-form>

  </el-dialog>
  </div>
</template>

<script>
  import store from '@/store'
  import moment from 'moment'
  import Uploadfile from '@/components/page/common/uploadfile'

  export default {
    name: "addwork",
    components:{Uploadfile},
    inject:['reload'],

    data() {

      return {
        dialogFormVisible: false,
        form: {
          t_id:'',
          b_id:'',
          c_id:'',
          value: [],
          date:'',
          begindate:'',
          enddate:'',
          jianjie:'',
          delivery: false,
        },
        options:[],
        w_id:'',
        admin:store.state.user.who
      }
    },
    methods:{

      // 获取作业号
      getWorkId(){
        let text = "";
        let possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for( let i = 0; i < 7; i++ )
          text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
      },

      // 打开添加对话框
      openAdd(){
        this.dialogFormVisible = true

        // 获取作业号
        this.w_id = this.getWorkId()

        // 获取 班级-教师-课程 三级联动菜单
        this.$axios.get('/findBTC').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.options = successResponse.data
          }
        })

      },

      // 添加作业
      addWork(){

        // 检测是否为空
        this.form.b_id=this.form.value[0]
        this.form.t_id=this.form.value[1]
        this.form.c_id=this.form.value[2]

        if(!this.form.t_id||!this.form.b_id||!this.form.c_id||!this.form.date){
          this.$message.warning('请补全信息！')
        }else{

          // 日期格式
          this.form.begindate = this.form.date[0]
          this.form.enddate = this.form.date[1]

          if(this.$refs.child.fileList.length==0){
            // 没有上传文件
            if(this.form.jianjie.split(' ').join('').length == 0){
              this.$message.warning('作业简介或上传文件二者至少选一个！')
            }else{
              this.$axios.post('/addWork','w_id='+this.w_id+'&t_id='+this.form.t_id+'&b_id='+this.form.b_id+'&c_id='+this.form.c_id+'&begindate='+this.form.begindate+'&enddate='+this.form.enddate+'&jianjie='+this.form.jianjie).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  this.reload();
                  this.$message.success('添加作业成功！')
                }
              })
            }
          }else{
            // 上传了文件
            if(this.form.jianjie.split(' ').join('').length == 0){
              this.form.jianjie = '暂无'
            }
            this.$axios.post('/addWork','w_id='+this.w_id+'&t_id='+this.form.t_id+'&b_id='+this.form.b_id+'&c_id='+this.form.c_id+'&begindate='+this.form.begindate+'&enddate='+this.form.enddate+'&jianjie='+this.form.jianjie).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                // 上传文件
                this.$refs.child.$refs.upload.submit()
                this.reload();
                this.$message.success('添加作业成功！')
              }
            })
          }

            this.dialogFormVisible = false
          }
        },

        // 重置
        resetForm(formName) {
          this.$refs[formName].resetFields();
          this.$refs.child.$refs.upload.clearFiles();

        }

      }
    }
</script>

<style scoped>

</style>
