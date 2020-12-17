<#include "/include/layou.ftlh" />

<@layout "用户中心">
<div class="layui-container fly-marginTop fly-user-main">
<#--左导航条-->
  <@centerLeft level=1></@centerLeft>
<#--右主体-->
    <div class="fly-panel fly-panel-user" pad20>

        <div class="layui-tab layui-tab-brief" lay-filter="user">
            <ul class="layui-tab-title" id="LAY_mine">
                <li data-type="mine-jie" lay-id="index" class="layui-this">我发的帖（<span id="js-release-count">0</span>）</li>
                <li data-type="collection" data-url="/collection/find/" lay-id="collection">我收藏的帖（<span id="js-collection-count">0</span>）</li>
            </ul>
            <div class="layui-tab-content" style="padding: 20px 0;">
                <div class="layui-tab-item layui-show">
                    <ul class="mine-view jie-row" id="js-release">
                        <script id="js-tem-release" type="text/html">
                            <li>
                                <a class="jie-title" href="../post/{{d.id}}" target="_blank">{{d.title}}</a>
                                <i>{{layui.util.toDateString(d.created,'yyyy-MM-dd HH:mm')}}</i>
                                <a class="mine-edit" href="/post/edit?id={{d.id}}">编辑</a>
                                <em>{{d.viewCount}}阅/{{d.commentCount}}答</em>
                            </li>
                        </script>
                    </ul>
                    <div id="LAY_page"></div>
                </div>
                <div class="layui-tab-item">
                    <ul class="mine-view jie-row" id="js-collection">
                        <script id="js-tem-collection" type="text/html">
                        <li>
                            <a class="jie-title" href="/post/{{d.id}}" target="_blank">{{d.title}}</a>
                            <i>{{layui.util.timeAgo(d.created,true)}}</i>
                        </li>
                        </script>
                    </ul>
                    <div id="LAY_page1"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    layui.cache.page = 'user';
    layui.use(['laytpl', 'flow', 'util'], function () {
        var $ = layui.jquery;
        var laytpl = layui.laytpl;
        var flow = layui.flow;
        var util = layui.util;
        flow.load({
            elem: '#js-release' //指定列表容器
            , isAuto: false
            , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/user/getUserPost?pn=' + page, function (res) {
                    $('#js-release-count').html(res.data.total)
                    //假设你的列表返回在data集合中
                    layui.each(res.data.records, function (index, item) {

                        var tpl = $("#js-tem-release").html();
                        laytpl(tpl).render(item, function (html) {
                            $('#js-release .layui-flow-more').before(html);
                        });

                    });
                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < res.data.pages);
                });
            }
        });
        flow.load({
            elem: '#js-collection' //指定列表容器
            , isAuto: false
            , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/user/collection?pn=' + page, function (res) {
                    $('#js-collection-count').html(res.data.total)
                    //假设你的列表返回在data集合中
                    layui.each(res.data.records, function (index, item) {

                        var tpl = $("#js-tem-collection").html();
                        laytpl(tpl).render(item, function (html) {
                            $('#js-collection .layui-flow-more').before(html);
                        });

                    });
                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < res.data.pages);
                });
            }
        });
    });


</script>
</@layout>