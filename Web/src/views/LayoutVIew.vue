<template>
  <el-container>
    <el-header>
      <el-menu :default-active="((activeIndexComputed+1) === 4?'4-1':(activeIndexComputed+1)).toString()"
               class="el-menu-demo"
               mode="horizontal" @select="handleSelect"
               background-color="#545c64"
               text-color="#fff"
               active-text-color="#ffd04b">
        <el-menu-item index="1">主页</el-menu-item>
        <el-menu-item index="2">写日记</el-menu-item>
        <el-menu-item index="3">时光轴</el-menu-item>
        <el-submenu index="4" v-if="userinfo">
          <template slot="title">{{ userinfo.name }}</template>
          <el-menu-item index="4-1">修改个人信息</el-menu-item>
          <el-menu-item index="4-2">退出登录</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-header>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>

</template>

<style scoped>
.el-container {
  width: 100%;
  height: 100%;
}

.el-header {
  padding: 0 !important;
}

.el-main {
  padding: 0 !important;
}

.el-menu-demo {
  padding: 0 20px;
}

.el-menu-demo >>> .el-submenu {
  float: right;
}

</style>

<script>
import tools from '@/utils/utils'

export default {
  name: 'LayoutView',
  data() {
    return {
      activeIndex: '1',
      userinfo: null
    };
  },
  computed: {
    childernRoutes() {
      var routes = this.$router.options.routes
      let route = this.$route.matched;
      for (var i = 0; i < route.length - 1; i++) routes = routes.find((e) => e.name === route[i].name)
      return routes.children
    },
    activeIndexComputed() {
      return this.childernRoutes.findIndex((e) => e.component.name === this.$route.name)
    }
  },
  created() {
    this.userinfo = tools.getUserInfo()
  },
  methods: {
    handleSelect(key) {
      // console.log(key, keyPath);
      if (key === '1') {
        this.$router.replace('/')
      } else if (key === '2') {
        this.$router.replace('/editor')
      } else if (key === '3') {
        this.$router.replace('/time')
      } else if (key === '4-1') {
        this.$router.replace('/userSetting')
      } else if (key === '4-2') {
        this.$notify.info("用户 " + this.userinfo.name + " 已退出！")
        tools.loginOut()
      }
    },

  }
}
</script>