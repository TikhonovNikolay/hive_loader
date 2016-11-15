Running TEXT loader with Ignite
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
9. Change in "ignite-base-config.xml" path in hdfs to source data for all table on correct values. For example:

    <util:list id="externalSource" value-type="com.gridgain.ImportDataSource">
        <bean class="com.gridgain.ImportDataSource">
            <!-- Need to change on correct value -->
            <property name="path" value="hdfs://localhost:9000/data/cpr_user_info_vw"/>
            <property name="clazz" value="com.gridgain.cpr_user_info_vw"/>
        </bean>

        ...

    </util:list>

10. Run loader passing require arguments to it. E.g.:

./run-direct.sh -Dignite.config_path=/opt/1964/gridgain-professional-fabric-1.6.5/config/ignite-multicast-config.xml