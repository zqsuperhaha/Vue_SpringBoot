<template>
    <div>
      <el-card>
        <div slot="header">
          <span>文件设置</span>
        </div>
        <div>
          <el-form align="left" style="margin-left: 30px">

            <div style="margin-bottom: 10px"><i class="el-icon-link">&nbsp;&nbsp;文件类型：</i></div>
            <el-form-item align="left">

              <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>

              <el-checkbox-group v-model="checkedTypes">
                <el-checkbox v-for="t in typeList" :label="t" :key="t">{{t}}</el-checkbox>
              </el-checkbox-group>

            </el-form-item>

            <div style="margin-bottom: 20px"><i class="el-icon-takeaway-box">&nbsp;&nbsp;文件大小：</i></div>
            <el-form-item align="left">
              <el-input-number v-model="size" :min="1" :max="1000"></el-input-number>
              <el-select v-model="unit" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item align="right">
              <el-button @click="resetForm()">重置</el-button>
              <el-button type="primary" @click="updateType">确 定</el-button>
            </el-form-item>

          </el-form>
        </div>
      </el-card>

    </div>
</template>

<script>
    export default {
      name: "webset",
      data(){
        return {
          originTypes:[],
          typeList:[],
          checkAll: false,
          checkedTypes: [],
          isIndeterminate: true,

          originSize:0,
          size:0,

          originUnit:'',
          unit:'',

          options: [{
            value: 'B',
            label: 'B'
          }, {
            value: 'KB',
            label: 'KB'
          }, {
            value: 'MB',
            label: 'MB'
          }, {
            value: 'GB',
            label: 'GB'
          }],
        }
      },
      mounted: function () {
        this.$axios.get('/findType').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            let typelist = successResponse.data

            this.size = typelist[0].size
            this.originSize = this.size

            this.unit = typelist[0].unit
            this.originUnit = this.unit

            for (let i = 0; i < typelist.length; i++) {
              if(typelist[i].ischeck=="1"){
                this.checkedTypes.push(typelist[i].type)
              }
              this.typeList.push(typelist[i].type)
            }

            this.originTypes = this.checkedTypes
          }
        })

      },

      methods:{

        // 全选
        handleCheckAllChange(val) {
          this.checkedTypes = val ? this.typeList : [];
          this.isIndeterminate = false;
        },

        // 修改
        updateType(){
          this.$axios.post('/updateType','typelist='+this.checkedTypes+'&size='+this.size+'&unit='+this.unit).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.$message.success('文件设置成功！')
            }
          })
        },

        // 重置 -- 恢复到原来的
        resetForm() {
          this.checkedTypes = this.originTypes
          this.size = this.originSize
          this.unit = this.originUnit
        }

      }
    }
</script>

<style scoped>

</style>
