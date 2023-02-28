<!--
*@EditorView
*@author MoYuXi
*@date 2023/2/9 18:27:55
-->
<template>
 <el-card>
   <my-quill-editor  :diaty_id="id"/>
 </el-card>
</template>

<script>
import tools from '@/utils/utils'
import myQuillEditor from "@/components/MyQuillEditor.vue"

export default {
  name: "EditorView",
  components:{
    myQuillEditor,
  },
  data() {
    return {
      id:Number,

    }
  },
  methods: {

  },
  created(e) {
    this.id = this.$route.params.id
  }
}
</script>

<style scoped>
.el-card{
  width:calc(100vw - 100px);
  height:calc(100vh - 150px);
  margin: auto;
  margin-top: calc(80px / 2);
  background-color: #f1f0ed;
}
.demo-form-inline {
  height: 100%;
}
.demo-form-inline >>> .editor {
  height: calc(100vh - 150px - 150px);
}
</style>
