<template>
  <a-card :loading="loading" style="margin-top: 24px" :bordered="false" :body-style="{padding: '24px'}">
    <a-tabs default-active-key="1">
      <a-tab-pane key="1" tab="表格显示">
        <a-button @click="add">
          添加记录
        </a-button>
        <a-modal v-model="visible" title="添加记录" @ok="handleOK">
          <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
            <a-form-item label="日期">
              <a-date-picker
                v-decorator="['date', { rules: [{ required: true, message: '请选择日期!' }] }]"
                @change="onChange"
              />
            </a-form-item>
            <a-form-item label="身高">
              <a-input
                placeholder="请输入身高"
                suffix="cm"
                v-decorator="['height', {rules: [{ required: true, message: '请输入身高！', whitespace: true}]}]"
              />
            </a-form-item>
            <a-form-item label="体重">
              <a-input
                placeholder="请输入体重"
                suffix="kg"
                v-decorator="['weight', {rules: [{ required: true, message: '请输入体重！', whitespace: true}]}]"
              />
            </a-form-item>
            <a-form-item label="高压">
              <a-input
                placeholder="请输入体高压"
                suffix="kg"
                v-decorator="['high', {rules: [{ required: true, message: '请输入高压！', whitespace: true}]}]"
              />
            </a-form-item>
            <a-form-item label="低压">
              <a-input
                placeholder="请输入低压"
                suffix="kg"
                v-decorator="['low', {rules: [{ required: true, message: '请输入低压！', whitespace: true}]}]"
              />
            </a-form-item>
            <a-form-item label="肺活量">
              <a-input
                placeholder="请输入肺活量"
                suffix="kg"
                v-decorator="['lung', {rules: [{ required: true, message: '请输入肺活量！', whitespace: true}]}]"
              />
            </a-form-item>
          </a-form>
        </a-modal>
        <a-table :columns="columns" :data-source="data" bordered>
          <template
            v-for="col in ['date', 'height', 'weight', 'bp_high', 'bp_low', 'lung']"
            :slot="col"
            slot-scope="text, record"
          >
            <div :key="col">
              <a-input
                v-if="record.editable"
                style="margin: -5px 0"
                :value="text"
                @change="e => handleChange(e.target.value, record.key, col)"
              />
              <template v-else>
                {{ text }}
              </template>
            </div>
          </template>
          <template slot="edit" slot-scope="text, record">
            <div class="editable-row-operations">
              <span v-if="record.editable">
                <a @click="() => save(record.key)">保存</a>
                <a-popconfirm title="确定取消操作吗?" @confirm="() => cancel(record.key)">
                  <a>取消</a>
                </a-popconfirm>
              </span>
              <span v-else>
                <a :disabled="editingKey !== ''" @click="() => edit(record.key)">编辑</a>
                <a-popconfirm title="确定删除吗?" @confirm="() => del(record.key)">
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </div>
          </template>
        </a-table>
      </a-tab-pane>
      <a-tab-pane key="2" tab="图表显示">
        <div>
          <a-range-picker @change="selectDate"/>
        </div>
        <a-tabs default-active-key="3">
          <a-tab-pane key="3" tab="身高">
            <template>
              <div>
                <v-chart :forceFit="true" :height="height" :data="selectData" :scale="scale">
                  <v-tooltip />
                  <v-axis />
                  <v-smooth-line position="date*height" />
                  <v-point position="date*height" shape="circle" />
                </v-chart>
              </div>
            </template>
          </a-tab-pane>
          <a-tab-pane key="4" tab="体重">
            <template>
              <div>
                <v-chart :forceFit="true" :height="height" :data="selectData" :scale="scale">
                  <v-tooltip />
                  <v-axis />
                  <v-smooth-line position="date*weight" />
                  <v-point position="date*weight" shape="circle" />
                </v-chart>
              </div>
            </template>
          </a-tab-pane>
          <a-tab-pane key="5" tab="血压">
            <template>
              <div>
                <v-chart :forceFit="true" :height="height" :data="selectData_bp" :scale="scale">
                  <v-tooltip />
                  <v-axis />
                  <v-legend />
                  <v-smooth-line position="date*bp" color="high"/>
                  <v-point position="date*bp" color="high" shape="circle" />
                </v-chart>
              </div>
            </template>
          </a-tab-pane>
          <a-tab-pane key="6" tab="肺活量">
            <template>
              <div>
                <v-chart :forceFit="true" :height="height" :data="selectData" :scale="scale">
                  <v-tooltip />
                  <v-axis />
                  <v-smooth-line position="date*lung" />
                  <v-point position="date*lung" shape="circle" />
                </v-chart>
              </div>
            </template>
          </a-tab-pane>
        </a-tabs>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>
