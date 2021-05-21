<template>
  <div id="login">
    <div class="login_img">
      <div class="login_tit">
        <div></div>
        <div>医疗管理后台</div>
        <div></div>
      </div>
    </div>
    <div class="login_con">
      <div class="login_form">
        <h3>账号注册</h3>
        <el-form :label-position="labelPosition" :model="form">
          <el-form-item>
            <!--选择用户身份的下拉框 --->
            <el-dropdown @command="changeRole">
              <!--下拉框的主体 --->
              <span class="el-dropdown-link">
                {{role}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <!--下拉框的每个条目 --->
                <el-dropdown-item  
                  v-for="item in RoleList"  
                  :command="item"
                >
                  {{item}}
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-form-item>
          <el-form-item>
            <el-input
              class="phone"
              prefix-icon="el-icon-user-solid"
              v-model="adduser.id"
              placeholder="请输入用户名"
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              prefix-icon="el-icon-s-goods"
              :type="passw"
              v-model="adduser.password"
              placeholder="请输入密码"
            >
              <i slot="suffix" :class="icon" @click="showpass"></i>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              prefix-icon="el-icon-s-goods"
              :type="passw"
              v-model="adduser.repassword"
              placeholder="请确认密码"
            >
              <i slot="suffix" :class="icon" @click="showpass"></i>
            </el-input>
          <!--错误信息 --->
          </el-form-item>
          <h5 v-show="showerror">{{errortext}}</h5>
          <el-form-item>
            <el-button class="login" round type="primary" @click="enroll"
              >注册</el-button>
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
        role:"请选择用户身份",
        RoleList:["普通用户","医生"],
        adduser:{
          id:null,
          password:null,
          repassword:null
        },
        passw:"password",
        icon:"el-icon-view",
        showerror:false,
        errortext:"",
      }
    },
    methods:{
      changeRole(data) {
        if(typeof data == "object"){
          return;
        }
        this.role = data;
      },
      showpass(){
        if(this.passw=="text"){
          this.passw="password";
          this.icon="el-icon-view";
        }else{
          this.passw="text";
          this.icon="el-icon-loading"
        };
      },
      enroll() {
        if(this.adduser.id=="user1"){
          this.showerror=true;
          this.errortext="该用户名已存在！";
        }else if(this.adduser.password!=this.adduser.repassword){
          this.showerror=true;
          this.errortext="密码错误！";
        }else if(this.role!="医生" && this.role!="普通用户"){
          this.showerror=true;
          this.errortext="请选择身份！";
        }else{
          this.showerror=false;
          this.$router.replace({
            name: "login",
          });
          setTimeout(() => {
            this.$router.push({
              name: "doctor",
            });
          }, 1500);
        }
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
  .login_img {
    height: 100%;
    width: 60%;
    position: absolute;
    background: url(../assets/img/login2.png) right center;

    left: 0px;
    .login_tit {
      display: flex;
      margin-left: 200px;
      font-size: 30px;
      color: #fff;
      div:nth-child(1) {
        width: 0;
        height: 0;
        border-bottom: 50px solid #16dcb8;
        border-left: 30px solid transparent;
      }
      div:nth-child(2) {
        background: #16dcb8;
        width: 230px;
        text-align: center;
        line-height: 50px;
      }
      div:nth-child(3) {
        width: 0;
        height: 0;
        border-top: 50px solid #16dcb8;
        border-right: 30px solid transparent;
      }
    }
  }
  .login_con {
    width: 94%;
    height: 80%;
    background: #f2f3f7;
    overflow: hidden;

    .login_form {
      width: 300px;
      float: right;
      margin-right: 200px;
      margin-top: 100px;

      h3 {
        width: 120px;
        font-size: 22px;
        color: #30c0e0;
        border-bottom: 2px solid #30c0e0;
        padding-bottom: 20px;
        margin-bottom: 30px;
        font-weight: normal;
      }
      h4 {
        font-size: 15px;
        color: #30c0e0;
        font-weight: normal;
        padding-left: 10px;
        margin-top: -7px;
      }

      .el-input__inner {
        border-radius: 20px;
      }
      .el-button.login {
        width: 50%;
        background: #30c0e0;
        margin-top: 25px;
        border: none;
      }
      .phone {
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
