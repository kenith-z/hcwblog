# hcwblog
博客日常


### 开发采坑笔记
2020-09-03
数据库最新文章（post表）为20200901，20200902，20200903，缓存到mysql时却出现了day:rank:20200904。
查看centos时间为2020年 08月 18日 星期二 10:22:39 CST ，后使用 ntpdate ntp.ntsc.ac.cn更新北京时间，依旧出现04的记录，
初步怀疑是mysql，输入命令SHOW VARIABLES LIKE "%time_zone%";发现time_zone为SYSTEM，初步排除系统时间与mysql导致的时间错误。
最后发现是jsbc的URL上使用serverTimezone=UTC，后将UTC改为Asia/Shanghai成功解决问题

