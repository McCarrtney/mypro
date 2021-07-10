<template>
	<div class="main1">	
		<div class="main_down1">
			<div class="left">
				<div class="left_up">
					<div class="label_title">
						已建立会话
					</div>
					<div :class="curSessionId == item.id ? 'box_select' : 'box'" v-for="item in sessionList_already" :key="item.id">
						<div class="right_left_del">
							<a-popconfirm title="确认断开连接？" ok-text="确认" cancel-text="取消" @confirm="delSession(item.phone)">
								<a-icon type="close-circle"/>
							</a-popconfirm>
							<!-- <i class="a-icon-close" @click="delSession(item.id)"></i> -->
						</div>
						<div class="box_left"  @click="startSession(item.phone, item.username)">
							{{item.username}}
						</div>
						<div class="right_left">
							<div class="right_left_count">
								{{item.unread}}
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="right">
				<div class="right_top_user">
					{{curToName}}
				</div>
				<div class="up" ref="element" id="msg_end">
					<div v-for="(item,i) in list" :key="i" :class="item.fromUserId === curUserId ? 'msg_right' : 'msg_left'">
						<div class="msg_right_up">
							{{item.fromUserId === curUserId ? curUserName : curToName}}
						</div>
						<div :class="item.fromUserId === curUserId ? 'msg_right_down' : 'msg_left_down'">
							{{item.content}}
						</div>
					</div>
				</div>
				<div class="down">
					<a-textarea
					type="textarea"
					:rows="9"
					placeholder="请输入内容，回车发送！"
					@keyup.enter.native = "sendMsg"
					v-model="textarea"
					style="width: 100%;"
					/>
				</div>
			</div>
		</div>
  </div>
</template>

<script>
import {getSessionList, getMsgList, deleteSession} from '@/services/user'
import {mapGetters} from 'vuex'

// 对方昵称
var chatUser = "小王";
// 我的昵称
var myUser = "小夏";

