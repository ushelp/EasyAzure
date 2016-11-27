package cn.easyproject.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import com.microsoft.azure.storage.queue.MessageUpdateFields;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.EntityProperty;
import com.microsoft.azure.storage.table.EntityResolver;
import com.microsoft.azure.storage.table.TableBatchOperation;
import com.microsoft.azure.storage.table.TableEntity;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableResult;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;

/**
 * Azure Storage Util. Support Blob, Table, Queue, File
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 */
public class StorageUtil {

	/*
	 * 
	 * Blob
	 * 
	 */
	
	/**
	 * Create CloudBlobClient
	 * @param connectionString
	 * @return
	 * @throws InvalidKeyException
	 * @throws URISyntaxException
	 */
	public static CloudBlobClient blobClientCreate(String connectionString) throws InvalidKeyException, URISyntaxException{
		// Retrieve storage account from connection-string.
		 CloudStorageAccount account = CloudStorageAccount.parse(connectionString);
		  // Create the table client.
		 return  account.createCloudBlobClient();
	}
	/**
	 * Create CloudFileClient
	 * @param connectionString
	 * @return
	 * @throws InvalidKeyException
	 * @throws URISyntaxException
	 */
	public static CloudFileClient fileClientCreate(String connectionString) throws InvalidKeyException, URISyntaxException{
		// Retrieve storage account from connection-string.
		CloudStorageAccount account = CloudStorageAccount.parse(connectionString);
		// Create the table client.
		return  account.createCloudFileClient();
	}
	/**
	 * Create CloudTableClient
	 * @param connectionString
	 * @return
	 * @throws InvalidKeyException
	 * @throws URISyntaxException
	 */
	public static CloudTableClient tableClientCreate(String connectionString) throws InvalidKeyException, URISyntaxException{
		// Retrieve storage account from connection-string.
		CloudStorageAccount account = CloudStorageAccount.parse(connectionString);
		// Create the table client.
		return  account.createCloudTableClient();
	}
	/**
	 * Create CloudQueueClient
	 * @param connectionString
	 * @return
	 * @throws InvalidKeyException
	 * @throws URISyntaxException
	 */
	public static CloudQueueClient queueClientCreate(String connectionString) throws InvalidKeyException, URISyntaxException{
		// Retrieve storage account from connection-string.
		CloudStorageAccount account = CloudStorageAccount.parse(connectionString);
		// Create the table client.
		return  account.createCloudQueueClient();
	}

	/**
	 * Create Blob Container
	 * 
	 * @param serviceClient
	 * @param containerName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static CloudBlobContainer blobContainerCreate(CloudBlobClient serviceClient, String containerName)
			throws URISyntaxException, StorageException {
		// Container name must be lower case.
		CloudBlobContainer container = serviceClient.getContainerReference(containerName);
		container.createIfNotExists();
		return container;
	}

	/**
	 * Delete Blob
	 * @param serviceClient
	 * @param containerName
	 * @param blobName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean blobDelete(CloudBlobClient serviceClient, String containerName, String blobName)
			throws URISyntaxException, StorageException, FileNotFoundException, IOException {
		// Retrieve reference 
		CloudBlobContainer container = serviceClient.getContainerReference(containerName);
		// Delete the blob.
		return blobDelete(container, blobName);
	}
	
	/**
	 * Delete Blob
	 * 
	 * @param container
	 * @param blobName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean blobDelete(CloudBlobContainer container, String blobName)
			throws URISyntaxException, StorageException, FileNotFoundException, IOException {
		// Retrieve reference to a blob named "myimage.jpg".
		CloudBlockBlob blob = container.getBlockBlobReference(blobName);
		// Delete the blob.
		return blob.deleteIfExists();
	}
	

	/**
	 * Delete Blob Container
	 * 
	 * @param serviceClient
	 * @param containerName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static boolean blobDeleteContainer(CloudBlobClient serviceClient, String containerName)
			throws URISyntaxException, StorageException {
		// Container name must be lower case.
		// Retrieve reference to a previously created container.
		CloudBlobContainer container = serviceClient.getContainerReference(containerName);
		// Delete the blob container.
		return container.deleteIfExists();
	}

	/**
	 * Download Blob
	 * 
	 * @param container
	 * @param blobName
	 * @param destinationFile
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void blobDownload(CloudBlobContainer container, String blobName, File destinationFile)
			throws URISyntaxException, StorageException, FileNotFoundException, IOException {
		CloudBlockBlob blob = container.getBlockBlobReference(blobName);
		blob.downloadToFile(destinationFile.getAbsolutePath());
	}
	
	/**
	 * Download Blob
	 * 
	 * @param serviceClient
	 * @param containerName
	 * @param blobName
	 * @param destinationFile
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void blobDownload(CloudBlobClient serviceClient, String containerName, String blobName, File destinationFile)
			throws URISyntaxException, StorageException, FileNotFoundException, IOException {
		CloudBlobContainer container = serviceClient.getContainerReference(containerName);
		 blobDownload(container, blobName, destinationFile);
	}

	/**
	 * Set Container Blob is public
	 * 
	 * @param container
	 * @throws StorageException
	 */
	public static void blobSetPublic(CloudBlobContainer container) throws StorageException {
		// Create a permissions object.
		BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
		// Include public access in the permissions object.
		containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
		// Set the permissions on the container.
		container.uploadPermissions(containerPermissions);
	}
	
