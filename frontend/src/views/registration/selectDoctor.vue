<template>
    <!-- 电子病例分页列表页 -->
    <div id="cottomsList">
      <el-form :inline="true" :model="hospital">
        <el-form-item>
          <el-input
            v-model="name"
            placeholder="请输入医院名称"
          ></el-input>
        </el-form-item>
  
        <el-form-item>
            <el-input
              v-model="office"
              placeholder="请输入科室"
            ></el-input>
          </el-form-item>
  
        <el-form-item>
          <el-button icon="el-icon-search" circle @click="search"></el-button>
        </el-form-item>
      </el-form>
      <!-- 筛选列表内容 -->
  
      <el-row  :gutter="10"  class="el-row" type="flex">
        <el-col :span="4" v-for="item in tables">
          <el-card :body-style="{ padding: '0px' }">
            <img src="../../assets/img/testdoctor.jpg" class="image">
            <div style="padding: 14px;">
              <span>{{item.name}}</span>
              <div class="bottom clearfix">
                <el-button type="text" class="button" @click="goDetail(item)">查看详情</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <el-pagination
        class="page"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        background
        :page-size="10"
        layout="prev, pager, next, jumper"
        :total="100"
      >
      </el-pagination>
      <!-- 分页效果 -->
    </div>
  </template>
  <script>
  export default {
    data() {
      return {
        hospital: {
          name: "",
          office: "",
        },
        //定义查询的表单元素
        name: "",
        office: "",

        doctors: [{
                name: "doctor1",
                office: "office1",
                hospital: "hospital1",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor2",
                office: "office2",
                hospital: "hospital1",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor3",
                office: "office3",
                hospital: "hospital1",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor4",
                office: "office1",
                hospital: "hospital2",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor5",
                office: "office2",
                hospital: "hospital2",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor6",
                office: "office3",
                hospital: "hospital2",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor1",
                office: "office1",
                hospital: "hospital1",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor2",
                office: "office2",
                hospital: "hospital1",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor3",
                office: "office3",
                hospital: "hospital1",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor4",
                office: "office1",
                hospital: "hospital2",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor5",
                office: "office2",
                hospital: "hospital2",
                intro: "I'm a doctor!",
            },
            {
                name: "doctor6",
                office: "office3",
                hospital: "hospital2",
                intro: "I'm a doctor!",
            },
        ],

        currentPage: 1,
        //当前点击页码数
        pageSize: 10,
        //每页展示数目
      };
    },
    mounted() {
      // this.getCottomsList();
      //this.selectedDoctors = this.doctors;
    },
    //进入页面请求电子病例列表数据
  
    methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        // this.getCottomsList();
      }, //点击分页时改变当前页码，重新请求数据
      //在这里val就是分页组件中用户切换的页码值，赋值给currentPage，再次请求数据的时候 请求的就是用户切换的页码值的数据了
      search() {
        this.hospital.name = this.name;
        this.hospital.office = this.office;
      },
      goDetail(_doctor) {
        this.$router.push({
            path: "doctordetail",
            query: {
                doctor: JSON.stringify(_doctor)
            }
        });
      },
    },
    computed: {
      tables() {
        return this.doctors.filter((item) => {
          return (
            item.hospital.includes(this.hospital.name) &&
            item.office.includes(this.hospital.office)
          );
        });
      },
      // 根据查询条件过滤病例列表数据
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
  