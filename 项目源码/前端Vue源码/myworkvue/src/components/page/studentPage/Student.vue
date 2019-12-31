<template>
  <el-container>

    <el-header>
      <Top></Top>
    </el-header>

    <div style="margin-left: 20px">
      <router-view/>
    </div>

    <el-footer style="height: 60px;margin-top: 100px">
      <Foot></Foot>
    </el-footer>

  </el-container>
</template>

<script>
    import Top from './top'
    import Foot from '../common/foot'
    export default {
        name: "Student",
      components: {Top,Foot}
    }
</script>

<style scoped>

</style>
