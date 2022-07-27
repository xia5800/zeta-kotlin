# WebSocket
zeta-kotlin项目已经配置好了基于Stomp协议的websocket

见`org.zetaframework.extra.websocket.WebsocketStompConfiguration`。注释已经完善了，看注释就行

## 案例(简易聊天室)
用户一的前端代码
```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>WebSocket测试-用户1</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/jquery@2.1.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <style>
        #response {
            background-color: #2b2b2b;
            height: 400px;
        }

        #response span {
            color: #fff;
            font-size: large;
        }
    </style>
</head>

<body class="container">
<!-- 上 -->
<div class="list-inline">
    <button id="connect" onclick="connect()" type="button" class="btn btn-primary">连接</button>
    <button id="disconnect" onclick="disconnect();" type="button" class="btn btn-secondary">断开连接</button>
    <button onclick="getUserInfo();" type="button" class="btn btn-info">获取个人信息</button>
</div>
<!-- 中 -->
<div class="card">
    <div class="card-header">私聊</div>
    <div class="card-body">
        <form action="" class="form-inline was-validated needs-validation" style="padding-bottom: 20px" novalidate>
            <div class="form-group">
                <label for="toUserId" class="mb-2 mt-2 mr-sm-2">私聊用户id:</label>
                <input id="toUserId" name="toUserId" value="2" class="form-control mb-2 mt-2 mr-sm-2"
                       placeholder="请输入私聊用户id" type="text" autocomplete="off" required/>
                <div class="valid-feedback">验证成功！</div>
                <div class="invalid-feedback">请输入私聊用户id！</div>
            </div>
            <div class="form-group">
                <label for="content" class="mb-2 mt-2 mr-sm-2">聊天内容:</label>
                <input id="content" name="content" value="听见你说~朝阳起又落！~" class="form-control mb-2 mt-2 mr-sm-2"
                       placeholder="请输入聊天内容" autocomplete="off" required/>
                <div class="valid-feedback">验证成功！</div>
                <div class="invalid-feedback">请输入聊天内容！</div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="sendOneToOne(event)">发送</button>
        </form>
    </div>
</div>
<div class="card">
    <div class="card-header">群发</div>
    <div class="card-body">
        <form action="" class="form-inline was-validated needs-validation" style="padding-bottom: 20px" novalidate>
            <div class="form-group">
                <label for="groupContent" class="mb-2 mt-2 mr-sm-2">群发内容:</label>
                <input id="groupContent" name="groupContent" value="听见你说~朝阳起又落！~" class="form-control mb-2 mt-2 mr-sm-2"
                       placeholder="请输入聊天内容" autocomplete="off" required/>
                <div class="valid-feedback">验证成功！</div>
                <div class="invalid-feedback">请输入群发内容！</div>
            </div>
            <button type="submit" class="btn btn-primary" onclick="sendOneToMore(event)">发送</button>
        </form>
    </div>
</div>
<!-- 下 -->
<div class="card">
    <div class="card-header">公屏</div>
    <div class="card-body">
        <div id="response"></p>
        </div>
    </div>

    <script type="text/javascript">
        var connectioned = false;
        var stompClient = null;

        /**
         * 连接
         */
        function connect() {
            // 网关websocket代理地址
            var socket = new SockJS('http://localhost:8080/ws');
            stompClient = Stomp.over(socket);
            // stompClient.debug = null; // 放开注释，关闭调试
            stompClient.connect({
                userId: "1"
            }, function (frame) {
                connectioned = true;
                console.log('Connected: ' + frame);
                $("#response").html("");
                $("#response").append(format("connect success"));

                // websocket订阅。【公屏】
                stompClient.subscribe('/topic/group', function (response) {
                    console.log("/topic/group => respnose:", response);
                    $("#response").append(format("收到了公屏信息>>>>> " + response.body));
                });

                // websocket订阅。【系统信息】
                stompClient.subscribe('/user/queue/message', function (response) {
                    console.log("/user/queue/message => respnose:", response);
                    $("#response").append(format("收到了系统信息>>>>> " + response.body));
                });

                // websocket订阅。【好友私聊信息】
                stompClient.subscribe('/user/queue/private', function (response) {
                    console.log("/user/queue/private => respnose:", response);
                    $("#response").append(format("收到了私聊信息>>>>> " + response.body));
                });

                // websocket订阅。【在线人数】。
                stompClient.subscribe('/onlineUserCount', function (response) {
                    // Q: 这里不加/topic 或 /queue为什么也能收到呢？
                    // A: 因为后端使用了@SubscribeMapping注解来监听"/onlineUserCount"地址的订阅，故而能收到消息
                    // 但是后端主动往"/onlineUserCount"地址推送消息（simpMessagingTemplate.convertAndSend("/onlineUserCount", 10)），客户端是收不到的
                    // 所以标准的写法是"/topic/onlineUserCount" 或 "/queue/onlineUserCount"
                    console.log("/onlineUserCount => respnose:", response.body);
                    $("#response").append(format("当前聊天室在线人数>>>>> " + response.body));
                });

                // websocket订阅。【获取自己的信息】
                stompClient.subscribe('/user/queue/info', function (response) {
                    console.log("/user/queue/info => respnose:",response.body);
                    alert(response.body);
                });

            }, function(error) {
                // 设备离线
                console.log(error);
                $("#response").append(format(error));
            });
        }


        /**
         * 断开连接
         */
        function disconnect() {
            if(!connectioned) {
                alert("还未连接websocket")
                return;
            }
            if (stompClient != null) {
                stompClient.disconnect();
            }
            $("#response").append(format("connect close"));
            connectioned = false;
        }

        /**
         * 发送群发消息
         */
        function sendOneToMore(event) {
            event.preventDefault();
            event.stopPropagation();
            if(!connectioned) {
                alert("还未连接websocket");
            }

            var message = $("#groupContent").val();
            stompClient.send("/group", {}, message)
        }

        /**
         * 发送私聊信息
         */
        function sendOneToOne(event) {
            event.preventDefault();
            event.stopPropagation();
            if(!connectioned) {
                alert("还未连接websocket");
                return;
            }

            var userId = $("#toUserId").val();
            var message = $("#content").val();
            stompClient.send("/private", {toUserId: userId}, message);
            $("#response").append(format("私聊信息已发送"));
        }

        /**
         * 获取个人信息
         */
        function getUserInfo() {
            if(!connectioned) {
                alert("还未连接websocket");
                return;
            }
            // send方法3个参数分别是("topic", 请求头，内容)
            stompClient.send("/info", {}, {})
        }

        function format(data) {
            let logDate = new Date().Format("yyyy-MM-dd HH:mm:ss");
            return `
			<div>
				<span style="color: #8aca3f;">${logDate}</span>
				<span style="margin-left: 10px">${data}<span>
			</div>
			`
        }
        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "H+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds() //秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }
    </script>
</body>
</html>
```

