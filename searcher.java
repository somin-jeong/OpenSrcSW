package gitlab03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class searcher {
	public searcher(String args) {
		
	}
	
	static String data;
	
	public static void query(String args) {
		System.out.print("입력 : ");		
		data = args;
		System.out.println(data);
	}
	
	public static double[] InnerProduct(String args) throws IOException, ClassNotFoundException {
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(data, true);
		
		
		FileInputStream fileinputstream = new FileInputStream(args);
		ObjectInputStream ois = new ObjectInputStream(fileinputstream);
		
		Object object = ois.readObject();
		ois.close();


		double[] id = new double[5];
		
		for(int k=0; k<kl.size(); k++) {
			
			HashMap hm = (HashMap) object;
			Iterator<String> it = hm.keySet().iterator();
			
			String content = null;
			
			Keyword kwrd = kl.get(k);
			
			content = kwrd.getString();
			while(it.hasNext()) {
				String key = it.next();
				
				
				if(key.compareTo(content) == 0) {
					List value = (List) hm.get(key);
					
					for(int i=0; i<value.size(); i++) {
					  
					    String s = value.get(i).toString();
					    double d = Double.valueOf(s).doubleValue();
						if(d == 0.0) {
							id[0] += (double)kwrd.getCnt() * (double)value.get(i+1);
							
						}
						if(d==1.0) {
							id[1] += (double)kwrd.getCnt() * (double)value.get(i+1);
							
						}
						if(d==2.0) {
							id[2] += (double)kwrd.getCnt() * (double)value.get(i+1);
							
						}
						if(d==3.0) {
							id[3] += (double)kwrd.getCnt() * (double)value.get(i+1);
							
						}
						if(d==4.0) {
							id[4] += (double)kwrd.getCnt() * (double)value.get(i+1);
						
						}
						
					}
				}
				
			}	
		}
		
		return id;
	}
	
	
	public static void make(String args) throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
		
		String url = args.replace("index.post", "collection.xml");

		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		Document doc = null;
		try{
			doc = dBuilder.parse(url);
		}catch(MalformedURLException e) {
			
		}
		
		Element root = doc.getDocumentElement();

		
		NodeList child = root.getChildNodes();
		String[] title = new String[child.getLength()];
		
		for(int i = 0; i < child.getLength(); i++) {
			Node node = child.item(i);

			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element ele = (Element)node;
				
				// <doc id = "0">
				String nodeName = ele.getNodeName();
				String s = null;
				
				if(nodeName.equals("doc")){
					s = ele.getAttribute("id");
				}
				
				// <title> <body>
				NodeList children = ele.getChildNodes();
				
				for(int j = 0; j < children.getLength(); j++) {
					Node node1 = children.item(j);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element ele1 = (Element)node1;
						String nodeName1 = ele1.getNodeName();
						String ti = ele1.getTextContent();
						ti.trim();
						if(nodeName1 == "title") {
							title[i] = ti;

						}
					}
				}	
				
			}
		}
		
		
		double[] id = InnerProduct(args);
		
		
		
		for(int i=0; i<id.length; i++) {
			System.out.println(id[i]);
		}
		
		
		int[] num = new int[id.length];
		double n = 0;
		
		for(int i=0; i<id.length; i++) {
			num[i] = i;
		}
		
		for(int i=0; i<id.length; i++) {
			for(int j=i+1; j<id.length; j++) {
				if(id[i] < id[j]) {
					int temp = num[i];
					num[i] = num[j];
					num[j] = temp; 
				}
			}
		}
		

		for(int i=0; i<3; i++) {
			System.out.println(title[num[i]]);
		}
		
		
		
		
		
	}

}





