package com.retail.commons.tools.type.oracle;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.retail.commons.tools.type.DBType;

public class DBTypeOracle implements DBType{
	private static final Map<String,Class<?>> oracleMap = new HashMap<String,Class<?>>(); 
	static{
		oracleMap.put(OracleType.BINARY_DOUBLE.toString(),Double.class);
		oracleMap.put(OracleType.BINARY_FLOAT.toString(),Float.class);
		oracleMap.put(OracleType.CHAR.toString(),char.class);
		oracleMap.put(OracleType.DATE.toString(),Date.class);
		oracleMap.put(OracleType.LONG.toString(),Long.class);
		oracleMap.put(OracleType.NUMBER.toString(),Integer.class);
		oracleMap.put(OracleType.NVARCHAR2.toString(),String.class);
		oracleMap.put(OracleType.TIMESTAMP.toString(),Timestamp.class);
		
	}
	
	public Class<?>  converClass(String columnType){
		if(columnType.toUpperCase().startsWith("TIMESTAMP")){
			return  oracleMap.get("TIMESTAMP");
		}
		Class<?> clazz = oracleMap.get(columnType);
		if( clazz== null){ 
			return oracleMap.get(OracleType.NVARCHAR2.toString());
		}
		return clazz;
	}
	
	public String getJdbcType(String type){
		//过滤一些类型
		if(type.equals("Byte")){
			return OracleType.BINARY_DOUBLE.toString();
		}else if(type.equals("Integer") ){
			return "INTEGER";
		}else if(type.equals("Date")){
			return "DATE";
		}else if(type.equals("String")){
			return "NVARCHAR";
		}
		for(Map.Entry<String, Class<?>> map : oracleMap.entrySet()){
			String resultType = map.getValue().getName();
			resultType = resultType.indexOf(".") > 0 ? resultType.substring(resultType.lastIndexOf(".") +1 , resultType.length()):resultType;
			if(resultType.equals(type)){
				//number 类型特殊处理
				if(map.getKey().toUpperCase().equals(OracleType.NUMBER.toString())){
					return "NUMERIC";
				}
					
				return map.getKey().toUpperCase();
			}
		}
		//不存在统一返回Varchar
		return "NVARCHAR";
	}
	
}
