<#include "/include/layou.ftlh" />
<@layout "错误提示">
  <div class="layui-container fly-marginTop">
    <div class="fly-panel">
      <div class="fly-none">
        <h2><i class="iconfont icon-tishilian"></i></h2>
        <p>${message}<span style="display: block;font-size: 16px">${time}</span></p>

      </div>
    </div>
  </div>
  <script>
    layui.cache.page = '';
  </script>
</@layout>
