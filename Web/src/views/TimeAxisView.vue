<!--
*@TimeAxisView
*@author MoYuXi
*@date 2023/2/9 18:39:31
-->
<template>
  <el-container class="content">
    <el-main>
      <el-timeline v-if="course.length > 0">
        <el-timeline-item v-for="(value,index) in course" :timestamp="value.time"
                          placement="top">
          <el-card>
            {{ value.incident }}
          </el-card>
        </el-timeline-item>
      </el-timeline>
      <el-timeline v-else>
        <el-empty description="这里空空的，啥都没有哦！"/>
      </el-timeline>

    </el-main>

    <el-aside width="350px">
      <el-card style="flex-shrink: 0">
        <div id="he-plugin-standard">
          <p>今天天气如何呢……</p>
        </div>
      </el-card>
      <el-card v-if="origin.title.length !== 0">
        <el-row>
          <el-col align="center">{{ origin.title }}</el-col>
        </el-row>
        <el-row>
          <el-col align="center"> （{{ origin.dynasty }}）{{ origin.author }}</el-col>
        </el-row>
        <el-row>
          <el-col class="shiciCol">
            <div id="shici"/>
          </el-col>
        </el-row>

      </el-card>
      <el-card v-else>
        <p>今天要学那首诗词呢……</p>
      </el-card>
    </el-aside>
  </el-container>

</template>

<script>
export default {
  name: "TimeAxisView",
  data() {
    return {
      course: [],
      origin: {
        author: "",
        content: [],
        dynasty: '',
        title: '',
      },
    }
  },
  methods: {},
  mounted() {
    this.$axios.get('/course').then(
        res => {
          if (res.code === 200) {
            this.course = res.data
          } else {
            this.$message.error(res.msg)
          }
        }
    )

    //和风天气插件调用
    window.WIDGET = {
      "CONFIG": {
        "layout": "1",
        "width": "300",
        "height": "150",
        "border": 'true',
        "borderRadius": '25',
        "background": "5",
        "dataColor": "000",
        "key": "cd06d238b6f14143bcd98e1d892fe98a",
        // "location": "101300112",
        // "city": "CN101300101"
      }
    };
    (function (d) {
      var c = d.createElement('link');
      c.rel = 'stylesheet';
      c.href = 'https://widget.qweather.net/standard/static/css/he-standard.css?v=1.4.0';
      var s = d.createElement('script');
      s.src = 'https://widget.qweather.net/standard/static/js/he-standard.js?v=1.4.0';
      var sn = d.getElementsByTagName('script')[0];
      sn.parentNode.insertBefore(c, sn);
      sn.parentNode.insertBefore(s, sn);
    })(document);

    // 诗词接口请求
    this.$axios.get('https://v2.jinrishici.com/one.json?client=browser-sdk/1.2&X-User-Token=GZi5wcS3vFRdsLlT3EG4Gc%2BIhWu8%2FU2Y').then(
        res => {
          this.origin = res.data.origin
          this.$nextTick(() => {    //上面数据更新完后渲染界面，使用this.$nextTick等待渲染，防止document找不到元素
            const shiciDiv = document.getElementById('shici')
            if (shiciDiv === null) return
            const data = this.origin.content, interval = 1000 / 30
            var dataStr, now, index = 0, index2 = 0, then = Date.now()
            const writing = function () {
              requestAnimationFrame(writing)
              now = Date.now()
              var delta = now - then
              if (index < data.length && delta > interval) {
                dataStr = data[index]
                if (dataStr && index2 < dataStr.length) {
                  then = now - (delta % interval)
                  shiciDiv.innerHTML += dataStr[index2++]
                }
                if (index2 == dataStr.length) {
                  index++
                  index2 = 0
                  shiciDiv.innerHTML += "</br>"
                }
              }
            }
            writing()
          })
        }
    )


  }
}
</script>

<style scoped>
.content {
  padding: 20px;
}

.shiciCol {
  display: flex;
  justify-content: center;
}

.shiciCol > div {
  height: auto;
  max-height: 300px;
  overflow-y: auto;
}

.shiciCol > div::-webkit-scrollbar {
  width: 0 !important
}

.el-card {
  background-color: #f1f0ed;
}

.el-main::-webkit-scrollbar {
  width: 0 !important
}

.el-aside {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.el-aside > .el-card {
  margin-top: 20px;
}

/* 滚动条整体 */
.el-aside::-webkit-scrollbar {
  height: 5px;
  width: 5px;
}

/* 两个滚动条交接处 -- x轴和y轴 */
.el-aside::-webkit-scrollbar-corner {
  background-color: transparent;
}

/* 滚动条滑块 */
.el-aside::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: #b2b6b6;
}

/* 滚动条轨道 */
.el-aside::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background: transparent;
}

/* 滚动条两端按钮 */
.el-aside::-webkit-scrollbar-button {
}

</style>