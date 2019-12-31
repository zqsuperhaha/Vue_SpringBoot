<template>
  <div style="background-color: white">

    <div v-show="isValue">

      <div>

        <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left;margin-top: 10px;margin-left: 10px;margin-right: 10px">
          <el-breadcrumb-item>作业列表</el-breadcrumb-item>
          <el-breadcrumb-item>共{{pageAll}}项</el-breadcrumb-item>
        </el-breadcrumb>

        <Exportexcel class="btn" :exportName="name"></Exportexcel>

        <el-popover
          placement="top"
          width="160"
          v-model="visible">
          <p>{{delInfo}}</p>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="visible = false">取消</el-button>
            <el-button type="primary" size="mini" @click="deleteInfo">确定</el-button>
          </div>
          <el-button slot="reference" class="btn" size="medium" type="primary">批量删除</el-button>
        </el-popover>

        <el-input placeholder="请输入内容" style="width: 300px;float: left" v-model="workany">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>

      </div>

      <el-table
        id="workTable"
        v-loading="loading"
        :data="workList"
        border
        highlight-current-row
        @selection-change="handleSelectionChange"
        stripe
        style="clear: both;"
        :header-cell-style="{background:'rgba(105, 205, 255, 0.10)',color:'#606266'}">
        <el-table-column
          type="selection"
          width="50"
          align="center">
        </el-table-column>
        <el-table-column
          type="index"
          :index="table_index"
          label="序号"
          width="70"
          align="center">
        </el-table-column>
        <el-table-column
          prop="w_id"
          label="作业号"
          width="100"
          align="center">
        </el-table-column>
        <el-table-column
          prop="b_name"
          label="班级"
          width="120"
          align="center">
        </el-table-column>
        <el-table-column
          prop="c_name"
          label="课程"
          width="120"
          align="center">
        </el-table-column>
        <el-table-column
          prop="begindate"
          :formatter="BeginDateFormatter"
          label="布置日期"
          width="160"
          sortable
          column-key="begindate"
          align="center">
        </el-table-column>
        <el-table-column
          prop="enddate"
          :formatter="EndDateFormatter"
          label="截止日期"
          width="160"
          sortable
          align="center">
        </el-table-column>

        <el-table-column
          prop="yjnums"
          label="已交人数"
          width="50"
          align="center">
          <template slot-scope="scope">
            <el-link type="primary" @click="openYj(scope.row.w_id)">{{scope.row.yjnums}}</el-link>
          </template>
        </el-table-column>

        <el-table-column
          prop="njnums"
          label="未交人数"
          width="50"
          align="center">
          <template slot-scope="scope">
            <el-link type="primary" @click="openNj(scope.row.w_id)">{{scope.row.njnums}}</el-link>
          </template>
        </el-table-column>

        <el-table-column label="作业详情" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" plain @click="openDetail(scope.row)">点击查看</el-button>
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

    <div v-show="isValue1">
      <YDetail :w_id="wid" :yjListCount="yjListCount" :yjList="yjList"></YDetail>
    </div>

    <div v-show="isValue2">
      <NDetail  :w_id="wid" :njListCount="njListCount" :njList="njList"></NDetail>
    </div>

    <div v-show="isValue3">
      <Workdetail :workrow="workrow" :files="files" :subfiles="subfiles"></Workdetail>
    </div>

  </div>

</template>

