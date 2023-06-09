package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import com.project.classesForTables.ResponsibilityGroup;
import com.project.dbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponsibilityGroupController {

    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private TextField findField;

    @FXML
    private TextField violationField;

    @FXML
    private ImageView homeImage;

    @FXML
    private TableColumn<ResponsibilityGroup, Integer> idColumn;

    @FXML
    private TableColumn<ResponsibilityGroup, String> groupColumn;

    @FXML
    private TableView<ResponsibilityGroup> groupTable;

    public ResponsibilityGroupController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        loadTable();
    }

    @FXML
    public void clickOnImageBack() {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    public void clickOnImageHome() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Menu.fxml");
    }

    @FXML
    private void add() throws SQLException, IOException {
        if (violationField.getText().replace(" ", "").isEmpty()) {
            exeptionScene.createExeptionScene("Не было заполнено поле для добавления.");
        } else {
            String addBrand = "INSERT INTO responsibility_group(name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addBrand);
            preparedStatement.setString(1, violationField.getText());
            preparedStatement.executeUpdate();
            loadTable();
            violationField.clear();
        }
    }

    @FXML
    private int delete() throws SQLException, IOException {
        if (groupTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        String checkSelect = """
                SELECT * FROM type_of_responsibility WHERE group_id = ?""";
        PreparedStatement check = connection.prepareStatement(checkSelect);
        check.setLong(1, Long.parseLong(String.valueOf(groupTable.getSelectionModel().getSelectedItem().getId())));
        ResultSet resultSet = check.executeQuery();
        if(resultSet.next()){
            exeptionScene.createExeptionScene("Удаление данного элемента нарушает целостность базы данных.");
            return 1;
        }
        ResponsibilityGroup responsibilityGroup = groupTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createDeleteScene(responsibilityGroup.getName());
        while (stage.isShowing()) {
        }
        if (DeleteAndEditController.needDelete) {
            String deleteBrand = "DELETE FROM responsibility_group WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setInt(1, responsibilityGroup.getId());
            preparedStatement.executeUpdate();
            loadTable();
        }
        return 1;
    }

    @FXML
    private int update() throws SQLException, IOException {
        if (groupTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для редактирования.");
            return 1;
        }
        if (violationField.getText().isEmpty() || violationField.getText().replace(" ", "") == "") {
            exeptionScene.createExeptionScene("Поле для изменения не было заполнено.");
            return 1;
        }
        ResponsibilityGroup responsibilityGroup = groupTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createEditScene(responsibilityGroup.getName(), violationField.getText());
        while (stage.isShowing()) {
        }
        if (DeleteAndEditController.needDelete) {
            String updateBrand = "UPDATE responsibility_group SET name = ? WHERE id = ?";
            PreparedStatement preparedUpdate = connection.prepareStatement(updateBrand);
            preparedUpdate.setString(1, violationField.getText());
            preparedUpdate.setInt(2, responsibilityGroup.getId());
            preparedUpdate.executeUpdate();
            loadTable();
        }
        violationField.clear();
        return 1;
    }

    @FXML
    public void findByString() throws SQLException{
        ObservableList<ResponsibilityGroup> list = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM responsibility_group WHERE name LIKE ? ORDER BY id";
        String finalString;
        finalString = "%" + findField.getText().trim() + "%";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        preparedStatement.setString(1, finalString);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()) {
            list.add(new ResponsibilityGroup(resultSet.getInt(1), resultSet.getString(2)));
        }
        groupTable.setItems(list);
    }

    @FXML
    public void clearButton() throws SQLException {
        findField.clear();
        loadTable();
    }

    private void loadTable() throws SQLException {
        ObservableList<ResponsibilityGroup> listViolation = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM responsibility_group ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()){
            listViolation.add(new ResponsibilityGroup(resultSet.getInt(1), resultSet.getString(2)));
        }
        groupTable.setItems(listViolation);
    }
}

