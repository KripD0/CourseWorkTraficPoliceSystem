package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import com.project.classesForTables.Vehicle;
import com.project.dbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

public class VehicleController {


    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private TableColumn<Vehicle, String> brandColumn;

    @FXML
    private ComboBox<String> brandCombobox;

    @FXML
    private TableColumn<Vehicle, String> colorColumn;

    @FXML
    private ComboBox<String> colorCombobox;

    @FXML
    private ImageView homeImage;

    @FXML
    private TableColumn<Vehicle, Integer> idColumn;

    @FXML
    private TableColumn<Vehicle, String> stateNumberColumn;

    @FXML
    private TextField stateNumberField;

    @FXML
    private TableView<Vehicle> vehicleTable;

    @FXML
    private TableColumn<Vehicle, String> winColumn;

    @FXML
    private TextField winField;

    public VehicleController() throws SQLException {
    }


    @FXML
    private void initialize() throws SQLException {
        loadTable();
        loadColor();
        loadBrand();
    }

    @FXML
    private int add() throws SQLException, IOException {
        if(stateNumberField.getText().trim().isEmpty() || winField.getText().trim().isEmpty() ||
                colorCombobox.getSelectionModel().getSelectedIndex() == 0 || brandCombobox.getSelectionModel().getSelectedIndex() == 0){
            exeptionScene.createExeptionScene("Одно или несколько полей не были заполнены.");
            return 1;
        }
        String sqladd = """
                INSERT INTO vehicle(state_number, win, color_id, brand_id)
                VALUES (?, ?, (SELECT id FROM color WHERE name = ?), (SELECT id FROM brand WHERE name = ?))""";
        PreparedStatement preparedStatement = connection.prepareStatement(sqladd);
        preparedStatement.setString(1, stateNumberField.getText());
        preparedStatement.setString(2, winField.getText());
        preparedStatement.setString(3, colorCombobox.getSelectionModel().getSelectedItem());
        preparedStatement.setString(4, brandCombobox.getSelectionModel().getSelectedItem());
        preparedStatement.executeUpdate();
        clearButton();
        return 1;
    }

    @FXML
    private void clearButton() throws SQLException {
        loadTable();
        colorCombobox.getSelectionModel().selectFirst();
        brandCombobox.getSelectionModel().selectFirst();
        stateNumberField.clear();
        winField.clear();
    }

    @FXML
    private void clickOnAddColor() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Colors.fxml");
    }


    @FXML
    private void clickOnAddBrand() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Brands.fxml");
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
    private int delete() throws IOException, SQLException {
        if (vehicleTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        Vehicle vehicle = vehicleTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createDeleteHardScene(String.valueOf(vehicle.getId()));
        while (stage.isShowing()){
        }
        if (DeleteAndEditController.needDelete) {
            String deleteBrand = "DELETE FROM vehicle WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setInt(1, vehicle.getId());
            preparedStatement.executeUpdate();
            loadTable();
        }
        return 1;
    }

    @FXML
    private int findByString() throws IOException, SQLException {
        if (colorCombobox.getSelectionModel().getSelectedIndex() == 0 && brandCombobox.getSelectionModel().getSelectedIndex() == 0 &&
                stateNumberField.getText().trim().isEmpty() && winField.getText().trim().isEmpty()) {
            exeptionScene.createExeptionScene("Ни один из параметров фильтрования не был выбран.");
            return 1;
        }
        loadTable();
        ObservableSet<Vehicle> removeset = FXCollections.observableSet();
        for (Vehicle obj : vehicleTable.getItems()){
            if(!stateNumberField.getText().trim().isEmpty() && !obj.getStateNumber().contains(stateNumberField.getText())){
                removeset.add(obj);
            }
            if(!winField.getText().trim().isEmpty() && !obj.getWIN().contains(winField.getText())){
                removeset.add(obj);
            }
            if(colorCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getColor().contains(colorCombobox.getSelectionModel().getSelectedItem())){
                removeset.add(obj);
            }
            if(brandCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getBrand().contains(brandCombobox.getSelectionModel().getSelectedItem())){
                removeset.add(obj);
            }
        }
        vehicleTable.getItems().removeAll(removeset);
        return 1;
    }

    @FXML
    private int update() throws IOException, SQLException {
        if (vehicleTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для редактирования");
            return 1;
        }
        if (colorCombobox.getSelectionModel().getSelectedIndex() == 0 && brandCombobox.getSelectionModel().getSelectedIndex() == 0 &&
                stateNumberField.getText().trim().isEmpty() && winField.getText().trim().isEmpty()) {
            exeptionScene.createExeptionScene("Ни один из параметров не был изменен.");
            return 1;
        }
        Vehicle vehicle = vehicleTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createEditHardScene(String.valueOf(vehicle.getId()));
        while (stage.isShowing()) {
        }
        //Изменение параметров
        if(!stateNumberField.getText().trim().isEmpty()){
            vehicle.setStateNumber(stateNumberField.getText());
        }
        if(!winField.getText().trim().isEmpty()){
            vehicle.setWIN(winField.getText());
        }
        if(colorCombobox.getSelectionModel().getSelectedIndex() != 0){
            vehicle.setColor(colorCombobox.getSelectionModel().getSelectedItem());
        }
        if(brandCombobox.getSelectionModel().getSelectedIndex() != 0){
            vehicle.setBrand(brandCombobox.getSelectionModel().getSelectedItem());
        }
        if(DeleteAndEditController.needDelete){
            String sqlupdate = """
                UPDATE vehicle
                SET state_number = ?,
                    win          = ?,
                    color_id     = (SELECT id FROM color WHERE name = ?),
                    brand_id     = (SELECT id FROM brand WHERE name = ?)
                WHERE id = ?""";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlupdate);
            preparedStatement.setString(1, vehicle.getStateNumber());
            preparedStatement.setString(2, vehicle.getWIN());
            preparedStatement.setString(3, vehicle.getColor());
            preparedStatement.setString(4, vehicle.getBrand());
            preparedStatement.setInt(5, vehicle.getId());
            preparedStatement.executeUpdate();
            clearButton();
        }
        return 1;
    }

    private void loadTable() throws SQLException {
        ObservableList<Vehicle> list = FXCollections.observableArrayList();
        String sqlquerry = """
                SELECT v.id, v.state_number, v.win, c.name, b.name
                FROM vehicle v
                         INNER JOIN brand b on b.id = v.brand_id
                         INNER JOIN color c on c.id = v.color_id
                         ORDER BY v.id""";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        stateNumberColumn.setCellValueFactory(new PropertyValueFactory<>("stateNumber"));
        winColumn.setCellValueFactory(new PropertyValueFactory<>("WIN"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        while (resultSet.next()) {
            list.add(new Vehicle(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5)));
        }
        vehicleTable.setItems(list);
    }

    private void loadColor() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите цвет");
        String sqlquerry = "SELECT name FROM color";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        colorCombobox.getItems().addAll(list);
        colorCombobox.getSelectionModel().selectFirst();
    }

    private void loadBrand() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите марку");
        String sqlquerry = "SELECT name FROM brand";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        brandCombobox.getItems().addAll(list);
        brandCombobox.getSelectionModel().selectFirst();
    }
}
