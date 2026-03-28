package com.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Step;
import models.Tutorial;
import services.StepService;
import services.TutorialService;

import java.io.IOException;
import java.util.List;

public class CreateStepController {

    @FXML
    private ComboBox<Long> tutorialId;
    @FXML
    private TextField tutorialTitle;
    @FXML
    private TextField stepTitle;
    @FXML
    private TextField stepText;
    @FXML
    private TextField stepNumber;

    private final StepService stepService = new StepService();

    private final TutorialService tutorialService = new TutorialService();

    private List<Tutorial> allTutorials;

    @FXML
    public void initialize() throws Exception {
        try{
            allTutorials = tutorialService.getAllTutorials();

            for (Tutorial tutorial : allTutorials){
                tutorialId.getItems().add(tutorial.getId());
            }
        } catch (Exception e) {
            System.err.println("error fetching data!");
        }
    }

    public void back() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/step.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        HelloApplication.getCentralStage().setTitle("Hello!");
        HelloApplication.getCentralStage().setScene(scene);
        HelloApplication.getCentralStage().show();
    }

    public void sendPostRequest() {
        if (tutorialId.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("choose tutorial!");
            alert.setContentText("you must choose a tutorial!");

            alert.showAndWait();
            return;
        }

        try{
            Long.parseLong(stepNumber.getText());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input");
            alert.setContentText("number must be a valid nummer!");

            alert.showAndWait();
            return;
        }

        try {
            Step newStep = new Step();
            newStep.setTitle(stepTitle.getText());
            newStep.setText(stepText.getText());
            newStep.setNumber(Long.parseLong(stepNumber.getText()));
            newStep.setTutorialId(tutorialId.getValue());

            stepService.create(newStep);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("The step was successfully created!");
            alert.setContentText(newStep.getTitle() + " successfully save!");

            alert.showAndWait();
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("The step could not be created!");
            alert.setContentText("Please check the input fields!");
        }
    }

    public void tutorialSelection() {
        Long selectedId = tutorialId.getValue();

        for (Tutorial tutorial : allTutorials) {
            if (tutorial.getId() == selectedId) {
                tutorialTitle.setText(tutorial.getTitle());
                break;
            }
        }
    }
}
