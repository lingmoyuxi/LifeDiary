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
        <el-avatar :size="150" :src="baseUrl+'/'+userinfo.icon"></el-avatar>
        <el-upload
            :before-upload="beforeAvatarUpload"
            :http-request="uploadimage"
            :show-file-list="false"
            action=""
            name="file">
          <el-button size="small">更换头像</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过5MB</div>
        </el-upload>
        <p>用户昵称：{{ userinfo.name }}</p>
        <p>用户账号：{{ userinfo.account }}</p>
      </el-card>
    </el-aside>
    <el-main>
      <el-card class="myCard">
        <p>信息修改</p>
        <el-divider/>
        <el-form ref="form" :hide-required-asterisk="true" :model="form" :rules="formRules" size="mini">
          <el-form-item label="用户昵称">
            <el-input v-model="form.name" placeholder="请输入新昵称"/>
          </el-form-item>
          <p>修改密码(不修改请留空)</p>
          <el-divider/>
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input v-model="form.oldPassword" class="inps" maxlength="18" placeholder="请输入原密码"
                      type="password"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="form.newPassword" class="inps" maxlength="18" placeholder="请输入新密码"
                      type="password"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="newpasswordConfirm">
            <el-input v-model="form.newpasswordConfirm" class="inps" maxlength="18" placeholder="请确认新密码"
                      type="password"></el-input>
          </el-form-item>
          <el-form-item align="center">
            <el-button @click="submitForm('form')">提交更改</el-button>
          </el-form-item>
        </el-form>

      </el-card>
    </el-main>
  </el-container>

</template>

<script>
import tools from "@/utils/utils";

export default {
  name: "UserSettingView",
  inject: ['reload'],
  data() {
    let validatePass = (rule, value, callback) => {
      if (value == '' && this.form.newPassword != "") {
        callback(new Error('请输入原密码!'))
      } else {
        callback()
      }
    }
    let validatePass1 = (rule, value, callback) => {
      if (this.form.oldPassword != "" && value == "") {
        callback(new Error('请输入新密码!'))
      } else {
        callback()
      }
    }
    let validatePass2 = (rule, value, callback) => {
      if (value !== this.form.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      form: {name: '', oldPassword: '', newPassword: '', newpasswordConfirm: '',},
      userinfo: {},
      baseUrl: "",
      iconurl: this.$axios.defaults.baseURL + 'file/uploadIcon',
      iconfile: [],
      imageUrl: '',
      formRules: {
        oldPassword: [{required: true, validator: validatePass, trigger: 'blur'}],
        newPassword: [{required: true, validator: validatePass1, trigger: 'blur'}],
        newpasswordConfirm: [{required: true, validator: validatePass2, trigger: 'blur'}],
      }
    }
  },
  computed: {},
  methods: {
    uploadimage(item) {
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
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('user/update', this.form).then((res) => {
            if (res.code === 200) {
              this.userinfo.name = this.form.name.toString()
              localStorage.setItem("userInfo", JSON.stringify(this.userinfo))
              this.$notify.info("修改成功")
              // this.form = {}
              // this.form.name = this.userinfo.name.toString()
              this.reload()
            } else {
              this.$message({type: "error", message: res.msg})
            }
          })
        } else {
          return false;
        }
      });
    }
  },
  created() {
    this.userinfo = tools.getUserInfo();
    this.form.name = this.userinfo.name;
    this.baseUrl = this.$axios.defaults.baseURL;
  }
}
</script>

<style scoped>
.myCard {
  width: 500px;
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