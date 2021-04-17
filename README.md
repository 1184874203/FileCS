# FileCS
本项目是：用JAVA 实现一个基于HTTP 协议的简易文件服务器Server 端和Client 端。
项目整体是使用了Maven的webapp架构，没有使用其他第三方框架，服务端是用最传统的Servlet实现的。

所有java文件都存放在了，src/main/java文件夹下面。

Server端的三个接口分别是：DownloadServlet  UploadServlet  getFileRecordServlet

Client端是  myClient：
其中有三个函数是实现对Server端三个接口的调用。

单元测试文件存放在test/java/Client下，测试文件中有详细的注释。

数据库方面使用了Mybatis进行业务处理。

测试上传文件接口的测试数据存放在src/UploadFile文件夹中

下载文件接口的下载文件存放在src/downloadFile文件夹中
