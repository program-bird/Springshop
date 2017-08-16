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

### 1.1 功能概述

查询商品功能，新增商品功能（包括新增商品类目，添加商品图片，添加商品概述），添加商品规格参数

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

利用分页插件实现分页,无需修改逆向工程生成文件

###### 3.2.3.1 分页插件实现原理

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%873.png)<br/>

###### 3.2.3.2 使用方法

第一步：引入pageHelper的jar包。</br>
第二步：需要在SqlMapConfig.xml中配置插件。</br>
第三步：在查询的sql语句执行之前，添加一行代码：
PageHelper.startPage(1, 10);</br>
第一个参数是page，要显示第几页。</br>
第二个参数是rows，没页显示的记录数。</br>
第四步：取查询结果的总数量。</br>
创建一个PageInfo类的对象，从对象中取分页信息。</br>

##### 3.2.3 service 层

接收分页参数，一个是page一个是rows。调用dao查询商品列表。并分页。返回商品列表。</br>

##### 3.2.4 controller 层

接收页面传递过来的参数page、rows。返回json格式的数据。</br>

结果图：

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%874.png)<br/>

### 4 实现商品类目选择功能

#### 4.1 需求

在商品添加页面，点击“选择类目”显示商品类目列表：

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%875.png)<br/>

#### 4.2 实现步骤

1、按钮添加点击事件，弹出窗口，加载数据显示tree<br/>
2、将选择类目的组件封装起来，通过TT.iniit()初始化，最终调用initItemCat()方法进行初始化<br/>
3、创建数据库、以及tb _item_cat表，初始化数据<br/>
4、编写Controller、Service、Mapper<br/>

#### 4.3 EasyUI tree数据结构

数据结构中必须包含：<br/>
Id：节点id<br/>
Text：节点名称<br/>
State：如果不是叶子节点就是close，叶子节点就是open。Close的节点点击后会在此发送请求查询子项目。<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%876.png)<br/>

### 5 图片上传

#### 5.1 图片服务器

搭建分布式图片服务器。使用nginx作为静态资源服务器（本项目此处利用虚拟机模拟图片服务器）
在虚拟机上安装nginx并进行配置

##### 5.1.1 Nginx配置

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%877.png)<br/>

需要把nginx的根目录指向ftp上传文件的目录。

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%878.png)<br/>

#### 5.2 图片上传实现

##### 5.2.1需求分析

Common.js
1、绑定事件<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8711.png)<br/>

2、初始化参数<br/>
![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8713.png)<br/>

3、上传图片的url：<br/>
/pic/upload<br/>
4、上图片参数名称：<br/>
uploadFile<br/>
5、返回结果数据类型json<br/>
![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8712.png)<br/>

##### 5.2.2 service实现

功能：接收controller层传递过来的图片对象，把图片上传到ftp服务器。给图片生成一个新的名字。<br/>
参数：MultiPartFile uploadFile<br/>
返回值：返回一个pojo，应该是PictureResult。<br/>

##### 5.2.4 Controller实现

功能：接收页面传递过来的图片。调用service上传到图片服务器。返回结果。<br/>
参数：MultiPartFile uploadFile<br/>
返回值：返回json数据，应该返回一个pojo，PictureResult对象。<br/>

### 6 富文本编辑器
