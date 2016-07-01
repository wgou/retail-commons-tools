package com.retail.commons.tools.utils;

import java.util.ArrayList;
import java.util.List;

import com.retail.commons.tools.jdbc.Columns;
import com.retail.commons.tools.jdbc.TableColumn;
import com.retail.commons.tools.type.DBTypeFactory;

/**
 * 数据库列类型转化为java对象类型
 * @author 苟伟
 *
 */
public class ClassUtil {
	//columnName -> Type -> Comment
	public static List<TableColumn> columnConverList(String database,List<Columns> listTable){
		List<TableColumn> listC = new ArrayList<TableColumn>(listTable.size());
		for(Columns c : listTable){
			TableColumn tableC = new TableColumn();
			tableC.setField(c.getColumnName());
			tableC.setTypeClazz(converClass(database,c.getType()));
			tableC.setComment(c.getComment());
			listC.add(tableC);
		}
		return listC;
	}
	
	private static Class<?> converClass(String database,String columnType){
		return DBTypeFactory.bind(database).converClass(columnType);
		
	}
	
}