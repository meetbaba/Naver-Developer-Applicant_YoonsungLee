/**
 * <pre>
 * 1. ������Ʈ�� : excavateTerm
 * 2. ��Ű����(�Ǵ� ���丮 ���) : com.excavateterm
 * 3. ���ϸ� : ItemContent_Sixth.java
 * 4. �ۼ��� : 2015. 8. 9. ���� 7:43:39
 * 5. �ۼ��� : ����
 * 6. ���� :
 * </pre>
 */
package com.excavateterm;

import android.os.Handler;

/**
 * ������ : ����
 * �ۼ��� : 2015. 8. 9. ���� 7:43:39
 * ���� : 
 */
public class ItemContent {

	
	String text;
	int status;
	
	Handler handler;
	
	public ItemContent(String text, Handler handler) {
		// TODO Auto-generated constructor stub
		this.text=text;
		this.status=StaticStrings.STATUS_PRE;
		this.handler=handler;
	}
	
	public ItemContent(String text, Handler handler, int status) {
		// TODO Auto-generated constructor stub
		this.text=text;
		this.status=status;
		this.handler=handler;
	}

	/**
	 * @return the handler
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
