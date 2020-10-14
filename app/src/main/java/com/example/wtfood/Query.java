package com.example.wtfood;

public class Query {
    String CompareAttribute;
    String sign;
    int Value;

    public void setCompareAttribute(String compareAttribute) {
        CompareAttribute = compareAttribute;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setValue(int value) {
        Value = value;
    }

    @Override
    public String toString() {
        return CompareAttribute + sign + Value;
    }

    public Query(String compareAttribute, String sign, int value) {
        CompareAttribute = compareAttribute;
        this.sign = sign;
        Value = value;
    }
}
