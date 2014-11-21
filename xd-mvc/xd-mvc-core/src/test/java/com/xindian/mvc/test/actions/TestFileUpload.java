package com.xindian.mvc.test.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.xindian.mvc.ActionContext;
import com.xindian.mvc.ActionContextDestoryListener;
import com.xindian.mvc.annotation.Action;
import com.xindian.mvc.annotation.Upload;
import com.xindian.mvc.result.Stream;
import com.xindian.mvc.utils.FileItem;

/**
 * 测试文件上传
 * 
 * @author Elva
 * 
 */
@Action(name = "up")
public class TestFileUpload
{
	/**
	 * 这个跟FORM中的type="file"的表单域的名字相同
	 */
	// 注:参数的注解没有实现,这个@Upload目前在这里没有任何用处
	@Upload(maxSize = 1024 * 1024)
	FileItem file;

	String fileName;

	// 接受上传的文件,然后直接向客户端输出
	public Object uploadImage()
	{
		System.out.println("FILE ContentType:" + file.getContentType());
		System.out.println("FILE FileName:" + file.getFileName());
		System.out.println("FILE OriginalFileName:" + file.getOriginalFileName());
		System.out.println("FILE FilesystemName:" + file.getFilesystemName());

		// 这个DestoryListener只针对这次request有效!配置全局Listener使用ActionContextManager中的方法
		ActionContext.getContext().addDestoryListener(new ActionContextDestoryListener()
		{
			@Override
			public void onActionContextDestoryed(ActionContext context)
			{
				if (file.getFile().exists())
				{
					file.getFile().delete();
					System.out.println("CLEAN UP TEMP FILE:" + file.getFile().getAbsolutePath());
				}
			}
		});
		System.out.println("fileName: " + fileName);
		try
		{
			return new Stream(new FileInputStream(file.getFile()))//
					.setContentType(file.getContentType())//
					.setFileName(file.getFileName());
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return file.getFile();// 直接返回你刚才上传的文件
		}
	}

	public FileItem getFile()
	{
		return file;
	}

	public void setFile(FileItem file)
	{
		this.file = file;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

}
