package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import com.project.classesForTables.TypeOfResponsibility;
import com.project.dbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeOfResponsibilityController {

    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private TextField typeField;

    @FXML
    private TextField findField;

    @FXML
    private TableColumn<TypeOfResponsibility, String> groupColumn;

    @FXML
    private ComboBox<String> findCombobox;

    @FXML
    private ComboBox<String> groupCombobox;

    @FXML
    private ImageView homeImage;

    @FXML
    private TableColumn<TypeOfResponsibility, Integer> idColumn;

    @FXML
    private TableColumn<TypeOfResponsibility, String> typeOfResponsibilityColumn;

    @FXML
    private TableView<TypeOfResponsibility> typeOfResponsibilityTable;

    public TypeOfResponsibilityController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        loadTable();
        loadCombobox();
    }

    @FXML
    private void add() throws SQLException, IOException {
        if (typeField.getText().replace(" ", "").isEmpty()) {
            exeptionScene.createExeptionScene("Не было заполнено поле для добавления.");
        } else {
            if (!groupCombobox.getSelectionModel().getSelectedItem().equals("Выберите группу ответственности")) {
                String addBrand = "INSERT INTO type_of_responsibility(group_id, name) VALUES ((SELECT id FROM responsibility_group WHERE name = ?), ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(addBrand);
                preparedStatement.setString(1, groupCombobox.getSelectionModel().getSelectedItem());
                preparedStatement.setString(2, typeField.getText());
                preparedStatement.executeUpdate();
                loadTable();
                typeField.clear();
                groupCombobox.getSelectionModel().selectFirst();
            } else {
                exeptionScene.createExeptionScene("Не была выбранна группа ответственности");
            }
        }
    }

    @FXML
    private int delete() throws SQLException, IOException {
        if (typeOfResponsibilityTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        TypeOfResponsibility typeOfResponsibility = typeOfResponsibilityTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createDeleteScene(typeOfResponsibility.getName());
        while (stage.isShowing()) {
        }
        if (DeleteAndEditController.needDelete) {
            String deleteBrand = "DELETE FROM type_of_responsibility WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setInt(1, typeOfResponsibility.getId());
            preparedStatement.executeUpdate();
            loadTable();
        }
        return 1;
    }

    @FXML
    private int update() throws SQLException, IOException {
        if (typeOfResponsibilityTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для редактирования");
            return 1;
        }
        if (typeField.getText().isEmpty() && groupCombobox.getSelectionModel().getSelectedIndex() == 0) {
            exeptionScene.createExeptionScene("Ни один из параметров не был изменен.");
            return 1;
        }
        //Проверки и изменение параметров
        TypeOfResponsibility typeOfResponsibility = typeOfResponsibilityTable.getSelectionModel().getSelectedItem();
        if (!typeField.getText().trim().isEmpty()) {
            typeOfResponsibility.setName(typeField.getText());
        }
        if (groupCombobox.getSelectionModel().getSelectedIndex() != 0) {
            typeOfResponsibility.setGroup_id(groupCombobox.getSelectionModel().getSelectedItem());
        }
        Stage stage = exeptionScene.createEditHardScene(typeOfResponsibility.getId().toString());
        while (stage.isShowing()) {
        }
        //Запрос в БД
        if (DeleteAndEditController.needDelete) {
            String sqlupdate = "UPDATE type_of_responsibility SET group_id = (SELECT id FROM responsibility_group WHERE responsibility_group.name = ?), name = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlupdate);
            preparedStatement.setString(1, typeOfResponsibility.getGroup_id());
            preparedStatement.setString(2, typeOfResponsibility.getName());
            preparedStatement.setInt(3, typeOfResponsibility.getId());
            preparedStatement.executeUpdate();
            loadTable();
        }
        typeField.clear();
        groupCombobox.getSelectionModel().selectFirst();

        return 1;
    }

    @FXML
    private void findByString() throws SQLException {
        loadTable();
        ObservableSet<TypeOfResponsibility> removeset = FXCollections.observableSet();
        for (TypeOfResponsibility obj : typeOfResponsibilityTable.getItems()) {
            if (findCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getGroup_id().contains(findCombobox.getSelectionModel().getSelectedItem())) {
                removeset.add(obj);
            }
            if (!findField.getText().trim().isEmpty() && !obj.getName().contains(findField.getText())) {
                removeset.add(obj);
            }
        }
        typeOfResponsibilityTable.getItems().removeAll(removeset);
    }

    @FXML
    private void clearButton() throws SQLException {
        findField.clear();
        findCombobox.getSelectionModel().selectFirst();
        loadTable();
    }

    @FXML
    private void clickOnImageBack() {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene();
    }

    @FXML
    private void clickOnImageHome() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Menu.fxml");
    }

    public void loadTable() throws SQLException {
        ObservableList<TypeOfResponsibility> list = FXCollections.observableArrayList();
        String getTableType = "SELECT t.id, rg.name, t.name FROM type_of_responsibility t INNER JOIN responsibility_group rg on rg.id = t.group_id;";
        PreparedStatement preparedStatement = connection.prepareStatement(getTableType);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group_id"));
        typeOfResponsibilityColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        while (resultSet.next()) {
            list.add(new TypeOfResponsibility(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
        }
        typeOfResponsibilityTable.setItems(list);
    }

    private void loadCombobox() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите группу ответственности");
        String getGroups = "SELECT name FROM responsibility_group ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(getGroups);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        groupCombobox.getItems().addAll(list);
        groupCombobox.getSelectionModel().selectFirst();
        findCombobox.getItems().addAll(list);
        findCombobox.getSelectionModel().selectFirst();
    }
}
