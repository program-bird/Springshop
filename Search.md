
# 搜索系统

## 1 solr的搭建

1. 安装<br/>

2. 配置业务字段<br/>
  
a) 中文分析器的配置<br/>

b) 业务字段配置<br/>
将将需求字段配置到solr中。<br/>

## 2 search系统的搭建

系统架构：<br/>

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片35.png)<br/>


### 2.1 构建索引库信息

使用java程序读取mysql数据库中的商品信息，然后创建solr文档对象，把商品信息写入索引库。需要发布一个服务。为了灵活的进行分布式部署需要创建一搜素的服务工程发布 搜素服务。<br/>

导入商品数据：<br/>
需要使用的表：<br/>
商品描述表，商品分类表，商品信息表<br/>

sql语句：<br/>
SELECT<br/>
	a.id,<br/>
	a.title,<br/><br/>
	a.sell_point,<br/>
	a.price,<br/>
	a.image,<br/>
	b.`name` category_name,<br/>
	c.item_desc<br/>
FROM<br/>
	tb_item a<br/>
LEFT JOIN tb_item_cat b ON a.cid = b.id<br/>
LEFT JOIN tb_item_desc c ON a.id = c.item_id<br/>

### 2.1.2 完成导入过程

dao层:<br/>
需要创建一个mapper接口+mapper映射文件。名称相同且在同一目录下。<br/>

Service层:<br/>
功能：导入所有的商品数据。没有参数。返回结果TaotaoResult。从数据库中查询出所有的商品数据。创建一个SolrInputDocument对象，把对象写入索引库。<br/>

Controller层:<br/>
功能：发布一个rest形式的服务。调用Service的服务方法，把数据导入到索引库中，返回TaotaoResult<br/>

## 2.2 搜索服务发布

### 2.2.1 需求分析

http形式的服务。对外提供搜索服务是一个get形式的服务。调用此服务时需要查询条件，分页条件可以使用page（要显示第几页）、rows（每页显示的记录数）。返回一个json格式的数据。可以使用TaotaoResult包装一个商品列表转换成json。<br/>

请求的url：/search/query?q={查询条件}&page={page}&rows={rows}<br/>
返回的结果：TaotaoResult包装商品列表。<br/>
