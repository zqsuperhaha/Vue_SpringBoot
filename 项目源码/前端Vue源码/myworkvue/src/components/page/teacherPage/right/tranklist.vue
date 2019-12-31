<template>
  <div>
    <el-input placeholder="请输入内容" style="width: 300px;float: right" v-model="rankany">
      <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
    </el-input>

    <Rank :rankList="rankList"></Rank>
  </div>
</template>

<script>
  import store from '@/store'
  import Rank from '@/components/page/common/rank'

  export default {
    name: "ranklist",
    components:{Rank},
    data(){
      return {
        rankList:[],
        rankany:''
      }
    },
    mounted() {

      // 学生排名列表
      this.$axios.post('/findTchRank','t_id='+store.state.user.username).then(successResponse => {
        if (successResponse && successResponse.status === 200) {
          this.rankList = successResponse.data
        }
      })

    },

    methods:{

      // 模糊查询
      search(){
        this.$axios.post('/findTchRankSH','t_id='+store.state.user.username+'&rankany='+this.rankany).then(successResponse => {
          if (successResponse && successResponse.status === 200) {
            this.rankList = successResponse.data
          }
        })
      }

    }
  }
</script>

<style scoped>

</style>
