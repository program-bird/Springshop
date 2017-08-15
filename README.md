# Springshop
基于ssm框架实现的网上商城

工程使用maven构建，采用分布式架构，后续会对各个功能模块进行介绍。

ps：第一次写github，还在摸索之中，不足的地方请多多指正。代码和文档会陆续更新。。

------------------------------------------------华丽的分割线------------------------------------------------------------------------

## 后台管理系统

### 1 工程结构

Shop-parent(父工程)
>Shop-common(通用工程)

Shop-manager(pom工程)聚合工程
>Shop-pojo(jar包)
>Shop-mapper(jar包)
>Shop-service(jar包)
>Shop-controller(war包)

前台工程


### 2 准备工作

#### 2.1 创建数据库
使用mysql数据库
![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%871.jpg)

### 3 商品列表的实现
