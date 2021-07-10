### 1. 用户类测试

#### 1.1获取用户基本信息

url   /user/getInfo?id=""

数据包括：

```json
"id":
"username":
"birthday": #出生年月日
"picture": #头像
```

#### 1.1.1修改基本信息

url  /user/changeInfo

```json
"id":
"username":
"birthday": #出生年月日
"picture": #头像
```



#### 1.2获取用户健康信息

url  /user/getHealth?id=""

前端需要一个list，每个item包括：

```json
"item":{
    "id"  #记录本身的id
	"date":
	"height":
	"weight":
	"highpressure":
    "lowpressure":
	"lung":
}
```

#### 1.2.1 上传用户健康信息

 url   /user/addHealth

json数据格式

```json
"uid":  用户id
"date":
"height":
"weight":
"bloodpressure":
"lung":
```

#### 1.2.2删除健康记录

url  /user/deleteHealth

json数据格式

```
"id": 记录本身id
```

#### 1.2.3修改健康记录

url  /user/changeHealth

json数据格式

```
"id":  记录本身id
"date":
"height":
"weight":
"bloodpressure":
"lung":
```







#### 1.3获取医生信息

url  /user/getDoctor

返回所有医生的信息，数据过多可设上限

```json
"doctor":{
    "id":
    "age":
	"username":
	"hospital":
	"office":	#科室
	"headimg": 	#头像
	"comment":	#介绍
}
```

#### 1.4获取病历信息

url   /user/getMedical?id=""

返回该病人所有病历信息

```json
"medical":{
    "id":#病人id
	"createTime":
	"doctor":
	"diagnosis":
	"prescription":
	"did":#医生id
}
```

prescription是处方信息，是一个list，每个item包括：

```json
"id":上传时传null
"userid":
"recordid":上传时传null
"medicine": #药品名
"description":	#用法用量说明，字符串格式
```

### 2.医生类测试

#### 2.1获取医生基本信息

url  /doctor/getInfo?id=""

数据包括：

```json
"id":
"username":
"age":
"hospital":
"office":	#科室
"headimg": 	#头像
"comment":	#介绍
"state":
```

#### 2.1.1修改基本信息

url  /doctor/changeInfo

```json
"id":
"username":
"age":
"hospital":
"office":	#科室
"headimg": 	#头像
"comment":	#介绍
"state":
```



#### 2.2获取病人信息

url   /doctor/getPatient?id=""

数据包括 每次就诊记录：

```json
"createTime":
"patient":
"state":	#就诊状态信息：待就诊（已挂号）、就诊中、就诊完成
"diagnosis":
"prescription":	#处方信息，同上
"id":病人id
"rid":记录本身id
```

#### 2.2.1增加病历（医生给病人增加病历，所以动作发出方是医生，这个现在改为用户调用）

url  /user/addMedical

```json
"id":#病人id
"createTime":
"doctor":
"diagnosis":
"prescription":
"did":#医生id
增加 "rid":处方id
```

#### 2.2.2医生修改病历

url  /doctor/changeMedical

```
"id":#病人id
"createTime":
"doctor":
"diagnosis":
"prescription":
"did":#医生id
增加 "rid":处方id
```





### 3.聊天室测试

#### 3.1获取好友列表

url   /chat/getFriend?id=""

（用户、医生之间通过挂号绑定好友关系，用户和用户之间、医生与医生之间的好友关系暂时不做）

数据包括好友list：

```json
"id":
"name":
"picture":
```

#### 3.2获取历史聊天记录

  url   /chat/getRecord?id=""&toid="" (id:我方id, toid:对方id)

每条记录包括：

```json
"id": #这条记录谁发出的
"info": #聊天内容
"time": #从年精确到秒
```



