package com.xindian.coal;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZkUploadFile
{
	public static void main(String[] args) throws IOException, KeeperException,
			InterruptedException
	{
		String propFilePath = "/Users/elva/workspace/XD/xd-coal/src/main/resources/config.properties";
		// 建立和zookeeper的连接
		ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, new Watcher()
		{
			@Override
			public void process(WatchedEvent watchedEvent)
			{
				System.out.println("zk connection");
			}
		});
		// 将文件的内容转换成输入流
		InputStream is = new FileInputStream(propFilePath);
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		byte[] data = null;
		int ch;
		while ((ch = is.read()) != -1)
		{
			bytestream.write(ch);
		}
		data = bytestream.toByteArray();
		System.out.println(new String(data));
		// 判断路径是否存在
		Stat stat = zk.exists("/testApp1_config_properties", true);
		if (stat == null)
		{
			System.out.println("the path is not exists");
			// 写入路径和文件内容
			zk.create("/testApp1_config_properties", data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
			//zk.create(path, data, acl, createMode, cb, ctx)
		}else
		{
			//zk.delete("/testApp1_config_properties", arg1);
		}

		System.out.println(new String(zk.getData("/testApp1_config_properties", true, null)));
	}
}