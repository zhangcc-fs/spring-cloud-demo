```
此工程已将公共部分抽离出整合成框架smart-cloud。 

smart-cloud地址：https://github.com/smart-cloud/smart-cloud
smart-cloud对应示例smart-cloud-examples地址：https://github.com/smart-cloud/smart-cloud-examples
```

# 一、项目说明
**此项目为spring cloud微服务学习示例项目。所实现功能如下：**
- 接口文档自动生成（服务启动时通过swagger生成原始数据，处理后上传至gitbook）
- 可以生成mock数据，充分发挥前后端分离的作用
- 部署灵活，服务可合并（合并后服务间通过内部进程通信；分开后通过rpc通信）部署
- 业务无关代码自动生成
- 接口（加密+签名）安全保证
- 业务无关功能（如日志打印、公共配置、常用工具类等）抽象为公共模块
- 支持多数据源、分表分库、分布式事务
- 敏感配置信息加密
- 单体服务开发接阶段测试不依赖其他服务（挡板测试、关闭eureka）
- 通过单元测试、集成测试、系统测试减少代码的缺陷
- 代码安全保护
- 技术栈稳定、实用、易用

----------
> <table>
> 	<tr>
> 		<th width="150px">模块</th>
> 		<th width="235px">项目名</th>
> 		<th>说明</th>
> 		<th width="70px">端口</th>
> 	</tr>
> 	<tr>
> 		<td>公共配置</td>
> 		<td>common</td>
> 		<td>日志切面、rpc测试挡板、接口mock数据、公共实体对象、工具类、公共拦截器、log4j2日志模板等</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>代码生成</td>
> 		<td>demo-code-auto-generate</td>
> 		<td>自动生成entity、dao、biz、service、controller、公共配置等业务无关代码</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=3>服务注册中心</td>
> 		<td>demo-eureka-module</td>
> 		<td>服务注册中心模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>eureka-nodeA</td>
> 		<td>eureka节点A</td>
> 		<td>10001</td>
> 	</tr>
> 	<tr>
> 		<td>eureka-nodeB</td>
> 		<td>eureka节点B</td>
> 		<td>10002</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=5>电商模块</td>
> 		<td>demo-mall-module</td>
> 		<td>电商模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-mall-order-service</td>
> 		<td>订单服务</td>
> 		<td>20011</td>
> 	</tr>
> 	<tr>
> 		<td>demo-mall-product-service</td>
> 		<td>商品服务</td>
> 		<td>20021</td>
> 	</tr>
> 	<tr>
> 		<td>demo-mall-user-service</td>
> 		<td>用户服务（用户、登陆等）</td>
> 		<td>20031</td>
> 	</tr>
> 	<tr>
> 		<td>demo-mall-auth-service</td>
> 		<td>权限服务</td>
> 		<td>20041</td>
> 	</tr>
> 	<tr>
> 		<td>电商模块rpc接口</td>
> 		<td>demo-mall-rpc</td>
> 		<td>包括请求、响应对象、rpc接口</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>ORM</td>
> 		<td>demo-mapper-common</td>
> 		<td>mybatis、mapper、sharding jdbc、seata等封装。业务无关mapper动态生成，sql日志打印等</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=2>系统测试模块</td>
> 		<td>demo-system-test-module</td>
> 		<td>系统测试模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-mall-system-test</td>
> 		<td>商城系统测试</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td rowspan=2>服务合并模块</td>
> 		<td>demo-merge-module</td>
> 		<td>合并模块父项目</td>
> 		<td>-</td>
> 	</tr>
> 	<tr>
> 		<td>demo-merge-mall</td>
> 		<td>电商合并服务</td>
> 		<td>30001</td>
> 	</tr>
> 	<tr>
> 		<td>服务监控</td>
> 		<td>spring-boot-admin</td>
> 		<td>-</td>
> 		<td>10011</td>
> 	</tr>
> 	<tr>
> 		<td>服务网关</td>
> 		<td>spring-cloud-gateway</td>
> 		<td>-</td>
> 		<td>80</td>
> 	</tr>
> 	<tr>
> 		<td>相关文档</td>
> 		<td>docs</td>
> 		<td>服务相关sql、文档图片等</td>
> 		<td>-</td>
> 	</tr>
> </table>

