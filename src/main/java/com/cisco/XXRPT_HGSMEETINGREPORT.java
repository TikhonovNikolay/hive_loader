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
public class XXRPT_HGSMEETINGREPORT {
    @Order(0)
    @QuerySqlField
    private Double confid;

    @Order(1)
    @QuerySqlField
    private String confkey;

    @Order(2)
    @QuerySqlField
    private String confname;

    @Order(3)
    @QuerySqlField
    private String code;

    @Order(4)
    @QuerySqlField
    private Double conftype;

    @Order(5)
    @QuerySqlField
    private String starttime;

    @Order(6)
    @QuerySqlField
    private String endtime;

    @Order(7)
    @QuerySqlField
    private Double duration;

    @Order(8)
    @QuerySqlField
    private Double hostid;

    @Order(9)
    @QuerySqlField
    private String webexid;

    @Order(10)
    @QuerySqlField
    private String hostname;

    @Order(11)
    @QuerySqlField
    private String hostemail;

    @Order(12)
    @QuerySqlField
    private Double totalattendee;

    @Order(13)
    @QuerySqlField
    private Double dialin_user;

    @Order(14)
    @QuerySqlField
    private Double dialin1;

    @Order(15)
    @QuerySqlField
    private Double dialin2;

    @Order(16)
    @QuerySqlField
    private Double dialout_user;

    @Order(17)
    @QuerySqlField
    private Double dialout1;

    @Order(18)
    @QuerySqlField
    private Double dialout2;

    @Order(19)
    @QuerySqlField
    private Double voip_user;

    @Order(20)
    @QuerySqlField
    private Double voip;

    @Order(21)
    @QuerySqlField
    private Double siteid;

    @Order(22)
    @QuerySqlField
    private String sitename;

    @Order(23)
    @QuerySqlField
    private Double invoiceid;

    @Order(24)
    @QuerySqlField
    private Double paymentid;

    @Order(25)
    @QuerySqlField
    private Double amount;

    @Order(26)
    @QuerySqlField
    private Double chargeflag;

    @Order(27)
    @QuerySqlField
    private Double peoplemin;

    @Order(28)
    @QuerySqlField
    private String division;

    @Order(29)
    @QuerySqlField
    private String department;

    @Order(30)
    @QuerySqlField
    private String project;

    @Order(31)
    @QuerySqlField
    private String other;

    @Order(32)
    @QuerySqlField
    private String charge;

    @Order(33)
    @QuerySqlField
    private Double creditcardid;

    @Order(34)
    @QuerySqlField
    private Double customerid;

    @Order(35)
    @QuerySqlField
    private String creditcardholder;

    @Order(36)
    @QuerySqlField
    private Double hgssiteid;

    @Order(37)
    @QuerySqlField
    private String meetingtype;

    @Order(38)
    @QuerySqlField
    private Double timezone;

    @Order(39)
    @QuerySqlField
    private Double platform;

    @Order(40)
    @QuerySqlField
    private Double ssofficeid;

    @Order(41)
    @QuerySqlField
    private String city;

    @Order(42)
    @QuerySqlField
    private String state;

    @Order(43)
    @QuerySqlField
    private String zipcode;

    @Order(44)
    @QuerySqlField
    private String country;

    @Order(45)
    @QuerySqlField
    private String projectnumber;

    @Order(46)
    @QuerySqlField
    private String trackingnumber;

    @Order(47)
    @QuerySqlField
    private String custom7;

    @Order(48)
    @QuerySqlField
    private String custom8;

    @Order(49)
    @QuerySqlField
    private String custom9;

    @Order(50)
    @QuerySqlField
    private String custom10;

    @Order(51)
    @QuerySqlField
    private String host_phone;

    @Order(52)
    @QuerySqlField
    private String win;

    @Order(53)
    @QuerySqlField
    private Double registered;

    @Order(54)
    @QuerySqlField
    private Double invited;

    @Order(55)
    @QuerySqlField
    private String submeetingtype;

    @Order(56)
    @QuerySqlField
    private Double not_attended;

    @Order(57)
    @QuerySqlField
    private String service_identifier;

    @Order(58)
    @QuerySqlField
    private String user_identifier;

    @Order(59)
    @QuerySqlField
    private String purchase_identifier;

