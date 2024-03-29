# Guide-Map

##  大一暑期校园导航系统 JAVA SWING MYSQL项目


### 数据库使用Mysql 数据库备份文件请使用主目录下MYSQL文件夹下SQLyog的工具恢复guide.map文件

### 新增功能:

##### 1,记住密码,在普通用户登录时可选择记住密码,在下次登陆时,密码会在密码框内以圆点方式出现.

##### 2,自动登陆,用户登陆时可选择自动登录,在程序下次启动时会自动登陆至改用户,可在用户界面注销登陆,注销后会自动取消自动登陆的选中,选中自动登陆的同时会附带添加记住密码.

##### 3,优化界面设计,添加背景.优化各组件大小.

##### 4,新增输入框输入事件,在注册,登陆时均只允许输入字母以及数字,避免出现非法字符的干扰.

##### 5,增加长度限制,限制输入用户名及密码输入长度上限.

##### 6,注册逻辑优化:添加验证二次密码功能,用户名验重.

##### 7,在登录时,用户名输入框会获得以及登陆的用户名称,方便下次登陆.

##### 8,密码使用MD5加密,加密后存储至数据库中.

![](https://img-blog.csdnimg.cn/20190727091359702.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTg4NzQ0,size_14,color_FFFFFF,t_70)

### 用户端:(用户可查询两建筑之间的最短路径,查询地点基于模糊搜索,全程可鼠标操作,无需键盘,在鼠标点击地图时,底下文本框自动更新地点信息,右侧出发地以及到达地随左右键点击自动更新)

### 新增功能:

##### 1,新增星标建筑功能,星标建筑只可由已登陆用户设置,在设置星标建筑后,在下次用户登陆时,坐标点会自动跟踪至星标建筑,及方便查找路线.(注:星标建筑功能可用户决定是否关闭,关闭后星标建筑图标不会显示,无法设置星标建筑).

##### 2,增加注销功能,用户可注销选择其他用户登陆.

##### 3,增加道路信息显示,由鼠标左右键控制,在没有道路时获得提示信息.

##### 4,新增三种线路,共四种,最短,最快,最美,最绿.优化算法设计,由Floyd算法改写为Dijkstra算法,优化时间复杂度.

##### 5,新增鼠标事件,用户左右键坐标点不允许自由自动,一定会位于建筑上方.

##### 6,界面优化,大幅提升用户体验,建筑不同类型会决定图片的类型,星标建筑由不同颜色图标提示,优化路径颜色,路径透明,不会遮挡建筑名称.设置背景图片,图片风格与登陆界面一致.

##### 7,新增修改密码功能,修改密码需原密码,需验证新密码.



 ![](https://img-blog.csdnimg.cn/20190727092009586.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTg4NzQ0,size_16,color_FFFFFF,t_70)
 
 ![](https://img-blog.csdnimg.cn/20190727092726585.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTg4NzQ0,size_16,color_FFFFFF,t_70)

### 管理员登陆请使用账号admin 密码123456

### 管理员端使用界面(实现了建筑的添加,删除,修改位置,修改资料,以及实现了增加两建筑之间的路,删除一条路)

### 新增功能:

##### 1,实现全程连接数据库,将图片存入数据库,无需本地文件.

##### 2,添加判断逻辑:在添加建筑时不允许与附近建筑重叠.

##### 3,添加非法输入判断:不允许过长输入,长度以及美化程度绿化程度的输入均只能输入数字,同时对所有的输入均会进行长度检验,长度根据具体输入设置.

##### 4,对删除建筑进行优化,原删除建筑会删除附带的路,但需要在下次启动程序时刷新,现在可直接刷新.

##### 5,新增逻辑判断,在查询或者修改道路信息时优先判断是否有路,没有路会提示是否创建.

##### 6,新增界面美化:按钮的名字均有所修改,优化管理员使用体验.

![](https://img-blog.csdnimg.cn/20190727093828786.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzMTg4NzQ0,size_16,color_FFFFFF,t_70)

##### 在用户查询两地点后,地点以及路线将标红,为用户提供舒适的体验.

### 整个程序的地图由AWT的绘图类绘制,可自动更新,在删除,添加操作后将进行重新绘图,无需重启程序.
