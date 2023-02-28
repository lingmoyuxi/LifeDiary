<template>
  <el-container style="height: 100%;">

    <el-aside>

      <el-card class="box-card" :body-style="{height:'180px'}" >
        <div slot="header" class="clearfix">
          <i class="el-icon-tickets"/>
          <span style="margin-left: 5px" slot="title">按类型查看</span>
        </div>
        <el-table size="mini" :data="diaryByGroup"
                  class="item" height="180" :show-header='false' @row-click="changeType">
          <el-table-column width="120px" prop="type" :show-overflow-tooltip="true"/>
          <el-table-column width="50px" prop="list.length" align="right"/>
        </el-table>
      </el-card>

      <el-card class="box-card" :body-style="{height:'120px'}">
        <div slot="header" class="clearfix">
          <i class="el-icon-time"/>
          <span style="margin-left: 5px">按日期查看</span>
        </div>
        <el-table size="mini" :data="diaryByGroup2"
                  class="item" height="120px" :show-header='false' @row-click="changeType2">
          <el-table-column width="120px" prop="type" :show-overflow-tooltip="true"/>
          <el-table-column width="50px" prop="list.length" align="right"/>
        </el-table>
      </el-card>

    </el-aside>

    <el-main>
      <el-card class="item">
        <div v-if="diaryByGroupList.length !== 0">
          <div slot="header" class="clearfix">
            <i class="el-icon-files"/>
            <span style="margin-left: 5px">日记列表</span>
            <el-input
                size="mini"
                style="float:right; width: 200px;"
                clearable
                v-model="searchText"
                placeholder="按标题搜索！"
                @input="searchDiary"
                @keyup.enter.native="searchDiary" >
              <i slot="prefix" class="el-input__icon el-icon-search" @click="searchDiary"/>
            </el-input>
          </div>

          <el-table :data="diaryByGroupList.slice((currpage - 1) * pagesize,currpage * pagesize)"
                    :header-cell-style="{'text-align':'center','background-color':'#f1f3f5',height:'55px',
                'width':'100%','color':'#000'}"
                    :row-style="{height:'55px'}"
                    stripe @row-click="handleDiaryChange">
            <el-table-column width="100px" :show-overflow-tooltip="true" prop="createTime" label="时间" :formatter="filterTime" sortable/>
            <el-table-column width="120px" :show-overflow-tooltip="true" prop="type" label="类型" align="center" sortable/>
            <el-table-column width="150px" :show-overflow-tooltip="true" prop="title" label="标题" align="center"/>
            <el-table-column width="auto" :show-overflow-tooltip="true" prop="content" label="内容" />
            <el-table-column width="120px" :show-overflow-tooltip="true" align="center">
              <template slot="header" slot-scope="scope">
                <el-switch v-model="isControl" inactive-text="操作" />
              </template>
              <template slot-scope="scope" :v-if="isControl">
                <el-button-group v-if="isControl" >
                  <el-button title="编辑" size="mini" type="primary" icon="el-icon-edit" @click.native.stop="edit(scope.row.id)"/>
                  <el-button title="删除" size="mini" type="danger" icon="el-icon-delete" @click.native.stop="del(scope.row.id)"/>
                </el-button-group>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination align="center"
                         v-if="diaryByGroupList.length !== 0"
                         v-model="diaryByGroupList"
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         :page-sizes="[5, 10, 20, 50]"
                         :page-size="pagesize"
                         :current-page="currpage"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="diaryByGroupList.length" />
        </div>

          <el-empty v-else description="这里空空的，啥都没有哦！" />

      </el-card>

      <diaty-info ref="info"/>
    </el-main>
  </el-container>
</template>

<script>
import myQuillEditor from "@/components/MyQuillEditor.vue"
import diatyInfo from '@/components/DiaryInfo.vue'

