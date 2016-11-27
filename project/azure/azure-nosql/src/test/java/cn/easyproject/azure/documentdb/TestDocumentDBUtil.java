package cn.easyproject.azure.documentdb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;
import com.microsoft.azure.documentdb.PartitionKey;
import com.microsoft.azure.documentdb.PartitionKeyDefinition;
import com.microsoft.azure.documentdb.RequestOptions;
import com.microsoft.azure.documentdb.ResourceResponse;

import cn.easyproject.azure.documentdb.DocumentDBUtil;
import cn.easyproject.azure.documentdb.UserInfo;

public class TestDocumentDBUtil {
	

   
	public static void main(String[] args) throws DocumentClientException {
		
		 // Instantiate a DocumentClient w/ your DocumentDB Endpoint and AuthKey.
        DocumentClient documentClient = new DocumentClient(Constant.END_POINT,
        		Constant.MASTER_KEY, ConnectionPolicy.GetDefault(),
                ConsistencyLevel.Session);

		
		// 1. create Database
//		Database myDatabase=DocumentDBUtil.createDB(documentClient, DATABASE_ID, true);
//		System.out.println(myDatabase.toString());
		
		// 2. create Database Collection
//		DocumentCollection myCollection = DocumentDBUtil.createCollection(documentClient, DATABASE_ID, COLLECTION_ID, true);
//		System.out.println(myCollection.toString());
		
		// 3. create document
//		UserInfo pojoObject = new UserInfo("1231", "Allen Brewer", "allen [at] contoso.com");
//		UserInfo pojoObject2 = new UserInfo("4561", "Lisa Andrews", "lisa [at] contoso.com");
//		Document docuemnt = DocumentDBUtil.create(pojoObject, documentClient, DATABASE_ID, COLLECTION_ID);
//		System.out.println(docuemnt.toString());
//		Document docuemnt2 = DocumentDBUtil.create(pojoObject2, documentClient, DATABASE_ID, COLLECTION_ID);
//		System.out.println(docuemnt2.toString());
		
		// 4. query document
		String query= "SELECT * FROM myCollection WHERE myCollection.email = 'allen [at] contoso.com'";
		 query= "SELECT * FROM myCollection";
		List<Document> results=DocumentDBUtil.query(documentClient, Constant.DATABASE_ID, Constant.COLLECTION_ID, query);
		System.out.println(results.toString());
		
		// 5. replace document
//		UserInfo allenPojo = DocumentDBUtil.gson.fromJson(results.get(0).toString(), UserInfo.class);
//		allenPojo.setName("Percy Bowman");
//		allenPojo.setEmail("Percy Bowman [at] contoso.com");
//		Document document=DocumentDBUtil.replace(documentClient, results.get(0), allenPojo);
//		System.out.println(document.toString());
        
//		// 6. delete document
//		query= "SELECT * FROM myCollection WHERE myCollection.id='456'";
//		List<ResourceResponse<Document>> responses=DocumentDBUtil.deleteQueryDocuments(documentClient, DATABASE_ID, COLLECTION_ID, query);
//		System.out.println(responses);
//		results=DocumentDBUtil.query(documentClient, DATABASE_ID, COLLECTION_ID, query);
////		System.out.println(results);
//		ResourceResponse<Document> response=DocumentDBUtil.delete(documentClient, results.get(0));
//		System.out.println(response);
		
//		// 7. delete database
//		ResourceResponse<Database> response2=DocumentDBUtil.deleteDB(documentClient, DATABASE_ID);
//		System.out.println(response2);
		
		// query all document
		 query= "SELECT * FROM myCollection";
		results=DocumentDBUtil.query(documentClient, Constant.DATABASE_ID, Constant.COLLECTION_ID, query);
		for (Document document : results) {
			UserInfo pojo = DocumentDBUtil.gson.fromJson(results.get(0).toString(), UserInfo.class);
			System.out.println(document.get("id")+", "+document.getId());
			System.out.println(pojo);
		}
		
		
		
	}
}
