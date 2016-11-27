package cn.easyproject.storage;


import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//Include the following imports to use table APIs
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;

public class TableTest {
	
	
	public static void main(String[] args) throws InvalidKeyException, URISyntaxException, StorageException {
		 
		   // Create the table client.
		   CloudTableClient tableClient =StorageUtil.tableClientCreate(Constant.storageConnectionString);

		   // 创建表
		   CloudTable cloudTable=StorageUtil.tableCreate(tableClient, "people");
		   CloudTable cloudTable2=StorageUtil.tableCreate(tableClient, "user");
		   // 列出表
		   for (String table : tableClient.listTables())
		    {
		      // Output each table name.
		      System.out.println(table);
		   }
		   
		   // 向表中添加实体
		   // Create a new customer entity.
		    CustomerEntity customer = new CustomerEntity("Smith", "John");
		    customer.setEmail("Smith@contoso.com");
		    customer.setPhoneNumber("425-555-0101");
		    
//		    TableResult res=StorageUtil.insertOrReplace(cloudTable, customer);
//		    System.out.println(res);
		    
		    // 批量添加
		    List<TableEntity> tableEntites=new ArrayList<TableEntity>();
		    
		    // Create a customer entity to add to the table.
		    CustomerEntity customer1 = new CustomerEntity("Smith", "Jeff");
		    customer1.setEmail("Jeff@contoso.com");
		    customer1.setPhoneNumber("425-555-0104");

		   // Create another customer entity to add to the table.
		   CustomerEntity customer2 = new CustomerEntity("Smith", "Ben");
		   customer2.setEmail("Ben@contoso.com");
		   customer2.setPhoneNumber("425-555-0102");

		   // Create a third customer entity to add to the table.
		   CustomerEntity customer3 = new CustomerEntity("Smith", "Denise");
		   customer3.setEmail("Denise@contoso.com");
		   customer3.setPhoneNumber("425-555-0103");

		   tableEntites.add(customer1);
		   tableEntites.add(customer2);
		   tableEntites.add(customer3);
		    
//		   ArrayList<TableResult> res2=StorageUtil.batchInsertOrReplace(cloudTable, tableEntites);
//		   System.out.println(res2);
		   
		   // 检索分区中的所有实体
		   System.out.println("---------------检索分区中的所有实体-------------------");
		   // Define constants for filters.
		    final String PARTITION_KEY = "PartitionKey";
		    final String ROW_KEY = "RowKey";
		    final String TIMESTAMP = "Timestamp";
		    
		    Iterable<CustomerEntity> it=StorageUtil.tablePartitionQuery(cloudTable, PARTITION_KEY, "Smith", CustomerEntity.class);
//		    Iterable<CustomerEntity> it=StorageUtil.tablePartitionQuery(cloudTable, PARTITION_KEY, "Smith", CustomerEntity.class, QueryComparisons.EQUAL);

		    // Loop through the results, displaying information about the entity.
		    for (CustomerEntity entity : it) {
		        System.out.println(entity.getPartitionKey() +
		            " " + entity.getRowKey() +
		            "\t" + entity.getEmail() +
		            "\t" + entity.getPhoneNumber());
		   }
		    
		    
		    // 检索分区中的一部分实体
		    System.out.println("---------------检索分区中的一部分实体-------------------");
		 // Create a filter condition where the partition key is "Smith".
		    String partitionFilter = TableQuery.generateFilterCondition(
		       PARTITION_KEY,
		       QueryComparisons.EQUAL,
		       "Smith");

		    // Create a filter condition where the row key is less than the letter "E".
		    String rowFilter = TableQuery.generateFilterCondition(
		       ROW_KEY,
		       QueryComparisons.LESS_THAN,
		       "E");

		    // Combine the two conditions into a filter expression.
		    String combinedFilter = TableQuery.combineFilters(partitionFilter,
		        Operators.AND, rowFilter);
		    
		    System.out.println(combinedFilter);
	
		    Iterable<CustomerEntity> it2=StorageUtil.tableQueryByCombinedFilter(cloudTable, CustomerEntity.class, combinedFilter);
		    
		    for (CustomerEntity entity : it2) {
		        System.out.println(entity.getPartitionKey() +
		            " " + entity.getRowKey() +
		            "\t" + entity.getEmail() +
		            "\t" + entity.getPhoneNumber());
		   }
		    
		    System.out.println("---------------检索单个实体-------------------");
		    // 在查询中指定分区键和行键是从表服务中检索单个实体的最快方法
		    CustomerEntity entity =StorageUtil.tableQueryOneEntity(cloudTable, CustomerEntity.class, "Smith", "Denise");
		 // Output the entity.
		    if (entity != null)
		    {
		        System.out.println(entity.getPartitionKey() +
		            " " + entity.getRowKey() +
		            "\t" + entity.getEmail() +
		            "\t" + entity.getPhoneNumber());
		   }
		    
		    System.out.println("---------------修改实体-------------------");
		    
//		    entity.setEmail("xxxx@xxx.com");
//		    TableResult res=StorageUtil.tableReplaceEntity(cloudTable, entity);
//		    System.out.println(res.getResult());
		    
		    System.out.println("---------------投影：查询实体属性子集(可减少带宽并提高查询性能，尤其适用于大型实体)-------------------");
		   
		    String[] columns=new String[] {"Email","PhoneNumber"};
		    
		    // User define resolver
//		    EntityResolver<String[]> resolver = new EntityResolver<String[]>() {
//		        @Override
//		        public String[] resolve(String PartitionKey, String RowKey, Date timeStamp, HashMap<String, EntityProperty> properties, String etag) {
//		            return new String[]{properties.get("Email").getValueAsString(), properties.get("PhoneNumber").getValueAsString()};
//		        }
//		    };
//		    
//		    Iterable<String[]> s=StorageUtil.tableProjectionQuery(cloudTable, columns, CustomerEntity.class, resolver);
//		    
//		    for (String[] cols : s) {
//				System.out.println(cols[0]+", "+cols[1]);
//			}
		    
		    // Use Array result
//		    Iterable<String[]> s=StorageUtil.tableProjectionQueryResultArrays(cloudTable, columns, CustomerEntity.class);
//		    for (String[] cols : s) {
//				System.out.println(cols[0]+", "+cols[1]);
//			}
		    
		    // Use Map result
//		    Iterable<Map<String, String>> s=StorageUtil.tableProjectionQueryResultMaps(cloudTable, columns, CustomerEntity.class);
//		    for (Map<String, String> cols : s) {
//		    	System.out.println(cols.get("Email")+", "+cols.get("PhoneNumber"));
//		    }

		    // Use List result
		    Iterable<List<String>> s=StorageUtil.tableProjectionQueryResultLists(cloudTable, columns, CustomerEntity.class);
		    for (List<String> cols : s) {
		    	System.out.println(cols.get(0)+", "+cols.get(1));
		    }
		    
		    
		    System.out.println("---------------插入或更新实体的一部分-------------------");
			   
		    CustomerEntity customer5 = new CustomerEntity("Smith", "Ben");
		    customer5.setPhoneNumber("123456");
//		    StorageUtil.tableInsertOrMerge(cloudTable, customer5);

		    System.out.println("---------------删除实体-------------------");
//		    StorageUtil.tableDelete(cloudTable, CustomerEntity.class, "Smith", "Ben");
	}
	
	
	
}
