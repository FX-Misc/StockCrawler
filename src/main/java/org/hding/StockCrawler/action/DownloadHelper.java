package org.hding.StockCrawler.action;

import java.io.InputStream;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.config.RequestConfig;

public class DownloadHelper {
    private URI uri;
    private String key;
    //private InputStream is;
    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;
    private HttpEntity entity;
    public DownloadHelper(URI uri) {
        this.uri = uri;
        this.key = null;
    }
    public DownloadHelper(URI uri, String key) {
        this.uri = uri;
        this.key = key;
    }
    public InputStream getInputStream() throws Exception {
        httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        System.out.println(uri);
        RequestConfig requestConfig = RequestConfig
                                        .custom()
                                        .setSocketTimeout(30000)
                                        .setConnectTimeout(30000)
                                        .build();
        httpGet.setConfig(requestConfig);
        //DefaultHttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler(3, false); 
        //httpClient.setHttpRequestRetryHandler(handler); 
        response = httpClient.execute(httpGet);
        System.out.println(response.getStatusLine());
        if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
            //System.out.println("Connect Error");
            return null;
        }
        if (key != null) {
            Header header = response.getFirstHeader(key);
            if (header == null) { 
                //not valid date
                //System.out.println("Not valid date.");
                return null;
            }    
        }
        entity = response.getEntity();
        return entity.getContent();
    }
    public void close() throws Exception {
        //is.close();
        if (entity != null) {
            EntityUtils.consume(entity);
        }
        response.close();
        httpClient.close();  
    }
}