<script>
  import store from '@/store'
  import moment from 'moment'
  import Editwork from "./editwork"
  import Exportexcel from "@/components/page/common/exportexcel"
  import YDetail from "@/components/page/common/yjdetail"
  import NDetail from "@/components/page/common/njdetail"
  import Workdetail from  './workdetail'

  export default {
    name: "tworklist",
    components: {Editwork,Exportexcel,YDetail,NDetail,Workdetail},
    inject:['reload'],

    data() {

        return {
          workList:[
            {w_id:this.w_id},
            {b_id:this.b_id},
            {b_name:this.b_name},
            {c_id:this.c_id},
            {c_name:this.c_name},
            {begindate:this.begindate},
            {enddate:this.enddate},
            {jianjie:this.jianjie}
          ],
          currentRow: null,
          pagesize:8,
          pageAll:0,
          currentPage:1,
          workany:'',
          visible: false,
          delList:[],
          name:'tchwork',
          isValue:true,
          isValue1:false,
          isValue2:false,
          isValue3:false,
          yjListCount:0,
          yjList:[],
          njListCount:0,
          njList:[],
          wid:'',
          workrow:{},
          files:[],
          subfiles:[],
          loading: true,
        }
      },
      computed:{
        delInfo:function () {
          if(this.delList.length==0){
            return '请先选中需要删除的作业！'
          }else{
            return '您确定删除'+this.delList.length+'条作业吗？'
          }
        }
      },
      mounted: function () {

        // 第一页查询
        this.$axios.post('/findTchWork','t_id='+store.state.user.username+'&page=0').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.workList = successResponse.data
            this.loading = false
          }
        })

        // 总数量
        this.$axios.get('/findTchWorkCount?t_id='+store.state.user.username).then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.pageAll = successResponse.data
          }
        })
      },
      methods:{

        // 日期格式
        BeginDateFormatter (row, column) {

          return moment(row.begindate).format('YYYY-MM-DD HH:mm')
        },
        EndDateFormatter (row, column) {

          return moment(row.enddate).format('YYYY-MM-DD HH:mm')
        },

        // 切换页面查询（分页）
        handleCurrentChange(val) {
          if(this.workany.trim()==""){

            // 正常查询
            this.$axios.post('/findTchWork','t_id='+store.state.user.username+'&page='+(val-1)*8).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.workList = successResponse.data
              }
            })

          }else{

            // 模糊查询
            this.$axios.post('/findTchWorkSH','t_id='+store.state.user.username+'&workany='+this.workany+'&page='+(val-1)*8).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.workList = successResponse.data
              }
            })
          }
        },

        // 序号连续变化
        table_index(index){
          return (this.currentPage-1) * this.pagesize + index + 1
        },

        // 模糊查询
        search(){

          // 数量
          this.$axios.post('/findTchWorkSHCount','t_id='+store.state.user.username+'&workany='+this.workany).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.pageAll = successResponse.data
            }
          })

          // 详情
          this.$axios.post('/findTchWorkSH','t_id='+store.state.user.username+'&workany='+this.workany+'&page=0').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.workList = successResponse.data
            }
          })

          this.currentPage = 1

        },

        // 全选-单选
        handleSelectionChange(val) {
          this.delList = val;
        },

        // 批量删除
        deleteInfo(){
          if(this.delList.length!=0){

            this.$axios.post('/deleteWork',this.delList).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.reload();
                this.$message.success('批量删除成功！')
              }
            })
          }
          this.visible = false
        },

        // 打开已交详情
        openYj(w_id){

          this.wid = w_id

          this.isValue = false
          this.isValue2 = false
          this.isValue1 = true

          // 数量
          this.$axios.post('/findYjCount','w_id='+w_id).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.yjListCount = successResponse.data
            }
          })

          // 详情
          this.$axios.post('/findYj','w_id='+w_id+'&page=0').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.yjList = successResponse.data
            }
          })

        },

        // 打开未交详情
        openNj(w_id){

          this.wid = w_id

          this.isValue = false
          this.isValue1 = false
          this.isValue2 = true

          // 数量
          this.$axios.post('/findNjCount','w_id='+w_id).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.njListCount = successResponse.data
            }
          })

          // 详情
          this.$axios.post('/findNj','w_id='+w_id+'&page=0').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.njList = successResponse.data
            }
          })
        },

        // 打开作业详情
        openDetail(row){

          this.workrow = row

          this.isValue = false
          this.isValue1 = false
          this.isValue2= false

          this.isValue3 = true

          // 作业文件
          this.$axios.get('/findFiles?w_id='+row.w_id).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              let files = successResponse.data
              for (let i = 0; i < files.length; i++) {
                this.files.push(files[i])
              }
            }
          })

          // 提交文件
          this.$axios.get('/findAllSubFiles?w_id='+row.w_id).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              let files2 = successResponse.data
              for (let i = 0; i < files2.length; i++) {
                this.subfiles.push(files2[i])
              }
            }
          })

        }

      }
    }
</script>

<style scoped>
  .btn{
    margin-top: 2px;
    margin-right: 5px;
    float: right;
  }
</style>
