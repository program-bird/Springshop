# Springshop
基于ssm框架实现的网上商城

工程使用maven构建，采用分布式架构，后续会对各个功能模块进行介绍。

ps：第一次写github，还在摸索之中，不足的地方请多多指正。代码和文档会陆续更新。。

------------------------------------------------华丽的分割线----------------------------------------------------------------------

## 后台管理系统

### 1 工程结构

Shop-parent(父工程)
>Shop-common(通用工程)

Shop-manager(pom工程)聚合工程
>Shop-pojo(jar包)<br/>
>Shop-mapper(jar包)<br/>
>Shop-service(jar包)<br/>
>Shop-controller(war包)<br/>

前台工程


### 2 准备工作

#### 2.1 创建数据库
使用mysql数据库

商品表：

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%871.jpg)

#### 2.2 使用mybatis逆向工程

#### 2.3 SSM框架整合

##### 2.3.1 DAO层

使用mybatis框架。创建SqlMapConfig.xml，创建一个applicationContext-dao.xml<br/>
1、配置数据源
2、需要让spring容器管理SqlsessionFactory，单例存在。
3、把mapper的代理对象放到spring容器中。使用扫描包的方式加载mapper的代理对象。

##### 2.3.2 Service层

1、事务管理
2、需要把service实现类对象放到spring容器中管理。

##### 2.3.3 表现层

1、配置注解驱动
2、配置视图解析器
3、需要扫描controller

##### web.xml

1、spring容器的配置
2、Springmvc前端控制器的配置
3、Post乱码过滤器

### 3 商品列表的实现

#### 3.1 打开后台管理工程的首页

通过一个controller进行页面跳转展示首页，前端页面使用easyUI开发
