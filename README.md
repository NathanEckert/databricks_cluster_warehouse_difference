# databricks_cluster_warehouse_difference

## Environment

Maven, `mvn --version`:

```
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /home/nec/.sdkman/candidates/maven/current
Java version: 11.0.17, vendor: Eclipse Adoptium, runtime: /home/nec/.sdkman/candidates/java/11.0.17-tem
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.2.0-32-generic", arch: "amd64", family: "unix"
```

## Steps to reproduce

- Create the table in your Databricks environment as done in the file `create_table.sql`
- Define your environment variables:
  - `DATABRICKS_SERVER_HOSTNAME`
  - `DATABRICKS_HTTP_PATH_CLUSTER`
  - `DATABRICKS_HTTP_PATH_WAREHOUSE`
  - `DATABRICKS_AUTH_TOKEN`
- Run `mvn exec:java -Dexec.mainClass="com.activeviam.databricks.clustervswarehouse.Main"`

Observe the difference in the output: `["2021-07-21","2021-07-20","2021-07-19"]` on cluster vs `[2021-07-20,2021-07-19,2021-07-21]` on warehouse.
On Databricks warehouse, the lack of quotation marks does not allow proper object parsing.
