<template>
  <div>
    <el-button size="mini" type="primary" plain @click="openBj(bjrow.status)">{{bjrow.status === '2' ? '补交' : '无'}}</el-button>

    <el-dialog title="补交" :visible.sync="dialogVisible" align="left">
      <el-form :model="form">
        <el-form-item label="截止日期">
          <div class="block" >
            <label style="color: red">*&nbsp;</label>
            <el-date-picker
              v-model="form.enddate"
              type="datetime"
              @blur="onBlur"
              placeholder="选择日期时间"
              default-time="12:00:00"
              format="yyyy-MM-dd HH:mm"
              value-format="yyyy-MM-dd HH:mm"
              align="left">
            </el-date-picker>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateBj">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import moment from 'moment'

  export default {
    name: "bjcheck",
    props:['wid','bjrow'],
    inject:['reload'],

    data() {
        return {
          form:{
            enddate:''
          },
          dialogVisible: false,
        }
      },
      methods:{

        // 打开补交对话框
        openBj(s){
          if(s=="2"){
            this.dialogVisible = true
          }
        },

        // 判断日期是否符合要求
        onBlur(){
          if((new Date(this.form.enddate)<=new Date(moment().format('YYYY-MM-DD HH:mm')))){
            this.$message.error('截止日期必须超过现在时间！')
            this.form.enddate=''  // 清空日期
          }
        },

        // 补交
        updateBj(){
          if(!this.form.enddate){
            this.$message.warning('请补全信息！')
          }else{
            this.$axios.post('/updateBj','enddate='+this.form.enddate+'&w_id='+this.wid+'&s_id='+this.bjrow.s_id).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.bjsuccess();
              }
            })
            this.dialogVisible = false
          }

        },

        // 补交生效
        bjsuccess(){
          this.$alert('补交已生效！', '提示', {
            confirmButtonText: '确定',
            type: 'success',
            callback: action => {
              this.reload()

            }
          });

        },
      }
    }
</script>

<style scoped>

</style>
