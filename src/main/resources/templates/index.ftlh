<#include "/include/layou.ftlh" />

<@layout "首页">
    <#include "/include/header-panel.ftlh"/>
<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="fly-panel">
                <div class="fly-panel-title fly-filter">
                    <a>置顶</a>
                </div>
                <ul class="fly-list">
                   <@posts level=1>
                        <#list results.records as post>
                            <@postList post></@postList>
                        </#list>
                   </@posts>
                </ul>
            </div>

            <div class="fly-panel" style="margin-bottom: 0;">

                <div class="fly-panel-title fly-filter">
                    <#if !(recommend??)>
                    <a href="/" class="layui-this">综合</a>
                        <span class="fly-mid"></span>
                    <a href="/recommend">精华</a>
                    <#else >
                    <a href="/">综合</a>
                        <span class="fly-mid"></span>
                    <a href="/recommend" class="layui-this">精华</a>
                    </#if>
                    <span class="fly-filter-right layui-hide-xs">
<#--            <a href="" class="layui-this">按最新</a>-->
<#--            <span class="fly-mid"></span>-->
<#--            <a href="">按热议</a>-->
          </span>
                </div>

                <ul class="fly-list">
                    <#list pageData.records as post>
                        <@postList post></@postList>
                    </#list>
                </ul>
                <@paging pageData></@paging>

            </div>
        </div>
        <#include "include/right.ftlh"/>
    </div>
</div>
</@layout>
