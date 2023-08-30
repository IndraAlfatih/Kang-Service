package com.airun.bigboss.kangservice;

public class RequestAndroid {

    public String merk;
    public String versi;
    public String url;
    public String kerusakan;

    public String getMerk() {
        return merk;
    }

    public String getUrl() {
        return url;
    }

    public String getVersi() { return versi;}

    public String getKerusakan() { return kerusakan;}

    public RequestAndroid(String merk, String versi, String url, String kerusakan) {
        this.merk = merk;
        this.versi = versi;
        this.url = url;
        this.kerusakan = kerusakan;
    }

    public RequestAndroid(){}
}
