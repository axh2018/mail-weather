# 天气邮件

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

[!](https://img.shields.io/badge/maven-3.6.3-orange

每天定时发送一封天气信息的邮件

环境：Java+Python3

## 环境

Ubuntu Server：`apt-get install python3`，`apt-get install python-pip`，`pip install requests`

服务器JDK配置：

1. 进入`opt`目录`cd opt/`，下载`oraclejdk`

   ```shell
   wget https://repo.huaweicloud.com/java/jdk/8u202-b08/jdk-8u202-linux-x64.tar.gz
   ```

2. 解压压缩包

   ```shell
   tar -xvzf jdk-8u202-linux-x64.tar.gz
   ```

3. 删除压缩包

   ````shell
   rm  -rf jdk-8u202-linux-x64.tar.gz
   ````

4. 设置环境变量

   ```shell
   vim /etc/profile
   ```

   在文件末尾追加以下内容：

   ```shell
   export JAVA_HOME=/usr/java/jdk1.8.0_102
   export JRE_HOME=/usr/java/jdk1.8.0_102/jre
   export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
   export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
   ```

5. 更新配置信息

   ```shell
   source /etc/profile
   ```

6. 测试jdk是否安装成功

   ```shell
   java -version
   ```

   ```shell
   java version "1.8.0_202"                                                    
   Java(TM) SE Runtime Environment (build 1.8.0_202-b08)                       
   Java HotSpot(TM) 64-Bit Server VM (build 25.202-b08, mixed mode) 
   ```

   到此，Ubuntu安装OracleJdk就成功了。

## 使用

```shell
git clone https://github.com/axh2018/mail-weather.git
```

解压，然后将`src/main/java/com.mail/MailWeather.java`中的发件人账号密码改成自己的，这里采用(推荐)阿里云邮箱，因为他默认开启了`stmp`，阿里云邮箱申请地址： https://mailsso.mxhichina.com/aliyun/register?lang=zh_CN ，如果使用QQ邮箱，则需要改邮件服务器主机名`smtp.aliyun.com`为`stmp.qq.com`，并且QQ邮箱需要开启`POP3/SMTP服务`，密码则为QQ邮箱的密钥。

上面一切准备就绪后，`mvn clean`，`mvn compile`，`mvn package`

只需将生成的`jar`包和`get.py`上传到服务器的`/opt`目录下。

添加定时任务：

服务器`crontab -e`，添加下面两个定时任务

* `0 7 * * * python3 /opt/get.py > /opt/weather.txt `
* `0 8 * * * java -jar weathwer-1.0-SNAPSHOT.jar`

## Contributing

PRs and issues accepted.

## License

MIT © 2020 axh2018
