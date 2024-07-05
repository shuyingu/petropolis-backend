package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.service.GPTService;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class GPTServiceImpl implements GPTService {

    private final OkHttpClient httpClient = new OkHttpClient();
    @Value("${openai.api.key}")
    private String apiKey;
    @Value("${openai.api.url}")
    private String apiUrl;
    @Value("${openai.api.modelType}")
    private String modelType;

    @Override
    @Async
    public CompletableFuture<String> callOpenAi(String prompt) {
        JSONObject json = new JSONObject();
        json.put("model", modelType);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonEnt = new JSONObject();
        jsonEnt.put("role","user");
        jsonEnt.put("content", prompt);
        jsonArray.put(jsonEnt);
        json.put("messages", jsonArray);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                json.toString()
        );

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return CompletableFuture.completedFuture(parseResponseContent(response.body().string()));
            } else {
                return CompletableFuture.completedFuture("Error: " + response.message() + json.toString());
            }
        } catch (IOException e) {
            return CompletableFuture.completedFuture("Error: " + e.getMessage());
        }
    }

    private String parseResponseContent(String responseBody) {
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray choices = jsonResponse.getJSONArray("choices");
        if (!choices.isEmpty()) {
            JSONObject choice = choices.getJSONObject(0);
            JSONObject message = choice.getJSONObject("message");
            return message.getString("content");
        }
        return "Error";
    }
}