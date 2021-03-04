<#include "/include/layou.ftlh" />
<@layout "博客分类">
    <#include "/include/header-panel.ftlh"/>
<div class="layui-container">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md8">
            <div class="fly-panel" style="margin-bottom: 0;">

                <div class="fly-panel-title fly-filter">
                    <#if recommend>
                        <a href="/category/${currentCategoryId}<#if pn??&&pn gt 1>?pn=${pn}&</#if>">综合</a>
                        <span class="fly-mid"></span>
                        <a href="/category/recommend/<#if pn??&&pn gt 1>?pn=${pn}&</#if>recommend=true" class="layui-this">精华</a>
                    <#else >
                        <a href="/category/${currentCategoryId}<#if pn??&&pn gt 1>?pn=${pn}&</#if>" class="layui-this">综合</a>
                        <span class="fly-mid"></span>
                        <a href="/category/recommend/${currentCategoryId}<#if pn??&&pn gt 1>?pn=${pn}</#if>">精华</a>
                    </#if>

                </div>
                <@posts categoryId=currentCategoryId pn=pn recommend=recommend>
                    <ul class="fly-list">
                        <#list results.records as post>
                            <@postList post></@postList>
                        </#list>
                    </ul>
                    <@paging results ></@paging>
                </@posts>
            </div>
        </div>
    <#include "../include/right.ftlh"/>
    </div>
</div>
    <script>
        layui.cache.page = 'jie';
    </script>
</@layout>