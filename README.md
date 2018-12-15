### 一、架构图

![img](https://img-blog.csdnimg.cn/20181212215151153.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

### 二、包含的微服务

### 2.1 网关微服务

> 架构图

![img](https://img-blog.csdnimg.cn/20181214102311483.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

不管是来自于客户端（PC或移动端）的请求，还是服务内部调用。一切对服务的请求都会经过Zuul这个网关，然后再由网关来实现 鉴权、动态路由等等操作。Zuul就是我们服务的统一入口。

> 配置

![img](https://img-blog.csdnimg.cn/20181214102758667.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

服务网关是微服务架构中一个不可或缺的部分。通过服务网关统一向外系统提供REST API的过程中，除了具备服务路由、均衡负载功能之外，它还具备了`权限控制`等功能。为微服务架构提供了前门保护的作用，同时将权限控制这些较重的非业务逻辑内容迁移到服务路由层面，使得服务集群主体能够具备更高的可复用性和可测试性。

> 主要功能

身份认证与安全：识别每个资源的验证要求，并拒绝那些与要求不相符的请求。（对jwt鉴权）

动态路由：动态地将请求路由到不同的后端集群。

负载均衡和熔断

### 2.2 授权中心微服务

> 结合RSA的鉴权

![img](https://img-blog.csdnimg.cn/20181214104033784.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

- 首先利用RSA生成公钥和私钥。私钥保存在授权中心，公钥保存在Zuul和各个微服务
- 用户请求登录
- 授权中心校验，通过后用私钥对JWT进行签名加密
- 返回jwt给用户
- 用户携带JWT访问
- Zuul直接通过公钥解密JWT，进行验证，验证通过则放行
- 请求到达微服务，微服务直接用公钥解析JWT，获取用户信息，无需访问授权中心

> 授权中心的主要职责

1. 用户鉴权：接收用户的登录请求，通过用户中心的接口进行校验，通过后生成JWT。使用私钥生成JWT并返回
2. 服务鉴权：微服务间的调用不经过Zuul，会有风险，需要鉴权中心进行认证。原理与用户鉴权类似，但逻辑稍微复杂一些（未实现）。

### 2.3 购物车微服务

> 功能需求

- 用户可以在登录状态下将商品添加到购物车

放入数据库

放入redis（采用）

- 用户可以在未登录状态下将商品添加到购物车
- 放入localstorage
- 用户可以使用购物车一起结算下单
- 用户可以查询自己的购物车
- 用户可以在购物车中修改购买商品的数量。
- 用户可以在购物车中删除商品。
- 在购物车中展示商品优惠信息
- 提示购物车商品价格变化

> 流程图

![img](https://img-blog.csdnimg.cn/20181214104500266.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)

这幅图主要描述了两个功能：新增商品到购物车、查询购物车。

新增商品：

​        判断是否登录

​               是：则添加商品到后台Redis中

​               否：则添加商品到本地的Localstorage

无论哪种新增，完成后都需要查询购物车列表：

​        判断是否登录

​              否：直接查询localstorage中数据并展示

​              是：已登录，则需要先看本地是否有数据，

​                         有：需要提交到后台添加到redis，合并数据，而后查询

​                         否：直接去后台查询redis，而后返回

### 2.4 评论微服务（新增）

> 功能需求

1. 用户在确认收货后可以对商品进行评价，每个用户对订单中的商品只能发布一次顶级评论，可以追评，也可以回复别人的评论。
2. 当用户确认收货后没有进行手动评价时，3天后自动五星好评

> 表结构设计

parent和isparent字段是用来实现评论嵌套的。

> 实现

使用MongoDB存储评论，基本的CRUD。

### 2.5 配置中心微服务

> 需求

在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloudconfig ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。

使用SpringCloudBus来实现配置的自动更新。

> 组成结构

在spring cloud config 组件中，分两个角色，一是config server，二是config client。

Config Server是一个可横向扩展、集中式的配置服务器，它用于集中管理应用程序各个环境下的配置，默认使用Git存储配置文件内容，也可以使用SVN存储，或者是本地文件存存储

Config Client是Config Server的客户端，用于操作存储在Config Server中的配置内容。微服务在启动时会请求Config Server获取配置文件的内容，请求到后再启动容器

> 实现

创建配置中心，对Config Server进行配置，然后在其它微服务中配置Config Client。最后使用Github上的Webhooks进行配置的动态刷新，所以还要使用内网穿透工具，同时要在配置中心中添加过滤器，因为使用Webhooks提交请求时会加上一段Payload，而本地是无法解析这个Payload的，所以要将它过滤掉。

### 2.6 页面静态化微服务

商品详情浏览量比较大，并发高，所以单独开启一个微服务用来展示商品详情。 页面详情及其静态化

### 2.7 后台管理微服务

### 2.8 订单微服务

### 2.9 注册中心

### 2.10 搜索微服务

### 2.11 秒杀微服务

### 2.12 短信微服务

### 2.13 文件上传微服务

### 2.14 用户中心微服务

![img](https://img-blog.csdnimg.cn/20181212215548926.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2x5ajIwMThneXE=,size_16,color_FFFFFF,t_70)

![点击并拖拽以移动](data:image/gif;base64,R0lGODlhAQABAPABAP///wAAACH5BAEKAAAALAAAAAABAAEAAAICRAEAOw==)