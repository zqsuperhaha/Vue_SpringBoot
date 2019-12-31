<template>

    <div>

      <div v-show="isShow">

        <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left">
          <el-breadcrumb-item>作业列表</el-breadcrumb-item>
          <el-breadcrumb-item>共{{njList.length}}项</el-breadcrumb-item>
        </el-breadcrumb>

        <el-input placeholder="请输入内容" style="width: 300px;float: right" v-model="subany">
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>

        <el-table
          :data="njList"
          height="470"
          style="clear: both"
          stripe
          highlight-current-row
          :header-cell-style="{background:'rgba(105, 205, 255, 0.30)',color:'#606266'}">

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
            :fit="true">
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
            prop="c_name"
            label="课程"
            align="center"
            width="150">
          </el-table-column>
          <el-table-column
            prop="begindate"
            label="布置日期"
            :formatter="BeginDateFormatter"
            sortable
            align="center"
            width="180"
            style="color: dodgerblue">
          </el-table-column>
          <el-table-column
            prop="enddate"
            label="截止日期"
            :formatter="EndDateFormatter"
            sortable
            align="center"
            width="180"
            style="color: crimson">
          </el-table-column>
          <el-table-column label="作业详情" align="center" :fit="true">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">点击查看</el-button>
            </template>
          </el-table-column>

        </el-table>

      </div>

      <div v-show="!isShow">
        <Detail :unfinishrow="unfhrow" :files="newfiles" :tchinfo="tchinfo" ref="child"></Detail>
      </div>

    </div>
</template>

<script>
  import store from '@/store'
  import moment from 'moment'
  import Detail from './detail'

  export default {
        name: "unfinish",
        components:{Detail},
        data() {
          //  w_id,teacher.t_id,t_name,course.c_id,c_name,begindate,enddate,jianjie
          return {
            njList:[
              {
                w_id:this.w_id,
                t_id:this.t_id,
                t_name:this.t_name,
                c_id:this.c_id,
                c_name:this.c_name,
                begindate:this.begindate,
                enddate:this.enddate,
                jianjie:this.jianjie,
                status:this.status
              }
            ],
            isShow:true,
            unfhrow:{},
            files:{},
            newfiles:[],
            subany:'',
            tchinfo:{}
          }
        },
        mounted: function () {

          // 未完成 作业列表
          this.$axios.post('/findStuNj','s_id='+store.state.user.username).then(successResponse => {
            if (successResponse && successResponse.status === 200) {
              this.njList = successResponse.data
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

          // 查询对应作业的文件
          handleEdit(index, row) {
            this.unfhrow = row
            this.$axios.get('/findFiles?w_id='+row.w_id).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.files = successResponse.data
                for (let i = 0; i < this.files.length; i++) {
                  this.newfiles.push(this.files[i])
                }
              }
            })

            // 查询对应作业的教师信息
            this.$axios.post('/findWorkTch','w_id='+row.w_id).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.tchinfo = successResponse.data[0]
              }
            })

            this.isShow = false


            //
            // if(this.$refs.child.tabShow == 'no'){
            //   alert('ok?')
            //   // this.$refs.Tab.$children[0].$refs.Tab[1].style.display = 'none'
            // }else{
            //   alert(this.$refs.child.tabShow)
            // }
          },

          // 状态筛选
          filterTag(value, row) {
            return row.status === value;
          },

          // 模糊查询
          search(){
            this.$axios.post('/findStuNjSH','s_id='+store.state.user.username+'&subany='+this.subany).then(successResponse => {
              if (successResponse && successResponse.status === 200) {
                this.njList = successResponse.data
              }
            })
          }
        }
    }
</script>

<style scoped>
  .item{
    margin-bottom: 5px;
    color: rgba(105, 205, 255, 0.09);
  }
</style>
