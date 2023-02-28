import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"

// 引入富文本组件
import VueQuillEditor from 'vue-quill-editor'
// 引入富文本组件样式
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import axios from '@/axios'

//导入permission.js，用户进行前端的权限控制
import "./permission"

// //实现quill-editor编辑器拖拽上传图片
import Quill from 'quill'
import { ImageDrop } from 'quill-image-drop-module'
Quill.register('modules/imageDrop', ImageDrop)
// //实现quill-editor编辑器调整图片尺寸
import ImageResize from 'quill-image-resize-module'
Quill.register('modules/imageResize', ImageResize)

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.use(Element)
Vue.use(VueQuillEditor)

//默认登录
if (!localStorage.getItem('token')){
  console.log('自动配置token')
  localStorage.setItem('userInfo','{"name":"admin","id":1,"account":123456,"token":"eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiYWNjb3VudCI6MTIzNDU2LCJqdGkiOiJ0b2tlbklkIiwiaWF0IjoxNjc2ODE0NjY3LCJleHAiOjE2NzY4MTcyNTl9.V2ZX2xtZA2xkpaiwvFu9VBwra43dzz0ny8Zgtte3IQE"}')
  localStorage.setItem("token", 'eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiYWNjb3VudCI6MTIzNDU2LCJqdGkiOiJ0b2tlbklkIiwiaWF0IjoxNjc2ODE0NjY3LCJleHAiOjE2NzY4MTcyNTl9.V2ZX2xtZA2xkpaiwvFu9VBwra43dzz0ny8Zgtte3IQE')
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

