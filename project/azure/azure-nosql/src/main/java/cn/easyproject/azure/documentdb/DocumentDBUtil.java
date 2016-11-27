package cn.easyproject.azure.documentdb;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;
import com.microsoft.azure.documentdb.RequestOptions;
import com.microsoft.azure.documentdb.ResourceResponse;

/**
 * Azure DocumentDB Util
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 */
public class DocumentDBUtil {
	
    // We'll use Gson for POJO <=> JSON serialization for this sample.
    // Codehaus' Jackson is another great POJO <=> JSON serializer.
    public static Gson gson = new Gson();
    
    /**
     * Create Database
     * @param documentClient
     * @param databaseId
     * @param deleteExists
     * @return
     * @throws DocumentClientException
     */
    public static Database createDB(DocumentClient documentClient,String databaseId,boolean deleteExists) throws DocumentClientException{
        if(deleteExists){
	        // Start from a clean state (delete database in case it already exists).
	        try {
	            documentClient.deleteDatabase("dbs/" + databaseId, null);
	        }
	        catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
        }

        // Define a new database using the id above.
        Database myDatabase = new Database();
        myDatabase.setId(databaseId);

        // Create a new database.
        myDatabase = documentClient.createDatabase(myDatabase, null)
                .getResource();
        System.out.println("Created a new database:");
        return myDatabase;

    }
    
    /**
     * Create Collection
     * @param documentClient
     * @param databaseId
     * @param collectionId
     * @param deleteExists
     * @return
     * @throws DocumentClientException
     */
    public static DocumentCollection createCollection(DocumentClient documentClient,String databaseId,String collectionId, boolean deleteExists) throws DocumentClientException{
    	 // Define a new collection using the id above.
        DocumentCollection myCollection = new DocumentCollection();
        myCollection.setId(collectionId);
     // Set the provisioned throughput for this collection to be 1000 RUs.
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.setOfferThroughput(1000);
        
        if(deleteExists){
        	documentClient.deleteCollection("dbs/" + databaseId + "/colls/" + collectionId, requestOptions);
        }

        // Create a new collection.
        myCollection = documentClient.createCollection(
                "dbs/" + databaseId, myCollection, requestOptions)
                .getResource();

        System.out.println("Created a new collection:");
        
        return myCollection;
    }
    
    /**
     * Create object
     * @param pojoObject
     * @param documentClient
     * @param databaseId
     * @param collectionId
     * @return
     * @throws DocumentClientException
     */
    public static Document create(Object pojoObject,DocumentClient documentClient,String databaseId,String collectionId) throws DocumentClientException{
    	  // Create an object, serialize it into JSON, and wrap it into a document.
        String json = gson.toJson(pojoObject);
        Document document = new Document(json);

        // Create the document.
        document = documentClient.createDocument(
                "dbs/" + databaseId + "/colls/" + collectionId, document, null, false)
                .getResource();

        System.out.println("Created document:");
        return document;
    }
    
    /**
     * query
     * @param documentClient
     * @param databaseId
     * @param collectionId
     * @param query
     * @return
     */
    public static List<Document> query(DocumentClient documentClient,String databaseId,String collectionId,String query){
    	  // Query documents
    	
        List<Document> results = documentClient
                .queryDocuments(
                        "dbs/" + databaseId + "/colls/" + collectionId,
                       query,
                        null).getQueryIterable().toList();
        
        return results;
    }

    /**
     * Replace
     * @param documentClient
     * @param document
     * @param newPojoObject
     * @return
     * @throws DocumentClientException
     */
    public static Document replace(DocumentClient documentClient,Document document, Object newPojoObject) throws DocumentClientException{
    	// Replace Document
    	document = documentClient.replaceDocument(
    			document.getSelfLink(),
                new Document(gson.toJson(newPojoObject)), null)
                .getResource();

        System.out.println("Replaced information");
       return document;
    }
    
    /**
     * Delete
     * @param documentClient
     * @param document
     * @return
     * @throws DocumentClientException
     */
    public static ResourceResponse<Document> delete(DocumentClient documentClient,Document document) throws DocumentClientException{
    	// Delete Document
    	ResourceResponse<Document> response= documentClient.deleteDocument(document.getSelfLink(), null);
        System.out.println("Deleted information");
       return response;
    }
  
    /**
     * Delete by query
     * @param documentClient
     * @param databaseId
     * @param collectionId
     * @param query
     * @return
     * @throws DocumentClientException
     */
    public static List<ResourceResponse<Document>> deleteQueryDocuments(DocumentClient documentClient,String databaseId,String collectionId,String query) throws DocumentClientException{
    	List<ResourceResponse<Document>> responses=new ArrayList<ResourceResponse<Document>>();
    	// Delete Document
    	List<Document> results=DocumentDBUtil.query(documentClient, databaseId, collectionId, query);
    	if(results.size()>0){
    		for (Document document : results) {
    			ResourceResponse<Document> response= documentClient.deleteDocument(document.getSelfLink(), null);
        		responses.add(response);
			}
    	}
    	System.out.println("Deleted informations");
    	return responses;
    }
    
    /**
     * Delete database
     * @param documentClient
     * @param databaseId
     * @return
     * @throws DocumentClientException
     */
    public static ResourceResponse<Database> deleteDB(DocumentClient documentClient,String databaseId) throws DocumentClientException{
    	// Replace Document
    	ResourceResponse<Database> response= documentClient.deleteDatabase("dbs/" + databaseId, null);
        System.out.println("Deleted database");
    	return response;
    }
    
    
    
    
}