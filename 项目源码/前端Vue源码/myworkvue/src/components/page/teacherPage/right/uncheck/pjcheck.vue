<template>
    <div>
      <el-form :model="form1" ref="form1" >

        <el-form-item label="评价日期">
          <el-date-picker
            v-model="form1.pjdate"
            :readonly="true"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="评分" prop="num">
          <el-input-number v-model="form1.num" step-strictly controls-position="right" :min="scorelist.minpj" :max="scorelist.maxpj"></el-input-number>
          <div>（最高：{{scorelist.maxpj}}分&nbsp;&nbsp;最低：{{scorelist.minpj}}分 ）</div>
        </el-form-item>

        <el-form-item label="评语：" prop="pingjia">
          <label style="color: red">*</label>
          <el-input
            type="textarea"
            placeholder="请输入内容"
            :autosize="{ minRows: 2, maxRows: 4}"
            v-model="form1.pingjia">
          </el-input>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form1')">重置</el-button>
          <el-button type="primary" @click="addPj">确认批改</el-button>
        </el-form-item>

        <el-form-item align="right">
          <Czcheck :w_id="wid" :s_id="sid"></Czcheck>
        </el-form-item>

      </el-form>
    </div>
</template>

<script>
  import moment from 'moment'
  import Czcheck from './czcheck'
    export default {
      name: "pjcheck",
      components:{Czcheck},
      props:['wid','sid','scorelist'],
      inject:['reload'],

      data(){
        return {
          form1:{
            num:0,
            pingjia:'',
            pjdate:moment().format('YYYY-MM-DD HH:mm')
          },
        }
      },
      methods:{

        // 评价
        addPj(){
          if(!this.form1.pingjia){
            this.form1.pingjia = '暂无评语'
          }
          this.$axios.post('/updatePj','pjdate='+this.form1.pjdate+'&w_score='+this.form1.num+'&pingjia='+this.form1.pingjia+'&w_id='+this.wid+'&s_id='+this.sid).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.pjsuccess();
            }
          })
        },

        // 评价成功弹框
        pjsuccess(){

          this.$confirm('批改作业成功！', '提示', {
            confirmButtonText: '继续批改其他作业',
            cancelButtonText: '重新批改',
            type: 'success'
          }).then(() => {
            this.reload()
          })

        },

        // 重置
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }
      }
    }
</script>

<style scoped>

</style>
