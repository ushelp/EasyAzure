# EasyAzure

EasyAzure 是一个针对微软 Azure 云平台的 Java 开发公共组件集合。主要提供 Https 认证生成安装,  NoSQL(DocumentDB), Storage(Blob, Table, Queue, File) 的支持。

最新版本:  `1.1.0-RELEASE`


## 组件简介

- **Azure-Util**: 提供 Java HTTPS 证书生成和安装

- **Azure-NoSQL**: 提供 DocumentDB 操作支持

- **Azure-Storage**: 提供 Blob, Table, Queue, File 对象操作支持

## Maven

```XML
<dependency>
	<groupId>cn.easyproject</groupId>
	<artifactId>azure-nosql</artifactId>
	<version>1.1.0-RELEASE</version>
</dependency>

<dependency>
	<groupId>cn.easyproject</groupId>
	<artifactId>azure-storage</artifactId>
	<version>1.1.0-RELEASE</version>
</dependency>

<dependency>
	<groupId>cn.easyproject</groupId>
	<artifactId>azure-util</artifactId>
	<version>1.1.0-RELEASE</version>
</dependency>
```

## Azure-Util

提供 Java HTTPS 证书生成和安装（`cn.easyproject.azure.util.InstallCert.java`）。

- HTTPS 认证常见问题

 ```JAVA
 PKIX path building failed: 
 javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: 
 sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
 ```

- 使用方法

 1. 运行代码，生成对应 cer 文件
 
	```JAVA
	InstallCert.installCert("bqf96rv4qj.database.chinacloudapi.cn:443");
	InstallCert.installCert("storagedemo.blob.core.chinacloudapi.cn:443","changeit");
	InstallCert.installCert("wamsshaclus001rest-hs.chinacloudapp.cn");
	InstallCert.installCert("wamsprodglobal001acs.accesscontrol.chinacloudapi.cn","changeit");
	InstallCert.installCert("nosqldemo.documents.azure.cn");
	```

	从控制台获得输出的待执行命令：
	
	```
	keytool -importcert -noprompt -alias bqf96rv4qj.database.chinacloudapi.cn -keystore "C:\Program Files\Java\jdk1.8.0_91\jre\lib\security/jssecacerts" -storepass changeit -file bqf96rv4qj.database.chinacloudapi.cn.cer
	keytool -importcert -noprompt -alias storagedemo.blob.core.chinacloudapi.cn -keystore "C:\Program Files\Java\jdk1.8.0_91\jre\lib\security/jssecacerts" -storepass changeit -file storagedemo.blob.core.chinacloudapi.cn.cer
	keytool -importcert -noprompt -alias wamsshaclus001rest-hs.chinacloudapp.cn -keystore "C:\Program Files\Java\jdk1.8.0_91\jre\lib\security/jssecacerts" -storepass changeit -file wamsshaclus001rest-hs.chinacloudapp.cn.cer
	keytool -importcert -noprompt -alias wamsprodglobal001acs.accesscontrol.windows.net -keystore "C:\Program Files\Java\jdk1.8.0_91\jre\lib\security/jssecacerts" -storepass changeit -file wamsprodglobal001acs.accesscontrol.windows.net.cer
	keytool -importcert -noprompt -alias nosqldemo.documents.azure.cn -keystore "C:\Program Files\Java\jdk1.8.0_91\jre\lib\security/jssecacerts" -storepass changeit -file nosqldemo.documents.azure.cn.cer
	``` 
	

 2. 拷贝执行命令，使用管理员身份执行(`administrator/sudo`)
 

## Azure-NoSQL

提供 `DocumentDB` 操作支持(`cn.easyproject.azure.documentdb.DocumentDBUtil`)。

```JAVA
create(Object pojoObject, DocumentClient documentClient, String databaseId, String collectionId)

createCollection(DocumentClient documentClient, String databaseId, String collectionId, boolean deleteExists)

createDB(DocumentClient documentClient, String databaseId, boolean deleteExists)

delete(DocumentClient documentClient, Document document)

deleteDB(DocumentClient documentClient, String databaseId)

deleteQueryDocuments(DocumentClient documentClient, String databaseId, String collectionId, String query)

query(DocumentClient documentClient, String databaseId, String collectionId, String query)

replace(DocumentClient documentClient, Document document, Object newPojoObject)
```

