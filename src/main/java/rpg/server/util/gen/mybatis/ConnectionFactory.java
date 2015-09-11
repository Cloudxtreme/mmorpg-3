package rpg.server.util.gen.mybatis;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import rpg.server.util.StringUtil;

public class ConnectionFactory {

	private static ConnectionFactory instance = new ConnectionFactory();

	public static ConnectionFactory getInstance() {
		return instance;
	}

	/**
	 *  
	 */
	private ConnectionFactory() {
		super();
	}

	public Connection getConnection(JDBCConnectionConfiguration config)
			throws SQLException {
		Driver driver = getDriver(config);

		Properties props = new Properties();

		if (StringUtil.stringHasValue(config.getUser())) {
			props.setProperty("user", config.getUser()); //$NON-NLS-1$
		}

		if (StringUtil.stringHasValue(config.getPassword())) {
			props.setProperty("password", config.getPassword()); //$NON-NLS-1$
		}

		// props.putAll(config.getProperties());

		Connection conn = driver.connect(config.getConnectionURL(), props);

		if (conn == null) {
			throw new SQLException(
					"Cannot connect to database (possibly bad driver/URL combination)"); //$NON-NLS-1$
		}

		return conn;
	}

	private Driver getDriver(JDBCConnectionConfiguration connectionInformation) {
		String driverClass = connectionInformation.getDriverClass();
		Driver driver;

		try {
			Class<?> clazz = Class.forName(driverClass);
			driver = (Driver) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Exception getting JDBC Driver", e); //$NON-NLS-1$
		}

		return driver;
	}

	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// ignore
				;
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// ignore
				;
			}
		}
	}

}
