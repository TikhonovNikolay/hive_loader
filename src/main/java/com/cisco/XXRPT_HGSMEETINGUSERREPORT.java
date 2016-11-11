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
public class XXRPT_HGSMEETINGUSERREPORT {
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
    private Double conftype;

    @Order(4)
    @QuerySqlField
    private String usertype;

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
    private Double uid_;

    @Order(9)
    @QuerySqlField
    private Double gid;

    @Order(10)
    @QuerySqlField
    private String webexid;

    @Order(11)
    @QuerySqlField
    private String username;

    @Order(12)
    @QuerySqlField
    private String useremail;

    @Order(13)
    @QuerySqlField
    private String ipaddress;

    @Order(14)
    @QuerySqlField
    private Double siteid;

    @Order(15)
    @QuerySqlField
    private String sitename;

    @Order(16)
    @QuerySqlField
    private Double dialin1;

    @Order(17)
    @QuerySqlField
    private Double dialin2;

    @Order(18)
    @QuerySqlField
    private Double dialout1;

    @Order(19)
    @QuerySqlField
    private Double dialout2;

    @Order(20)
    @QuerySqlField
    private Double voip;

    @Order(21)
    @QuerySqlField
    private Double hgssiteid;

    @Order(22)
    @QuerySqlField
    private String meetingtype;

    @Order(23)
    @QuerySqlField
    private Double timezone;

    @Order(24)
    @QuerySqlField
    private Double objid;

    @Order(25)
    @QuerySqlField
    private String clientagent;

    @Order(26)
    @QuerySqlField
    private String mtg_starttime;

    @Order(27)
    @QuerySqlField
    private String mtg_endtime;

    @Order(28)
    @QuerySqlField
    private Double mtg_timezone;

    @Order(29)
    @QuerySqlField
    private String registered;

    @Order(30)
    @QuerySqlField
    private String invited;

    @Order(31)
    @QuerySqlField
    private String reg_company;

    @Order(32)
    @QuerySqlField
    private String reg_title;

    @Order(33)
    @QuerySqlField
    private String reg_phone;

    @Order(34)
    @QuerySqlField
    private String reg_address1;

    @Order(35)
    @QuerySqlField
    private String reg_address2;

    @Order(36)
    @QuerySqlField
    private String reg_city;

    @Order(37)
    @QuerySqlField
    private String reg_state;

    @Order(38)
    @QuerySqlField
    private String reg_country;

    @Order(39)
    @QuerySqlField
    private String reg_zip;

    @Order(40)
    @QuerySqlField
    private Double non_billable;

    @Order(41)
    @QuerySqlField
    private Double attendantid;

    @Order(42)
    @QuerySqlField
    private String att_firstname;

    @Order(43)
    @QuerySqlField
    private String att_lastname;

    @Order(44)
    @QuerySqlField
    private String service_identifier;

    @Order(45)
    @QuerySqlField
    private String user_identifier;

    @Order(46)
    @QuerySqlField
    private String purchase_identifier;

    @Order(47)
    @QuerySqlField
    private String consumption_date;

    @Order(48)
    @QuerySqlField
    private String subscription_code;

    @Order(49)
    @QuerySqlField
    private Double boss_contractid;

    @Order(50)
    @QuerySqlField
    private Double rbe_status;

    @Order(51)
    @QuerySqlField
    private String rbe_timestamp;

    @Order(52)
    @QuerySqlField
    private Double isinternal;

    @Order(53)
    @QuerySqlField
    private Double prosumer;

    @Order(54)
    @QuerySqlField
    private Double inattentiveminutes;

    @Order(55)
    @QuerySqlField
    private Double meetinglanguageid;

    @Order(56)
    @QuerySqlField
    private Double regionid;

    @Order(57)
    @QuerySqlField
    private String webexid_translated;

    @Order(58)
    @QuerySqlField
    private String useremail_translated;

    @Order(59)
    @QuerySqlField
    private String reg_address1_translated;

    @Order(60)
    @QuerySqlField
    private String reg_address2_translated;

    @Order(61)
    @QuerySqlField
    private String reg_city_translated;

    @Order(62)
    @QuerySqlField
    private String reg_company_translated;

    @Order(63)
    @QuerySqlField
    private String reg_country_translated;

    @Order(64)
    @QuerySqlField
    private String att_firstname_translated;

    @Order(65)
    @QuerySqlField
    private String att_lastname_translated;

    @Order(66)
    @QuerySqlField
    private String reg_state_translated;

    @Order(67)
    @QuerySqlField
    private String reg_title_translated;

