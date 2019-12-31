<template>
  <div style="background-color: white;">

    <div>

      <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left;margin-top: 10px;margin-left: 10px;margin-right: 10px">
        <el-breadcrumb-item>管理员列表</el-breadcrumb-item>
        <el-breadcrumb-item>共{{pageAll}}项</el-breadcrumb-item>
      </el-breadcrumb>

      <Importexcel class="btn" :who="admin"></Importexcel>
      <Exportexcel class="btn" :exportName="admin"></Exportexcel>
      <AddAdmin class="btn" :adminlist="adminList"></AddAdmin>

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

      <el-input placeholder="请输入内容" style="width: 300px;float: left" v-model="adminany">
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>

    </div>

    <el-table
      id="adminTable"
      :data="adminList"
      highlight-current-row
      @selection-change="handleSelectionChange"
      stripe
      style="clear: both;">
      <el-table-column
        type="selection"
        width="50">
      </el-table-column>
      <el-table-column
        type="index"
        align="center"
        :index="table_index"
        label="序号"
        width="80">
      </el-table-column>
      <el-table-column
        prop="a_id"
        align="center"
        label="用户名"
        width="120">
      </el-table-column>
      <!--<el-table-column-->
        <!--prop="a_pwd"-->
        <!--align="center"-->
        <!--label="密码"-->
        <!--width="100">-->
      <!--</el-table-column>-->
      <el-table-column
        prop="a_name"
        align="center"
        label="姓名"
        width="120">
      </el-table-column>

      <el-table-column
        prop="a_phone"
        align="center"
        label="手机号"
        :fit="true">
      </el-table-column>
      <el-table-column
        prop="a_email"
        align="center"
        label="邮箱"
        :fit="true">
      </el-table-column>

      <el-table-column
        prop="authority"
        align="center"
        label="权限"
        width="100">
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope" >
          <EditAdmin :adminrow="scope.row" :adminlist="adminList"></EditAdmin>
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
  import store from '@/store'
  import FileSaver from 'file-saver'
  import XLSX from 'xlsx'
  import AddAdmin from "./addadmin"
  import EditAdmin from './editadmin'
  import Importexcel from "@/components/page/common/importexcel"
  import Exportexcel from "@/components/page/common/exportexcel"

  export default {
    name: "adminlist",
    components: {AddAdmin, Importexcel,EditAdmin,Exportexcel},
    inject:['reload'],

    data() {
      return {
        adminList:[],
        currentRow: null,
        pagesize:8,
        pageAll:0,
        currentPage:1,
        visible: false,
        adminany:'',
        delList:[],
        admin:'admin',
      };
    },
    computed:{
      delInfo:function () {
        if(this.delList.length==0){
          return '请先选中需要删除的管理员！'
        }else{
          return '您确定删除'+this.delList.length+'项管理员吗？'
        }
      }
    },
    mounted: function () {

      // 第一页查询
      this.$axios.post('/findAllAdminPart','page=0').then(successResponse => {
        if (successResponse && successResponse.status === 200) {
          let admins = successResponse.data
          for (let i = 0; i < admins.length; i++) {
            if(store.state.user.username == admins[i].a_id){
              if(admins[i].authority=="1"){

                this.adminList = successResponse.data

                // 总数量
                this.$axios.get('/findAllAdminPartCount').then(successResponse => {
                  if (successResponse && successResponse.status === 200) {
                    this.pageAll = successResponse.data
                  }
                })

              }else{

                this.$alert('您没有权限访问！', '提示', {
                  confirmButtonText: '返回作业列表界面',
                  callback: action => {
                    this.$router.replace('worklist')
                  }
                });

              }
            }
          }
        }
      })


    },
    methods: {

      // 切换页面查询（分页）
      handleCurrentChange(val) {

        if(this.adminany.trim()==""){

          // 正常查询
          this.$axios.post('/findAllAdminPart','page='+(val-1)*8).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.adminList = successResponse.data
            }
          })

        }else{

          // 模糊查询
          this.$axios.post('/findAdminSearch','adminany='+this.adminany+'&page='+(val-1)*8).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.adminList = successResponse.data
            }
          })
        }
      },

      // 模糊查询
      search(){

        // 数量
        this.$axios.post('/findAdminSearchCount','adminany='+this.adminany).then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.pageAll = successResponse.data
          }
        })

        // 详情
        this.$axios.post('/findAdminSearch','adminany='+this.adminany+'&page=0').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.adminList = successResponse.data
          }
        })

        this.currentPage = 1

      },

      // 序号连续变化
      table_index(index){
        return (this.currentPage-1) * this.pagesize + index + 1
      },

      // 导出表
      exportExcel () {
        let wb = XLSX.utils.table_to_book(document.querySelector('#tchTable'));
        let wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' });
        try {
          FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '管理员表.xlsx');
        } catch (e)
        {
          if (typeof console !== 'undefined')
            console.log(e, wbout)
        }
        return wbout
      },

      // 全选-单选
      handleSelectionChange(val) {
        this.delList = val;
      },

      // 批量删除
      deleteInfo(){
        if(this.delList.length!=0){

          this.$axios.post('/deleteAdmin',this.delList).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.reload();
              this.$message.success('批量删除成功！')
            }
          })
        }
        this.visible = false
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
