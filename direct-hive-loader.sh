IGNITE_HOME=/opt/1964/gridgain-professional-fabric-1.6.5
HADOOP_HOME=/opt/4ibm/hadoop-2.7.1

CP=${IGNITE_HOME}/libs/*:${IGNITE_HOME}/libs/ignite-spring/*:${IGNITE_HOME}/libs/optional/ignite-log4j/*:target/dependency/*:${HADOOP_HOME}/share/hadoop/hdfs/*

java -cp target/ignite-hive-loader-1.0.0-SNAPSHOT.jar:${CP} "${@}" com.atconsulting.ignitehiveloader.direct.DirectOrcLoaderRunner
