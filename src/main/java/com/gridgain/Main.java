/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gridgain;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

/**
 *
 */
public class Main {
    /**
     * @param args Arguments.
     */
    public static void main(String[] args) {
        Ignite ignite = Ignition.start("ignite-localhost-config.xml");
    }

    /**
     * @param s DML structure.
     */
    private static void codeGenerator(String s) {
        int order = 0;

        for (String s0 : s.split("\n")) {
            String[] split = s0.split("\t");

            if (split.length != 3)
                throw new IllegalArgumentException(s0);

            System.out.println("    @Order(" + order + ")");
            System.out.println("    @QuerySqlField");
            System.out.println("    private " + matchType(split[1]) + " " + split[0] + ";");
            System.out.println();

            ++order;
        }
    }

    /**
     * @param t Type
     * @return Map CSV type on Java Type.
     */
    private static String matchType(String t) {
        switch (t) {
            case "string" :
                return "String";

            case "bigint" :
                return "Long";

            case "int" :
                return "Integer";

            case "timestamp" :
            case "date" :
                return "Date";

            case "double" :
                return "Double";

            case "map<string,string>" :
                return "Map<String, String>";
        }

        throw new IllegalArgumentException(t);
    }
}