	/**
	 * Set Container Blob is public
	 * @param serviceClient
	 * @param containerName
	 * @throws StorageException
	 * @throws URISyntaxException
	 */
	public static void blobSetPublic(CloudBlobClient serviceClient, String containerName) throws StorageException, URISyntaxException {
		CloudBlobContainer container = serviceClient.getContainerReference(containerName);
		blobSetPublic(container);
	}

	/**
	 * Upload Blob
	 * 
	 * @param container
	 * @param blobName
	 * @param sourceFile
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void blobUpload(CloudBlobContainer container, String blobName, File sourceFile)
			throws URISyntaxException, StorageException, FileNotFoundException, IOException {
		CloudBlockBlob blob = container.getBlockBlobReference(blobName);
		blob.upload(new FileInputStream(sourceFile), sourceFile.length());
	}
	
	/**
	 * Upload Blob
	 * @param serviceClient
	 * @param containerName
	 * @param blobName
	 * @param sourceFile
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void blobUpload(CloudBlobClient serviceClient, String containerName, String blobName, File sourceFile)
			throws URISyntaxException, StorageException, FileNotFoundException, IOException {
		CloudBlobContainer container = serviceClient.getContainerReference(containerName);
		blobUpload(container, blobName, sourceFile);
	}

	
	/*
	 * 
	 * File
	 * 
	 */