    @Order(60)
    @QuerySqlField
    private String consumption_date;

    @Order(61)
    @QuerySqlField
    private String subscription_code;

    @Order(62)
    @QuerySqlField
    private Double boss_contractid;

    @Order(63)
    @QuerySqlField
    private Double callin_toll_users;

    @Order(64)
    @QuerySqlField
    private Double callin_tollfree_users;

    @Order(65)
    @QuerySqlField
    private Double callback_dom_users;

    @Order(66)
    @QuerySqlField
    private Double callback_intl_users;

    @Order(67)
    @QuerySqlField
    private Double did_mins;

    @Order(68)
    @QuerySqlField
    private Double did_users;

    @Order(69)
    @QuerySqlField
    private Double rbe_status;

    @Order(70)
    @QuerySqlField
    private String rbe_timestamp;

    @Order(71)
    @QuerySqlField
    private Double spv_mins;

    @Order(72)
    @QuerySqlField
    private Double spv_users;

    @Order(73)
    @QuerySqlField
    private Double mpv_mins;

    @Order(74)
    @QuerySqlField
    private Double mpv_users;

    @Order(75)
    @QuerySqlField
    private Double sa_users;

    @Order(76)
    @QuerySqlField
    private Double sa_mins;

    @Order(77)
    @QuerySqlField
    private Double internal_users;

    @Order(78)
    @QuerySqlField
    private Double internal_mins;

    @Order(79)
    @QuerySqlField
    private Double companyid;

    @Order(80)
    @QuerySqlField
    private Double prosumer;

    @Order(81)
    @QuerySqlField
    private Double peakattendee;

    @Order(82)
    @QuerySqlField
    private Double bt_basic_toll;

    @Order(83)
    @QuerySqlField
    private Double bt_basic_tollfree;

    @Order(84)
    @QuerySqlField
    private Double bt_basic_callback;

    @Order(85)
    @QuerySqlField
    private Double bt_basic_intl_callback;

    @Order(86)
    @QuerySqlField
    private Double bt_basic_intl_tollfree;

    @Order(87)
    @QuerySqlField
    private Double bt_premium_toll;

    @Order(88)
    @QuerySqlField
    private Double bt_premium_tollfree;

    @Order(89)
    @QuerySqlField
    private Double bt_premium_callback;

    @Order(90)
    @QuerySqlField
    private Double bt_premium_intl_callback;

    @Order(91)
    @QuerySqlField
    private Double bt_premium_intl_tollfree;

    @Order(92)
    @QuerySqlField
    private Double recurringseq;

    @Order(93)
    @QuerySqlField
    private Double integrationtype;

    @Order(94)
    @QuerySqlField
    private Double scheduledfrom;

    @Order(95)
    @QuerySqlField
    private Double startedfrom;

    @Order(96)
    @QuerySqlField
    private String modifiedmtgtype;

    @Order(97)
    @QuerySqlField
    private Double pureattendee;

    @Order(98)
    @QuerySqlField
    private String custom10_translated;

    @Order(99)
    @QuerySqlField
    private String custom7_translated;

    @Order(100)
    @QuerySqlField
    private String custom8_translated;

    @Order(101)
    @QuerySqlField
    private String custom9_translated;

    @Order(102)
    @QuerySqlField
    private String department_translated;

    @Order(103)
    @QuerySqlField
    private String division_translated;

    @Order(104)
    @QuerySqlField
    private String other_translated;

    @Order(105)
    @QuerySqlField
    private String project_translated;

    @Order(106)
    @QuerySqlField
    private String projectnumber_translated;

    @Order(107)
    @QuerySqlField
    private String trackingnumber_translated;

    @Order(108)
    @QuerySqlField
    private String webexid_translated;

    @Order(109)
    @QuerySqlField
    private String hostemail_translated;

    @Order(110)
    @QuerySqlField
    private String city_translated;

    @Order(111)
    @QuerySqlField
    private String country_translated;

    @Order(112)
    @QuerySqlField
    private String state_translated;

    @Order(113)
    @QuerySqlField
    private String code_translated;

    @Order(114)
    @QuerySqlField
    private String hostname_translated;

    @Order(115)
    @QuerySqlField
    private String sitename_translated;

