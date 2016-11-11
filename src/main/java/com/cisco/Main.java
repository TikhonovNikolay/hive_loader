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

package com.cisco;

/**
 *
 */
public class Main {
    /**
     * @param args Arguments.
     */
    public static void main(String[] args) {
        codeGenerator("confid\tdouble\t0\n" +
            "confkey\tstring\t1\n" +
            "confname\tstring\t2\n" +
            "conftype\tdouble\t3\n" +
            "usertype\tstring\t4\n" +
            "starttime\tstring\t5\n" +
            "endtime\tstring\t6\n" +
            "duration\tdouble\t7\n" +
            "uid_\tdouble\t8\n" +
            "gid\tdouble\t9\n" +
            "webexid\tstring\t10\n" +
            "username\tstring\t11\n" +
            "useremail\tstring\t12\n" +
            "ipaddress\tstring\t13\n" +
            "siteid\tdouble\t14\n" +
            "sitename\tstring\t15\n" +
            "dialin1\tdouble\t16\n" +
            "dialin2\tdouble\t17\n" +
            "dialout1\tdouble\t18\n" +
            "dialout2\tdouble\t19\n" +
            "voip\tdouble\t20\n" +
            "hgssiteid\tdouble\t21\n" +
            "meetingtype\tstring\t22\n" +
            "timezone\tdouble\t23\n" +
            "objid\tdouble\t24\n" +
            "clientagent\tstring\t25\n" +
            "mtg_starttime\tstring\t26\n" +
            "mtg_endtime\tstring\t27\n" +
            "mtg_timezone\tdouble\t28\n" +
            "registered\tstring\t29\n" +
            "invited\tstring\t30\n" +
            "reg_company\tstring\t31\n" +
            "reg_title\tstring\t32\n" +
            "reg_phone\tstring\t33\n" +
            "reg_address1\tstring\t34\n" +
            "reg_address2\tstring\t35\n" +
            "reg_city\tstring\t36\n" +
            "reg_state\tstring\t37\n" +
            "reg_country\tstring\t38\n" +
            "reg_zip\tstring\t39\n" +
            "non_billable\tdouble\t40\n" +
            "attendantid\tdouble\t41\n" +
            "att_firstname\tstring\t42\n" +
            "att_lastname\tstring\t43\n" +
            "service_identifier\tstring\t44\n" +
            "user_identifier\tstring\t45\n" +
            "purchase_identifier\tstring\t46\n" +
            "consumption_date\tstring\t47\n" +
            "subscription_code\tstring\t48\n" +
            "boss_contractid\tdouble\t49\n" +
            "rbe_status\tdouble\t50\n" +
            "rbe_timestamp\tstring\t51\n" +
            "isinternal\tdouble\t52\n" +
            "prosumer\tdouble\t53\n" +
            "inattentiveminutes\tdouble\t54\n" +
            "meetinglanguageid\tdouble\t55\n" +
            "regionid\tdouble\t56\n" +
            "webexid_translated\tstring\t57\n" +
            "useremail_translated\tstring\t58\n" +
            "reg_address1_translated\tstring\t59\n" +
            "reg_address2_translated\tstring\t60\n" +
            "reg_city_translated\tstring\t61\n" +
            "reg_company_translated\tstring\t62\n" +
            "reg_country_translated\tstring\t63\n" +
            "att_firstname_translated\tstring\t64\n" +
            "att_lastname_translated\tstring\t65\n" +
            "reg_state_translated\tstring\t66\n" +
            "reg_title_translated\tstring\t67\n" +
            "username_translated\tstring\t68\n" +
            "ipaddress_translated\tstring\t69\n" +
            "sitename_translated\tstring\t70\n" +
            "clientagent_translated\tstring\t71\n" +
            "createdate\tstring\t72\n" +
            "confname_translated\tstring\t73\n" +
            "private_ipaddress\tstring\t74\n" +
            "hostid\tdouble\t75\n" +
            "hostlanguageid\tdouble\t76\n" +
            "accountid\tstring\t77\n" +
            "servicecode\tstring\t78\n" +
            "subscriptioncode\tstring\t79\n" +
            "billingaccountid\tstring\t80\n" +
            "meetingnodeflag\tstring\t81\n" +
            "attendeetag\tdouble\t82\n");
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
