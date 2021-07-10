<template>
  <common-layout>
    <div class="top">
      <div class="header">
        <!-- <img alt="logo" class="logo" src="@/assets/img/logo.png" /> -->
        <span class="title">线上医疗系统</span>
      </div>
      <!-- <div class="desc">线上医疗系统</div> -->
    </div>
    <div class="login">
      <a-form :form="form">
        <a-tabs size="large" :tabBarStyle="{textAlign: 'center'}" style="padding: 0 2px;">
          <a-tab-pane tab="账户密码登录" key="1">
            <a-alert type="error" :closable="true" v-show="error" :message="error" showIcon style="margin-bottom: 24px;" />
              <form-row label="医院" style="padding-bottom: 11px">
                <a-form-item style="left: 0px;width: 100%;">
                  <a-select
                    v-decorator="[
                      'role_l',
                      { rules: [{ required: true, message: '请选择角色!' }] },
                      ]"
                    placeholder="选择用户角色"
                    @change="handleMenuClickLogin"
                  >
                    <a-select-option value="普通用户">
                      普通用户
                    </a-select-option>
                    <a-select-option value="医生">
                      医生
                    </a-select-option>
                    <!-- <a-select-option value="管理员">
                      管理员
                    </a-select-option> -->
                  </a-select>
                </a-form-item>
              </form-row>
              <a-form-item>
                <a-input
                  autocomplete="autocomplete"
                  size="large"
                  placeholder="请输入手机号"
                  v-decorator="['phone_l', {rules: [{ required: true, message: '请输入手机号！', whitespace: true}]}]"
                >
                  <a-icon slot="prefix" type="user" />
                </a-input>
              </a-form-item>
              <a-form-item>
                <a-input
                  size="large"
                  placeholder="请输入密码"
                  autocomplete="autocomplete"
                  type="password"
                  v-decorator="['password_l', {rules: [{ required: true, message: '请输入密码！', whitespace: true}]}]"
                >
                  <a-icon slot="prefix" type="lock" />
                </a-input>
              </a-form-item>
              <div>
                <a-checkbox :checked="true" >自动登录</a-checkbox>
                <a style="float: right">忘记密码</a>
              </div>
              <a-form-item>
                <a-button :loading="logging" style="width: 100%;margin-top: 24px" size="large" htmlType="submit" type="primary" @click="handleLogin">登录</a-button>
              </a-form-item>
          </a-tab-pane>
          <a-tab-pane tab="注册账户" key="2">
            <a-alert type="error" :closable="true" v-show="error" :message="error" showIcon style="margin-bottom: 24px;" />
            <form-row label="医院" style="padding-bottom: 11px">
                <a-form-item style="left: 0px;width: 100%;">
                  <a-select
                    v-decorator="[
                      'role_r',
                      { rules: [{ required: true, message: '请选择角色!' }] },
                      ]"
                    placeholder="选择用户角色"
                    @change="handleMenuClickRegister"
                  >
                    <a-select-option value="普通用户">
                      普通用户
                    </a-select-option>
                    <a-select-option value="医生">
                      医生
                    </a-select-option>
                    <!-- <a-select-option value="管理员">
                      管理员
                    </a-select-option> -->
                  </a-select>
                </a-form-item>
            </form-row>
            <a-form-item>
              <a-input
                autocomplete="autocomplete"
                size="large"
                placeholder="请输入手机号"
                v-decorator="['phone_r', {rules: [{ required: true, message: '请输入手机号！', whitespace: true}]}]"
              >
                <a-icon slot="prefix" type="user" />
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-input
                size="large"
                placeholder="请输入密码"
                autocomplete="autocomplete"
                type="password"
                v-decorator="['password_r', {rules: [{ required: true, message: '请输入密码！', whitespace: true}]}]"
              >
                <a-icon slot="prefix" type="lock" />
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-button :loading="logging" style="width: 100%;margin-top: 24px" size="large" htmlType="submit" type="primary" @click="handleRegister">注册</a-button>
            </a-form-item>
          </a-tab-pane>
        </a-tabs>
      </a-form>
    </div>
  </common-layout>
</template>

<script>
import CommonLayout from '@/layouts/CommonLayout'
import {login, getRoutesConfig, register, getUserInfo, getDoctorInfo} from '@/services/user'
import {setAuthorization} from '@/utils/request'
import {loadRoutes} from '@/utils/routerUtil'
import {mapMutations} from 'vuex'

const user =  [{
  router: 'root',
  children: [
    {
      router: 'info',
      children: ['userInfo', 'healthInfo'],
    },
    {
      router: 'medicalCheck',
    },
    {
      router: 'doctorManage',
    },
    {
      router: 'talkSystem',
    },
  ]
}]

