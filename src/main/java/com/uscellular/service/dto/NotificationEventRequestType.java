
package com.uscellular.service.dto;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Root Type for NotificationEventRequestType
 * <p>
 * JSON Request to sent message to Kafka Topic.
 * 
 */
@Generated("jsonschema2pojo")
public class NotificationEventRequestType {

    @SerializedName("notificationActivityType")
    @Expose
    private String notificationActivityType;
    /**
     * Method by which the notification will be fulfilled. Values: 'SMS' or 'email'
     * (Required)
     * 
     */
    @SerializedName("notificationMethod")
    @Expose
    private String notificationMethod;
    /**
     * Unique identifier of Customer.
     * 
     */
    @SerializedName("customerId")
    @Expose
    private String customerId;
    /**
     * Information needed to send a SMS.
     * 
     */
    @SerializedName("smsInfo")
    @Expose
    private SmsInfo smsInfo;
    /**
     * Information needed to send an email.
     * 
     */
    @SerializedName("emailInfo")
    @Expose
    private EmailInfo emailInfo;

    public String getNotificationActivityType() {
        return notificationActivityType;
    }

    public void setNotificationActivityType(String notificationActivityType) {
        this.notificationActivityType = notificationActivityType;
    }

    /**
     * Method by which the notification will be fulfilled. Values: 'SMS' or 'email'
     * (Required)
     * 
     */
    public String getNotificationMethod() {
        return notificationMethod;
    }

    /**
     * Method by which the notification will be fulfilled. Values: 'SMS' or 'email'
     * (Required)
     * 
     */
    public void setNotificationMethod(String notificationMethod) {
        this.notificationMethod = notificationMethod;
    }

    /**
     * Unique identifier of Customer.
     * 
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Unique identifier of Customer.
     * 
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Information needed to send a SMS.
     * 
     */
    public SmsInfo getSmsInfo() {
        return smsInfo;
    }

    /**
     * Information needed to send a SMS.
     * 
     */
    public void setSmsInfo(SmsInfo smsInfo) {
        this.smsInfo = smsInfo;
    }

    /**
     * Information needed to send an email.
     * 
     */
    public EmailInfo getEmailInfo() {
        return emailInfo;
    }

    /**
     * Information needed to send an email.
     * 
     */
    public void setEmailInfo(EmailInfo emailInfo) {
        this.emailInfo = emailInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NotificationEventRequestType.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("notificationActivityType");
        sb.append('=');
        sb.append(((this.notificationActivityType == null)?"<null>":this.notificationActivityType));
        sb.append(',');
        sb.append("notificationMethod");
        sb.append('=');
        sb.append(((this.notificationMethod == null)?"<null>":this.notificationMethod));
        sb.append(',');
        sb.append("customerId");
        sb.append('=');
        sb.append(((this.customerId == null)?"<null>":this.customerId));
        sb.append(',');
        sb.append("smsInfo");
        sb.append('=');
        sb.append(((this.smsInfo == null)?"<null>":this.smsInfo));
        sb.append(',');
        sb.append("emailInfo");
        sb.append('=');
        sb.append(((this.emailInfo == null)?"<null>":this.emailInfo));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.customerId == null)? 0 :this.customerId.hashCode()));
        result = ((result* 31)+((this.notificationActivityType == null)? 0 :this.notificationActivityType.hashCode()));
        result = ((result* 31)+((this.notificationMethod == null)? 0 :this.notificationMethod.hashCode()));
        result = ((result* 31)+((this.smsInfo == null)? 0 :this.smsInfo.hashCode()));
        result = ((result* 31)+((this.emailInfo == null)? 0 :this.emailInfo.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NotificationEventRequestType) == false) {
            return false;
        }
        NotificationEventRequestType rhs = ((NotificationEventRequestType) other);
        return ((((((this.customerId == rhs.customerId)||((this.customerId!= null)&&this.customerId.equals(rhs.customerId)))&&((this.notificationActivityType == rhs.notificationActivityType)||((this.notificationActivityType!= null)&&this.notificationActivityType.equals(rhs.notificationActivityType))))&&((this.notificationMethod == rhs.notificationMethod)||((this.notificationMethod!= null)&&this.notificationMethod.equals(rhs.notificationMethod))))&&((this.smsInfo == rhs.smsInfo)||((this.smsInfo!= null)&&this.smsInfo.equals(rhs.smsInfo))))&&((this.emailInfo == rhs.emailInfo)||((this.emailInfo!= null)&&this.emailInfo.equals(rhs.emailInfo))));
    }

}
