package com.star.truffle.core.okhttp;

import okhttp3.OkHttpClient;

public class StarSslOkHttp {

  private OkHttpClient okHttpClient;

  public StarSslOkHttp(OkHttpClient okHttpClient){
    this.okHttpClient = okHttpClient;
  }

  public OkHttpClient okHttpClient(){
    return this.okHttpClient;
  }

}
