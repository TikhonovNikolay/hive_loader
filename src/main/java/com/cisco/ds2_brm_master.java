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

import java.util.Date;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.springframework.core.annotation.Order;

/**
 *
 */
public class ds2_brm_master {
    @Order(0)
    @QuerySqlField
    private Long account_id;

    @Order(1)
    @QuerySqlField
    private String account_number;

    @Order(2)
    @QuerySqlField
    private String account_name;

    @Order(3)
    @QuerySqlField
    private String account_status;

    @Order(4)
    @QuerySqlField
    private String tenant_name;

    @Order(5)
    @QuerySqlField
    private String organization_name;

    @Order(6)
    @QuerySqlField
    private String contact_first_name;

    @Order(7)
    @QuerySqlField
    private String contact_last_name;

    @Order(8)
    @QuerySqlField
    private String currency;

    @Order(9)
    @QuerySqlField
    private Long bill_day;

    @Order(10)
    @QuerySqlField
    private String bill_to_address1;

    @Order(11)
    @QuerySqlField
    private String bill_to_city;

    @Order(12)
    @QuerySqlField
    private String bill_to_state_province;

    @Order(13)
    @QuerySqlField
    private String bill_to_postal_code;

    @Order(14)
    @QuerySqlField
    private String bill_to_country;

    @Order(15)
    @QuerySqlField
    private Long bill_to_phone;

    @Order(16)
    @QuerySqlField
    private String bill_to_email;

    @Order(17)
    @QuerySqlField
    private String ship_to_address1;

    @Order(18)
    @QuerySqlField
    private String ship_to_city;

    @Order(19)
    @QuerySqlField
    private String ship_to_state_province;

    @Order(20)
    @QuerySqlField
    private String ship_to_postal_code;

    @Order(21)
    @QuerySqlField
    private String ship_to_country;

    @Order(22)
    @QuerySqlField
    private Long ship_to_phone;

    @Order(23)
    @QuerySqlField
    private String ship_to_email;

    @Order(24)
    @QuerySqlField
    private Date created_date;

    @Order(25)
    @QuerySqlField
    private Date created_dtm;

    @Order(26)
    @QuerySqlField
    private String payment_method;

    @Order(27)
    @QuerySqlField
    private String languages;

    @Order(28)
    @QuerySqlField
    private String business_type;

    @Order(29)
    @QuerySqlField
    private String country;

    @Order(30)
    @QuerySqlField
    private String state_province;

    @Order(31)
    @QuerySqlField
    private Long subscription_id;

    @Order(32)
    @QuerySqlField
    private Long previous_subscription_id;

    @Order(33)
    @QuerySqlField
    private Date subscription_effective_from_dt;

    @Order(34)
    @QuerySqlField
    private Date subscription_effective_to_dt;

    @Order(35)
    @QuerySqlField
    private String subscription_status;

    @Order(36)
    @QuerySqlField
    private Long initial_term;

    @Order(37)
    @QuerySqlField
    private Long renewal_term;

    @Order(38)
    @QuerySqlField
    private Date service_renewal_date;

    @Order(39)
    @QuerySqlField
    private Integer prepayment_term;

    @Order(40)
    @QuerySqlField
    private String offer_code;

    @Order(41)
    @QuerySqlField
    private String finance_reason_code;

    @Order(42)
    @QuerySqlField
    private String finance_sub_status;

    @Order(43)
    @QuerySqlField
    private Long order_id;

    @Order(44)
    @QuerySqlField
    private Long order_number;

    @Order(45)
    @QuerySqlField
    private String order_type;

    @Order(46)
    @QuerySqlField
    private String order_sub_type;

    @Order(47)
    @QuerySqlField
    private String order_sell_type;

    @Order(48)
    @QuerySqlField
    private String order_status;

    @Order(49)
    @QuerySqlField
    private String order_finance_approved_flg;

    @Order(50)
    @QuerySqlField
    private String order_opportunity_id;

    @Order(51)
    @QuerySqlField
    private Date order_created_date;

    @Order(52)
    @QuerySqlField
    private Date order_created_dtm;

    @Order(53)
    @QuerySqlField
    private Date order_effective_to;

    @Order(54)
    @QuerySqlField
    private Date order_effective_to_dtm;

    @Order(55)
    @QuerySqlField
    private Date order_completed_date;

    @Order(56)
    @QuerySqlField
    private Date order_completed_dtm;

    @Order(57)
    @QuerySqlField
    private Date order_accepted_date;

    @Order(58)
    @QuerySqlField
    private Date order_accepted_dtm;