    @Order(116)
    @QuerySqlField
    private String zipcode_translated;

    @Order(117)
    @QuerySqlField
    private String modifiedmtgtype_translated;

    @Order(118)
    @QuerySqlField
    private String creditcardholder_translated;

    @Order(119)
    @QuerySqlField
    private String confname_translated;

    @Order(120)
    @QuerySqlField
    private String createdate;

    @Order(121)
    @QuerySqlField
    private Double bt_premium_replay;

    @Order(122)
    @QuerySqlField
    private Double internalmeeting;

    @Order(123)
    @QuerySqlField
    private Double hostlanguageid;

    @Order(124)
    @QuerySqlField
    private String accountid;

    @Order(125)
    @QuerySqlField
    private String subscriptioncode;

    @Order(126)
    @QuerySqlField
    private String billingaccountid;

    @Order(127)
    @QuerySqlField
    private String servicecode;

    @Order(128)
    @QuerySqlField
    private Double did_toll_mins;

    @Order(129)
    @QuerySqlField
    private Double did_tollfree_mins;

    @Order(130)
    @QuerySqlField
    private String meetinguuid;

    @Order(131)
    @QuerySqlField
    private String meetinginstanceuuid;

    @Order(132)
    @QuerySqlField
    private Double prerate_status;

    @Order(133)
    @QuerySqlField
    private String prerate_date;

    @Order(134)
    @QuerySqlField
    private Double objid;

    @Order(135)
    @QuerySqlField
    private Double peakattendee_aud;

    @Order(136)
    @QuerySqlField
    private String feature;

    @Order(137)
    @QuerySqlField
    private String service_type;

    public XXRPT_HGSMEETINGREPORT() {
    }

    public Double getConfid() {
        return confid;
    }

    public void setConfid(Double confid) {
        this.confid = confid;
    }

    public String getConfkey() {
        return confkey;
    }

    public void setConfkey(String confkey) {
        this.confkey = confkey;
    }

    public String getConfname() {
        return confname;
    }

