package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Tutorial;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;


public class TutorialService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<Tutorial> getAllTutorials() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/tutorials"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Tutorial[] tutorialArray =
                objectMapper.readValue(response.body(), Tutorial[].class);

        for (Tutorial tutorial : tutorialArray) {
            System.out.println("Tutorial id: " + tutorial.getId());
            System.out.println("Tutorial title: " + tutorial.getTitle());
            System.out.println("Tutorial description: " + tutorial.getDescription());
            System.out.println("Tutorial published: " + tutorial.isPublished());
        }
        return Arrays.asList(tutorialArray);
    }


   public void create (Tutorial tutorial) throws Exception {
       String jsonBody = objectMapper.writeValueAsString(tutorial);

        HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("http://localhost:8082/api/tutorials"))
               .header("Content-Type", "application/json")
               .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

       HttpResponse<String> response =
               client.send(request, HttpResponse.BodyHandlers.ofString());
   }

    // DELETE a tutorial
    public void delete(Tutorial tutorial) throws Exception{
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/tutorials/" + tutorial.getId()))
                .DELETE()
                .build();

        HttpResponse<String> response =
                client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());


    }

    //update a tutorial
    public void update(Tutorial tutorial) throws Exception{
        String jsonBody = objectMapper.writeValueAsString(tutorial);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/tutorials/" + tutorial.getId()))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<Tutorial> getById(long id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/api/tutorials/" + id))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            System.out.println("Tutorial not found! Status " + response.statusCode());
            return List.of();
        }

        Tutorial tutorial =
                objectMapper.readValue(response.body(), Tutorial.class);

            System.out.println("Tutorial id: " + tutorial.getId());
            System.out.println("Tutorial title: " + tutorial.getTitle());
            System.out.println("Tutorial description: " + tutorial.getDescription());
            System.out.println("Tutorial published: " + tutorial.isPublished());

        return Arrays.asList(tutorial);
    }

}