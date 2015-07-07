package gov.utah.dts.sdc.webservice;
/**
 * Class for encoding and decoding data via Base64
 *
 * @author Paul Coleman
 * @version 1.0
 */
public class Base64 {
    
    public Base64() {
    }
    
    // Code standard prefixes m=Member Variable, p=Function Paramater.  Local Variables are freeform.
    public static char[] encode(byte pData[]) {
        char out[] = new char[((pData.length + 2) / 3) * 4];
        int i = 0;
        for (int index = 0; i < pData.length; index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = 0xff & pData[i];
            val <<= 8;
            if (i + 1 < pData.length) {
                val |= 0xff & pData[i + 1];
                trip = true;
            }
            val <<= 8;
            if (i + 2 < pData.length) {
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
    
    public static byte[] decode(char pData[]) throws Exception {
        int tempLen = pData.length;
        int tempLastOk = -1;
        for (int ix = 0; ix < pData.length; ix++) {
            int value = mCodes[pData[ix] & 0xff];
            if (value < 0 && pData[ix] != '=')
                tempLen--;
            else
                tempLastOk = ix;
        }
        
        tempLastOk++;
        int len = ((tempLen + 3) / 4) * 3;
        if (tempLastOk > 0 && pData[tempLastOk - 1] == '=')
            len--;
        if (tempLastOk > 1 && pData[tempLastOk - 2] == '=')
            len--;
        byte out[] = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;
        for (int ix = 0; ix < pData.length; ix++) {
            int value = mCodes[pData[ix] & 0xff];
            if (value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte)(accum >> shift & 0xff);
                }
            }
        }
        
        return out;
    }
    
    private static char mAlphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static byte mCodes[];
    
    static  {
        mCodes = new byte[256];
        for (int i = 0; i < 256; i++)
            mCodes[i] = -1;
        
        for (int i = 65; i <= 90; i++)
            mCodes[i] = (byte)(i - 65);
        
        for (int i = 97; i <= 122; i++)
            mCodes[i] = (byte)((26 + i) - 97);
        
        for (int i = 48; i <= 57; i++)
            mCodes[i] = (byte)((52 + i) - 48);
        
        mCodes[43] = 62;
        mCodes[47] = 63;
    }
}
