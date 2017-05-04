package com.tlcb.bdp.admin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtil {
	public Statement st;

	public DbUtil() {

	}

	/**
	 * paraMap参数 driverClassName 驱动 JdbcURL jdbc 该字符串为： config.ini
	 * 的JdbcURL+IP地址(含端口号)+dbStr+数据库名称 userName 数据库名称 userpwd 数据库密码
	 */
	public Statement getStatement(Map<String, String> paraMap) {
		// 加载数据库驱动
		try {
			Class.forName(paraMap.get("driver"));
			Connection conn = DriverManager.getConnection(paraMap.get("jdbc"), paraMap.get("userName"),
					paraMap.get("userpwd"));
			if (!conn.isClosed()) {
			} else {
				return null;
			}
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			return statement;
			// 要执行的SQL语句
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取数据库表信息
	public List getTableNames(Map<String, String> dbInfoMap, Map<String,String> schemaNameTableName) {
		List<String> tableList = new ArrayList<String>(); // 存储表名
		try {
			st = this.getStatement(dbInfoMap);
			if (st == null) {
				return null;
			}
			// 替换数据库名字占位符
			String selTableSql = String.format(dbInfoMap.get("showTable").toString(),schemaNameTableName.get("schema"),schemaNameTableName.get("table") );
			ResultSet tabRs = st.executeQuery(selTableSql);
			// 保存表名
			tableList = convertList(tabRs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return tableList;
	}

	// 读取表字段信息
	public List getColumnNames(Map<String, String> dbInfoMap, String tabName) {
		List colList = new ArrayList(); // 存储字段信息
		try {
			st = this.getStatement(dbInfoMap);
			if (st == null) {
				return null;
			}
			// 替换表名占位符

			String selColumnSql = dbInfoMap.get("showColumns").toString().replace("%", tabName);
			// 模式处理
			if (dbInfoMap.get("dbType").equalsIgnoreCase("DB2")) {
				selColumnSql = selColumnSql.replace("$", dbInfoMap.get("schamaName"));
			}

			ResultSet columnRs = st.executeQuery(selColumnSql);
			colList = convertList(columnRs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return colList;
	}
	
	// 读取表字段信息
	public List getColumnNames(Map<String, String> dbInfoMap, String tabName,String schemaName) {
		List colList = new ArrayList(); // 存储字段信息
		try {
			st = this.getStatement(dbInfoMap);
			if (st == null) {
				return null;
			}
			// 替换表名占位符

			String selColumnSql = dbInfoMap.get("showColumns").toString().replace("%", tabName);
			// 模式处理
			if (dbInfoMap.get("dbType").equalsIgnoreCase("DB2")) {
				selColumnSql = selColumnSql.replace("$", schemaName.trim());
			}

			ResultSet columnRs = st.executeQuery(selColumnSql);
			colList = convertList(columnRs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return colList;
	}

	// 读取配置
	@SuppressWarnings("finally")
	public Map<String, HashMap<String, String>> getDbConfigMap() {
		Map<String, HashMap<String, String>> sectionsMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> itemsMap = new HashMap<String, String>();
		String currentSection = "";
		BufferedReader reader = null;
		try {
			// 读取当前文件路径下的ini文件
			System.out.println(this.getClass().getResourceAsStream("config.ini"));
			InputStream configStream = this.getClass().getResourceAsStream("config.ini");
			reader = new BufferedReader(new InputStreamReader(configStream, "UTF-8"));

			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if ("".equals(line))
					continue;
				if (line.startsWith("[") && line.endsWith("]")) {
					itemsMap = new HashMap<String, String>();
					currentSection = line.substring(1, line.length() - 1);
					sectionsMap.put(currentSection, itemsMap);
					currentSection = "";
				} else {
					int index = line.indexOf("=");
					if (index != -1) {
						String key = line.substring(0, index);
						String value = line.substring(index + 1, line.length());
						itemsMap.put(key, value.trim());
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return sectionsMap;
		}
	}

	private static List convertList(ResultSet rs) throws SQLException {

		List list = new ArrayList();

		ResultSetMetaData md = rs.getMetaData();

		int columnCount = md.getColumnCount(); // Map rowData;

		while (rs.next()) { // rowData = new HashMap(columnCount);

			Map rowData = new HashMap();
			
			for (int i = 1; i <= columnCount; i++) {

				rowData.put(md.getColumnName(i), rs.getObject(i));
			}

			list.add(rowData);

		}
		return list;

	}
}
