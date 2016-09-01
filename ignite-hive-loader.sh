#!/bin/bash -e

HIVE_HOME=/opt/1964/apache-hive-1.2.1-bin
IGNITE_HOME=/opt/1964/gridgain-professional-fabric-1.6.5
HADOOP_HOME=/opt/4ibm/hadoop-2.7.1

# clean up output folder:
${HADOOP_HOME}/bin/hadoop fs -rm -r /tmp/ignite-hive-loader-out || true

LIBJARS="" 

# Compose comma-separated list of libraries needed in the runtime: 
for f in \
${IGNITE_HOME}/libs/*.jar \
${IGNITE_HOME}/libs/ignite-spring/*.jar \
${HIVE_HOME}/lib/*.jar 
do
    if [ -z "${LIBJARS}" ]; then
       LIBJARS="${f}" 
    else 
       LIBJARS="${LIBJARS},${f}"
    fi
done

export HADOOP_CLASSPATH=`echo "${LIBJARS}" | tr ',' ':'`

${HADOOP_HOME}/bin/yarn jar ./target/ignite-hive-loader-1.0.0-SNAPSHOT.jar com.atconsulting.ignitehiveloader.OrcLoader \
-libjars "${LIBJARS}" \
-Dignite.orc.input=hdfs://testagent06:9000/user/hive/warehouse/cha_min_orc_1g/ \
-Dignite.orc.output=/tmp/ignite-hive-loader-out \
-Dignite.orc.config_path=/mnt/nfsshare01/share/default-config.xml \
-Dignite.orc.cache_name=mycache \
-Dignite.orc.clear_cache=false \
-Dignite.orc.buffer_size=1024 \
-Dignite.orc.parallel_ops=16 \
-Dignite.orc.mode=STREAMER \
-Dignite.orc.ignite.orc.filter.current_day=false \
 "${@}"

