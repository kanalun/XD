package com.xindian.deploy;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Elva
 * @date 2013-2-2
 * @version 1.0.0
 */
public class SimpleHttpServer extends BasicModule
{
	private static Logger Log = LoggerFactory.getLogger(SimpleHttpServer.class);

	/** Jetty服务器 */
	private Server server = new Server();

	/** Jetty http 端口 */
	private int httpPort = 8080;

	/** war路径 */
	private String warPath = "D:\\Elva\\WebstormProjects\\1\\client";

	public SimpleHttpServer()
	{
		super("SimpleHttpServer");
	}

	/**
	 * 启动CRMHttp服务器
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public void start()
	{
		Log.info("about to start SimpleHttpServer...");
		Log.info(warPath);
		Connector connector = new SelectChannelConnector();
		connector.setPort(httpPort);
		server.setConnectors(new Connector[] { connector });
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.addServlet(new ServletHolder(new TestServlet()), "/quartzsManage");
		webAppContext.setContextPath("/");
		webAppContext.setWar(warPath); // 如需指定war目录，则相对应地在工程目录下建立一同名目录，否则启动时会产生异常
		server.setHandler(webAppContext);
		try
		{
			server.start();
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		Log.info("CRMHttpServer started!");
	}

	/**
	 * 关闭CRMHttp服务器
	 */
	@Override
	public void stop()
	{
		// CertificateManager.removeListener(certificateListener);
		if (server != null)
		{
			try
			{
				server.stop();
			} catch (Exception e)
			{
				Log.error("Error stoping HTTP bind service", e);
			}
		}
	}

	public static void main(String args[]) throws Exception
	{
		SimpleHttpServer httpServer = new SimpleHttpServer();
		httpServer.start();
	}

	@Override
	public String getName()
	{
		return "SimpleHttpServer";
	}

	@Override
	public void initialize(Container server)
	{

	}

	@Override
	public void destroy()
	{

	}
}
