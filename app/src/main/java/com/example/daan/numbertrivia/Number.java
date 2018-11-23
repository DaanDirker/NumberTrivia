package com.example.daan.numbertrivia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Number {

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("number")
    @Expose
    private Integer number;

    @SerializedName("found")
    @Expose
    private Boolean found;

    @SerializedName("type")
    @Expose
    private String type;

    public String getText() {
        return text;
    }

    public Integer getNumber() {
        return number;
    }

    public Boolean getFound() {
        return found;
    }

    public String getType() {
        return type;
    }
}
