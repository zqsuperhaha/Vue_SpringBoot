<template>

  <el-menu
    class="el-menu"
    :default-active="$route.path"
    router>

    <el-menu-item index="/teacher/tmain/unchecklist">
      <i class="el-icon-edit"></i>
      <span slot="title">未批改</span>
    </el-menu-item>

    <el-menu-item index="/teacher/tmain/checkedlist">
      <i class="el-icon-delete"></i>
      <span slot="title">已批改</span>
    </el-menu-item>

    <el-menu-item index="/teacher/tmain/tworkadd">
      <i class="el-icon-position"></i>
      <span slot="title">布置作业</span>
    </el-menu-item>

    <el-menu-item index="/teacher/tmain/tworklist">
      <i class="el-icon-document"></i>
      <span slot="title">作业列表</span>
    </el-menu-item>

    <el-menu-item index="/teacher/tmain/tranklist">
      <i class="el-icon-s-flag"></i>
      <span slot="title">学生排名</span>
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
