package com.example.shopee.retrofit2;
public class APIUtils {
    public  static final String Base_Url="https://muabanre.net/";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}