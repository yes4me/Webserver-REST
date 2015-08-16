/* ===========================================================================
Created:	2015/08/05
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Inspire from:	http://www.rgagnon.com/javadetails/java-0573.html
=========================================================================== */

package lib;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class MyXML {
	private String xml = "";

	public MyXML() {
	}
	public MyXML(String xml) {
		this.xml = xml;
	}

	private Document loadXMLFromString(String... customXml) throws Exception
	{
		if (customXml.length > 0)
			xml = customXml[0];
		if (xml.equals(""))
			return null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		InputSource is = new InputSource(new StringReader(xml));

		//This also works - ByteArrayInputStream class allows a buffer in the memory to be used as an InputStream. The input source is a byte array
		//ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes())   
		return builder.parse(is);
	}
	public String getTextContent(String tagName, String... customXml) {
		if (customXml.length > 0)
			xml = customXml[0];
		if (xml.equals(""))
			return "";

		String value = "";
		try
		{
			Document doc	= loadXMLFromString(xml);
			value			= doc.getElementsByTagName(tagName).item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	public boolean checkType(String tagName, Class theclass) {
		String thevalue = getTextContent(tagName);
		try
		{
			if (theclass == Integer.class)
				Integer.valueOf(thevalue);
			else if (theclass == Double.class)
				Double.valueOf(thevalue);
			else if (theclass == Boolean.class)
				Boolean.valueOf(thevalue);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	public static void main(String[] args)
	{
		String xml = "<StockQuotes><Stock><Symbol>NFLX</Symbol><Last>123.52</Last><Date>8/7/2015</Date><Time>4:00pm</Time><Change>-2.93</Change><Open>126.42</Open><High>126.60</High><Low>121.30</Low><Volume>17360669</Volume><MktCap>52.62B</MktCap><PreviousClose>126.45</PreviousClose><PercentageChange>-2.32%</PercentageChange><AnnRange>45.08 - 129.29</AnnRange><Earns>0.45</Earns><P-E>276.95</P-E><Name>Netflix</Name></Stock></StockQuotes>";
		MyXML myXML = new MyXML(xml);
		System.out.println( myXML.getTextContent("Symbol") );
	}
}