## Azure-Storage

提供 `Blob`, `Table`, `Queue`, `File` 对象操作支持。

```JAVA
/*
* Blob
*/

blobClientCreate(String connectionString blobName)

blobContainerCreate(CloudBlobClientserviceClient, String containerName)

blobDelete(CloudBlobClientserviceClient, String containerName, String blobName)

blobDelete(CloudBlobContainer cloudBlobContainer, String blobName)

blobDeleteContainer(CloudBlobClientserviceClient, String containerName)

blobDownload(CloudBlobClientserviceClient, String containerName, String blobName, File destinationFile)

blobDownload(CloudBlobContainer cloudBlobContainer, String blobName, File destinationFile)

blobSetPublic(CloudBlobClientserviceClient, String containerName)

blobSetPublic(CloudBlobContainer)

blobUpload(CloudBlobClientserviceClient, String containerName, String blobName, File destinationFile)

blobUpload(CloudBlobContainer cloudBlobContainer, String blobName, File destinationFile)

/*
* File
*/

fileClientCreate(String connectionString)

fileCreateDirectory(CloudFileClient fileClient, String shareName, String fileName)

fileCreateDirectory(CloudFileShare share, String directioyName)

fileDelete(CloudFileClient fileClient, String shareName, String fileName)

fileDelete(CloudFileClient fileClient, String shareName, String filePath, String fileName)

fileDelete(CloudFileShare share, String directioyName)

fileDelete(CloudFileShare share, String directioyName, String fileName)

fileDeleteShare(CloudFileShare share, String directioyName)

fileDirectoryDelete(CloudFileClient fileClient, String shareName, String fileName)

fileDirectoryDelete(CloudFileShare share, String directioyName)

fileDownload(CloudFileClient fileClient, String shareName, String fileName)

fileDownload(CloudFileClient fileClient, String shareName, String filePath, String fileName)
fileDownload(CloudFileShare share, String directioyName)

fileDownload(CloudFileShare share, String directioyName, String fileName)

fileShareCreate(CloudFileShare share, String directioyName)

fileUpload(CloudFileClient fileClient, String shareName, String filePath, String fileName)

fileUpload(CloudFileClient fileClient, String shareName, String directory, String filePath, String fileName)

fileUpload(CloudFileShare share, String directioyName, String fileName)

fileUpload(CloudFileShare share, String directioyName, String filePath, String fileName)

/*
* Queue
*/

queueAddMessage(CloudQueue queue, String)

queueAddMessage(CloudQueue queue, String message, final int timeToLiveInSeconds,final int initialVisibilityDelayInSeconds)

queueAddMessage(CloudQueueClient queueClient, String queueName, String)

queueAddMessage(CloudQueueClient queueClient, String queueName, String message, final int timeToLiveInSeconds,final int initialVisibilityDelayInSeconds)

queueClientCreate(String connectionString)

queueCreate(CloudQueueClient queueClient, String queueName)

queueDelete(CloudQueue queue)

queueDelete(CloudQueueClient queueClient, String queueName)

queueGetApproximateMessageCount(CloudQueue queue)

queueGetApproximateMessageCount(CloudQueueClient queueClient, String queueName)

queueModify(CloudQueue queue, int maxNumberOfMessageToPeek, String originMessage,String newMessage, final int visibilityTimeoutInSeconds)

queueModify(CloudQueueClient queueClient, String queueName, int maxNumberOfMessageToPeek, String originMessage,String newMessage, final int visibilityTimeoutInSeconds)

queuePeekMessage(CloudQueue queue)

queuePeekMessage(CloudQueueClient queueClient, String queueName)

queueRetrieveThenDelete(CloudQueue queue)

queueRetrieveThenDelete(CloudQueueClient queueClient, String queueName)

/*
* Table
*/

tableBatchInsertOrReplace(CloudTable cloudTable, List<TableEntity> tableEntites)

tableBatchInsertOrReplace(CloudTableClient tableClient, String tableName, List<TableEntity> tableEntites)

tableClientCreate(String connectionString)

tableCreate(CloudTableClient tableClient, String tableName)

tableDelete(CloudTable cloudTable, final Class<? extends TableEntity> clazzType, String partitionKey, String rowKey)

tableDelete(CloudTable cloudTable, TableEntity entity)

tableDelete(CloudTableClient tableClient, String tableName, final Class<? extends TableEntity> clazzType, String partitionKey, String rowKey)

tableInsertOrMerge(CloudTable cloudTable, TableEntity entity)
tableInsertOrMerge(CloudTableClient tableClient, String tableName, TableEntity entity)

tableInsertOrReplace(CloudTable cloudTable, TableEntity entity)

tableInsertOrReplace(CloudTableClient tableClient, String tableName, TableEntity entity)

tablePartitionQuery(CloudTable cloudTable, String partitionKey, String partitionName, Class<T> clazzType)

tablePartitionQuery(CloudTable cloudTable, String partitionKey, String partitionName, Class<T> clazzType, String)

tablePartitionQuery(CloudTableClient tableClient, String tableName, String partitionKey, String partitionName, Class<T> clazzType)

tablePartitionQuery(CloudTableClient tableClient, String tableName, String partitionKey, String partitionName, Class<T> clazzType, String queryComparisons)

tableProjectionQuery(CloudTable cloudTable, final String[] columns, final Class<? extends TableEntity> clazzType, EntityResolver<R> resolver)

tableProjectionQuery(CloudTableClient cloudTableClient, String tableName, final String[] columns, final Class<? extends TableEntity> clazzType, EntityResolver<R> resolver)

tableProjectionQueryResultArrays(CloudTable cloudTable, final String[] columns, final Class<? extends TableEntity> clazzType)

tableProjectionQueryResultArrays(CloudTableClient cloudTableClient, String tableName, final String[] columns, final Class<? extends TableEntity> clazzType)

tableProjectionQueryResultLists(CloudTable cloudTable, final String[] columns, final Class<? extends TableEntity> clazzType)

tableProjectionQueryResultLists(CloudTableClient cloudTableClient, String tableName, final String[] columns, final Class<? extends TableEntity> clazzType)

tableProjectionQueryResultMaps(CloudTable cloudTable, final String[] columns, final Class<? extends TableEntity> clazzType)

tableProjectionQueryResultMaps(CloudTableClient cloudTableClient, String tableName, final String[] columns, final Class<? extends TableEntity> clazzType)

tableQueryByCombinedFilter(CloudTable cloudTable, Class<T> clazzType, String combinedFilter)

tableQueryByCombinedFilter(CloudTableClient cloudTableClient, String tableName, Class<T> clazzType, String combinedFilter)

tableQueryOneEntity(CloudTable cloudTable, Class<T> clazzType, String partitionKey, String rowKey)

tableQueryOneEntity(CloudTableClient tableClient, String tableName, Class<T> clazzType, String partitionKey, String rowKey)

tableReplaceEntity(CloudTable cloudTable, TableEntity entity)

tableReplaceEntity(CloudTableClient tableClient, String tableName, TableEntity entity)
```


