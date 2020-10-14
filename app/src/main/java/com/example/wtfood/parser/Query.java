package com.example.wtfood.parser;

public class Query {
    String CompareAttribute;
    String sign;
    String Value;

    public void setCompareAttribute(String compareAttribute) {
        CompareAttribute = compareAttribute;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCompareAttribute() {
        return CompareAttribute;
    }

    public String getSign() {
        return sign;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return CompareAttribute + sign + Value;
    }

    public Query(String compareAttribute, String sign, String value) {
        CompareAttribute = compareAttribute;
        this.sign = sign;
        Value = value;
    }
}
