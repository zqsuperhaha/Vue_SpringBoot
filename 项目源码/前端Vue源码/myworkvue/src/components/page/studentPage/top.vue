<template>
  <el-menu
    :default-active="activeIndex"
    router
    mode="horizontal"
    background-color="white"
    text-color="#222"
    active-text-color="red"
    style="width: 1220px;margin-top: 5px;">

    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name" >
      {{ item.navItem }}
    </el-menu-item>

    <el-menu-item style="float: right" :index="back">
      退出
    </el-menu-item>

    <el-menu-item style="float: right;margin-right: 10px">
      <Setscore></Setscore>
    </el-menu-item>

  </el-menu>
</template>

<script>
    import Setscore from '@/components/page/common/setscore'
    export default {
        name: "top",
        components:{Setscore},
        data () {
          return {
            activeIndex:'/student/smain/unfinish',
            navList: [
              {name: '/student/smain/unfinish', navItem: '首页'},
              {name: '/student/sperson/userinfo', navItem: '个人中心'}
            ],
            back:'/login',
          }
        },
        mounted:function(){
          if(this.$route.path=='/student/sperson/unfinish'){
            this.activeIndex = this.$route.path
          }

          if(this.$route.path=='/student/sperson/userinfo' || this.$route.path== '/student/sperson/updatepwd'){
            this.activeIndex ='/student/sperson/userinfo'
          }
        }
    }
</script>

<style scoped>

</style>
