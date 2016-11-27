package cn.easyproject.storage;
import java.io.*;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
public class BlobTest {
	
	 
	 public static void main(String[] args) {
		 try {
			 	CloudBlobClient serviceClient = StorageUtil.blobClientCreate(Constant.storageConnectionString);
	          
	            // Container name must be lower case.
	            CloudBlobContainer container = StorageUtil.blobContainerCreate(serviceClient, "myimages");

	            // Set Container to publish: 使上传的Bolob公开直接可以只读访问。默认为私有访问。
	            StorageUtil.blobSetPublic(container);
	            
	            // Upload an image file.
	            File sourceFile=new File("storage-concepts2.png");
//	            StorageUtil.uploadBlob(container, "storage-concepts", sourceFile);
	            
//	             Download the image file.
	            File destinationFile = new File(sourceFile.getParentFile(), "image2Download.png");
//	            StorageUtil.downloadBlob(container, "storage-concepts", destinationFile);
	        
	         // Loop over blobs within the container and output the URI to each of them.
	            for (ListBlobItem blobItem : container.listBlobs()) {
	               System.out.println(blobItem.getUri());
	           }
		 
//	            StorageUtil.deleteBlob(container, "storage-concepts");
	            
	            StorageUtil.blobDeleteContainer(serviceClient, "myimages");
		 }
//	        catch (FileNotFoundException fileNotFoundException) {
//	            System.out.print("FileNotFoundException encountered: ");
//	            System.out.println(fileNotFoundException.getMessage());
//	            fileNotFoundException.printStackTrace();
//	            System.exit(-1);
//	        }
	        catch (StorageException storageException) {
	            System.out.print("StorageException encountered: ");
	            System.out.println(storageException.getMessage());
	            storageException.printStackTrace();
	            System.exit(-1);
	            
	        }
	        catch (Exception e) {
	            System.out.print("Exception encountered: ");
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	            System.exit(-1);
	        }
	}
}
