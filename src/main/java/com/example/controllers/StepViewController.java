package com.example.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.Step;
import services.StepService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StepViewController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField tutorialId;

    @FXML
    private TableView<Step> stepTable;

    @FXML
    private TableColumn<Step, String> idColumn;

    @FXML
    private TableColumn<Step, String> titleColumn;

    @FXML
    private TableColumn<Step, String> textColumn;

    @FXML
    private TableColumn<Step, String> numberColumn;

    @FXML
    private TableColumn<Step, String> tutorialIdColumn;

    @FXML
    private TableColumn<Step, String> tutorialTitleColumn;

    private final StepService stepService = new StepService();

    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        tutorialIdColumn.setCellValueFactory(new PropertyValueFactory<>("tutorialId"));
        tutorialTitleColumn.setCellValueFactory(new PropertyValueFactory<>("tutorialTitle"));

        stepTable.setRowFactory(
                new Callback<TableView<Step>, TableRow<Step>>() {
                    @Override
                    public TableRow<Step> call(TableView<Step> tableView) {
                        final TableRow<Step> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem editItem = new MenuItem("Edit");
                        editItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Step step = row.getItem();
                                openUpdateView(step);
                            }
                        });
                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    Step step = row.getItem();
                                    stepTable.getItems().remove(step);
                                    stepService.delete(step);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    //alert
                                }
                            }
                        });
                        rowMenu.getItems().addAll(editItem, removeItem);

                        // only display context menu for non-empty rows:
                        row.contextMenuProperty().bind(
                                Bindings.when(row.emptyProperty())
                                        .then((ContextMenu) null)
                                        .otherwise(rowMenu));
                        return row;
                    }
                });


    }

    private void openUpdateView(Step step) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("/com/example/views/updateStep.fxml"));
        try {
            Parent parent = (AnchorPane) loader.load();
            Scene scene = new Scene(parent);
            HelloApplication.getCentralStage().setScene(scene);
            PutStepController controller = loader.getController();
            controller.setStep(step);
            HelloApplication.getCentralStage().show();
            controller.fillTutorialFields();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void GetAllSteps() {

        try {

            List<Step> steps = stepService.getAllSteps();

            ObservableList<Step> stepList =
                    FXCollections.observableArrayList(steps);

            stepTable.setItems(stepList);

        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText("Error fetching step data!");
        }
    }

    public void getStepsByTutorialId()  {

        String input = tutorialId.getText();

        try {
            long id = Long.parseLong(input);

            List<Step> result = stepService.getById(Long.parseLong(String.valueOf(id)));

            ObservableList<Step> stepList =
                    FXCollections.observableArrayList(result);

            stepTable.setItems(stepList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void OpenHelloView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        HelloApplication.getCentralStage().setTitle("Hello!");
        HelloApplication.getCentralStage().setScene(scene);
        HelloApplication.getCentralStage().show();
    }

    public void OpenCreateStepView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/createStep.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        HelloApplication.getCentralStage().setTitle("Create Step");
        HelloApplication.getCentralStage().setScene(scene);
        HelloApplication.getCentralStage().show();
    }

}
