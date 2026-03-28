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

public class PutStepController {

    @FXML
    private ComboBox<Long> tutorialId;
    @FXML
    private TextField tutorialTitle;
    @FXML
    private TextField title;
    @FXML
    private TextField text;
    @FXML
    private TextField number;

    private final TutorialService tutorialService = new TutorialService();

    private final StepService stepService = new StepService();

    private Step stepToUpdate;

    private List<Tutorial> allTutorials;

    public void setStep(Step step){
        this.stepToUpdate = step;
    }

    public void fillTutorialFields() throws Exception {
        try{
            allTutorials = tutorialService.getAllTutorials();

            for (Tutorial tutorial : allTutorials){
                tutorialId.getItems().add(tutorial.getId());
            }

            tutorialId.setValue(stepToUpdate.getTutorialId());


        } catch (Exception e) {
            System.err.println("error fetching data!");
        }

            tutorialTitle.setText(stepToUpdate.getTutorialTitle());
            title.setText(stepToUpdate.getTitle());
            text.setText(stepToUpdate.getText());
            number.setText(String.valueOf(stepToUpdate.getNumber()));
    }


    public void sendPutRequest(){
        try {
            Long.parseLong(number.getText());
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input");
            alert.setContentText("number must be a valid nummer!");

            alert.showAndWait();
            return;
        }

        try{

            Step changedStep = new Step();
            changedStep.setId(stepToUpdate.getId());
            changedStep.setTitle(title.getText());
            changedStep.setText(text.getText());
            changedStep.setNumber(Long.valueOf((number.getText())));
            changedStep.setTutorialId(tutorialId.getValue());

            stepService.update(changedStep);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("The step was successfully updated!");
            alert.setContentText(changedStep.getTitle() + " successfully save!");

            alert.showAndWait();
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("The step could not be updated!");
            alert.setContentText("Please check the input fields!");
        }
    }

    public void back() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/step.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        HelloApplication.getCentralStage().setTitle("Steps");
        HelloApplication.getCentralStage().setScene(scene);
        HelloApplication.getCentralStage().show();
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
