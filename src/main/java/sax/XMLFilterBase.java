package sax;

import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;

public class XMLFilterBase extends XMLFilterImpl
    implements LexicalHandler
{

    private LexicalHandler lexicalHandler;
    protected static final Attributes EMPTY_ATTS = new AttributesImpl();
    protected static final String LEXICAL_HANDLER_NAMES[] = {
        "http://xml.org/sax/properties/lexical-handler", "http://xml.org/sax/handlers/LexicalHandler"
    };

    public XMLFilterBase()
    {
        lexicalHandler = null;
    }

    public XMLFilterBase(XMLReader parent)
    {
        super(parent);
        lexicalHandler = null;
    }

    public void characters(String data)
        throws SAXException
    {
        char ch[] = data.toCharArray();
        characters(ch, 0, ch.length);
    }

    public void comment(char ch[], int start, int length)
        throws SAXException
    {
        if(lexicalHandler != null)
        {
            lexicalHandler.comment(ch, start, length);
        }
    }

    public void dataElement(String localName, String content)
        throws SAXException
    {
        dataElement("", localName, "", EMPTY_ATTS, content);
    }

    public void dataElement(String uri, String localName, String content)
        throws SAXException
    {
        dataElement(uri, localName, "", EMPTY_ATTS, content);
    }

    public void dataElement(String uri, String localName, String qName, Attributes atts, String content)
        throws SAXException
    {
        startElement(uri, localName, qName, atts);
        characters(content);
        endElement(uri, localName, qName);
    }

    public void dataElement(String localName, Attributes atts, String content)
        throws SAXException
    {
        dataElement("", localName, "", atts, content);
    }

    public void emptyElement(String localName)
        throws SAXException
    {
        emptyElement("", localName, "", EMPTY_ATTS);
    }

    public void emptyElement(String uri, String localName)
        throws SAXException
    {
        emptyElement(uri, localName, "", EMPTY_ATTS);
    }

    public void emptyElement(String uri, String localName, String qName, Attributes atts)
        throws SAXException
    {
        startElement(uri, localName, qName, atts);
        endElement(uri, localName, qName);
    }

    public void emptyElement(String localName, Attributes atts)
        throws SAXException
    {
        emptyElement("", localName, "", atts);
    }

    public void endCDATA()
        throws SAXException
    {
        if(lexicalHandler != null)
        {
            lexicalHandler.endCDATA();
        }
    }

    public void endDTD()
        throws SAXException
    {
        if(lexicalHandler != null)
        {
            lexicalHandler.endDTD();
        }
    }

    public void endElement(String localName)
        throws SAXException
    {
        endElement("", localName, "");
    }

    public void endElement(String uri, String localName)
        throws SAXException
    {
        endElement(uri, localName, "");
    }

    public void endEntity(String name)
        throws SAXException
    {
        if(lexicalHandler != null)
        {
            lexicalHandler.endEntity(name);
        }
    }

    public LexicalHandler getLexicalHandler()
    {
        return lexicalHandler;
    }

    public Object getProperty(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        for(int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++)
        {
            if(LEXICAL_HANDLER_NAMES[i].equals(name))
            {
                return getLexicalHandler();
            }
        }

        return super.getProperty(name);
    }

    private void installLexicalHandler()
    {
        XMLReader parent = getParent();
        if(parent == null)
        {
            throw new NullPointerException("No parent for filter");
        }
        for(int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++)
        {
            try
            {
                parent.setProperty(LEXICAL_HANDLER_NAMES[i], this);
                break;
            }
            catch(SAXNotRecognizedException _ex) { }
            catch(SAXNotSupportedException _ex) { }
        }

    }

    public void parse(InputSource input)
        throws SAXException, IOException
    {
        installLexicalHandler();
        super.parse(input);
    }

    public void setLexicalHandler(LexicalHandler handler)
    {
        if(handler == null)
        {
            throw new NullPointerException("Null lexical handler");
        } else
        {
            lexicalHandler = handler;
            return;
        }
    }

    public void setProperty(String name, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        for(int i = 0; i < LEXICAL_HANDLER_NAMES.length; i++)
        {
            if(LEXICAL_HANDLER_NAMES[i].equals(name))
            {
                setLexicalHandler((LexicalHandler)value);
                return;
            }
        }

        super.setProperty(name, value);
    }

    public void startCDATA()
        throws SAXException
    {
        if(lexicalHandler != null)
        {
            lexicalHandler.startCDATA();
        }
    }

    public void startDTD(String name, String publicId, String systemId)
        throws SAXException
    {
        if(lexicalHandler != null)
        {
            lexicalHandler.startDTD(name, publicId, systemId);
        }
    }

    public void startElement(String localName)
        throws SAXException
    {
        startElement("", localName, "", EMPTY_ATTS);
    }

    public void startElement(String uri, String localName)
        throws SAXException
    {
        startElement(uri, localName, "", EMPTY_ATTS);
    }

    public void startElement(String localName, Attributes atts)
        throws SAXException
    {
        startElement("", localName, "", atts);
    }

    public void startEntity(String name)
        throws SAXException
    {
        if(lexicalHandler != null)
        {
            lexicalHandler.startEntity(name);
        }
    }

}
