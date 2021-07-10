<template>
  <a-card title="个人中心">
    <div style="width:auto">
      <div style="height:300px">
        <div style=" width:70%;;float:left;">
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

        <div style=" width:30%;float:right;">
          <br/>
          <p>
            头像上传:
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
import {changeUserInfo} from '@/services/user'
import {mapGetters} from 'vuex'
  
  
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
        doc_Age:'',
        
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
      
     
      save_circle()
      {
        changeUserInfo(this.id, this.doc_Name, this.doc_Age).then(
          res => {
            console.log(res)
          }
        )
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
  