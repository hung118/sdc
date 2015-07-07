package sax;

import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.NamespaceSupport;

public class XMLWriter extends XMLFilterBase {

    private Map prefixTable;
    private Map forcedDeclTable;
    private Map doneDeclTable;
    private boolean openElement;
    private int elementLevel;
    private Writer output;
    private NamespaceSupport nsSupport;
    private int prefixCounter;
    private boolean inDTD;

    public XMLWriter() {
        openElement = false;
        elementLevel = 0;
        prefixCounter = 0;
        inDTD = false;
        init(null);
    }

    public XMLWriter(Writer writer) {
        openElement = false;
        elementLevel = 0;
        prefixCounter = 0;
        inDTD = false;
        init(writer);
    }

    public XMLWriter(XMLReader xmlreader) {
        super(xmlreader);
        openElement = false;
        elementLevel = 0;
        prefixCounter = 0;
        inDTD = false;
        init(null);
    }

    public XMLWriter(XMLReader xmlreader, Writer writer) {
        super(xmlreader);
        openElement = false;
        elementLevel = 0;
        prefixCounter = 0;
        inDTD = false;
        init(writer);
    }

    public void characters(char[] ch, int start, int len) throws SAXException {
        closeElement();
        writeEsc(ch, start, len, false);
        super.characters(ch, start, len);
    }

    private void closeElement() throws SAXException {
        if (openElement) {
            write('>');
            openElement = false;
        }
    }

    public void comment(char[] ch, int start, int length) throws SAXException {
        if (!inDTD) {
            closeElement();
            write("<!--");
            write(ch, start, length);
            write("-->");
            if (elementLevel < 1) {
                write('\n');
            }
        }
        super.comment(ch, start, length);
    }

    private String doPrefix(String uri, String qName, boolean isElement) {
        String defaultNS = nsSupport.getURI("");
        if ("".equals(uri)) {
            if (isElement && defaultNS != null) {
                nsSupport.declarePrefix("", "");
            }
            return null;
        }
        String prefix;
        if (isElement && defaultNS != null && uri.equals(defaultNS)) {
            prefix = "";
        } else {
            prefix = nsSupport.getPrefix(uri);
        }
        if (prefix != null) {
            return prefix;
        }
        prefix = (String) doneDeclTable.get(uri);
        if (prefix != null && ((!isElement || defaultNS != null) && "".equals(prefix) || nsSupport.getURI(prefix) != null)) {
            prefix = null;
        }
        if (prefix == null) {
            prefix = (String) prefixTable.get(uri);
            if (prefix != null && ((!isElement || defaultNS != null) && "".equals(prefix) || nsSupport.getURI(prefix) != null)) {
                prefix = null;
            }
        }
        if (prefix == null && qName != null && !"".equals(qName)) {
            int i = qName.indexOf(':');
            if (i == -1) {
                if (isElement && defaultNS == null) {
                    prefix = "";
                }
            } else {
                prefix = qName.substring(0, i);
            }
        }
        for (; prefix == null || nsSupport.getURI(prefix) != null; prefix = "__NS" + ++prefixCounter) {
        }
        nsSupport.declarePrefix(prefix, uri);
        doneDeclTable.put(uri, prefix);
        return prefix;
    }

    public void endCDATA() throws SAXException {
        write("]]>");
        super.endCDATA();
    }

    public void endDTD() throws SAXException {
        inDTD = false;
        super.endDTD();
    }