	/**
	 * Create directory
	 * @param fileClient
	 * @param shareName
	 * @param directioyName
	 * @param filePath
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static void fileCreateDirectory(CloudFileClient fileClient, String shareName, String directioyName) throws URISyntaxException, StorageException, IOException{
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		fileCreateDirectory(share, directioyName);
	}

	/**
	 * Create directory
	 * @param share
	 * @param directioyName
	 * @param filePath
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static void fileCreateDirectory(CloudFileShare share, String directioyName) throws URISyntaxException, StorageException, IOException{
		//Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		//Get a reference to the sampledir directory
		CloudFileDirectory sampleDir = rootDir.getDirectoryReference(directioyName);
		sampleDir.createIfNotExists();
//		if (sampleDir.createIfNotExists()) {
//		    System.out.println("sampledir created");
//		} else {
//		    System.out.println("sampledir already exists");
//		}
	}

	/**
	 * Download File from root
	 * @param fileClient
	 * @param shareName
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static CloudFile fileDownload(CloudFileClient fileClient, String shareName, String fileName) throws URISyntaxException, StorageException, IOException{
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		return fileDownload(share, fileName);
	}

	/**
	 * Download File
	 * @param fileClient
	 * @param shareName
	 * @param directoryName
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static CloudFile fileDownload(CloudFileClient fileClient, String shareName, String directoryName, String fileName) throws URISyntaxException, StorageException, IOException{
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		return fileDownload(share, directoryName, fileName);
	}

	/**
	 * Download File from root
	 * @param share
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static CloudFile fileDownload(CloudFileShare share, String fileName) throws URISyntaxException, StorageException, IOException{
		//Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		//Get a reference to the directory that contains the file
		//Get a reference to the file you want to download
		CloudFile file = rootDir.getFileReference(fileName);
		return file;
	}

	/**
	 * Download file
	 * @param share
	 * @param directoryName
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static CloudFile fileDownload(CloudFileShare share, String directoryName, String fileName) throws URISyntaxException, StorageException, IOException{
		//Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		//Get a reference to the directory that contains the file
		CloudFileDirectory sampleDir = rootDir.getDirectoryReference(directoryName);
		//Get a reference to the file you want to download
		CloudFile file = sampleDir.getFileReference(fileName);
		return file;
	}
	
	/**
	 * Delete File
	 * @param fileClient
	 * @param shareName
	 * @param directoryName
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static boolean fileDelete(CloudFileClient fileClient, String shareName, String directoryName, String fileName) throws URISyntaxException, StorageException, IOException{
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		return fileDelete(share, directoryName, fileName);
	}
	
	/**
	 * Delete File
	 * @param fileClient
	 * @param shareName
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static boolean fileDelete(CloudFileClient fileClient, String shareName,  String fileName) throws URISyntaxException, StorageException, IOException{
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		return fileDelete(share, fileName);
	}
	
	/**
	 * Delete File
	 * @param share
	 * @param directoryName
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static boolean fileDelete(CloudFileShare share, String directoryName, String fileName) throws URISyntaxException, StorageException, IOException{
		// Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		// Get a reference to the directory where the file to be deleted is in
		CloudFileDirectory containerDir = rootDir.getDirectoryReference(directoryName);
		CloudFile file = containerDir.getFileReference(fileName);
		return file.deleteIfExists() ;
	}
	
	/**
	 * Delete File
	 * @param share
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static boolean fileDelete(CloudFileShare share,  String fileName) throws URISyntaxException, StorageException, IOException{
		// Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		CloudFile file = rootDir.getFileReference(fileName);
		return file.deleteIfExists() ;
	}
	
	/**
	 * Delete directory
	 * @param share
	 * @param directory
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static boolean fileDirectoryDelete(CloudFileShare share, String directory) throws URISyntaxException, StorageException, IOException{
		// Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		return  rootDir.getDirectoryReference(directory).deleteIfExists() ;
	}
	
	/**
	 * Delete directory
	 * @param fileClient
	 * @param shareName
	 * @param directory
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static boolean fileDirectoryDelete(CloudFileClient fileClient,final String shareName, String directory) throws URISyntaxException, StorageException, IOException{
		// Get a reference to the root directory for the share.
		CloudFileShare share = fileClient.getShareReference(shareName);
		return  fileDirectoryDelete(share, directory);
	}
	
	/**
	 * Delete share
	 * @param fileClient
	 * @param shareName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static boolean fileDeleteShare(CloudFileClient fileClient,final String shareName ) throws URISyntaxException, StorageException{
		// Get a reference to the file share
		CloudFileShare share = fileClient.getShareReference(shareName);
		return share.deleteIfExists();
	}

	/**
	 * Create FileShare
	 * @param fileClient
	 * @param shareName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static CloudFileShare fileShareCreate(CloudFileClient fileClient, final String shareName) throws URISyntaxException, StorageException{
		CloudFileShare share = fileClient.getShareReference("sampleshare");
		share.createIfNotExists();
		return share;
	}

	/**
	 * Upload File
	 * @param fileClient
	 * @param shareName
	 * @param filePath
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static void fileUpload(CloudFileClient fileClient, String shareName, String filePath, String fileName) throws URISyntaxException, StorageException, IOException{
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		fileUpload(share, filePath, fileName);
	}

	/**
	 * Upload File to directory
	 * @param fileClient
	 * @param shareName
	 * @param directory
	 * @param filePath
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static void fileUpload(CloudFileClient fileClient, String shareName, String directory, String filePath, String fileName) throws URISyntaxException, StorageException, IOException{
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		fileUpload(share, directory, filePath, fileName);
	}

	/**
	 * Upload File
	 * @param share
	 * @param filePath
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static void fileUpload(CloudFileShare share, String filePath, String fileName) throws URISyntaxException, StorageException, IOException{
		//Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		CloudFile cloudFile = rootDir.getFileReference(fileName);
		cloudFile.uploadFromFile(filePath);
	}

	/**
	 * Upload File to directory
	 * @param share
	 * @param directory
	 * @param filePath
	 * @param fileName
	 * @throws URISyntaxException
	 * @throws StorageException
	 * @throws IOException
	 */
	public static void fileUpload(CloudFileShare share, String directory, String filePath, String fileName) throws URISyntaxException, StorageException, IOException{
		//Get a reference to the root directory for the share.
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		CloudFileDirectory dir=rootDir.getDirectoryReference(directory);
		dir.createIfNotExists();
		CloudFile cloudFile = dir.getFileReference(fileName);
		cloudFile.uploadFromFile(filePath);
	}


	
	/*
	 * 
	 * Queue
	 * 
	 */
	
	/**
	 * Add Message to queue
	 * 
	 * @param queue
	 * @param message
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static void queueAddMessage(CloudQueue queue, String message) throws URISyntaxException, StorageException {
		// Create a message and add it to the queue.
		CloudQueueMessage queueMessage = new CloudQueueMessage(message);
		queue.addMessage(queueMessage);
	}
	

	/**
	 * Add Message to queue
	 * 
	 * @param queue
	 * @param message
	 * @param timeToLiveInSeconds
	 * @param initialVisibilityDelayInSeconds
	 */
	public static void queueAddMessage(CloudQueue queue, String message, final int timeToLiveInSeconds,
			final int initialVisibilityDelayInSeconds) throws URISyntaxException, StorageException {
		// Create a message and add it to the queue.
		CloudQueueMessage queueMessage = new CloudQueueMessage(message);
		queue.addMessage(queueMessage, timeToLiveInSeconds, initialVisibilityDelayInSeconds, null, null);
	}

