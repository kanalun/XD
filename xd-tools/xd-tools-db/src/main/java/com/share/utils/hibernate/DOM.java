package com.share.utils.hibernate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DOM
{
	private Element e;

	private DOM(Element element)
	{
		this.e = element;
	}

	public void print()
	{
		NodeList nodeList = e.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			System.out.println("节点名: " + node.getNodeName() + ", 节点值: " + node.getNodeValue() + ", 节点类型: " + node.getNodeType());
		}
	}

	public Element getDomElement()
	{
		return e;
	}

	public static DOM newDom(String rootName) throws XmlException
	{
		Document doc = null;
		try
		{
			DocumentBuilder dombuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = dombuilder.newDocument();
			// doc.setXmlStandalone(true);
		} catch (Exception e)
		{
			throw new XmlException(e.getMessage(), e);
		}
		Element root = doc.createElement(rootName);
		doc.appendChild(root);
		return new DOM(root);
	}

	public static DOM getRoot(InputStream is) throws XmlException
	{
		Document doc = null;
		try
		{
			DocumentBuilder dombuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = dombuilder.parse(is);
		} catch (Exception e)
		{
			throw new XmlException(e.getMessage(), e);
		}
		Element root = doc.getDocumentElement();
		return new DOM(root);
	}

	public static DOM getRoot(String xmlFile) throws XmlException
	{
		try
		{
			InputStream is = new FileInputStream(xmlFile);
			DOM root = getRoot(is);
			is.close();
			return root;
		} catch (Exception e)
		{
			throw new XmlException(e.getMessage(), e);
		}
	}

	public String getAttributeValue(String attributeName)
	{
		return e.getAttribute(attributeName);
	}

	public boolean existElement(String elementName)
	{
		NodeList nodeList = e.getElementsByTagName(elementName);
		if ((nodeList == null) || (nodeList.getLength() < 1))
		{
			return false;
		}
		return true;
	}

	public String elementText(String elementName)
	{
		Element element = (Element) e.getElementsByTagName(elementName).item(0);
		Node textNode = element.getFirstChild();
		if (textNode == null)
		{
			return "";
		}
		return textNode.getNodeValue();
	}

	public DOM element(String elementName)
	{
		NodeList nodeList = e.getElementsByTagName(elementName);
		if ((nodeList == null) || (nodeList.getLength() < 1))
		{
			return null;
		}
		Element element = (Element) nodeList.item(0);
		return new DOM(element);
	}

	public List<DOM> elements(String elementName)
	{
		List<DOM> eList = new ArrayList<DOM>();
		NodeList nodeList = e.getElementsByTagName(elementName);
		if (nodeList == null)
		{
			return eList;
		}
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				eList.add(new DOM(element));
			}
		}
		return eList;
	}

	public DOM addElement(String name)
	{
		Document document = e.getOwnerDocument();
		Element element = document.createElement(name);
		e.appendChild(element);
		return new DOM(element);
	}

	public DOM addElement(String name, String value)
	{
		Document document = e.getOwnerDocument();
		Element element = document.createElement(name);
		e.appendChild(element);
		Text text = document.createTextNode(value);
		element.appendChild(text);
		return new DOM(element);
	}

	// 添加或修改属性
	public DOM setAttribute(String name, String value)
	{
		e.setAttribute(name, value);
		return this;
	}

	public void remove(DOM subDom)
	{
		e.removeChild(subDom.getDomElement());
	}

	public void removeElement(String name)
	{
		NodeList nodeList = e.getElementsByTagName(name);
		if (nodeList == null)
		{
			return;
		}
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			e.removeChild(nodeList.item(i));
		}
	}

	public void removeAttribute(String name)
	{
		e.removeAttribute(name);
	}

	public DOM updateElementText(String name, String value)
	{
		Element element = (Element) e.getElementsByTagName(name).item(0);
		Node textNode = element.getFirstChild();
		textNode.setNodeValue(value);
		return new DOM(element);
	}

	public DOM updateElementText(String value)
	{
		Node textNode = e.getFirstChild();
		textNode.setNodeValue(value);
		return this;
	}

	public String getElementText()
	{
		Node textNode = e.getFirstChild();
		return textNode.getNodeValue();
	}

	public void write(OutputStream os)
	{
		write(os, "UTF-8");
	}

	public void write(OutputStream os, String encoding)
	{
		try
		{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			tFactory.setAttribute("indent-number", 2);
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.transform(new DOMSource(e.getOwnerDocument()), new StreamResult(new OutputStreamWriter(os)));
		} catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e)
		{
			e.printStackTrace();
		} catch (TransformerException e)
		{
			e.printStackTrace();
		}
	}

	public void write(String xmlFile) throws XmlException
	{
		write(xmlFile, "UTF-8");
	}

	public void write(String xmlFile, String encoding) throws XmlException
	{
		try
		{
			OutputStream os = new FileOutputStream(xmlFile);
			write(os, encoding);
			os.close();
		} catch (Exception e)
		{
			throw new XmlException(e.getMessage(), e);
		}
	}
}

class XmlException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public XmlException(String message)
	{
		super(message);
	}

	public XmlException(String message, Throwable cause)
	{
		super(message, cause);
	}
}