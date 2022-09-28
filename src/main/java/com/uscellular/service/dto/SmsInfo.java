
package com.uscellular.service.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Information needed to send a SMS.
 * 
 */
@Generated("jsonschema2pojo")
public class SmsInfo {

    /**
     * The notification type identifies the specfic notification configuration type to be used when constructing the message to the customer.
     * 
     */
    @SerializedName("notificationType")
    @Expose
    private String notificationType;
    /**
     * Customer MDN to be sent notification to.
     * 
     */
    @SerializedName("recipientMDN")
    @Expose
    private String recipientMDN;
    @SerializedName("dynamicAttributes")
    @Expose
    private List<DynamicAttribute> dynamicAttributes = new ArrayList<DynamicAttribute>();

    /**
     * The notification type identifies the specfic notification configuration type to be used when constructing the message to the customer.
     * 
     */
    public String getNotificationType() {
        return notificationType;
    }

    /**
     * The notification type identifies the specfic notification configuration type to be used when constructing the message to the customer.
     * 
     */
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * Customer MDN to be sent notification to.
     * 
     */
    public String getRecipientMDN() {
        return recipientMDN;
    }

    /**
     * Customer MDN to be sent notification to.
     * 
     */
    public void setRecipientMDN(String recipientMDN) {
        this.recipientMDN = recipientMDN;
    }

    public List<DynamicAttribute> getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(List<DynamicAttribute> dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SmsInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("notificationType");
        sb.append('=');
        sb.append(((this.notificationType == null)?"<null>":this.notificationType));
        sb.append(',');
        sb.append("recipientMDN");
        sb.append('=');
        sb.append(((this.recipientMDN == null)?"<null>":this.recipientMDN));
        sb.append(',');
        sb.append("dynamicAttributes");
        sb.append('=');
        sb.append(((this.dynamicAttributes == null)?"<null>":this.dynamicAttributes));
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
        result = ((result* 31)+((this.dynamicAttributes == null)? 0 :this.dynamicAttributes.hashCode()));
        result = ((result* 31)+((this.notificationType == null)? 0 :this.notificationType.hashCode()));
        result = ((result* 31)+((this.recipientMDN == null)? 0 :this.recipientMDN.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SmsInfo) == false) {
            return false;
        }
        SmsInfo rhs = ((SmsInfo) other);
        return ((((this.dynamicAttributes == rhs.dynamicAttributes)||((this.dynamicAttributes!= null)&&this.dynamicAttributes.equals(rhs.dynamicAttributes)))&&((this.notificationType == rhs.notificationType)||((this.notificationType!= null)&&this.notificationType.equals(rhs.notificationType))))&&((this.recipientMDN == rhs.recipientMDN)||((this.recipientMDN!= null)&&this.recipientMDN.equals(rhs.recipientMDN))));
    }

}
