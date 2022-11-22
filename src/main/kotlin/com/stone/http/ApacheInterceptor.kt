package com.stone.http

import org.apache.http.HttpException
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.protocol.HTTP
import org.apache.http.protocol.HttpContext
import java.io.IOException
import java.net.URI

class ApacheInterceptor: HttpRequestInterceptor {

    @Throws(HttpException::class, IOException::class)
    override fun process(request: HttpRequest?, context: HttpContext?) {
//        println("${request?.getHeaders()?.}")
        request?.requestLine?.uri?.let {
            URLEncodedUtils.parse(URI(it), HTTP.UTF_8)?.forEach {
                it.name.plus(":").plus(it.value)
            }
        }
    }
}