export default {
  name: 'HomeView',
  components:{
    diatyInfo,
    myQuillEditor,
  },
  data() {
    return {
      diarydata: [],
      searchText:"",
      type: "全部",
      type2: 1,
      pagesize: 10,
      currpage: 1,
      drawer: false,
      isCollapse: true,
      isControl:false,
    }
  },
  computed: {
    diaryByGroup: function () {
      let result = []
      let type = {}

      result.push({type: "全部", list: this.diarydata})

      this.diarydata.forEach((item) => {
        if (!type[item.type]) {
          result.push({type: item.type, list: [item]})
          type[item.type] = item;
        } else {
          result.forEach((i) => {
            if (i.type === item.type) {
              i.list.push(item)
            }
          })
        }
      })
      return result;
    },
    diaryByGroup2: function () {
      let result = []
      let type = {}

      result.push({type: "全部", list: this.diarydata})

      this.diarydata.forEach((item) => {
        let date = item.createTime.match(/\d{4}-\d{1,2}/g)[0]
        if (!type[date]) {
          result.push({type: date, list: [item]})
          type[date] = item;
        } else {
          result.forEach((i) => {
            if (i.type === date) {
              i.list.push(item)
            }
          })
        }
      })
      result.sort((a, b) => new Date(b.type) - new Date(a.type))
      return result;
    },
    diaryByGroupList: function () {
      if (!this.diaryByGroup) {
        return []
      }
      if (this.type2 === 1) {
        return this.diaryByGroup.find(item => item.type === this.type).list
      } else if (this.type2 === 2) {
        return this.diaryByGroup2.find(item => item.type === this.type).list
      } else if (this.type2 === 3) {
        if(!this.searchText){
          return this.diarydata
        }
        return this.diarydata.filter((e) => e.title.toLowerCase().indexOf(this.searchText.toLowerCase()) === 0)
      }

    }
  },
  methods: {
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },

    changeTabel(type,type2){
      this.type = type
      this.type2 = type2
      this.currpage = 1
    },
    handleSizeChange(val) {
      this.pagesize = val
    },
    handleCurrentChange(val) {
      this.currpage = val
    },
    handleDiaryChange(item) {
      this.$refs.info.init(item.id)
    },
    changeType(item) {
      this.changeTabel(item.type,1)
    },
    changeType2(item) {
      this.changeTabel(item.type,2)
    },
    //日记搜索
    searchDiary(value) {
      if (!this.searchText){
        return
      }
      this.type2 = 3
    },
    //日记搜索词提示，依据标题
    querySearch(queryString, cb) {
      var restaurants = this.diarydata;
      var results = queryString ? restaurants.filter((e) => e.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    //编辑
    edit(id) {
      this.$router.push({name:'EditorView',params:{id:id}})
    },
    //删除
    del(id) {
      this.$confirm('此操作将永久删除该日记, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.get('/diary/delete', {params: {'id': id}}).then((res) => {
          if (res.code === 200) {
            this.$notify.success("删除成功！")
            let index = this.diarydata.findIndex((item) => item.id === id);
            this.diarydata.splice(index, 1)
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除！'
        });
      })
    },
    filterTime(row, column, cellValue){
      return cellValue.split(' ')[0];
    }

  },
  mounted() {
    this.$axios.get("/diary/diaryList").then((res) => {
      if (res.code === 200) {
        this.diarydata = res.data
      } else {
        this.$message.error(res.msg)
      }
    })
  }
}
</script>

<style scoped>

.item {
  background-color: #f1f0ed;
}

/*去除边框线*/
.box-card >>> .el-table__row > td {
  border: none;
  background-color: #f1f0ed;
}

.box-card >>> .el-table::before {
  height: 0;
}

.box-card >>> .el-table__body-wrapper::-webkit-scrollbar {
  width: 0;
}

.box-card >>> .el-card__body {
  padding: 0px 10px;
}

.item >>> .el-card__body {
  padding: 0 20px 10px;
}

.clearfix {
  height:5px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  max-width: 280px;
  margin: 20px auto 0px;
  background-color: #f1f0ed;
}


.el-aside {
  /*height: 70vh;*/
  width: 180px;
  min-width: 150px;
  max-width: 225px;
  user-select: none;
  display: flex;
  /*align-items: center;*/
  flex-direction: column;
  /*text-align: center;*/
  /*line-height: 200px; */
}

.el-aside::-webkit-scrollbar {
  display: none;
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

.el-pagination {
  margin-top: 10px;
  margin-left: 20px;
}
</style>