<script>
import {format} from 'date-fns'
import moment from 'moment'
import {getHealth, addHealth, changeHealth, deleteHealth} from '@/services/user'
import {mapGetters} from 'vuex'
const DataSet = require('@antv/data-set');
const columns = [
  {
    title: '日期',
    dataIndex: 'date',
    width: '15%',
    scopedSlots: { customRender: 'date' },
    sorter: (a, b) => a.height - b.height,
    sortDirections: ['descend', 'ascend'],
  },
  {
    title: '身高',
    dataIndex: 'height',
    width: '15%',
    scopedSlots: { customRender: 'height' },
    sorter: (a, b) => a.height - b.height,
    sortDirections: ['descend', 'ascend'],
  },
  {
    title: '体重',
    dataIndex: 'weight',
    width: '15%',
    scopedSlots: { customRender: 'weight' },
    sorter: (a, b) => a.weight - b.weight,
    sortDirections: ['descend', 'ascend'],
  },
  {
    title: '血压（高压）',
    dataIndex: 'bp_high',
    width: '15%',
    scopedSlots: { customRender: 'bp_high' },
    sorter: (a, b) => a.bp_high - b.bp_high,
    sortDirections: ['descend', 'ascend'],
  },
  {
    title: '血压（低压）',
    dataIndex: 'bp_low',
    width: '15%',
    scopedSlots: { customRender: 'bp_low' },
    sorter: (a, b) => a.bp_low - b.bp_low,
    sortDirections: ['descend', 'ascend'],
  },
  {
    title: '肺活量',
    dataIndex: 'lung',
    width: '15%',
    scopedSlots: { customRender: 'lung' },
    sorter: (a, b) => a.lung - b.lung,
    sortDirections: ['descend', 'ascend'],
  },
  {
    title: '编辑',
    dataIndex: 'edit',
    scopedSlots: { customRender: 'edit' },
  },
  // {
  //   title: '删除',
  //   dataIndex: 'delete',
  //   scopedSlots: { customRender: 'delete' },
  // },
];
const beginDay = new Date().getTime()
var begin = format(new Date(beginDay - 1000 * 60 * 60 * 24 * 15), 'yyyy-MM-dd');
var end = format(new Date(beginDay + 1000 * 60 * 60 * 24 * 15), 'yyyy-MM-dd');
const scale = [{
  dataKey: 'date',
  min: begin,
  max: end,
}];

const data = [];

var selectData = [...data];
selectData =  selectData.filter(function(item){
  return item.date>=begin && item.date<=end
})
var dv = new DataSet.View().source(selectData);
dv.transform({
  type: 'fold',
  fields: ['bp_low', 'bp_high'],
  key: 'high',
  value: 'bp',
});
var selectData_bp =  dv.rows

