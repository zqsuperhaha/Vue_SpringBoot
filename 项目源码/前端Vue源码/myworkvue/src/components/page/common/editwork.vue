<template>
  <div>
    <el-button size="mini" type="primary" plain @click="openEdit(workrow.yjnums)">{{workrow.yjnums == 0 ? '编辑' : '无'}}</el-button>

    <el-dialog title="编辑作业" :visible.sync="dialogFormVisible">

      <el-form :model="workrow" ref="form" align="left">

        <el-form-item label="作业号:" :label-width="formLabelWidth" prop="w_id">
          {{workrow.w_id}}
        </el-form-item>

        <el-form-item label="班级-教师-课程" :label-width="formLabelWidth" prop="value">
          <el-cascader
            v-model="value1"
            :options="options">
          </el-cascader>
        </el-form-item>

        <el-form-item align="center" prop="date">
          <div class="block">日期&nbsp;&nbsp;&nbsp;
            <label style="color: red">*&nbsp;</label>
            <el-date-picker
              v-model.tirm="date"
              type="datetimerange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['12:00:00', '24:00:00']">
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item align="center" prop="jianjie">作业简介
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容(默认为无)"
            v-model.tirm="workrow.jianjie">
          </el-input>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="updateWork">确 定</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>
  </div>
</template>

<script>

  import moment from 'moment'

  export default {
        name: "editwork",
        props:{
          workrow:{
            type:Object,
            required:true
          }
        },
        data(){
          return{
            dialogFormVisible: false,
            date:[],
            formLabelWidth: '130px',
            tList:[],
            bList:[],
            cList:[],
            value1:[],
            options:[],
          }
        },
        methods:{

          openEdit(yjnums){

            if(yjnums==0){
              this.dialogFormVisible = true

              this.date = [this.workrow.begindate,this.workrow.enddate]

              this.value1 = [this.workrow.b_id,this.workrow.t_id,this.workrow.c_id]


              // 获取 班级-教师-课程 三级联动菜单
              this.$axios.get('/findBTC').then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  this.options = successResponse.data
                }
              })
            }
          },

          // 修改
          updateWork(){

            // 检测是否为空
            this.workrow.b_id=this.value1[0]
            this.workrow.t_id=this.value1[1]
            this.workrow.c_id=this.value1[2]

            if(!this.date){
              this.$message.warning('请补全信息！')
            }else{
              if(this.workrow.jianjie==''){
                this.$message.warning('作业默认为暂无')
                this.workrow.jianjie = '暂无'
              }
              // 日期格式
              this.workrow.begindate = moment(this.date[0]).format('YYYY-MM-DD hh:mm')
              this.workrow.enddate = moment(this.date[1]).format('YYYY-MM-DD hh:mm')

              this.$axios.post('/updateWork','w_id='+this.workrow.w_id+'&t_id='+this.workrow.t_id+'&b_id='+this.workrow.b_id+'&c_id='+this.workrow.c_id+'&begindate='+this.workrow.begindate+'&enddate='+this.workrow.enddate+'&jianjie='+this.workrow.jianjie).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  this.$message.success('修改作业成功！')
                }
              })

              this.dialogFormVisible = false
            }
          },

          // 重置
          resetForm(formName) {
            this.date = [this.workrow.begindate,this.workrow.enddate]
            this.$refs[formName].resetFields();
          }
        }

    }
</script>

<style scoped>

</style>
