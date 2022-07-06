package com.brenner.savingsgoals.controller.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class AbstractServiceManager {
    
    private static final String BASE_URL = "http://localhost:9001/api";
    
    protected String doPost(String apiTarget, String json) throws IOException {
        
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(BASE_URL + apiTarget);
            StringEntity entity = new StringEntity(json);
            entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            String result = readResponseToString(response);
            return result;
        }
    }
    
    protected String doGet(String apiTarget) throws IOException {
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            HttpResponse response = client.execute(new HttpGet(BASE_URL + apiTarget));
            String result = readResponseToString(response);
            return result;
        }
    }
    
    protected String doPut(String apiTarget, String json) throws IOException {
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            final HttpPut post = new HttpPut(BASE_URL + apiTarget);
            StringEntity entity = new StringEntity(json);
            entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            post.setEntity(entity);
            CloseableHttpResponse response = client.execute(post);
            return readResponseToString(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected int doDelete(String apiTarget, String json) throws IOException {
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            final HttpDelete delete = new HttpDelete(BASE_URL + apiTarget);
            HttpResponse response = client.execute(delete);
            return response.getStatusLine().getStatusCode();
        }
    }
    
    private String readResponseToString(HttpResponse response) throws IOException {
        
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
    
            return builder.toString();
        }
    }
}
