package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Tutorial;
import services.TutorialService;

import java.io.IOException;

public class CreateTutorialController {

    @FXML
    private TextField tutorialTitle;
    @FXML
    private TextField tutorialDescription;

    private final TutorialService tutorialService = new TutorialService();

    public void sendPostRequest(){

        try {
            Tutorial newTutorial = new Tutorial();
            newTutorial.setTitle(tutorialTitle.getText());
            newTutorial.setDescription(tutorialDescription.getText());

            tutorialService.create(newTutorial);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("The tutorial was successfully created!");
            alert.setContentText(newTutorial.getTitle() + " successfully save!");

            alert.showAndWait();
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("The tutorial could not be created!");
            alert.setContentText("Please check the input fields!");
        }
    }


    public void back() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        HelloApplication.getCentralStage().setTitle("Hello!");
        HelloApplication.getCentralStage().setScene(scene);
        HelloApplication.getCentralStage().show();
    }
}
