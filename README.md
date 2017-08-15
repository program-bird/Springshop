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
使用mysql数据库<br/>

商品表：

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%871.jpg)<br/>

#### 2.2 使用mybatis逆向工程

#### 2.3 SSM框架整合

##### 2.3.1 DAO层

使用mybatis框架。创建SqlMapConfig.xml，创建一个applicationContext-dao.xml<br/>
1、配置数据源<br/>
2、需要让spring容器管理SqlsessionFactory，单例存在。<br/>
3、把mapper的代理对象放到spring容器中。使用扫描包的方式加载mapper的代理对象。<br/>

##### 2.3.2 Service层

1、事务管理<br/>
2、需要把service实现类对象放到spring容器中管理。<br/>

##### 2.3.3 表现层

1、配置注解驱动<br/>
2、配置视图解析器<br/>
3、需要扫描controller<br/>

##### 2.3.4 web.xml

1、spring容器的配置<br/>
2、Springmvc前端控制器的配置<br/>
3、Post乱码过滤器<br/>

### 3 商品列表的实现

#### 3.1 打开后台管理工程的首页

通过一个controller进行页面跳转展示首页，前端页面使用easyUI开发<br/>

#### 3.2 商品列表查询

##### 3.2.1 需求分析

1、请求的url：/item/list

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%872.png)<br/>

2、请求的参数：http://localhost:8080/item/list?page=1&rows=30	分页信息。（需要看官方的手册）<br/>
3、返回值。Json数据。数据格式：<br/>
Easyui中datagrid控件要求的数据格式为：<br/>
{total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]}<br/>

##### 3.2.2 dao层

Sql语句：SELECT * from tb_item LIMIT 0,30

##### 3.2.3 分页插件PageHelper

利用分页插件实现分页

###### 3.2.3，1 分页插件实现原理