## END
### [官方主页](http://www.easyproject.cn/easyazure/zh-cn/index.jsp '官方主页')

[留言评论](http://www.easyproject.cn/easyazure/zh-cn/index.jsp#donation '留言评论')

如果您有更好意见，建议或想法，请联系我。

### [The official home page](http://www.easyproject.cn/easyazure/en/index.jsp 'The official home page')

[Comments](http://www.easyproject.cn/easyazure/en/index.jsp#donation 'Comments')

If you have more comments, suggestions or ideas, please contact me.



Email：<inthinkcolor@gmail.com>

[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")



**支付宝钱包扫一扫捐助：**

我们相信，每个人的点滴贡献，都将是推动产生更多、更好免费开源产品的一大步。

**感谢慷慨捐助，以支持服务器运行和鼓励更多社区成员。**

<img alt="支付宝钱包扫一扫捐助" src="http://www.easyproject.cn/images/s.png"  title="支付宝钱包扫一扫捐助"  height="256" width="256"></img>



We believe that the contribution of each bit by bit, will be driven to produce more and better free and open source products a big step.

**Thank you donation to support the server running and encourage more community members.**

[![PayPal](http://www.easyproject.cn/images/paypaldonation5.jpg)](https://www.paypal.me/easyproject/10 "Make payments with PayPal - it's fast, free and secure!")

