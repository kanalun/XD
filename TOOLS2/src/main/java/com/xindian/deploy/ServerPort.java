package com.xindian.deploy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a port on which the server will listen for connections. Used to
 * aggregate information that the rest of the system needs regarding the port
 * while hiding implementation details.
 */
public class ServerPort
{
	private int port;

	/** portNames */
	private List<String> names = new ArrayList<String>(1);

	private String address;
	/** 是否加密SSL */
	private boolean secure;
	private String algorithm;

	/** 端口类型 */
	private Type type;

	public ServerPort(int port, String name, String address, boolean isSecure, String algorithm, Type type)
	{
		this.port = port;
		this.names.add(name);
		this.address = address;
		this.secure = isSecure;
		this.algorithm = algorithm;
		this.type = type;
	}

	/**
	 * Returns the port number that is being used.
	 * 
	 * @return the port number this server port is listening on.
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Returns the logical domains for this server port. As multiple domains may
	 * point to the same server, this helps to define what the server considers
	 * "local".
	 * 
	 * @return the server domain name(s) as Strings.
	 */
	public List<String> getDomainNames()
	{
		return Collections.unmodifiableList(names);
	}

	/**
	 * Returns the dot separated IP address for the server.
	 * 
	 * @return The dot separated IP address for the server
	 */
	public String getIPAddress()
	{
		return address;
	}

	/**
	 * Determines if the connection is secure.
	 * 
	 * @return True if the connection is secure
	 */
	public boolean isSecure()
	{
		return secure;
	}

	/**
	 * Returns the basic protocol/algorithm being used to secure the port
	 * connections. An example would be "SSL" or "TLS".
	 * 
	 * @return The protocol used or null if this is not a secure server port
	 */
	public String getSecurityType()
	{
		return algorithm;
	}

	/**
	 * Returns true if other servers can connect to this port for s2s
	 * communication.
	 * 
	 * @return true if other servers can connect to this port for s2s
	 *         communication.
	 */
	public boolean isServerPort()
	{
		return type == Type.server;
	}

	/**
	 * Returns true if clients can connect to this port.
	 * 
	 * @return true if clients can connect to this port.
	 */
	public boolean isClientPort()
	{
		return type == Type.client;
	}

	/**
	 * Returns true if external components can connect to this port.
	 * 
	 * @return true if external components can connect to this port.
	 */
	public boolean isComponentPort()
	{
		return type == Type.component;
	}

	/**
	 * Returns true if connection managers can connect to this port.
	 * 
	 * @return true if connection managers can connect to this port.
	 */
	public boolean isConnectionManagerPort()
	{
		return type == Type.connectionManager;
	}

	public Type getType()
	{
		return type;
	}

	public static enum Type
	{
		client,

		server,

		component,

		connectionManager
	}
}
