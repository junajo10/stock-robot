package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SettingParser {
	public static String getAlgorithmPath() {
		File f = new File("configuration.xml");

		if (!f.exists()) {
			// TODO: make nicer, this is only temporary!!!
			JFileChooser chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Set plugin path");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					FileWriter writer = new FileWriter(f);
					writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
"<settings>\n"+
"	<algorithmPath>" + chooser.getSelectedFile() + "/</algorithmPath>\n"+
"</settings>");
					
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("getSelectedFile() : " 
						+  chooser.getSelectedFile());
			}
		}
/*
 * <?xml version="1.0" encoding="UTF-8" ?>
<settings>
	<algorithmPath>/home/daniel/plugin/</algorithmPath>
</settings>

 */

		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true); 
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse("configuration.xml");

			XPath xpath = XPathFactory.newInstance().newXPath();

			XPathExpression expr = xpath.compile("//algorithmPath/text()");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;

			List<String> results = new LinkedList<String>();
			for (int i = 0; i < nodes.getLength(); i++) {
				results.add(nodes.item(i).getNodeValue()); 
			}
			if (nodes.getLength() != 1)
				return null;
			else
				return nodes.item(0).getNodeValue();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