    @Order(59)
    @QuerySqlField
    private Date order_booked_date;

    @Order(60)
    @QuerySqlField
    private Date order_booked_dtm;

    @Order(61)
    @QuerySqlField
    private Date order_compensation_date;

    @Order(62)
    @QuerySqlField
    private Date order_compensation_dtm;

    @Order(63)
    @QuerySqlField
    private String order_created_by;

    @Order(64)
    @QuerySqlField
    private String order_modified_by;

    @Order(65)
    @QuerySqlField
    private String user_type;

    @Order(66)
    @QuerySqlField
    private Date order_service_start_date;

    @Order(67)
    @QuerySqlField
    private Date order_service_end_date;

    @Order(68)
    @QuerySqlField
    private Date order_start_date;

    @Order(69)
    @QuerySqlField
    private Date order_start_dtm;

    @Order(70)
    @QuerySqlField
    private Date order_end_date;

    @Order(71)
    @QuerySqlField
    private Date order_end_dtm;

    @Order(72)
    @QuerySqlField
    private Date term_start_date;

    @Order(73)
    @QuerySqlField
    private Date term_end_date;

    @Order(74)
    @QuerySqlField
    private String sku_id;

    @Order(75)
    @QuerySqlField
    private String sku_label;

    @Order(76)
    @QuerySqlField
    private String sku_type;

    @Order(77)
    @QuerySqlField
    private String charge_type;

    @Order(78)
    @QuerySqlField
    private String promo_code;

    @Order(79)
    @QuerySqlField
    private Double sku_mrr;

    @Order(80)
    @QuerySqlField
    private Double sku_mrr_usd;

    @Order(81)
    @QuerySqlField
    private Double selling_unit_price;

    @Order(82)
    @QuerySqlField
    private Long qty;

    @Order(83)
    @QuerySqlField
    private String creditor;

    @Order(84)
    @QuerySqlField
    private String recurring_ind;

    @Order(85)
    @QuerySqlField
    private String service_type;

    @Order(86)
    @QuerySqlField
    private String rating_model;

    @Order(87)
    @QuerySqlField
    private String product;

    @Order(88)
    @QuerySqlField
    private String deployment_model;

    @Order(89)
    @QuerySqlField
    private String core_offers;

    @Order(90)
    @QuerySqlField
    private String initial_offer_code;

    @Order(91)
    @QuerySqlField
    private String signup_type;

    @Order(92)
    @QuerySqlField
    private String fraud_flag;

    @Order(93)
    @QuerySqlField
    private Long order_track_id;

    @Order(94)
    @QuerySqlField
    private String acquisition_source;

    @Order(95)
    @QuerySqlField
    private String acquisition_source_rptg;

    @Order(96)
    @QuerySqlField
    private Integer rownumber;

    @Order(97)
    @QuerySqlField
    private String campaign_name;

    @Order(98)
    @QuerySqlField
    private String promo_description;

    @Order(99)
    @QuerySqlField
    private String delivery_channel;

    @Order(100)
    @QuerySqlField
    private Date promo_start_date;

    @Order(101)
    @QuerySqlField
    private Date promo_end_date;

    @Order(102)
    @QuerySqlField
    private String promo_discount_type;

    @Order(103)
    @QuerySqlField
    private String discount_pct_amt;

    @Order(104)
    @QuerySqlField
    private Long promo_term;

    @Order(105)
    @QuerySqlField
    private String country_code;

    @Order(106)
    @QuerySqlField
    private String country_name;

    @Order(107)
    @QuerySqlField
    private String geo_area;

    @Order(108)
    @QuerySqlField
    private String region;

    @Order(109)
    @QuerySqlField
    private Long order_id_prev;

    @Order(110)
    @QuerySqlField
    private Double order_mrr;

    @Order(111)
    @QuerySqlField
    private Double order_mrr_prev;

    @Order(112)
    @QuerySqlField
    private Double order_mrr_change;

    @Order(113)
    @QuerySqlField
    private String order_change_type;

    @Order(114)
    @QuerySqlField
    private String order_change_type_detail;

    @Order(115)
    @QuerySqlField
    private Double data_mrr;

    @Order(116)
    @QuerySqlField
    private Double data_mrr_prev;

    @Order(117)
    @QuerySqlField
    private Double data_mrr_change;

    @Order(118)
    @QuerySqlField
    private Double audio_mrr;

    @Order(119)
    @QuerySqlField
    private Double audio_mrr_prev;

    @Order(120)
    @QuerySqlField
    private Double audio_mrr_change;

    @Order(121)
    @QuerySqlField
    private Double storage_mrr;

