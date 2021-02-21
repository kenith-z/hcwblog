$(document).ready(function(){
    layui.use('layim', function (layim) {
    const $ = layui.jquery;

    layim.config({
        brief: true //是否简约模式（如果true则不显示主面板）
        ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html'
    })

    const tioWs = new tio.ws($, layim)

    // 获取个人、群聊信息并打开聊天窗口
    tioWs.openChatWindow()
    // 历史聊天信息回显
    tioWs.initHistoryMess();
    // 建立ws连接
    tioWs.connect()

    // 发送消息
    tioWs.sendMessage()


})
})