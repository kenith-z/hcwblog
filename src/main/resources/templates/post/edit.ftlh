<#include "/include/layou.ftlh" />
<@layout "编辑帖子">
  <div class="layui-container fly-marginTop">
    <div class="fly-panel" pad20 style="padding-top: 5px;">
      <!--<div class="fly-none">没有权限</div>-->
      <div class="layui-form layui-form-pane">
        <div class="layui-tab layui-tab-brief" lay-filter="user">
          <ul class="layui-tab-title">
            <li class="layui-this"><#if !post??>发表新帖<#else>编辑帖子</#if></li>
          </ul>
          <div class="layui-form layui-tab-content" id="LAY_ucm" style="padding: 20px 0;">
            <div class="layui-tab-item layui-show">
              <form action="/post/submit" method="post">
                <div class="layui-row layui-col-space15 layui-form-item">
                  <div class="layui-col-md3">
                    <label class="layui-form-label">所在专栏</label>
                    <div class="layui-input-block">
                      <select lay-verify="required" name="categoryId" lay-filter="column">
                        <option></option>
                        <#list categories as c>
                          <option value="${c.id}" <#if post?? && c.id==post.categoryId>selected</#if>>${c.name}</option>
                        </#list>
                      </select>
                    </div>
                  </div>
                  <div class="layui-col-md9">
                    <label for="L_title" class="layui-form-label">标题</label>
                    <div class="layui-input-block">
                      <input type="text" id="L_title" name="title" value="${(post.title)!''}"required lay-verify="required" autocomplete="off" class="layui-input">
                      <input type="hidden" name="id" value="${(post.id)!''}">
                    </div>
                  </div>
                </div>
                <div class="layui-form-item layui-form-text">
                  <div class="layui-input-block">
                    <textarea id="L_content" name="content" required lay-verify="required" placeholder="详细描述" class="layui-textarea fly-editor" style="height: 260px;">${(post.content)!}</textarea>
                  </div>
                </div>
                <div class="layui-form-item">
                  <label for="L_vercode" class="layui-form-label">验证码</label>
                  <div class="layui-input-inline">
                    <input type="text" id="L_vercode" name="vercode" required lay-verify="required" placeholder="请回答后面的问题" autocomplete="off" class="layui-input">
                  </div>
                  <div>
                    <img id="js-capthca" src="/capthca.jpg" alt="验证码">
                  </div>
                </div>
                <div class="layui-form-item">
                  <button class="layui-btn" lay-filter="*" lay-submit alert="true">立即发布</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <script>
    layui.cache.page = 'jie';
    $("#js-capthca").click(function() {
      $("#js-capthca").attr('src','/capthca'+Math.ceil(Math.random()*10)+'.jpg');
    });
  </script>

</@layout>
