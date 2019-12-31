<template>
    <div>

      <!-- s_id,s_name,b_id,b_name,s_phone,s_email,sum(score) as oksum-->

      <el-breadcrumb separator-class="el-icon-arrow-right" style="float: left">
        <el-breadcrumb-item>积分排名</el-breadcrumb-item>
        <el-breadcrumb-item>共{{rankList.length}}项</el-breadcrumb-item>
      </el-breadcrumb>

      <el-table
        :data="rankList"
        stripe
        highlight-current-row
        :header-cell-style="{background:'rgba(255, 236, 7, 0.10)',color:'#606266'}">

        <el-table-column
          prop="orderby"
          align="center"
          sortable
          label="名次"
          width="100">
        </el-table-column>

        <el-table-column
          prop="s_id"
          label="学号"
          align="center"
          width="100">
        </el-table-column>

        <el-table-column
          prop="s_name"
          label="姓名"
          align="center"
          width="130">
        </el-table-column>

        <el-table-column
          prop="b_name"
          label="班级"
          align="center"
          width="150">
        </el-table-column>

        <el-table-column
          prop="s_phone"
          label="手机号"
          align="center"
          :fit="true">
        </el-table-column>

        <el-table-column
          prop="s_email"
          label="邮箱"
          align="center"
          :fit="true">
        </el-table-column>

        <el-table-column
          prop="oksum"
          label="积分"
          sortable
          align="center"
          :fit="true">
        </el-table-column>

      </el-table>

    </div>
</template>

<script>
    export default {
      name: "rank",
      props:['rankList']
    }
</script>

<style scoped>

</style>
