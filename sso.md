
# 单点登录系统

## 1 系统结构

传统单点登录系统结构：

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片36.png)<br/>

分布式环境下，项目中将ession数据存放在redis，并对外提供服务，保证一致性。Redis可以设置key的生存时间、访问速度快效率高。

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片37.png)<br/>

## 2 创建单点登陆系统

### 2.1 系统架构

![image](https://github.com/program-bird/Springshop/blob/master/Image/图片38.png)<br/>

### 2.2 数据校验的开发

dao层：<br/>
查询tb_user<br/>

Service层：<br/>
接收两个参数：内容、内容类型。根据内容类型查询tb_user表返回Taotaoresult对象。Data属性值：返回数据，true：数据可用，false：数据不可用<br/>

Controller层<br/>
从url中接收两个参数，调用Service进行校验，在调用Service之前，先对参数进行校验，例如type必须是1、2、3其中之一。返回TaotaoResult。需要支持jsonp。<br/>

### 2.2 用户注册的开发

Dao层<br/>
可以使用逆向工程生成代码<br/>

Service层<br/>
接收TbUser对象，补全user的属性。向tb_user表插入记录。返回taoTaoResult。<br/>

controller层<br/>
接收提交的数据用户名、密码、电话、邮件。使用pojo接收。使用TbUser。调用Service向表中添加记录。返回TaotaoResult.<br/>

### 2.3 用户登录接口的开发

是一个post请求，包含用户和密码。接收用户名和密码，到数据库中查询，根据用户名查询用户信息，查到之后进行密码比对，需要对密码进行md5加密后进行比对。比对成功后说明登录成功，需要生成一个token可以使用UUID。需要把用户信息写入redis，key就是token，value就是用户信息。返回token字符串。<br/>

Dao层<br/>
查询数据库tb_user表。根据用户名查询用户信息。<br/>

Service层<br/>
接收两个参数用户名、密码。调用dao层查询用户信息。生成token，把用户信息写入redis。返回token。使用TaotaoResult包装。<br/>

Controller层<br/>
接收表单，包含用户、密码。调用Service进行登录返回TaotaoResult<br/>

### 2.4 通过token查询用户信息

根据token判断用户是否登录或者session是否过期。接收token，根据token到redis中取用户信息。判断token字符串是否对应用户信息，如果不对应说明token非法或者session已过期。取到了说明用户就是正常的登录状态。返回用户信息，同时重置用户的过期时间。<br/>

Dao层<br/>
使用JedisClient实现类。<br/>

Service层<br/>
接收token，调用dao，到redis中查询token对应的用户信息。返回用户信息并更新过期时间。<br/>

controller层<br/>
接收token调用Service返回用户信息，使用TaotaoResult包装。<br/>
请求的url：<br/>
http://sso.taotao.com/user/token/{token}<br/>

### 2.5 配置拦截器实现用户登录

在门户系统点击登录连接跳转到登录页面。登录成功后，跳转到门户系统的首页，在门户系统中需要从cookie中 把token取出来。所以必须在登录成功后把token写入cookie。并且cookie的值必须在系统之间能共享。<br/>
