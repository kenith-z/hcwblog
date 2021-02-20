<#include "/include/layou.ftlh" />
<@layout "搜索 - ${q}">
    <#include "/include/header-panel.ftlh"/>
    <div class="layui-container">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md8">
                <div class="fly-panel">
                    <div class="fly-panel-title fly-filter">
                        <a>您正在搜索关键字“ <strong>${q}</strong> ” - 共有 <strong>${(pageData.total)!}</strong> 条记录</a>
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
