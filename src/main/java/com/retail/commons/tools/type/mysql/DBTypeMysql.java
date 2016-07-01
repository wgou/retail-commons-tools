package com.retail.commons.tools.type.mysql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.retail.commons.tools.type.DBType;

public class DBTypeMysql implements DBType{
	private static final Map<String,Class<?>> mysqlMap = new HashMap<String,Class<?>>(); 
	static{
		mysqlMap.put(MysqlType.FLOAT.toString(),Float.class);
		mysqlMap.put(MysqlType.CHAR.toString(),char.class);
		mysqlMap.put(MysqlType.DATE.toString(),Date.class);
		mysqlMap.put(MysqlType.TINYINT.toString(),Byte.class);
		mysqlMap.put(MysqlType.INT.toString(),Integer.class);
		mysqlMap.put(MysqlType.NUMBER.toString(),Long.class);
		mysqlMap.put(MysqlType.BIGINT.toString(),Long.class);
		mysqlMap.put(MysqlType.VARCHAR.toString(),String.class);
		mysqlMap.put(MysqlType.TIMESTAMP.toString(),Timestamp.class);
		mysqlMap.put(MysqlType.DATETIME.toString(),Date.class);
		mysqlMap.put(MysqlType.DOUBLE.toString(),Double.class);
		mysqlMap.put(MysqlType.DECIMAL.toString(),BigDecimal.class);
		
	}
	
	public  Class<?>  converClass(String columnType){
		for(Map.Entry<String, Class<?>> map : mysqlMap.entrySet()){
			
			if(columnType.toUpperCase().startsWith(map.getKey())){
				return map.getValue();
			}
		}
		return mysqlMap.get("VARCHAR");
	}
	
	public String getJdbcType(String type){
		//过滤一些类型
		if(type.equals("Byte")){
			return MysqlType.TINYINT.toString();
		}else if(type.equals("Integer") ){
			return "INTEGER";
		}else if(type.equals("Date")){
			return "DATE";
		}
		for(Map.Entry<String, Class<?>> map : mysqlMap.entrySet()){
			String resultType = map.getValue().getName();
			resultType = resultType.indexOf(".") > 0 ? resultType.substring(resultType.lastIndexOf(".") +1 , resultType.length()):resultType;
			if(resultType.equals(type)){
				//number 类型特殊处理
				if(map.getKey().toUpperCase().equals(MysqlType.NUMBER.toString())){
					return "NUMERIC";
				}
					
				return map.getKey().toUpperCase();
			}
		}
		//不存在统一返回Varchar
		return MysqlType.VARCHAR.toString();
	}
	
}
