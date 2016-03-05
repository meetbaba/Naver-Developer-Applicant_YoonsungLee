/**
 * 
 */
package com.msp_manager;

/**
 * @author À±¼º
 *
 */
public class ItemContentDaily {

	String value_name;
	String value_success;
	String value_fail;
	
	public ItemContentDaily(String value_name, String value_success, String unit){
		this.value_name=value_name;
		this.value_success=value_success;
		this.value_fail=unit;
	}
	
	public void setItem_value_name(String value_name){
		this.value_name=value_name;

	}
	
	public void setItem_value_success(String value_success){
		this.value_success=value_success;

	}
	
	public void setItem_value_fail(String value_fail){
		this.value_fail=value_fail;
	}
	public String getItem_value_name(){
		return this.value_name;
	}
	
	public String getItem_value_success(){
		return this.value_success;
	}
	
	public String getItem_value_fail(){
		return this.value_fail;
	}
}
