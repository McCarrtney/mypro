首先建立数据库

```
mysql -u root -p

create database hospital;
use hospital;
source cloud_hospital.sql
source websocket.sql
```

然后连接数据库：

/src/main/resources/application.yml

![image-20210711052015662](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20210711052015662.png)

将password改为root的密码

/src/main/resources/db.properties

![image-20210711052110672](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20210711052110672.png)

将password改为root的密码

回到根目录中，执行

```
mvn package
java -jar target/cloud-0.0.1-SNAPSHOT.jar
```

可以正常运行。

