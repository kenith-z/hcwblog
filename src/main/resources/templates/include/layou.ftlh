<#macro layout title>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="utf-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="Shortcut Icon" href="/images/logo.png" type="image/x-icon"/>
        <meta name="keywords" content="java,java博客,SpringBoot,后端,java技术交流,java开发,java学习,java编程">
        <meta name="description" content="hcwblog是张红尘的java技术博客">
        <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css"/>
        <link rel="stylesheet" href="/css/global.css">
        <link rel="stylesheet" href="/css/my.css">
        <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
        <script src="/layui.js"></script>
        <script src="/js/sockjs.min.js"></script>
        <script src="/js/stomp.js"></script>


    </head>
    <#include "/include/headher.ftlh">
    <#include "/include/common.ftlh">
    <#nested >
    <#include "/include/footer.ftlh">

    <script>
        // layui.cache.page = '';
        layui.cache.user = {
            <#if profile??>
            username: '${profile.username!"游客"}'
            , uid: ${profile.id!-1}
            , avatar: '${profile.avatar!"/images/avatar/default.png"}'
            , experience: 83
            , sex: '${profile.sex!"保密"}'
            <#else>
            username: '游客'
            , uid: -1
            , avatar: '/images/avatar/default.png'
            , experience: 83
            , sex: '保密'
            </#if>
        };
        layui.config({
            version: "3.0.0"
            , base: '/js/'
        }).extend({
            fly: 'index'
        }).use('fly');
    </script>
    <#if profile??>
        <script>
            layui.data('userId', {
                key: 'userId'
                ,value: ${profile.id!-1}
            })
            if (!/windows phone|iphone|android|iPad|iPod|iOS|SymbianOS/ig.test(window.navigator.userAgent)) {
                $.getScript('/js/im.js');$.getScript('/js/chat.js');
            }

            function showTips(count) {
                let msg = $('<a class="fly-nav-msg" href="javascript:;">' + count + '</a>');
                let elemUser = $('.fly-nav-user');
                elemUser.append(msg);
                msg.on('click', function () {
                    location.href = "/user/message"
                });
                layer.tips('你有 ' + count + ' 条未读消息', msg, {
                    tips: 3
                    , tipsMore: true
                    , fixed: true
                });
                msg.on('mouseenter', function () {
                    layer.closeAll('tips');
                })
            }

            $(function () {
                var elemUser = $('.fly-nav-user');
                if (layui.cache.user.uid !== -1 && elemUser[0]) {
                    var socket = new SockJS("/websocket")
                    stompClient = Stomp.over(socket);
                    stompClient.connect({}, function (frame) {
                        stompClient.subscribe("/user/" + ${profile.id} + "/messCount", function (res) {

                            // 弹窗
                            showTips(res.body);
                        })
                    });
                }
            });
        </script>
    </#if>

    </body>
    </html>
</#macro>