const doctor = [{
  router: 'root',
  children: [
    {
      router: 'upload',
    },
    {
      router: 'presCheck',
    },
    {
      router: 'talkSystem',
    },
  ]
}]

export default {
  name: 'Login',
  components: {CommonLayout},
  data () {
    return {
      logging: false,
      error: '',
      phone: null,
      role: -1,
      form: this.$form.createForm(this),
    }
  },
  computed: {
    systemName () {
      return this.$store.state.setting.systemName
    }
  },
  methods: {
    ...mapMutations('account', ['setUser', 'setPermissions', 'setRoles', 'setID']),
    
    handleMenuClickRegister(value) {
      this.form.setFieldsValue({
        role_r: value,
      });
    },
    handleRegister (e) {
      e.preventDefault()
      this.form.validateFields(["role_r","phone_r","password_r"], (err) => {
        if (!err) {
          const phone = this.form.getFieldValue('phone_r')
          const password = this.form.getFieldValue('password_r')
          var role = this.form.getFieldValue('role_r')
          if(role=='普通用户'){
            role = "2";
          }else if(role=='医生'){
            role = "3";
          }else{
            role = "1";
          }
          register(phone, password, 'user'+phone, role).then(this.afterRegister)
        }
      })
    },
    afterRegister(res) {
      console.log(res)
    },

    handleMenuClickLogin(value) {
      this.form.setFieldsValue({
        role_l: value,
      });
    },
    handleLogin (e) {
      e.preventDefault()
      this.form.validateFields(["role_l","phone_l","password_l"],(err) => {
        if (!err) {
          this.logging = true
          this.phone = this.form.getFieldValue('phone_l')
          var role = this.form.getFieldValue('role_l')
          if(role=='普通用户'){
            this.role = "2";
          }else if(role=='医生'){
            this.role = "3";
          }else{
            this.role = "1";
          }
          const password = this.form.getFieldValue('password_l')
          login(this.phone, password).then(this.afterLogin)
        }
      })
    },
    afterLogin(res) {
      if(process.env.NODE_ENV === 'production'){
        var data = res.data
        this.logging = false
        if (data.retCode == 200) {
          this.setUser(this.phone)
          this.setRoles(this.role)
          setAuthorization({token: data.result.token, expireAt: new Date(data.result.expire)})
          if(this.role=="2"){
            getUserInfo(this.phone).then( res => {
              this.setID(res.data.result.id)
            })
          }else if(this.role=="3"){
            getDoctorInfo(this.phone).then( res => {
              this.setID(res.data.result.id)
            })
          }
          
          // 获取路由配置
          if(this.role === "2"){
            loadRoutes(user)
            this.$router.push('/info/userinfo')
          }else if(this.role === "3"){
            loadRoutes(doctor)
            this.$router.push('/upload')
          }
          this.$message.success(data.msg, 3)
        } else {
          this.error = data.msg
        }
        console.log(1)
      } else {
        this.logging = false
        const loginRes = res.data
        if (loginRes.code >= 0) {
          const {user, permissions, roles} = loginRes.data
          this.setUser(user)
          this.setPermissions(permissions)
          this.setRoles(roles)
          setAuthorization({token: loginRes.data.token, expireAt: new Date(loginRes.data.expireAt)})
          // 获取路由配置
          getRoutesConfig().then(result => {
            console.log(result)
            const routesConfig = result.data.data
            loadRoutes(routesConfig)
            this.$router.push('/dashboard/workplace')
            this.$message.success(loginRes.message, 3)
         })
        } else {
          this.error = loginRes.message
        }
      }
      
    }
  }
}
</script>

<style lang="less" scoped>
  .common-layout{
    .top {
      text-align: center;
      .header {
        height: 44px;
        line-height: 44px;
        a {
          text-decoration: none;
        }
        .logo {
          height: 44px;
          vertical-align: top;
          margin-right: 16px;
        }
        .title {
          font-size: 33px;
          color: @title-color;
          font-family: 'Myriad Pro', 'Helvetica Neue', Arial, Helvetica, sans-serif;
          font-weight: 600;
          position: relative;
          top: 2px;
        }
      }
      .desc {
        font-size: 14px;
        color: @text-color-second;
        margin-top: 12px;
        margin-bottom: 40px;
      }
    }
    .login{
      width: 368px;
      margin: 0 auto;
      @media screen and (max-width: 576px) {
        width: 95%;
      }
      @media screen and (max-width: 320px) {
        .captcha-button{
          font-size: 14px;
        }
      }
      .icon {
        font-size: 24px;
        color: @text-color-second;
        margin-left: 16px;
        vertical-align: middle;
        cursor: pointer;
        transition: color 0.3s;

        &:hover {
          color: @primary-color;
        }
      }
    }
  }
</style>