	/**
	 * Add Message to queue
	 * 
	 * @param queueClient
	 * @param queueName
	 * @param message
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static void queueAddMessage(CloudQueueClient queueClient, String queueName, String message) throws URISyntaxException, StorageException {
		// Create a message and add it to the queue.
		 // Retrieve a reference to a queue.
	    CloudQueue queue = queueClient.getQueueReference(queueName);

		queueAddMessage(queue, message);
	}

	/**
	 * Add Message to queue
	 *  
	 * @param queueClient
	 * @param queueName
	 * @param message
	 * @param timeToLiveInSeconds
	 * @param initialVisibilityDelayInSeconds
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static void queueAddMessage(CloudQueueClient queueClient, String queueName, String message, final int timeToLiveInSeconds,
			final int initialVisibilityDelayInSeconds) throws URISyntaxException, StorageException {
		// Create a message and add it to the queue.
		 CloudQueue queue = queueClient.getQueueReference(queueName);
		 queueAddMessage(queue, message, timeToLiveInSeconds, initialVisibilityDelayInSeconds);
	}

	
	/**
	 * Create Queue
	 * 
	 * @param queueClient
	 * @param queueName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static CloudQueue queueCreate(CloudQueueClient queueClient, final String queueName)
			throws URISyntaxException, StorageException {
		// Retrieve a reference to a queue.
		CloudQueue queue = queueClient.getQueueReference(queueName);
		// Create the queue if it doesn't already exist.
		queue.createIfNotExists();
		return queue;
	}

	/**
	 * Delete queue
	 * 
	 * @param queue
	 * @throws StorageException
	 * @throws URISyntaxException
	 */
	public static void queueDelete(CloudQueue queue) throws StorageException, URISyntaxException {
		// Retrieve a reference to a queue.
		// Delete the queue if it exists.
		queue.deleteIfExists();
	}

	/**
	 * Delete queue
	 * 
	 * @param queueClient
	 * @param queueName
	 * @throws StorageException
	 * @throws URISyntaxException
	 */
	public static void queueDelete(CloudQueueClient queueClient,String queueName) throws StorageException, URISyntaxException {
		  // Retrieve a reference to a queue.
	    CloudQueue queue = queueClient.getQueueReference(queueName);
	    // Delete the queue if it exists.
	    queueDelete(queue);
	}

	/**
	 * Get Approximate Message Count
	 * 
	 * @param queue
	 * @return
	 * @throws StorageException
	 */
	public static long queueGetApproximateMessageCount(CloudQueue queue) throws StorageException {
		// Download the approximate message count from the server.
		queue.downloadAttributes();
		// Retrieve the newly cached approximate message count.
		long cachedMessageCount = queue.getApproximateMessageCount();
		return cachedMessageCount;
	}

	/**
	 * Get Approximate Message Count
	 * @param queueClient
	 * @param queueName
	 * @param queue
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException 
	 */
	public static long queueGetApproximateMessageCount(CloudQueueClient queueClient, String queueName) throws StorageException, URISyntaxException {
		 CloudQueue queue = queueClient.getQueueReference(queueName);
		return queueGetApproximateMessageCount(queue);
	}


	/**
	 * Modify Queue message
	 * 
	 * @param queue
	 * @param maxNumberOfMessageToPeek
	 * @param originMessage
	 * @param newMessage
	 * @param visibilityTimeoutInSeconds
	 * @throws StorageException
	 */
	public static void queueModify(CloudQueue queue, int maxNumberOfMessageToPeek, String originMessage,
			String newMessage, final int visibilityTimeoutInSeconds) throws StorageException {
		// Loop through the messages in the queue.
		for (CloudQueueMessage message : queue.retrieveMessages(maxNumberOfMessageToPeek, 1,
				null, null)) {
			// Check for a specific string.
			if (message.getMessageContentAsString().equals(originMessage)) {
				// Modify the content of the first matching message.
				message.setMessageContent(newMessage);
				// Set it to be visible in 30 seconds.
				EnumSet<MessageUpdateFields> updateFields = EnumSet.of(MessageUpdateFields.CONTENT,
						MessageUpdateFields.VISIBILITY);
				// Update the message.
				queue.updateMessage(message, visibilityTimeoutInSeconds, updateFields, null, null);
				break;
			}
		}
	}
	
	/**
	 * Modify Queue message
	 * @param queueClient
	 * @param queueName
	 * @param maxNumberOfMessageToPeek
	 * @param originMessage
	 * @param newMessage
	 * @param visibilityTimeoutInSeconds
	 * @throws StorageException
	 * @throws URISyntaxException 
	 */
	public static void queueModify(CloudQueueClient queueClient, String queueName, int maxNumberOfMessageToPeek, String originMessage,
			String newMessage, final int visibilityTimeoutInSeconds) throws StorageException, URISyntaxException {
		 CloudQueue queue = queueClient.getQueueReference(queueName);
		 queueModify(queue, maxNumberOfMessageToPeek, originMessage, newMessage, visibilityTimeoutInSeconds);
	}
	
	

