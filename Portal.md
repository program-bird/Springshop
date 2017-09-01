
# 前台系统
## 1. 门户系统的搭建

前台系统和后台系统是分开的，只在数据库层面有关系。都是同一个数据库。</br>
![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8721.png)</br>
![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8722.png)</br>
优点：</br>
1、前台系统和服务层可以分开，降低系统的耦合度。</br>
2、开发团队可以分开，提高开发效率。</br>
3、系统分开可以灵活的进行分布式部署。</br>

## 2.商品分类列表的展示

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8723.png)</br>

### 2.1 Ajax跨域请求
出于安全考虑，js设计时不可以跨域。</br>
Jsonp其实就是一个跨域解决方案。Js跨域请求数据是不可以的，但是js跨域请求js脚本是可以的。可以把数据封装成一个js语句，做一个方法的调用。跨域请求js脚本可以得到此脚本。得到js脚本之后会立即执行。可以把数据做为参数传递到方法中。就可以获得数据。从而解决跨域问题。<br/>

### 2.1 rest服务的搭建
1.从数据库中取商品分类列表.</br>
2.查询所有商品分类生成前台页面要求的json数据格式。返回一个pojo。</br>
3.接收页面传递过来的参数。参数就是方法的名称。返回一个json数据，需要把json数据包装成一句js代码。返回一个字符串。</br>

controller中@ResponseBody注解下直接返回字符串会产生中文乱码，这里提供两种解决方法</br>

方法一</br>

使用MappingJacksonValue对象包装返回结果，并设置jsonp的回调方法。</br>
//包装jsonp</br>
  MappingJacksonValue jacksonValue = new MappingJacksonValue(result);</br>
//设置包装的回调方法名</br>
	jacksonValue.setJsonpFunction(callback);</br>

方法二</br>

先把ItemCatResult对象转换成json字符串，然后使用字符串拼接的方法拼装成jsonp格式的数据。需要设置相应结果的MediaType。</br>

在@RequestMapping 中加入属性 produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8"</br>


### 2.2 页面实现

门户系统直接调用rest服务接口，展示结果如下</br>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8724.png)</br>


## 3.商城首页大广告位的展示

### 3.1 方案分析

方案一：</br>

jsonp跨域请求</br>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8728.png)</br>

需要当首页加载完毕后，大广告位就应该显示。没有触发事件。不是太合适。</br>
优点：不需要二次请求，页面直接加载内容数据。减少门户系统的压力。</br>
缺点：需要延迟加载。不利于seo优化。</br>

方案二：</br>

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8729.png)</br>

优点：有利于seo优化。可以在taotao-portal中对数据进行加工。</br>
缺点：系统直接需要调用服务查询内容信息。多了一次http请求。</br>

系统直接服务的调用，需要使用httpclient来实现。portal和rest是在同一个局域网内部。速度非常快，调用时间可以忽略不计。</br>

展示首页内容功能，使用方案二实现。</br>

### 3.2 流程展示

![image](https://github.com/program-bird/Springshop/blob/master/Image/%E5%9B%BE%E7%89%8730.png)</br>

### 3.3 内容服务发布

根据内容的分类id查询内容列表，从tb_content表中查询。服务是一个restFul形式的服务。使用http协议传递json格式的数据。</br>

4.3.3Service层
接收内容分类id，根据分类id查询分类列表。返回一个内容pojo列表。
参数：分类id
返回值：pojo列表

Dao层：</br>
从tb_content表中查询，根据内容分类id查询。是单表查询。可以使用逆向工程生成的代码。</br>

Service层</br></br>
接收内容分类id，根据分类id查询分类列表。返回一个内容pojo列表。</br>
参数：分类id</br>
返回值：pojo列表</br>

商品key的定义：
基本信息：
REDIS_ITEM_KEY:商品id:base=json
描述：
REDIS_ITEM_KEY:商品id:desc=json
规格参数：
REDIS_ITEM_KEY:商品id:param=jsonController层</br>
发布服务。接收查询参数。Restful风格内容分类id应该从url中取。</br>
/rest/content/list/{contentCategoryId}</br>
从url中取内容分类id，调用Service查询内容列表。返回内容列表。返回一个json格式的数据。可以使用TaotaoResult包装此列表。</br>

### 3.4 Httpclient的使用

对Httpclient get和post方法进行封装，以供使用。</br>

### 3.5 大广告位展示实现

Service层</br>
根据内容分类id查询分类的内容列表，需要使用httpclient调用rest的服务。得到服务发布的java对象。从java对象中取出内容列表。把内容列表转换成jsp页面要求的json格式。返回一个json字符串。</br>
参数：没有参数</br>
返回值：json字符串。</br>

Controller</br>
展示首页返回一个逻辑视图，需要把首页大广告位的json数据传递给jsp。

## 4.商品搜索的实现

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片33.png)<br/>

### 4.1 需求分析

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片34.png)<br/>

用户在首页中输入查询条件，点击查询向taotao-portal发送请求，参数就是查询的条件，页码。Taoto-portal调用taotao-search发布的服务进行搜索，参数应该是查询条件和页码及每页显示的记录数（参数可选）。Taotao-search返回一个json格式的数据（TaotaoResult包装一个SearchResult对象）。Taotao-portal接收json数据需要把json数据转换成java对象。把java对象传递给jsp页面，jsp渲染java对象得到商品查询结果页面。<br/>

## 5.商品详情页面展示

需要在portal中调用rest发布的服务，查询商品详情。<br/>
1、商品的基本信息<br/>
2、商品的描述<br/>
3、商品的规格<br/>

### 5.1 服务发布

需要在taotao-rest工程中发布服务<br/>
1、取商品基本信息的服务<br/>
2、取商品描述的服务<br/>
3、取商品规格的服务<br/>
需要把商品信息添加到缓存中。设置商品的过期时间，过期时间为一天。需要缓存同步。<br/>

#### 5.1.1 取商品基本信息

dao层：<br/>
查询的表tb_item：<br/>

接收商品id，根据商品id查询商品基本信息。返回一个商品的pojo，使用taotaoResult包装返回。<br/>

Controller层：<br/>

接收商品id调用Service查询商品信息，返回商品对象，使用TaotaoResult包装。<br/>
Url：/rest/item/info/{itemId}<br/>

#### 5.1.2 添加缓存逻辑

Redis的hash类型中的key是不能设置过期时间。如果还需要对key进行分类可以使用折中的方案。<br/>

商品key的定义：<br/>
基本信息：<br/>
REDIS_ITEM_KEY:商品id:base=json<br/>
描述：<br/>
REDIS_ITEM_KEY:商品id:desc=json<br/>
规格参数：<br/>
REDIS_ITEM_KEY:商品id:param=json<br/>

#### 5.1.2 取商品基本信息
同上<br/>

#### 5.1.2 取商品基本信息
同上<br/>

### 5.2 portal 调用服务

商品基本信息<br/>
商品描述信息<br/>
商品规格参数<br/>
