package com.retail.commons.tools.jdbc.easyj;

import java.util.List;

import com.retail.commons.tools.jdbc.TableColumn;

 
public class Table {
	private String tableName;
	private String forkeyName;
	private List<PrmiryKey> prmiryKey;
	private List<TableColumn> propertyName;  //列名 value:property type comment
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getForkeyName() {
		return forkeyName;
	}
	public void setForkeyName(String forkeyName) {
		this.forkeyName = forkeyName;
	}

	public List<TableColumn> getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(List<TableColumn> propertyName) {
		this.propertyName = propertyName;
	}
	public List<PrmiryKey> getPrmiryKey() {
		return prmiryKey;
	}
	public void setPrmiryKey(List<PrmiryKey> prmiryKey) {
		this.prmiryKey = prmiryKey;
	}
	
	
}
