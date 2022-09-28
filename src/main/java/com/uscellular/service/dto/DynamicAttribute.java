
package com.uscellular.service.dto;

import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DynamicAttribute {

    /**
     * Name of the dynamic attribute
     * 
     */
    @SerializedName("key")
    @Expose
    private String key;
    /**
     * Value of dynamic attribute
     * 
     */
    @SerializedName("value")
    @Expose
    private String value;
    /**
     * Description of values to be replaced in templates.
     * 
     */
    @SerializedName("purpose")
    @Expose
    private String purpose;

    /**
     * Name of the dynamic attribute
     * 
     */
    public String getKey() {
        return key;
    }

    /**
     * Name of the dynamic attribute
     * 
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Value of dynamic attribute
     * 
     */
    public String getValue() {
        return value;
    }

    /**
     * Value of dynamic attribute
     * 
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Description of values to be replaced in templates.
     * 
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Description of values to be replaced in templates.
     * 
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DynamicAttribute.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("key");
        sb.append('=');
        sb.append(((this.key == null)?"<null>":this.key));
        sb.append(',');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
        sb.append(',');
        sb.append("purpose");
        sb.append('=');
        sb.append(((this.purpose == null)?"<null>":this.purpose));
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
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        result = ((result* 31)+((this.purpose == null)? 0 :this.purpose.hashCode()));
        result = ((result* 31)+((this.key == null)? 0 :this.key.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DynamicAttribute) == false) {
            return false;
        }
        DynamicAttribute rhs = ((DynamicAttribute) other);
        return ((((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value)))&&((this.purpose == rhs.purpose)||((this.purpose!= null)&&this.purpose.equals(rhs.purpose))))&&((this.key == rhs.key)||((this.key!= null)&&this.key.equals(rhs.key))));
    }

}
