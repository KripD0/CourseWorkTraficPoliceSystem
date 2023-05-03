package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import com.project.classesForTables.Decree;
import com.project.dbUtil.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;

public class DecreeController {

    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private TableColumn<Decree, Date> dateColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Decree> decreeTable;

    @FXML
    private TableColumn<Decree, BigInteger> dlnColumn;

    @FXML
    private ComboBox<String> dlnCombobox;

    @FXML
    private ImageView homeImage;

    @FXML
    private TableColumn<Decree, Integer> idColumn;

    @FXML
    private TableColumn<Decree, String> regionColumn;

    @FXML
    private ComboBox<String> regionCombobox;

    @FXML
    private TableColumn<Decree, String> responsibilityColumn;

    @FXML
    private ComboBox<String> responsibilityCombobox;

    @FXML
    private ComboBox<String> tsCombobox;

    @FXML
    private TableColumn<Decree, String> statusColumn;

    @FXML
    private ComboBox<String> statusCombobox;

    @FXML
    private TableColumn<Decree, String> tsNumberColumn;

    @FXML
    private TableColumn<Decree, String> violationColumn;

    @FXML
    private ComboBox<String> violationCombobox;

    public DecreeController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        loadTable();
        loadDLN();
        loadViolation();
        loadRegion();
        loadStatus();
        loadResponsibility();
        loadTs();
    }

    @FXML
    private int add() throws SQLException, IOException {
        if (dlnCombobox.getSelectionModel().getSelectedIndex() == 0 || regionCombobox.getSelectionModel().getSelectedIndex() == 0 ||
                responsibilityCombobox.getSelectionModel().getSelectedIndex() == 0 || statusCombobox.getSelectionModel().getSelectedIndex() == 0 ||
                violationCombobox.getSelectionModel().getSelectedIndex() == 0  || datePicker.getValue() == null || tsCombobox.getSelectionModel().getSelectedIndex() == 0) {
            exeptionScene.createExeptionScene("Одно или несколько полей не были заполнены.");
            return 1;
        }
        String sqladd = """
                INSERT INTO decree(dln_id, violation_id, date_violation, vehicle_id, region_id, status_id, responsibility_id)
                VALUES (?, (SELECT id FROM violation WHERE name = ?), ?, (SELECT id FROM vehicle WHERE state_number = ?),
                        (SELECT id FROM region WHERE name = ?), (SELECT id FROM status WHERE name = ?), (SELECT id FROM type_of_responsibility WHERE name = ?))""";
        PreparedStatement preparedStatement = connection.prepareStatement(sqladd);
        preparedStatement.setLong(1, Long.parseLong(dlnCombobox.getSelectionModel().getSelectedItem()));
        preparedStatement.setString(2, violationCombobox.getSelectionModel().getSelectedItem());
        preparedStatement.setDate(3, Date.valueOf(datePicker.getValue()));
        preparedStatement.setString(4, tsCombobox.getSelectionModel().getSelectedItem());
        preparedStatement.setString(5, regionCombobox.getSelectionModel().getSelectedItem());
        preparedStatement.setString(6, statusCombobox.getSelectionModel().getSelectedItem());
        preparedStatement.setString(7, responsibilityCombobox.getSelectionModel().getSelectedItem());
        preparedStatement.executeUpdate();
        clearButton();
        return 1;
    }

    @FXML
    private void clearButton() throws SQLException {
        loadTable();
        responsibilityCombobox.getSelectionModel().selectFirst();
        violationCombobox.getSelectionModel().selectFirst();
        statusCombobox.getSelectionModel().selectFirst();
        regionCombobox.getSelectionModel().selectFirst();
        dlnCombobox.getSelectionModel().selectFirst();
        tsCombobox.getSelectionModel().selectFirst();
        datePicker.setValue(null);
    }

    @FXML
    public void clickOnImageBack(){
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
        if (decreeTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        Decree decree = decreeTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createDeleteHardScene(String.valueOf(decree.getId()));
        while (stage.isShowing()){
        }
        if (DeleteAndEditController.needDelete) {
            String deleteBrand = "DELETE FROM decree WHERE resolution_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteBrand);
            preparedStatement.setInt(1, decree.getId());
            preparedStatement.executeUpdate();
            loadTable();
        }
        return 1;
    }

    @FXML
    public int findByString() throws IOException, SQLException {
        if (dlnCombobox.getSelectionModel().getSelectedIndex() == 0 && regionCombobox.getSelectionModel().getSelectedIndex() == 0 &&
                responsibilityCombobox.getSelectionModel().getSelectedIndex() == 0 && statusCombobox.getSelectionModel().getSelectedIndex() == 0 &&
                violationCombobox.getSelectionModel().getSelectedIndex() == 0 && datePicker.getValue() == null && tsCombobox.getSelectionModel().getSelectedIndex() == 0) {
            exeptionScene.createExeptionScene("Ни один из параметров фильтрования не был выбран.");
            return 1;
        }
        loadTable();
        ObservableSet<Decree> removeset = FXCollections.observableSet();
        for (Decree obj : decreeTable.getItems()){
            if (dlnCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getDln_number().equals(BigInteger.valueOf(Long.parseLong(dlnCombobox.getSelectionModel().getSelectedItem())))) {
                removeset.add(obj);
            }
            if (regionCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getRegion().contains(regionCombobox.getSelectionModel().getSelectedItem())) {
                removeset.add(obj);
            }
            if (responsibilityCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getResponsibility().contains(responsibilityCombobox.getSelectionModel().getSelectedItem())) {
                removeset.add(obj);
            }
            if (statusCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getStatus().contains(statusCombobox.getSelectionModel().getSelectedItem())) {
                removeset.add(obj);
            }
            if (violationCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getViolation().contains(violationCombobox.getSelectionModel().getSelectedItem())) {
                removeset.add(obj);
            }
            if (tsCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getVehicle().contains(tsCombobox.getSelectionModel().getSelectedItem())) {
                removeset.add(obj);
            }
            if (datePicker.getValue() != null && !obj.getDate().equals(Date.valueOf(datePicker.getValue()))) {
                removeset.add(obj);
            }
        }
        decreeTable.getItems().removeAll(removeset);
        return 1;
    }

    @FXML
    private int update() throws IOException, SQLException {
        if (decreeTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для редактирования");
            return 1;
        }
        if (dlnCombobox.getSelectionModel().getSelectedIndex() == 0 && regionCombobox.getSelectionModel().getSelectedIndex() == 0 &&
                responsibilityCombobox.getSelectionModel().getSelectedIndex() == 0 && statusCombobox.getSelectionModel().getSelectedIndex() == 0 &&
                violationCombobox.getSelectionModel().getSelectedIndex() == 0 && datePicker.getValue() == null && tsCombobox.getSelectionModel().getSelectedIndex() == 0) {
            exeptionScene.createExeptionScene("Ни один из параметров не был изменен.");
            return 1;
        }
        //Изменение параметров
        Decree decree = decreeTable.getSelectionModel().getSelectedItem();
        if(dlnCombobox.getSelectionModel().getSelectedIndex() != 0){
            decree.setDln_number(BigInteger.valueOf(Long.parseLong(dlnCombobox.getSelectionModel().getSelectedItem())));
        }
        if(regionCombobox.getSelectionModel().getSelectedIndex() != 0){
            decree.setRegion(regionCombobox.getSelectionModel().getSelectedItem());
        }
        if(responsibilityCombobox.getSelectionModel().getSelectedIndex() != 0){
            decree.setResponsibility(responsibilityCombobox.getSelectionModel().getSelectedItem());
        }
        if(statusCombobox.getSelectionModel().getSelectedIndex() != 0){
            decree.setStatus(statusCombobox.getSelectionModel().getSelectedItem());
        }
        if(violationCombobox.getSelectionModel().getSelectedIndex() != 0){
            decree.setViolation(violationCombobox.getSelectionModel().getSelectedItem());
        }
        if(datePicker.getValue() != null){
            decree.setDate(Date.valueOf(datePicker.getValue()));
        }
        if(tsCombobox.getSelectionModel().getSelectedIndex() != 0){
            decree.setVehicle(tsCombobox.getSelectionModel().getSelectedItem());
        }
        Stage stage = exeptionScene.createEditHardScene(String.valueOf(decree.getId()));
        while (stage.isShowing()) {
        }
        //Запрос в БД
        if (DeleteAndEditController.needDelete) {
            String sqlupdate = """
                    UPDATE decree
                    SET dln_id            = ?,
                        violation_id      = (SELECT id FROM violation WHERE name = ?),
                        date_violation    = ?,
                        vehicle_id        = (SELECT id FROM vehicle WHERE state_number = ?),
                        region_id         = (SELECT id FROM region WHERE name = ?),
                        status_id         = (SELECT id FROM status WHERE name = ?),
                        responsibility_id = (SELECT id FROM type_of_responsibility WHERE name = ?)
                    WHERE resolution_number = ?""";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlupdate);
            preparedStatement.setLong(1, Long.parseLong(String.valueOf(decree.getDln_number())));
            preparedStatement.setString(2, decree.getViolation());
            preparedStatement.setDate(3, (Date) decree.getDate());
            preparedStatement.setString(4, decree.getVehicle());
            preparedStatement.setString(5, decree.getRegion());
            preparedStatement.setString(6, decree.getStatus());
            preparedStatement.setString(7, decree.getResponsibility());
            preparedStatement.setInt(8, decree.getId());
            preparedStatement.executeUpdate();
            clearButton();
        }
        return 1;
    }

    @FXML
    private void clickOnAddViolation() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Violation.fxml");
    }

    @FXML
    private void clickOnAddRegion() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Region.fxml");
    }

    @FXML
    private void clickOnAddStatus() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Status.fxml");
    }

    @FXML
    private void clickOnAddResponsibility() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/TypeOfResponsibility.fxml");
    }

    private void loadTable() throws SQLException {
        ObservableList<Decree> list = FXCollections.observableArrayList();
        String selectAll = """
                SELECT resolution_number, dln_id, v.name, date_violation,  v2.state_number, r.name, s.name, tor.name FROM decree d
                INNER JOIN violation v on v.id = d.violation_id
                INNER JOIN vehicle v2 on v2.id = d.vehicle_id
                INNER JOIN region r on r.id = d.region_id
                INNER JOIN status s on s.id = d.status_id
                INNER JOIN type_of_responsibility tor on d.responsibility_id = tor.id
                ORDER BY resolution_number""";
        PreparedStatement preparedStatement = connection.prepareStatement(selectAll);
        ResultSet resultSet = preparedStatement.executeQuery();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dlnColumn.setCellValueFactory(new PropertyValueFactory<>("dln_number"));
        violationColumn.setCellValueFactory(new PropertyValueFactory<>("violation"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        responsibilityColumn.setCellValueFactory(new PropertyValueFactory<>("responsibility"));
        while (resultSet.next()) {
            list.add(new Decree(resultSet.getInt(1), BigInteger.valueOf(resultSet.getLong(2)), resultSet.getString(3), resultSet.getDate(4),
                    resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8)));
        }
        decreeTable.setItems(list);
    }

    private void loadDLN() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите ВУ");
        String sqlquerry = "SELECT drivers_license_number FROM drivers_license";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        dlnCombobox.getItems().addAll(list);
        dlnCombobox.getSelectionModel().selectFirst();
    }

    private void loadViolation() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите нарушение");
        String sqlquerry = "SELECT name FROM violation";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        violationCombobox.getItems().addAll(list);
        violationCombobox.getSelectionModel().selectFirst();
    }

    private void loadRegion() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите регион");
        String sqlquerry = "SELECT name FROM region";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        regionCombobox.getItems().addAll(list);
        regionCombobox.getSelectionModel().selectFirst();
    }

    private void loadStatus() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите статус");
        String sqlquerry = "SELECT name FROM status";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        statusCombobox.getItems().addAll(list);
        statusCombobox.getSelectionModel().selectFirst();
    }

    private void loadResponsibility() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите ответственность");
        String sqlquerry = "SELECT name FROM type_of_responsibility";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        responsibilityCombobox.getItems().addAll(list);
        responsibilityCombobox.getSelectionModel().selectFirst();
    }

    private void loadTs() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите номер ТС");
        String sqlquerry = "SELECT state_number FROM vehicle";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        tsCombobox.getItems().addAll(list);
        tsCombobox.getSelectionModel().selectFirst();
    }
}