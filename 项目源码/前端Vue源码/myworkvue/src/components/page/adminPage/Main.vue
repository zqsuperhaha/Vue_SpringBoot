<template>
  <el-container>

    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <Left></Left>
    </el-aside>

    <el-main>
      <router-view style="width: 1000px;"/>
    </el-main>

  </el-container>
</template>

<script>
    import Left from './left'

    export default {
        name: "Main",
        components: {Left}

    }
</script>

<style scoped>

</style>
