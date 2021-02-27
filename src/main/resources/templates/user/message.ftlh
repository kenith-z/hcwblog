<#include "/include/layou.ftlh" />

<@layout "我的消息">

<div class="layui-container fly-marginTop fly-user-main">
<#--左导航条-->
    <@centerLeft level=3></@centerLeft>
<#--右主体-->
    <div class="fly-panel fly-panel-user" pad20>
        <div class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
            <button class="layui-btn layui-btn-danger" id="LAY_delallmsg">清空全部消息</button>
            <div id="LAY_minemsg" style="margin-top: 10px;">
                <!--<div class="fly-none">您暂时没有最新消息</div>-->
                <ul class="mine-msg">
                    <#list pageData.records as mess>
                        <li data-id="${mess.id}">
                            <blockquote class="layui-elem-quote">
                            <#if mess.type==0>
                                系统消息：${mess.content}
                            <#else>
                            <div style="display:inline-block;vertical-align:top">

                                <a href="/home/${mess.fromUserId}">
                                    <img class="img-size" src="${mess.fromAvatar}"
                                         alt="${mess.fromAvatar}">
                                </a>
                            </div>
                            <div style="display:inline-block;">
                                <p class="mess-p-tips-size">
                                    <a href="/home/${mess.fromUserId}" target="_blank"
                                    ><cite>${mess.fromUserName}</cite></a>在<a target="_blank"
                                                                              href="/post/${mess.postId}"><cite>${mess.postTitle}</cite></a>
                                    <#if mess.type == 1>
                                        评论了你的文章：
                                    <#else>
                                        回复了您的评论 ：
                                    </#if>
                                </p>
                                <p class="mess-p-content-size">${mess.content}</p>
                            </div>
                            </#if>
                            </blockquote>
                            <p>
                                <span>${timeAgo(mess.created)}</span><a href="javascript:;"
                                                                        class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a>
                            </p>
                        </li>
                    </#list>
                </ul>
                <@paging pageData></@paging>
            </div>
        </div>
    </div>

</div>

<script>
    layui.cache.page = 'user';
</script>
</@layout>