    @Order(122)
    @QuerySqlField
    private Double storage_mrr_prev;

    @Order(123)
    @QuerySqlField
    private Double storage_mrr_change;

    @Order(124)
    @QuerySqlField
    private String data_change_type;

    @Order(125)
    @QuerySqlField
    private String audio_change_type;

    @Order(126)
    @QuerySqlField
    private String storage_change_type;

    @Order(127)
    @QuerySqlField
    private Integer order_filter;

    @Order(128)
    @QuerySqlField
    private String order_comment;

    @Order(129)
    @QuerySqlField
    private Long churn_reason_code;

    @Order(130)
    @QuerySqlField
    private String churn_reason_description;

    @Order(131)
    @QuerySqlField
    private String churn_comment;

    @Order(132)
    @QuerySqlField
    private String fiscal_qtr_subscr_effect_from;

    @Order(133)
    @QuerySqlField
    private String fiscal_mth_subscr_effect_from;

    @Order(134)
    @QuerySqlField
    private String fiscal_week_subscr_effect_from;

    @Order(135)
    @QuerySqlField
    private String fiscal_qtr_op_subscr_effect_from;

    @Order(136)
    @QuerySqlField
    private String fiscal_mth_op_subscr_effect_from;

    @Order(137)
    @QuerySqlField
    private String fiscal_qtr_subscr_effect_to;

    @Order(138)
    @QuerySqlField
    private String fiscal_mth_subscr_effect_to;

    @Order(139)
    @QuerySqlField
    private String fiscal_week_subscr_effect_to;

    @Order(140)
    @QuerySqlField
    private String fiscal_qtr_op_subscr_effect_to;

    @Order(141)
    @QuerySqlField
    private String fiscal_mth_op_subscr_effect_to;

    @Order(142)
    @QuerySqlField
    private Date edw_create_dtm;

    @Order(143)
    @QuerySqlField
    private String edw_create_user;

    @Order(144)
    @QuerySqlField
    private Date edw_update_dtm;

    @Order(145)
    @QuerySqlField
    private String edw_update_user;

    @Order(146)
    @QuerySqlField
    private Date order_service_start_dtm;

    @Order(147)
    @QuerySqlField
    private Date order_service_end_dtm;

    @Order(148)
    @QuerySqlField
    private Date subscription_effective_from_dtm;

    @Order(149)
    @QuerySqlField
    private Date subscription_effective_to_dtm;

    @Order(150)
    @QuerySqlField
    private String subscription_same_week_cancellation_flag;

    @Order(151)
    @QuerySqlField
    private String first_time_free_to_paid_flag;

    @Order(152)
    @QuerySqlField
    private String last_record_identifier;

    @Order(153)
    @QuerySqlField
    private Long churn_reason_code_combined;

    @Order(154)
    @QuerySqlField
    private String churn_reason_description_combined;

