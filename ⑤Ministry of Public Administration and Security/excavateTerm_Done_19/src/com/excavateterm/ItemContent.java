/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : ItemContent_Sixth.java
 * 4. 작성일 : 2015. 8. 9. 오전 7:43:39
 * 5. 작성자 : 윤성
 * 6. 설명 :
 * </pre>
 */
package com.excavateterm;

import android.os.Handler;

/**
 * 개발자 : 윤성
 * 작성일 : 2015. 8. 9. 오전 7:43:39
 * 설명 : 
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
