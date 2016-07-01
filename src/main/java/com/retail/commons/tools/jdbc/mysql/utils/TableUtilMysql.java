package com.retail.commons.tools.jdbc.mysql.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.retail.commons.tools.jdbc.Columns;
import com.retail.commons.tools.utils.JdbcUtil;
import com.retail.commons.tools.utils.TableUtil;

/***
 * 根据用户填写，获取数据库表结构信息
 * @author 苟伟
 * 2013/10/10
 */
public class TableUtilMysql extends JdbcUtil implements TableUtil{
	
	//读取指定用户数据库所有表
	public  List<String> getTables() throws SQLException, ClassNotFoundException{
		List<String> tables = new ArrayList<String>();
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement("SHOW TABLES ");
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			tables.add(rs.getString(1));
		}
		return tables;
	}
	//查询指定表的列名和列类型以及注释
	public List<Columns> getTableColumn(String tableName) throws SQLException{
		List<Columns> listColumn = new ArrayList<Columns>();
		String sql = "SHOW FULL COLUMNS FROM  " + tableName ;
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			Columns c = new Columns();
			c.setColumnName(rs.getString("Field"));
			c.setType(rs.getString("Type"));
			c.setComment(rs.getString("Comment"));
			listColumn.add(c);
		}
		return listColumn;
	}
	//查询table主键,mysql 不处理主键
	public List<String> getPrimaryKeyName(String tableName) throws SQLException{
		List<String> list = new ArrayList<String>();
		String sql = "SELECT  t.TABLE_NAME, t.CONSTRAINT_TYPE,c.COLUMN_NAME,c.ORDINAL_POSITION " +
					" FROM  INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t,  INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS c "+
					" WHERE t.TABLE_NAME = c.TABLE_NAME AND t.TABLE_NAME = '"+tableName+"' AND t.CONSTRAINT_TYPE = 'PRIMARY KEY'";
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		while(rs.next()){
			list.add(rs.getString("COLUMN_NAME"));
		}
		return list;
	}
	
}
