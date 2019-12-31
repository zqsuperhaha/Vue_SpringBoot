<template>
  <el-menu
    class="el-menu"
    :default-active="$route.path"
    router>

    <el-menu-item index="/admin/person/userinfo">
      <i class="el-icon-s-custom"></i>
      <span slot="title">账号信息</span>
    </el-menu-item>

    <el-menu-item index="/admin/person/updatepwd">
      <i class="el-icon-menu"></i>
      <span slot="title">修改密码</span>
    </el-menu-item>

  </el-menu>
</template>

<script>
    export default {
        name: "personleft"
    }
</script>

<style scoped>

</style>
