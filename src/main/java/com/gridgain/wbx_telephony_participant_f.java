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
public class wbx_telephony_participant_f {
    @Order(0)
    @QuerySqlField
    private Long session_id;

    @Order(1)
    @QuerySqlField
    private String session_key;

    @Order(2)
    @QuerySqlField
    private String brm_subscription_id;

    @Order(3)
    @QuerySqlField
    private String xaas_subscription_id;

    @Order(4)
    @QuerySqlField
    private String blis_subscription_id;

    @Order(5)
    @QuerySqlField
    private String site_service_id;

    @Order(6)
    @QuerySqlField
    private Long site_id;

    @Order(7)
    @QuerySqlField
    private String site_url;

    @Order(8)
    @QuerySqlField
    private Long eng_site_id;

    @Order(9)
    @QuerySqlField
    private Long host_id;

    @Order(10)
    @QuerySqlField
    private Long row_id;

    @Order(11)
    @QuerySqlField
    private String user_id;

    @Order(12)
    @QuerySqlField
    private Date call_start_dtm;

    @Order(13)
    @QuerySqlField
    private Date call_end_dtm;

    @Order(14)
    @QuerySqlField
    private Double call_duration_minutes;

    @Order(15)
    @QuerySqlField
    private String call_type;

    @Order(16)
    @QuerySqlField
    private String telephony_server;

    @Order(17)
    @QuerySqlField
    private String call_service_type;

    @Order(18)
    @QuerySqlField
    private String meeting_type;

    @Order(19)
    @QuerySqlField
    private String session_type;

    @Order(20)
    @QuerySqlField
    private String major_session_type;

    @Order(21)
    @QuerySqlField
    private Date telephony_creation_date;

    @Order(22)
    @QuerySqlField
    private Date consumption_date;

    @Order(23)
    @QuerySqlField
    private Long call_rate_id;

    @Order(24)
    @QuerySqlField
    private Double wbx_price;

    @Order(25)
    @QuerySqlField
    private Double rating_billing_status;

    @Order(26)
    @QuerySqlField
    private Date rating_billing_dtm;

    @Order(27)
    @QuerySqlField
    private Date meeting_start_time_dtm;

    @Order(28)
    @QuerySqlField
    private Date meeting_end_time_dtm;

    @Order(29)
    @QuerySqlField
    private String did_phone_number;

    @Order(30)
    @QuerySqlField
    private String provider;

    @Order(31)
    @QuerySqlField
    private String provider_country;

    @Order(32)
    @QuerySqlField
    private String user_email;

    @Order(33)
    @QuerySqlField
    private String user_name;

    @Order(34)
    @QuerySqlField
    private String webexid;

    @Order(35)
    @QuerySqlField
    private String did_country;

    @Order(36)
    @QuerySqlField
    private String bridge_country;

    @Order(37)
    @QuerySqlField
    private String country_code;

    @Order(38)
    @QuerySqlField
    private String toll_type;

    @Order(39)
    @QuerySqlField
    private String area_code;

    @Order(40)
    @QuerySqlField
    private String dnis;

    @Order(41)
    @QuerySqlField
    private String ani;

    @Order(42)
    @QuerySqlField
    private String billable_flag;

    @Order(43)
    @QuerySqlField
    private String usage_type;

    @Order(44)
    @QuerySqlField
    private Date edw_create_dtm;

    @Order(45)
    @QuerySqlField
    private String edw_create_user;

    @Order(46)
    @QuerySqlField
    private Date edw_update_dtm;

    @Order(47)
    @QuerySqlField
    private String edw_update_user;

    public wbx_telephony_participant_f() {
    }

    public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getBrm_subscription_id() {
        return brm_subscription_id;
    }

    public void setBrm_subscription_id(String brm_subscription_id) {
        this.brm_subscription_id = brm_subscription_id;
    }

    public String getXaas_subscription_id() {
        return xaas_subscription_id;
    }

    public void setXaas_subscription_id(String xaas_subscription_id) {
        this.xaas_subscription_id = xaas_subscription_id;
    }

    public String getBlis_subscription_id() {
        return blis_subscription_id;
    }

    public void setBlis_subscription_id(String blis_subscription_id) {
        this.blis_subscription_id = blis_subscription_id;
    }

    public String getSite_service_id() {
        return site_service_id;
    }

    public void setSite_service_id(String site_service_id) {
        this.site_service_id = site_service_id;
    }

    public Long getSite_id() {
        return site_id;
    }