用户二的前端代码
```html
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport"
		  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>WebSocket测试-用户2</title>
	<link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/jquery@2.1.4/dist/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
	<script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<style>
        #response {
            background-color: #2b2b2b;
            height: 400px;
        }

        #response span {
            color: #fff;
            font-size: large;
        }
    </style>
</head>

<body class="container">
	<!-- 上 -->
	<div class="list-inline">
		<button id="connect" onclick="connect()" type="button" class="btn btn-primary">连接</button>
		<button id="disconnect" onclick="disconnect();" type="button" class="btn btn-secondary">断开连接</button>
		<button onclick="getUserInfo();" type="button" class="btn btn-info">获取个人信息</button>
	</div>
	<!-- 中 -->
	<div class="card">
		<div class="card-header">私聊</div>
		<div class="card-body">
			<form action="" class="form-inline was-validated needs-validation" style="padding-bottom: 20px" novalidate>
				<div class="form-group">
					<label for="toUserId" class="mb-2 mt-2 mr-sm-2">私聊用户id:</label>
					<input id="toUserId" name="toUserId" value="2" class="form-control mb-2 mt-2 mr-sm-2"
						   placeholder="请输入私聊用户id" type="text" autocomplete="off" required/>
					<div class="valid-feedback">验证成功！</div>
					<div class="invalid-feedback">请输入私聊用户id！</div>
				</div>
				<div class="form-group">
					<label for="content" class="mb-2 mt-2 mr-sm-2">聊天内容:</label>
					<input id="content" name="content" value="听见你说~朝阳起又落！~" class="form-control mb-2 mt-2 mr-sm-2"
						   placeholder="请输入聊天内容" autocomplete="off" required/>
					<div class="valid-feedback">验证成功！</div>
					<div class="invalid-feedback">请输入聊天内容！</div>
				</div>
				<button type="submit" class="btn btn-primary" onclick="sendOneToOne(event)">发送</button>
			</form>
		</div>
	</div>
	<div class="card">
		<div class="card-header">群发</div>
		<div class="card-body">
			<form action="" class="form-inline was-validated needs-validation" style="padding-bottom: 20px" novalidate>
				<div class="form-group">
					<label for="groupContent" class="mb-2 mt-2 mr-sm-2">群发内容:</label>
					<input id="groupContent" name="groupContent" value="听见你说~朝阳起又落！~" class="form-control mb-2 mt-2 mr-sm-2"
						   placeholder="请输入聊天内容" autocomplete="off" required/>
					<div class="valid-feedback">验证成功！</div>
					<div class="invalid-feedback">请输入群发内容！</div>
				</div>
				<button type="submit" class="btn btn-primary" onclick="sendOneToMore(event)">发送</button>
			</form>
		</div>
	</div>
	<!-- 下 -->
	<div class="card">
		<div class="card-header">公屏</div>
		<div class="card-body">
			<div id="response"></p>
		</div>
	</div>

	<script type="text/javascript">
		var connectioned = false;
		var stompClient = null;

		/**
		 * 连接
		 */
		function connect() {
			// 网关websocket代理地址
			var socket = new SockJS('http://localhost:8080/ws'); 
			stompClient = Stomp.over(socket);
			// stompClient.debug = null; // 放开注释，关闭调试
			stompClient.connect({
				userId: "2"
			}, function (frame) {
				connectioned = true;
				console.log('Connected: ' + frame);
				$("#response").html("");
				$("#response").append(format("connect success"));

				// websocket订阅。【公屏】
				stompClient.subscribe('/topic/group', function (response) {
					console.log("/topic/group => respnose:", response);
					$("#response").append(format("收到了公屏信息>>>>> " + response.body));
				});

				// websocket订阅。【系统信息】
				stompClient.subscribe('/user/queue/message', function (response) {
					console.log("/user/queue/message => respnose:", response);
					$("#response").append(format("收到了系统信息>>>>> " + response.body));
				});

				// websocket订阅。【好友私聊信息】
				stompClient.subscribe('/user/queue/private', function (response) {
					console.log("/user/queue/private => respnose:", response);
					$("#response").append(format("收到了私聊信息>>>>> " + response.body));
				});

				// websocket订阅。【在线人数】。
				stompClient.subscribe('/onlineUserCount', function (response) {
					// Q: 这里不加/topic 或 /queue为什么也能收到呢？
					// A: 因为后端使用了@SubscribeMapping注解来监听"/onlineUserCount"地址的订阅，故而能收到消息
					// 但是后端主动往"/onlineUserCount"地址推送消息（simpMessagingTemplate.convertAndSend("/onlineUserCount", 10)），客户端是收不到的
					// 所以标准的写法是"/topic/onlineUserCount" 或 "/queue/onlineUserCount"
					console.log("/onlineUserCount => respnose:", response.body);
					$("#response").append(format("当前聊天室在线人数>>>>> " + response.body));
				});

				// websocket订阅。【获取自己的信息】
				stompClient.subscribe('/user/queue/info', function (response) {
					console.log("/user/queue/info => respnose:",response.body);
					alert(response.body);
				});

			}, function(error) {
				// 设备离线
				console.log(error);
				$("#response").append(format(error));
			});
		}


		/**
		 * 断开连接
		 */
		function disconnect() {
			if(!connectioned) {
				alert("还未连接websocket")
				return;
			}
			if (stompClient != null) {
				stompClient.disconnect();
			}
			$("#response").append(format("connect close"));
			connectioned = false;
		}

		/**
		 * 发送群发消息
		 */
		function sendOneToMore(event) {
			event.preventDefault();
			event.stopPropagation();
			if(!connectioned) {
				alert("还未连接websocket");
			}

			var message = $("#groupContent").val();
			stompClient.send("/group", {}, message)
		}

		/**
		 * 发送私聊信息
		 */
		function sendOneToOne(event) {
			event.preventDefault();
			event.stopPropagation();
			if(!connectioned) {
				alert("还未连接websocket");
				return;
			}

			var userId = $("#toUserId").val();
			var message = $("#content").val();
			stompClient.send("/private", {toUserId: userId}, message);
			$("#response").append(format("私聊信息已发送"));
		}

		/**
		 * 获取个人信息
		 */
		function getUserInfo() {
			if(!connectioned) {
				alert("还未连接websocket");
				return;
			}
			// send方法3个参数分别是("topic", 请求头，内容)
			stompClient.send("/info", {}, {})
		}

		function format(data) {
			let logDate = new Date().Format("yyyy-MM-dd HH:mm:ss");
			return `
			<div>
				<span style="color: #8aca3f;">${logDate}</span>
				<span style="margin-left: 10px">${data}<span>
			</div>
			`
		}
		Date.prototype.Format = function (fmt) { //author: meizz
			var o = {
				"M+": this.getMonth() + 1, //月份
				"d+": this.getDate(), //日
				"H+": this.getHours(), //小时
				"m+": this.getMinutes(), //分
				"s+": this.getSeconds() //秒
			};
			if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o)
				if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
		}
	</script>
</body>
</html>
```


聊天室功能涉及到的类有：
```
/** 业务包 */
// 聊天室
com.zeta.msg.controller.ChartController

/** zetaframework包 */
// Websocket配置
org.zetaframework.extra.websocket.WebsocketStompConfiguration
// Websocket用户消息 拦截器
org.zetaframework.extra.websocket.interceptor.WsUserInterceptor
```
