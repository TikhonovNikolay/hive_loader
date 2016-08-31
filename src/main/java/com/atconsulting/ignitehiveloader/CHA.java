package com.atconsulting.ignitehiveloader;

import java.sql.*;
import java.util.*;

import org.apache.ignite.cache.affinity.*;
import org.apache.ignite.cache.query.annotations.*;

/**
 * CHA
 */
public class CHA {
    /**
     * {@link CHA} key
     */
    public static class Key {
        /**
         * Call date
         */
        @QuerySqlField(name = "START_CALL_DATE_TIME")
        private Timestamp startCallDateTime;

        /**
         * Subscriber ID
         */
        @AffinityKeyMapped
        @QuerySqlField(name = "SUBSCRIBER_ID")
        private long subscriberId;

        public Key() {}

        public Key(Timestamp startCallDateTime,
                   long subscriberId) {
            this.startCallDateTime = startCallDateTime;
            this.subscriberId = subscriberId;
        }

        public Timestamp getStartCallDateTime() {
            return startCallDateTime;
        }

        public void setStartCallDateTime(Timestamp startCallDateTime) {
            this.startCallDateTime = startCallDateTime;
        }

        public long getSubscriberId() {
            return subscriberId;
        }

        public void setSubscriberId(long subscriberId) {
            this.subscriberId = subscriberId;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;

            if (object == null
                    || getClass() != object.getClass()) return false;

            final Key key = (Key) object;
            return subscriberId == key.subscriberId
                    && Objects.equals(startCallDateTime, key.startCallDateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subscriberId,
                    startCallDateTime);
        }

        @Override
        public String toString() {
            return "CHA.Key["
                    + "startCallDateTime=" + startCallDateTime
                    + ", subscriberId=" + subscriberId
                    + ']';
        }
    }

    /**
     * Activity type
     */
    @QuerySqlField(name = "ACTIVITY_TYPE")
    private String activityType;

    /**
     * Usage amount
     */
    @QuerySqlField(name = "USAGE_AMOUNT")
    private long usageAmount;

    /**
     * Balances info
     */
    @QuerySqlField(name = "BALANCES_INFO")
    private String balancesInfo;

    /**
     * Set timestamp
     */
    @QuerySqlField(name = "PUT_TIMESTAMP")
    private Timestamp putTimestamp;

    /**
     * Load duration, nanoseconds
     */
    @QuerySqlField(name = "LOAD_DURATION")
    private long loadDuration;

    public CHA() {}

    public CHA(String activityType,
               long usageAmount,
               String balancesInfo) {
        this.activityType = activityType;
        this.usageAmount = usageAmount;
        this.balancesInfo = balancesInfo;
    }

    public CHA(String activityType,
               long usageAmount,
               String balancesInfo,
               Timestamp putTimestamp,
               long loadDuration) {
        this(activityType,
                usageAmount,
                balancesInfo);
        this.putTimestamp = putTimestamp;
        this.loadDuration = loadDuration;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public long getUsageAmount() {
        return usageAmount;
    }

    public void setUsageAmount(long usageAmount) {
        this.usageAmount = usageAmount;
    }

    public String getBalancesInfo() {
        return balancesInfo;
    }

    public void setBalancesInfo(String balancesInfo) {
        this.balancesInfo = balancesInfo;
    }

    public Timestamp getPutTimestamp() {
        return putTimestamp;
    }

    public void setPutTimestamp(Timestamp putTimestamp) {
        this.putTimestamp = putTimestamp;
    }

    public long getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(long loadDuration) {
        this.loadDuration = loadDuration;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null
                || getClass() != object.getClass()) return false;

        final CHA cha = (CHA) object;
        return Objects.equals(activityType, cha.activityType)
                && usageAmount == cha.usageAmount
                && Objects.equals(balancesInfo, cha.balancesInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityType,
                usageAmount,
                balancesInfo);
    }

    @Override
    public String toString() {
        return "CHA["
                + "activityType='" + activityType + '\''
                + ", usageAmount=" + usageAmount
                + ", balancesInfo='" + balancesInfo + '\''
                + ", putTimestamp=" + putTimestamp
                + ", loadDuration=" + loadDuration
                + ']';
    }
}
