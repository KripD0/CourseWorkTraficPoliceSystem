package com.project.Controllers;

import com.project.Auxiliary.SceneChanger;
import com.project.ClassesForTables.Violation;
import com.project.DbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    private TableColumn<Violation, Integer> idColumn;

    @FXML
    private ImageView imageBack;


    @FXML
    private TableColumn<Violation, String> groupColumn;

    @FXML
    private TableView<Violation> groupTable;

    public ResponsibilityGroupController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        loadTable(connection);
    }

    @FXML
    private void clickOnImageBack(){
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    private void clickOnImageHome(){
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("Scenes/Menu.fxml");
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
            loadTable(connection);
            violationField.clear();
        }
    }

    @FXML
    private int delete() throws SQLException, IOException {
        if (groupTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        Violation violation = groupTable.getSelectionModel().getSelectedItem();
        exeptionScene.createDeleteScene(violation.getName());
        while (DeleteAndEditController.isWaiting) {
        }
        if (DeleteAndEditController.needDelete) {
            String deleteBrand = "DELETE FROM responsibility_group WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setInt(1, violation.getId());
            preparedStatement.executeUpdate();
            loadTable(connection);
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
        Violation violation = groupTable.getSelectionModel().getSelectedItem();
        exeptionScene.createEditScene(violation.getName(), violationField.getText());
        while (DeleteAndEditController.isWaiting) {
        }
        if (DeleteAndEditController.needDelete) {
            String updateBrand = "UPDATE responsibility_group SET name = ? WHERE id = ?";
            PreparedStatement preparedUpdate = connection.prepareStatement(updateBrand);
            preparedUpdate.setString(1, violationField.getText());
            preparedUpdate.setInt(2, violation.getId());
            preparedUpdate.executeUpdate();
            loadTable(connection);
        }
        violationField.clear();
        return 1;
    }

    @FXML
    private void findByString() throws SQLException{
        ObservableList<Violation> list = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM responsibility_group WHERE name LIKE ?";
        String finalString;
        finalString = "%" + findField.getText().trim() + "%";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        preparedStatement.setString(1, finalString);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()) {
            list.add(new Violation(resultSet.getInt(1), resultSet.getString(2)));
        }
        groupTable.setItems(list);
    }

    @FXML
    private void clearButton() throws SQLException {
        findField.clear();
        loadTable(connection);
    }

    private void loadTable(Connection connection) throws SQLException {
        ObservableList<Violation> listViolation = FXCollections.observableArrayList();
        String getTableBrands = "SELECT * FROM responsibility_group";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableBrands);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()){
            listViolation.add(new Violation(resultSet.getInt(1), resultSet.getString(2)));
        }
        groupTable.setItems(listViolation);
    }
}

