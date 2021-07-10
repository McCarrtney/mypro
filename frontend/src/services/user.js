// import {LOGIN, ROUTES, REGISTER} from '@/services/api'
import {request, METHOD, removeAuthorization} from '@/utils/request'
// import axios from 'axios'

const BASE_URL = process.env.NODE_ENV === 'production' ? "http://localhost:8080" : process.env.VUE_APP_API_BASE_URL
const LOGIN = process.env.NODE_ENV === 'production' ? `${BASE_URL}/user/login` : `${BASE_URL}/login`
const REGISTER = `${BASE_URL}/user/register`
const GETHEALTHINFO = `${BASE_URL}/user/getHealth`
const ADDHEALTHINFO = `${BASE_URL}/user/addHealth`
const CHANGEHEALTHINFO = `${BASE_URL}/user/changeHealth`
const DELETEHEALTHINFO = `${BASE_URL}/user/deleteHealth`
const GETUSERINFO = `${BASE_URL}/user/getInfo`
const GETDOCTOR = `${BASE_URL}/user/getDoctor`
const ADDMEDICAL = `${BASE_URL}/user/addMedical`
const GETMEDICAL = `${BASE_URL}/user/getMedical`
const USERCHANGEINFO = `${BASE_URL}/user/changeInfo`

const DOCTORCHANGEINFO = `${BASE_URL}/doctor/changeInfo`
const DOCTORGETINFO = `${BASE_URL}/doctor/getInfo`
const GETPATIENT = `${BASE_URL}/doctor/getPatient`
const CHANGEMEDICAL = `${BASE_URL}/doctor/changeMedical`
const ROUTES = `${BASE_URL}/routes`

const CREATESESSION = `${BASE_URL}/createSession`
const DELETESESSION = `${BASE_URL}/delSession`
const SESSIONLISTALREADY = `${BASE_URL}/sessionList/already`
const GETMSG = `${BASE_URL}/chat/records`

/**
 * 登录服务
 * @param name 账户名
 * @param password 账户密码
 * @returns {Promise<AxiosResponse<T>>}
 */
 export async function register(phone, password, name, role) {
  return request(REGISTER, METHOD.POST, {
    phone: phone,
    password: password,
    username: name,
    role: role
  },
  // {
  //   transformRequest:[function (data1,headers){
  //     //这个请求不需要请求头(在此处删除，上方设置的请求头。)
  //     if(headers.common.Authorization){
  //      delete headers.common.Authorization;
  //     }
  //     return data1;
  //   }]
  // }
  )
}

export async function login(phone, password) {
  return request(LOGIN, METHOD.POST, {
    phone: phone,
    password: password,
    username: null,
    role: null
  },
  // {
  //   transformRequest:[function (data1,headers){
  //     //这个请求不需要请求头(在此处删除，上方设置的请求头。)
  //     if(headers.common.Authorization){
  //      delete headers.common.Authorization;
  //     }
  //     return data1;
  //   }]
  // })
  )
}

export async function getHealth(phone) {
  return request(GETHEALTHINFO, METHOD.GET, {
    phone: phone,
  })
}

export async function addHealth(date, height, weight, hp, lp, lung, uid) {
  return request(ADDHEALTHINFO, METHOD.POST, {
    date: date,
    height: height,
    weight: weight,
    highpressure: hp,
    lowpressure: lp,
    lung: lung,
    uid: uid,
  })
}

export async function changeHealth(date, height, weight, hp, lp, lung, uid) {
  return request(CHANGEHEALTHINFO, METHOD.POST, {
    date: date,
    height: height,
    weight: weight,
    highpressure: hp,
    lowpressure: lp,
    lung: lung,
    uid: uid,
  })
}

export async function deleteHealth(uid) {
  return request(DELETEHEALTHINFO, METHOD.POST, {
    uid: uid,
  })
}

export async function getUserInfo(phone) {
  return request(GETUSERINFO, METHOD.GET, {
    phone: phone,
  })
}

export async function changeUserInfo(id, name, date) {
  return request(USERCHANGEINFO, METHOD.POST, {
    id: id,
    username: name,
    birthday: date,
    picture: null
  })
}

export async function getDoctor(phone) {
  return request(GETDOCTOR, METHOD.GET, {
    phone: phone
  })
}

export async function addMedical(id, time, doctor, diag, pres, did) {
  return request(ADDMEDICAL, METHOD.POST, {
    id: id,
    createTime: time,
    doctor: doctor,
    diagnosis: diag,
    prescriptions: pres,
    did: did,
    rid: null
  })
}

export async function getMedical(phone) {
  return request(GETMEDICAL, METHOD.GET, {
    phone: phone
  })
}



///doctor
export async function getDoctorInfo(phone) {
  return request(DOCTORGETINFO, METHOD.GET, {
    phone: phone,
  })
}

export async function getRoutesConfig() {
  return request(ROUTES, METHOD.GET)
}

export async function changeDoctorInfo(id, name, age, hospital, office, headimg, comment, state) {
  return request(DOCTORCHANGEINFO, METHOD.POST, {
   id: id,
   username: name,
   age: age,
   hospital: hospital,
   office: office,
   headimg: headimg,
   comment: comment,
   state: state
  })
}

export async function getPatient(phone) {
  return request(GETPATIENT, METHOD.GET, {
    phone: phone,
  })
}

export async function changeMedical(time, diag, pres, rid) {
  return request(CHANGEMEDICAL, METHOD.POST, {
    id: null,
    createTime: time,
    doctor: null,
    diagnosis: diag,
    prescriptions: pres,
    did: null,
    rid: rid
  })
}

//session
export async function createSession(phone1, phone2) {
  return request(CREATESESSION, METHOD.GET, {
    phone1: phone1,
    phone2: phone2,
  })
}

export async function deleteSession(phone1, phone2) {
  return request(DELETESESSION, METHOD.GET, {
    phone1: phone1,
    phone2: phone2,
  })
}

export async function getSessionList(phone) {
  return request(SESSIONLISTALREADY, METHOD.GET, {
    phone: phone
  })
}

export async function getMsgList(phone1, phone2) {
  return request(GETMSG, METHOD.GET, {
    phone1: phone1,
    phone2: phone2
  })
}

/**
 * 退出登录
 */
export function logout() {
  localStorage.removeItem(process.env.VUE_APP_ROUTES_KEY)
  localStorage.removeItem(process.env.VUE_APP_PERMISSIONS_KEY)
  localStorage.removeItem(process.env.VUE_APP_ROLES_KEY)
  removeAuthorization()
}
export default {
  login,
  logout,
  getRoutesConfig
}
