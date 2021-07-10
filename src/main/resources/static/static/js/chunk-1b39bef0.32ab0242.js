(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1b39bef0"],{"1c2d8":function(t,e,a){"use strict";a("23d2")},"23d2":function(t,e,a){},"60fb":function(t,e,a){"use strict";a.r(e);var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("a-card",{attrs:{title:"审核表"}},[a("div",{staticStyle:{width:"auto"}},[a("div",{staticStyle:{height:"300px"}},[a("div",{staticStyle:{width:"70%",float:"left"}},[a("br"),a("br"),a("p",[t._v(" 您的姓名: ")]),a("a-input",{ref:"doc_NameInput",staticStyle:{width:"200px",margin:"10px"},attrs:{placeholder:" 您的姓名"},model:{value:t.doc_Name,callback:function(e){t.doc_Name=e},expression:"doc_Name"}},[a("a-icon",{attrs:{slot:"prefix",type:"user"},slot:"prefix"}),a("a-tooltip",{attrs:{slot:"suffix",title:"注意字间勿加空格"},slot:"suffix"},[a("a-icon",{staticStyle:{color:"rgba(0,0,0,.45)"},attrs:{type:"info-circle"}})],1)],1),a("br"),a("br"),a("br"),a("p",[t._v(" 您的出生日期: ")]),a("a-date-picker",{staticStyle:{width:"200px",margin:"10px"},on:{change:t.onChange}}),a("br"),a("br"),a("br"),a("br")],1),a("div",{staticStyle:{width:"30%",float:"right"}},[a("br"),a("br"),a("p",[t._v(" 填写进度 ")]),a("a-progress",{staticStyle:{margin:"20px","text-align":"center"},attrs:{type:"circle",percent:t.percent}})],1)]),a("div",{staticStyle:{height:"300px"}},[a("br"),a("p",[t._v(" 您的科室和所属医院: ")]),a("div",{staticStyle:{width:"70%",float:"left"}},[a("a-form",{attrs:{form:t.form}},[a("form-row",{staticStyle:{"padding-bottom":"11px"},attrs:{label:"科室"}},[a("a-form-item",{staticStyle:{left:"0px",width:"70%"}},[a("a-select",{staticStyle:{"max-width":"70%"},on:{change:t.handleDepartmentChange}},t._l(t.departments,(function(e){return a("a-select-option",{key:e,attrs:{value:e}},[t._v(" "+t._s(e)+" ")])})),1)],1)],1),a("form-row",{staticStyle:{"padding-bottom":"11px"},attrs:{label:"医院"}},[a("a-form-item",{staticStyle:{left:"0px",width:"70%"}},[a("a-select",{staticStyle:{"max-width":"70%"},on:{change:t.handleHospitalChange}},t._l(t.hospitals,(function(e){return a("a-select-option",{key:e,attrs:{value:e,maxTagCount:"1"}},[t._v(" "+t._s(e)+" ")])})),1)],1)],1)],1),a("p",[t._v(" 备注: ")]),a("a-input",{ref:"ComInput",staticStyle:{width:"200px",margin:"10px"},attrs:{placeholder:"..."},model:{value:t.doc_Comment,callback:function(e){t.doc_Comment=e},expression:"doc_Comment"}},[a("a-icon",{attrs:{slot:"prefix",type:"user"},slot:"prefix"}),a("a-icon",{staticStyle:{color:"rgba(0,0,0,.45)"},attrs:{type:"info-circle"}})],1)],1),a("div",{staticStyle:{width:"30%",float:"right"}},[a("br"),a("p",[t._v(" 材料上传: ")]),a("a-upload",{staticClass:"avatar-uploader",attrs:{name:"avatar","list-type":"picture-card","show-upload-list":!1,action:"https://www.mocky.io/v2/5cc8019d300000980a055e76","before-upload":t.beforeUpload},on:{change:t.handleChange}},[t.imageUrl?a("img",{staticStyle:{height:"120px",width:"120px"},attrs:{src:t.imageUrl,alt:"avatar"}}):a("div",[a("a-icon",{attrs:{type:t.loading?"loading":"plus"}}),a("div",{staticClass:"ant-upload-text"},[t._v(" 上传 ")])],1)])],1)])]),a("br"),a("br"),a("div",{staticStyle:{"text-align":"center"}},[a("a-button",{staticStyle:{"text-align":"center"},attrs:{type:"primary"},on:{click:t.save_circle}},[t._v(" 保存 ")]),a("br"),a("br")],1)])},o=[],s=a("5530"),r=a("93d6"),n=a("5880"),c=[],l=["中国医学科学院北京协和医院","四川大学华西医院","中国人民解放军总医院","上海交通大学医学院附属瑞金医院","复旦大学附属中山医院","中山大学附属第一医院","华中科技大学同济医学院","空军军医大学西京医院","复旦大学附属华山医院","华中科技大学同济医学院"],d=["急诊科","内科","外科","妇产科","儿科","中医科","耳鼻喉科","口腔科","眼科","皮肤科","麻醉科","康复科","预防保健科"],h=l,p=d;function u(t,e){var a=new FileReader;a.addEventListener("load",(function(){return e(a.result)})),a.readAsDataURL(t)}var f={data:function(){return{doc_Name:"",doc_Comment:"",doc_Age:"",hos_out:[],dep_out:[],hos_select:0,depart_select:0,percent:0,hospitals:l,departments:d,loading:!1,imageUrl:"",year:"",year_string:""}},computed:Object(s["a"])({},Object(n["mapGetters"])("account",["user","id"])),methods:{emitEmpty_Name:function(){this.$refs.doc_NameInput.focus(),this.doc_Name=""},increase:function(){var t=this.percent+10;t>100&&(t=100),this.percent=t},decline:function(){var t=this.percent-10;t<0&&(t=0),this.percent=t},save_circle:function(){this.percent=0,""!=this.doc_Name&&(this.percent+=20),""!=this.doc_Age&&(this.percent+=20),""!=this.doc_Comment&&(this.percent+=20),1==this.hos_select&&(this.percent+=20),1==this.depart_select&&(this.percent+=20),Object(r["c"])(this.id,this.doc_Name,this.doc_Age,this.hos_out,this.dep_out,null,this.doc_Comment,1).then((function(t){console.log(t)}))},handleHospitalChange:function(t){if(console.log(t),0==t.length)return this.hos_select=0,this.doctorData=c,void(h=l);this.hos_select=1,this.hos_out=t,this.doctorData=[],h=t;for(var e=0;e<c.length;e++)-1!=h.indexOf(c[e]["hospital"])&&-1!=p.indexOf(c[e]["department"])&&this.doctorData.push(c[e]);console.log(this.doctorData)},handleDepartmentChange:function(t){if(console.log(t),0==t.length)return this.depart_select=0,p=d,void(this.doctorData=c);this.depart_select=1,this.dep_out=t,this.doctorData=[],p=t;for(var e=0;e<c.length;e++)-1!=h.indexOf(c[e]["hospital"])&&-1!=p.indexOf(c[e]["department"])&&this.doctorData.push(c[e]);console.log(this.doctorData)},handleChange:function(t){var e=this;"uploading"!==t.file.status?(t.file.status,u(t.file.originFileObj,(function(t){e.imageUrl=t,e.loading=!1}))):this.loading=!0},beforeUpload:function(t){var e="image/jpeg"===t.type||"image/png"===t.type;e||this.$message.error("You can only upload JPG file!");var a=t.size/1024/1024<2;return a||this.$message.error("Image must smaller than 2MB!"),e&&a},onChange:function(t,e){this.year=t,this.doc_Age=e}}},m=f,g=(a("1c2d8"),a("2877")),_=Object(g["a"])(m,i,o,!1,null,null,null);e["default"]=_.exports}}]);