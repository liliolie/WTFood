package com.example.wtfood.parser;

public class Query {
    // This class is for storing information from input string. Create by Yen Kuo.

    String compareAttribute;
    String operator;
    String value;

    public void setCompareAttribute(String compareAttribute) {
        this.compareAttribute = compareAttribute;
    }

    public void setSign(String sign) {
        this.operator = sign;
    }

    public String getCompareAttribute() {
        return compareAttribute;
    }

    public String getSign() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return compareAttribute + operator + value;
    }

    public Query(String compareAttribute, String sign, String value) {
        this.compareAttribute = compareAttribute;
        this.operator = sign;
        this.value = value;
    }
}
