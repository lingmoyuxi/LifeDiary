<!--
*@TimeAxisView
*@author MoYuXi
*@date 2023/2/9 18:39:31
-->
<template>
  <div class="content">
    <el-timeline>
      <el-timeline-item timestamp="2018/4/12" placement="top">
        <el-card>
          <h4>功能：最近提交，数据统计</h4>
          <p>王小虎 提交于 2018/4/12 20:46</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/4/13" placement="top">
        <el-card>
          <h4>更新 Github 模板</h4>
          <p>王小虎 提交于 2018/4/12 20:46</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/4/14" placement="top">
        <el-card>
          <h4>更新 Github 模板</h4>
          <p>王小虎 提交于 2018/4/12 20:46</p>
        </el-card>
      </el-timeline-item>

    </el-timeline>

  </div>

</template>

<script>
export default {
  name: "TimeAxisView",
  data() {
    return {}
  },
  methods: {},
}
</script>

<style scoped>
.content {
  padding: 20px;
}
.el-card{
  background-color: #f1f0ed;
}
</style>