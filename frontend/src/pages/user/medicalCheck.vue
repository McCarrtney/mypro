<template>
    <a-card style = "width:100%" title="病历信息">
        <a-table :columns="columns" :data-source="data" >
  
            <template v-for="col in ['createTime', 'doctor']" :slot="col" slot-scope="text">
              <div :key="col">
                {{text}}
              </div>
            </template>
  
            <template slot="key" slot-scope="text,record">
                <div key="key">
                    <a-button type="primary" @click="showModal(text)">
                    详情
                    </a-button>
                    <a-modal
                      title="详细信息"
                      width = 60%
                      cloasble= clo
                      :visible="list[text]"
                      :footer="null"
                      :closable="clo"
                    >
                    <template>
                      <a-card title ="病历信息" style="text-align:left;">
                        <p style="fontSize: 16px;color: rgba(0, 0, 0, 0.85); marginBottom: 16px;fontWeight: 500">
                          基本情况
                        </p>
                        <a-card title="医生姓名" >
                          <h style="fontSize: 16px;">
                          {{record.doctor}}
                          </h>
                        </a-card>
                        <a-card title="就诊状态" :style="{ marginTop: '8px' }">
                          <h style="fontSize: 16px;">
                          <!-- {{record.state}} -->
                          已就诊
                          </h>
                        </a-card>
                        <a-card title="诊断结果" :style="{ marginTop: '8px' }">
                          <h style="fontSize: 16px;">
                          {{record.diagnosis}}
                          </h>
                        </a-card>
                      </a-card>
                      <a-card title="患者处方信息" style="{ marginTop: '8px' }">
                        <div style="background-color: #ececec; padding: 20px;">
                          <a-row :gutter="16">
                            <a-col :span="8">
                              <a-card title="处方一" :bordered="false">
                                <!-- <p style="fontSize:16px">处方ID:{{record.prescriptions[0].id}}</p> -->
                                <p style="fontSize:16px">药品单:<br/>{{record.prescriptions[0].medicine}}</p>
                                <p style="fontSize:16px">用法:<br/>{{record.prescriptions[0].description}}</p>
                              </a-card>
                            </a-col>
                            <a-col :span="8">
                              <a-card title="处方二" :bordered="false">
                                <!-- <p style="fontSize:16px">处方ID:{{record.prescriptions[1].id}}</p> -->
                                <p style="fontSize:16px">药品单:<br/>{{record.prescriptions[1].medicine}}</p>
                                <p style="fontSize:16px">用法:<br/>{{record.prescriptions[1].description}}</p>
                              </a-card>
                            </a-col>
                            <a-col :span="8">
                              <a-card title="处方三" :bordered="false">
                                <!-- <p style="fontSize:16px">处方ID:{{record.prescriptions[2].id}}</p> -->
                                <p style="fontSize:16px">药品单:<br/>{{record.prescriptions[2].medicine}}</p>
                                <p style="fontSize:16px">用法:<br/>{{record.prescriptions[2].description}}</p>
                              </a-card>
                            </a-col>
                          </a-row>
                        </div>
                      </a-card>
                    </template>
  
                    <br/>
                    <br/>  
                      <div style="text-align:right">
                      <a-button type="primary" @click="handleCancel(text)">
                        返回
                      </a-button>
                      </div>
                    </a-modal>
                </div>
            </template>
        </a-table>
    </a-card>
  </template>
  
  <script>
  import {getMedical} from '@/services/user'
  import {mapGetters} from 'vuex'
  const columns = [
  {
    title: '登记时间',
    dataIndex: 'createTime',
    width: '40%',
    align: 'center',
    scopedSlots: { customRender: 'createTime' },
  },
  {
    title: '医生姓名',
    dataIndex: 'doctor',
    width: '30%',
    align: 'center',
    scopedSlots: { customRender: 'doctor' },
  },
//   {
//     title: '流程状态',
//     dataIndex: 'state',
//     width: '20%',
//     align: 'center',
//     scopedSlots: { customRender: 'state' },
//   },
  /*{
    title: '处方号',
    dataIndex: 'pres_list',
    width: '50%',
    align: 'center',
    scopedSlots: { customRender: 'pres_list' },
  },*/
  /*{
    title:'头像',
    dataIndex:'photo',
    render:(record) => (<img src={require(record)}/>),
    scopedSlots: { customRender: 'photo' },
  },*/
  {
    title: '详情',
    dataIndex: 'key',
    align: 'center',
    scopedSlots: { customRender: 'key' },
  },
  ];
  
  const data = [];
  var list = {};
  for(let i=0;i<100000;i++){
      list[i] = false;
  }
//   var list = new Object();
//   list.add = function(key,value){this[key]=value};
//   list.get = function(key){return this[key]};
  
//   for (let i = 0; i < 100; i++) {
//   data.push({
//     key: i.toString(),
//     time:"2021-7-9",
//     user_id: 100000+i,
//     state: '已就诊',
//     name : '张三',
//     diagnosis: '风湿性关节炎',
//     prescription:[],
//     operation: 100000+i,
//     // name: `Edrward ${i}`,
//     // age: 32,
//     // address: `London Park no. ${i}`
//   });
//   data[i].prescription[0]={id:10001,userid:35,medicine:'保济丸',description:'每日三次，一次2粒'};
//   data[i].prescription[1]={id:10002,userid:35,medicine:'头孢胶囊',description:'每日一次，一次3粒'};
//   data[i].prescription[2]={id:10003,userid:35,medicine:'莲花口服液',description:'每日一次，一次1支'};
//   list.add(100000+i,false);
//   }
  
  
  export default {
  data() {
    this.cacheData = data.map(item => ({ ...item }));
    return {
      data,
      columns,
      editingKey: '',
      clo:false,
  
      ModalText: 'Content of the modal',
      list,
    };
  },
  computed: {
      ...mapGetters('account', ['user', 'id']),
    },
    mounted() {
        getMedical(this.user).then( res => {
          this.data = []
            for(let i=0;i<res.data.result.length;i++){
              var item = res.data.result[i];
              if(item.rid!=38 && item.rid!=42){
                item["key"] = i
                this.data.push(item)
              }
            }
            console.log(this.data)
        })
    },
  methods: {
    showModal(text) {
    //   this.list.add(text,true);
        this.list[text] = true;
        console.log(this.list[text])
        console.log(text)
    },
  
    handleCancel(text) {
      console.log('Clicked cancel button');
    //   this.list.add(text,false);
        this.list[text] = false;
    },
  },
  };
  
  </script>
  
  <style scoped>
  .editable-row-operations a {
  margin-right: 8px;
  }
  .left{
    width: 60%;
    float: left;
    text-align: center;
  }
  .right{
   width:40%;
   float: right;
    text-align: center;
  }
  </style>
  