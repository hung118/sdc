/*
 * XMLUtils.java
 * @author  jstevenson
 */

package gov.utah.dts.sdc.webservice;

import java.util.*;
import org.jdom.*;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import sax.*;

public class XMLUtils {
    
    /** Creates a new instance of XMLUtils */
    public XMLUtils() {
    }
    
    public static java.util.Map getContentsAsMap(String data) throws Exception {
        XMLWriter echo = new XMLWriter();
        SAXBuilder builder = new SAXBuilder();
        builder.setXMLFilter(echo);
        java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream(data.getBytes());
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        java.util.List children = root.getChildren();
        java.util.Map map = new java.util.HashMap();
        map = getSingleChildren(root, 0, map);
        return map;
    }
    
    public static java.util.Map getSingleChildren(Element current, int depth, java.util.Map map) {
        
        printSpaces(depth);
        //log.debug(current.getName());
        if(current != null && current.getText() != null){
            map.put(current.getName(), current.getText());
        }
        //log.debug("CONTENT "+current.getText());
        java.util.List children = current.getChildren();
        java.util.Iterator iterator = children.iterator();
        while (iterator.hasNext()) {
            Element child = (Element) iterator.next();
            getSingleChildren(child, depth+1, map);
        }
        return map;
    }
    private static void printSpaces(int n) {
        
        for (int i = 0; i < n; i++) {
            System.out.print(' ');
        }
        
    }
    
    public static void listNodes(Object o, int depth) {
        
        printSpaces(depth);
        
        if (o instanceof Element) {
            Element element = (Element) o;
            //log.debug("Element: " + element.getName());
            List children = element.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                listNodes(child, depth+1);
            }
        } else if (o instanceof Document) {
            //log.debug("Document");
            Document doc = (Document) o;
            List children = doc.getContent();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Object child = iterator.next();
                listNodes(child, depth+1);
            }
        } else if (o instanceof Comment) {
            //log.debug("Comment");
        } else if (o instanceof CDATA) {
            //log.debug("CDATA section");
            // CDATA is a subclass of Text so this test must come
            // before the test for Text.
        } else if (o instanceof Text) {
            //log.debug("Text");
        } else if (o instanceof EntityRef) {
            //log.debug("Entity reference");
        } else if (o instanceof ProcessingInstruction) {
            //log.debug("Processing Instruction");
        } else {  // This really shouldn't happen
            //log.debug("Unexpected type: " + o.getClass());
        }
        
    }

}
