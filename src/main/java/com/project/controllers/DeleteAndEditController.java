package com.project.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteAndEditController {

    public static boolean needDelete = false;
    private static String message;

    @FXML
    private Label outputlabel;

    public DeleteAndEditController(){
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @FXML
    private void initialize(){
        outputlabel.setText(message);
        outputlabel.setWrapText(true);
    }

    @FXML
    private void clickYes(){
        Stage stage = (Stage) outputlabel.getScene().getWindow();
        stage.close();
        needDelete = true;
    }

    @FXML
    private void clickNo(){
        Stage stage = (Stage) outputlabel.getScene().getWindow();
        stage.close();
        needDelete = false;
    }

}