    public void setConfname(String confname) {
        this.confname = confname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getConftype() {
        return conftype;
    }

    public void setConftype(Double conftype) {
        this.conftype = conftype;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getHostid() {
        return hostid;
    }

    public void setHostid(Double hostid) {
        this.hostid = hostid;
    }

    public String getWebexid() {
        return webexid;
    }

    public void setWebexid(String webexid) {
        this.webexid = webexid;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostemail() {
        return hostemail;
    }

    public void setHostemail(String hostemail) {
        this.hostemail = hostemail;
    }

    public Double getTotalattendee() {
        return totalattendee;
    }

    public void setTotalattendee(Double totalattendee) {
        this.totalattendee = totalattendee;
    }

    public Double getDialin_user() {
        return dialin_user;
    }

    public void setDialin_user(Double dialin_user) {
        this.dialin_user = dialin_user;
    }

    public Double getDialin1() {
        return dialin1;
    }

    public void setDialin1(Double dialin1) {
        this.dialin1 = dialin1;
    }

    public Double getDialin2() {
        return dialin2;
    }

    public void setDialin2(Double dialin2) {
        this.dialin2 = dialin2;
    }

    public Double getDialout_user() {
        return dialout_user;
    }

    public void setDialout_user(Double dialout_user) {
        this.dialout_user = dialout_user;
    }

    public Double getDialout1() {
        return dialout1;
    }

    public void setDialout1(Double dialout1) {
        this.dialout1 = dialout1;
    }

    public Double getDialout2() {
        return dialout2;
    }

    public void setDialout2(Double dialout2) {
        this.dialout2 = dialout2;
    }

    public Double getVoip_user() {
        return voip_user;
    }

    public void setVoip_user(Double voip_user) {
        this.voip_user = voip_user;
    }

    public Double getVoip() {
        return voip;
    }

    public void setVoip(Double voip) {
        this.voip = voip;
    }

    public Double getSiteid() {
        return siteid;
    }

    public void setSiteid(Double siteid) {
        this.siteid = siteid;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public Double getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Double invoiceid) {
        this.invoiceid = invoiceid;
    }

    public Double getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Double paymentid) {
        this.paymentid = paymentid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getChargeflag() {
        return chargeflag;
    }

    public void setChargeflag(Double chargeflag) {
        this.chargeflag = chargeflag;
    }

    public Double getPeoplemin() {
        return peoplemin;
    }

    public void setPeoplemin(Double peoplemin) {
        this.peoplemin = peoplemin;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public Double getCreditcardid() {
        return creditcardid;
    }

    public void setCreditcardid(Double creditcardid) {
        this.creditcardid = creditcardid;
    }

    public Double getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Double customerid) {
        this.customerid = customerid;
    }

    public String getCreditcardholder() {
        return creditcardholder;
    }

    public void setCreditcardholder(String creditcardholder) {
        this.creditcardholder = creditcardholder;
    }

    public Double getHgssiteid() {
        return hgssiteid;
    }

    public void setHgssiteid(Double hgssiteid) {
        this.hgssiteid = hgssiteid;
    }

    public String getMeetingtype() {
        return meetingtype;
    }

    public void setMeetingtype(String meetingtype) {
        this.meetingtype = meetingtype;
    }

    public Double getTimezone() {
        return timezone;
    }

    public void setTimezone(Double timezone) {
        this.timezone = timezone;
    }

    public Double getPlatform() {
        return platform;
    }

    public void setPlatform(Double platform) {
        this.platform = platform;
    }

    public Double getSsofficeid() {
        return ssofficeid;
    }

    public void setSsofficeid(Double ssofficeid) {
        this.ssofficeid = ssofficeid;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProjectnumber() {
        return projectnumber;
    }

    public void setProjectnumber(String projectnumber) {
        this.projectnumber = projectnumber;
    }

    public String getTrackingnumber() {
        return trackingnumber;
    }

    public void setTrackingnumber(String trackingnumber) {
        this.trackingnumber = trackingnumber;
    }

    public String getCustom7() {
        return custom7;
    }

    public void setCustom7(String custom7) {
        this.custom7 = custom7;
    }

    public String getCustom8() {
        return custom8;
    }

    public void setCustom8(String custom8) {
        this.custom8 = custom8;
    }

    public String getCustom9() {
        return custom9;
    }

    public void setCustom9(String custom9) {
        this.custom9 = custom9;
    }

    public String getCustom10() {
        return custom10;
    }

    public void setCustom10(String custom10) {
        this.custom10 = custom10;
    }

    public String getHost_phone() {
        return host_phone;
    }

    public void setHost_phone(String host_phone) {
        this.host_phone = host_phone;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public Double getRegistered() {
        return registered;
    }

    public void setRegistered(Double registered) {
        this.registered = registered;
    }

    public Double getInvited() {
        return invited;
    }

    public void setInvited(Double invited) {
        this.invited = invited;
    }

    public String getSubmeetingtype() {
        return submeetingtype;
    }

    public void setSubmeetingtype(String submeetingtype) {
        this.submeetingtype = submeetingtype;
    }

    public Double getNot_attended() {
        return not_attended;
    }

    public void setNot_attended(Double not_attended) {
        this.not_attended = not_attended;
    }

    public String getService_identifier() {
        return service_identifier;
    }

    public void setService_identifier(String service_identifier) {
        this.service_identifier = service_identifier;
    }

    public String getUser_identifier() {
        return user_identifier;
    }

    public void setUser_identifier(String user_identifier) {
        this.user_identifier = user_identifier;
    }

    public String getPurchase_identifier() {
        return purchase_identifier;
    }

    public void setPurchase_identifier(String purchase_identifier) {
        this.purchase_identifier = purchase_identifier;
    }

    public String getConsumption_date() {
        return consumption_date;
    }

    public void setConsumption_date(String consumption_date) {
        this.consumption_date = consumption_date;
    }

    public String getSubscription_code() {
        return subscription_code;
    }

    public void setSubscription_code(String subscription_code) {
        this.subscription_code = subscription_code;
    }

    public Double getBoss_contractid() {
        return boss_contractid;
    }

    public void setBoss_contractid(Double boss_contractid) {
        this.boss_contractid = boss_contractid;
    }

    public Double getCallin_toll_users() {
        return callin_toll_users;
    }

    public void setCallin_toll_users(Double callin_toll_users) {
        this.callin_toll_users = callin_toll_users;
    }

    public Double getCallin_tollfree_users() {
        return callin_tollfree_users;
    }

    public void setCallin_tollfree_users(Double callin_tollfree_users) {
        this.callin_tollfree_users = callin_tollfree_users;
    }

    public Double getCallback_dom_users() {
        return callback_dom_users;
    }

    public void setCallback_dom_users(Double callback_dom_users) {
        this.callback_dom_users = callback_dom_users;
    }

    public Double getCallback_intl_users() {
        return callback_intl_users;
    }

    public void setCallback_intl_users(Double callback_intl_users) {
        this.callback_intl_users = callback_intl_users;
    }

    public Double getDid_mins() {
        return did_mins;
    }

    public void setDid_mins(Double did_mins) {
        this.did_mins = did_mins;
    }

    public Double getDid_users() {
        return did_users;
    }

    public void setDid_users(Double did_users) {
        this.did_users = did_users;
    }

    public Double getRbe_status() {
        return rbe_status;
    }

    public void setRbe_status(Double rbe_status) {
        this.rbe_status = rbe_status;
    }

    public String getRbe_timestamp() {
        return rbe_timestamp;
    }

    public void setRbe_timestamp(String rbe_timestamp) {
        this.rbe_timestamp = rbe_timestamp;
    }

    public Double getSpv_mins() {
        return spv_mins;
    }

    public void setSpv_mins(Double spv_mins) {
        this.spv_mins = spv_mins;
    }

    public Double getSpv_users() {
        return spv_users;
    }

    public void setSpv_users(Double spv_users) {
        this.spv_users = spv_users;
    }

    public Double getMpv_mins() {
        return mpv_mins;
    }

    public void setMpv_mins(Double mpv_mins) {
        this.mpv_mins = mpv_mins;
    }

    public Double getMpv_users() {
        return mpv_users;
    }

    public void setMpv_users(Double mpv_users) {
        this.mpv_users = mpv_users;
    }

    public Double getSa_users() {
        return sa_users;
    }

    public void setSa_users(Double sa_users) {
        this.sa_users = sa_users;
    }

    public Double getSa_mins() {
        return sa_mins;
    }

    public void setSa_mins(Double sa_mins) {
        this.sa_mins = sa_mins;
    }

    public Double getInternal_users() {
        return internal_users;
    }

    public void setInternal_users(Double internal_users) {
        this.internal_users = internal_users;
    }

    public Double getInternal_mins() {
        return internal_mins;
    }

    public void setInternal_mins(Double internal_mins) {
        this.internal_mins = internal_mins;
    }

    public Double getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Double companyid) {
        this.companyid = companyid;
    }

    public Double getProsumer() {
        return prosumer;
    }

    public void setProsumer(Double prosumer) {
        this.prosumer = prosumer;
    }

    public Double getPeakattendee() {
        return peakattendee;
    }

    public void setPeakattendee(Double peakattendee) {
        this.peakattendee = peakattendee;
    }

    public Double getBt_basic_toll() {
        return bt_basic_toll;
    }

    public void setBt_basic_toll(Double bt_basic_toll) {
        this.bt_basic_toll = bt_basic_toll;
    }

    public Double getBt_basic_tollfree() {
        return bt_basic_tollfree;
    }

    public void setBt_basic_tollfree(Double bt_basic_tollfree) {
        this.bt_basic_tollfree = bt_basic_tollfree;
    }

    public Double getBt_basic_callback() {
        return bt_basic_callback;
    }

    public void setBt_basic_callback(Double bt_basic_callback) {
        this.bt_basic_callback = bt_basic_callback;
    }

    public Double getBt_basic_intl_callback() {
        return bt_basic_intl_callback;
    }

    public void setBt_basic_intl_callback(Double bt_basic_intl_callback) {
        this.bt_basic_intl_callback = bt_basic_intl_callback;
    }

    public Double getBt_basic_intl_tollfree() {
        return bt_basic_intl_tollfree;
    }

    public void setBt_basic_intl_tollfree(Double bt_basic_intl_tollfree) {
        this.bt_basic_intl_tollfree = bt_basic_intl_tollfree;
    }

    public Double getBt_premium_toll() {
        return bt_premium_toll;
    }

    public void setBt_premium_toll(Double bt_premium_toll) {
        this.bt_premium_toll = bt_premium_toll;
    }

    public Double getBt_premium_tollfree() {
        return bt_premium_tollfree;
    }

    public void setBt_premium_tollfree(Double bt_premium_tollfree) {
        this.bt_premium_tollfree = bt_premium_tollfree;
    }

    public Double getBt_premium_callback() {
        return bt_premium_callback;
    }

    public void setBt_premium_callback(Double bt_premium_callback) {
        this.bt_premium_callback = bt_premium_callback;
    }

    public Double getBt_premium_intl_callback() {
        return bt_premium_intl_callback;
    }

    public void setBt_premium_intl_callback(Double bt_premium_intl_callback) {
        this.bt_premium_intl_callback = bt_premium_intl_callback;
    }

    public Double getBt_premium_intl_tollfree() {
        return bt_premium_intl_tollfree;
    }

    public void setBt_premium_intl_tollfree(Double bt_premium_intl_tollfree) {
        this.bt_premium_intl_tollfree = bt_premium_intl_tollfree;
    }

    public Double getRecurringseq() {
        return recurringseq;
    }

    public void setRecurringseq(Double recurringseq) {
        this.recurringseq = recurringseq;
    }

    public Double getIntegrationtype() {
        return integrationtype;
    }

    public void setIntegrationtype(Double integrationtype) {
        this.integrationtype = integrationtype;
    }

    public Double getScheduledfrom() {
        return scheduledfrom;
    }

    public void setScheduledfrom(Double scheduledfrom) {
        this.scheduledfrom = scheduledfrom;
    }

    public Double getStartedfrom() {
        return startedfrom;
    }

    public void setStartedfrom(Double startedfrom) {
        this.startedfrom = startedfrom;
    }

    public String getModifiedmtgtype() {
        return modifiedmtgtype;
    }

    public void setModifiedmtgtype(String modifiedmtgtype) {
        this.modifiedmtgtype = modifiedmtgtype;
    }

    public Double getPureattendee() {
        return pureattendee;
    }

    public void setPureattendee(Double pureattendee) {
        this.pureattendee = pureattendee;
    }

    public String getCustom10_translated() {
        return custom10_translated;
    }

    public void setCustom10_translated(String custom10_translated) {
        this.custom10_translated = custom10_translated;
    }

    public String getCustom7_translated() {
        return custom7_translated;
    }

    public void setCustom7_translated(String custom7_translated) {
        this.custom7_translated = custom7_translated;
    }

    public String getCustom8_translated() {
        return custom8_translated;
    }

    public void setCustom8_translated(String custom8_translated) {
        this.custom8_translated = custom8_translated;
    }

    public String getCustom9_translated() {
        return custom9_translated;
    }

    public void setCustom9_translated(String custom9_translated) {
        this.custom9_translated = custom9_translated;
    }

    public String getDepartment_translated() {
        return department_translated;
    }

    public void setDepartment_translated(String department_translated) {
        this.department_translated = department_translated;
    }

    public String getDivision_translated() {
        return division_translated;
    }

    public void setDivision_translated(String division_translated) {
        this.division_translated = division_translated;
    }

    public String getOther_translated() {
        return other_translated;
    }

    public void setOther_translated(String other_translated) {
        this.other_translated = other_translated;
    }

    public String getProject_translated() {
        return project_translated;
    }

    public void setProject_translated(String project_translated) {
        this.project_translated = project_translated;
    }

    public String getProjectnumber_translated() {
        return projectnumber_translated;
    }

    public void setProjectnumber_translated(String projectnumber_translated) {
        this.projectnumber_translated = projectnumber_translated;
    }

    public String getTrackingnumber_translated() {
        return trackingnumber_translated;
    }

    public void setTrackingnumber_translated(String trackingnumber_translated) {
        this.trackingnumber_translated = trackingnumber_translated;
    }

    public String getWebexid_translated() {
        return webexid_translated;
    }

    public void setWebexid_translated(String webexid_translated) {
        this.webexid_translated = webexid_translated;
    }

    public String getHostemail_translated() {
        return hostemail_translated;
    }

    public void setHostemail_translated(String hostemail_translated) {
        this.hostemail_translated = hostemail_translated;
    }

    public String getCity_translated() {
        return city_translated;
    }

    public void setCity_translated(String city_translated) {
        this.city_translated = city_translated;
    }

    public String getCountry_translated() {
        return country_translated;
    }

    public void setCountry_translated(String country_translated) {
        this.country_translated = country_translated;
    }

    public String getState_translated() {
        return state_translated;
    }

    public void setState_translated(String state_translated) {
        this.state_translated = state_translated;
    }

    public String getCode_translated() {
        return code_translated;
    }

    public void setCode_translated(String code_translated) {
        this.code_translated = code_translated;
    }

    public String getHostname_translated() {
        return hostname_translated;
    }

    public void setHostname_translated(String hostname_translated) {
        this.hostname_translated = hostname_translated;
    }

    public String getSitename_translated() {
        return sitename_translated;
    }

    public void setSitename_translated(String sitename_translated) {
        this.sitename_translated = sitename_translated;
    }

    public String getZipcode_translated() {
        return zipcode_translated;
    }

    public void setZipcode_translated(String zipcode_translated) {
        this.zipcode_translated = zipcode_translated;
    }

    public String getModifiedmtgtype_translated() {
        return modifiedmtgtype_translated;
    }

    public void setModifiedmtgtype_translated(String modifiedmtgtype_translated) {
        this.modifiedmtgtype_translated = modifiedmtgtype_translated;
    }

    public String getCreditcardholder_translated() {
        return creditcardholder_translated;
    }

    public void setCreditcardholder_translated(String creditcardholder_translated) {
        this.creditcardholder_translated = creditcardholder_translated;
    }

    public String getConfname_translated() {
        return confname_translated;
    }

    public void setConfname_translated(String confname_translated) {
        this.confname_translated = confname_translated;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Double getBt_premium_replay() {
        return bt_premium_replay;
    }

    public void setBt_premium_replay(Double bt_premium_replay) {
        this.bt_premium_replay = bt_premium_replay;
    }

    public Double getInternalmeeting() {
        return internalmeeting;
    }

    public void setInternalmeeting(Double internalmeeting) {
        this.internalmeeting = internalmeeting;
    }

    public Double getHostlanguageid() {
        return hostlanguageid;
    }

    public void setHostlanguageid(Double hostlanguageid) {
        this.hostlanguageid = hostlanguageid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getSubscriptioncode() {
        return subscriptioncode;
    }

    public void setSubscriptioncode(String subscriptioncode) {
        this.subscriptioncode = subscriptioncode;
    }

    public String getBillingaccountid() {
        return billingaccountid;
    }

    public void setBillingaccountid(String billingaccountid) {
        this.billingaccountid = billingaccountid;
    }

    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public Double getDid_toll_mins() {
        return did_toll_mins;
    }

    public void setDid_toll_mins(Double did_toll_mins) {
        this.did_toll_mins = did_toll_mins;
    }

    public Double getDid_tollfree_mins() {
        return did_tollfree_mins;
    }

    public void setDid_tollfree_mins(Double did_tollfree_mins) {
        this.did_tollfree_mins = did_tollfree_mins;
    }

    public String getMeetinguuid() {
        return meetinguuid;
    }

    public void setMeetinguuid(String meetinguuid) {
        this.meetinguuid = meetinguuid;
    }

    public String getMeetinginstanceuuid() {
        return meetinginstanceuuid;
    }

    public void setMeetinginstanceuuid(String meetinginstanceuuid) {
        this.meetinginstanceuuid = meetinginstanceuuid;
    }

    public Double getPrerate_status() {
        return prerate_status;
    }

    public void setPrerate_status(Double prerate_status) {
        this.prerate_status = prerate_status;
    }

    public String getPrerate_date() {
        return prerate_date;
    }

    public void setPrerate_date(String prerate_date) {
        this.prerate_date = prerate_date;
    }

    public Double getObjid() {
        return objid;
    }

    public void setObjid(Double objid) {
        this.objid = objid;
    }

    public Double getPeakattendee_aud() {
        return peakattendee_aud;
    }

    public void setPeakattendee_aud(Double peakattendee_aud) {
        this.peakattendee_aud = peakattendee_aud;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }
}
