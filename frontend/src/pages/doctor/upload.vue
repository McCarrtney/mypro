  <template>
    <a-card title="审核表">
      <div style="width:auto">
        <div style="height:300px">
          <div style=" width:70%;float:left;">
            <br/><br/>
            <p>
              您的姓名:
            </p>
            <a-input ref="doc_NameInput" v-model="doc_Name" placeholder=" 您的姓名" 
            style = "width: 200px; margin: 10px">
              <a-icon slot="prefix" type="user" />
              <a-tooltip slot="suffix" title="注意字间勿加空格">
                <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
              </a-tooltip>
            </a-input>
            <br/><br/><br/>
            <p>
              您的出生日期:
            </p>
              <a-date-picker @change="onChange" style = "width: 200px; margin: 10px"/>
            <br />
            <br />
            
            <br />
            <br />
          </div>
    
          <div style="width:30%;float:right;">
            <br />
            <br />
            <p>
                  填写进度
            </p>
            <a-progress type="circle" :percent="percent" style = "margin: 20px ; text-align:center" />
          </div>
        </div>
        
        <div style="height:300px">
          <br/> 
          <p>
            您的科室和所属医院:
          </p>
          <div style=" width:70%;float:left;">
            <a-form :form="form">
              <form-row label="科室" style="padding-bottom: 11px">
                <a-form-item  style="left: 0px;width: 70%;">
                  <a-select style="max-width: 70%;" @change="handleDepartmentChange">
                    <a-select-option v-for="i in departments" :key="i" :value="i">
                      {{i}}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </form-row>
              <form-row label="医院" style="padding-bottom: 11px">
                <a-form-item style="left: 0px;width: 70%;">
                  <a-select
                    style="max-width: 70%;"
                    @change="handleHospitalChange"
                  >
                    <a-select-option v-for="i in hospitals" :key="i" :value="i" maxTagCount =1>
                      {{i}}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </form-row>
            </a-form>
    
            <p>
              备注:
            </p>
            <a-input ref="ComInput" v-model="doc_Comment" placeholder="..." 
            style = "width: 200px; margin: 10px">
              <a-icon slot="prefix" type="user" />
              <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
            </a-input>
          </div>
          
          <div style=" width:30%;float:right;">
            <br/>
            <p>
              材料上传:
            </p>
            <a-upload
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
            :before-upload="beforeUpload"
            @change="handleChange"
            >
            <img v-if="imageUrl" :src="imageUrl" alt="avatar" style="height:120px;width:120px;"/>
            <div v-else>
              <a-icon :type="loading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">
                上传
              </div>
            </div>
            </a-upload>
          </div>
    
        </div>
      </div>
      <br/>
      <br/>
      <div style="text-align:center">
      <a-button type="primary" @click="save_circle" style = "text-align:center">
        保存
      </a-button>
      <br/>
      <br/>
      </div>
    </a-card>
    </template>
    
    <script>
      import {changeDoctorInfo} from '@/services/user'
  import {mapGetters} from 'vuex'
    var a_doctorData = [];
    
    const hospitals = ["中国医学科学院北京协和医院", "四川大学华西医院", "中国人民解放军总医院", 
                        "上海交通大学医学院附属瑞金医院", "复旦大学附属中山医院", "中山大学附属第一医院",
                        "华中科技大学同济医学院", "空军军医大学西京医院", "复旦大学附属华山医院", "华中科技大学同济医学院"];
    
    const departments = ["急诊科", "内科", "外科", "妇产科", "儿科", "中医科", "耳鼻喉科", "口腔科", "眼科", "皮肤科", "麻醉科", "康复科", "预防保健科"]
    
    var selected_hospital = hospitals;
    var selected_department = departments;
    
    function getBase64(img, callback) {
      const reader = new FileReader();
      reader.addEventListener('load', () => callback(reader.result));
      reader.readAsDataURL(img);
    }
    
    export default {
      data() {
        //var Name_flag = false;
        //var Age_flag = false;
    
        return {
          doc_Name:'',//用户名
          doc_Comment:'',
          doc_Age:'',
          hos_out:[],
          dep_out:[],
    
          hos_select:0,
          depart_select:0,
          percent:0,
        
          hospitals,
          departments,
          
          loading: false,
          imageUrl: '',
    
          year:'',
          year_string:'',
        };
      },
      computed: {
        ...mapGetters('account', ['user', 'id']),
      },
      methods: {
        emitEmpty_Name() {//用户名输入为空时
          this.$refs.doc_NameInput.focus();
          this.doc_Name = '';
        },
        increase() {
          let percent = this.percent + 10;
          if (percent > 100) {
            percent = 100;
          }
          this.percent = percent;
        },
        decline() {
          let percent = this.percent - 10;
          if (percent < 0) {
            percent = 0;
          }
          this.percent = percent;
        },
        
       
        save_circle()
        {
          this.percent = 0;
    
          if (this.doc_Name!='')
          this.percent += 20;
          if (this.doc_Age!='')
          this.percent += 20; 
          if (this.doc_Comment!='')
          this.percent += 20;
          if (this.hos_select==1)
          this.percent += 20;
          if (this.depart_select==1)
          this.percent += 20;
          changeDoctorInfo(this.id, this.doc_Name, this.doc_Age, this.hos_out, this.dep_out, null, this.doc_Comment, 1).then(
            res => {
              console.log(res)
            }
          )
        },
        
        handleHospitalChange(value) {
          console.log(value);
          if(value.length == 0) {
    
            this.hos_select = 0;
    
            this.doctorData = a_doctorData;
            selected_hospital = hospitals;
    
            return;
          }
    
          this.hos_select = 1;
          this.hos_out = value;
    
          this.doctorData = [];
          selected_hospital = value;
          for (let index = 0; index < a_doctorData.length; index++) {
            if(selected_hospital.indexOf(a_doctorData[index]["hospital"]) != -1 && selected_department.indexOf(a_doctorData[index]["department"]) != -1) {
              this.doctorData.push(a_doctorData[index]);
            }
          }
          console.log(this.doctorData);
        },
        handleDepartmentChange(value) {
          console.log(value);
          if(value.length == 0) {
    
            this.depart_select = 0;
            
            selected_department = departments;
            this.doctorData = a_doctorData;
            return;
          }
    
          this.depart_select = 1;
          this.dep_out = value;
    
          this.doctorData = [];
          selected_department = value;
          for (let index = 0; index < a_doctorData.length; index++) {
            if(selected_hospital.indexOf(a_doctorData[index]["hospital"]) != -1 && selected_department.indexOf(a_doctorData[index]["department"]) != -1) {
              this.doctorData.push(a_doctorData[index]);
            }
          }
          console.log(this.doctorData);
        },
        handleChange(info) {
          if (info.file.status === 'uploading') {
            this.loading = true;
            return;
          } else if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl => {
              this.imageUrl = imageUrl;
              this.loading = false;
            });
          } 
          else 
          {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, imageUrl => {
              this.imageUrl = imageUrl;
              this.loading = false;
            });
          }
        },
        beforeUpload(file) {
          const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
          if (!isJpgOrPng) {
            this.$message.error('You can only upload JPG file!');
          }
          const isLt2M = file.size / 1024 / 1024 < 2;
          if (!isLt2M) {
            this.$message.error('Image must smaller than 2MB!');
          }
          return isJpgOrPng && isLt2M;
        },
        onChange(date, dateString) {
          this.year = date;
          this.doc_Age = dateString;
        },
      },
    };
    </script>
    <style>
    /* you can make up upload button and sample style by using stylesheets */
    .ant-upload-select-picture-card i {
      font-size: 32px;
      color: #999;
    }
    
    .ant-upload-select-picture-card .ant-upload-text {
      margin-top: 8px;
      color: #666;
    }
    
    .avatar-uploader > .ant-upload {
      width: 128px;
      height: 128px;
    }
    .ant-upload-select-picture-card i {
      font-size: 32px;
      color: #999;
    }
    
    .ant-upload-select-picture-card .ant-upload-text {
      margin-top: 8px;
      color: #666;
    }
    </style>
    