<template>
  <div>
  <a-card :bordered="false" class="search-form">
    <a-form :form="form">
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
      <form-row label="科室" style="padding-bottom: 11px">
        <a-form-item  style="left: 0px;width: 100%;">
          <a-select mode="multiple" style="max-width: 100%;" @change="handleDepartmentChange">
            <a-select-option v-for="i in offices" :key="i" :value="i">
              {{i}}
            </a-select-option>
          </a-select>
        </a-form-item>
      </form-row>
    </a-form>
  </a-card>
  <a-list :grid="{ gutter: 16, xl: 4, lg: 3, md: 3, sm: 2, xs: 1 }" :pagination="pagination" :data-source="data">
    <a-list-item slot="renderItem" slot-scope="item" style="padding: 0 8px">
      <a-card>
          <a-tooltip class="tool"  title="详细信息" slot="actions">
            <a-icon type="user" @click="detail(item.id)" />
          </a-tooltip>
          <a-modal v-model="visible[item.id]" title="医生详情" @ok="handleOk(item.id)">
            <a-avatar :size="80" src="/doctor_ avatar/001-doctor.png" />
          </a-modal>
          <a-tooltip class="tool"  title="分享" slot="actions">
            <a-icon type="share-alt" />
          </a-tooltip>
          <div class="content">
            <a-avatar :size="80" src="/doctor_ avatar/001-doctor.png" />
            <a-card-meta>
              <template slot="title">
                <p style="font-size:18px;">{{item.username}}</p>
              </template>
              <template slot="description">
                <p>{{item.hospital}}</p>
                <p>{{item.office}}</p>
              </template>
            </a-card-meta>
            <!-- <div>
              <p>新增用户</p>
              <p>{{item.department}}</p>
            </div> -->
          </div>
      </a-card>
    </a-list-item>
  </a-list>
</div>
</template>
<script>
import FormRow from '../components/form/FormRow'
const hospitals = ["中国医学科学院北京协和医院", "四川大学华西医院", "中国人民解放军总医院", 
                    "上海交通大学医学院附属瑞金医院", "复旦大学附属中山医院", "中山大学附属第一医院",
                    "华中科技大学同济医学院", "空军军医大学西京医院", "复旦大学附属华山医院", "华中科技大学同济医学院"];
const offices = ["急诊科", "内科", "外科", "妇产科", "儿科", "中医科", "耳鼻喉科", "口腔科", "眼科", "皮肤科", "麻醉科", "康复科", "预防保健科"]
const data = [];
var selected_hospital = hospitals;
var selected_office = offices;
var visible = {};
for (let i = 0; i < 100; i++) {
  data.push({
    id: i,
    age: i,
    username: "张三",
    hospital: hospitals[i%10],
    office: offices[i%10],
    // heading:
    comment: ".................."
  });
  visible[i] = false;
}
export default {
  components: {FormRow},
  data() {
    return {
      data,
      hospitals,
      offices,
      ModalText: 'Content of the modal',
      visible,
      confirmLoading: false,
      pagination: {
        onChange: page => {
          console.log(page);
        },
        pageSize: 12,
      },
      form: this.$form.createForm(this)
    };
  },
  methods: {
    detail(id) {
      this.visible[id] = true;
    },
    handleOk(id) {
      this.visible[id] = false;
    },
    handleHospitalChange(value) {
      console.log(value);
      if(value.length == 0) {
        this.data = data;
        selected_hospital = hospitals;
        return;
      }
      this.data = [];
      selected_hospital = value;
      for (let index = 0; index < data.length; index++) {
        if(selected_hospital.indexOf(data[index]["hospital"]) != -1 && selected_office.indexOf(data[index]["office"]) != -1) {
          this.data.push(data[index]);
        }
      }
      console.log(this.data);
    },
    handleDepartmentChange(value) {
      console.log(value);
      if(value.length == 0) {
        selected_office = offices;
        this.data = data;
        return;
      }
      this.data = [];
      selected_office = value;
      for (let index = 0; index < data.length; index++) {
        if(selected_hospital.indexOf(data[index]["hospital"]) != -1 && selected_office.indexOf(data[index]["office"]) != -1) {
          this.data.push(data[index]);
        }
      }
      console.log(this.data);
    },
  }
};
</script>
<style lang="less" scoped>
  .clearfix() {
    zoom: 1;
    &:before,
    &:after {
      content: ' ';
      display: table;
    }
    &:after {
      clear: both;
      visibility: hidden;
      font-size: 0;
      height: 0;
    }
  }
  .content {
    .clearfix();
    margin-top: 16px;
    & > div {
      position: relative;
      text-align: left;
      float: right;
      width: 50%;
      /* p {
        color: @text-color-second;
        font-size: 18px;
        line-height: 20px;
        margin-bottom: 4px;
      }
      p:first-child {
        line-height: 32px;
        font-size: 24px;
        margin: 4px;
      } */
    }
  }
</style>

