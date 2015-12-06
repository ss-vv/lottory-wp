package com.xhcms.lottery.mc.sms.platform;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;




@XmlRootElement(name="request")
@XmlType(propOrder={"userId","password","templateId","phone","port","data","signature"})
public class AspireSmsPlatformRequest {
	private String userId;
	private String password;
	private String templateId;
	private String phone;
	private String port;
	private String data;
	private String signature;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String toXml() {
		String result=null;
		try {
			JAXBContext jc = JAXBContext.newInstance(AspireSmsPlatformRequest.class);
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter sw=new StringWriter();
			m.marshal(this, sw);
			result=sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}

}
