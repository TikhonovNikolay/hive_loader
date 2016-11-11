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

import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.springframework.core.annotation.Order;

/**
 *
 */
public class dw_ua_url {
    @Order(0)
    @QuerySqlField
    private String area_level1;

    @Order(1)
    @QuerySqlField
    private String area_level2;

    @Order(2)
    @QuerySqlField
    private String area_level3;

    @Order(3)
    @QuerySqlField
    private String area_level4;

    @Order(4)
    @QuerySqlField
    private String area_level5;

    @Order(5)
    @QuerySqlField
    private String area_level6;

    @Order(6)
    @QuerySqlField
    private String url_desc;

    @Order(7)
    @QuerySqlField
    private String url;

    @Order(8)
    @QuerySqlField
    private String update_date;

    @Order(9)
    @QuerySqlField
    private String create_date;

    public dw_ua_url() {
    }

    public String getArea_level1() {
        return area_level1;
    }

    public void setArea_level1(String area_level1) {
        this.area_level1 = area_level1;
    }

    public String getArea_level2() {
        return area_level2;
    }

    public void setArea_level2(String area_level2) {
        this.area_level2 = area_level2;
    }

    public String getArea_level3() {
        return area_level3;
    }

    public void setArea_level3(String area_level3) {
        this.area_level3 = area_level3;
    }

    public String getArea_level4() {
        return area_level4;
    }

    public void setArea_level4(String area_level4) {
        this.area_level4 = area_level4;
    }

    public String getArea_level5() {
        return area_level5;
    }

    public void setArea_level5(String area_level5) {
        this.area_level5 = area_level5;
    }

    public String getArea_level6() {
        return area_level6;
    }

    public void setArea_level6(String area_level6) {
        this.area_level6 = area_level6;
    }

    public String getUrl_desc() {
        return url_desc;
    }

    public void setUrl_desc(String url_desc) {
        this.url_desc = url_desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
