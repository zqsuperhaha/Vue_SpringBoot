<template>
    <div>

      <div v-show="isShow">
        <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left">
          <el-breadcrumb-item>提交列表</el-breadcrumb-item>
          <el-breadcrumb-item>共{{yjList.length}}项</el-breadcrumb-item>
        </el-breadcrumb>

        <el-input placeholder="请输入内容" style="width: 300px;float: right" v-model="subany">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>

        <el-table
          :data="yjList"
          height="470"
          style="clear: both"
          stripe
          highlight-current-row
          :header-cell-style="{background:'rgba(94, 255, 22, 0.13)',color:'#606266'}">
          <el-table-column
            type="index"
            align="center"
            label="序号"
            width="70">
          </el-table-column>
          <el-table-column
            prop="w_id"
            label="作业号"
            align="center"
            :fit="true">
          </el-table-column>
          <el-table-column
            prop="tag"
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
            prop="c_name"
            label="课程"
            align="center"
            width="100">
          </el-table-column>
          <el-table-column
            prop="subdate"
            label="提交日期"
            :formatter="SubDateFormatter"
            sortable
            align="center"
            width="160"
            style="color: dodgerblue">
          </el-table-column>
          <el-table-column
            prop="pjdate"
            label="评价日期"
            :formatter="PjDateFormatter"
            sortable
            align="center"
            width="160"
            style="color: crimson">
          </el-table-column>

          <el-table-column
            prop="w_score"
            label="得分"
            sortable
            align="center"
            :fit="true">
          </el-table-column>
          <el-table-column label="提交详情" align="center" :fit="true">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">点击查看</el-button>
            </template>
          </el-table-column>

        </el-table>
      </div>

      <div v-show="!isShow">
        <Detail :finishrow="fhrow" :files="newfiles"></Detail>
      </div>

    </div>
</template>

<script>
  import store from '@/store'
  import moment from 'moment'
  import Detail from './detail'
    export default {
      name: "finish",
      components:{Detail},
      data() {
        return {
          // w_id,t_id,t_name,c_id,c_name,subdate,pjdate,ps,w_score,`status`,ischeck
          yjList:[
            {
              w_id:this.w_id,
              t_id:this.t_id,
              t_name:this.t_name,
              c_id:this.c_id,
              c_name:this.c_name,
              subdate:this.subdate,
              pjdate:this.pjdate,
              ps:this.ps,
              pingjia:this.pingjia,
              w_score:this.w_score,
              status:this.status,
              ischeck:this.ischeck
            }
          ],
          isShow:true,
          fhrow:{},
          files: {},
          newfiles:[],
          subany:''
        }
      },
      mounted: function () {

        // 已完成 作业列表
        this.$axios.post('/findStuYj','s_id='+store.state.user.username).then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.yjList = successResponse.data
          }
        })
      },
      methods:{

        // 日期格式
        SubDateFormatter (row, column) {

          return moment(row.subdate).format('YYYY-MM-DD HH:mm')
        },
        PjDateFormatter (row, column) {

          let normal = moment(row.pjdate).format('YYYY-MM-DD HH:mm')
          if(normal == '5000-01-01 00:00'){
            return '暂无'
          }else{
            return normal
          }
        },

        // 批改状态筛选
        filterTag(value, row) {
          return row.ischeck === value;
        },

        // 作业状态筛选
        filterTag2(value, row) {
          return row.status === value;
        },

        // 模糊查询
        search(){
          this.$axios.post('/findStuYjSH','s_id='+store.state.user.username+'&subany='+this.subany).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.yjList = successResponse.data
            }
          })
        },

        // 查询作业的文件
        handleEdit(index, row) {
          this.fhrow = row
          this.$axios.get('/findSubFiles?w_id='+row.w_id+'&s_id='+store.state.user.username).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.files = successResponse.data
              for (let i = 0; i < this.files.length; i++) {
                this.newfiles.push(this.files[i])
              }
            }
          })

          this.isShow = false
        },
      }
    }
</script>

<style scoped>

</style>
