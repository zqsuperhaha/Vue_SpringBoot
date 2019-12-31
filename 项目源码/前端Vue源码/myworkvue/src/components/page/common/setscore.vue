<template>
  <div>
    <el-button type="text" @click="openHelp" style="font-size: 15px">积分规则</el-button>

    <el-dialog title="积分规则" :visible.sync="dialogFormVisible">
      <div>
        <span style="font-size: 18px">按时交：<span style="color: crimson"> +{{scoreList.aj}}</span>&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          重做：<span style="color: crimson"> {{scoreList.cz}}</span>&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          过期：<span style="color: crimson"> {{scoreList.qj}}</span>&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          补交：<span style="color: crimson"> 0</span>&nbsp;&nbsp;分</span>
        <div><h3>（ 重新提交不加分 ）</h3></div>
        <el-divider></el-divider>
        <span  style="font-size: 18px;">总积分 <span style="color: crimson">&nbsp;&nbsp;=&nbsp;&nbsp;</span>每项作业状态分 <span style="color: crimson">&nbsp;&nbsp;+&nbsp;&nbsp;</span> 教师评分</span>
        <el-divider></el-divider>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "setscore",
        data(){
          return {
            scoreList:{},
            dialogFormVisible: false,
          }
        },
        methods:{
          openHelp(){
            this.dialogFormVisible = true
            this.$axios.get('/findScore').then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.scoreList = successResponse.data[0]
              }
            })
          }
        }
    }
</script>

<style scoped>

</style>
