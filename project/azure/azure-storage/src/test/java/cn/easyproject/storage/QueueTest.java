package cn.easyproject.storage;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;

public class QueueTest {
	 
	public static void main(String[] args) throws InvalidKeyException, URISyntaxException, StorageException {
	   // Create the queue client.
	   CloudQueueClient queueClient = StorageUtil.queueClientCreate(Constant.storageConnectionString);
	   
	   System.out.println("-----Create queue------");
	   // Create the queue,
	   CloudQueue queue = StorageUtil.queueCreate(queueClient,"myqueue");
	   System.out.println(queue);
	   
	   System.out.println("-----向队列添加消息------");
//	   StorageUtil.queueAddMessage(queue, "Hello, 你好！");
//	   StorageUtil.queueAddMessage(queue, "Hello, 你好2！");
//	   StorageUtil.queueAddMessage(queue, "Hello, 你好3！");
//	   StorageUtil.queueAddMessage(queue, "Hello, 你好4！");
//	   StorageUtil.queueAddMessage(queue, "Hello, 你好5！");
//	   StorageUtil.queueAddMessage(queue, "Hello, 你好6！");
	   
	   System.out.println("-----扫视下一条消息(扫视队列前面的消息，而不会从队列中删除它)------");
//	   System.out.println(StorageUtil.queuePeekMessage(queue));
//	   System.out.println(StorageUtil.queuePeekMessage(queueClient,"myqueue"));
	 
	   System.out.println("-----更改已排队消息的内容(扫视队列前面的消息，而不会从队列中删除它)------");
//	   StorageUtil.queueModify(queue, 32, "Hello, 你好！", "Hello, 你好！2", 0);
//	   System.out.println(StorageUtil.queuePeekMessage(queue));
//	   System.out.println(StorageUtil.queuePeekMessage(queueClient,"myqueue"));
	   
	   
	   System.out.println("-----获取队列长度------");
//	   System.out.println(String.format("Queue length: %d", StorageUtil.queueGetApproximateMessageCount(queue)));
//	   System.out.println(String.format("Queue length: %d", StorageUtil.queueGetApproximateMessageCount(queueClient,"myqueue")));

	   
	   System.out.println("-----retrieveMessage 时，将获得队列中的下一条消息。"
	   		+ "从 retrieveMessage 返回的消息变得对从此队列读取消息的任何其他代码不可见。"
	   		+ "默认情况下，此消息将持续 30 秒不可见------");
//	   System.out.println(queue.retrieveMessage()); //default: 30
	   System.out.println(queue.retrieveMessage(1, null, null).getMessageContentAsString());
	   System.out.println(queue.retrieveMessage(1, null, null).getMessageContentAsString());
	   System.out.println(queue.retrieveMessage(1, null, null).getMessageContentAsString());
	   System.out.println(queue.retrieveMessage(1, null, null).getMessageContentAsString());
	   System.out.println(queue.retrieveMessage(1, null, null).getMessageContentAsString());
	   System.out.println(queue.retrieveMessage(1, null, null).getMessageContentAsString());
	   
	   System.out.println("-----取出消息，并删除------");
//	   System.out.println(StorageUtil.queueRetrieveThenDelete(queue));
//	   System.out.println(StorageUtil.queueRetrieveThenDelete(queueClient,"myqueue"));
	   System.out.println(String.format("Queue length: %d", StorageUtil.queueGetApproximateMessageCount(queue)));

	   System.out.println("-----列出队列------");
	// Loop through the collection of queues.
	    for (CloudQueue queue2 : queueClient.listQueues())
	    {
	        // Output each queue name.
	        System.out.println(queue2.getName());
	    }
	
	    System.out.println("-----删除队列------");
//	    StorageUtil.queueDelete(queue);
	    StorageUtil.queueDelete(queueClient,"myqueue");
	    
	    for (CloudQueue queue2 : queueClient.listQueues())
	    {
	        // Output each queue name.
	        System.out.println(queue2.getName());
	    }
	}

}
