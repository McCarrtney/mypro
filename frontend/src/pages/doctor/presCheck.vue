<template>
  <a-card style = "width:100%" title="病历信息">
      <a-table :columns="columns" :data-source="data" >

          <template v-for="col in ['createTime', 'doctor']" :slot="col" slot-scope="text">
            <div :key="col">
              {{text}}
            </div>
          </template>

          <template slot="rid" slot-scope="text,record">
              <div key="rid">
                  <a-button type="primary" @click="showModal(text)">
                  编辑
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
                      <a-card title="患者姓名" >
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
                        <a-input
                          style="margin: 0px 0"
                          v-model="diagnosis"
                          />
                      </a-card>
                    </a-card>
                    <a-card title="患者处方信息" style="{ marginTop: '8px' }">
                      <div style="background-color: #ececec; padding: 20px;">
                        <a-row :gutter="16">
                          <a-col :span="8">
                            <a-card title="处方一" :bordered="false">
                              <p style="fontSize:16px">药品单:</p>
                              <a-input
                                style="margin: 0px 0"
                                v-model="medical1"
                              />
                              <p style="fontSize:16px">用法:</p>
                              <a-input
                                style="margin: 0px 0"
                                v-model="describe1"
                              />
                            </a-card>
                          </a-col>
                          <a-col :span="8">
                            <a-card title="处方二" :bordered="false">
                              <p style="fontSize:16px">药品单:</p>
                              <a-input
                                style="margin: 0px 0"
                                v-model="medical2"
                              />
                              <p style="fontSize:16px">用法:</p>
                              <a-input
                                style="margin: 0px 0"
                                v-model="describe2"
                              />
                            </a-card>
                          </a-col>
                          <a-col :span="8">
                            <a-card title="处方三" :bordered="false">
                              <p style="fontSize:16px">药品单:</p>
                              <a-input
                                style="margin: 0px 0"
                                v-model="medical3"
                              />
                              <p style="fontSize:16px">用法:</p>
                              <a-input
                                style="margin: 0px 0"
                                v-model="describe3"
                              />
                            </a-card>
                          </a-col>
                        </a-row>
                      </div>
                    </a-card>
                  </template>

                  <br/>
                  <br/>  
                    <div style="text-align:right">
                      <a-button type="primary" @click="Cancel(text)">
                        取消
                      </a-button>
                    <a-button type="primary" @click="handleCancel(text)">
                      保存
                    </a-button>
                    </div>
                  </a-modal>
              </div>
          </template>
      </a-table>
  </a-card>
</template>

<script>
import {format} from 'date-fns'
import {getPatient, changeMedical} from '@/services/user'
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
  title: '患者姓名',
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
  dataIndex: 'rid',
  align: 'center',
  scopedSlots: { customRender: 'rid' },
},
];

const data = [];
var list = {};
for(let i=0;i<100000;i++){
    list[i] = false;
}

export default {
data() {
  this.cacheData = data.map(item => ({ ...item }));
  return {
    data,
    columns,
    editingKey: '',
    clo:false,

    diagnosis: "",
    medical1: "",
    describe1: "",
    medical2: "",
    describe2: "",
    medical3: "",
    describe3: "",

    ModalText: 'Content of the modal',
    list,
  };
},
computed: {
    ...mapGetters('account', ['user', 'id']),
  },
  mounted() {
      getPatient(this.user).then( res => {
        this.data = res.data.result
          console.log(this.data)
      })
  },
methods: {
  showModal(text) {
  //   this.list.add(text,true);
      this.list[text] = true;
      console.log(this.list[text])
      console.log(text)
      var i = 0;
    for(;i<this.data.length;i++){
      if(this.data[i].rid == text){
        break;
      }
    }
    this.diagnosis = this.data[i].diagnosis
    this.medical1 = this.data[i].prescriptions[0].medicine
    this.describe1 = this.data[i].prescriptions[0].description
    this.medical2 = this.data[i].prescriptions[1].medicine
    this.describe2 = this.data[i].prescriptions[1].description
    this.medical3 = this.data[i].prescriptions[2].medicine
    this.describe3 = this.data[i].prescriptions[2].description
  },
  Cancel(text){
    this.list[text] = false;
  },
  handleCancel(text) {
    console.log('Clicked cancel button');
  //   this.list.add(text,false);
    var i = 0;
    for(;i<this.data.length;i++){
      if(this.data[i].rid == text){
        break;
      }
    }
    this.data[i].diagnosis = this.diagnosis
    this.data[i].prescriptions[0].medicine = this.medical1
    this.data[i].prescriptions[0].description = this.describe1
    this.data[i].prescriptions[1].medicine = this.medical2
    this.data[i].prescriptions[1].description = this.describe2
    this.data[i].prescriptions[2].medicine = this.medical3
    this.data[i].prescriptions[2].description = this.describe3
    const now = new Date().getTime()
    const begin = format(new Date(now), 'yyyy-MM-dd');
    changeMedical(begin, this.data[i].diagnosis, this.data[i].prescriptions, this.data[i].rid).then(
      res => {
        console.log(res)
      }
    )
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