    @Order(68)
    @QuerySqlField
    private String username_translated;

    @Order(69)
    @QuerySqlField
    private String ipaddress_translated;

    @Order(70)
    @QuerySqlField
    private String sitename_translated;

    @Order(71)
    @QuerySqlField
    private String clientagent_translated;

    @Order(72)
    @QuerySqlField
    private String createdate;

    @Order(73)
    @QuerySqlField
    private String confname_translated;

    @Order(74)
    @QuerySqlField
    private String private_ipaddress;

    @Order(75)
    @QuerySqlField
    private Double hostid;

    @Order(76)
    @QuerySqlField
    private Double hostlanguageid;

    @Order(77)
    @QuerySqlField
    private String accountid;

    @Order(78)
    @QuerySqlField
    private String servicecode;

    @Order(79)
    @QuerySqlField
    private String subscriptioncode;

    @Order(80)
    @QuerySqlField
    private String billingaccountid;

    @Order(81)
    @QuerySqlField
    private String meetingnodeflag;

    @Order(82)
    @QuerySqlField
    private Double attendeetag;

    public XXRPT_HGSMEETINGUSERREPORT() {
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

    public Double getConftype() {
        return conftype;
    }

    public void setConftype(Double conftype) {
        this.conftype = conftype;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
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

    public Double getUid_() {
        return uid_;
    }

    public void setUid_(Double uid_) {
        this.uid_ = uid_;
    }

    public Double getGid() {
        return gid;
    }

    public void setGid(Double gid) {
        this.gid = gid;
    }

    public String getWebexid() {
        return webexid;
    }

    public void setWebexid(String webexid) {
        this.webexid = webexid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
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

    public Double getVoip() {
        return voip;
    }

    public void setVoip(Double voip) {
        this.voip = voip;
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

    public Double getObjid() {
        return objid;
    }

    public void setObjid(Double objid) {
        this.objid = objid;
    }

    public String getClientagent() {
        return clientagent;
    }

    public void setClientagent(String clientagent) {
        this.clientagent = clientagent;
    }

    public String getMtg_starttime() {
        return mtg_starttime;
    }

    public void setMtg_starttime(String mtg_starttime) {
        this.mtg_starttime = mtg_starttime;
    }

    public String getMtg_endtime() {
        return mtg_endtime;
    }

    public void setMtg_endtime(String mtg_endtime) {
        this.mtg_endtime = mtg_endtime;
    }

    public Double getMtg_timezone() {
        return mtg_timezone;
    }

    public void setMtg_timezone(Double mtg_timezone) {
        this.mtg_timezone = mtg_timezone;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getInvited() {
        return invited;
    }

    public void setInvited(String invited) {
        this.invited = invited;
    }

    public String getReg_company() {
        return reg_company;
    }

    public void setReg_company(String reg_company) {
        this.reg_company = reg_company;
    }

    public String getReg_title() {
        return reg_title;
    }

    public void setReg_title(String reg_title) {
        this.reg_title = reg_title;
    }

    public String getReg_phone() {
        return reg_phone;
    }

    public void setReg_phone(String reg_phone) {
        this.reg_phone = reg_phone;
    }

    public String getReg_address1() {
        return reg_address1;
    }

    public void setReg_address1(String reg_address1) {
        this.reg_address1 = reg_address1;
    }

    public String getReg_address2() {
        return reg_address2;
    }

    public void setReg_address2(String reg_address2) {
        this.reg_address2 = reg_address2;
    }

    public String getReg_city() {
        return reg_city;
    }

    public void setReg_city(String reg_city) {
        this.reg_city = reg_city;
    }

    public String getReg_state() {
        return reg_state;
    }

    public void setReg_state(String reg_state) {
        this.reg_state = reg_state;
    }

    public String getReg_country() {
        return reg_country;
    }

    public void setReg_country(String reg_country) {
        this.reg_country = reg_country;
    }

    public String getReg_zip() {
        return reg_zip;
    }

    public void setReg_zip(String reg_zip) {
        this.reg_zip = reg_zip;
    }

    public Double getNon_billable() {
        return non_billable;
    }

    public void setNon_billable(Double non_billable) {
        this.non_billable = non_billable;
    }

    public Double getAttendantid() {
        return attendantid;
    }

    public void setAttendantid(Double attendantid) {
        this.attendantid = attendantid;
    }

    public String getAtt_firstname() {
        return att_firstname;
    }

    public void setAtt_firstname(String att_firstname) {
        this.att_firstname = att_firstname;
    }

    public String getAtt_lastname() {
        return att_lastname;
    }

    public void setAtt_lastname(String att_lastname) {
        this.att_lastname = att_lastname;
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

    public Double getIsinternal() {
        return isinternal;
    }

    public void setIsinternal(Double isinternal) {
        this.isinternal = isinternal;
    }

    public Double getProsumer() {
        return prosumer;
    }

    public void setProsumer(Double prosumer) {
        this.prosumer = prosumer;
    }

    public Double getInattentiveminutes() {
        return inattentiveminutes;
    }

    public void setInattentiveminutes(Double inattentiveminutes) {
        this.inattentiveminutes = inattentiveminutes;
    }

    public Double getMeetinglanguageid() {
        return meetinglanguageid;
    }

    public void setMeetinglanguageid(Double meetinglanguageid) {
        this.meetinglanguageid = meetinglanguageid;
    }

    public Double getRegionid() {
        return regionid;
    }

    public void setRegionid(Double regionid) {
        this.regionid = regionid;
    }

    public String getWebexid_translated() {
        return webexid_translated;
    }

    public void setWebexid_translated(String webexid_translated) {
        this.webexid_translated = webexid_translated;
    }

    public String getUseremail_translated() {
        return useremail_translated;
    }

    public void setUseremail_translated(String useremail_translated) {
        this.useremail_translated = useremail_translated;
    }

    public String getReg_address1_translated() {
        return reg_address1_translated;
    }

    public void setReg_address1_translated(String reg_address1_translated) {
        this.reg_address1_translated = reg_address1_translated;
    }

    public String getReg_address2_translated() {
        return reg_address2_translated;
    }

    public void setReg_address2_translated(String reg_address2_translated) {
        this.reg_address2_translated = reg_address2_translated;
    }

    public String getReg_city_translated() {
        return reg_city_translated;
    }

    public void setReg_city_translated(String reg_city_translated) {
        this.reg_city_translated = reg_city_translated;
    }

    public String getReg_company_translated() {
        return reg_company_translated;
    }

    public void setReg_company_translated(String reg_company_translated) {
        this.reg_company_translated = reg_company_translated;
    }

    public String getReg_country_translated() {
        return reg_country_translated;
    }

    public void setReg_country_translated(String reg_country_translated) {
        this.reg_country_translated = reg_country_translated;
    }

    public String getAtt_firstname_translated() {
        return att_firstname_translated;
    }

    public void setAtt_firstname_translated(String att_firstname_translated) {
        this.att_firstname_translated = att_firstname_translated;
    }

    public String getAtt_lastname_translated() {
        return att_lastname_translated;
    }

    public void setAtt_lastname_translated(String att_lastname_translated) {
        this.att_lastname_translated = att_lastname_translated;
    }

    public String getReg_state_translated() {
        return reg_state_translated;
    }

    public void setReg_state_translated(String reg_state_translated) {
        this.reg_state_translated = reg_state_translated;
    }

    public String getReg_title_translated() {
        return reg_title_translated;
    }

    public void setReg_title_translated(String reg_title_translated) {
        this.reg_title_translated = reg_title_translated;
    }

    public String getUsername_translated() {
        return username_translated;
    }

    public void setUsername_translated(String username_translated) {
        this.username_translated = username_translated;
    }

    public String getIpaddress_translated() {
        return ipaddress_translated;
    }

    public void setIpaddress_translated(String ipaddress_translated) {
        this.ipaddress_translated = ipaddress_translated;
    }

    public String getSitename_translated() {
        return sitename_translated;
    }

    public void setSitename_translated(String sitename_translated) {
        this.sitename_translated = sitename_translated;
    }

    public String getClientagent_translated() {
        return clientagent_translated;
    }

    public void setClientagent_translated(String clientagent_translated) {
        this.clientagent_translated = clientagent_translated;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getConfname_translated() {
        return confname_translated;
    }

    public void setConfname_translated(String confname_translated) {
        this.confname_translated = confname_translated;
    }

    public String getPrivate_ipaddress() {
        return private_ipaddress;
    }

    public void setPrivate_ipaddress(String private_ipaddress) {
        this.private_ipaddress = private_ipaddress;
    }

    public Double getHostid() {
        return hostid;
    }

    public void setHostid(Double hostid) {
        this.hostid = hostid;
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

    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
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

    public String getMeetingnodeflag() {
        return meetingnodeflag;
    }

    public void setMeetingnodeflag(String meetingnodeflag) {
        this.meetingnodeflag = meetingnodeflag;
    }

    public Double getAttendeetag() {
        return attendeetag;
    }

    public void setAttendeetag(Double attendeetag) {
        this.attendeetag = attendeetag;
    }
}
