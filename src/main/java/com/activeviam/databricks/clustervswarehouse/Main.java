package com.activeviam.databricks.clustervswarehouse;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	private static final String CATALOG_NAME = "hive_metastore";
	private static final String SCHEMA_NAME = "test_schema_debug";
	private static final String TABLE_NAME = "difference_cluster_vs_warehouse";

	private static final String SQL_QUERY_WITH_COALESCE =
			"SELECT \n"
			+ " ARRAY_AGG(DISTINCT COALESCE(`T0`.`date`, '1970-01-01')) AS `distinct_date`\n"
			+ "FROM \n"
			+ " `" + CATALOG_NAME + "`.`" + SCHEMA_NAME + "`.`" + TABLE_NAME + "` AS T0";

	private static final String SQL_QUERY_NO_COALESCE =
			"SELECT \n"
					+ " ARRAY_AGG(DISTINCT `T0`.`date`) AS `distinct_date`\n"
					+ "FROM \n"
					+ " `" + CATALOG_NAME + "`.`" + SCHEMA_NAME + "`.`" + TABLE_NAME + "` AS T0";


	private static final String SERVER_HOSTNAME = System.getenv("DATABRICKS_SERVER_HOSTNAME");
	private static final String HTTP_PATH_CLUSTER = System.getenv("DATABRICKS_HTTP_PATH_CLUSTER");

	private static final String HTTP_PATH_WAREHOUSE = System.getenv("DATABRICKS_HTTP_PATH_WAREHOUSE");

	private static final String CONNECTION_STRING_CLUSTER = "jdbc:databricks://"
			+ SERVER_HOSTNAME + "/default;"
			+ "transportMode=http;"
			+ "ssl=1;"
			+ "httpPath=" + HTTP_PATH_CLUSTER + ";"
			+ "AuthMech=3;"
			+ "UID=token;";

	private static final String CONNECTION_STRING_WAREHOUSE = "jdbc:databricks://"
			+ SERVER_HOSTNAME + ";"
			+ "httpPath=" + HTTP_PATH_WAREHOUSE + ";";

	private static final String AUTH_TOKEN = System.getenv("DATABRICKS_AUTH_TOKEN");

	public static void main(String[] args) throws SQLException {

		LOGGER.info("Performing test on cluster");
		generateTestCase(CONNECTION_STRING_CLUSTER, SQL_QUERY_WITH_COALESCE);
		generateTestCase(CONNECTION_STRING_CLUSTER, SQL_QUERY_NO_COALESCE);
		LOGGER.info("Performing test on warehouse");
		generateTestCase(CONNECTION_STRING_WAREHOUSE, SQL_QUERY_WITH_COALESCE);
		generateTestCase(CONNECTION_STRING_WAREHOUSE, SQL_QUERY_NO_COALESCE);

	}

	private static void generateTestCase(final String connectionString, final String query) throws SQLException {
		LOGGER.info(() -> "Test for query " + query);
		try (
				final Connection connection = createConnection(connectionString);
				final PreparedStatement statement = connection.prepareStatement(query);
				final ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				final Object rawResult = resultSet.getObject("distinct_date");
				System.out.println(rawResult.getClass());
				System.out.println(rawResult);
			}
		}
	}

	private static Connection createConnection(final String connectionString) throws SQLException {
		final Properties jdbcProperties = new Properties();
		jdbcProperties.setProperty("PWD", AUTH_TOKEN);

		return DriverManager.getConnection(connectionString, jdbcProperties);
	}
}