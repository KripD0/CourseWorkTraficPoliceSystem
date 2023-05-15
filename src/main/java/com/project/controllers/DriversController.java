package com.project.controllers;

import com.project.auxiliary.SceneChanger;
import com.project.classesForTables.Drivers;
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
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DriversController {
    private final Connection connection = DbConnection.buildConnection();

    private final SceneChanger exeptionScene = new SceneChanger();

    @FXML
    private TableColumn<Drivers, String> addressColumn;

    @FXML
    private TableColumn<Drivers, String> categoryColumn;

    @FXML
    private TableColumn<Drivers, Date> dateColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Drivers, String> departamentColumn;

    @FXML
    private TableColumn<Drivers, BigInteger> dlnNumberColumn;

    @FXML
    private TableView<Drivers> driverTable;

    @FXML
    private TableColumn<Drivers, String> fioColumn;

    @FXML
    private ComboBox<String> categoryCombobox;

    @FXML
    private ImageView homeImage;

    @FXML
    private TableColumn<Drivers, String> passportColumn;

    @FXML
    private TableColumn<Drivers, String> telephoneColumn;

    @FXML
    private TextField departamentField;

    @FXML
    private TextField dlnNumberField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passportField;

    @FXML
    private TextField secondSurnameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField addressField;

    public DriversController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        loadTable();
        loadCombobox();
    }

    @FXML
    private int add() throws IOException, SQLException {
        if (categoryCombobox.getSelectionModel().getSelectedIndex() == 0 || datePicker.getValue() == null ||
                addressField.getText().trim().isEmpty() || telephoneField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty() ||
                nameField.getText().trim().isEmpty() || secondSurnameField.getText().trim().isEmpty() || passportField.getText().trim().isEmpty() ||
                dlnNumberField.getText().trim().isEmpty() || departamentField.getText().trim().isEmpty()) {
            exeptionScene.createExeptionScene("Одно или несколько полей не были заполнены.");
            return 1;
        }
        if(!dlnNumberField.getText().trim().isEmpty()){
            try {
                Long.parseLong(dlnNumberField.getText());
            }catch (NumberFormatException e){
                exeptionScene.createExeptionScene("Поле номер ВУ должно быть числом.");
                return 1;
            }
        }
        if(!passportField.getText().trim().isEmpty()){
            try {
                Long.parseLong(passportField.getText());
            }catch (NumberFormatException e){
                exeptionScene.createExeptionScene("Поле Пасспорт должно быть числом.");
                return 1;
            }
        }
        if(!telephoneField.getText().trim().isEmpty()){
            try {
                Long.parseLong(telephoneField.getText());
            }catch (NumberFormatException e){
                exeptionScene.createExeptionScene("Поле Номер телефона должно быть числом.");
                return 1;
            }
        }
        String createDLN = """
                INSERT INTO drivers_license(drivers_license_number, date_of_issue, traffic_police_department, category_id)
                VALUES (?, ?, ?, (SELECT id FROM category c WHERE c.name = ?))""";
        PreparedStatement dlnStatement = connection.prepareStatement(createDLN);
        dlnStatement.setLong(1, Long.parseLong(dlnNumberField.getText()));
        dlnStatement.setDate(2, Date.valueOf(datePicker.getValue()));
        dlnStatement.setInt(3, Integer.parseInt(departamentField.getText()));
        dlnStatement.setString(4, categoryCombobox.getSelectionModel().getSelectedItem());
        dlnStatement.executeUpdate();
        String createHuman = """
                INSERT INTO human(surname, name, second_surname, passport, dln_id, telephone_number, registration_address)
                VALUES (?, ?, ?, ?, ?, ?, ?)""";
        PreparedStatement humanStatement = connection.prepareStatement(createHuman);
        humanStatement.setString(1, surnameField.getText());
        humanStatement.setString(2, nameField.getText());
        humanStatement.setString(3, secondSurnameField.getText());
        humanStatement.setLong(4, Long.parseLong(passportField.getText()));
        humanStatement.setLong(5, Long.parseLong(dlnNumberField.getText()));
        humanStatement.setLong(6, Long.parseLong(telephoneField.getText()));
        humanStatement.setString(7, addressField.getText());
        humanStatement.executeUpdate();
        clearButton();
        return 1;
    }

    @FXML
    public void clearButton() throws SQLException {
        loadTable();
        addressField.clear();
        telephoneField.clear();
        datePicker.setValue(null);
        surnameField.clear();
        nameField.clear();
        secondSurnameField.clear();
        passportField.clear();
        dlnNumberField.clear();
        departamentField.clear();
        categoryCombobox.getSelectionModel().selectFirst();
    }


    @FXML
    private void clickOnAddCategory() {
        SceneChanger sceneChanger = new SceneChanger(homeImage.getScene());
        sceneChanger.changeScene("scenes/Category.fxml");
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
        if (driverTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для удаления.");
            return 1;
        }
        String checkSelect = """
                SELECT * FROM decree WHERE dln_id = ?""";
        PreparedStatement check = connection.prepareStatement(checkSelect);
        check.setLong(1, Long.parseLong(String.valueOf(driverTable.getSelectionModel().getSelectedItem().getDriversLicenseNumber())));
        ResultSet resultSet = check.executeQuery();
        if(resultSet.next()){
            exeptionScene.createExeptionScene("Удаление данного элемента нарушает целостность базы данных.");
            return 1;
        }
        Drivers drivers = driverTable.getSelectionModel().getSelectedItem();
        Stage stage = exeptionScene.createDeleteHardScene(String.valueOf(drivers.getId()));
        while (stage.isShowing()) {
        }
        if (DeleteAndEditController.needDelete) {
            String delete = "DELETE FROM human WHERE dln_id = ?";
            String deletesecond = "DELETE FROM drivers_license WHERE drivers_license_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setLong(1, Long.parseLong(String.valueOf(drivers.getDriversLicenseNumber())));
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connection.prepareStatement(deletesecond);
            preparedStatement1.setLong(1, Long.parseLong(String.valueOf(drivers.getDriversLicenseNumber())));
            preparedStatement1.executeUpdate();
            loadTable();
        }
        return 1;
    }

    @FXML
    public int findByString() throws IOException, SQLException {
        if (categoryCombobox.getSelectionModel().getSelectedIndex() == 0 && datePicker.getValue() == null &&
                addressField.getText().trim().isEmpty() && telephoneField.getText().trim().isEmpty() && surnameField.getText().trim().isEmpty() &&
                nameField.getText().trim().isEmpty() && secondSurnameField.getText().trim().isEmpty() && passportField.getText().trim().isEmpty() &&
                dlnNumberField.getText().trim().isEmpty() && departamentField.getText().trim().isEmpty()) {
            exeptionScene.createExeptionScene("Ни один из параметров фильтрования не был выбран.");
            return 1;
        }
        loadTable();
        ObservableSet<Drivers> removeset = FXCollections.observableSet();
        for (Drivers obj : driverTable.getItems()) {
            String[] fio = obj.getFio().split(" ");
            if (categoryCombobox.getSelectionModel().getSelectedIndex() != 0 && !obj.getCategory().equals(categoryCombobox.getSelectionModel().getSelectedItem())) {
                removeset.add(obj);
            }
            if (datePicker.getValue() != null && !obj.getDate().equals(Date.valueOf(datePicker.getValue()))) {
                removeset.add(obj);
            }
            if (!addressField.getText().trim().isEmpty() && !obj.getRegistrationAddres().contains(addressField.getText())) {
                removeset.add(obj);
            }
            if (!telephoneField.getText().trim().isEmpty() && !String.valueOf(obj.getTelephoneNumber()).contains(telephoneField.getText())) {
                removeset.add(obj);
            }
            if (!dlnNumberField.getText().trim().isEmpty() && !String.valueOf(obj.getDriversLicenseNumber()).contains(dlnNumberField.getText())) {
                removeset.add(obj);
            }
            if (!passportField.getText().trim().isEmpty() && !String.valueOf(obj.getPassport()).contains(passportField.getText())) {
                removeset.add(obj);
            }
            if (!surnameField.getText().trim().isEmpty() && !fio[0].contains(surnameField.getText())) {
                removeset.add(obj);
            }
            if (!nameField.getText().trim().isEmpty() && !fio[1].contains(nameField.getText())) {
                removeset.add(obj);
            }
            if (!secondSurnameField.getText().trim().isEmpty() && !fio[2].contains(secondSurnameField.getText())) {
                removeset.add(obj);
            }
            if (!departamentField.getText().trim().isEmpty() && !obj.getTrafficPoliceDepartament().contains(departamentField.getText())) {
                removeset.add(obj);
            }
        }
        driverTable.getItems().removeAll(removeset);
        return 1;
    }

    @FXML
    private int update() throws IOException, SQLException {
        if (driverTable.getSelectionModel().getSelectedItem() == null) {
            exeptionScene.createExeptionScene("Не был выбран элемент для редактирования");
            return 1;
        }
        if (categoryCombobox.getSelectionModel().getSelectedIndex() == 0 && datePicker.getValue() == null &&
                addressField.getText().trim().isEmpty() && telephoneField.getText().trim().isEmpty() && surnameField.getText().trim().isEmpty() &&
                nameField.getText().trim().isEmpty() && secondSurnameField.getText().trim().isEmpty() && passportField.getText().trim().isEmpty() &&
                dlnNumberField.getText().trim().isEmpty() && departamentField.getText().trim().isEmpty()) {
            exeptionScene.createExeptionScene("Ни один из параметров не был изменен.");
            return 1;
        }
        if(!dlnNumberField.getText().trim().isEmpty()){
            try {
                Long.parseLong(dlnNumberField.getText());
            }catch (NumberFormatException e){
                exeptionScene.createExeptionScene("Поле номер ВУ должно быть числом.");
                return 1;
            }
        }
        if(!passportField.getText().trim().isEmpty()){
            try {
                Long.parseLong(passportField.getText());
            }catch (NumberFormatException e){
                exeptionScene.createExeptionScene("Поле Пасспорт должно быть числом.");
                return 1;
            }
        }
        if(!telephoneField.getText().trim().isEmpty()){
            try {
                Long.parseLong(telephoneField.getText());
            }catch (NumberFormatException e){
                exeptionScene.createExeptionScene("Поле Номер телефона должно быть числом.");
                return 1;
            }
        }
        String checkSelect = """
                SELECT * FROM decree WHERE dln_id = ?""";
        PreparedStatement check = connection.prepareStatement(checkSelect);
        check.setLong(1, Long.parseLong(String.valueOf(driverTable.getSelectionModel().getSelectedItem().getDriversLicenseNumber())));
        ResultSet resultSet = check.executeQuery();
        if(resultSet.next()){
            exeptionScene.createExeptionScene("Удалите все записи из формы регистрация постановлений с этим ВУ и повторите попытку.");
            return 1;
        }
        boolean flag = false;
        Drivers drivers = driverTable.getSelectionModel().getSelectedItem();
        BigInteger dlnNumber = drivers.getDriversLicenseNumber();
        String[] Fio = drivers.getFio().split(" ");
        if (!addressField.getText().trim().isEmpty()) {
            drivers.setRegistrationAddres(addressField.getText());
        }
        if (!telephoneField.getText().trim().isEmpty()) {
            drivers.setTelephoneNumber(BigInteger.valueOf(Long.parseLong(telephoneField.getText())));
        }
        if (!surnameField.getText().trim().isEmpty()) {
            Fio[0] = surnameField.getText();
        }
        if (!nameField.getText().trim().isEmpty()) {
            Fio[1] = nameField.getText();
        }
        if (!secondSurnameField.getText().trim().isEmpty()) {
            Fio[2] = secondSurnameField.getText();
        }
        if (!passportField.getText().trim().isEmpty()) {
            drivers.setPassport(BigInteger.valueOf(Long.parseLong(passportField.getText())));
        }
        if (!dlnNumberField.getText().trim().isEmpty()) {
            flag = true;
            drivers.setDriversLicenseNumber(BigInteger.valueOf(Long.parseLong(dlnNumberField.getText())));
        }
        if (!departamentField.getText().trim().isEmpty()) {
            drivers.setTrafficPoliceDepartament(departamentField.getText());
        }
        if(categoryCombobox.getSelectionModel().getSelectedIndex() != 0){
            drivers.setCategory(categoryCombobox.getSelectionModel().getSelectedItem());
        }
        if(datePicker.getValue() != null){
            drivers.setDate(Date.valueOf(datePicker.getValue()));
        }
        Stage stage = exeptionScene.createEditHardScene(String.valueOf(drivers.getId()));
        while (stage.isShowing()) {
        }
        if (DeleteAndEditController.needDelete) {
            //Изза связи 1 к одному если пользователь попросит изменить dln то нужно будет сначала удалить human заменить dln если оно не используется в decree, потом создать заного
            String deletehuman = """
                    DELETE FROM human WHERE id = ?
                    """;
            String updatehuman = """
                    UPDATE human
                    SET surname              = ?,
                        name                 = ?,
                        second_surname       = ?,
                        passport             = ?,
                        telephone_number     = ?,
                        registration_address = ?
                    WHERE id = ?
                    """;
            String updateDLN = """
                    UPDATE drivers_license
                    SET drivers_license_number    = ?,
                        date_of_issue             = ?,
                        traffic_police_department = ?,
                        category_id               = (SELECT id FROM category WHERE name = ?)
                    WHERE drivers_license_number = ? 
                    """;
            String addhuman = """
                    INSERT INTO human(surname, name, second_surname, passport, dln_id, telephone_number, registration_address)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;
            String updatewithoutDLN = """                                        
                    UPDATE drivers_license
                    SET date_of_issue             = ?,
                        traffic_police_department = ?,
                        category_id               = (SELECT id FROM category WHERE name = ?)
                    WHERE drivers_license_number = ?
                    """;
            if (flag) {
                PreparedStatement deleteHuman = connection.prepareStatement(deletehuman);
                deleteHuman.setInt(1, drivers.getId());
                deleteHuman.executeUpdate();
                PreparedStatement updatedln = connection.prepareStatement(updateDLN);
                updatedln.setLong(1, Long.parseLong(String.valueOf(drivers.getDriversLicenseNumber())));
                updatedln.setDate(2, (Date) drivers.getDate());
                updatedln.setInt(3, Integer.parseInt(drivers.getTrafficPoliceDepartament()));
                updatedln.setString(4, drivers.getCategory());
                updatedln.setLong(5, Long.parseLong(String.valueOf(dlnNumber)));
                updatedln.executeUpdate();
                PreparedStatement addHuman = connection.prepareStatement(addhuman);
                addHuman.setString(1, Fio[0]);
                addHuman.setString(2, Fio[1]);
                addHuman.setString(3, Fio[2]);
                addHuman.setLong(4, Long.parseLong(String.valueOf(drivers.getPassport())));
                addHuman.setLong(5, Long.parseLong(String.valueOf(drivers.getDriversLicenseNumber())));
                addHuman.setLong(6, Long.parseLong(String.valueOf(drivers.getTelephoneNumber())));
                addHuman.setString(7, drivers.getRegistrationAddres());
                addHuman.executeUpdate();
            } else {
                PreparedStatement updateHuman = connection.prepareStatement(updatehuman);
                updateHuman.setString(1, Fio[0]);
                updateHuman.setString(2, Fio[1]);
                updateHuman.setString(3, Fio[2]);
                updateHuman.setLong(4, Long.parseLong(String.valueOf(drivers.getPassport())));
                updateHuman.setLong(5, Long.parseLong(String.valueOf(drivers.getTelephoneNumber())));
                updateHuman.setString(6, drivers.getRegistrationAddres());
                updateHuman.setInt(7, drivers.getId());
                updateHuman.executeUpdate();
                PreparedStatement preparedStatement = connection.prepareStatement(updatewithoutDLN);
                preparedStatement.setDate(1, (Date) drivers.getDate());
                preparedStatement.setInt(2, Integer.parseInt(drivers.getTrafficPoliceDepartament()));
                preparedStatement.setString(3, drivers.getCategory());
                preparedStatement.setLong(4, Long.parseLong(String.valueOf(drivers.getDriversLicenseNumber())));
                preparedStatement.executeUpdate();
            }
            clearButton();
        }
        return 1;
    }

    private void loadTable() throws SQLException {
        ObservableList<Drivers> list = FXCollections.observableArrayList();
        String sqlquerry = """
                SELECT h.id,
                       (surname,
                        h.name,
                        second_surname) AS FIO,
                       passport,
                       telephone_number,
                       registration_address,
                       drivers_license_number,
                       dl.date_of_issue,
                       dl.traffic_police_department,
                       c.name
                FROM human h
                         INNER JOIN drivers_license dl on dl.drivers_license_number = h.dln_id
                         INNER JOIN category c on c.id = dl.category_id
                         ORDER BY h.id""";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlquerry);
        ResultSet resultSet = preparedStatement.executeQuery();
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("Fio"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("passport"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("registrationAddres"));
        dlnNumberColumn.setCellValueFactory(new PropertyValueFactory<>("driversLicenseNumber"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        departamentColumn.setCellValueFactory(new PropertyValueFactory<>("trafficPoliceDepartament"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        while (resultSet.next()) {
            list.add(new Drivers(resultSet.getInt(1), resultSet.getString(2).replace("\"", "").replace(",", " ").replace("(", "").replace(")", ""),
                    BigInteger.valueOf(resultSet.getLong(3)), BigInteger.valueOf(resultSet.getLong(4)),
                    resultSet.getString(5), BigInteger.valueOf(resultSet.getLong(6)), resultSet.getDate(7),
                    resultSet.getString(8), resultSet.getString(9)));
        }
        driverTable.setItems(list);
    }

    private void loadCombobox() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Выберите категорию");
        String select = "SELECT name FROM category";
        PreparedStatement preparedStatement = connection.prepareStatement(select);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        categoryCombobox.getItems().addAll(list);
        categoryCombobox.getSelectionModel().selectFirst();
    }

}
