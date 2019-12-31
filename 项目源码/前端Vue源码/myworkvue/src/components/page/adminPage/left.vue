<template>
    <el-menu
      :default-openeds="['1','2','3']"
      :default-active="$route.path"
      router>
      <el-submenu index="1">
        <template slot="title"><i class="el-icon-message"></i>教务管理</template>
        <el-menu-item index="/admin/main/worklist">作业管理</el-menu-item>
        <el-menu-item index="/admin/main/banjilist">班级管理</el-menu-item>
        <el-menu-item index="/admin/main/courselist">课程管理</el-menu-item>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title"><i class="el-icon-user"></i>人员管理</template>
        <el-menu-item index="/admin/main/tchlist">教师管理</el-menu-item>
        <el-menu-item index="/admin/main/stulist">学生管理</el-menu-item>
        <el-menu-item index="/admin/main/adminlist">管理员管理</el-menu-item>
      </el-submenu>

      <el-submenu index="3">
        <template slot="title"><i class="el-icon-setting"></i>网站管理</template>
        <el-menu-item index="/admin/main/webset">文件设置</el-menu-item>
        <el-menu-item index="/admin/main/scoreset">积分设置</el-menu-item>
      </el-submenu>

  </el-menu>
</template>

<script>
    export default {
        name: "left"
    }
</script>

<style scoped>

</style>
