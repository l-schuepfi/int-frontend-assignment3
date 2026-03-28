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
import models.Tutorial;
import services.TutorialService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class HelloController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField tutorialId;

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

        try {

            List<Tutorial> tutorials = tutorialService.getAllTutorials();

            ObservableList<Tutorial> tutorialList =
                    FXCollections.observableArrayList(tutorials);

            tutorialTable.setItems(tutorialList);

        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText("Error fetching tutorial data!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        publColumn.setCellValueFactory(new PropertyValueFactory<>("published"));

        tutorialTable.setRowFactory(
                new Callback<TableView<Tutorial>, TableRow<Tutorial>>() {
                    @Override
                    public TableRow<Tutorial> call(TableView<Tutorial> tableView) {
                        final TableRow<Tutorial> row = new TableRow<>();
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem editItem = new MenuItem("Edit");
                        editItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Tutorial tutorial = row.getItem();
                                openUpdateView(tutorial);
                            }
                        });
                        MenuItem removeItem = new MenuItem("Delete");
                        removeItem.setOnAction(new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    Tutorial tutorial = row.getItem();
                                    tutorialTable.getItems().remove(tutorial);
                                    tutorialService.delete(tutorial);
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

    private void openUpdateView(Tutorial tutorial) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("/com/example/views/updateTutorial.fxml"));
        try {
            Parent parent = (AnchorPane) loader.load();
            Scene scene = new Scene(parent);
            HelloApplication.getCentralStage().setScene(scene);
            PutTutorialController controller = loader.getController();
            controller.setTutorial(tutorial);
            HelloApplication.getCentralStage().show();
            controller.fillTutorialFields();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void getTutorialById(){
        String input = tutorialId.getText();

        try {
            long id = Long.parseLong(input);

            List<Tutorial> result = tutorialService.getById(Long.parseLong(String.valueOf(id)));

            ObservableList<Tutorial> tutorialList =
                    FXCollections.observableArrayList(result);

            tutorialTable.setItems(tutorialList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void OpenCreateView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/createTutorial.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        HelloApplication.getCentralStage().setTitle("Create Tutorial");
        HelloApplication.getCentralStage().setScene(scene);
        HelloApplication.getCentralStage().show();
    }

    public void OpenStepView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/step.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        HelloApplication.getCentralStage().setTitle("Step");
        HelloApplication.getCentralStage().setScene(scene);
        HelloApplication.getCentralStage().show();
    }
}
