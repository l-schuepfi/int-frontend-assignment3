package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Tutorial;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class TutorialService extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void start(Stage primaryStage) {

    }

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
}