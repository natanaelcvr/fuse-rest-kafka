
package com.uscellular.service.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Information needed to send an email.
 * 
 */
@Generated("jsonschema2pojo")
public class EmailInfo {

    /**
     * The notification type identifies the specfic notification configuration type to be used when constructing the message to the customer.
     * 
     */
    @SerializedName("notificationType")
    @Expose
    private String notificationType;
    /**
     * Customer email to be sent notification to.
     * 
     */
    @SerializedName("recipientEmail")
    @Expose
    private String recipientEmail;
    /**
     * Sender email to send notification from.
     * 
     */
    @SerializedName("senderEmail")
    @Expose
    private String senderEmail;
    /**
     * Url containing the email template to be sent to the customer.
     * 
     */
    @SerializedName("templateUrl")
    @Expose
    private String templateUrl;
    /**
     * Text for the subject line of the email.
     * 
     */
    @SerializedName("emailSubject")
    @Expose
    private String emailSubject;
    @SerializedName("dynamicAttributes")
    @Expose
    private List<DynamicAttribute__1> dynamicAttributes = new ArrayList<DynamicAttribute__1>();

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
     * Customer email to be sent notification to.
     * 
     */
    public String getRecipientEmail() {
        return recipientEmail;
    }

    /**
     * Customer email to be sent notification to.
     * 
     */
    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    /**
     * Sender email to send notification from.
     * 
     */
    public String getSenderEmail() {
        return senderEmail;
    }

    /**
     * Sender email to send notification from.
     * 
     */
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    /**
     * Url containing the email template to be sent to the customer.
     * 
     */
    public String getTemplateUrl() {
        return templateUrl;
    }

    /**
     * Url containing the email template to be sent to the customer.
     * 
     */
    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    /**
     * Text for the subject line of the email.
     * 
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * Text for the subject line of the email.
     * 
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public List<DynamicAttribute__1> getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(List<DynamicAttribute__1> dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EmailInfo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("notificationType");
        sb.append('=');
        sb.append(((this.notificationType == null)?"<null>":this.notificationType));
        sb.append(',');
        sb.append("recipientEmail");
        sb.append('=');
        sb.append(((this.recipientEmail == null)?"<null>":this.recipientEmail));
        sb.append(',');
        sb.append("senderEmail");
        sb.append('=');
        sb.append(((this.senderEmail == null)?"<null>":this.senderEmail));
        sb.append(',');
        sb.append("templateUrl");
        sb.append('=');
        sb.append(((this.templateUrl == null)?"<null>":this.templateUrl));
        sb.append(',');
        sb.append("emailSubject");
        sb.append('=');
        sb.append(((this.emailSubject == null)?"<null>":this.emailSubject));
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
        result = ((result* 31)+((this.senderEmail == null)? 0 :this.senderEmail.hashCode()));
        result = ((result* 31)+((this.notificationType == null)? 0 :this.notificationType.hashCode()));
        result = ((result* 31)+((this.recipientEmail == null)? 0 :this.recipientEmail.hashCode()));
        result = ((result* 31)+((this.emailSubject == null)? 0 :this.emailSubject.hashCode()));
        result = ((result* 31)+((this.templateUrl == null)? 0 :this.templateUrl.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EmailInfo) == false) {
            return false;
        }
        EmailInfo rhs = ((EmailInfo) other);
        return (((((((this.dynamicAttributes == rhs.dynamicAttributes)||((this.dynamicAttributes!= null)&&this.dynamicAttributes.equals(rhs.dynamicAttributes)))&&((this.senderEmail == rhs.senderEmail)||((this.senderEmail!= null)&&this.senderEmail.equals(rhs.senderEmail))))&&((this.notificationType == rhs.notificationType)||((this.notificationType!= null)&&this.notificationType.equals(rhs.notificationType))))&&((this.recipientEmail == rhs.recipientEmail)||((this.recipientEmail!= null)&&this.recipientEmail.equals(rhs.recipientEmail))))&&((this.emailSubject == rhs.emailSubject)||((this.emailSubject!= null)&&this.emailSubject.equals(rhs.emailSubject))))&&((this.templateUrl == rhs.templateUrl)||((this.templateUrl!= null)&&this.templateUrl.equals(rhs.templateUrl))));
    }

}
