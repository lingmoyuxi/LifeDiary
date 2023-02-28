<!--
*@自定义的富文本编辑器
*@author MoYuXi
*@date 2023/2/22 16:02:23
-->
<template>
  <el-form :inline="true" :model="diary" class="demo-form-inline" :rules="rules" ref="from">

    <el-upload
        class="avatar-uploader"
        :action="uploadImgUrl"
        :accept="'image/*,video/*'"
        :show-file-list="false"
        :on-success="uploadEditorSuccess"
        :on-error="uploadEditorError"
        :before-upload="beforeEditorUpload"/>

    <el-form-item label="标题" size="mini" prop="title">
      <el-input v-model="diary.title" placeholder="日记的标题是什么呢？"/>
    </el-form-item>
    <el-form-item label="分类" size="mini" prop="type">
      <el-autocomplete
          class="inline-input"
          v-model="diary.type"
          :fetch-suggestions="querySearch"
          placeholder="未分类"/>
    </el-form-item>
    <el-form-item size="mini">
      <el-button type="primary" @click="resetForm('from')">{{ isUpdateMode ? "还原日记" : "重置日记" }}</el-button>
    </el-form-item>
    <el-form-item size="mini">
      <el-button type="primary" @click="onSubmit('from')">{{ isUpdateMode ? "更新日记" : "保存日记" }}</el-button>
    </el-form-item>
    <el-form-item size="mini" v-if="isUpdateMode">
      <el-button type="primary" @click="onExit">退出编辑</el-button>
    </el-form-item>
    <el-form-item size="mini" prop="content" :inline-message="true"/>
    <quill-editor class="editor" v-model="diary.content" ref="customQuillEditor" :options="editorOption"/>

  </el-form>
</template>

<script>
import * as Quill from 'quill'

// 这里引入修改过的video模块并注册
import Video from '@/utils/video'
import tools from "@/utils/utils";
//获取登录token，引入文件，如果只是简单测试，没有上传文件是否登录的限制的话，
//这个token可以不用获取，文件可以不引入，把上面对应的上传文件携带请求头  :headers="headers" 这个代码删掉即可

Quill.register(Video, true)