    public void setSite_id(Long site_id) {
        this.site_id = site_id;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public Long getEng_site_id() {
        return eng_site_id;
    }

    public void setEng_site_id(Long eng_site_id) {
        this.eng_site_id = eng_site_id;
    }

    public Long getHost_id() {
        return host_id;
    }

    public void setHost_id(Long host_id) {
        this.host_id = host_id;
    }

    public Long getRow_id() {
        return row_id;
    }

    public void setRow_id(Long row_id) {
        this.row_id = row_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getCall_start_dtm() {
        return call_start_dtm;
    }

    public void setCall_start_dtm(Date call_start_dtm) {
        this.call_start_dtm = call_start_dtm;
    }

    public Date getCall_end_dtm() {
        return call_end_dtm;
    }

    public void setCall_end_dtm(Date call_end_dtm) {
        this.call_end_dtm = call_end_dtm;
    }

    public Double getCall_duration_minutes() {
        return call_duration_minutes;
    }

    public void setCall_duration_minutes(Double call_duration_minutes) {
        this.call_duration_minutes = call_duration_minutes;
    }

    public String getCall_type() {
        return call_type;
    }

    public void setCall_type(String call_type) {
        this.call_type = call_type;
    }

    public String getTelephony_server() {
        return telephony_server;
    }

    public void setTelephony_server(String telephony_server) {
        this.telephony_server = telephony_server;
    }

    public String getCall_service_type() {
        return call_service_type;
    }

    public void setCall_service_type(String call_service_type) {
        this.call_service_type = call_service_type;
    }

    public String getMeeting_type() {
        return meeting_type;
    }

    public void setMeeting_type(String meeting_type) {
        this.meeting_type = meeting_type;
    }

    public String getSession_type() {
        return session_type;
    }

    public void setSession_type(String session_type) {
        this.session_type = session_type;
    }

    public String getMajor_session_type() {
        return major_session_type;
    }

    public void setMajor_session_type(String major_session_type) {
        this.major_session_type = major_session_type;
    }

    public Date getTelephony_creation_date() {
        return telephony_creation_date;
    }

    public void setTelephony_creation_date(Date telephony_creation_date) {
        this.telephony_creation_date = telephony_creation_date;
    }

    public Date getConsumption_date() {
        return consumption_date;
    }

    public void setConsumption_date(Date consumption_date) {
        this.consumption_date = consumption_date;
    }

    public Long getCall_rate_id() {
        return call_rate_id;
    }

    public void setCall_rate_id(Long call_rate_id) {
        this.call_rate_id = call_rate_id;
    }

    public Double getWbx_price() {
        return wbx_price;
    }

    public void setWbx_price(Double wbx_price) {
        this.wbx_price = wbx_price;
    }

    public Double getRating_billing_status() {
        return rating_billing_status;
    }

    public void setRating_billing_status(Double rating_billing_status) {
        this.rating_billing_status = rating_billing_status;
    }

    public Date getRating_billing_dtm() {
        return rating_billing_dtm;
    }

    public void setRating_billing_dtm(Date rating_billing_dtm) {
        this.rating_billing_dtm = rating_billing_dtm;
    }

    public Date getMeeting_start_time_dtm() {
        return meeting_start_time_dtm;
    }

    public void setMeeting_start_time_dtm(Date meeting_start_time_dtm) {
        this.meeting_start_time_dtm = meeting_start_time_dtm;
    }

    public Date getMeeting_end_time_dtm() {
        return meeting_end_time_dtm;
    }

    public void setMeeting_end_time_dtm(Date meeting_end_time_dtm) {
        this.meeting_end_time_dtm = meeting_end_time_dtm;
    }

    public String getDid_phone_number() {
        return did_phone_number;
    }

    public void setDid_phone_number(String did_phone_number) {
        this.did_phone_number = did_phone_number;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider_country() {
        return provider_country;
    }

    public void setProvider_country(String provider_country) {
        this.provider_country = provider_country;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWebexid() {
        return webexid;
    }

    public void setWebexid(String webexid) {
        this.webexid = webexid;
    }

    public String getDid_country() {
        return did_country;
    }

    public void setDid_country(String did_country) {
        this.did_country = did_country;
    }

    public String getBridge_country() {
        return bridge_country;
    }

    public void setBridge_country(String bridge_country) {
        this.bridge_country = bridge_country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getToll_type() {
        return toll_type;
    }

    public void setToll_type(String toll_type) {
        this.toll_type = toll_type;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getBillable_flag() {
        return billable_flag;
    }

    public void setBillable_flag(String billable_flag) {
        this.billable_flag = billable_flag;
    }

    public String getUsage_type() {
        return usage_type;
    }

    public void setUsage_type(String usage_type) {
        this.usage_type = usage_type;
    }

    public Date getEdw_create_dtm() {
        return edw_create_dtm;
    }

    public void setEdw_create_dtm(Date edw_create_dtm) {
        this.edw_create_dtm = edw_create_dtm;
    }

    public String getEdw_create_user() {
        return edw_create_user;
    }

    public void setEdw_create_user(String edw_create_user) {
        this.edw_create_user = edw_create_user;
    }

    public Date getEdw_update_dtm() {
        return edw_update_dtm;
    }

    public void setEdw_update_dtm(Date edw_update_dtm) {
        this.edw_update_dtm = edw_update_dtm;
    }

    public String getEdw_update_user() {
        return edw_update_user;
    }

    public void setEdw_update_user(String edw_update_user) {
        this.edw_update_user = edw_update_user;
    }
}
