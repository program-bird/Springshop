
# 后台管理系统

## 1 工程结构

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

## 2 准备工作

#### 2.1 创建数据库
使用mysql数据库<br/>

商品表：

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%871.jpg)<br/>

### 2.2 使用mybatis逆向工程

### 2.3 SSM框架整合

#### 2.3.1 DAO层

使用mybatis框架。创建SqlMapConfig.xml，创建一个applicationContext-dao.xml<br/>
1、配置数据源<br/>
2、需要让spring容器管理SqlsessionFactory，单例存在。<br/>
3、把mapper的代理对象放到spring容器中。使用扫描包的方式加载mapper的代理对象。<br/>

#### 2.3.2 Service层

1、事务管理<br/>
2、需要把service实现类对象放到spring容器中管理。<br/>

#### 2.3.3 表现层

1、配置注解驱动<br/>
2、配置视图解析器<br/>
3、需要扫描controller<br/>

#### 2.3.4 web.xml

1、spring容器的配置<br/>
2、Springmvc前端控制器的配置<br/>
3、Post乱码过滤器<br/>

## 3 商品列表的实现

### 3.1 打开后台管理工程的首页

通过一个controller进行页面跳转展示首页，前端页面使用easyUI开发<br/>

### 3.2 商品列表查询

#### 3.2.1 需求分析

1、请求的url：/item/list

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%872.png)<br/>

2、请求的参数：http://localhost:8080/item/list?page=1&rows=30	分页信息。（需要看官方的手册）<br/>
3、返回值。Json数据。数据格式：<br/>
Easyui中datagrid控件要求的数据格式为：<br/>
{total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]}<br/>

#### 3.2.2 dao层

Sql语句：SELECT * from tb_item LIMIT 0,30

#### 3.2.3 分页插件PageHelper

利用分页插件实现分页,无需修改逆向工程生成文件

##### 3.2.3.1 分页插件实现原理

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%873.png)<br/>

##### 3.2.3.2 使用方法

第一步：引入pageHelper的jar包。</br>
第二步：需要在SqlMapConfig.xml中配置插件。</br>
第三步：在查询的sql语句执行之前，添加一行代码：
PageHelper.startPage(1, 10);</br>
第一个参数是page，要显示第几页。</br>
第二个参数是rows，没页显示的记录数。</br>
第四步：取查询结果的总数量。</br>
创建一个PageInfo类的对象，从对象中取分页信息。</br>

#### 3.2.3 service 层

接收分页参数，一个是page一个是rows。调用dao查询商品列表。并分页。返回商品列表。</br>

#### 3.2.4 controller 层

接收页面传递过来的参数page、rows。返回json格式的数据。</br>

结果图：

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%874.png)<br/>

## 4 实现商品类目选择功能

### 4.1 需求

在商品添加页面，点击“选择类目”显示商品类目列表：

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%875.png)<br/>

### 4.2 实现步骤

1、按钮添加点击事件，弹出窗口，加载数据显示tree<br/>
2、将选择类目的组件封装起来，通过TT.iniit()初始化，最终调用initItemCat()方法进行初始化<br/>
3、创建数据库、以及tb _item_cat表，初始化数据<br/>
4、编写Controller、Service、Mapper<br/>

### 4.3 EasyUI tree数据结构

数据结构中必须包含：<br/>
Id：节点id<br/>
Text：节点名称<br/>
State：如果不是叶子节点就是close，叶子节点就是open。Close的节点点击后会在此发送请求查询子项目。<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%876.png)<br/>

## 5 图片上传

### 5.1 图片服务器

搭建分布式图片服务器。使用nginx作为静态资源服务器（本项目此处利用虚拟机模拟图片服务器）
在虚拟机上安装nginx并进行配置

#### 5.1.1 Nginx配置

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%877.png)<br/>

需要把nginx的根目录指向ftp上传文件的目录。

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%878.png)<br/>

### 5.2 图片上传实现

#### 5.2.1需求分析

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

#### 5.2.2 service实现

