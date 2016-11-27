package cn.easyproject.storage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;
import com.microsoft.azure.storage.file.ListFileItem;
import com.microsoft.azure.storage.queue.CloudQueueClient;

public class FileTest {

	public static void main(String[] args) throws InvalidKeyException, URISyntaxException, StorageException, IOException {

		// Create the Cloud client.
		CloudFileClient fileClient = StorageUtil.fileClientCreate(Constant.storageConnectionString);
		
		// Get a reference to the file share
		String shareName="sampleshare";
		
		CloudFileShare share = StorageUtil.fileShareCreate(fileClient, shareName);
		
		System.out.println("-----创建目录-----");
//		StorageUtil.fileCreateDirectory(share, "logo");
//		StorageUtil.fileCreateDirectory(fileClient, "sampleshare", "logo2");
		
		System.out.println("-----列出共享中的文件和目录-----");
		CloudFileDirectory rootDir = share.getRootDirectoryReference();
		
		System.out.println("-----upload-----");
//		StorageUtil.fileUpload(share, "sample.txt", "sample1");
//		StorageUtil.fileUpload(fileClient, "sampleshare", "sample.txt", "sample2");
//		StorageUtil.fileUpload(share, "logo", "sample.txt",  "sample3");
//		StorageUtil.fileUpload(share, "logo3", "sample.txt",  "sample4");
		
		
		// delete file
//		StorageUtil.fileDelete(share, "sample1");
		
		// delete directoy
//		System.out.println(StorageUtil.fileDirectoryDelete(share, "logo2"));

		
		for ( ListFileItem fileItem : rootDir.listFilesAndDirectories() ) {
		    System.out.println(fileItem.getUri());
		}
//		for ( ListFileItem fileItem : rootDir.getDirectoryReference("logo").listFilesAndDirectories() ) {
//			System.out.println(fileItem.getUri());
//		}
//		for ( ListFileItem fileItem : rootDir.getDirectoryReference("logo2").listFilesAndDirectories() ) {
//			System.out.println(fileItem.getUri());
//		}
//		for ( ListFileItem fileItem : rootDir.getDirectoryReference("logo3").listFilesAndDirectories() ) {
//			System.out.println(fileItem.getUri());
//		}
		
		System.out.println("-----下载文件-----");
		
//		CloudFile file=StorageUtil.fileDownload(share,  "sample1");
//		System.out.println(file.downloadText());
		
//		CloudFile file2=StorageUtil.fileDownload(share,  "sample2");
//		System.out.println(file2.downloadText());
//		
//		CloudFile file3=StorageUtil.fileDownload(share, "logo", "sample3");
//		System.out.println(file3.downloadText());
//		
//		CloudFile file4=StorageUtil.fileDownload(share, "logo3", "sample4");
//		System.out.println(file4.downloadText());
		
		System.out.println("------删除共享------");
		
		StorageUtil.fileDeleteShare(fileClient, shareName);
		
		for ( ListFileItem fileItem : rootDir.listFilesAndDirectories() ) {
		    System.out.println(fileItem.getUri());
		}
	}

}
