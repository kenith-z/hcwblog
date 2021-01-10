# hcwblog
博客日常


### 开发采坑笔记
- 2020-09-03  
数据库最新文章（post表）为20200901，20200902，20200903，缓存到mysql时却出现了day:rank:20200904。
查看centos时间为2020年 08月 18日 星期二 10:22:39 CST ，后使用 ntpdate ntp.ntsc.ac.cn更新北京时间，依旧出现04的记录，
初步怀疑是mysql，输入命令SHOW VARIABLES LIKE "%time_zone%";发现time_zone为SYSTEM，初步排除系统时间与mysql导致的时间错误。
最后发现是datasource的URL上使用serverTimezone=UTC，后将UTC改为Asia/Shanghai成功解决问题

- 2020-10-17  
七牛云上传token的过期时间为3600秒，未设置定时任务进行刷新，导致服务启动一小时后上传失败

- 2020-10-19  
上传头像接口出现  
NoSuchFileException: D:\Temp\undertow.1785867929502475879.8080\undertow2146949036606445489upload  
出现异常的原因是系统将D盘的临时目录清理后项目无法找到临时目录导致异常的发生，使用undertow无法自行设置临时目录，更改为tomcat容器后自行设置临时目录即可正常使用
  
- 2020-12-24
将/collection/find配置到shiro拦截列表中使用postman发送请求拦截失败。 
经过测试/collection/find拦截成功跳转登录页，使用/collection/find/则拦截失败，原因是存放拦截列表的map没有/collection/find/