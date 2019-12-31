<template>
    <div>
      <el-card>
        <div slot="header">
          <span>积分设置</span>
        </div>
        <div>
          <el-form :model="scoreList" ref="form" align="left" style="margin-left: 70px">

            <el-form-item label="按时交" prop="aj">
              <el-input-number v-model="scoreList.aj" :min="1" :max="100"></el-input-number>
            </el-form-item>

            <el-form-item label="重做" prop="cz">
              <el-input-number v-model="scoreList.cz" :min="-100" :max="0"></el-input-number>
            </el-form-item>

            <el-form-item label="过期" prop="qj">
              <el-input-number v-model="scoreList.qj" :min="-100" :max="0"></el-input-number>
            </el-form-item>

            <el-form-item label="教师评分上限" prop="maxpj">
              <el-input-number v-model="scoreList.maxpj" :min="1" :max="1000"></el-input-number>
            </el-form-item>

            <el-form-item label="教师评分下限" prop="minpj">
              <el-input-number v-model="scoreList.minpj" :min="-1000" :max="-1"></el-input-number>
            </el-form-item>

            <el-form-item align="right">
              <el-button @click="resetForm()">重 置</el-button>
              <el-button type="primary" @click="editScore">确认修改</el-button>
            </el-form-item>

          </el-form>
        </div>
      </el-card>
    </div>
</template>

<script>
    export default {
      name: "scoreset",
      data(){
        return {
          scoreList:{},
          aj:'',
          cz:'',
          qj:'',
          maxpj:'',
          minpj:'',
          dialogFormVisible: false,
        }
      },
      mounted(){
        this.$axios.get('/findScore').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.scoreList = successResponse.data[0]
            this.aj = this.scoreList.aj
            this.cz = this.scoreList.cz
            this.qj = this.scoreList.qj
            this.maxpj = this.scoreList.maxpj
            this.minpj = this.scoreList.minpj

          }
        })
      },
      methods:{

        // 修改分数
        editScore(){
          this.$axios.post('/updateScore', 'aj=' + this.scoreList.aj + '&cz=' + this.scoreList.cz + '&qj=' + this.scoreList.qj + '&maxpj=' + this.scoreList.maxpj + '&minpj='+this.scoreList.minpj).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.$message.success('分数设置修改成功！')
            }
          })
        },

        // 重置
        resetForm() {
          this.scoreList.aj = this.aj
          this.scoreList.cz = this.cz
          this.scoreList.qj= this.qj
          this.scoreList.maxpj = this.maxpj
          this.scoreList.minpj = this.minpj
        }
      }
    }
</script>

<style scoped>

</style>
