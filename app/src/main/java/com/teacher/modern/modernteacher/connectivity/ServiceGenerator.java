package com.teacher.modern.modernteacher.connectivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teacher.modern.modernteacher.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {
   // private static final String baseUrl = "http://202.164.211.78:2018/api/";
    //private static final String baseUrl = "http://192.168.0.26:2018/api/";
//    private static final String baseUrl = "http://202.164.211.78:4002/api/";
    //private static final String baseUrl = "http://amsnew.ambitioussoftwares.com/api/";
    //private static final String baseUrl = "http://amsnew.ambitioussoftwares.com/api/";
    //public static final String websiteUrl = "http://amsnew.ambitioussoftwares.com/";
    //public static final String websiteUrl = "http://amsnew.ambitioussoftwares.com";

   //public static final String websiteUrl = "http://amsnew.ambitioussoftwares.com/";

   private static final String baseUrl = "http://modern.ambitioussoftwares.com/api/";
   public static final String websiteUrl = "http://modern.ambitioussoftwares.com";

    /*private static final String baseUrl = "http://mamata.ambitioussoftwares.com/api/";
    public static final String websiteUrl = "http://mamata.ambitioussoftwares.com";*/

//    private static final String baseUrl = "http://polygon.ambitioussoftwares.com/api/";
//    public static final String websiteUrl = "http://polygon.ambitioussoftwares.com";

   // private static final String baseUrl = "http://sikdar.ambitioussoftwares.com/api/";
    //public static final String websiteUrl = "http://sikdar.ambitioussoftwares.com";

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson));
    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()

            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS);



    public static <S> S createService(Class<S> serviceClass) {

        if(BuildConfig.DEBUG) {
            if (!httpClient.interceptors().contains(logging)) {

                httpClient.addInterceptor(logging);
                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        } else {
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
}
