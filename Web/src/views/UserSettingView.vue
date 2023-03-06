<!--
*@UserSettingView
*@author MoYuXi
*@date 2023/3/6 15:04:44
-->
<template>
  <el-container>
    <el-aside width="350px">
      <el-card class="mySiceCard">
        <!--    {{userinfo.icon}}-->
        <el-avatar :size="250" :src="baseUrl+'/'+userinfo.icon"></el-avatar>
        <p>用户昵称：{{ userinfo.name }}</p>
        <!--      <p>{{ baseUrl + userinfo.icon }}</p>-->
        <p>用户账号：{{ userinfo.account }}</p>
      </el-card>
    </el-aside>
    <el-main>
      <el-card class="myCard">
        <el-upload
            :before-upload="beforeAvatarUpload"
            :http-request="uploadimage"
            :show-file-list="false"
            action=""
            name="file"
        >
          <el-button size="small" type="primary">更换头像</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5MB</div>
        </el-upload>
        <!--        <el-form :mode="form">-->
        <!--          <el-form-item label="用户名">-->
        <!--            <el-input v-model="form.username"/>-->
        <!--          </el-form-item>-->
        <!--          <el-form-item label="活动名称">-->
        <!--            <el-input v-model="form.name"></el-input>-->
        <!--          </el-form-item>-->
        <!--        </el-form>-->

      </el-card>
    </el-main>
  </el-container>

</template>

<script>
import tools from "@/utils/utils";

export default {
  name: "UserSettingView",
  data() {
    return {
      form: {},
      userinfo: {},
      baseUrl: "",
      iconurl: this.$axios.defaults.baseURL + 'file/uploadIcon',
      iconfile: [],
      imageUrl: '',

    }
  },
  computed: {},
  methods: {
    uploadimage(item) {
      // console.log(this.iconurl)
      var data = new FormData()
      data.append('file', item.file)
      this.$axios.post("file/uploadIcon", data,
          {headers: {'content-type': 'application/x-www-form-urlencoded'}})
          .then((res) => {
            if (res.code === 200) {
              this.$notify.success("更新成功！")
              this.imageUrl = res.data;
              this.userinfo.icon = this.imageUrl;
              localStorage.setItem("userInfo", JSON.stringify(this.userinfo))
            } else {
              this.$message.error(res.msg)
            }
          })
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 5;

      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG、PNG格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 5MB!');
      }
      return isJPG && isLt2M;
    },

  },
  created() {
    this.userinfo = tools.getUserInfo();
    this.baseUrl = this.$axios.defaults.baseURL;
  }
}
</script>

<style scoped>
.myCard {
  width: 500px;
  height: 300px;
  background-color: #f1f0ed;
}

.mySiceCard {
  width: 300px;
  margin: auto;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  background-color: #f1f0ed;
}
</style>