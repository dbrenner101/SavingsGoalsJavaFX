package com.brenner.savingsgoals.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Abstract parent object of the service classes. This centralizes and abstracts the HttpClient calls.
 *
 * @TODO inject the base url rather than hardcoded
 */
public abstract class AbstractServiceManager {
    /** Base url for all calls. Assumes all calls go to the same endpoint. */
    private static final String BASE_URL = "http://localhost:9001/api";
    
    /**
     * Executes the HttpClient.doPost call and returns the response as a String.
     *
     * @param apiTarget The api endpoint path
     * @param json The object to post in JSON format
     * @return The json formatted response
     * @throws IOException Framework exception
     */
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
    
    /**
     * Executes the HttpClient.doGet call and returns the response as a String.
     *
     * @param apiTarget The api endpoint path
     * @returnThe json formatted response
     * @throws IOException Framework exception
     */
    protected String doGet(String apiTarget) throws IOException {
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            HttpResponse response = client.execute(new HttpGet(BASE_URL + apiTarget));
            String result = readResponseToString(response);
            return result;
        }
    }
    
    /**
     * Executes the HttpClient.doPut call and returns the response as a String.
     *
     * @param apiTarget The api endpoint path
     * @param json The object to post in JSON format
     * @return The json formatted response
     * @throws IOException Framework exception
     */
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
    
    /**
     * Executes the HttpClient.doDelete call and returns the status result.
     *
     * @param apiTarget The api endpoint path
     * @param json Ignored, here for future use
     * @return The Http Status Code
     * @throws IOException Framework exception
     */
    protected int doDelete(String apiTarget, String json) throws IOException {
        try (final CloseableHttpClient client = HttpClients.createDefault()) {
            final HttpDelete delete = new HttpDelete(BASE_URL + apiTarget);
            HttpResponse response = client.execute(delete);
            return response.getStatusLine().getStatusCode();
        }
    }
    
    /**
     * Helper method to convert the HttpClient response stream to a String
     *
     * @param response The output stream from the Http response
     * @return The stream captured in a string
     * @throws IOException Framework exception
     */
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
