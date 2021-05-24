<template>
    <!-- 电子病例分页列表页 -->
    <div id="cottomsList">
        <el-row  :gutter="10"  class="el-row" type="flex" :span="20">
            <el-col :span="6">
              <el-card :body-style="{ padding: '0px' }">
                <img src="../../assets/img/testdoctor.jpg" class="image">
              </el-card>
            </el-col>
            <el-col :span="18">
                <el-row>
                    <span>{{doctor.name}}</span>
                </el-row>
                <el-row>
                    <span>{{doctor.hospital}}</span>
                </el-row>
                <el-row>
                    <span>{{doctor.office}}</span>
                </el-row>
                <el-row>
                    <span>{{doctor.intro}}</span>
                </el-row>
            </el-col>
          </el-row>
          <el-row>
            <el-button
            type="danger"
            icon="el-icon-service">预约
            </el-button>
          </el-row>
    </div>
  </template>
  <script>
  export default {
    data() {
      return {
        doctor: ""
      };
    },
    mounted() {
      // this.getCottomsList();
      this.doctor = JSON.parse(this.$route.query.doctor);
    },
    //进入页面请求电子病例列表数据
  
    methods: {
      
    },
  };
  </script>
  <style lang="scss">
  #cottomsList {
    position: relative;
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
  .el-row {
  margin-bottom: 20px;
  display: flex;
  flex-wrap: wrap
 }
 .el-col {
  border-radius: 4px;
  align-items: stretch;
  margin-bottom: 40px;
 }
    .page {
      position: absolute;
      left: 50%;
      transform: translateX(-50%);
      bottom: 20px;
    }
    .beatch_remove {
      position: absolute;
      bottom: 20px;
      left: 50px;
    }
  }
  </style>
  