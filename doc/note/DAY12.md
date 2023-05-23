## 关于伪造的跨域攻击

Spring Security框架设计了“防止伪造的跨域攻击”的防御机制，所以，默认情况下，自定义的POST请求是不可用的，简单的解决方案就是在Spring Security的配置类中禁用这个防御机制即可，例如：

![image-20230523093203638](assets/image-20230523093203638.png)

**伪造的跨域攻击：**此类攻击原理是利用服务器端对客户端浏览器的“信任”来实现的！目前，主流的浏览器都是**多选项卡**模式的，假设在第1个选项卡中登录了某个网站，在第2个选项卡也打开这个网站的页面，就会被当作是已经登录的状态！基于这种特征，假设在第1个选项卡中登录了某个网上银行，在第2个选项卡中打开了某个坏人的网站（不是网上银行的网站），但是，在这个坏人的网站的页面中隐藏了一个使用网上银行进行转账的请求，这个请求在坏人的网站的页面刚刚打开时就自动发送出去了（自动发送：方法很多，例如将URL设置为某个不显示的`<img>`标签的`src`值），由于在第1个选项卡中已经登录了网上银行，从第2个选项卡中发出的请求也会被视为已经登录网上银行的状态，这就实现了一种攻击行为！当然，以上只是举例，真正的银行转账不会这么简单，例如还需要输入密码、手机验证码等等，但是，这种模式的攻击行为是确实存在的，由于使用另一个网站（坏人的网站）偷偷的实现的攻击，所以，称之为“伪造的跨域攻击”！

**典型的防御手段：**在Spring Security框架中，默认就开启了对于“伪造跨域攻击”的防御机制，其做法是在所有POST表单中隐藏一个具有“唯一性”的“随机值”，例如UUID值，当客户端提交请求时，必须提交这个UUID值，如果未提交，则服务器端将其直接视为攻击行为，将拒绝处理此请求！以Spring Security默认的登录表单为例：

![image-20230523094939116](assets/image-20230523094939116.png)

提示：此前“如果在打开登录页面后重启过服务器端，则第1次的输入是无效的”，也是因为这种防御机制，当打开登录页，服务器端生成了此次使用的UUID，但重启服务器后，服务器不再识别此前生成的UUID，所以，第1次的输出是无效的！








