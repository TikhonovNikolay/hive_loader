1. Build this project with "mvn clean package". 

2. Specify HADOOP_HOME, HIVE_HOME, IGNITE_HOME in script "hive-ignite-load.sh". (Hadoop cluster assumed to be configured, up and running).

3. Run Ignite Fabric server node(s). 

4. Run import, e.g. 
   ./ignite-hive-loader.sh /opt/apache-ignite-fabric-1.7.0-SNAPSHOT-bin/config/default-config.xml my-cache hdfs://localhost:9000/user/hive/warehouse/cha_min_orc

