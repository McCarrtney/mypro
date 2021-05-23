<template>
    <!-- 电子病例分页列表页 -->
    <div id="cottomsList">
      <!-- <el-form :inline="true" :model="formInline">
        <el-form-item>
          <el-input
            v-model="formInline.userName"
            placeholder="请输入用户姓名"
          ></el-input>
        </el-form-item>
  
        <el-form-item>
          <el-date-picker
            v-model="formInline.date"
            type="date"
            :value="null ? '' : formInline.date"
            placeholder="选择就诊时间"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
  
        <el-form-item>
          <el-button icon="el-icon-search" circle></el-button>
        </el-form-item>
      </el-form> -->
      <!-- 筛选列表内容 -->
  
      <el-table
        :data="healthList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        ref="multipleTable"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="date" label="日期">
          <template slot-scope="scope">
            <template v-if="scope.row.editing">
              <el-input class="edit-input" v-model="scope.row.date"></el-input>
            </template>
           <span v-else>{{ scope.row.date }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="height" label="身高">
          <template slot-scope="scope">
            <template v-if="scope.row.editing">
              <el-input class="edit-input" v-model="scope.row.height"></el-input>
            </template>
           <span v-else>{{ scope.row.height }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="weight" label="体重">
          <template slot-scope="scope">
            <template v-if="scope.row.editing">
              <el-input class="edit-input" v-model="scope.row.weight"></el-input>
            </template>
           <span v-else>{{ scope.row.weight }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="lung" label="肺活量">
          <template slot-scope="scope">
            <template v-if="scope.row.editing">
              <el-input class="edit-input" v-model="scope.row.lung"></el-input>
            </template>
           <span v-else>{{ scope.row.lung }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="bloodpressure" label="血压">
          <template slot-scope="scope">
            <template v-if="scope.row.editing">
              <el-input class="edit-input" v-model="scope.row.bloodpressure"></el-input>
            </template>
           <span v-else>{{ scope.row.bloodpressure }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="editing" label="操作"  width="300">
          <template slot-scope="scope">
            <el-button
               type="danger"
               v-if="!scope.row.editing"
               icon="el-icon-edit"
               v-model="scope.$index"
               @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <div v-else>
            <el-button
               type="danger"
               icon="el-icon-success"
               v-model="scope.$index"
               @click="handleSave(scope.$index, scope.row)">保存
            </el-button>
            <el-button
               type="danger"
               icon="el-icon-error"
               v-model="scope.$index"
               @click="handleCancle(scope.$index, scope.row)">取消
            </el-button>
            </div>
          </template>
       </el-table-column>
        <!-- 点击查看详情按钮时跳转到电子病历详情页 -->
      </el-table>
      <!-- 使用element-ui里的表格展示请求到的数据 -->
  
      <div class="beatch_remove">
        <el-button round type="danger" icon="el-icon-delete" @click="delete_record">删除</el-button>
        <el-button round type="danger" icon="el-icon-circle-plus" @click="add_record">添加</el-button>
      </div>
  
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
        formInline: {
          userName: "",
          date: "",
        },
        //定义查询的表单元素
  
        healthList: [{
                date: "2021/5/22",
                height: "180",
                weight: "60",
                lung: "5000",
                bloodpressure: "130/80",
            },
            {
                date: "2021/5/21",
                height: "180",
                weight: "60",
                lung: "5000",
                bloodpressure: "130/80",
            },
            {
                date: "2021/5/20",
                height: "180",
                weight: "60",
                lung: "5000",
                bloodpressure: "130/80",
            },
            {
                date: "2021/5/19",
                height: "180",
                weight: "60",
                lung: "5000",
                bloodpressure: "130/80",
            },
            {
                date: "2021/5/18",
                height: "180",
                weight: "60",
                lung: "5000",
                bloodpressure: "130/80",
            },
        ],
        //电子病历列表数据
  
        currentPage: 1,
        //当前点击页码数
        pageSize: 10,
        //每页展示数目
  
        multipleSelection: [],
        // 被选中的列表数据
        prevValue: "",
      };
    },
    mounted() {
      // this.getCottomsList();
      let datalength = healthList.length;
      for(let i=0; i<datalength; i++) {
        this.$set(healthList[i], 'editing', false)
      }
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
  
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      //保存选中的列表数据
      handleEdit(index,row){
          this.$set(row, 'editing', true);
          this.prevValue = JSON.parse(JSON.stringify(row));
      },
      handleCancle(index,row){
        this.$set(row, 'editing', false);
        let prevContent = this.prevValue;
        this.$set(row, 'date', prevContent.date);
        this.$set(row, 'height', prevContent.height);
        this.$set(row, 'weight', prevContent.weight);
        this.$set(row, 'lung', prevContent.lung);
        this.$set(row, 'bloodpressure', prevContent.bloodpressure);
      },
      handleSave(index,row){
        this.$set(row, 'editing', false);
      },
      add_record() {
        let j = {
                    date: "",
                    height: "",
                    weight: "",
                    lung: "",
                    bloodpressure: "",
                };
                this.healthList.unshift(j);
      },
      delete_record() {
        let  multData = this.multipleSelection;
        let  tableData =this.healthList;
        let  multDataLen = multData.length;
        let  tableDataLen = tableData.length;
        for(let i = 0; i < multDataLen ;i++){ 
            for(let y=0;y < tableDataLen;y++){
                     if(JSON.stringify(tableData[y]) == JSON.stringify(multData[i])){  //判断是否相等，相等就删除
                       this.healthList.splice(y,1)
                     }
                 }
        }
      },
      getCottomsList() {
        console.log("111");
        this.$http("/medicalrecord/list", "get", {
          pageNo: this.currentPage,
          pageSize: 10,
        }).then((res) => {
          this.cottomsList = res.result.list;
        });
      },
      //获取电子病历列表
    },
    computed: {
      tables() {
        return this.cottomsList.filter((item) => {
          return (
            item.userinfo.username.includes(this.formInline.userName) &&
            item.seektime.includes(
              this.formInline.date == null ? "" : this.formInline.date
            )
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
    .el-input__inner {
      border-radius: 20px;
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
  