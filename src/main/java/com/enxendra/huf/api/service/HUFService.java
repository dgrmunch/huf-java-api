package com.enxendra.huf.api.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.enxendra.huf.api.Constants;
import com.enxendra.huf.api.RequestMethod;
import com.enxendra.huf.api.RequestOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class HUFService {

    protected RequestOptions requestOptions;
    protected String urlBase;

    public RequestOptions getRequestOptions() {
        return requestOptions;
    }

    public void setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    protected JsonObject callService(String path, RequestMethod requestMethod) throws Exception {
        return callService(path, requestMethod, null, null);
    }

    protected JsonObject callService(String path, RequestMethod requestMethod, Map<String, Object> queryParams)
            throws Exception {
        return callService(path, requestMethod, queryParams, null);
    }

    protected JsonObject callService(String path, RequestMethod requestMethod, JsonObject body) throws Exception {
        return callService(path, requestMethod, null, body);
    }

    protected JsonObject callService(String path, RequestMethod requestMethod, Map<String, Object> queryParams,
            JsonObject body) throws Exception {

        JsonReader reader;
        JsonParser parser = new JsonParser();
        String endpoint = this.urlBase + path;

        // If there are queryParams and it is a GET request
        if (queryParams != null && queryParams.size() > 0 && requestMethod.equals(RequestMethod.GET))
            endpoint = this.urlBase + path + getQuery(queryParams);

        System.out.println(requestMethod.name() + " request to: " + endpoint);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(endpoint).openConnection();

        // Add header
        System.out.println(Constants.CONTENT_TYPE + ": " + Constants.APP_JSON);
        System.out.println(Constants.AUTH + ": " + Constants.BASIC + " " + requestOptions.getApiKey());

        urlConnection.setRequestProperty(Constants.CONTENT_TYPE, Constants.APP_JSON);
        urlConnection.setRequestProperty(Constants.AUTH, Constants.BASIC + " " + requestOptions.getApiKey());
        urlConnection.setRequestMethod(requestMethod.name());

        // Add body (only if there is a body JsonObject and it is not a GET request)
        if (body != null && !requestMethod.equals(RequestMethod.GET)) {
            urlConnection.setDoOutput(true);
            System.out.println("Body: " + body.toString());
            OutputStream os = urlConnection.getOutputStream();
            os.write(body.toString().getBytes());
            os.flush();
        }

        urlConnection.connect();

        try {
            reader = new JsonReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException e) {
            reader = new JsonReader(new InputStreamReader(urlConnection.getErrorStream()));
        }

        System.out.println("Response code: " + urlConnection.getResponseCode());
        System.out.println("Response message: " + urlConnection.getResponseMessage());
        JsonElement rootElement = parser.parse(reader);

        return rootElement.getAsJsonObject();

    }

    private String getQuery(Map<String, Object> params) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8")); // TODO: Review encoding
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }

        return "?" + result.toString();

    }
}