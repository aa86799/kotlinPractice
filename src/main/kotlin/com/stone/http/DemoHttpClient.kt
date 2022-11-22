package com.stone.http

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient

class DemoHttpClient {

    fun test() {
        val client = DefaultHttpClient()
        client.addRequestInterceptor(ApacheInterceptor())
    }
}