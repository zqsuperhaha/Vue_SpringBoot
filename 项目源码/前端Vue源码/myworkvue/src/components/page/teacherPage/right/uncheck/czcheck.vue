<template>
    <div>
      <el-button type="warning" plain @click="dialogVisible = true">重 做</el-button>
      <el-dialog title="重做" :visible.sync="dialogVisible" align="left">
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
          <el-form-item label="（确认后，系统自动为该学生扣除相应的个人分）"></el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateCz">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import moment from 'moment'

  export default {
    name: "czcheck",
    props:['w_id','s_id'],
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

        // 判断日期是否符合要求
        onBlur(){
          if((new Date(this.form.enddate)<=new Date(moment().format('YYYY-MM-DD HH:mm')))){
            this.$message.error('截止日期必须超过现在时间！')
            this.form.enddate=''  // 清空日期
          }
        },

        // 重做
        updateCz(){
          if(!this.form.enddate){
            this.$message.warning('请补全信息！')
          }else{
            this.$axios.post('/updateCz','enddate='+this.form.enddate+'&w_id='+this.w_id+'&s_id='+this.s_id).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.czsuccess();
              }
            })
            this.dialogVisible = false
          }

        },

        // 重做生效
        czsuccess(){

          this.$alert('重做已生效！', '提示', {
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
