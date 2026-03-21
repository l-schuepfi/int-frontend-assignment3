package com.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Tutorial;
import services.TutorialService;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class HelloController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Tutorial> tutorialTable;

    @FXML
    private TableColumn<Tutorial, String> idColumn;

    @FXML
    private TableColumn<Tutorial, String> titleColumn;

    @FXML
    private TableColumn<Tutorial, String> descColumn;

    @FXML
    private TableColumn<Tutorial, String> publColumn;

    private final TutorialService tutorialService = new TutorialService();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        try {

            List<Tutorial> tutorials = tutorialService.getAllTutorials();

            ObservableList<Tutorial> hardwareList =
                    FXCollections.observableArrayList(tutorials);

            tutorialTable.setItems(hardwareList);

        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText("Error fetching hardware data!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
        publColumn.setCellValueFactory(new PropertyValueFactory<>("publ"));
    }
}