功能：接收controller层传递过来的图片对象，把图片上传到ftp服务器。给图片生成一个新的名字。<br/>
参数：MultiPartFile uploadFile<br/>
返回值：返回一个pojo，应该是PictureResult。<br/>

#### 5.2.4 Controller实现

功能：接收页面传递过来的图片。调用service上传到图片服务器。返回结果。<br/>
参数：MultiPartFile uploadFile<br/>
返回值：返回json数据，应该返回一个pojo，PictureResult对象。<br/>

## 6 富文本编辑器

### 6.1 kindeditor的使用过程：

1、导入js：<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8714.png)<br/>

2、定义多行文本（不可见、给定name）<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8715.png)<br/>

3、调用TT.createEditor

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8716.png)<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8717.png)<br/>

4、效果

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8718.png)<br/>

### 6.1 取文本编辑器中的内容

将编辑器的内容设置到原来的textarea控件里。<br/>

## 7 新增商品实现

### 7.1 提交请求的数据格式

$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串以post 的形式将表单的内容提交。<br/>

请求的url： /item/save。<br/>

返回的结果：。<br/>
淘淘自定义返回结果：。<br/>
1、状态码。<br/>
2、响应的消息。<br/>
3、响应的数据。<br/>

### 7.2 service的实现

功能分析：接收controller传递过来的对象一个是item一个是itemDesc对象。需要生成商品的id。把不为空的字段都补全。分别向两个表中插入数据。<br/>
参数：TbItem，TbItemDesc<br/>

### 7.3 controller的实现

功能分析：接收页面传递过来的数据包括商品和商品描述。<br/>
参数：TbItem、TbItemDesc。<br/>

## 8 商品规格

### 8.1 商品规格简介

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片19.png)<br/>

规格参数：<br/>
规格组<br/>
  |-规格项：规格值<br/>

规律：<br/>
1、同一类商品的规格项分组相同。<br/>
2、同一类商品的规格项目是相同的。规格项目是跟商品关联。<br/>
3、不同商品规格参数的值是不同的<br/>

### 8.1 实现方案

#### 8.1.1 方案分析

可以使用模板的思路来解决此问题。<br/>
1、每一个商品分类对一个规格参数模板。<br/>
[<br/>
    {<br/>
        "group": "主体",  //组名称<br/>
        "params": [ // 记录规格成员<br/>
            "品牌",<br/>
            "型号",<br/>
            "颜色",<br/>
            "上市年份",<br/>
            "上市月份"<br/>
        ]<br/>
}，<br/>
{<br/>
        "group": "网络",  //组名称<br/>
        "params": [ // 记录规格成员<br/>
            "4G",<br/>
            "3G,<br/>
            "2G"<br/>
        ]<br/>
}<br/>

]<br/>