	/**
	 * Peek queue Message
	 * 
	 * @param queue
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static String queuePeekMessage(CloudQueue queue) throws URISyntaxException, StorageException {
		// Peek at the next message.
		CloudQueueMessage peekedMessage = queue.peekMessage();
		// Output the message value.
		return (peekedMessage != null) ? peekedMessage.getMessageContentAsString() : null;
	}
	
	/**
	 * Peek queue Message
	 * @param queueClient
	 * @param queueName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static String queuePeekMessage(CloudQueueClient queueClient, String queueName) throws URISyntaxException, StorageException {
		// Peek at the next message.
		 CloudQueue queue = queueClient.getQueueReference(queueName);
		return queuePeekMessage(queue);
	}

	/**
	 * Retrieve Then Delete(Pop) message
	 * @param queue
	 * @return
	 * @throws StorageException
	 */
	public static CloudQueueMessage queueRetrieveThenDelete(CloudQueue queue) throws StorageException {
		CloudQueueMessage retrievedMessage = queue.retrieveMessage();
		if (retrievedMessage != null) {
			// Process the message in less than 30 seconds, and then delete the
			// message.
			queue.deleteMessage(retrievedMessage);
		}
		return retrievedMessage;
	}
	/**
	 * Retrieve Then Delete(Pop) message
	 * @param queueClient
	 * @param queueName
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException 
	 */
	public static CloudQueueMessage queueRetrieveThenDelete(CloudQueueClient queueClient, String queueName) throws StorageException, URISyntaxException {
		 CloudQueue queue = queueClient.getQueueReference(queueName);
		return queueRetrieveThenDelete(queue);
	}

	
	/*
	 * 
	 * Table
	 * 
	 */
	/**
	 * Batch insert or replace tableEntity
	 * 
	 * @param cloudTable
	 * @param tableEntites
	 * @return
	 * @throws StorageException
	 */
	public static ArrayList<TableResult> tableBatchInsertOrReplace(CloudTable cloudTable,
			List<TableEntity> tableEntites) throws StorageException {
		// Define a batch operation.
		TableBatchOperation batchOperation = new TableBatchOperation();
		for (TableEntity entity : tableEntites) {
			batchOperation.insertOrReplace(entity);
		}
		// Execute the batch of operations on the "people" table.

		return cloudTable.execute(batchOperation);
	}

	
	/**
	 * Batch insert or replace tableEntity
	 * @param tableClient
	 * @param tableName
	 * @param tableEntites
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException
	 */
	public static ArrayList<TableResult> tableBatchInsertOrReplace(CloudTableClient tableClient, String tableName,
			List<TableEntity> tableEntites) throws StorageException, URISyntaxException {
		
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		return tableBatchInsertOrReplace(cloudTable, tableEntites);
	}
	
