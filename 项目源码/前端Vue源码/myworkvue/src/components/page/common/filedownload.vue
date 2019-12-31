<template>
  <el-card class="box-card">

    <div slot="header">
      <span><i class="el-icon-connection"></i> 文件 (共{{files.length}}项)</span>
    </div>

    <div>
      <div v-for="f in files">
        <el-link @click="LinkHref(f)" type="primary">{{f.name}}</el-link>
        <div style="margin-bottom: 15px"></div>
      </div>
    </div>

  </el-card>
</template>

<script>
    export default {
        name: "filedownload",
        props:{

          files:{
            type:Array,
            required: true
          }
        },
        methods:{
          LinkHref(f){
            var path = '/download?f_id='+f.f_id+'&f_name='+f.name
            this.$axios.post(path, {}, {
              responseType: 'blob'
            }).then(res => {
              let url = window.URL.createObjectURL(res.data)
              let a = document.createElement('a')
              document.body.appendChild(a)
              a.href = url
              a.download = f.name
              a.click()
              window.URL.revokeObjectURL(url)
              document.body.removeChild(a)

              this.$message.success("下载成功！")
            })

          }
        }
      }
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }
  .box-card {
    width: 300px;
  }
</style>
