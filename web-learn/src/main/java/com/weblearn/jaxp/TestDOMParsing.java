package com.weblearn.jaxp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
// JAXP
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

// DOM
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestDOMParsing {

    public static void main(String[] args) {
        try {
            String xml = "<xml><ToUserName><![CDATA[gh_6f5696b8e59c]]></ToUserName><FromUserName><![CDATA[oGJiy1V-SqouozZgaR8DefwU2zF0]]></FromUserName><CreateTime>1507788921</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[VIEW]]></Event><EventKey><![CDATA[https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx18bf22642e20c429&redirect_uri=http://www.tongyaosh.com/wechat/entrance?redirectUrl=http://www.tongyaosh.com/html/index.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect]]></EventKey><MenuId>417858339</MenuId></xml>";

            
            // Get Document Builder Factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Leave off validation, and turn off namespaces
            factory.setValidating(false);
            factory.setNamespaceAware(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));   
            Document doc = builder.parse(is);

            // Print the document from the DOM tree and
            //   feed it an initial indentation of nothing
            //printNode(doc, "");
            NodeList nl = doc.getElementsByTagName("Event");
            System.out.println(nl.item(0).getTextContent());
        } catch (ParserConfigurationException e) {
            System.out.println("The underlying parser does not support the requested features.");
        } catch (FactoryConfigurationError e) {
            System.out.println("Error occurred obtaining Document Builder Factory.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent)  {
        switch (node.getNodeType()) {
            case Node.DOCUMENT_NODE:
                System.out.println("<xml version=\"1.0\">\n");
                // recurse on each child
                NodeList nodes = node.getChildNodes();
                if (nodes != null) {
                    for (int i=0; i<nodes.getLength(); i++) {
                        printNode(nodes.item(i), "");
                    }
                }
                break;
                
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();
                System.out.print(indent + "<" + name);
                NamedNodeMap attributes = node.getAttributes();
                for (int i=0; i<attributes.getLength(); i++) {
                    Node current = attributes.item(i);
                    System.out.print(" " + current.getNodeName() +
                                     "=\"" + current.getNodeValue() +
                                     "\"");
                }
                System.out.print(">");
                
                // recurse on each child
                NodeList children = node.getChildNodes();
                if (children != null) {
                    for (int i=0; i<children.getLength(); i++) {
                        printNode(children.item(i), indent + "  ");
                    }
                }
                
                System.out.print("</" + name + ">");
                break;

            case Node.TEXT_NODE:
                System.out.print(node.getNodeValue());
                break;
        }
    }

}