# databricks_cluster_warehouse_difference

## Environment

- Tested with Java 11: `mvn --version`:

```
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /home/nec/.sdkman/candidates/maven/current
Java version: 11.0.17, vendor: Eclipse Adoptium, runtime: /home/nec/.sdkman/candidates/java/11.0.17-tem
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.2.0-32-generic", arch: "amd64", family: "unix"
```

- Tested also with Java 17: `mvn --version`:
- 
```
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /home/nec/.sdkman/candidates/maven/current
Java version: 17.0.8.1, vendor: Eclipse Adoptium, runtime: /home/nec/.sdkman/candidates/java/17.0.8.1-tem
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.2.0-32-generic", arch: "amd64", family: "unix"
```

- Databricks cluster version tested: 12.2 LTS and 13.3 LTS

## Steps to reproduce

- Create the table in your Databricks environment as done in the file `create_table.sql`
- Define your environment variables:
  - `DATABRICKS_SERVER_HOSTNAME`
  - `DATABRICKS_HTTP_PATH_CLUSTER`
  - `DATABRICKS_HTTP_PATH_WAREHOUSE`
  - `DATABRICKS_AUTH_TOKEN`
- Select your java version and run `mvn --version` to be sure 
- Build the project with `mvn clean install` 
- Run `mvn exec:java -Dexec.mainClass="com.activeviam.databricks.clustervswarehouse.Main"`

Observe the difference in the output: `["2021-07-21","2021-07-20","2021-07-19"]` on cluster vs `[2021-07-20,2021-07-19,2021-07-21]` on warehouse.
On Databricks warehouse, the lack of quotation marks does not allow proper object parsing.
