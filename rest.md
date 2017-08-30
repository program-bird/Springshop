
# 后台系统

## 1.redis缓存

### 1.1 redis集群的搭建

架构图：<br/>
![image](https://github.com/program-bird/Springshop/blob/master/Image/图片31.png)<br/>

Redis 集群中内置了 16384 个哈希槽，当需要在 Redis 集群中放置一个 key-value 时，redis 先对 key 使用 crc16 算法算出一个结果，然后把结果对 16384 求余数，这样每个 key 都会对应一个编号在 0-16383 之间的哈希槽，redis 会根据节点数量大致均等的将哈希槽映射到不同的节点<br/>

redis 容错机制采用投票的方法，半数以上master节点与master节点通信超过(cluster-node-timeout),认为当前master节点挂掉.<br/>

项目中的集群：<br/>

集群中有三个节点的集群，每个节点有一主一备，搭建一个伪分布式的集群，使用6个redis实例来模拟。<br/>

### 1.2 在业务逻辑中加入缓存

添加缓存逻辑的原则：缓存逻辑不能影响正常的业务逻辑执行。<br/>

系统架构：<br/>
![image](https://github.com/program-bird/Springshop/blob/master/Image/图片32.png)<br/>

在spring容器中进行redis集群的配置<br/>

#### 1.2.1 首页大广告位添加缓存

缓存逻辑:<br/>
查询内容时先到redis中查询是否有改信息，如果有使用redis中的数据，如果没有查询数据库，然后将数据缓存至redis。返回结果。<br/>

### 1.3 缓存同步

当数据库中的内容信息发生改变后，通过在trest工程中发布一个服务，做为专门同步数据使用，把缓存中的数据清空即可。当管理后台更新了内容信息后，需要调用此服务。<br/>
