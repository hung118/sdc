package gov.utah.dts.sdc.webservice;

import gov.utah.dts.sdc.webservice.Base64;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class UCJISCipher {
    
    private UCJISCipher() {
    }
    
    public static void main(String args[])
            throws Exception {
        String xml = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?><udps:DocumentDescriptor type=\"XID\" class=\"SI\" authenticator=\"DPS\" routingCode=\"L\"/>");
        try {
            System.out.println("XML String: " + xml);
            String data = "I ran a red light, Oh crap I shouldn't have decrypted that.";
            String resultStr = encrypt(xml, data, "udps:DocumentDescriptor");
            System.out.println("Encrypted and Encoded: " + resultStr);
            Base64 b64 = new Base64();
            System.out.println("decoded cipher text: " + new String(b64.decode(resultStr.toCharArray())));
            resultStr = decryptString(xml, resultStr, "udps:DocumentDescriptor");
            System.out.println("Decrypted: " + resultStr);
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static String encrypt(String pXML, String pPlainText, String pSourceElement)
            throws Throwable {
        String KeyMaterial = getNodeXMLKey(pSourceElement, pXML);
        if(KeyMaterial != null && !KeyMaterial.equals("") && pPlainText.toString().length() > 0) {
            byte plaintext[] = null;
            plaintext = pPlainText.toString().trim().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte raw[] = md.digest(KeyMaterial.getBytes());
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(1, key);
            int modulus = plaintext.length % 16;
            byte paddeddata[];
            if(modulus != 0) {
                paddeddata = new byte[plaintext.length + (16 - modulus)];
                Arrays.fill(paddeddata, (byte)0);
                System.arraycopy(plaintext, 0, paddeddata, 0, plaintext.length);
                if(plaintext.length < paddeddata.length && paddeddata[paddeddata.length - 1] == 0)
                    paddeddata[paddeddata.length - 1] = 8;
            } else {
                paddeddata = plaintext;
            }
            byte encrypted[] = cipher.doFinal(paddeddata);
            return new String(mimeEncode(encrypted));
        } else {
            throw new Exception("Usage: encrypt() takes three valid arguments: XML, the data to be encrypted, and the source element");
        }
    }
    
    public static String decryptString(String pXML, String pEncodedCipherText, String pSourceElement)
            throws Throwable {
        String KeyMaterial = getNodeXMLKey(pSourceElement, pXML);
        if(KeyMaterial != null && !KeyMaterial.equals("") && pEncodedCipherText.toString() != null && !pEncodedCipherText.toString().equals("")) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte raw[] = md.digest(KeyMaterial.getBytes());
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(2, key);
            byte original[] = cipher.doFinal(mimeDecode(pEncodedCipherText.toCharArray()));
            return new String((new String(original)).trim());
        } else {
            throw new Exception("Usage: decryptString() takes three valid arguments: XML, the base64-encoded data to be decrypted, and the source element");
        }
    }
    
    private static String getNodeXMLKey(String pElementName, String pXML)
            throws Throwable {
        return getXMLFromTagName(pXML.toCharArray(), pElementName) + "<!--Key 6C64AD23-1628-44EF-9E61-9C930A3DF44C-->";
    }
    
    private static String getXMLFromTagName(char pMessage[], String pTagName)
            throws IOException {
        StringBuffer ary = new StringBuffer("");
        StringBuffer result = new StringBuffer("");
        boolean extracting = false;
        int msgindex = (new String(pMessage)).indexOf("</" + pTagName + ">");
        if(msgindex != -1) {
            int x = 0;
            do
            {
                if(x >= pMessage.length)
                    break;
                ary.append(pMessage[x]);
                if(ary.toString().endsWith("<" + pTagName) && (pMessage[x + 1] == ' ' || pMessage[x + 1] == '>')) {
                    if(!extracting) {
                        extracting = true;
                        result.append("<" + pTagName);
                    }
                } else
                    if(ary.toString().endsWith("</" + pTagName)) {
                        if(extracting) {
                            extracting = false;
                            result.append(pMessage[x] + ">");
                            break;
                        }
                    } else
                        if(extracting)
                            result.append(pMessage[x]);
                x++;
            } while(true);
        } else {
            for(int x = 0; x < pMessage.length; x++) {
                ary.append(pMessage[x]);
                if(ary.toString().endsWith("<" + pTagName)) {
                    if(!extracting) {
                        extracting = true;
                        result.append("<" + pTagName);
                    }
                    continue;
                }
                if(result.toString().endsWith("/>")) {
                    if(!extracting)
                        continue;
                    extracting = false;
                    break;
                }
                if(extracting)
                    result.append(pMessage[x]);
            }
            
        }
        new WeakReference(ary);
        return result == null ? "" : result.toString();
    }
    
    private static char[] mimeEncode(byte pData[]) {
        char out[] = new char[((pData.length + 2) / 3) * 4];
        int i = 0;
        for(int index = 0; i < pData.length; index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = 0xff & pData[i];
            val <<= 8;
            if(i + 1 < pData.length) {
                val |= 0xff & pData[i + 1];
                trip = true;
            }
            val <<= 8;
            if(i + 2 < pData.length) {
                val |= 0xff & pData[i + 2];
                quad = true;
            }
            out[index + 3] = mAlphabet[quad ? val & 0x3f : 64];
            val >>= 6;
            out[index + 2] = mAlphabet[trip ? val & 0x3f : 64];
            val >>= 6;
            out[index + 1] = mAlphabet[val & 0x3f];
            val >>= 6;
            out[index] = mAlphabet[val & 0x3f];
            i += 3;
        }
        
        return out;
    }
    
    private static byte[] mimeDecode(char pData[])
            throws Exception {
        int tempLen = pData.length;
        int tempLastOk = -1;
        for(int ix = 0; ix < pData.length; ix++) {
            int value = mCodes[pData[ix] & 0xff];
            if(value < 0 && pData[ix] != '=')
                tempLen--;
            else
                tempLastOk = ix;
        }
        
        tempLastOk++;
        int len = ((tempLen + 3) / 4) * 3;
        if(tempLastOk > 0 && pData[tempLastOk - 1] == '=')
            len--;
        if(tempLastOk > 1 && pData[tempLastOk - 2] == '=')
            len--;
        byte out[] = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;
        for(int ix = 0; ix < pData.length; ix++) {
            int value = mCodes[pData[ix] & 0xff];
            if(value < 0)
                continue;
            accum <<= 6;
            shift += 6;
            accum |= value;
            if(shift >= 8) {
                shift -= 8;
                out[index++] = (byte)(accum >> shift & 0xff);
            }
        }
        
        return out;
    }
    
    private static char mAlphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte mCodes[];
    
    static
    {
        mCodes = new byte[256];
        for(int i = 0; i < 256; i++)
            mCodes[i] = -1;
        
        for(int i = 65; i <= 90; i++)
            mCodes[i] = (byte)(i - 65);
        
        for(int i = 97; i <= 122; i++)
            mCodes[i] = (byte)((26 + i) - 97);
        
        for(int i = 48; i <= 57; i++)
            mCodes[i] = (byte)((52 + i) - 48);
        
        mCodes[43] = 62;
        mCodes[47] = 63;
    }
}