	/**
	 * Create Table
	 * 
	 * @param tableClient
	 * @param tableName
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static CloudTable tableCreate(CloudTableClient tableClient, String tableName)
			throws URISyntaxException, StorageException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		cloudTable.createIfNotExists();
		return cloudTable;
	}
	
	/**
	 * Table delete
	 * @param cloudTable
	 * @param clazzType
	 * @param partitionKey
	 * @param rowKey
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException 
	 */
	public static TableResult tableDelete(CloudTableClient tableClient, String tableName, final Class<? extends TableEntity> clazzType,
			String partitionKey, String rowKey) throws StorageException, URISyntaxException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		return tableDelete(cloudTable, clazzType, partitionKey, rowKey);
	}
	
	/**
	 * Table delete
	 * @param cloudTable
	 * @param clazzType
	 * @param partitionKey
	 * @param rowKey
	 * @return
	 * @throws StorageException
	 */
	public static TableResult tableDelete(CloudTable cloudTable, final Class<? extends TableEntity> clazzType,
			String partitionKey, String rowKey) throws StorageException {
		// Create an operation to retrieve the entity with partition key of
		// "Smith" and row key of "Jeff".
		TableOperation retrieve = TableOperation.retrieve(partitionKey, rowKey, clazzType);
		// Retrieve the entity with partition key and row key
		CustomerEntity entity = cloudTable.execute(retrieve).getResultAsType();
		
		// Create an operation to delete the entity.
		TableOperation delete = TableOperation.delete(entity);
		// Submit the delete operation to the table service.
		return cloudTable.execute(delete);
	}
	
	/**
	 * Delete tableEntity
	 * 
	 * @param cloudTable
	 * @param tableEntity
	 * @return
	 * @throws StorageException
	 */
	public static TableResult tableDelete(CloudTable cloudTable, TableEntity tableEntity) throws StorageException {
		// Create an operation to delete the entity.
		TableOperation delete = TableOperation.delete(tableEntity);

		// Submit the delete operation to the table service.
		return cloudTable.execute(delete);
	}
	

	
	/**
	 * Insert or merge tableEntity
	 * 
	 * @param cloudTable
	 * @param tableEntity
	 * @return
	 * @throws StorageException
	 */
	public static TableResult tableInsertOrMerge(CloudTable cloudTable, TableEntity tableEntity)
			throws StorageException {
		// Create an operation to add the new customer to the people table.
		TableOperation insertOrMerge = TableOperation.insertOrMerge(tableEntity);
		// Submit the operation to the table service.
		return cloudTable.execute(insertOrMerge);
	}
	
	/**
	 * Insert or merge tableEntity
	 * 
	 * @param cloudTable
	 * @param tableEntity
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException 
	 */
	public static TableResult tableInsertOrMerge(CloudTableClient tableClient, String tableName, TableEntity tableEntity)
			throws StorageException, URISyntaxException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		// Submit the operation to the table service.
		return tableInsertOrMerge(cloudTable, tableEntity);
	}

	/**
	 * Insert or replace tableEntity
	 * 
	 * @param cloudTable
	 * @param tableEntity
	 * @return
	 * @throws StorageException
	 */
	public static TableResult tableInsertOrReplace(CloudTable cloudTable, TableEntity tableEntity)
			throws StorageException {
		// Create an operation to add the new customer to the people table.
		TableOperation insertOrReplace = TableOperation.insertOrReplace(tableEntity);
		// Submit the operation to the table service.
		return cloudTable.execute(insertOrReplace);
	}
	
	/**
	 * Insert or replace tableEntity
	 * 
	 * @param tableClient
	 * @param tableName
	 * @param tableEntity
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException
	 */
	public static TableResult tableInsertOrReplace(CloudTableClient tableClient, String tableName, TableEntity tableEntity)
			throws StorageException, URISyntaxException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		// Submit the operation to the table service.
		return tableInsertOrReplace(cloudTable, tableEntity);
	}
	
	/**
	 * Query data by partation
	 * 
	 * @param cloudTable
	 * @param partitionKey
	 * @param partitionName
	 * @param clazzType
	 * @return
	 */
	public static <T extends TableEntity> Iterable<T> tablePartitionQuery(CloudTable cloudTable, String partitionKey,
			String partitionName, Class<T> clazzType) {
		// 检索分区中的所有实体
		// Create a filter condition where the partition key is "Smith".
		String partitionFilter = TableQuery.generateFilterCondition(partitionKey, QueryComparisons.EQUAL,
				partitionName);

		System.out.println(partitionFilter);

		// Specify a partition query, using "Smith" as the partition key filter.
		TableQuery<T> partitionQuery = TableQuery.from(clazzType).where(partitionFilter);

		// Loop through the results, displaying information about the entity.
		return cloudTable.execute(partitionQuery);
	}
	
	/**
	 * Query data by partation
	 * 
	 * @param tableClient
	 * @param tableName
	 * @param partitionKey
	 * @param partitionName
	 * @param clazzType
	 * @return
	 * @throws URISyntaxException
	 * @throws StorageException
	 */
	public static <T extends TableEntity> Iterable<T> tablePartitionQuery(CloudTableClient tableClient, String tableName, String partitionKey,
			String partitionName, Class<T> clazzType) throws URISyntaxException, StorageException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		// Loop through the results, displaying information about the entity.
		return tablePartitionQuery(cloudTable, partitionKey, partitionName, clazzType);
	}

	/**
	 * Query data by partation
	 * 
	 * @param cloudTable
	 * @param partitionKey
	 * @param partitionName
	 * @param clazzType
	 * @param queryComparisons
	 * @return
	 */
	public static <T extends TableEntity> Iterable<T> tablePartitionQuery(CloudTable cloudTable, String partitionKey,
			String partitionName, Class<T> clazzType, String queryComparisons) {
		// 检索分区中的所有实体
		// Create a filter condition where the partition key is "Smith".
		String partitionFilter = TableQuery.generateFilterCondition(partitionKey,
				// QueryComparisons.EQUAL,
				queryComparisons, partitionName);

		// Specify a partition query, using "Smith" as the partition key filter.
		TableQuery<T> partitionQuery = TableQuery.from(clazzType).where(partitionFilter);

		return cloudTable.execute(partitionQuery);
	}
	
	/**
	 * Query data by partation
	 * @param tableClient
	 * @param tableName
	 * @param partitionKey
	 * @param partitionName
	 * @param clazzType
	 * @param queryComparisons
	 * @return
	 * @throws StorageException 
	 * @throws URISyntaxException 
	 */
	public static <T extends TableEntity> Iterable<T> tablePartitionQuery(CloudTableClient tableClient, String tableName, String partitionKey,
			String partitionName, Class<T> clazzType, String queryComparisons) throws URISyntaxException, StorageException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);

		return tablePartitionQuery(cloudTable, partitionKey, partitionName, clazzType, queryComparisons);
	}
	
	
	/**
	 * Projection query use user define resolver
	 * 
	 * @param cloudTable
	 * @param columns
	 * @param clazzType
	 * @param resolver
	 * @return
	 */
	public static <R> Iterable<R> tableProjectionQuery(CloudTable cloudTable, final String[] columns,
			Class<? extends TableEntity> clazzType, EntityResolver<R> resolver) {

		// Define a projection query that retrieves only the Email property
		TableQuery<? extends TableEntity> projectionQuery = TableQuery.from(clazzType).select(columns);
		// Loop through the results, displaying the Email values.
		return cloudTable.execute(projectionQuery, resolver);
	}
	
	/**
	 * Projection query use user define resolver
	 * 
	 * @param tableClient
	 * @param tableName
	 * @param columns
	 * @param clazzType
	 * @param resolver
	 * @return
	 * @throws StorageException 
	 * @throws URISyntaxException 
	 */
	public static <R> Iterable<R> tableProjectionQuery(CloudTableClient tableClient, String tableName, final String[] columns,
			Class<? extends TableEntity> clazzType, EntityResolver<R> resolver) throws URISyntaxException, StorageException {
		
		CloudTable cloudTable = tableClient.getTableReference(tableName);

		return tableProjectionQuery(cloudTable, columns, clazzType, resolver);
	}
	
	
	/**
	 * Projection query return Array results
	 * 
	 * @param cloudTable
	 * @param columns
	 * @param clazzType
	 * @return
	 */
	public static Iterable<String[]> tableProjectionQueryResultArrays(CloudTable cloudTable, final String[] columns,
			Class<? extends TableEntity> clazzType) {

		// Define a projection query that retrieves only the Email property
		TableQuery<? extends TableEntity> projectionQuery = TableQuery.from(clazzType).select(columns);

		EntityResolver<String[]> resolver = new EntityResolver<String[]>() {
			@Override
			public String[] resolve(String PartitionKey, String RowKey, Date timeStamp,
					HashMap<String, EntityProperty> properties, String etag) {
				String[] values = new String[columns.length];
				for (int i = 0; i < columns.length; i++) {
					values[i] = properties.get(columns[i]).getValueAsString();
				}
				return values;
			}
		};
		// Loop through the results, displaying the Email values.
		return cloudTable.execute(projectionQuery, resolver);
	}
	
	/**
	 * Projection query return Array results
	 * 
	 * @param tableClient
	 * @param tableName
	 * @param columns
	 * @param clazzType
	 * @return
	 * @throws StorageException 
	 * @throws URISyntaxException 
	 */
	public static Iterable<String[]> tableProjectionQueryResultArrays(CloudTableClient tableClient, String tableName, final String[] columns,
			Class<? extends TableEntity> clazzType) throws URISyntaxException, StorageException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
	
		// Loop through the results, displaying the Email values.
		return tableProjectionQueryResultArrays(cloudTable, columns, clazzType);
	}
	

	/**
	 * Projection query return List results
	 * 
	 * @param cloudTable
	 * @param columns
	 * @param clazzType
	 * @return
	 */
	public static Iterable<List<String>> tableProjectionQueryResultLists(CloudTable cloudTable, final String[] columns,
			Class<? extends TableEntity> clazzType) {

		// Define a projection query that retrieves only the Email property
		TableQuery<? extends TableEntity> projectionQuery = TableQuery.from(clazzType).select(columns);

		EntityResolver<List<String>> resolver = new EntityResolver<List<String>>() {
			@Override
			public List<String> resolve(String PartitionKey, String RowKey, Date timeStamp,
					HashMap<String, EntityProperty> properties, String etag) {
				List<String> values = new ArrayList<String>();
				for (int i = 0; i < columns.length; i++) {
					values.add(properties.get(columns[i]).getValueAsString());
				}
				return values;
			}
		};
		// Loop through the results, displaying the Email values.
		return cloudTable.execute(projectionQuery, resolver);
	}
	
	/**
	 * Projection query return List results
	 * 
	 * @param tableClient
	 * @param tableName
	 * @param columns
	 * @param clazzType
	 * @return
	 * @throws StorageException 
	 * @throws URISyntaxException 
	 */
	public static Iterable<List<String>> tableProjectionQueryResultLists(CloudTableClient tableClient, String tableName, final String[] columns,
			Class<? extends TableEntity> clazzType) throws URISyntaxException, StorageException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		
		// Loop through the results, displaying the Email values.
		return tableProjectionQueryResultLists(cloudTable, columns, clazzType);
	}
	
	
	/**
	 * Projection query return Map results
	 * 
	 * @param cloudTable
	 * @param columns
	 * @param clazzType
	 * @return
	 */
	public static Iterable<Map<String, String>> tableProjectionQueryResultMaps(CloudTable cloudTable,
			final String[] columns, Class<? extends TableEntity> clazzType) {

		// Define a projection query that retrieves only the Email property
		TableQuery<? extends TableEntity> projectionQuery = TableQuery.from(clazzType).select(columns);

		EntityResolver<Map<String, String>> resolver = new EntityResolver<Map<String, String>>() {
			@Override
			public Map<String, String> resolve(String PartitionKey, String RowKey, Date timeStamp,
					HashMap<String, EntityProperty> properties, String etag) {

				Map<String, String> values = new HashMap<String, String>();
				for (int i = 0; i < columns.length; i++) {
					values.put(columns[i], properties.get(columns[i]).getValueAsString());
				}
				return values;
			}
		};
		// Loop through the results, displaying the Email values.
		return cloudTable.execute(projectionQuery, resolver);
	}
	
	/**
	 * Projection query return Map results
	 * 
	 * @param tableClient
	 * @param tableName
	 * @param columns
	 * @param clazzType
	 * @return
	 * @throws StorageException 
	 * @throws URISyntaxException 
	 */
	public static Iterable<Map<String, String>> tableProjectionQueryResultMaps(CloudTableClient tableClient, String tableName,
			final String[] columns, Class<? extends TableEntity> clazzType) throws URISyntaxException, StorageException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
	
		// Loop through the results, displaying the Email values.
		return tableProjectionQueryResultMaps(cloudTable, columns, clazzType);
	}

	/**
	 * Query data by combinedFilter string
	 * 
	 * @param cloudTable
	 * @param clazzType
	 * @param combinedFilter
	 * @return
	 */
	public static <T extends TableEntity> Iterable<T> tableQueryByCombinedFilter(CloudTable cloudTable,
			Class<T> clazzType, String combinedFilter) {
		TableQuery<T> rangeQuery = TableQuery.from(clazzType).where(combinedFilter);
		return cloudTable.execute(rangeQuery);
	}
	
	/**
	 * Query data by combinedFilter string
	 * 
	 * @param tableClient
	 * @param tableName
	 * @param clazzType
	 * @param combinedFilter
	 * @return
	 * @throws StorageException 
	 * @throws URISyntaxException 
	 */
	public static <T extends TableEntity> Iterable<T> tableQueryByCombinedFilter(CloudTableClient tableClient, String tableName,
			Class<T> clazzType, String combinedFilter) throws URISyntaxException, StorageException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		return tableQueryByCombinedFilter(cloudTable, clazzType, combinedFilter);
	}

	/**
	 * Query One Entity
	 * 
	 * @param cloudTable
	 * @param clazzType
	 * @param partitionKey
	 * @param rowKey
	 * @return
	 * @throws StorageException
	 */
	public static <T extends TableEntity> T tableQueryOneEntity(CloudTable cloudTable, Class<T> clazzType,
			String partitionKey, String rowKey) throws StorageException {
		TableOperation retrieve = TableOperation.retrieve(partitionKey, rowKey, clazzType);
		return cloudTable.execute(retrieve).getResultAsType();
	}
	
	/**
	 * Query One Entity
	 * @param tableClient
	 * @param tableName
	 * @param clazzType
	 * @param partitionKey
	 * @param rowKey
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException 
	 */
	public static <T extends TableEntity> T tableQueryOneEntity(CloudTableClient tableClient, String tableName, Class<T> clazzType,
			String partitionKey, String rowKey) throws StorageException, URISyntaxException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		return tableQueryOneEntity(cloudTable, clazzType, partitionKey, rowKey);
	}
	
	/**
	 * Replace Entity
	 * 
	 * @param cloudTable
	 * @param entity
	 * @return
	 * @throws StorageException
	 */
	public static TableResult tableReplaceEntity(CloudTable cloudTable, final TableEntity entity)
			throws StorageException {
		// Create an operation to replace the entity.
		TableOperation replaceEntity = TableOperation.replace(entity);
		// Submit the operation to the table service.
		return cloudTable.execute(replaceEntity);
	}
	
	/**
	 * Replace Entity
	 *  
	 * @param tableClient
	 * @param tableName
	 * @param entity
	 * @return
	 * @throws StorageException
	 * @throws URISyntaxException 
	 */
	public static TableResult tableReplaceEntity(CloudTableClient tableClient, String tableName, final TableEntity entity)
			throws StorageException, URISyntaxException {
		CloudTable cloudTable = tableClient.getTableReference(tableName);
		// Submit the operation to the table service.
		return tableReplaceEntity(cloudTable, entity);
	}
	
	

}
