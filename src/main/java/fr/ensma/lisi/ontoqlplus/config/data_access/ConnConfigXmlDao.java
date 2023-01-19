/*********************************************************************************
* This file is part of OntoQLPlus Project.
* Copyright (C) 2006  LISI - ENSMA
*   Teleport 2 - 1 avenue Clement Ader
*   BP 40109 - 86961 Futuroscope Chasseneuil Cedex - FRANCE
* 
* OntoQLPlus is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* OntoQLPlus is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public License
* along with OntoQLPlus.  If not, see <http://www.gnu.org/licenses/>.
**********************************************************************************/
package fr.ensma.lisi.ontoqlplus.config.data_access;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import fr.ensma.lisi.ontoqlplus.common.Environment;
import fr.ensma.lisi.ontoqlplus.config.ConnConfig;

/**
 * @author Stéphane JEAN
 */
public class ConnConfigXmlDao {

	public static Element oracleConnConfigObjectToXML(ConnConfig config) {
		return createConfigElement(config);
	}

	public static void writeConfigs(Vector configs) throws IOException {
		File configFile = new File(Environment.CONN_CONFIG_FILE_PATH);
		Element rootElement = new Element("connection_configs");
		for (int i = 0; i < configs.size(); i++)
			rootElement.addContent(createConfigElement((ConnConfig) configs.get(i)));
		FileWriter writer = new FileWriter(configFile);
		XMLOutputter xmlOutputter = new XMLOutputter("  ", true);
		xmlOutputter.output(new Document(rootElement), writer);
		writer.close();
	}

	public static Vector loadSavedConfigs() throws JDOMException {
		Vector savedConfigs = new Vector();
		if (configFileExists()) {
			Document doc = new SAXBuilder().build("file:///" + Environment.CONN_CONFIG_FILE_PATH);
			Element rootElement = doc.getRootElement();
			List children = rootElement.getChildren();
			for (int i = 0; i < children.size(); i++) {
				Element configElement = (Element) children.get(i);
				ConnConfig config = new ConnConfig(configElement.getAttribute("name").getValue(),
						configElement.getChild("ip").getTextTrim(),
						Integer.parseInt(configElement.getChild("port").getTextTrim()),
						configElement.getChild("sid").getTextTrim(), configElement.getChild("user").getTextTrim(),
						configElement.getChild("password").getTextTrim());
				savedConfigs.add(config);
			}
		}
		return savedConfigs;
	}

	private static Element createConfigElement(ConnConfig config) {
		// configElement
		Element configElement = new Element("Config");
		// Config attribute name
		configElement.setAttribute("name", config.getName());
		// ipElement
		Element ipElement = new Element("ip");
		ipElement.addContent(config.getIp());
		configElement.addContent(ipElement);
		// portElement
		Element portElement = new Element("port");
		portElement.addContent(Integer.toString(config.getPort()));
		configElement.addContent(portElement);
		// sidElement
		Element sidElement = new Element("sid");
		sidElement.addContent(config.getSid());
		configElement.addContent(sidElement);
		// userElement
		Element userElement = new Element("user");
		userElement.addContent(config.getUser());
		configElement.addContent(userElement);
		// passwordElement
		if (config.getPassword() != null) { // password optionally saved
			Element passwordElement = new Element("password");
			passwordElement.addContent(config.getPassword());
			configElement.addContent(passwordElement);
		}

		return configElement;
	}

	private static boolean configFileExists() {
		return new File(Environment.CONN_CONFIG_FILE_PATH).exists();
	}
}
