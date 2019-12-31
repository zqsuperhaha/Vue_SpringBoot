<template>
  <div>
    <el-button type="danger" size="medium" class="btn" @click="dialogFormVisible = true">添加</el-button>
    <el-dialog title="添加班级" :visible.sync="dialogFormVisible">

      <el-form :model="form" ref="form" align="left">

        <el-form-item align="center" prop="b_id">班级号
          <el-input placeholder="请输入班级号" v-model.tirm="form.b_id"></el-input>
        </el-form-item>

        <el-form-item align="center" prop="b_name">班级名称
          <el-input placeholder="请输入班级名称" v-model.tirm="form.b_name"></el-input>
        </el-form-item>

        <el-form-item align="right">
          <el-button @click="resetForm('form')">重置</el-button>
          <el-button type="primary" @click="addBanji">确 定</el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    export default {
        name: "addbanji",
        props:{
          bjlist:{
            type:Array,
            required:true
          }
        },
      inject:['reload'],

      data(){
          return {
            form: {
              b_id:'',
              b_name:'',
              delivery: false,
            },
            dialogFormVisible: false,

          }
        },
        methods:{

          // 添加班级
          checkId(){
            // 检测id是否重复
            for(let i=0;i<this.bjlist.length;i++){
              if(this.bjlist[i].b_id==this.form.b_id){
                return false
              }
            }
            return true
          },
          addBanji(){

            // 检测是否为空
            if(this.form.b_id==''||this.form.b_name==''){
              this.$message.warning('请补全信息！')
            }else if (!this.checkId()) {
              this.$message.error('班级号重复！')
            }else{
              this.$axios.post('/addBanji','b_id='+this.form.b_id+'&b_name='+this.form.b_name).then(successResponse => {
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