export default {
  data() {
    this.cacheData = data.map(item => ({ ...item }));
    return {
      data,
      selectData,
      selectData_bp,
      columns,
      editingKey: '',
      scale,
      height: 400,
      visible: false,
      formLayout: 'horizontal',
      form: this.$form.createForm(this, { name: 'coordinated' }),
    };
  },
  computed: {
    ...mapGetters('account', ['user', 'id']),
  },
  mounted() {
    getHealth(this.user).then( res => {
      var data = res.data
      this.data = []
      if(data.retCode == 905){
        console.log(905)
      }else{
        for(let i=0; i<data.result.length; i++){
          var item = data.result[i]
          this.data.push({
            key: item.uid,
            height: item.height,
            weight: item.weight,
            bp_high: item.highpressure,
            bp_low: item.lowpressure,
            lung: item.lung,
            date: format(new Date(item.date), 'yyyy-MM-dd')
          });
        }
      }
    })
  },
  methods: {
    add() {
      this.visible = true;
    },
    onChange(date, dateString) {
      console.log(dateString)
      this.form.setFieldsValue({
        date: dateString,
      });
    },
    handleOK(e) {
      e.preventDefault();
      this.form.validateFields((err) => {
        if (!err) {
          const date = moment(this.form.getFieldValue('date')).format('YYYY-MM-DD')
          const height = this.form.getFieldValue('height')
          const weight =  this.form.getFieldValue('weight')
          const bp_high = this.form.getFieldValue('high')
          const bp_low = this.form.getFieldValue('low')
          const lung = this.form.getFieldValue('lung')
          
          addHealth(date, height, weight, bp_high, bp_low, lung, this.id).then( res => {
            this.data.push({
            key: res.data.result.id,
            height: height,
            weight: weight,
            bp_high: bp_high,
            bp_low: bp_low,
            lung: lung,
            date: date,
          });
          })
          this.visible = false;
        }
      });
    },
    selectDate(date, dateString) {
      begin = dateString[0];
      end = dateString[1];
      const newData = [...this.data];
      this.selectData =  newData.filter(function(item){
        return item.date>=begin && item.date<=end
      })
      const dv = new DataSet.View().source(this.selectData);
      dv.transform({
        type: 'fold',
        fields: ['bp_low', 'bp_high'],
        key: 'high',
        value: 'bp',
      });
      this.selectData_bp =  dv.rows
      this.scale = [{
        dataKey: 'date',
        min: begin,
        max: end,
      }];
    },
    del(key){
      var i = 0;
      console.log(key)
      for(;i<this.data.length;i++){
        if(this.data[i].key === key){
          break;
        }
      }
      console.log(i)
      this.data.splice(i,1)
      deleteHealth(key).then( res => {
        console.log(res)
      })
    },
    handleChange(value, key, column) {
      const newData = [...this.data];
      const target = newData.filter(item => key === item.key)[0];
      if (target) {
        target[column] = value;
        this.data = newData;
      }
    },
    edit(key) {
      const newData = [...this.data];
      const target = newData.filter(item => key === item.key)[0];
      this.editingKey = key;
      if (target) {
        target.editable = true;
        this.data = newData;
      }
    },
    save(key) {
      const newData = [...this.data];
      const newCacheData = [...this.cacheData];
      const target = newData.filter(item => key === item.key)[0];
      console.log("target")
      console.log(target)
      changeHealth(target['date'], target['height'], target['weight'], target['bp_high'], target['bp_low'], target['lung'], target['key']).then(
          res => {
            console.log(res)
          }
        )
      const targetCache = newCacheData.filter(item => key === item.key)[0];
      if (target && targetCache) {
        delete target.editable;
        this.data = newData;
        Object.assign(targetCache, target);
        this.cacheData = newCacheData;
      }
      this.editingKey = '';
    },
    cancel(key) {
      const newData = [...this.data];
      const target = newData.filter(item => key === item.key)[0];
      this.editingKey = '';
      if (target) {
        Object.assign(target, this.cacheData.filter(item => key === item.key)[0]);
        delete target.editable;
        this.data = newData;
      }
    },
  },
};
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>
