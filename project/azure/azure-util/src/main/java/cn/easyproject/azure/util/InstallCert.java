package cn.easyproject.azure.util;

import java.io.*;

import java.security.*;
import java.security.cert.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.*;

/**
 * Java cert install Util: installCert(String host, String passphraseStr, boolean append)
 * host: xxxx.xxx:443
 * passphraseStr: changeit
 * append: false
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 */
public class InstallCert {
	
	  static Logger log = Logger.getLogger("InstallCert"); 
      
	public static void installCert(String host) {
		installCert(host,"changeit");
	}

	public static void installCert(String host, String passphraseStr) {
		 ConsoleHandler consoleHandler = new ConsoleHandler(); 
		
		 log.setLevel(Level.INFO); 
		 log.addHandler(consoleHandler);
		 log.config("=======================Start: "+host);
		
		char[] passphrase = passphraseStr.toCharArray();
		int port=443;
		
		if(host.contains(":")){
			String[] arr=host.split(":");
			host=arr[0];
			port=Integer.valueOf(arr[1]);
		}
		
		
		File file = new File("jssecacerts");
		final char SEPARATOR_CHAR = File.separatorChar;
		File dir = new File(System.getProperty("java.home") + SEPARATOR_CHAR + "lib" + SEPARATOR_CHAR + "security");
		if (file.isFile() == false) {
			file = new File("jssecacerts");
			if (file.isFile() == false) {
				file = new File(dir, "cacerts");
			}
		}
		

		try {
			log.config("Loading KeyStore " + file + "...");
			InputStream in = new FileInputStream(file);
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(in, passphrase);
			in.close();

			SSLContext context = SSLContext.getInstance("TLS");
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
			SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
			context.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory factory = context.getSocketFactory();

			log.config("Opening connection to " + host + ":" + port + "...");
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
			socket.setSoTimeout(10000);
			try {
				log.config("Starting SSL handshake...");
				socket.startHandshake();
				socket.close();
//				System.out.println();
				log.config("No errors, certificate is already trusted");
			} catch (SSLException e) {
				log.config("startHandshake errors");
//				e.printStackTrace(System.out);
//				return;
			}
			
			X509Certificate[] chain = tm.chain;
			if (chain == null) {
				log.warning("Could not obtain server certificate chain");
				return;
			}

			log.config("Server sent " + chain.length + " certificate(s):");

			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			for (int i = 0; i < chain.length; i++) {
				X509Certificate cert = chain[i];
//				log.config(" " + (i + 1) + " Subject " + cert.getSubjectDN());
//				log.config("   Issuer  " + cert.getIssuerDN());
				cert.getSubjectDN();
				cert.getIssuerDN();
				sha1.update(cert.getEncoded());
//				log.config("   sha1    " + toHexString(sha1.digest()));
				toHexString(sha1.digest());
				md5.update(cert.getEncoded());
//				log.config("   md5     " + toHexString(md5.digest()));
				toHexString(md5.digest());
			}
			
//			log.config("Enter certificate to add to trusted keystore or 'q' to quit: [1]");
			int k=0;
			
			X509Certificate cert = chain[k];
			String alias = host + "-" + (k + 1);
			ks.setCertificateEntry(alias, cert);

			
			OutputStream out = new FileOutputStream("jssecacerts");
			ks.store(out, passphrase);
			out.close();

//			log.config(cert);
			log.config("Added certificate to keystore 'jssecacerts' using alias '" + alias + "'");
			log.config("=======================End: "+host);
			
			Runtime run = Runtime.getRuntime();
//			run.exec("cmd /k shutdown -s -t 3600");
			// keytool -exportcert -alias [host]-1 -keystore jssecacerts -storepass changeit -file [host].cer
			// keytool -exportcert -alias woot.com-1 -keystore jssecacerts -storepass changeit -file woot.com.cer
			String cmd = "keytool -exportcert -noprompt -alias "+host+"-1 -keystore jssecacerts -storepass "+passphraseStr+" -file "+host+".cer";  
			run.exec(cmd);
			// keytool -importcert -alias [host] -keystore [path to system keystore] -storepass changeit -file [host].cer
			// (sudo) keytool -importcert -alias woot.com -keystore /usr/lib/jvm/java-6-sun-1.6.0.26/jre/lib/security/cacerts -storepass changeit -file woot.com.cer
			cmd = "keytool -importcert -noprompt -alias "+host+" -keystore \""+dir.getAbsolutePath()+"/jssecacerts"+"\" -storepass "+passphraseStr+" -file "+host+".cer";  
			log.config("### Please execute use sudo ###"); 
			System.out.println(cmd); 
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

	private static String toHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 3);
		for (int b : bytes) {
			b &= 0xff;
			sb.append(HEXDIGITS[b >> 4]);
			sb.append(HEXDIGITS[b & 15]);
			sb.append(' ');
		}
		return sb.toString();
	}

	private static class SavingTrustManager implements X509TrustManager {

		private final X509TrustManager tm;
		private X509Certificate[] chain;

		SavingTrustManager(X509TrustManager tm) {
			this.tm = tm;
		}

		public X509Certificate[] getAcceptedIssuers() {
			throw new UnsupportedOperationException();
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			throw new UnsupportedOperationException();
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			this.chain = chain;
			tm.checkServerTrusted(chain, authType);
		}
	}

}