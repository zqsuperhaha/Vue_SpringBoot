<template>
    <div>

      <div v-show="isShow">

        <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left">
          <el-breadcrumb-item>已批改列表</el-breadcrumb-item>
          <el-breadcrumb-item>共{{ypList.length}}项</el-breadcrumb-item>
        </el-breadcrumb>

        <el-input placeholder="请输入内容" style="width: 300px;float: right" v-model="pgany">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>

        <el-table
          :data="ypList"
          height="470"
          style="clear: both"
          stripe
          highlight-current-row
          :header-cell-style="{background:'rgba(255, 131, 25, 0.13)',color:'#606266'}">

          <el-table-column
            type="index"
            align="center"
            label="序号"
            width="100">
          </el-table-column>
          <el-table-column
            prop="w_id"
            label="作业号"
            align="center"
            width="110">
          </el-table-column>
          <el-table-column
            prop="tag"
            label="作业状态"
            align="center"
            width="120"
            :filters="[{ text: '按时交', value:'1' }, { text: '补交', value: '3' }, { text: '重交', value: '4' }]"
            :filter-method="filterTag"
            filter-placement="bottom-end">
            <template slot-scope="scope">
              <el-tag
                :type="scope.row.status === '1' ? 'success' :(scope.row.status === '2' ? 'primary':'warning') "
                disable-transitions>{{scope.row.status === '1' ? '按时交' :(scope.row.status === '3' ? '补交':'重交')}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column
            prop="c_name"
            label="课程"
            align="center"
            width="150">
          </el-table-column>
          <el-table-column
            prop="s_name"
            label="学生"
            align="center"
            width="90">
          </el-table-column>
          <el-table-column
            prop="pjdate"
            label="评价日期"
            :formatter="BeginDateFormatter"
            sortable
            align="center"
            width="180"
            style="color: dodgerblue">
          </el-table-column>
          <el-table-column
            prop="w_score"
            label="得分"
            align="center"
            width="100">
          </el-table-column>

          <el-table-column label="批改详情" align="center" :fit="true">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">点击查看</el-button>
            </template>
          </el-table-column>

        </el-table>
      </div>

      <div v-show="!isShow">
        <Detail :checkedrow="checkedrow" :files="newfiles" :scorelist="scorelist"></Detail>
      </div>

    </div>
</template>

<script>
  import store from '@/store'
  import moment from 'moment'
  import Detail from './detail'

    export default {
      name: "checkedlist",
      components:{Detail},
      data(){
        return {
          ypList:[],
          pgany: '',
          isShow:true,
          newfiles:[],
          checkedrow:{},
          scorelist:{}
        }
      },
      mounted: function () {

        // 已批改 作业列表
        this.$axios.post('/findTchYp','t_id='+store.state.user.username).then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.ypList = successResponse.data
          }
        })
      },
      methods: {

        // 状态筛选 --- 批改状态
        filterTag(value, row) {
          return row.status === value;
        },


        // 日期格式
        BeginDateFormatter(row, column) {

          return moment(row.pjdate).format('YYYY-MM-DD HH:mm')
        },

        // 模糊查询
        search(){
          this.$axios.post('/findTchYpSH','t_id='+store.state.user.username+'&pgany='+this.pgany).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.ypList = successResponse.data
            }
          })
        },

        // 查询对应作业的文件
        handleEdit(index, row) {
          this.checkedrow = row
          this.$axios.get('/findSubFiles?w_id='+row.w_id+'&s_id='+row.s_id).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              let files = successResponse.data
              for (let i = 0; i < files.length; i++) {
                this.newfiles.push(files[i])
              }
            }
          })

          // 查询分数设置
          this.$axios.get('/findScore').then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.scorelist = successResponse.data[0]
            }
          })

          this.isShow = false
        },


      }
    }
</script>

<style scoped>

</style>
