<template>
  <el-container>

    <el-aside style="width: 200px;height:500px;margin-top: 20px">
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
        name: "Smain",
        components: {Left}


    }
</script>

<style scoped>

</style>
