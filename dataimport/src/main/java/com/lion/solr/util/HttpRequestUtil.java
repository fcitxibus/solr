package com.lion.solr.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpRequestUtil {
    private HttpRequestUtil(){}
    /**
     * 进行HTTP访问地址的发送，该地址发送之后不需要进行返回内容处理
     * @param address 要发送的请求地址
     * @return 发送成功返回true
     */
    public static boolean send(String address) {
        System.out.println("｛访问地址｝" + address);
        CloseableHttpClient httpClient = HttpClients.createDefault() ;
        HttpPost post = new HttpPost(address) ; // 构建post请求
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            return response.getStatusLine().getStatusCode() == 200 ;
        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
    }
}