    public ds2_brm_master() {
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getContact_first_name() {
        return contact_first_name;
    }

    public void setContact_first_name(String contact_first_name) {
        this.contact_first_name = contact_first_name;
    }

    public String getContact_last_name() {
        return contact_last_name;
    }

    public void setContact_last_name(String contact_last_name) {
        this.contact_last_name = contact_last_name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getBill_day() {
        return bill_day;
    }

    public void setBill_day(Long bill_day) {
        this.bill_day = bill_day;
    }

    public String getBill_to_address1() {
        return bill_to_address1;
    }

    public void setBill_to_address1(String bill_to_address1) {
        this.bill_to_address1 = bill_to_address1;
    }

    public String getBill_to_city() {
        return bill_to_city;
    }

    public void setBill_to_city(String bill_to_city) {
        this.bill_to_city = bill_to_city;
    }

    public String getBill_to_state_province() {
        return bill_to_state_province;
    }

    public void setBill_to_state_province(String bill_to_state_province) {
        this.bill_to_state_province = bill_to_state_province;
    }

    public String getBill_to_postal_code() {
        return bill_to_postal_code;
    }

    public void setBill_to_postal_code(String bill_to_postal_code) {
        this.bill_to_postal_code = bill_to_postal_code;
    }

    public String getBill_to_country() {
        return bill_to_country;
    }

    public void setBill_to_country(String bill_to_country) {
        this.bill_to_country = bill_to_country;
    }

    public Long getBill_to_phone() {
        return bill_to_phone;
    }

    public void setBill_to_phone(Long bill_to_phone) {
        this.bill_to_phone = bill_to_phone;
    }

    public String getBill_to_email() {
        return bill_to_email;
    }

    public void setBill_to_email(String bill_to_email) {
        this.bill_to_email = bill_to_email;
    }

    public String getShip_to_address1() {
        return ship_to_address1;
    }

    public void setShip_to_address1(String ship_to_address1) {
        this.ship_to_address1 = ship_to_address1;
    }

    public String getShip_to_city() {
        return ship_to_city;
    }

    public void setShip_to_city(String ship_to_city) {
        this.ship_to_city = ship_to_city;
    }

    public String getShip_to_state_province() {
        return ship_to_state_province;
    }

    public void setShip_to_state_province(String ship_to_state_province) {
        this.ship_to_state_province = ship_to_state_province;
    }

    public String getShip_to_postal_code() {
        return ship_to_postal_code;
    }

    public void setShip_to_postal_code(String ship_to_postal_code) {
        this.ship_to_postal_code = ship_to_postal_code;
    }

    public String getShip_to_country() {
        return ship_to_country;
    }

    public void setShip_to_country(String ship_to_country) {
        this.ship_to_country = ship_to_country;
    }

    public Long getShip_to_phone() {
        return ship_to_phone;
    }

    public void setShip_to_phone(Long ship_to_phone) {
        this.ship_to_phone = ship_to_phone;
    }

    public String getShip_to_email() {
        return ship_to_email;
    }

    public void setShip_to_email(String ship_to_email) {
        this.ship_to_email = ship_to_email;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getCreated_dtm() {
        return created_dtm;
    }

    public void setCreated_dtm(Date created_dtm) {
        this.created_dtm = created_dtm;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public Long getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(Long subscription_id) {
        this.subscription_id = subscription_id;
    }

    public Long getPrevious_subscription_id() {
        return previous_subscription_id;
    }

    public void setPrevious_subscription_id(Long previous_subscription_id) {
        this.previous_subscription_id = previous_subscription_id;
    }

    public Date getSubscription_effective_from_dt() {
        return subscription_effective_from_dt;
    }

    public void setSubscription_effective_from_dt(Date subscription_effective_from_dt) {
        this.subscription_effective_from_dt = subscription_effective_from_dt;
    }

    public Date getSubscription_effective_to_dt() {
        return subscription_effective_to_dt;
    }

    public void setSubscription_effective_to_dt(Date subscription_effective_to_dt) {
        this.subscription_effective_to_dt = subscription_effective_to_dt;
    }

    public String getSubscription_status() {
        return subscription_status;
    }

    public void setSubscription_status(String subscription_status) {
        this.subscription_status = subscription_status;
    }

    public Long getInitial_term() {
        return initial_term;
    }

    public void setInitial_term(Long initial_term) {
        this.initial_term = initial_term;
    }

    public Long getRenewal_term() {
        return renewal_term;
    }

    public void setRenewal_term(Long renewal_term) {
        this.renewal_term = renewal_term;
    }

    public Date getService_renewal_date() {
        return service_renewal_date;
    }

    public void setService_renewal_date(Date service_renewal_date) {
        this.service_renewal_date = service_renewal_date;
    }

    public Integer getPrepayment_term() {
        return prepayment_term;
    }

    public void setPrepayment_term(Integer prepayment_term) {
        this.prepayment_term = prepayment_term;
    }

    public String getOffer_code() {
        return offer_code;
    }

    public void setOffer_code(String offer_code) {
        this.offer_code = offer_code;
    }

    public String getFinance_reason_code() {
        return finance_reason_code;
    }

    public void setFinance_reason_code(String finance_reason_code) {
        this.finance_reason_code = finance_reason_code;
    }

    public String getFinance_sub_status() {
        return finance_sub_status;
    }

    public void setFinance_sub_status(String finance_sub_status) {
        this.finance_sub_status = finance_sub_status;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Long order_number) {
        this.order_number = order_number;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_sub_type() {
        return order_sub_type;
    }

    public void setOrder_sub_type(String order_sub_type) {
        this.order_sub_type = order_sub_type;
    }

    public String getOrder_sell_type() {
        return order_sell_type;
    }

    public void setOrder_sell_type(String order_sell_type) {
        this.order_sell_type = order_sell_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_finance_approved_flg() {
        return order_finance_approved_flg;
    }

    public void setOrder_finance_approved_flg(String order_finance_approved_flg) {
        this.order_finance_approved_flg = order_finance_approved_flg;
    }

    public String getOrder_opportunity_id() {
        return order_opportunity_id;
    }

    public void setOrder_opportunity_id(String order_opportunity_id) {
        this.order_opportunity_id = order_opportunity_id;
    }

    public Date getOrder_created_date() {
        return order_created_date;
    }

    public void setOrder_created_date(Date order_created_date) {
        this.order_created_date = order_created_date;
    }

    public Date getOrder_created_dtm() {
        return order_created_dtm;
    }

    public void setOrder_created_dtm(Date order_created_dtm) {
        this.order_created_dtm = order_created_dtm;
    }

    public Date getOrder_effective_to() {
        return order_effective_to;
    }

    public void setOrder_effective_to(Date order_effective_to) {
        this.order_effective_to = order_effective_to;
    }

    public Date getOrder_effective_to_dtm() {
        return order_effective_to_dtm;
    }

    public void setOrder_effective_to_dtm(Date order_effective_to_dtm) {
        this.order_effective_to_dtm = order_effective_to_dtm;
    }

    public Date getOrder_completed_date() {
        return order_completed_date;
    }

    public void setOrder_completed_date(Date order_completed_date) {
        this.order_completed_date = order_completed_date;
    }

    public Date getOrder_completed_dtm() {
        return order_completed_dtm;
    }

    public void setOrder_completed_dtm(Date order_completed_dtm) {
        this.order_completed_dtm = order_completed_dtm;
    }

    public Date getOrder_accepted_date() {
        return order_accepted_date;
    }

    public void setOrder_accepted_date(Date order_accepted_date) {
        this.order_accepted_date = order_accepted_date;
    }

    public Date getOrder_accepted_dtm() {
        return order_accepted_dtm;
    }

    public void setOrder_accepted_dtm(Date order_accepted_dtm) {
        this.order_accepted_dtm = order_accepted_dtm;
    }

    public Date getOrder_booked_date() {
        return order_booked_date;
    }

    public void setOrder_booked_date(Date order_booked_date) {
        this.order_booked_date = order_booked_date;
    }

    public Date getOrder_booked_dtm() {
        return order_booked_dtm;
    }

    public void setOrder_booked_dtm(Date order_booked_dtm) {
        this.order_booked_dtm = order_booked_dtm;
    }

    public Date getOrder_compensation_date() {
        return order_compensation_date;
    }

    public void setOrder_compensation_date(Date order_compensation_date) {
        this.order_compensation_date = order_compensation_date;
    }

    public Date getOrder_compensation_dtm() {
        return order_compensation_dtm;
    }

    public void setOrder_compensation_dtm(Date order_compensation_dtm) {
        this.order_compensation_dtm = order_compensation_dtm;
    }

    public String getOrder_created_by() {
        return order_created_by;
    }

    public void setOrder_created_by(String order_created_by) {
        this.order_created_by = order_created_by;
    }

    public String getOrder_modified_by() {
        return order_modified_by;
    }

    public void setOrder_modified_by(String order_modified_by) {
        this.order_modified_by = order_modified_by;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public Date getOrder_service_start_date() {
        return order_service_start_date;
    }

    public void setOrder_service_start_date(Date order_service_start_date) {
        this.order_service_start_date = order_service_start_date;
    }

    public Date getOrder_service_end_date() {
        return order_service_end_date;
    }

    public void setOrder_service_end_date(Date order_service_end_date) {
        this.order_service_end_date = order_service_end_date;
    }

    public Date getOrder_start_date() {
        return order_start_date;
    }

    public void setOrder_start_date(Date order_start_date) {
        this.order_start_date = order_start_date;
    }

    public Date getOrder_start_dtm() {
        return order_start_dtm;
    }

    public void setOrder_start_dtm(Date order_start_dtm) {
        this.order_start_dtm = order_start_dtm;
    }

    public Date getOrder_end_date() {
        return order_end_date;
    }

    public void setOrder_end_date(Date order_end_date) {
        this.order_end_date = order_end_date;
    }

    public Date getOrder_end_dtm() {
        return order_end_dtm;
    }

    public void setOrder_end_dtm(Date order_end_dtm) {
        this.order_end_dtm = order_end_dtm;
    }

    public Date getTerm_start_date() {
        return term_start_date;
    }

    public void setTerm_start_date(Date term_start_date) {
        this.term_start_date = term_start_date;
    }

    public Date getTerm_end_date() {
        return term_end_date;
    }

    public void setTerm_end_date(Date term_end_date) {
        this.term_end_date = term_end_date;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getSku_label() {
        return sku_label;
    }

    public void setSku_label(String sku_label) {
        this.sku_label = sku_label;
    }

    public String getSku_type() {
        return sku_type;
    }

    public void setSku_type(String sku_type) {
        this.sku_type = sku_type;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public Double getSku_mrr() {
        return sku_mrr;
    }

    public void setSku_mrr(Double sku_mrr) {
        this.sku_mrr = sku_mrr;
    }

    public Double getSku_mrr_usd() {
        return sku_mrr_usd;
    }

    public void setSku_mrr_usd(Double sku_mrr_usd) {
        this.sku_mrr_usd = sku_mrr_usd;
    }

    public Double getSelling_unit_price() {
        return selling_unit_price;
    }

    public void setSelling_unit_price(Double selling_unit_price) {
        this.selling_unit_price = selling_unit_price;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getRecurring_ind() {
        return recurring_ind;
    }

    public void setRecurring_ind(String recurring_ind) {
        this.recurring_ind = recurring_ind;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getRating_model() {
        return rating_model;
    }

    public void setRating_model(String rating_model) {
        this.rating_model = rating_model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDeployment_model() {
        return deployment_model;
    }

    public void setDeployment_model(String deployment_model) {
        this.deployment_model = deployment_model;
    }

    public String getCore_offers() {
        return core_offers;
    }

    public void setCore_offers(String core_offers) {
        this.core_offers = core_offers;
    }

    public String getInitial_offer_code() {
        return initial_offer_code;
    }

    public void setInitial_offer_code(String initial_offer_code) {
        this.initial_offer_code = initial_offer_code;
    }

    public String getSignup_type() {
        return signup_type;
    }

    public void setSignup_type(String signup_type) {
        this.signup_type = signup_type;
    }

    public String getFraud_flag() {
        return fraud_flag;
    }

    public void setFraud_flag(String fraud_flag) {
        this.fraud_flag = fraud_flag;
    }

    public Long getOrder_track_id() {
        return order_track_id;
    }

    public void setOrder_track_id(Long order_track_id) {
        this.order_track_id = order_track_id;
    }

    public String getAcquisition_source() {
        return acquisition_source;
    }

    public void setAcquisition_source(String acquisition_source) {
        this.acquisition_source = acquisition_source;
    }

    public String getAcquisition_source_rptg() {
        return acquisition_source_rptg;
    }

    public void setAcquisition_source_rptg(String acquisition_source_rptg) {
        this.acquisition_source_rptg = acquisition_source_rptg;
    }

    public Integer getRownumber() {
        return rownumber;
    }

    public void setRownumber(Integer rownumber) {
        this.rownumber = rownumber;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getPromo_description() {
        return promo_description;
    }

    public void setPromo_description(String promo_description) {
        this.promo_description = promo_description;
    }

    public String getDelivery_channel() {
        return delivery_channel;
    }

    public void setDelivery_channel(String delivery_channel) {
        this.delivery_channel = delivery_channel;
    }

    public Date getPromo_start_date() {
        return promo_start_date;
    }

    public void setPromo_start_date(Date promo_start_date) {
        this.promo_start_date = promo_start_date;
    }

    public Date getPromo_end_date() {
        return promo_end_date;
    }

    public void setPromo_end_date(Date promo_end_date) {
        this.promo_end_date = promo_end_date;
    }

    public String getPromo_discount_type() {
        return promo_discount_type;
    }

    public void setPromo_discount_type(String promo_discount_type) {
        this.promo_discount_type = promo_discount_type;
    }

    public String getDiscount_pct_amt() {
        return discount_pct_amt;
    }

    public void setDiscount_pct_amt(String discount_pct_amt) {
        this.discount_pct_amt = discount_pct_amt;
    }

    public Long getPromo_term() {
        return promo_term;
    }

    public void setPromo_term(Long promo_term) {
        this.promo_term = promo_term;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getGeo_area() {
        return geo_area;
    }

    public void setGeo_area(String geo_area) {
        this.geo_area = geo_area;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getOrder_id_prev() {
        return order_id_prev;
    }

    public void setOrder_id_prev(Long order_id_prev) {
        this.order_id_prev = order_id_prev;
    }

    public Double getOrder_mrr() {
        return order_mrr;
    }

    public void setOrder_mrr(Double order_mrr) {
        this.order_mrr = order_mrr;
    }

    public Double getOrder_mrr_prev() {
        return order_mrr_prev;
    }

    public void setOrder_mrr_prev(Double order_mrr_prev) {
        this.order_mrr_prev = order_mrr_prev;
    }

    public Double getOrder_mrr_change() {
        return order_mrr_change;
    }

    public void setOrder_mrr_change(Double order_mrr_change) {
        this.order_mrr_change = order_mrr_change;
    }

    public String getOrder_change_type() {
        return order_change_type;
    }

    public void setOrder_change_type(String order_change_type) {
        this.order_change_type = order_change_type;
    }

    public String getOrder_change_type_detail() {
        return order_change_type_detail;
    }

    public void setOrder_change_type_detail(String order_change_type_detail) {
        this.order_change_type_detail = order_change_type_detail;
    }

    public Double getData_mrr() {
        return data_mrr;
    }

    public void setData_mrr(Double data_mrr) {
        this.data_mrr = data_mrr;
    }

    public Double getData_mrr_prev() {
        return data_mrr_prev;
    }

    public void setData_mrr_prev(Double data_mrr_prev) {
        this.data_mrr_prev = data_mrr_prev;
    }

    public Double getData_mrr_change() {
        return data_mrr_change;
    }

    public void setData_mrr_change(Double data_mrr_change) {
        this.data_mrr_change = data_mrr_change;
    }

    public Double getAudio_mrr() {
        return audio_mrr;
    }

    public void setAudio_mrr(Double audio_mrr) {
        this.audio_mrr = audio_mrr;
    }

    public Double getAudio_mrr_prev() {
        return audio_mrr_prev;
    }

    public void setAudio_mrr_prev(Double audio_mrr_prev) {
        this.audio_mrr_prev = audio_mrr_prev;
    }

    public Double getAudio_mrr_change() {
        return audio_mrr_change;
    }

    public void setAudio_mrr_change(Double audio_mrr_change) {
        this.audio_mrr_change = audio_mrr_change;
    }

    public Double getStorage_mrr() {
        return storage_mrr;
    }

    public void setStorage_mrr(Double storage_mrr) {
        this.storage_mrr = storage_mrr;
    }

    public Double getStorage_mrr_prev() {
        return storage_mrr_prev;
    }

    public void setStorage_mrr_prev(Double storage_mrr_prev) {
        this.storage_mrr_prev = storage_mrr_prev;
    }

    public Double getStorage_mrr_change() {
        return storage_mrr_change;
    }

    public void setStorage_mrr_change(Double storage_mrr_change) {
        this.storage_mrr_change = storage_mrr_change;
    }

    public String getData_change_type() {
        return data_change_type;
    }

    public void setData_change_type(String data_change_type) {
        this.data_change_type = data_change_type;
    }

    public String getAudio_change_type() {
        return audio_change_type;
    }

    public void setAudio_change_type(String audio_change_type) {
        this.audio_change_type = audio_change_type;
    }

    public String getStorage_change_type() {
        return storage_change_type;
    }

    public void setStorage_change_type(String storage_change_type) {
        this.storage_change_type = storage_change_type;
    }

    public Integer getOrder_filter() {
        return order_filter;
    }

    public void setOrder_filter(Integer order_filter) {
        this.order_filter = order_filter;
    }

    public String getOrder_comment() {
        return order_comment;
    }

    public void setOrder_comment(String order_comment) {
        this.order_comment = order_comment;
    }

    public Long getChurn_reason_code() {
        return churn_reason_code;
    }

    public void setChurn_reason_code(Long churn_reason_code) {
        this.churn_reason_code = churn_reason_code;
    }

    public String getChurn_reason_description() {
        return churn_reason_description;
    }

    public void setChurn_reason_description(String churn_reason_description) {
        this.churn_reason_description = churn_reason_description;
    }

    public String getChurn_comment() {
        return churn_comment;
    }

    public void setChurn_comment(String churn_comment) {
        this.churn_comment = churn_comment;
    }

    public String getFiscal_qtr_subscr_effect_from() {
        return fiscal_qtr_subscr_effect_from;
    }

    public void setFiscal_qtr_subscr_effect_from(String fiscal_qtr_subscr_effect_from) {
        this.fiscal_qtr_subscr_effect_from = fiscal_qtr_subscr_effect_from;
    }

    public String getFiscal_mth_subscr_effect_from() {
        return fiscal_mth_subscr_effect_from;
    }

    public void setFiscal_mth_subscr_effect_from(String fiscal_mth_subscr_effect_from) {
        this.fiscal_mth_subscr_effect_from = fiscal_mth_subscr_effect_from;
    }

    public String getFiscal_week_subscr_effect_from() {
        return fiscal_week_subscr_effect_from;
    }

    public void setFiscal_week_subscr_effect_from(String fiscal_week_subscr_effect_from) {
        this.fiscal_week_subscr_effect_from = fiscal_week_subscr_effect_from;
    }

    public String getFiscal_qtr_op_subscr_effect_from() {
        return fiscal_qtr_op_subscr_effect_from;
    }

    public void setFiscal_qtr_op_subscr_effect_from(String fiscal_qtr_op_subscr_effect_from) {
        this.fiscal_qtr_op_subscr_effect_from = fiscal_qtr_op_subscr_effect_from;
    }

    public String getFiscal_mth_op_subscr_effect_from() {
        return fiscal_mth_op_subscr_effect_from;
    }

    public void setFiscal_mth_op_subscr_effect_from(String fiscal_mth_op_subscr_effect_from) {
        this.fiscal_mth_op_subscr_effect_from = fiscal_mth_op_subscr_effect_from;
    }

    public String getFiscal_qtr_subscr_effect_to() {
        return fiscal_qtr_subscr_effect_to;
    }

    public void setFiscal_qtr_subscr_effect_to(String fiscal_qtr_subscr_effect_to) {
        this.fiscal_qtr_subscr_effect_to = fiscal_qtr_subscr_effect_to;
    }

    public String getFiscal_mth_subscr_effect_to() {
        return fiscal_mth_subscr_effect_to;
    }

    public void setFiscal_mth_subscr_effect_to(String fiscal_mth_subscr_effect_to) {
        this.fiscal_mth_subscr_effect_to = fiscal_mth_subscr_effect_to;
    }

    public String getFiscal_week_subscr_effect_to() {
        return fiscal_week_subscr_effect_to;
    }

    public void setFiscal_week_subscr_effect_to(String fiscal_week_subscr_effect_to) {
        this.fiscal_week_subscr_effect_to = fiscal_week_subscr_effect_to;
    }

    public String getFiscal_qtr_op_subscr_effect_to() {
        return fiscal_qtr_op_subscr_effect_to;
    }

    public void setFiscal_qtr_op_subscr_effect_to(String fiscal_qtr_op_subscr_effect_to) {
        this.fiscal_qtr_op_subscr_effect_to = fiscal_qtr_op_subscr_effect_to;
    }

    public String getFiscal_mth_op_subscr_effect_to() {
        return fiscal_mth_op_subscr_effect_to;
    }

    public void setFiscal_mth_op_subscr_effect_to(String fiscal_mth_op_subscr_effect_to) {
        this.fiscal_mth_op_subscr_effect_to = fiscal_mth_op_subscr_effect_to;
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

    public Date getOrder_service_start_dtm() {
        return order_service_start_dtm;
    }

    public void setOrder_service_start_dtm(Date order_service_start_dtm) {
        this.order_service_start_dtm = order_service_start_dtm;
    }

    public Date getOrder_service_end_dtm() {
        return order_service_end_dtm;
    }

    public void setOrder_service_end_dtm(Date order_service_end_dtm) {
        this.order_service_end_dtm = order_service_end_dtm;
    }

    public Date getSubscription_effective_from_dtm() {
        return subscription_effective_from_dtm;
    }

    public void setSubscription_effective_from_dtm(Date subscription_effective_from_dtm) {
        this.subscription_effective_from_dtm = subscription_effective_from_dtm;
    }

    public Date getSubscription_effective_to_dtm() {
        return subscription_effective_to_dtm;
    }

    public void setSubscription_effective_to_dtm(Date subscription_effective_to_dtm) {
        this.subscription_effective_to_dtm = subscription_effective_to_dtm;
    }

    public String getSubscription_same_week_cancellation_flag() {
        return subscription_same_week_cancellation_flag;
    }

    public void setSubscription_same_week_cancellation_flag(String subscription_same_week_cancellation_flag) {
        this.subscription_same_week_cancellation_flag = subscription_same_week_cancellation_flag;
    }

    public String getFirst_time_free_to_paid_flag() {
        return first_time_free_to_paid_flag;
    }

    public void setFirst_time_free_to_paid_flag(String first_time_free_to_paid_flag) {
        this.first_time_free_to_paid_flag = first_time_free_to_paid_flag;
    }

    public String getLast_record_identifier() {
        return last_record_identifier;
    }

    public void setLast_record_identifier(String last_record_identifier) {
        this.last_record_identifier = last_record_identifier;
    }

    public Long getChurn_reason_code_combined() {
        return churn_reason_code_combined;
    }

    public void setChurn_reason_code_combined(Long churn_reason_code_combined) {
        this.churn_reason_code_combined = churn_reason_code_combined;
    }

    public String getChurn_reason_description_combined() {
        return churn_reason_description_combined;
    }

    public void setChurn_reason_description_combined(String churn_reason_description_combined) {
        this.churn_reason_description_combined = churn_reason_description_combined;
    }
}
