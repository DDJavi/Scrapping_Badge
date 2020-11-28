package com.borjamoll.scrapping.data;


public class Search {
    private String key;
    private boolean isPrime;


    public Search(String key, boolean isPrime) {
        this.key = key;
        this.isPrime = isPrime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isPrime() {
        return isPrime;
    }

    public void setPrime(boolean prime) {
        isPrime = prime;
    }


}
