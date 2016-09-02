Running ORC loader with Hadoop
----------------------------------------
1. Build the project with Maven: "mvn clean package".

2. Specify HADOOP_HOME, HIVE_HOME, IGNITE_HOME in script "ignite-hive-loader.sh" (Hadoop cluster assumed to be
configured, up and running).

3. Ensure that loader properties are set correctly:
-Dignite.orc.input - folder in HDFS with Hive table;
-Dignite.orc.output - output folder; not really needed for load itself , but required by Hadoop;
-Dignite.orc.config_path - path to Ignite XML configuration;
-Dignite.orc.cache_name - cache name;
-Dignite.orc.clear_cache - whether to clear cache before starting load; used for debugging;
-Dignite.orc.buffer_size=1024 - load buffer size (how many entries are loaded at once);
-Dignite.orc.parallel_ops=16 - parallel operations per node;
-Dignite.orc.mode - load mode (use STREAMER for normal load, or SKIP to measure time of Hadoop
    infrastructure + ORC parsing);
-Dignite.orc.ignite.orc.filter.current_day - set to "true" to load data only for the current day.

4. Run loader:
   ./ignite-hive-loader.sh

Running ORC loader with Ignite
----------------------------------------
This mode require you to have an Ignite client started on each HDFS data node.

1. Prepare separate Ignite/GridGain distribution for client node. You may simply copy your current server distribution
to a nearby directory:

cp -r gridgain-fabric-professional-1.6.5 ./gridgain-fabric-professional-1.6.5-cli

2. Set "clientMode" flag in the "config/default-config.xml" file located in client distribution:

<bean id="grid.cfg" class="org.apache.ignite.configuration.IgniteConfiguration">
    <property name="clientMode" value="true" />
    ...
</bean>

3. Build the project with Maven.

mvn clean package

Once build is finished, you will see target JAR "target/ignite-hive-loader-1.0.0-SNAPSHOT.jar" and a lot of
dependent JAR files inside "target/dependency"

4. Prepare client classpath. This process consists of two steps.
4.1. Copy or symlink "target/ignite-hive-loader-1.0.0-SNAPSHOT.jar" to the "libs" folder of your client distribution.
This way client will have current projects JARs in the classpath.
4.2. Copy or symlink all JARS from "target/dependency" to the "libs" folder of your client distribution. You may
symlink entire directory as well. This way client will have all required Hive JARs in classpath.

5. Repeat steps 1-4 for all machines machines with HDFS data nodes. Alternatively you may prepare a single client
distribution, and the deliver it to all HDFS machines.

6. Start Ignite server nodes.
7. Start an Ignite client node on every HDFS machine. In the end you should see a topology with several server and
   several client nodes.

8. Review "direct-hive-loader.sh" file and set correct HADOOP_HOME and IGNITE_HOME directories there.
9. Run loader passing require arguments to it. E.g.:

./direct.sh -Dignite.orc.input=hdfs://testagent06:9000/user/hive/warehouse/cha_min_orc_10g -Dignite.orc.config_path=/opt/1964/gridgain-professional-fabric-1.6.5/config/default-config.xml -Dignite.orc.cache_name=mycache --Dignite.orc.ignite.orc.filter.current_day=true