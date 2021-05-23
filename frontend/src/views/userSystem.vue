<template>
  <div id="doctor">
    <div id="doc_main">
      <div id="doc_bot">
        <el-row>
          <el-col :span="4" class="exit">
            <el-dropdown @command="exitLogin">
              <!--下拉框的主体 --->
              <span class="el-dropdown-link">
                {{username}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <!--下拉框的每个条目 --->
                <el-dropdown-item>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
          <el-col :span="4" :offset="16" class="setup">
            <i class="el-icon-moon"></i>
            <i class="el-icon-message-solid"></i>
            <i class="el-icon-s-tools"></i>
          </el-col>
        </el-row>
      </div>
      <el-row>
        <el-col :span="4" class="menu">
          <!-- <div class="menu"> -->
          <el-row class="tac">
            <!-- <h5 class="menu_tit">YOUR LOGO</h5> -->
            <el-col :span="18" :offset="6">
              <el-menu
                class="el-menu-vertical-demo"
                background-color="rgba(0,0,0,0)"
                text-color="#797979"
                active-text-color="#ffd04b"
                :router="true"
                :default-active="defaultActive"
              >
                <!-- 使用 vue-router 的模式，启用该模式会在激活导航时以 index 作为 path 进行路由跳转 -->
                <!-- 当前激活的菜单选项是当前的路径，保证刷新的时候激活样式不掉 -->
                <el-menu-item index="/user/settings">
                  <i class="el-icon-user-solid"></i>
                  <span slot="title">个人中心</span>
                </el-menu-item>
              </el-menu>
            </el-col>
            
          </el-row>
          <!-- </div> -->
        </el-col>
        <!-- 定义医生功能模块左侧菜单部分 -->

        <el-col :span="20" class="content">
          <!-- <div> -->
          <router-view></router-view>
          <!-- </div> -->
        </el-col>
        <!-- 右侧展示内容，根据菜单的点击进行路由跳转 -->
      </el-row>
    </div>
    <!-- 定义页面尾部的退出登录部分 -->
  </div>
</template>
<script>
export default {
  data() {
    return {
      username: "user1",
    };
  },
  methods: {
    exitLogin() {
      // this.$http("/shiro/logout", "post").then((res) => {
      //   //  console.log(res)
      //   if (res.msg == "ok") {
      //     this.$store.commit("setLogin", false);
      //     //把本地存储里的login变为false
      //     this.$router.push({ path: "/login" });
      //     //  退出登录成功后跳转到登录页面
      //   }
      // });
      this.$router.replace({path: "/login"});
    },
    //  退出登录方法
  },
  computed: {
    defaultActive() {
      console.log(this.$route.path.split("/"));
      let pathArr = this.$route.path.split("/");
      let activePath = "/" + pathArr[1] + "/" + pathArr[2];
      return activePath;
    },

    //控制element-ui navmenu组件的default-active属性，因为这里有子路由，所以如果只是简单的定义 :default-active="$route.path",那么在子路由状态下如果刷新的话就会丢失激活的状态，我们使用一个计算属性 保证在子路由状态下刷新也有激活状态
  },
};
</script>
<style lang="scss" scoped>
#doctor {
  width: 100%;
  height: 100%;
  min-height: 800px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  #doc_main {
    width: 100%;
    //  height: 90%;
    background: url(../assets/img/bg.jpg) center center;
    flex: 1;
  }

  .nav {
    height: 5%
  }
  .menu {
    height: 100%;
    background: rgba($color: #fdfdfe, $alpha: 0.7);
    position: relative;

    .menu_tit {
      height: 120px;
      font-size: 26px;
      color: #88e8dd;
      display: flex;
      justify-content: center;
      align-items: center;
      border-bottom: 2px solid #88e8dd;
    }
    .user {
      display: flex;
      width: 100%;
      height: 120px;
      border-top: 2px solid #797979;
      justify-content: center;
      align-items: center;
      position: absolute;
      bottom: 0px;
      em {
        margin-left: 14px;
      }
    }
  }
  .content {
    padding: 35px 50px;
    box-sizing: border-box;
    height: 100%;
    > div {
      height: 100%;
    }
  }
  #doc_bot {
    height: 60px;
    width: 100%;
    .exit {
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 10px;
      em {
        margin-left: 17px;
      }
    }
    .setup {
      display: flex;
      justify-content: space-evenly;
      margin-top: 10px;
    }
  }
}
</style>
<style lang="scss">
.el-menu-item.is-active {
  color: #16dcb8 !important;
}
</style>
