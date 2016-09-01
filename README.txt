1. Build this project with "mvn clean package". 

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

