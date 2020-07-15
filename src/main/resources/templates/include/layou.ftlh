<#macro layout title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="keywords" content="java,java博客,SpringBoot,后端,java技术交流,java开发,java学习,java编程">
    <meta name="description" content="hcwblog是张红尘的java技术博客">
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui/css/layui.css"/>
    <link rel="stylesheet" href="/css/global.css">
    <script src="https://www.layuicdn.com/layui/layui.js"></script>
</head>
<body>
    <#include "/include/headher.ftlh">
    <#include "/include/common.ftlh">
    <#nested >
<#include "/include/footer.ftlh">

<script>
    layui.cache.page = '';
    layui.cache.user = {
        username: '游客'
        , uid: -1
        , avatar: '/images/avatar/default.jpg'
        , experience: 83
        , sex: '男'
    };
    layui.config({
        version: "3.0.0"
        , base: '/js/'
    }).extend({
        fly: 'index'
    }).use('fly');
</script>



</body>
</html>
</#macro>