const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
  ['blockquote', 'code-block'],

  [{'header': 1}, {'header': 2}],               // custom button values
  [{'list': 'ordered'}, {'list': 'bullet'}],
  [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
  [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
  [{'direction': 'rtl'}],                         // text direction

  [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
  [{'header': [1, 2, 3, 4, 5, 6, false]}],

  [{'color': []}, {'background': []}],          // dropdown with defaults from theme
  [{'font': []}],
  [{'align': []}],
  ['clean'],                                         // remove formatting button
  ['link', 'image', 'video'],
];

export default {
  name: "MyQuillEditor",
  data() {
    return {
      headers: {
        Authorization: tools.getToken(),
      },
      isUpdateMode: false,
      oldDiary: {},
      diary: {
        title: '',
        type: '',
        content: '',
      },
      rules: {
        content: [{required: true, message: '正文不能为空', trigger: 'blur'}],
        title: [{required: true, message: '请输入日记标题', trigger: 'blur'}],
        type: [{required: true, message: '请归类本篇日记的类型', trigger: 'blur'}],
      },
      restaurants: [
        // {'value': '日常',},
      ],
      uploadImgUrl: "/v1/admin/common/upload",
      uploadUrlPath: "没有文件上传",
      quillUpdateImg: false,
      content: '',    //最终保存的内容
      editorOption: {
        placeholder: '今天有发生了什么有趣的事情呢？',
        modules: {
          imageResize: {
            displayStyles: {
              backgroundColor: 'black',
              border: 'none',
              color: 'white'
            },
            modules: ['Resize', 'DisplaySize', 'Toolbar']
          },
          imageDrop: false,
          toolbar: {
            container: toolbarOptions,  // 工具栏
            handlers: {
              // 'image': function (value) {
              //   if (value) {
              //     document.querySelector('.avatar-uploader input').click()
              //   } else {
              //     this.quill.format('image', false);
              //   }
              // },
              // 'video': function (value) {
              //   if (value) {
              //     document.querySelector('.avatar-uploader input').click()
              //   } else {
              //     this.quill.format('video', false);
              //   }
              // },
            }
          }
        }
      },
      toolbarTips: [
        {
          Choice: '.ql-bold',
          title: '加粗'
        },
        {
          Choice: '.ql-italic',
          title: '倾斜'
        },
        {
          Choice: '.ql-underline',
          title: '下划线'
        },
        {
          Choice: '.ql-header',
          title: '段落格式'
        },
        {
          Choice: '.ql-strike',
          title: '删除线'
        },
        {
          Choice: '.ql-blockquote',
          title: '块引用'
        },
        {
          Choice: '.ql-code-block',
          title: '代码块'
        },
        {
          Choice: '.ql-size',
          title: '字体大小'
        },
        {
          Choice: '.ql-list[value="ordered"]',
          title: '编号列表'
        },
        {
          Choice: '.ql-list[value="bullet"]',
          title: '项目列表'
        },
        {
          Choice: '.ql-script:first-child',
          title: '下标'
        },
        {
          Choice: '.ql-script:last-child',
          title: '上标'
        },
        {
          Choice: '.ql-header[value="1"]',
          title: 'H1'
        },
        {
          Choice: '.ql-header[value="2"]',
          title: 'H2'
        },
        {
          Choice: '.ql-align',
          title: '对齐方式'
        },
        {
          Choice: '.ql-color',
          title: '字体颜色'
        },
        {
          Choice: '.ql-direction',
          title: '文字方向'
        },
        {
          Choice: '.ql-background',
          title: '背景颜色'
        },
        {
          Choice: '.ql-image',
          title: '图像'
        },
        {
          Choice: '.ql-video',
          title: '视频'
        },
        {
          Choice: '.ql-link',
          title: '添加链接'
        },
        {
          Choice: '.ql-formula',
          title: '插入公式'
        },
        {
          Choice: '.ql-clean',
          title: '清除格式'
        },
        {
          Choice: '.ql-indent[value="-1"]',
          title: '向左缩进'
        },
        {
          Choice: '.ql-indent[value="+1"]',
          title: '向右缩进'
        },
        {
          Choice: '.ql-header .ql-picker-label',
          title: '标题大小'
        },
        {
          Choice: '.ql-header .ql-picker-item[data-value="1"]',
          title: '标题一'
        },
        {
          Choice: '.ql-header .ql-picker-item[data-value="2"]',
          title: '标题二'
        },
        {
          Choice: '.ql-header .ql-picker-item[data-value="3"]',
          title: '标题三'
        },
        {
          Choice: '.ql-header .ql-picker-item[data-value="4"]',
          title: '标题四'
        },
        {
          Choice: '.ql-header .ql-picker-item[data-value="5"]',
          title: '标题五'
        },
        {
          Choice: '.ql-header .ql-picker-item[data-value="6"]',
          title: '标题六'
        },
        {
          Choice: '.ql-header .ql-picker-item:last-child',
          title: '标准'
        },
        {
          Choice: '.ql-size .ql-picker-item[data-value="small"]',
          title: '小号'
        },
        {
          Choice: '.ql-size .ql-picker-item[data-value="large"]',
          title: '大号'
        },
        {
          Choice: '.ql-size .ql-picker-item[data-value="huge"]',
          title: '超大号'
        },
        {
          Choice: '.ql-font .ql-picker-label',
          title: '字体'
        },
        {
          Choice: '.ql-size .ql-picker-item:nth-child(2)',
          title: '标准'
        },
        {
          Choice: '.ql-align .ql-picker-item:first-child',
          title: '居左对齐'
        },
        {
          Choice: '.ql-align .ql-picker-item[data-value="center"]',
          title: '居中对齐'
        },
        {
          Choice: '.ql-align .ql-picker-item[data-value="right"]',
          title: '居右对齐'
        },
        {
          Choice: '.ql-align .ql-picker-item[data-value="justify"]',
          title: '两端对齐'
        },
        {
          Choice: '.ql-insertMetric',
          title: '插入指标'
        },
        {
          Choice: '.ql-save',
          title: '保存'
        }
      ],


    }
  },
  props: {
    // diary:Object,
    diaty_id: Number,
  },
  computed: {},
  methods: {
    //上传图片之前async
    beforeEditorUpload(res, file) {
      //显示上传动画
      this.quillUpdateImg = true;
      //  const res1 = await uploadImage()
      // console.log(res1,'=====');
      // this.$emit('before',res, file)
      console.log(res);
      console.log(file);
    },
    // 上传图片成功
    uploadEditorSuccess(res, file) {
      console.log("上传成功")
      // this.$emit('upload',res, file)
      console.log(res, file);
      //拼接出上传的图片在服务器的完整地址
      let imgUrl = res.data.url;
      let type = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);
      console.log(type);
      // 获取富文本组件实例
      let quill = this.$refs.customQuillEditor.quill;
      // 获取光标所在位置
      let length = quill.getSelection().index;
      // 插入图片||视频  res.info为服务器返回的图片地址
      if (type == 'mp4' || type == 'MP4') {
        quill.insertEmbed(length, 'video', imgUrl)
      } else {
        quill.insertEmbed(length, 'image', imgUrl)
      }
      // 调整光标到最后
      quill.setSelection(length + 1)
      //取消上传动画
      this.quillUpdateImg = false;
    },
    // 上传(文件)图片失败
    uploadEditorError(res, file) {
      console.log(res);
      console.log(file);
      //页面提示
      this.$message.error('上传图片失败')
      //取消上传动画
      this.quillUpdateImg = false;
    },
    //上传组件返回的结果
    uploadResult: function (res) {
      this.uploadUrlPath = res;
    },
    //提交事件
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isUpdateMode) {
            this.diary["id"] = this.diaty_id
            this.$axios.post('/diary/update', this.diary).then((res) => {
              if (res.code === 200) {
                this.resetForm(formName)
                this.$message.success("更新成功！")
                this.onExit()
              } else {
                this.$message.error(res.msg)
              }
            })
          } else {
            this.$axios.post('/diary/save', this.diary).then((res) => {
              if (res.code === 200) {
                this.resetForm(formName)
                this.$message.success("保存成功！")
              } else {
                this.$message.error(res.msg)
              }
            })
          }
        } else {
          return false;
        }
      });
    },
    //重置表单
    resetForm(form) {
      if (this.isUpdateMode) {
        this.diary = JSON.parse(JSON.stringify(this.oldDiary));
      } else {
        this.$refs[form].resetFields();
      }
    },
    onExit() {
      this.diary = []
      this.isUpdateMode = false
      this.$router.replace('/home');
    },
    //日记类型搜索
    querySearch(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter((e) => e.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },


  },
  mounted() {
    // 工具栏按钮提示
    for (let item of this.toolbarTips) {
      let tip = document.querySelector('.quill-editor ' + item.Choice);
      if (!tip) continue;
      tip.setAttribute('title', item.title);
    }
    if (!this.diaty_id) {
      let data = JSON.parse(sessionStorage.getItem('data'))
      //nextTick防止element 表单重置方法失效
      this.$nextTick(() => Object.assign(this, data))
      this.isUpdateMode = false;
    } else {
      this.$axios.get('/diary/diaryInfo', {params: {id: this.diaty_id}}).then((res) => {
        if (res.code == 200) {
          this.oldDiary = res.data;
          this.diary = JSON.parse(JSON.stringify(this.oldDiary));
          this.isShow = true;
          this.isUpdateMode = true;
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  },
  //保存数据
  beforeDestroy() {
    if(!this.isUpdateMode){
      if (this.diary.type || this.diary.content || this.diary.title) {
        sessionStorage.setItem('data', JSON.stringify(this.$data))
      } else {
        sessionStorage.removeItem('data')
      }
    }
  },

}
</script>

<style>

</style>