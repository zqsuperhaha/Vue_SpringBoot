<template>
    <div>

      <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left;margin-top: 10px;margin-left: 10px;margin-right: 10px">
        <el-breadcrumb-item><a @click="reload()">作业列表</a></el-breadcrumb-item>
        <el-breadcrumb-item>已交列表</el-breadcrumb-item>
        <el-breadcrumb-item>共{{pageAll}}项</el-breadcrumb-item>
      </el-breadcrumb>

      <el-input placeholder="请输入内容" style="width: 300px;float: right" v-model="ynany">
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>

      <el-table
        id="yTable"
        :data="yList"
        highlight-current-row
        stripe
        style="clear: both;"
        :header-cell-style="{background:'rgba(204, 204, 204, 0.25)',color:'#606266'}">
        <el-table-column
          type="index"
          align="center"
          :index="table_index"
          label="序号"
          width="70">
        </el-table-column>

        <el-table-column
          prop="tag1"
          label="批改状态"
          align="center"
          width="100"
          :filters="[{ text: '已批改', value:'1' }, { text: '未批改', value: '0' }]"
          :filter-method="filterTag"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.ischeck === '1' ? 'success' :'warning'"
              disable-transitions>{{scope.row.ischeck === '1' ? '已批改' :'未批改'}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="tag2"
          label="作业状态"
          align="center"
          width="100"
          :filters="[{ text: '按时交', value:'1' }, { text: '补交', value: '3' }, { text: '重交', value: '4' }]"
          :filter-method="filterTag2"
          filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === '1' ? 'success' :(scope.row.status === '3' ? 'primary':'warning') "
              disable-transitions>{{scope.row.status === '1' ? '按时交' :(scope.row.status === '3' ? '补交':'重交')}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="w_score"
          align="center"
          label="作业得分"
          width="80">
        </el-table-column>

        <el-table-column
          prop="s_id"
          align="center"
          label="学号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="s_name"
          align="center"
          label="姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="b_name"
          align="center"
          label="班级"
          width="110">
        </el-table-column>
        <el-table-column
          prop="s_phone"
          align="center"
          label="手机号"
          :fit="true">
        </el-table-column>
        <el-table-column
          prop="s_email"
          align="center"
          label="邮箱"
          :fit="true">
        </el-table-column>

      </el-table>

      <el-pagination
        background
        layout="prev, pager, next, jumper"
        :page-size="pagesize"
        :total="pageAll"
        :current-page.sync="currentPage"
        @current-change="handleCurrentChange">
      </el-pagination>

    </div>
</template>

<script>
    export default {
      name: "ydetail",
      props:['w_id','yjListCount','yjList'],
      inject:['reload'],

      data(){
          return {
            yList:this.yjList,
            pageAll:this.yjListCount,
            currentRow: null,
            pagesize:8,
            currentPage:1,
            delList:[],
            ynany:''

          }
        },
        watch:{
          // 时刻监听父组件的变化
          yjList:function (newVal,oldVal) {
            this.yList = newVal;
          },
          yjListCount:function (newVal,oldVal) {
            this.pageAll = newVal;
          },
        },

        methods:{

          // 序号连续变化
          table_index(index){
            return (this.currentPage-1) * this.pagesize + index + 1
          },

          // 批改状态筛选
          filterTag(value, row) {
            return row.ischeck === value;
          },

          // 作业状态筛选
          filterTag2(value, row) {
            return row.status === value;
          },

          // 切换页面查询（分页）
          handleCurrentChange(val) {
            if(this.ynany.trim()==""){

              // 正常查询
              this.$axios.post('/findYj','w_id='+this.w_id+'&page='+(val-1)*8).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  this.yList = successResponse.data
                }
              })

            }else{

              // 模糊查询
              this.$axios.post('/findYjSH','w_id='+this.w_id+'&ynany='+this.ynany+'&page='+(val-1)*8).then(successResponse => {
                if (successResponse && successResponse.status === 200) {
                  this.yList = successResponse.data
                }
              })
            }
          },

          // 模糊查询
          search(){

            // 数量
            this.$axios.post('/findYjSHCount','w_id='+this.w_id+'&ynany='+this.ynany).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.pageAll = successResponse.data
              }
            })

            // 详情
            this.$axios.post('/findYjSH','w_id='+this.w_id+'&ynany='+this.ynany+'&page=0').then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.yList = successResponse.data
              }
            })

            this.currentPage = 1

          },

        }
    }
</script>

<style scoped>

</style>
