<div class="layui-col-md4">

    <dl class="fly-panel fly-list-one">
        <dt class="fly-panel-title">本周热议</dt>
        <@hots>
            <#list results as post>
                <#if (post.title)??>
                <dd>
                    <a href="/post/${post.id}">${post.title}</a>
                    <span><i class="iconfont icon-pinglun1"></i>${post.commentCount}</span>
                </dd>
                </#if>
            </#list>
        </@hots>

        <!-- 无数据时 -->
        <!--
        <div class="fly-none">没有相关数据</div>
        -->
    </dl>

    <div class="fly-panel">
        <div class="fly-panel-title">
            广告区域
        </div>
        <div class="fly-panel-main">
           张红尘作品，本站仅仅只用于稳定性运行测试
        </div>
    </div>

    <div class="fly-panel fly-link">
        <h3 class="fly-panel-title">友情链接</h3>
        <dl class="fly-panel-main">
            <dd><a href="http://www.hcworld.xyz" target="_blank">hcblog</a>
            <dd>
            <dd><a href="mailto:zhang@hcworld.xyz"
                   class="fly-link">申请友链</a>
            <dd>
        </dl>
    </div>

</div>