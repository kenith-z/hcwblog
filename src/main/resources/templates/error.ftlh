<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>错误提示</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="Shortcut Icon" href="/images/logo.png" type="image/x-icon" />
  <meta name="keywords" content="java,java博客,SpringBoot,后端,java技术交流,java开发,java学习,java编程">
  <meta name="description" content="hcwblog是张红尘的java技术博客">
  <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css"/>
  <link rel="stylesheet" href="/css/global.css">
  <link rel="stylesheet" href="/css/my.css">
  <script src="/js/sockjs.min.js"></script>
  <script src="/js/stomp.js"></script>
  <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
  <script src="https://www.layuicdn.com/layui/layui.js"></script>
</head>
<div class="fly-header layui-bg-black">
  <div class="layui-container">
    <a class="fly-logo" href="/">
      <img src="/images/logo.png" alt="hcwblogLogo" style="width: 4.5rem">
    </a>
    <ul class="layui-nav fly-nav layui-hide-xs">
      <li class="layui-nav-item layui-this">
        <a href="/"><i class="iconfont icon-jiaoliu"></i>交流</a>
      </li>
      <li class="layui-nav-item">
        <a href="case/case.html"><i class="iconfont icon-iconmingxinganli"></i>案例</a>
      </li>
      <li class="layui-nav-item">
        <a href="http://www.layui.com/" target="_blank"><i class="iconfont icon-ui"></i>框架</a>
      </li>
    </ul>
  </div>
</div>

<#include "/include/common.ftlh">
<div class="layui-container fly-marginTop">
  <div class="fly-panel">
    <div class="fly-none">
      <h2><i class="iconfont icon-tishilian"></i></h2>
      <p><#if errMessage??&&errMessage=='No message available' >页面不存在<#else>${errMessage!'页面不存在'}</#if><span style="display: block;font-size: 16px">${.now?string("HH:mm:ss")}</span></p>

    </div>
  </div>
</div>
<#include "/include/footer.ftlh">

<script>
   layui.cache.page = '';
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
    function  showTips(count){
      let msg = $('<a class="fly-nav-msg" href="javascript:;">'+ count +'</a>');
      let elemUser = $('.fly-nav-user');
      elemUser.append(msg);
      msg.on('click', function(){
        location.href = "/user/message"
      });
      layer.tips('你有 '+ count +' 条未读消息', msg, {
        tips: 3
        ,tipsMore: true
        ,fixed: true
      });
      msg.on('mouseenter', function(){
        layer.closeAll('tips');
      })
    }
    $(function () {
      var elemUser = $('.fly-nav-user');
      if(layui.cache.user.uid !== -1 && elemUser[0]){
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