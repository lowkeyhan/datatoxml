package com.techstar.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.techstar.sys.config.Global;
public class xmlhelper {
	 /*
	  * Create a XML Document
	  */
	 public static Document createDocument()
	 {
	  Document document = DocumentHelper.createDocument();
	  
	  Element root = document.addElement("root");
	  
	  Element author1 = root.addElement("Lynch");
	  author1.addAttribute("Age","25");
	  author1.addAttribute("Country","China");
	  author1.addText("I am great!");
	  
	  Element author2 = root.addElement("Legend");
	  author2.addAttribute("Age","25");
	  author2.addAttribute("Country","China");
	  author2.addText("I am great!too!");
	  
	  return document;
	 }
	 
	 /*
	  * Create a XML document through String
	  */
	 public static Document StringToXML(String str) throws DocumentException
	 {
	  Document document = DocumentHelper.parseText(str);
	  return document;
	 }
	 public static Element FindElement(Document document)
	 {
	  Element root = document.getRootElement();
	  Element legend = null;
	  for(Iterator i=root.elementIterator("legend");i.hasNext();)
	  {
	   legend = (Element)i.next();
	  }
	  return legend;
	 }
	 
	 /*
	  * Write a XML file
	  */
	 public static void FileWrite(Document document) throws IOException
	 {
	  FileWriter out = new FileWriter(Global.getConfig("xmlpath"));
	  document.write(out);
	  out.close();
	 }
	 /*
	  * Write a XML file to doc
	  */
	 public static Document  WriteFiletodoc() throws IOException, DocumentException
	 {
		 File xmlfile=new File(Global.getConfig("timedatapath"));
	  SAXReader reader=new SAXReader();
	  Document document=reader.read(xmlfile);
	  return document;
	 }
	 /*
	  * Write a XML format file
	  */
	 public static void XMLWrite(Document document) throws IOException
	 {
	  XMLWriter writer = new XMLWriter(new FileWriter(Global.getConfig("xmlpath")));
	  writer.write(document);
	  writer.close();
	 }
	 /*
	  * Write a XML format file on path
	  */
	 public static void XMLWrite(Document document,String path) throws IOException
	 {
	  XMLWriter writer = new XMLWriter(new FileWriter(path));
	  writer.write(document);
	  writer.close();
	 }

}
