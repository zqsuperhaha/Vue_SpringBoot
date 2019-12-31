<template>
  <div>
    <el-button size="mini" type="primary" plain @click="dialogFormVisible=true">编辑</el-button>

    <el-dialog title="编辑课程" :visible.sync="dialogFormVisible">

      <el-form :model="courserow" ref="form" align="left">

        <el-form-item align="center">课程号
          {{courserow.c_id}}
        </el-form-item>

        <el-form-item align="center" prop="c_name">课程名称
          <el-input placeholder="请输入班级名称" v-model.tirm="courserow.c_name"></el-input>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="updateCourse">确 定</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>
  </div>
</template>

<script>
    export default {
      name: "editcourse",
      props:['courserow'],
      data(){
        return {
          dialogFormVisible: false,

          form:{
            c_id:this.c_id,
            c_name:this.c_name,
            delivery: false,
          }
        }
      },
      methods:{

        // 修改
        updateCourse(){

          // 检测是否为空
          if(this.courserow.c_name==''){
            this.$message.warning('请补全信息！')
          }else{

            this.$axios.post('/updateCourse','c_id='+this.courserow.c_id+'&c_name='+this.courserow.c_name).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.$message.success('修改班级成功！')
              }
            })

            this.dialogFormVisible = false
          }
        },

        // 重置 -- 修改
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }

      }
    }
</script>

<style scoped>

</style>
