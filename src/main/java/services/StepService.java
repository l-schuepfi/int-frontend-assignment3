package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Step;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class StepService {


    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<Step> getAllSteps() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/steps"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Step[] stepArray =
                objectMapper.readValue(response.body(), Step[].class);

//        for (Step step : stepArray) {
////            System.out.println("Tutorial id: " + tutorial.getId());
////            System.out.println("Tutorial title: " + tutorial.getTitle());
////            System.out.println("Tutorial description: " + tutorial.getDescription());
////            System.out.println("Tutorial published: " + tutorial.isPublished());
//        }
        return Arrays.asList(stepArray);
    }


    public void create (Step step) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(step);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/steps"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // DELETE a step
    public void delete(Step step) throws Exception{
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/steps/" + step.getId()))
                .DELETE()
                .build();

        HttpResponse<String> response =
                client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());


    }

    //update a step
    public void update(Step step) throws Exception{
        String jsonBody = objectMapper.writeValueAsString(step);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/steps/" + step.getId()))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<Step> getById(long id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/steps/" + id))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            System.out.println("Step not found! Status " + response.statusCode());
            return List.of();
        }

        Step[] stepsArray =
                objectMapper.readValue(response.body(), Step[].class);

//        System.out.println("Tutorial id: " + tutorial.getId());
//        System.out.println("Tutorial title: " + tutorial.getTitle());
//        System.out.println("Tutorial description: " + tutorial.getDescription());
//        System.out.println("Tutorial published: " + tutorial.isPublished());

        return Arrays.asList(stepsArray);
    }

}
