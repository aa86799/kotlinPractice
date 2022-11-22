package com.stone.http;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class ApacheInterceptor2 implements HttpRequestInterceptor {

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
//        println("${request?.getHeaders()?.}")
//            request?.requestLine?.uri?.let {
//            URLEncodedUtils.parse(URI(it), HTTP.UTF_8)?.forEach {
//            it.name.plus(":").plus(it.value)
//            }
//            }
//            }

        if (request == null || request.getRequestLine() == null || request.getRequestLine().getUri() == null) return;
        String uriStr = request.getRequestLine().getUri();
        System.out.println(uriStr);
//        try {
//            List<NameValuePair> pairs = URLEncodedUtils.parse(new URI(uriStr), HTTP.UTF_8);
//            for (NameValuePair pair : pairs) {
//                pair.getName()
//            }
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
    }
}