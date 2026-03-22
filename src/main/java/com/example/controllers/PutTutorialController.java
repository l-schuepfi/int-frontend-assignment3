package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.Tutorial;
import services.TutorialService;

import java.io.IOException;

public class PutTutorialController {

        @FXML
        private TextField tutorialTitle;
        @FXML
        private TextField tutorialDescription;
        @FXML
        private TextField tutorialPublished;

        private Tutorial tutorialToUpdate;
        private final TutorialService tutorialService = new TutorialService();

        public void setTutorial(Tutorial tutorial){
        this.tutorialToUpdate = tutorial;
        }

        public void fillTutorialFields(){
        tutorialTitle.setText(tutorialToUpdate.getTitle());
        tutorialDescription.setText(tutorialToUpdate.getDescription());
            if (tutorialToUpdate.isPublished()){
                tutorialPublished.setText("true");
            } else {
                tutorialPublished.setText("false");
            }
        }


        public void sendPutRequest(){

                if(!tutorialPublished.getText().equals("true") && !tutorialPublished.getText().equals("false")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid input");
                    alert.setHeaderText("Wrong published status");
                    alert.setContentText("must be true or false");
                    alert.showAndWait();
                    return;
                }
            try {
                Tutorial newTutorial = new Tutorial();
                newTutorial.setId(tutorialToUpdate.getId());
                newTutorial.setTitle(tutorialTitle.getText());
                newTutorial.setDescription(tutorialDescription.getText());
                newTutorial.setPublished(Boolean.parseBoolean(tutorialPublished.getText()));

                tutorialService.update(newTutorial);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("The tutorial was successfully updated!");
                alert.setContentText(newTutorial.getTitle() + " successfully save!");

                alert.showAndWait();
            }
            catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("The tutorial could not be updated!");
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



