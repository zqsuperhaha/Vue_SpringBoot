<template>
  <div>
    <el-button size="mini" type="primary" plain @click="dialogFormVisible=true">编辑</el-button>

    <el-dialog title="编辑班级" :visible.sync="dialogFormVisible">

      <el-form :model="banjirow" ref="form" align="left">

        <el-form-item align="center">班级号
          {{banjirow.b_id}}
        </el-form-item>

        <el-form-item align="center" prop="b_name">班级名称
          <el-input placeholder="请输入班级名称" v-model.tirm="banjirow.b_name"></el-input>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="updateBanji">确 定</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "editbanji",
        props:{
          banjirow:{
            type:Object,
            required:true
          }
        },
        data(){
          return {
            dialogFormVisible: false,

            form:{
              b_id:this.b_id,
              b_name:this.b_name,
              delivery: false,
            }
          }
        },
        methods:{

          // 修改
          updateBanji(){

            // 检测是否为空
            if(this.banjirow.b_name==''){
              this.$message.warning('请补全信息！')
            }else{

              this.$axios.post('/updateBanji','b_id='+this.banjirow.b_id+'&b_name='+this.banjirow.b_name).then(successResponse => {
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
