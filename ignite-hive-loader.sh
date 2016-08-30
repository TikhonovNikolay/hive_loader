#!/bin/bash -e

ignite_config=${1}
shift || true

cache_name=${1}
shift || true

if [[ ${#} -lt 1 || -z "${cache_name}" || -z "${ignite_config}" ]]; then
   echo "Parameters expected: <ignite config> <cache name> <directory to import> [<more directories to import>]"
   exit 2
fi


# Unpacked Apache Hive binary distribution: 
HIVE_HOME=.../apache-hive-1.2.1-bin

# Unpacked Apache Hadoop binary distribution:
HADOOP_HOME=.../hadoop-2.7.1

# Unpacked Ignite Fabric binary distribution:
IGNITE_HOME=.../apache-ignite-fabric-1.7.0-SNAPSHOT-bin


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

${HADOOP_HOME}/bin/yarn jar target/ignite-hive-loader-1.0.0-SNAPSHOT.jar com.atconsulting.ignitehiveloader.OrcLoader \
-libjars "${LIBJARS}" \
-Dignite-client-config-path=${ignite_config} \
-Dignite-cache-name=${cache_name} \
 "${@}" /tmp/ignite-hive-loader-out

