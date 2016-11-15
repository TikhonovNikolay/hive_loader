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

import java.util.Date;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.springframework.core.annotation.Order;

/**
 *
 */
public class unqvstr_rvrs_ip_rep {
    @Order(0)
    @QuerySqlField
    private String unique_visitor;

    @Order(1)
    @QuerySqlField
    private String ip;

    @Order(2)
    @QuerySqlField
    private String country_code;

    @Order(3)
    @QuerySqlField
    private String domain;

    @Order(4)
    @QuerySqlField
    private String dtype;

    @Order(5)
    @QuerySqlField
    private String duns;

    @Order(6)
    @QuerySqlField
    private String name;

    @Order(7)
    @QuerySqlField
    private String city;

    @Order(8)
    @QuerySqlField
    private String state;

    @Order(9)
    @QuerySqlField
    private String region;

    @Order(10)
    @QuerySqlField
    private String timezone;

    @Order(11)
    @QuerySqlField
    private String unique_email_user_count;

    @Order(12)
    @QuerySqlField
    private Long timestamp;

    @Order(13)
    @QuerySqlField
    private Date viewdate;

    public unqvstr_rvrs_ip_rep() {
    }

    public String getUnique_visitor() {
        return unique_visitor;
    }

    public void setUnique_visitor(String unique_visitor) {
        this.unique_visitor = unique_visitor;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getDuns() {
        return duns;
    }

    public void setDuns(String duns) {
        this.duns = duns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getUnique_email_user_count() {
        return unique_email_user_count;
    }

    public void setUnique_email_user_count(String unique_email_user_count) {
        this.unique_email_user_count = unique_email_user_count;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getViewdate() {
        return viewdate;
    }

    public void setViewdate(Date viewdate) {
        this.viewdate = viewdate;
    }
}