2、使用模板
每个商品对应一唯一的规格参数。在添加商品时，可以根据规格参数的模板。生成一个表单。保存规格参数时。还可以生成规格参数的json数据。保存到数据库中。<br/>
[<br/>
    {<br/>
        "group": "主体",<br/>
        "params": [<br/>
            {<br/>
                "k": "品牌",<br/>
                "v": "苹果（Apple）"<br/>
            },<br/>
            {<br/>
                "k": "型号",<br/>
                "v": "iPhone 6 A1589"<br/>
            },<br/>
{<br/>
                "k": "智能机",<br/>
                "v": "是 "<br/>
            }<br/>

}<br/>
]<br/>

### 8.1.2 创建规格参数模板

请求的url：<br/>
/item/param/query/itemcatid/{itemCatId}<br/>
参数：itemCatId，从url中获得<br/>
返回值：TaotaoResult<br/>

dao层：<br/>
从tb_item_param表中根据商品分类id查询内容。<br/>
单表操作。可以实现逆向工程的代码。<br/>

3Service层：<br/>
功能：接收商品分类id。调用mapper查询tb_item_param表，返回结果TaotaoResult。<br/>

Controller层：<br/>
接收cid参数。调用Service查询规格参数模板。返回TaotaoResult。返回json数据。<br/>

### 8.1.3 提交规格参数模板

请求的url：<br/>
/item/param/save/{cid}<br/>
参数：<br/>
String paramData<br/>
返回值：<br/>
TaotaoResult<br/>

Dao层<br/>
保存规格参数模板，向tb_item_param表添加一条记录。可以使用逆向工程生成的代码。<br/>

Service层<br/>
功能：接收TbItemParam对象。 把对象调用mapper插入到tb_item_param表中。返回TaotaoResult。<br/>

Controller层<br/>
功能：接收cid、规格参数模板。创建一TbItemParam对象。调用Service返回TaotaoResult。返回json数据。<br/>

### 8.1.4 根据规格参数模板生成表单

在商品添加功能中，读取此商品对应的规格模板，生成表单。供使用者添加规格参数。<br/>

### 8.1.5 保存商品的规格参数

Dao层<br/>
需要向tb_item_param_item表中添加数据。<br/>

Service层<br/>
接收规格参数的内容，和商品id。拼装成pojo调用mapper 的方法tb_item_param_item表中添加数据返回TaotaoResult。<br/>

cotroller层：<br/>
接收规格参数信息，调用Service层保存商品信息及商品描述及商品规格参数。返回taotaoResult.<br/>

### 8.1.6 展示规格参数

当现实商品详情页面时，需要把商品的规格参数根据商品id取出来，生成html展示到页面。<br/>

Dao层<br/>
根据商品id查询规格参数，单表查询。<br/>

Service<br/>
接收商品id查询规格参数表。根据返回的规格参数生成html返回html。<br/>

Controller<br/>
接收商品id调用Service查询规格参数信息，得到规格参数的html。返回一个逻辑视图。把html展示到页面。<br/>

## 9 网站内容管理

内容管理完成对首页的动态管理，本项目中主要实现的是对首页广告位的分类以及内容的管理<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片25.png)<br/>

实现了内容的分类管理和内容管理<br/>

### 9.1 内容分类管理

完成了内容分类的添加和显示功能<br/>

#### 9.1.1 内容分类的显示

参数是id，当前节点id属性，应该根据此id查询子节点列表。<br/>

返回值：包含id、text、state三个属性的json数据列表。<br/>

dao层：<br/>
     根据parentid查询节点列表<br/> 
     SELECT * FROM `tb_content_category` WHERE parent_id = 30;<br/>
     
service层：<br/> 
功能：接收parentid。<br/>
根据parentid查询节点列表，返回返回一个EasyUI异步Tree要求的节点列表。每个节点包含三个属性id、text，state三个属性。可以使用EUTreeNode。<br/>
参数：id<br/>
返回值:List<EUTreeNode><br/>
  
Controller层：<br/>
接收页面传递过来的parentid，根据parentid查询节点列表。返回List<EUTreeNode>。需要响应json数据。<br/>
  

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片26.png)<br/>

#### 9.1.2 内容分类的添加

Dao层：<br/>
使用逆向工程生成的代码<br/>

service层：<br/>
功能：接收两个参数parentId父节点id、name：当前节点的名称。向tb_content_category表中添加一条记录。返回TaoTaoResult包含记录的pojo对象。<br/>

Controller层：<br/>
接收两个参数parentid、name。调用Service添加记录。返回TaotaoResult。应该返回json数据。<br/>

### 9.2 内容管理

#### 9.2.2 内容的添加

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片27.png)<br/>

请求的url：/content/save<br/>
请求的方法：post<br/>
请求内容：表单中的内容。<br/><br/>
返回的结果：TaotaoResult。<br/>

Dao层<br/>
向tb_content表中插入数据。使用逆向工程生成的代码。<br/>

Service层<br/>
接收表tb_content对应的pojo对象。把pojo对象插入到tb_content表中。返回TaotaoResult。<br/>

Controller层<br/>
接收表单中的内容，使用pojo接收。要求pojo的属性要和表单中的name一致。调用Service插入内容信息。返回TaotaoResult。Json格式的数据。<br/>
