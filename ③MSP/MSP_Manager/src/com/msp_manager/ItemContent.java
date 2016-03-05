/**
 * release 0.1  : ������Ÿ�� (Prototype)
 * 
 * ���� �ϼ� ��¥ : 2015-06-12
 * 
 * ������ : ������ (Yoonsung Lee)
 *  
 *  �� ���α׷��� ���۱��� �����ڿ��� ������ �������� ������� ���� ����, ����, ������ �����մϴ�.
 * 
 * 
 * ���� ���� : 
 * ���ִ��б� �ý��۰��� ����  ������Ʈ
 * ��米�� : �̼���
 * �����ü : MSP (���� ������)
 * 
 * 
 * ���α׷� ���� :
 * 
 * �� ���α׷��� ���ִ��б� 2015�⵵ 1�б� �ý��� ���� �������� ����� ���� ������Ʈ �������� ���������.
 * �� ���α׷� MSP_Manager�� ȸ�� ���� ���� �/���� ������ ��� ����� ��� �� ������ ���� �����ϴ� MSP�� �ý����� �����ϱ����� ���������.
 * �� ���α׷��� MSP_Manager�� ���ؼ� ������ ��å�� �����ڴ� �ս��� �۾����� ������ �� �ְ�, ��� ������ �����޾� �����δ� ���ϰ� 
 * �۾��� �����ϰ� ��ǥ�޼��� ���� ���������� ���̼�, �׸��� ����ũ ��Ȳ�� ���� �ﰢ���� ��ó�� ���� �� �ִ�.
 * 
 * 
 */
package com.msp_manager;

/**
 * @author ����
 *
 */
public class ItemContent {

	String value_textView;
	String value_editText;
	String value_unit;
	
	public ItemContent(String value_textView, String value_editText, String unit){
		this.value_textView=value_textView;
		this.value_editText=value_editText;
		this.value_unit=unit;
	}
	
	public void setItem_value_textView(String value_textView){
		this.value_textView=value_textView;

	}
	
	public void setItem_value_editText(String value_editText){
		this.value_editText=value_editText;

	}
	
	public void setItem_value_unit(String value_unit){
		this.value_unit=value_unit;
	}
	public String getItem_value_textView(){
		return this.value_textView;
	}
	
	public String getItem_value_editText(){
		return this.value_editText;
	}
	
	public String getItem_value_unit(){
		return this.value_unit;
	}
}
