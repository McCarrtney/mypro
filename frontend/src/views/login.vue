<template>
  <div id="login">
    <div class="login_con1">
      <div class="login_form1">
        <div class="login_choice">
        <h3>账号登录</h3>
        <h4>短信登录</h4>
        </div>
        <el-form :label-position="labelPosition" :model="form">
          <el-form-item>
            <el-input
              class="phone1"
              prefix-icon="el-icon-user-solid"
              v-model="adduser.id"
              placeholder="用户名/邮箱/手机号"
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              prefix-icon="el-icon-s-goods"
              :type="passw"
              v-model="adduser.password"
              placeholder="密码"
            >
              <!--密码输入栏的后缀图标，点击可切换密码是否显示 --->
              <i slot="suffix" :class="icon" @click="showpass" style="margin-right: 10px;margin-top: 18px;"></i>
            </el-input>
          <!--显示错误信息 --->
          </el-form-item>
          <h5 v-show="showerror">用户名或密码错误,请重新输入！</h5>
          <el-form-item>
            <el-button class="login" round type="primary" @click="login"
              >登录</el-button>
            <el-link @click="enroll" style="margin-left: 30px;">没有账号？立即注册></el-link>
          </el-form-item>
        </el-form>
        <!-- 使用element-ui定义form表单，在样式上做一些调整 -->
      </div>
    </div>
  </div>
</template>

<script>
  export default({
    data(){
      return {
        adduser:{
          id:null,
          password:null
        },
        passw:"password",
        icon:"el-icon-view",
        showerror:false,
      }
    },
    methods:{
      showpass(){
        if(this.passw=="text"){
          this.passw="password";
          this.icon="el-icon-view";
        }else{
          this.passw="text";
          this.icon="el-icon-loading"
        };
      },
      login() {
        if(this.adduser.id=="user1" && this.adduser.password=="123"){
          this.showerror=false;
          setTimeout(() => {
            this.$router.push({
              name: "user",
            });
          }, 1500);
        }else{
          this.showerror=true;
        }
      },
      enroll() {
        this.showerror=false;
        setTimeout(() => {
            this.$router.replace({
              name: "enroll",
            });
          }, 1500);
      },
    },
  })
</script>

<style lang="scss" scope>
body,
html {
  width: 100%;
  height: 100%;
}
#login {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: left;
  align-items: center;
  min-height: 550px;
  min-width: 1200px;
  .login_con1 {
    background: url(../assets/img/bkimg.jpg) right center no-repeat;
    background-size: 100% 100%;
    width: 100%;
    height: 100%;
    overflow: hidden;

    .login_form1 {
      padding: 70px 70px 70px 70px;
      width: 400px;
      height: 300px;
      background-color: white;
      float: left;
      position: absolute;
      top: 50%;
      margin-top: -200px;
      left: 25%;
      margin-left: -150px;
      border-radius: 10px;

      .login_choice {
        display: flex;
      }

      h3 {
        width: 200px;
        font-size: 22px;
        color: #30c0e0;
        border-bottom: 2px solid #30c0e0;
        padding-bottom: 10px;
        margin-bottom: 30px;
        font-weight: normal;
        text-align:center;
      }
      h4 {
        width: 200px;
        font-size: 22px;
        color: gray;
        border-bottom: 2px solid gray;
        padding-bottom: 10px;
        margin-bottom: 30px;
        font-weight: normal;
        text-align:center;
      }

      .el-input__inner {
        border-radius: 10px;
        height: 50px;
      }
      .el-button.login {
        height: 50px;
        width: 50%;
        background: #30c0e0;
        margin-top: 25px;
        border: none;
        border-radius: 10px;
        font-size: 18px;
      }
      .phone1 {
        width: 100%;
      }
      .getnum {
        width: 90px;
        font-size: 12px;
        background: #30c0e0;
        padding: 12px;
        margin-left: 5px;
      }
    }
  }
}
</style>
