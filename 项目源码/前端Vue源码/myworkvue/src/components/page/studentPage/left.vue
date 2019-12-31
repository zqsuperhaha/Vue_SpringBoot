<template>
  <el-menu
    class="el-menu"
    :default-active="$route.path"
    router>

    <el-menu-item index="/student/smain/unfinish">
      <i class="el-icon-edit"></i>
      <span slot="title">未完成</span>
    </el-menu-item>

    <el-menu-item index="/student/smain/finish">
      <i class="el-icon-delete"></i>
      <span slot="title">已完成</span>
    </el-menu-item>

    <el-menu-item index="/student/smain/sranklist">
      <i class="el-icon-s-flag"></i>
      <span slot="title">积分排名</span>
    </el-menu-item>

  </el-menu>
</template>

<script>
    export default {
        name: "left"
    }
</script>

<style scoped>

</style>
