<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left;margin-top: 10px;margin-left: 10px;margin-right: 10px">
      <el-breadcrumb-item><a @click="reload()">作业列表</a></el-breadcrumb-item>
      <el-breadcrumb-item>未交列表</el-breadcrumb-item>
      <el-breadcrumb-item>共{{pageAll}}项</el-breadcrumb-item>
    </el-breadcrumb>

    <el-input placeholder="请输入内容" style="width: 300px;float: right" v-model="ynany">
      <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
    </el-input>

    <el-table
      id="yTable"
      :data="nList"
      highlight-current-row
      stripe
      style="clear: both;"
      :header-cell-style="{background:'rgba(204, 204, 204, 0.25)',color:'#606266'}">
      <el-table-column
        type="index"
        align="center"
        :index="table_index"
        label="序号"
        width="80">
      </el-table-column>
      <el-table-column
        prop="tag"
        label="状态"
        align="center"
        width="100"
        :filters="[{ text: '新作业', value:'1' }, { text: '过期', value: '2' }, { text: '补交', value: '3' }, { text: '重做', value: '4' }]"
        :filter-method="filterTag"
        filter-placement="bottom-end">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status === '1' ? 'success' : (scope.row.status === '2' ? 'danger':(scope.row.status === '3' ? 'primary' : 'warning'))"
            disable-transitions>{{scope.row.status === '1' ? '新作业' : (scope.row.status === '2' ? '过期':(scope.row.status === '3' ? '补交':'重做'))}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="s_id"
        align="center"
        label="学号"
        width="120">
      </el-table-column>
      <el-table-column
        prop="s_name"
        align="center"
        label="姓名"
        width="120">
      </el-table-column>
      <el-table-column
        prop="b_name"
        align="center"
        label="班级"
        width="130">
      </el-table-column>
      <el-table-column
        prop="s_phone"
        align="center"
        label="手机号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="s_email"
        align="center"
        label="邮箱"
        width="180">
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <Bjcheck :wid="wid" :bjrow="scope.row"></Bjcheck>
        </template>
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
    import Bjcheck from './bjcheck'
    export default {
      name: "ndetail",
      props:['w_id','njListCount','njList'],
      inject:['reload'],

      components:{Bjcheck},
      data(){
        return {
          nList:this.njList,
          pageAll:this.njListCount,
          wid:'',
          currentRow: null,
          pagesize:8,
          currentPage:1,
          delList:[],
          ynany:'',
          bujiao:'',
        }
      },
      watch:{
        // 时刻监听父组件的变化
        njList:function (newVal,oldVal) {
          this.nList = newVal;
        },
        njListCount:function (newVal,oldVal) {
          this.pageAll = newVal;
        },
        w_id:function (newVal,oldVal) {
          this.wid = newVal
        }
      },

      methods:{

        // 序号连续变化
        table_index(index){
          return (this.currentPage-1) * this.pagesize + index + 1
        },


        // 状态筛选
        filterTag(value, row) {
          return row.status === value;
        },

        // 切换页面查询（分页）
        handleCurrentChange(val) {
          if(this.ynany.trim()==""){

            // 正常查询
            this.$axios.post('/findNj','w_id='+this.w_id+'&page='+(val-1)*8).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.nList = successResponse.data
              }
            })

          }else{

            // 模糊查询
            this.$axios.post('/findNjSH','w_id='+this.w_id+'&ynany='+this.ynany+'&page='+(val-1)*8).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.nList = successResponse.data
              }
            })
          }
        },

        // 模糊查询
        search(){

          // 数量
          this.$axios.post('/findNjSHCount','w_id='+this.w_id+'&ynany='+this.ynany).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.pageAll = successResponse.data
            }
          })

          // 详情
          this.$axios.post('/findNjSH','w_id='+this.w_id+'&ynany='+this.ynany+'&page=0').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.nList = successResponse.data
            }
          })

          this.currentPage = 1

        }

      }
    }
</script>

<style scoped>

</style>
