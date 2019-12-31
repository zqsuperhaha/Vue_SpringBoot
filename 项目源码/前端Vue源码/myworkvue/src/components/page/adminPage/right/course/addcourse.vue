<template>
  <div>
    <el-button type="danger" size="medium" class="btn" @click="dialogFormVisible = true">添加</el-button>
    <el-dialog title="添加课程" :visible.sync="dialogFormVisible">

      <el-form :model="form" ref="form" align="left">

        <el-form-item align="center" prop="c_id">课程号
          <el-input placeholder="请输入课程号" v-model.tirm="form.c_id"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="c_name">课程名称
          <el-input placeholder="请输入课程名称" v-model.tirm="form.c_name"></el-input>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="addCourse">确 定</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "addcourse",
      props:['cslist'],
      inject:['reload'],
      data(){
        return {
          form: {
            c_id:'',
            c_name:'',
            delivery: false,
          },
          dialogFormVisible: false,

        }
      },
      methods:{

        // 添加课程
        checkId(){
          // 检测id是否重复
          for(let i=0;i<this.cslist.length;i++){
            if(this.cslist[i].c_id==this.form.c_id){
              return false
            }
          }
          return true
        },
        addCourse(){

          // 检测是否为空
          if(this.form.c_id==''||this.form.c_name==''){
            this.$message.warning('请补全信息！')
          }else if (!this.checkId()) {
            this.$message.error('课程号重复！')
          }else{
            this.$axios.post('/addCourse','c_id='+this.form.c_id+'&c_name='+this.form.c_name).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.reload();
                this.$message.success('添加班级成功！')
              }
            })

            this.dialogFormVisible = false
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
