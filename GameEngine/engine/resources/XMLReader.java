package engine.resources;

import java.net.MalformedURLException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

	static Document doc;

	public static void main(String argv[]) {
		try {
			XMLParsers.testStatus();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*try {

			File fXmlFile = new File(ImageBank.getUserDir() + "\\res.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			if (doc.hasChildNodes())
				printNodeType(doc.getChildNodes(), 0, "image");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public static void printNodeType(NodeList nodeList, int id, String type) {
		System.out.println("NodeList length:" + nodeList.getLength());
		Node tempNode = nodeList.item(id);
		// make sure it's element node.
		if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
			System.out.println("Element Node");
			if (tempNode.hasAttributes()) {
				System.out.println("Has attribute");
				// get attributes names and values
				NamedNodeMap nodeMap = tempNode.getAttributes();

				for (int i = 0; i < nodeMap.getLength(); i++) {
					Node node = nodeMap.item(i);
					if (node.getNodeName() == type)
						System.out.println("attr value : "
								+ node.getNodeValue());
				}
			}
			//if (tempNode.hasChildNodes())
			//	printNode(tempNode.getChildNodes());

		}

	}

	public static void printNode(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.hasAttributes()) {

					System.out.print(tempNode.getNodeName() + " ");

					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();

					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println(node.getNodeName() + " = "
								+ node.getNodeValue());
					}
				}
				if (tempNode.hasChildNodes())
					// loop again if has child nodes
					printNode(tempNode.getChildNodes());

				System.out.println("Node Name =" + tempNode.getNodeName()
						+ " [CLOSE]");
			}
		}
	}

	public static String[] getStateImageNames(int id) {
		Node node = doc.getElementsByTagName("state").item(id);

		if (node.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) node;

			// System.out.println(node.item(id).getNodeName());
		}
		// Node nNode = nList.item(id);
		//
		// System.out.println("\nCurrent Element :" + nNode.getNodeName());
		//
		// Element e =
		// if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		//
		// Element eElement = (Element) nNode;
		//
		// System.out.println("Image Name : "
		// + eElement.getElementsByTagName("image").item(0)
		// .getTextContent());
		// }
		return null;
	}

}