    public void endDocument() throws SAXException {
        closeElement();
        write('\n');
        super.endDocument();
        try {
            flush();
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (openElement) {
            write("/>");
            openElement = false;
        } else {
            write("</");
            writeName(uri, localName, qName, true);
            write('>');
        }
        if (elementLevel == 1) {
            write('\n');
        }
        super.endElement(uri, localName, qName);
        nsSupport.popContext();
        elementLevel--;
    }

    public void endEntity(String name) throws SAXException {
        super.endEntity(name);
    }

    public void flush() throws IOException {
        output.flush();
    }

    public void forceNSDecl(String uri) {
        forcedDeclTable.put(uri, Boolean.TRUE);
    }

    public void forceNSDecl(String uri, String prefix) {
        setPrefix(uri, prefix);
        forceNSDecl(uri);
    }

    private void forceNSDecls() {
        String prefix;
        for (Iterator prefixes = forcedDeclTable.keySet().iterator(); prefixes.hasNext(); doPrefix(prefix, null, true)) {
            prefix = (String) prefixes.next();
        }
    }

    public String getPrefix(String uri) {
        return (String) prefixTable.get(uri);
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        closeElement();
        writeEsc(ch, start, length, false);
        super.ignorableWhitespace(ch, start, length);
    }

    private void init(Writer writer) {
        setOutput(writer);
        nsSupport = new NamespaceSupport();
        prefixTable = new HashMap();
        forcedDeclTable = new HashMap();
        doneDeclTable = new HashMap();
    }

    public void processingInstruction(String target, String data) throws SAXException {
        closeElement();
        write("<?");
        write(target);
        write(' ');
        write(data);
        write("?>");
        if (elementLevel < 1) {
            write('\n');
        }
        super.processingInstruction(target, data);
    }

    public void reset() {
        openElement = false;
        elementLevel = 0;
        prefixCounter = 0;
        nsSupport.reset();
        inDTD = false;
    }

    public void setOutput(Writer writer) {
        if (writer == null) {
            output = new OutputStreamWriter(System.out);
        } else {
            output = writer;
        }
    }

    public void setPrefix(String uri, String prefix) {
        prefixTable.put(uri, prefix);
    }

    public void startCDATA() throws SAXException {
        closeElement();
        write("<![CDATA[");
        super.startCDATA();
    }

    public void startDTD(String name, String publicId, String systemId) throws SAXException {
        inDTD = true;
        write("<!DOCTYPE ");
        write(name);
        boolean hasPublic = publicId != null && !publicId.equals("");
        if (hasPublic) {
            write(" PUBLIC \"");
            write(publicId);
            write('"');
        }
        if (systemId != null && !systemId.equals("")) {
            if (!hasPublic) {
                write(" SYSTEM");
            }
            write(" \"");
            write(systemId);
            write('"');
        }
        write(">\n\n");
        super.startDTD(name, publicId, systemId);
    }

    public void startDocument() throws SAXException {
        reset();
        write("<?xml version=\"1.0\"?>\n\n");
        super.startDocument();
    }

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        closeElement();
        elementLevel++;
        nsSupport.pushContext();
        write('<');
        writeName(uri, localName, qName, true);
        writeAttributes(atts);
        if (elementLevel == 1) {
            forceNSDecls();
        }
        writeNSDecls();
        openElement = true;
        super.startElement(uri, localName, qName, atts);
    }

    public void startEntity(String name) throws SAXException {
        closeElement();
        write('&');
        write(name);
        write(';');
        super.startEntity(name);
    }

    private void write(char c) throws SAXException {
        try {
            output.write(c);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    private void write(String s) throws SAXException {
        try {
            output.write(s);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    private void write(char[] cbuf, int off, int len) throws SAXException {
        try {
            output.write(cbuf, off, len);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    private void writeAttributes(Attributes atts) throws SAXException {
        int len = atts.getLength();
        for (int i = 0; i < len; i++) {
            char[] ch = atts.getValue(i).toCharArray();
            write(' ');
            writeName(atts.getURI(i), atts.getLocalName(i), atts.getQName(i), false);
            write("=\"");
            writeEsc(ch, 0, ch.length, true);
            write('"');
        }
    }

    private void writeEsc(char[] ch, int start, int length, boolean isAttVal) throws SAXException {
        for (int i = start; i < start + length; i++) {
            switch (ch[i]) {
                case 38:
                    // '&'
write("&amp;");     break;
                case 60:
                    // '<'
write("&lt;");      break;
                case 62:
                    // '>'
write("&gt;");      break;
                case 34:
                    // '"'
if (isAttVal) {
                        write("&quot;");
                    } else {
                        write('"');
                    }
                    break;
                default:
                    if (ch[i] > '\177') {
                        write("&#");
                        write(Integer.toString(ch[i]));
                        write(';');
                    } else {
                        write(ch[i]);
                    }
                    break;
            }
        }
    }

    private void writeNSDecls() throws SAXException {
        for (Enumeration prefixes = nsSupport.getDeclaredPrefixes(); prefixes.hasMoreElements(); write('"')) {
            String prefix = (String) prefixes.nextElement();
            String uri = nsSupport.getURI(prefix);
            if (uri == null) {
                uri = "";
            }
            char[] ch = uri.toCharArray();
            write(' ');
            if ("".equals(prefix)) {
                write("xmlns=\"");
            } else {
                write("xmlns:");
                write(prefix);
                write("=\"");
            }
            writeEsc(ch, 0, ch.length, true);
        }
    }

    private void writeName(String uri, String localName, String qName, boolean isElement) throws SAXException {
        String prefix = doPrefix(uri, qName, isElement);
        if (prefix != null && !"".equals(prefix)) {
            write(prefix);
            write(':');
        }
        write(localName);
    }
}
