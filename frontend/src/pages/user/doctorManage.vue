<template>
    <div>
      <a-card :bordered="false" class="search-form">
      <a-form :form="form">
        <form-row label="科室" style="padding-bottom: 11px">
          <a-form-item  style="left: 0px;width: 100%;">
            <a-select mode="multiple" style="max-width: 100%;" @change="handleDepartmentChange">
              <a-select-option v-for="i in departments" :key="i" :value="i">
                {{i}}
              </a-select-option>
            </a-select>
          </a-form-item>
        </form-row>
        <form-row label="医院" style="padding-bottom: 11px">
          <a-form-item style="left: 0px;width: 100%;">
            <a-select
              mode="multiple" style="max-width: 100%;"
              @change="handleHospitalChange"
            >
              <a-select-option v-for="i in hospitals" :key="i" :value="i">
                {{i}}
              </a-select-option>
            </a-select>
          </a-form-item>
        </form-row>
      </a-form>
    </a-card>
      <a-list :grid="{ gutter: 16, xl: 4, lg: 3, md: 3, sm: 2, xs: 1 }" :data-source="doctorData" :pagination="pagination">
        <a-list-item slot="renderItem" slot-scope="item" style="padding: 0 8px;">
          <a-card>
              <a-tooltip title="详细信息" slot="actions">
                <a-icon type="user" @click="detail(item.key)"/>
              </a-tooltip>
              <a-modal :visible="visible[item.key]" title="医生详情" @ok="handleOk(item.key)">
                <div style="display: table-cell;height: 380px;width: 100%">
                  <div style="width: 100%;height: 100px;">
                    <a-avatar :size="80" :src="item.avatar" />
                    <label style="position: absolute;
                                  top: 100px;
                                  left: 150px;
                                  font-size: 24px;
                                  text-align: left;
                                  line-height: 16px;
                                  margin-bottom: 20px;
                                  "
                    >{{item.username}}</label>
                    <label style="position: absolute;
                                  top: 130px;
                                  left: 150px;
                                  font-size: 18px;
                                  text-align: center;
                                  line-height: 16px;
                                  margin-bottom: 20px;
                                  "
                    >{{item.age}}岁</label>
                    </div>
                </div>
                <div>
                  <div class="content" style="top: 180px;left: -180px;">
                    <div>
                      <p>所在医院</p>
                      <p>{{item.hospital}}</p>
                    </div>
                    <div style="top: 12px;">
                      <p>科室</p>
                      <p>{{item.office}}</p>
                    </div>
                    <div style="top: 48px;">
                      <p>评价</p>
                      <p>{{item.comment}}</p>
                    </div>
                  </div>
                </div>
              </a-modal>
              <!-- <a-tooltip title="编辑" slot="actions">
                <a-icon type="edit" />
              </a-tooltip> -->
              <a-tooltip title="挂号" slot="actions">
                <a-icon type="share-alt" @click="register(item.phone, item.id, item.username)" />
              </a-tooltip>
              <a-avatar :src="item.avatar" class="avatar" style="float: left;"/>
              <label style="position: absolute;
                            top: 62.5%;
                            left: 15%;
                            width: 20%;
                            font-size: 18px;
                            text-align: center;
                            line-height: 16px;
                            margin-bottom: 20px;
                            "
              >{{item.username}}</label>
              <div class="content">
                <div>
                  <p>所在医院</p>
                  <p>{{item.hospital}}</p>
                </div>
                <div style="top: 12px;">
                  <p>科室</p>
                  <p>{{item.office}}</p>
                </div>
              </div>
          </a-card>
        </a-list-item>
      </a-list>
    </div>
  </template>
  
  <script>
  import FormRow from '@/components/form/FormRow'
  import {getDoctor, createSession, addMedical} from '@/services/user'
  import {mapGetters} from 'vuex'
  import {format} from 'date-fns'
  
  const hospitals = ["中国医学科学院北京协和医院", "四川大学华西医院", "中国人民解放军总医院", 
                      "上海交通大学医学院附属瑞金医院", "复旦大学附属中山医院", "中山大学附属第一医院",
                      "华中科技大学同济医学院", "空军军医大学西京医院", "复旦大学附属华山医院", "华中科技大学同济医学院"];
  
  const departments = ["急诊科", "内科", "外科", "妇产科", "儿科", "中医科", "耳鼻喉科", "口腔科", "眼科", "皮肤科", "麻醉科", "康复科", "预防保健科"]
  
  var doctorData = [];
  var a_doctorData = [];
  var visible = {};
  
  var selected_hospital = hospitals;
  var selected_department = departments;
  
  for (let i = 0; i < 1000; i++) {
    visible[i] = false;
  }
  
  export default {
    components: {FormRow},
    
    data() {
      return {
        visible,
        hospitals,
        doctorData,
        a_doctorData,
        departments,
        pagination: {
          onChange: page => {
            console.log(page);
          },
          pageSize: 8,
        },
        form: this.$form.createForm(this)
      };
    },
    computed: {
      ...mapGetters('account', ['user', 'id']),
    },
    mounted() {
      let that = this
      getDoctor(this.user).then( res => {
        const data = res.data
        that.doctorData = []
        that.a_doctorData = []
        if(data.retCode===200){
          for(let i=0; i<data.result.length; i++){
            const it = data.result[i]
            that.doctorData.push({
              key: i,
              id: it.id,
              age: it.age,
              username: it.username,
              hospital: it.hospital,
              office: it.office,
              // heading:
              comment: it.comment,
              avatar: "http://qw1cav079.bkt.clouddn.com/00" + (i % 10).toString() + "-doctor.png",
              phone: it.phone
            })
          }
          that.a_doctorData = that.doctorData
        }
      })
    },
    methods: {
      register(phone, did, name) {
        createSession(this.user, phone).then( res => {
          console.log(res)
        })
        const now = new Date().getTime()
        const time = format(new Date(now), 'yyyy-MM-dd');
        console.log(time)
        const pres = []
        for(let i=0;i<3;i++){
          pres.push({
            id: null,
            userid: this.id,
            recordid: null,
            medicine: null,
            description: null
          })
        }
        addMedical(this.id, time, name, null, pres, did).then( res => {
          console.log(res)
        })
      },
      detail(id) {
        console.log(id);
        this.visible[id] = true;
        console.log(this.visible)
      },
      handleOk(id) {
        this.visible[id] = false;
      },
      handleHospitalChange(value) {
        console.log(value);
        if(value.length == 0) {
          this.doctorData = this.a_doctorData;
          selected_hospital = hospitals;
          return;
        }
        this.doctorData = [];
        selected_hospital = value;
        for (let index = 0; index < this.a_doctorData.length; index++) {
          if(selected_hospital.indexOf(this.a_doctorData[index]["hospital"]) != -1 && selected_department.indexOf(this.a_doctorData[index]["office"]) != -1) {
            this.doctorData.push(this.a_doctorData[index]);
          }
        }
        console.log(this.doctorData);
      },
      handleDepartmentChange(value) {
        console.log(value);
        if(value.length == 0) {
          selected_department = departments;
          this.doctorData = this.a_doctorData;
          return;
        }
        this.doctorData = [];
        selected_department = value;
        for (let index = 0; index < this.a_doctorData.length; index++) {
          if(selected_hospital.indexOf(this.a_doctorData[index]["hospital"]) != -1 && selected_department.indexOf(this.a_doctorData[index]["office"]) != -1) {
            this.doctorData.push(this.a_doctorData[index]);
          }
        }
        console.log(this.doctorData);
      },
    }
  }
  </script>
  
  <style lang="less" scoped>
    .search-form{
      margin-bottom: 24px;
    }
    .avatar {
      top: 10px;
      width: 40%;
      height: 40%;
      margin-bottom: 50px;
    }
    .content {
      margin-top: 16px;
      margin-bottom: 20px;
      position: absolute;
      float: right;
      margin-left: 35%;
      width: 65%;
      & > div {
        position: relative;
        text-align: left;
        width: 70%;
        left: 10%;
        p {
          line-height: 28px;
          font-size: 18px;
          margin: 0;
        }
        p:first-child {
          color: @text-color-second;
          font-size: 12px;
          line-height: 20px;
          margin-bottom: 4px;
        }
      }
    }
  </style>
  