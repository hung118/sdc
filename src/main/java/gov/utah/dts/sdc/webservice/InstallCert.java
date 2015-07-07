package gov.utah.dts.sdc.webservice;


import java.io.*;

import java.security.*;
import java.security.cert.*;

import javax.net.ssl.*;

public class InstallCert {

    public static void main(String[] args) throws Exception {
	//String host = "webservices.ucjis.utah.gov";
    String host = "ws-sb-test.ps.utah.gov";	
	int port = 443;
        String p = "changeit";
        String sliceName = "bemsapp";
        String jdkVersion = "jdk1.6.0";
        String cacertsLocation = "";
        
	char[] passphrase = p.toCharArray();

        String winCertLocation = "C:\\Program Files\\Java\\jdk1.6.0_04\\jre\\lib\\security\\cacerts";
        String linuxCertLocation = "/hosts/cms/java/jdk1.6.0_04/jre/lib/security/cacerts";

	BufferedReader reader =	new BufferedReader(new InputStreamReader(System.in));
        
        /* Try to determine cert location automatically */
            char SEP = File.separatorChar;
	    File dir = new File(System.getProperty("java.home") + SEP
		    + "lib" + SEP + "security");
	    File file = new File(dir, "cacerts");
          
	 //   if (file.isFile() == false) {
	 //	file = new File(dir, "cacerts");
	 //   }
      
       /* HAVE USER SELECT A KEYSTORE */
        boolean foundLocation = true;
        System.out.println("Is This The Keystore Location?: ["+file.toString()+"]");
        String locationline = reader.readLine().trim();
	if(locationline.length() > 0 && !locationline.equalsIgnoreCase("y")){
               foundLocation = false;
               System.out.println("......................................");
               System.out.println(" Ok, Lets try to figure this out.");
               System.out.println("......................................");
        }
        /* --------------------- */     
        
        if(!foundLocation){
	/* HAVE USER SELECT A KEYSTORE */
        
        boolean manual = false;
        System.out.println("Do you want to manually enter the cacerts location?: [n]");
        String manline = reader.readLine().trim();
        if(manline.length() > 0 && !manline.equalsIgnoreCase("n")){
            manual = true;
            System.out.println("................");
            System.out.println(" Manual Cacerts path");
            System.out.println(" Example: /hosts/cms/java/jdk1.6.0_07/jre/lib/security/cacerts");
            System.out.println("................");  
        }
        
        if(!manual){
        boolean windows = true;
        System.out.println("Is This Windows?: [y]");
        String winline = reader.readLine().trim();
	if(winline.length() > 0 && !winline.equalsIgnoreCase("y")){
               windows = false;
               System.out.println("................");
               System.out.println(" Assuming Linux");
               System.out.println("................");
        }
        /* --------------------- */
        
        
        /* HAVE USER SELECT A SLICE NAME */
        if (!windows) {
            System.out.println("What is your slice name?: [" + sliceName + "]");
            String sliceline = reader.readLine().trim();
            if (sliceline.length() > 0) {
                sliceName = sliceline;
            }
        }
        /* --------------------- */
        
        /* HAVE USER SELECT A SLICE NAME */
            System.out.println("What is your jdk?: [" + jdkVersion + "]");
            String jdkline = reader.readLine().trim();
            if (jdkline.length() > 0) {
                jdkVersion = jdkline;
            }
        /* --------------------- */
        
        /* Build Keystore location */
        if(windows){
            cacertsLocation = "C:\\Program Files\\Java\\"+jdkVersion+"\\jre\\lib\\security\\cacerts";
        } else {
            cacertsLocation = "/hosts/" + sliceName+ "/java/" +jdkVersion+"/jre/lib/security/cacerts";
        }
        /* --------------------- */
        
         } else {
            System.out.println("What is the path?: ");
            String pathline = reader.readLine().trim();
            if (pathline.length() > 0) {
                cacertsLocation = pathline;
            }
         }
        
        
      
        file = new File(cacertsLocation);
        if(file.isFile() == false) {
            System.out.println("Could not find "+ cacertsLocation);
            return;
        }
        
        }
        System.out.println("Using KeyStore: [" +file+ "]");
        
        /* HAVE USER SELECT HOST */
        System.out.println("Enter hostname: ["+host+"]");
	String hostline = reader.readLine().trim();
	if(hostline.length() > 0){
                host = hostline;
            }
        /* --------------------- */
        
        /* HAVE USER SELECT PORT */
        System.out.println("Enter port: ["+port+"]");
	String portline = reader.readLine().trim();
	try {
            if (portline.length() > 0) {
                port = Integer.parseInt(portline);
            }
        } catch (NumberFormatException e) {
            System.out.println("Port not valid number - KeyStore not changed");
            return;
        }
        /* --------------------- */
        
	InputStream in = new FileInputStream(file);
	KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
	ks.load(in, passphrase);
	in.close();

	SSLContext context = SSLContext.getInstance("TLS");
	TrustManagerFactory tmf =
	    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	tmf.init(ks);
	X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
	SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
	context.init(null, new TrustManager[] {tm}, null);
	SSLSocketFactory factory = context.getSocketFactory();
	
	System.out.println("Opening connection to " + host + ":" + port + "...");
	SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
	socket.setSoTimeout(10000);
	try {
	    System.out.println("Starting SSL handshake...");
	    socket.startHandshake();
	    socket.close();
	    System.out.println();
	    System.out.println("No errors, certificate is already trusted");
	} catch (SSLException e) {
	    System.out.println();
	    e.printStackTrace(System.out);
	}

	X509Certificate[] chain = tm.chain;
	if (chain == null) {
	    System.out.println("Could not obtain server certificate chain");
	    return;
	}

	System.out.println();
	System.out.println("Server sent " + chain.length + " certificate(s):");
	System.out.println();
	MessageDigest sha1 = MessageDigest.getInstance("SHA1");
	MessageDigest md5 = MessageDigest.getInstance("MD5");
	for (int i = 0; i < chain.length; i++) {
	    X509Certificate cert = chain[i];
	    System.out.println
	    	(" " + (i + 1) + " Subject " + cert.getSubjectDN());
	    System.out.println("   Issuer  " + cert.getIssuerDN());
	    sha1.update(cert.getEncoded());
	    System.out.println("   sha1    " + toHexString(sha1.digest()));
	    md5.update(cert.getEncoded());
	    System.out.println("   md5     " + toHexString(md5.digest()));
	    System.out.println();
	}

	System.out.println("Enter certificate to add to trusted keystore or 'q' to quit: [1]");
	String line = reader.readLine().trim();
	int k;
	try {
	    k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;
	} catch (NumberFormatException e) {
	    System.out.println("KeyStore not changed");
	    return;
	}

	X509Certificate cert = chain[k];
	String alias = host + "-" + (k + 1);
	ks.setCertificateEntry(alias, cert);

	OutputStream out = new FileOutputStream(file.toString());
	ks.store(out, passphrase);
	out.close();

	System.out.println();
	System.out.println(cert);
	System.out.println();
	System.out.println
		("Added certificate to keystore 'cacerts' using alias '"
		+ alias + "'");
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
    
	public void checkClientTrusted(X509Certificate[] chain, String authType)
		throws CertificateException {
	    throw new UnsupportedOperationException();
	}
    
	public void checkServerTrusted(X509Certificate[] chain, String authType)
		throws CertificateException {
	    this.chain = chain;
	    tm.checkServerTrusted(chain, authType);
	}
    }

}
