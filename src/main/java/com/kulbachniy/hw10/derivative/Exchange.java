package com.kulbachniy.hw10.derivative;

public enum Exchange {
    NYSE("n","New York Stock Exchange"),
    NASDAQ("q","National Association of Securities Dealers Automated Quotation"),
    AMEX("a","American Stock Exchange"),
    CME("c","Chicago Mercantile Exchange"),
    LSE("l","London Stock Exchange"),
    FX("f","Foreign exchange market");

    private String suffix;
    private String fullName;

    private Exchange(String suffix, String fullName){

        this.suffix = suffix;
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}