----------

# 二、技术栈
## （一）服务端
 名称 | 说明
---|---
[spring boot](https://spring.io/projects/spring-boot/) | 手脚架 
[spring cloud gateway](https://spring.io/projects/spring-cloud-gateway) | 服务网关 
[eureka](https://spring.io/projects/spring-cloud-netflix) | 服务注册 
[spring boot admin](https://github.com/codecentric/spring-boot-admin) | 服务监控 
[携程apollo](https://github.com/ctripcorp/apollo) | 配置中心 
[openfeign](https://spring.io/projects/spring-cloud-openfeign) | 声明式服务调用 
[sleuth](https://spring.io/projects/spring-cloud-sleuth)、[log4j2](https://logging.apache.org/log4j/2.x/) | 链路追踪、日志 
[mybatis](http://www.mybatis.org/mybatis-3/zh/index.html) 、[mapper](https://github.com/abel533/Mapper)| ORM 
[seata](https://github.com/seata/seata) | 分布式事务
[sharding jdbc](https://github.com/apache/incubator-shardingsphere) | 分库分表
[redis](https://redis.io/) | 缓存 
[rocketmq](https://github.com/apache/rocketmq) | 消息队列 
[fastdfs](https://github.com/happyfish100/fastdfs) | 文件存储 
[xxl-job](https://github.com/xuxueli/xxl-job)| 定时任务 
[easyexcel](https://github.com/alibaba/easyexcel) | excel导入导出
[Hibernator-Validator](http://hibernate.org/validator/) | 参数校验 
[mockito](https://site.mockito.org/) | 单元测试
[swagger](https://swagger.io/)、[gitbook](https://www.gitbook.com/) | 接口文档 
[xjar](https://github.com/core-lib/xjar) | 代码安全 
[jasypt-spring-boot](https://github.com/ulisesbocchio/jasypt-spring-boot) | 配置文件中敏感数据加解密
[Lombok](https://www.projectlombok.org/) | 简化代码 
## （二）前端
技术 | 说明
---|---
[Vue](https://vuejs.org/) | 前端框架
[Vue-router](https://router.vuejs.org/) | 路由框架
[Vuex](https://vuex.vuejs.org/) | 全局状态管理框架
[Element](https://element.eleme.io/) | 前端UI框架
[mpvue](https://github.com/Meituan-Dianping/mpvue) | 基于 Vue.js 的小程序开发框架
[Axios](https://github.com/axios/axios) | 前端HTTP框架
[Js-cookie](https://github.com/js-cookie/js-cookie) | cookie管理工具
[nprogress](https://github.com/rstacruz/nprogress) | 进度条控件
[quicklink](https://github.com/GoogleChromeLabs/quicklink) | 在空闲时预取viewport内的链接来加快后续页面的加载速度
[Font Awesome](http://fontawesome.dashgame.com/) | 图标

# 三、接口协议

```
仅支持http get、http post两种方式。
对于http get，请求参数只签名；响应信息加密，且签名。
对于http post，请求响应皆加密，且签名。
```
## （一）约定
```
接口mapping url格式：接口使用端标志/接口类型标志/服务模块名/接口模块名/接口名
如：api/sign/user/loginInfo/login

接口使用端标记：
	api：app端使用的接口
	oms：管理后台使用的接口
	rpc：rpc接口
接口分为4种：
	open：既不登陆，亦不签名加密
	sign：不需登陆，需签名加密
	identity：需登陆，不签名加密，需鉴权
	auth：需登陆，需签名加密，需鉴权
	
http get、http post共同部分，即http headers部分的数据，它包含请求时间戳（默认2分钟内有效）、请求的token、交易流水号、签名等4个自定义字段：
	smart-sign: 109ad1a8e05f8de345e6d780f09b001e97dc3d6fa9bbbe6936edb2b75a81864ac3b0b071e093af001fbffa479217540138b98f6f165e8246dd25a2536649f1f6
	smart-timestamp: 1555778393862
	smart-token: 4c2e22605001000rK
	smart-nonce: eb9f81e7cee1c000
```

## （二）Http GET
请求数据由http headers、url查询字符串组成，url查询字符串为实际请求的参数。
如http://localhost:10010/api/open/user/loginInfo/queryById?id=100

## （三）Http POST
请求数据采用json格式，通过流的形式传输。
请求数据由http headers、http body两部分组成，http body部分为请求的实际参数（json格式）。

### 1、请求数据组成
**http body部分**
```
{
    "products": [{
        "buyCount": 1,
        "productId": 4
	}]
}
```

### 2、响应对象Resp组成
```
{
	"head": {
		"transactionId": null,
		"code": "100200",
		"msg": "成功",
		"timestamp": 0
	},
	"body": {
		"id": "2",
		"name": "手机",
		"price": "1200"
	},
	"sign": "109ad1a8e05f8de345e6d780f09b001e97dc3d6fa9bbbe6936edb2b75a81864ac3b0b071e093af001fbffa479217540138b98f6f165e8246dd25a2536649f1f6"
}
```
## （四）加密、签名
### 1、签名、加密的key传递
```
1、C（客户端）请求S（服务端）；
2、S端随机产生两对rsa公钥、私钥（clientPriKey、serverPubKey；clientPubKey、serverPriKey），以及token，并返回token、clientPubKey、clientPriKey给C端；
3、C端保存“token、clientPubKey、clientPriKey”，并随机生成aes加密的key；
4、C端将aesKey用clientPubKey加密，用clientPriKey签名并发送给S端；
5、S端校验签名并解密，保存aesKey；
6、后续C端与S端通信，将会用aesKey加解密；C端用clientPriKey签名，用clientPubKey校验签名；S端用serverPriKey签名，用serverPubKey校验签名。
```
### 2、请求方
#### 1.请求参数
```
Http Get请求方式 ：
1.url查询字符串中的参数以json的格式组装得到查询的json串；
2.sign = RSA签名（“httpmethod + http headers（按自然排序的json串） + url查询json串”组成）

Http Post请求方式 ：
1.将http body部分的数据json化；
2.AES加密body的json串；
3.sign = RSA签名（“httpmethod + http headers（按自然排序的json串） + AES加密body的json串”）
```
#### 2.返回结果
Http Get、Http Post方式响应信息加密、签名相同。
```
1.校验签名是否正确；
2.解密数据
head = AES解密(head的json串)
body = AES解密(body json串)
```

### 3、响应方
#### 1.请求入参
```
Http Get请求方式 ：
校验签名是否正确
sign = RSA签名校验（“httpmethod + http headers（按自然排序的json串） + url查询的json串”组成，sign， 公钥）

Http Post请求方式 ：
1.校验签名是否正确
  sign = RSA签名校验（“httpmethod + http headers（按自然排序的json串） + AES加密body的json串”， 公钥）
2.AES解密body的json串；
```
#### 2.响应结果
Http Get、Http Post请求方式响应信息加密、签名相同。
```
head = AES加密(head的json串)
body = AES加密(body json串)
sign = RSA签名签名(AES加密(head的json串) + AES加密(body json串))
```

# 四、相关说明
## （一）服务合并遇到的问题
单个服务以jar的形式，通过maven引入合并服务中。在单体服务中，feign接口通过http请求；服务合并后，feign接口通过内部进程的方式通信。
### 1、多数据源冲突
```
1. 定义单数据源properties对象SingleDataSourceProperties，多数据源配置数据以Map<String, SingleDataSourceProperties>的形式从yml文件中读取；
2. 手动（通过new方式）构建所有需要的bean对象；
3. 手动将bean注入到容器中。
```
**多数据源配置示例：**
```
smart:
  data-sources:
	product:
	  url: jdbc:mysql://127.0.0.1:3306/demo_product?characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai
	  username: root
	  password: 123456
	  mapper-interface-location: com.liyulin.demo.mall.product.mapper
	  mapper-xml-location: classpath*:com/liyulin/demo/mall/product/mybatis/**.xml
	order:
	  url: jdbc:mysql://127.0.0.1:3306/demo_order?characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=Asia/Shanghai
	  username: root
	  password: 123456
	  mapper-interface-location: com.liyulin.demo.mall.order.mapper
	  mapper-xml-location: classpath*:com/liyulin/demo/mall/order/mybatis/**.xml
```
### 2、rpc与rpc实现类冲突
```
自定义条件注解封装FeignClient。使其在单体服务时，rpc走feign；在合体服务时，rpc走内部进程通信。
```
### 3、yaml文件的自动加载
```
自定义注解YamlScan，用来加载配置的yaml文件（支持正则匹配）。通过SPI机制，在spring.factories文件中添加EnvironmentPostProcessor的实现类，通过其方法参数SpringApplication获取启动类的信息，从而获取YamlScan注解配置的yaml文件信息。然后将yaml文件加到ConfigurableEnvironment中。
```
### 4、启动类注解冲突
```
自定义条件注解SmartSpringCloudApplicationCondition，只会让启动类标记的启动注解生效。
```
### 5、maven打包异常
```
合体服务打包时，单体服务依赖的包也打进单体服务jar。通过maven profiles解决
```

## （二）接口mock数据
接口通过切面拦截的方式，通过反射可以获取返回对象的所有信息，然后根据对象的属性类型，可以随机生成数据；对于特定要求的数据，可以制定mock规则，生成指定格式的数据。

## （三）测试
### 1、单元测试
利用单元测试，提高测试覆盖率。
### 2、集成测试
```
在集成测试下，关闭eureka，减少依赖。
依赖的服务rpc接口，通过mockito走挡板。
通过事务回滚，还原Test case对DB的修改。
```
### 3、系统测试

## （四）接口文档
接口文档由三个步骤自动生成：
1. 通过swagger自动生成接口文档的json格式数据；
2. 将json格式数据转化为markdown格式；
3. 在服务启动时将markdown格式数据上传（可根据配置的开关控制是否上传）到gitbook。

# 五、笔记
## （一）@EnableDiscoveryClient与@EnableEurekaClient区别
如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient。

## （二）spring boot引入其他的yaml文件
比如src/main/resources下有application-email.yml、application-mq.yml等文件，在yaml中添加
```
spring:
  profiles:
    include: email,mq
或
spring:
  profiles:
    include:
      - 子项1
      - 子项2
      - 子项3  
```

## （三）sleuth
### 5.3.1、log4j2集成sleuth
日志打印pattern中加入
```
[%X{X-B3-TraceId},%X{X-B3-SpanId},%X{X-B3-ParentSpanId},%X{X-Span-Export}]
```
各字段解释：
- TraceId为此次调用链共享id；
- SpanId本应用唯一id；
- ParentSpanId为上级应用唯一id；
- X-Span-Export是否是发送给Zipkin。

### 5.3.2、sleuth的原理
Spring Cloud Sleuth可以追踪10种类型的组件：async、Hystrix、messaging、websocket、rxjava、scheduling、web（Spring MVC Controller，Servlet）、webclient（Spring RestTemplate）、Feign、Zuul。

例如scheduling
原理是AOP（TraceSchedulingAspect、TraceSchedulingAutoConfiguration）处理Scheduled注解，只要是在IOC容器中的Bean带有@Scheduled注解的方法的调用都会被sleuth处理。

其他组件实现见包org.springframework.cloud.sleuth.instrument。

# 六、环境搭建
- 更改hosts文件，添加如下内容
```
  127.0.0.1       nodeA
```

- 安装redis，并启动
- 安装mysql，执行/docs/sql下脚本
- 安装seata服务端，下载地址https://github.com/seata/seata/releases
- 服务启动（先启动eureka，然后依次启动mall下服务）

# 七、注意事项
- 针对**jasypt**加密，所有的需要合并的单体服务的**jasypt.encryptor.password**的值必须相同，否则会报错。
- 关于seata
```
1、seata目前不支持“allowMultiQueries=true”，一次执行多条sql会报错。
2、seata服务端以file方式存储；以db方式存储会报错。
```

- 服务构建
```
单体服务构建：clean install
合体服务构建：clean install -P merge
```