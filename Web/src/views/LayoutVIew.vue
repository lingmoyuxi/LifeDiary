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
          <template slot="title">
            <el-avatar :size="45" :src="this.$axios.defaults.baseURL + userinfo.icon"></el-avatar>
            {{ userinfo.name }}
          </template>
          <el-menu-item index="4-1">管理</el-menu-item>
          <el-menu-item index="4-2">退出</el-menu-item>
        </el-submenu>
      </el-menu>
    </el-header>
    <el-main style="height: 100%">
      <router-view/>
    </el-main>
  </el-container>

</template>

<style scoped>

.el-container {
  position: relative;
  left: 0px;
  right: 0px;
  top: 0px;
  bottom: 0px;
  width: 100%;
  height: 100%;
}

.el-header {
  padding: 0 !important;
}

.el-main {
  padding: 0 !important;
  background: url("@/assets/bg.jpg");
  background-size: cover;
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
      // userinfo: null
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
    },
    userinfo() {
      return tools.getUserInfo()
    }
  },
  created() {
    // this.userinfo = tools.getUserInfo()
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