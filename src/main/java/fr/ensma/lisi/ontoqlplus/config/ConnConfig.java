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
package fr.ensma.lisi.ontoqlplus.config;

/**
 * @author Stéphane JEAN
 */
public class ConnConfig implements Comparable {

	private String name;

	private String ip;

	private int port;

	private String sid;

	private String user;

	private String password;

	public ConnConfig(String name, String ip, int port, String sid, String user, String password) {
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.sid = sid;
		this.user = user;
		this.password = password;
	}

	public boolean equals(Object arg) {
		if (!(arg instanceof ConnConfig))
			return false;
		ConnConfig other = (ConnConfig) arg;
		return name.equals(other.name) && ip.equals(other.ip) && port == other.port && sid.equals(other.sid)
				&& user.equals(other.user) && password.equals(other.password);
	}

	public String toString() {
		return name;
	}

	public String url() {
		return "jdbc:postgresql://" + ip + ":" + port + "/" + sid;
	}

	public int compareTo(Object arg) {
		ConnConfig other = (ConnConfig) arg;
		return getName().toLowerCase().compareTo(other.getName().toLowerCase());
	}

	public String getName() {
		return name;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getSid() {
		return sid;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
