/*
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 */
package com.xhcms.lottery.admin.web.menu.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.xhcms.lottery.admin.web.menu.Menu;
import com.xhcms.lottery.admin.web.menu.MenuItem;
import com.xhcms.lottery.admin.web.menu.MenuManager;


/**
 * @author hdzhong
 * @version 0.0.1
 */
public class XmlMenuManager implements MenuManager {
	String configFile = "menu.xml";
	List<Menu> menus;
	
	public XmlMenuManager() {
		menus = new LinkedList<Menu>();
	}
	
	public void init()throws IOException{
		URL url = this.getClass().getClassLoader().getResource(configFile);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		InputStream in = url.openStream();
		try {
			
			SAXParser parser = factory.newSAXParser();
			parser.parse(in, new MenuParser(menus));
		} catch (SAXException e) {
			throw new IOException("Fail to parse menu.xml, file: "+url, e);
		} catch (ParserConfigurationException e) {
			throw new IOException(e.getMessage(), e);
		}
		in.close();
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	@Override
	public List<Menu> getMenus() {
		return menus;
	}
	
	@Override
	public List<Menu> getCloneMenus() {
		List<Menu> clone = new LinkedList<Menu>();
		for(Menu menu:menus){
			clone.add((Menu)menu.clone());
		}
		return clone;
	}

	private static class MenuParser extends DefaultHandler{
		List<Menu> menus;
		private Stack<Object> elementStack;
		
		public MenuParser(List<Menu> menus){
			this.menus = menus;
			this.elementStack = new Stack<Object>();
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			String tag = "";
			if ("".equals (uri))
			    tag = qName;
			else
			    tag = localName;
			
			Object lastElement = null;
			if(elementStack.size() > 0)
				lastElement = elementStack.peek();
			
			if("menu".equalsIgnoreCase(tag)){
				if(lastElement != null && lastElement instanceof Menu){
					throw new SAXException("expect \"item\" tag, but encounter "+tag+", name="+attributes.getValue("name"));
				}
				Menu menu = new Menu();
				menu.setName(attributes.getValue("name"));
				menu.setAlias(attributes.getValue("alias"));
				if(attributes.getValue("right") != null){
					menu.setRight(Long.parseLong(attributes.getValue("right"), 16));
				}
				elementStack.push(menu);
			}else if("item".equalsIgnoreCase(tag)){
				if(lastElement == null || lastElement instanceof MenuItem){
					throw new SAXException("expect \"menu\" tag, but encounter "+tag+", name="+attributes.getValue("name"));
				}
				MenuItem menuItem = new MenuItem();
				menuItem.setName(attributes.getValue("name"));
				menuItem.setAlias(attributes.getValue("alias"));
				menuItem.setScript(attributes.getValue("script"));
				menuItem.setUrl(attributes.getValue("url"));
				if(attributes.getValue("right") != null){
					menuItem.setRight(Long.parseLong(attributes.getValue("right"), 16));
				}
				if(attributes.getValue("unique") != null){
					menuItem.setUnique(Boolean.parseBoolean(attributes.getValue("unique")));
				}
				if(attributes.getValue("target") != null){
					menuItem.setTarget(attributes.getValue("target"));
				}
				if(attributes.getValue("option") != null){
					menuItem.setOption(attributes.getValue("option"));
				}
				if(attributes.getValue("service") != null){
					menuItem.setService(Boolean.parseBoolean(attributes.getValue("service")));
				}
				elementStack.push(menuItem);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			String tag = "";
			if ("".equals (uri))
			    tag = qName;
			else
			    tag = localName;
			
			if(!"menu".equalsIgnoreCase(tag) && !"item".equalsIgnoreCase(tag)){
				return;
			}
			
			Object lastElement = elementStack.pop();
			if(lastElement instanceof Menu){
				if(elementStack.size() == 0){
					this.menus.add((Menu)lastElement);
					return;
				}
				MenuItem parent = (MenuItem)elementStack.peek();
				if(parent.getSubMenu() != null){
					throw new SAXException("item tag can't contian more than one Menu element, item's name: "+parent.getName());
				}
				parent.setSubMenu((Menu)lastElement);
			}else if(lastElement instanceof MenuItem){
				Menu parent = (Menu)elementStack.peek();
				parent.addMenuItem((MenuItem)lastElement);
			}
		}
		
	}
	
	public static void main(String[] argv)throws Exception{
		XmlMenuManager mm = new XmlMenuManager();
		mm.init();
		mm.getMenus();
	}
}
