<template>
  <div class="mycontent">
    <div :class="['mylogin', isLogin == -1 ? '' : (!isLogin ? 'divlogin' : 'divlogin-activate')]">
      <h2 v-if="isLogin">登录</h2>
      <h2 v-else>注册</h2>
      <el-form :model="loginForm" :rules="loginRules">
        <el-form-item prop="account">
          <el-row>
            <el-col :span="2">
              <span class="el-icon-s-custom" />
            </el-col>
            <el-col :span="22">
              <el-input class="inps" placeholder="账号(仅数字)" maxlength="9" v-model="loginForm.account"
                oninput="value = value.replace(/[^0-9]/g,'')" />
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item prop="passWord">
          <el-row>
            <el-col :span="2">
              <span class="el-icon-lock" />
            </el-col>
            <el-col :span="22">
              <el-input class="inps" type="password" placeholder="密码" maxlength="18" v-model="loginForm.passWord" @keyup.enter.native="submitForm" />
            </el-col>
          </el-row>
        </el-form-item>
        <div
          style="position: absolute;top:162px;width: 240px;z-index: -1;animation-duration: 1s;animation-fill-mode: forwards"
          :class="isLogin == -1 ? '' : (!isLogin ? 'psw-confirm' : 'psw-confirm-activate')">

          <el-form-item prop="passWordConfirm" id="pasConfirm">
            <el-row>
              <el-col :span="2">
                <span class="el-icon-lock" />
              </el-col>
              <el-col :span="22">
                <el-input class="inps" type="password" placeholder="确认密码" maxlength="18" v-if="isLogin!==-1" :disabled="isLogin === 1?true:false"
                  v-model="loginForm.passWordConfirm" />
              </el-col>
            </el-row>
          </el-form-item>
        </div>
          <el-form-item style="margin-top: 30px;position: absolute;top:200px;left:0px;right: 0px;animation-duration: 1s;animation-fill-mode: forwards"
                        :class="isLogin == -1 ? '' : (!isLogin ? 'divlogin-confirm' : 'divlogin-confirm-activate')">
            <el-button type="primary" round class="submitBtn" @click="submitForm">{{
                isLogin? '登录': '注册并登录'
              }}
            </el-button>
            <el-form-item >
              <a style="text-align:right;margin-right: 10px;" href="javascript:;"
                 v-on:click="$message({ message: '未完工' })()">忘记密码?</a>
              |
              <a style="text-align:left;margin-left: 10px;" href="javascript:;"
                 v-on:click="isLogin = isLogin == -1 ? 0 : (isLogin == 1 ? 0 : 1)">{{
                  isLogin? '注册新账号': '去登录'
                }}</a>
              <!-- <a href="javascript:;" v-else>去登陆</a> -->
            </el-form-item>
          </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.mycontent {
  width: 100vw;
  height: 100vh;
  background-color: #242645;
  color: #fff;
  text-align: center;
}

.mylogin {
  width: 240px;
  height: 280px;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
  padding: 30px 40px 40px 40px;
  box-shadow: -15px 15px 10px rgba(6, 17, 47, 0.85);
  opacity: 1;
  background: linear-gradient(225deg,
      rgba(255, 255, 255, 0) 15%,
      rgb(0, 0, 0) 100%);
}

.divlogin {
  animation-name: divloginShort;
  animation-duration: 1s;
  animation-fill-mode: forwards
}

.divlogin-activate {
  animation-name: divloginLong;
  animation-duration: 1s;
  animation-fill-mode: forwards
}

@keyframes divloginShort {
  0% {
    height: 280px;
  }

  100% {
    height: 348px;
  }
}

@keyframes divloginLong {
  0% {
    height: 348px;
  }

  100% {
    height: 280px;
  }
}

.psw-confirm {
  animation-name: loginHide;
}

.psw-confirm-activate {
  animation-name: loginShow;
}

@keyframes loginHide {
  0% {
    top: 162px;
    opacity: 0;
    z-index: -1;
  }

  100% {
    top: 225px;
    opacity: 1;
    z-index: 0;
  }
}

