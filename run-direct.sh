HIVE_HOME=/opt/1964/apache-hive-1.2.1-bin
IGNITE_HOME=/opt/1964/gridgain-professional-fabric-1.6.5
HADOOP_HOME=/opt/4ibm/hadoop-2.7.1

#-----------------------------------------------
# Unpacked Apache Hive binary distribution: 
HIVE_HOME=${HIVE_HOME:-.../apache-hive-1.2.1-bin}

# Unpacked Apache Hadoop binary distribution:
HADOOP_HOME=${HADOOP_HOME:-.../hadoop-2.7.1}

# Unpacked Ignite Fabric binary distribution:
IGNITE_HOME=${IGNITE_HOME:-.../apache-ignite-fabric-1.7.0-SNAPSHOT-bin}


# clean up output folder:
#${HADOOP_HOME}/bin/hadoop fs -rm -r /tmp/ignite-hive-loader-out || true
# Compose comma-separated list of libraries needed in the runtime: 
#CP=${IGNITE_HOME}/libs/*:${IGNITE_HOME}/libs/ignite-spring/*:${IGNITE_HOME}/libs/optional/ignite-log4j/*:${HIVE_HOME}/lib/*
CP="/mnt/nfsshare01/share/lib-share/*:${HADOOP_HOME}/share/hadoop/hdfs/*"

java -cp target/ignite-hive-loader-1.0.0-SNAPSHOT.jar:${CP} "${@}" com.gridgain.direct.DirectLoaderRunner