export default {
	name: 'talkSystem',
	data(){
		return{
			chatUser,
			myUser,
			dialogVisible: false,
			dialogTitle: '',
			loginName: '',
			textarea: "",
			list: [],
			curUserId: "",
			curUserName: "",
			// curSessionId: '',
			curToID: "",  //phone
			curToName: "",
			sessionList_already: [],
			// sessionList_already:[{"id": 1, "listName": "129.18.121.1", "unReadCount": 12},],
			// sessionList_not:[],
		}
	},
	computed: {
      ...mapGetters('account', ['user', 'id']),
    },  
    mounted() {
		let that = this;
		that.curUserId = this.user;  //phone
		console.log("this.user")
		console.log(this.user)
		that.dialogVisible = false
		that.sessionListAlready()
    }, 
	updated(){
		// 解决每次发消息到最后面
		var elmnt = document.getElementById("msg_end");
		elmnt.scrollTop = elmnt.scrollHeight;
	},   
	destroyed: function () { // 离开页面生命周期函数              
		this.websocketclose();        
	},
	methods: {            
		initWebSocket: function (phone1, phone2) {                
			// WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https                
			// this.websock = new WebSocket("ws://127.0.0.1:1997/websocket/"+userId+"/"+sessionId);  
			this.websock = new WebSocket("ws://localhost:8080/websocket/"+phone1+"/"+phone2) 
			console.log(1111)             
			this.websock.onopen = this.websocketonopen;                
			this.websock.onerror = this.websocketonerror;                
			this.websock.onmessage = this.websocketonmessage;                
			this.websock.onclose = this.websocketclose;
			console.log(2222)
		},              
		websocketonopen: function () {                
			console.log("WebSocket连接成功");    
		},              
		websocketonerror: function (e) { 
			console.log("WebSocket连接发生错误",e);              
		},              
		websocketonmessage: function (e) {  
			let data = JSON.parse(e.data);
			if(data instanceof Array){
				// 列表数据
				this.sessionList_already = data
			}else{
				// 消息数据
				this.list.push(data)
			}
		},              
		websocketclose: function () {
			if(this.websock != undefined){
				this.websock.close()
			}          
		},
		// 消息发送
		sendMsg(){
			if(this.curToID == ''){
				return this.$message.error("请选择左边的对话框开始聊天!")
			}
			let data = {
				"fromUserId": this.curUserId,
				"fromUserName": this.curUserName,
				"content": this.textarea
			}
			this.list.push(data)
			this.websock.send(this.textarea)
			this.textarea = ""
		},
		openDialog(openName){
			this.dialogTitle = openName
			this.dialogVisible = true
		},
		sessionListAlready(){
			let that = this
			getSessionList(this.curUserId).then( res => {
				that.sessionList_already = res.data.result
			})
		},
		startSession(phone, name){
			console.log(this.websock);
			if(this.websock != undefined){
				this.websock.close()
			}
			this.curToID = phone
			this.curToName = name
			this.msgList()
			this.initWebSocket(this.curUserId, phone)
		},
		// 删除会话
		delSession(phone){
			if(this.curToID == phone){
				this.websock.close()
				this.curToID = ""
				this.curToName = ""
			}
			deleteSession(this.curUserId, phone).then( res => {
					console.log(res)
				})
			this.sessionListAlready()
		},
		// 获取消息数据
		msgList(){
			let that = this
			getMsgList(this.curUserId, this.curToID).then( res => {
				console.log(res)
				that.list = []
				for(let i=0; i<res.data.result.length;i++){
					let item = res.data.result[i]
					if(that.curUserName==""){
						if(item.fromUserPhone==that.curUserId){
							that.curUserName = item.fromUserName
						}else{
							that.curUserName = item.toUserName
						}
					}
					console.log(item.fromUserId)
					console.log(that.curRealID)
					console.log(that.user)
					let data = {
						"fromUserId": item.fromUserPhone,
						"fromUserName": item.fromUserName,
						"content": item.content
					}
					that.list.push(data)
				}
			})
		}
	}    
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	.main1{
        margin-top: 3%;
        background: white;
        margin-left: 10%;
		width: 80%;
		/* border: 1px #1890ff solid; */
		height: 600px;
        border-radius: 15px;
	}
	.main_down1{
		width: 100%;
		height: 100%;
		display: flex;
		justify-self: space-between;
	}

	.left{
		width: 25%;
		height: 100%;
        background:rgb(247, 247, 247);
        border-radius: 15px 0 0 15px;
	}

	.left_up{
		width: 100%;
		height: 600px;
		overflow-y: auto;
		background-color:rgb(234, 234, 234);
        border-radius: 15px 0 0 15px;
		/* border: 1px red solid; */
	}
	.label_title{
		width: 100%;
		height: 25px;
		font-weight: 600;
        font-size: 17px;
        padding: 15px;
        padding-bottom: 30px;
	}
	.left_down{
		width: 100%;
		height: 550px;
		overflow-y: auto;
        /* background:yellow; */
        border-radius: 0 0 0 15px;
		background-color: rgb(247, 247, 247);
		/* border: 1px green solid; */
	}
	.right{
		width: 75%;
		height: 100%;
		/* border-right: 1px #1890ff solid; */
	}
	.box{
		width: 100%;
		height: 50px;
        /* margin-top: 5px; */
		display: flex;
		justify-self: flex-end;
        /* background-color: aliceblue; */
        line-height: 50px;
        font-size: 15px;
		/* border: 1px red solid; */
	}
	.box_select{
		width: 100%;
		height: 50px;
        /* margin-top: 5px; */
		display: flex;
		justify-self: flex-end;
        background-color:darkgrey;
        line-height: 50px;
        font-size: 15px;
		/* border: 1px red solid; */
	}
	.box_left{
		width: 100%;
		height: 100%;
        margin-left: 15px;
	}
	.right_left{
		width: 50px;
		height: 22px;
		display: flex;
		justify-content: space-between;
		/* border: 1px red solid; */
	}
	.right_left_count{
		/* border: 1px blue solid; */
		padding-left: 10px;
		margin-right: 10px;
		width: 20px;
		height: 20px;
	}
	.right_left_del{
		width: 20px;
		padding-left: 15px;
	}
	.up{
		width: 100%;
		height: 350px;
		overflow-y: scroll;
		overflow-x: hidden;
		/* padding-bottom: 40px; */
		/* border-bottom: 1px #1890ff solid; */
	}
	.msg_left{
		width: 100%;
		margin-top: 5px;
        margin-left: 20px;
	}
	.msg_left_up{
		height: 25px;
	}
	.msg_left_down{
		height: 25px;
		/* border: 1px #1890ff solid; */
		padding-left: 25px;
	}
	.msg_right{
		width: 100%;
		margin-top: 5px;
        margin-right: 20px;
		text-align: right;
	}
	.msg_right_up{
		height: 20px;
		padding-top: 5px;
		padding-right: 25px;
		font-size: 13px;
		color: silver;
	}
	.msg_right_down{
		height: 25px;
		/* border: 1px #1890ff solid; */
		padding-right: 25px;
	}
	.down{
		margin-left: 0px;
        margin-right: 0px;
		height: 100%;
		width: 100%;
		border-radius: 0 0 0 15px;
		/* border: 1px red solid; */
	}
	.right_top_user {
		top: 0;
		right: 0;
		width: 100%;
		height: 50px;
		border: rgb(224, 224, 224) solid;
		border-width: 0 0 1px 0;
		font-size: 19px;
		font-weight: 700;
		vertical-align: center;
		line-height: 50px;
		padding-left: 20px;
	}
</style>

