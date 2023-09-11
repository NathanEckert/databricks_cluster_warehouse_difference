# databricks_cluster_warehouse_difference

Steps to reproduce:

- Create the table in your Databricks environment as done in the file `create_table.sql`
- Define your environment variables:
  - `DATABRICKS_SERVER_HOSTNAME`
  - `DATABRICKS_HTTP_PATH_CLUSTER`
  - `DATABRICKS_HTTP_PATH_WAREHOUSE`
  - `DATABRICKS_AUTH_TOKEN`
- Run `mvn exec:java -Dexec.mainClass="com.activeviam.databricks.clustervswarehouse.Main"`

Observe the difference in the output: `["2021-07-21","2021-07-20","2021-07-19"]` on cluster vs `[2021-07-20,2021-07-19,2021-07-21]` on warehouse.
