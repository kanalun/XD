package com.xindian.mvc.result;

import java.util.ArrayList;
import java.util.List;

/**
 * 有的时候你可能需要有多个结果被处理<br/>
 * 
 * 可以使用MultipleResult封装多个结果然后返回, <br/>
 * 
 * 当然前提是这些结果必须不能是冲突的,比如:返回两个视图是不允许的!
 * 
 * 常规的作用是比如,你可能希望返回一个email(发送Email)<br/>
 * 
 * 的同时告诉用户你已经向他的邮箱中发送了这封邮件了,<br/>
 * 
 * 发送邮件跟通知用户这两个事情是不冲突的!<br/>
 * 
 * 你必须小心的使用该功能,应为它可能并不友好,<br/>
 * 
 * 事实上我们还找不到有更好的检查问题的方法,<br/>
 * 
 * 因为有些返回结果的处理存在顺序以及同步/异步的问题<br/>
 * 
 * @author Elva
 * @date 2011-1-17
 * @version 1.0
 */
public class MultipleResult extends AbstractResult<MultipleResult> implements Resultable
{
	private ViewResult viewResult;// 视图结果

	private List<Resultable> results = new ArrayList<Resultable>();

	@Override
	public Class<? extends ResultHandler> getHandler()
	{
		return null;
	}

	public MultipleResult(ViewResult result, List<Resultable> list)
	{

	}

	public MultipleResult(ViewResult result)
	{

	}
}
