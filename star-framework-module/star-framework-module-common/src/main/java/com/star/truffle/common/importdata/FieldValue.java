/**create by liuhua at 2018年1月20日 上午11:01:59**/
package com.star.truffle.common.importdata;

import java.io.Serializable;

public class FieldValue implements Serializable {
	private static final long serialVersionUID = 1L;

	private int rowNum;
	private int colNum;
	private String fieldName;
	private String caption;
	private Object value;             //入库需要的值
	private Object sourceValue;       //Excel中填的值
	private boolean valid;
	private String desc;
	
	public void setValidValue(int rowNum, int colNum, String fieldName, String caption, Object sourceValue, Object value){
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.fieldName = fieldName;
		this.caption = caption;
		this.value = value;
		this.sourceValue = sourceValue;
		this.valid = true;
	}
	
	public void setErrorValue(int rowNum, int colNum, String fieldName, String caption, Object sourceValue, String desc){
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.fieldName = fieldName;
		this.caption = caption;
		this.sourceValue = sourceValue;
		this.value = null;
		this.valid = false;
		this.desc = desc;
	}
	
	public void toError(String desc) {
		this.valid = false;
		this.desc = desc;
	}
	
	public void toError(String sourceValue, String desc) {
		this.valid = false;
		this.sourceValue = sourceValue;
		this.desc = desc;
	}
	
	public Object getValue() {
		return value;
	}

	public boolean isValid() {
		return valid;
	}

	public String getDesc() {
		return desc;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getCaption() {
		return caption;
	}

	public int getColNum() {
		return colNum;
	}

	public Object getSourceValue() {
		return sourceValue;
	}
}
