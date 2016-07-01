/**  
 * Project Name:OSUtil  
 * File Name:TableUtil.java  
 * Package Name:org.util  
 * Date:2016年4月3日上午11:05:47  
 * Copyright (c) 2016, 成都瑞泰尔科技有限公司 All Rights Reserved.  
 *  
*/  
  
package com.retail.commons.tools.utils;  

import java.sql.SQLException;
import java.util.List;

import com.retail.commons.tools.jdbc.Columns;

/**  
 * 描述: <br/>TODO; <br/>
 * ClassName:TableUtil <br/>  
 * Date:     2016年4月3日 上午11:05:47 <br/>  
 * @author   苟伟(gouewi@retail-tek.com) 
 * @version enclosing_typetags  
 * @see        
 */
public interface TableUtil {

		/**
		 * 获取所有的表
		 * @author luochen  
		 * @return
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 */
	  List<String> getTables() throws SQLException, ClassNotFoundException;
	  /**
	   *  获取指定表的列名和类型
	   * @author luochen  
	   * @param tableName
	   * @return
	   * @throws SQLException
	   */
	 List<Columns> getTableColumn(String tableName) throws SQLException;
	  
	  /**
	   * 获取指定表的主键
	   */
	  List<String> getPrimaryKeyName(String tableName) throws SQLException;
}
  
