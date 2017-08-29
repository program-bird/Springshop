
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
