package engine.resources;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sun.net.util.URLUtil;

public class XMLParsers {

	/**
	 * Finds the tag you are looking for and returns all of the corresponding
	 * elements.
	 * 
	 * @param url
	 * @param tagName
	 */
	public static void getAllElements(URL url, String tagName) {
		Document doc = createDocument(url);

		// Get the list of nodes by the tagName.
		NodeList list = doc.getElementsByTagName(tagName);

		// If the list has nodes, print the nodes.
		if (list.getLength() > 0)
			printNodes(list);
	}

	public static void testStatus() throws MalformedURLException {
		String user = "helson";
		String password = "123456";
		String tag = "status";

		String workout = findSpecificElement(ImageBank.getUserDir()
				+ "\\res.xml", "state");
		System.out.println(workout);
	}

	public static String findSpecificElement(String location, String... tags) {
		Document doc = createDocument(location);
		return getSpecificTag(doc, tags);
	}

	public static String findSpecificElement(URL url, String... tags) {
		Document doc = createDocument(url);
		return getSpecificTag(doc, tags);
	}

	public static String getSpecificTag(Document doc, String... tag) {
		NodeList nList = doc.getElementsByTagName(tag[0]);

		Node nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			if (eElement.getAttribute(tag[0]) != null) {
				if (tag.length > 1)
					return getSpecificTag(doc,
							Arrays.copyOfRange(tag, 1, tag.length - 1));
				else
					return eElement.getTextContent();
			}
		}
		System.out.println("Failed");
		return null;
	}

	public static void writeSpecificTag(String location, String info) {

	}

	/**
	 * Prints an entire XML file.
	 * 
	 * @param url
	 *            The URL where the XML file is located.
	 */
	public static void printXML(URL url) {
		Document doc = createDocument(url);

		if (doc.hasChildNodes())
			printNodes(doc.getChildNodes());
	}

	/**
	 * Returns a normalized document by parsing the the XML file at the
	 * specified URL.
	 * 
	 * @param url
	 *            The URL of the XML file.
	 * @return A XML document.
	 */
	private static Document createDocument(String loc) {
		File fXmlFile = new File(loc);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a normalized document by parsing the the XML file at the
	 * specified URL.
	 * 
	 * @param url
	 *            The URL of the XML file.
	 * @return A XML document.
	 */
	private static Document createDocument(URL url) {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		Document doc;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(url.toString());
			doc.normalize();
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void printNodes(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName()
						+ " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());

				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : "
								+ node.getNodeValue());
					}
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNodes(tempNode.getChildNodes());
				}

				System.out.println("Node Name =" + tempNode.getNodeName()
						+ " [CLOSE]");
			}
		}
	}
}
