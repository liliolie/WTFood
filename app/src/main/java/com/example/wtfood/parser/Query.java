package com.example.wtfood.parser;

public class Query {
    String compareAttribute;
    String sign;
    String value;

    public void setCompareAttribute(String compareAttribute) {
        this.compareAttribute = compareAttribute;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCompareAttribute() {
        return compareAttribute;
    }

    public String getSign() {
        return sign;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return compareAttribute + sign + value;
    }

    public Query(String compareAttribute, String sign, String value) {
        this.compareAttribute = compareAttribute;
        this.sign = sign;
        this.value = value;
    }
}
