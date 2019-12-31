<template>
  <div style="background-color: white;">

    <div>

      <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left;margin-top: 10px;margin-left: 10px;margin-right: 10px">
        <el-breadcrumb-item>班级列表</el-breadcrumb-item>
        <el-breadcrumb-item>共{{pageAll}}项</el-breadcrumb-item>
      </el-breadcrumb>

      <Importexcel class="btn" :who="banji"></Importexcel>
      <Exportexcel class="btn" :exportName="name"></Exportexcel>
      <Addbanji class="btn" :bjlist="banjiList"></Addbanji>


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

      <el-input placeholder="请输入内容" style="width: 300px;float: left" v-model="banjiany">
        <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
      </el-input>

    </div>

    <el-table
      id="banjiTable"
      :data="banjiList"
      highlight-current-row
      @selection-change="handleSelectionChange"
      stripe
      style="clear: both;">
      <el-table-column
        type="selection"
        width="70">
      </el-table-column>
      <el-table-column
        type="index"
        align="center"
        :index="table_index"
        label="序号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="b_id"
        align="center"
        label="班级号"
        width="230">
      </el-table-column>
      <el-table-column
        prop="b_name"
        align="center"
        label="班级名称"
        width="230">
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope" >
          <Editbanji :banjirow="scope.row"></Editbanji>
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
  import FileSaver from 'file-saver'
  import XLSX from 'xlsx'
  import Addbanji from "./addbanji"
  import Editbanji from './editbanji'
  import Importexcel from "@/components/page/common/importexcel"
  import Exportexcel from "@/components/page/common/exportexcel"


  export default {
    name: "banjilist",
    components: {Addbanji, Importexcel,Editbanji,Exportexcel},
    inject:['reload'],

    data() {
        return {

          banjiList:[
            {b_id:this.b_id},
            {b_name:this.b_name}
          ],
          currentRow: null,
          pagesize:8,
          pageAll:0,
          currentPage:1,
          visible: false,
          banjiany:'',
          delList:[],
          banji:'banji',
          name:'banji'
        };
      },
      computed:{
        delInfo:function () {
          if(this.delList.length==0){
            return '请先选中需要删除的班级！'
          }else{
            return '您确定删除'+this.delList.length+'项班级吗？'
          }
        }
      },
      mounted: function () {

        // 第一页查询
        this.$axios.post('/findAllBanjiPart','page=0').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.banjiList = successResponse.data
          }
        })

        // 总数量
        this.$axios.get('/findAllBanjiPartCount').then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.pageAll = successResponse.data
          }
        })
      },
      methods: {

        // 切换页面查询（分页）
        handleCurrentChange(val) {

          if(this.banjiany.trim()==""){

            // 正常查询
            this.$axios.post('/findAllBanjiPart','page='+(val-1)*8).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.banjiList = successResponse.data
              }
            })

          }else{

            // 模糊查询
            this.$axios.post('/findBanjiSearch','banjiany='+this.banjiany+'&page='+(val-1)*8).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.banjiList = successResponse.data
              }
            })
          }
        },

        // 模糊查询
        search(){

          // 数量
          this.$axios.post('/findBanjiSearchCount','banjiany='+this.banjiany).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.pageAll = successResponse.data
            }
          })

          // 详情
          this.$axios.post('/findBanjiSearch','banjiany='+this.banjiany+'&page=0').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.banjiList = successResponse.data
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
          let wb = XLSX.utils.table_to_book(document.querySelector('#banjiTable'));
          let wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' });
          try {
            FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '班级表.xlsx');
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

            this.$axios.post('/deleteBanji',this.delList).then(successResponse => {
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
