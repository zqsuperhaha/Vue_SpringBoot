<template>
  <el-container>

    <el-aside style="width: 200px;height:500px;margin-top: 20px">
      <PersonLeft></PersonLeft>
    </el-aside>

    <el-main>
      <router-view style="width: 1000px;"/>
    </el-main>

  </el-container>
</template>

<script>
  import PersonLeft from './personleft'

  export default {
      name: "Person",
      components:{PersonLeft}
    }
</script>

<style scoped>

</style>
