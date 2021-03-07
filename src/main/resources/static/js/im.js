$(document).ready(function() {
    if (typeof (tio) == "undefined") {
        tio = {};
    }
    tio.ws = {};
    tio.ws = function ($, layim) {
        // 心跳超时时间，单位：毫秒
        this.heartbeatTimeout = 20000;
        this.heartbeatSendInterval = this.heartbeatTimeout / 2;

        const self = this;

        // 建立连接
        this.connect = function () {
            let url = "wss://api.hcworld.xyz?userId=" + self.userId
            console.log(url)
            let socket = new WebSocket(url)
            self.socket = socket
            socket.onopen = function () {
                console.log("--------tio ws 启动---------")
                self.lastInteractionTime(new Date().getTime())
                self.ping()
            }

            socket.onmessage = function (res) {
                console.log("--------tio ws 接受到消息---------")
                // console.log(res)
                let msgBody = eval('(' + res.data + ')')
                if (msgBody.emit === 'chatMessage') {
                    layim.getMessage(msgBody.data)
                }
                self.lastInteractionTime(new Date().getTime())
            }
            socket.onclose = function () {
                console.log("--------tio ws 关闭---------")
                //尝试重连
                self.reconn();
            }
        }
        // 获取个人、群聊信息并打开聊天窗口
        this.openChatWindow = function () {

            //获取个人信息
            $.ajax({
                url: "/chat/getMineAndGroupData",
                async: false,
                success: function (res) {
                    self.group = res.data.group;
                    self.mine = res.data.mine;
                    self.userId = self.mine.id;
                }
            });
            let cache = layui.layim.cache()
            cache.mine = self.mine
            //打开窗口
            layim.chat(self.group)
            //收缩聊天面板
            layim.setChatMin()
        }
        //发送消息
        this.sendMessage = function () {
            layim.on('sendMessage', function (res) {
                self.socket.send(JSON.stringify({
                    type: 'chatMessage'
                    , data: res
                }));
            })

        }
        //历史消息
        this.initHistoryMess = function () {
            localStorage.clear();
            $.ajax({
                url: '/chat/getGroupHistoryMsg',
                success: function (res) {
                    let data = res.data;
                    if (data.length < 1) {
                        return
                    }
                    for (let i in data) {
                        layim.getMessage(data[i])
                    }
                }
            })
        }

        //-----------重试机制---------------
        this.lastInteractionTime = function () {
            // debugger;
            if (arguments.length === 1) {
                this.lastInteractionTimeValue = arguments[0]
            }
            return this.lastInteractionTimeValue
        }

        this.ping = function () {
            //建立一个定时器，定时心跳
            self.pingIntervalId = setInterval(function () {
                let iv = new Date().getTime() - self.lastInteractionTime(); // 已经多久没发消息了

                // debugger;

                // 单位：秒
                if ((self.heartbeatSendInterval + iv) >= self.heartbeatTimeout) {
                    self.socket.send(JSON.stringify({
                        type: 'pingMessage'
                        , data: 'ping'
                    }))
                }
            }, self.heartbeatSendInterval)
        };

        this.reconn = function () {
            // 先删除心跳定时器
            clearInterval(self.pingIntervalId);
            // 然后尝试重连
            self.connect();
        };
    }

})