<template>
    <div id="doctorInfo">
      <el-container>
        <el-header class="header">
          <h1>我的信息</h1>
          <p v-if="!revamp" style="color:red;" @click="revamp = true">
            <i class="el-icon-edit"></i>
            修改信息
          </p>
        </el-header>
        <el-divider></el-divider>
        <el-main>
          <el-row>
            <el-col :span="7">
              <el-form
                :model="detailForm"
                ref="ruleForm"
                label-width="100px"
                class="demo-ruleForm"
              >
                <el-form-item label="用户名称" prop="name">
                  <el-input v-model="prevForm.name" v-if="revamp"></el-input>
                  <span v-else>{{detailForm.name}}</span>
                </el-form-item>
                <el-row class="formInline">
                  <el-col :span="11">
                    <el-form-item label="性别" prop="sex">
                      <el-select v-model="prevForm.sex" placeholder="性别"  v-if="revamp">
                        <el-option label="男" value="男"></el-option>
                        <el-option label="女" value="女"></el-option>
                      </el-select>
                      <span v-else>{{detailForm.sex}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
  
                <el-form-item label="出生日期">
                  <el-col :span="10" v-if="revamp">
                    <el-date-picker
                      type="year"
                      placeholder="选择年份"
                      style="width: 100%;"
                      v-model="prevForm.year"
                      value-format="yyyy"
                    ></el-date-picker>
                  </el-col>
                  <el-col :span="10" v-else>
                    {{detailForm.year}}
                  </el-col>
                  <el-col :span="2" style="text-align:center;"
                    >&nbsp;&nbsp;年</el-col
                  >
                  <el-col :span="10" v-if="revamp">
                    <el-date-picker
                      placeholder="选择月份"
                      style="width: 100%;"
                      type="month"
                      v-model="prevForm.month"
                      value-format="mm"
                    ></el-date-picker>
                  </el-col>
                  <el-col :span="10" v-else>
                    {{detailForm.month}}
                  </el-col>
                  <el-col :span="2" style="text-align:center;">&nbsp;月</el-col>
                </el-form-item>

                <el-form-item label="联系电话" prop="name">
                  <el-input v-model="prevForm.tel" v-if="revamp"></el-input>
                  <span v-else>{{detailForm.tel}}</span>
                </el-form-item>
                <el-form-item label="地址" prop="name">
                  <el-input v-model="prevForm.addr" v-if="revamp"></el-input>
                  <span v-else>{{detailForm.addr}}</span>
                </el-form-item>
              </el-form>
            </el-col>
            <el-col :span="1" class="divider">
              <el-divider direction="vertical"></el-divider>
            </el-col>
  
            
           
          </el-row>
        </el-main>
        <div class="footer" v-if="revamp">
          <p class="bg16d" plain @click="save">保存</p>
          <p>取消</p>
        </div>
      </el-container>
    </div>
  </template>
  <script>
  export default {
    data() {
      return {
        detailForm: {
          name: "",
          sex: "",
          year: "",
          month: "",
          tel: "",
          addr: "",
        },
        prevForm: {
          name: "",
          sex: "",
          year: "",
          month: "",
          tel: "",
          addr: "",
        },
        revamp: false,
        //   是否修改数据
      };
    },
    methods: {
      save(){
        this.revamp = false;
        this.detailForm = this.prevForm;
      },
    },
  };
  </script>
  <style lang="scss" scoped>
  #doctorInfo {
    .header {
      h1 {
        font-weight: normal;
      }
      display: flex;
      justify-content: space-between;
    }
    .el-main {
      padding-top: 0;
    }
    .el-divider--horizontal {
      margin: 0;
    }
    .el-col-8 {
      display: flex;
      flex-direction: column;
      // height: 70%;
    }
    .divider {
      height: 420px;
      padding-top: 40px;
      box-sizing: border-box;
      display: flex;
      justify-content: center;
      .el-divider {
        height: 100%;
      }
    }
    .footer {
      display: flex;
      height: 40px;
      justify-content: center;
      p {
        width: 100px;
        height: 35px;
        text-align: center;
        line-height: 35px;
        color: #fff;
      }
      p:nth-child(2) {
        background: #ccc;
        margin-left: 20px;
      }
    }
  }
  </style>
  <style lang="scss">
  #doctorInfo {
    .el-form-item__content {
      margin-left: 0px !important;
    }
    .el-form-item__label {
      width: 100% !important;
      text-align: left !important;
    }
    .el-form-item {
      margin-bottom: 10px;
    }
  }
  </style>
  