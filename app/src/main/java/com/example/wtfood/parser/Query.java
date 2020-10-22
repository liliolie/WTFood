package com.example.wtfood.parser;

public class Query {
    // This class is for storing information from input string. Create by Yen Kuo.

    String compareAttribute;
    String operator;
    String value;



    public String getCompareAttribute() {
        return compareAttribute;
    }

    public String getSign() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return compareAttribute + operator + value;
    }


    /**
     * a constructor to construct a query with given name
     * @param compareAttribute the attribute which parser read.
     * @param sign the operator which parser read.
     * @param value the value which parser read.
     */
    public Query(String compareAttribute, String sign, String value) {
        this.compareAttribute = compareAttribute;
        this.operator = sign;
        this.value = value;
    }
}