@keyframes loginShow {
  0% {
    top: 225px;
    opacity: 1;
    z-index: 0;
  }

  100% {
    top: 162px;
    opacity: 0;
    z-index: -1;
  }
}

.divlogin-confirm {
  animation-name: divloginUp;
}

.divlogin-confirm-activate {
  animation-name: divloginDown;
}

@keyframes divloginUp {
  0% {
    top: 200px;
  }

  100% {
    top: 268px;
  }
}

@keyframes divloginDown {
  0% {
    top: 268px;
  }

  100% {
    top: 200px;
  }
}

.el-row {
  border-radius: 25px;
  border: 2px solid white;
}

.el-col > span {
  padding-left: 10px;
}

.el-col >>> .inps input {
  border: none;
  color: #fff;
  background-color: transparent;
  font-size: 15px;
}

input[disabled]{
  background-color: transparent !important;
}
.submitBtn {
  color: #fff !important;
  width: 200px;
}

.unlogin {
  display: flex;
}

.unlogin>a {
  flex: 1;
}


/*取消账号框type为number时出现的上下箭头*/
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}

input[type="number"] {
  -moz-appearance: textfield;
}
</style>

<script>
import {mapMutations} from "vuex";
import tools from '@/utils/utils';

export default {
  name: "LoginView",
  data: function () {
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.loginForm.passWord) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        account: "",
        passWord: "",
        passWordConfirm: "",
      },
      loginRules: {
        account: [{ required: true, message: "请输入账号", trigger: "blur" }],
        passWord: [{ required: true, message: "请输入密码", trigger: "blur" }],
        passWordConfirm: [{ required: true, validator: validatePass2, trigger: 'blur' }],
      },
      isLogin: -1,
      isLoading: null
    };
  },
  methods: {
    ...mapMutations(["changeLogin"]),
    dataCheck() {
      const userAccount = this.loginForm.account;
      const userPassword = this.loginForm.passWord;
      const userPasswordConfirm = this.loginForm.passWordConfirm;
      if (!userAccount) {
        this.$message({ type: "error", message: "账号不能为空！" });
        return false;
      }
      if (!userPassword) {
        this.$message({ type: "error", message: "密码不能为空！" });
        return false;
      }
      if (!this.isLogin && !userPasswordConfirm) {
        this.$message({ type: "error", message: "两次输入的密码不正确！" });
        return false;
      }
      return true;
    },
    loginUserSetting(res) {
      this.hideloading()
      if (res.code === 200) {
        let userInfo = res.data.userInfo
        tools.login(userInfo)
        this.$notify.info({ type: "success", message: "用户 "+userInfo.name+" 登录" })
        this.$router.replace({ path: '/' })
      } else {
        localStorage.removeItem("userInfo")
        localStorage.removeItem("token")
        this.$message({ type: "error", message: res.msg })
      }
    },
    loginUserUnSetting() {
      this.hideloading()
      localStorage.removeItem("userInfo")
      localStorage.removeItem("token")
    },
    submitForm() {
      if (!this.dataCheck()) return;
      this.isLoading = this.showloading();
      if (this.isLogin) {
        this.$axios.get("/user/login", { params: { account: this.loginForm.account, password: this.loginForm.passWord } })
          .then(res => this.loginUserSetting(res), this.loginUserUnSetting())
      } else {
        this.$axios.get("/user/registry", {
          params: {
            account: this.loginForm.account,
            password: this.loginForm.passWord
          }
        })
          .then(res => this.loginUserSetting(res), this.loginUserUnSetting())
      }
    },
    showloading() {
      let loading = this.$loading({
        lock: true,//lock的修改符--默认是false
        text: 'Loading',//显示在加载图标下方的加载文案
        spinner: 'el-icon-loading',//自定义加载图标类名
        background: 'rgba(0, 0, 0, 0.7)',//遮罩层颜色
        target: document.querySelector('#table')//loadin覆盖的dom元素节点
      })
      return () => {
        return { close: loading.close() }
      }
    },
    hideloading() {
      if(!this.isLoading())this.isLoading().close()
    }
  },
};
</script>