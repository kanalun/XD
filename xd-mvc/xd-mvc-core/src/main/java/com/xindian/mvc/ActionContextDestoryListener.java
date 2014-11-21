package com.xindian.mvc;

/**
 * ActionContext Destory 的时候(之前)会触发响应的事件<br/>
 * 
 * 利用它我们可以见证ActionContext的灭亡,这个在有的时
 * 
 * 候还是蛮有用的,比如:临时文件清理,资源回收,关闭必要的连接等
 * 
 * 再比如可以用此监听设计一个"Session Per Request",在请求结束的时候关闭session
 * 
 * @author Elva
 * 
 */
public interface ActionContextDestoryListener
{
	public void onActionContextDestoryed(ActionContext context);
}
