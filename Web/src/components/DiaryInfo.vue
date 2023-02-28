<!--
*@日记详情
*@author MoYuXi
*@date 2023/2/21 15:06:50
-->
<template>
  <el-dialog class="indialog" :visible.sync="isShow" width="80%" :title="diary.title" >
    <el-form :inline="true" >
      <el-form-item ><span>日记分类：{{diary.type}}</span></el-form-item>
      <el-form-item ><span>创建时间：{{diary.createTime}}</span></el-form-item>
      <el-form-item v-if="diary.updateTime"><span>最近更新：{{diary.updateTime}}</span></el-form-item>
    </el-form>
    <div v-html="diary.content" class="myDiaryDiv"/>
  </el-dialog>
</template>

<script>
export default {
  name: "DiaryInfo",
  data() {
    return {
      diary:{},
      isShow:false
    }
  },
  computed: {},
  methods: {
    init(diaryId){
      this.$axios.get('/diary/diaryInfo',{params:{id:diaryId}}).then( (res) =>{
        if (res.code == 200){
          this.diary = res.data;
          this.isShow = true;
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  },
}
</script>

<style scoped>
.indialog >>> .el-dialog{
  background-color: #f1f0ed;
}
.indialog >>> .el-dialog__body {
  padding: 0 20px 20px;
}
.indialog >>> .el-dialog__header{
  padding: 20px;
}
.indialog >>> .el-dialog__header, .el-dialog__body {
  background-color: #f1f0ed;
}

.el-row {
  font-size: 20px;
}
.el-form >>> .el-form-item{
  margin: 0 10px;
}


.myDiaryDiv{
  max-height: 50vh;
  overflow: auto;
}
/* 滚动条整体 */
.myDiaryDiv::-webkit-scrollbar {
  height: 5px;
  width: 5px;
}
/* 两个滚动条交接处 -- x轴和y轴 */
.myDiaryDiv::-webkit-scrollbar-corner {
  background-color: transparent;
}
/* 滚动条滑块 */
.myDiaryDiv::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: #b2b6b6;
}
/* 滚动条轨道 */
.myDiaryDiv::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background: transparent;
}
/* 滚动条两端按钮 */
.myDiaryDiv::-webkit-scrollbar-button {
}
</style>
<style>
.myDiaryDiv{
  font-family:initial;
}
</style>