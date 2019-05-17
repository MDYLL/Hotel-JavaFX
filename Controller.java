package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private DatePicker date1;
    @FXML
    private DatePicker date2;
    @FXML
    private TextField dateOn;
    @FXML
    private TextField dateOff;

    public enum rooms {
        chip,
        family,
        penthouse
    }

    ;
    private static Map<rooms, Integer> price = new HashMap<rooms, Integer>();

    @FXML
    private ComboBox<rooms> chooseRoom;

    @FXML
    private TextField total;
    @FXML
    private Button btnCheck;

    private void checkForToday() {
        if (date1.getValue().compareTo(LocalDate.now()) < 0) {
            date1.setValue(LocalDate.now());
            String d = date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateOn.setText(d);
        }
    }

    private void checkForTommorow() {
        if (date2.getValue().compareTo(LocalDate.now()) < -1) {
            date2.setValue(LocalDate.now().plusDays(1));
            String d = date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateOff.setText(d);
        }
    }

    @FXML
    private void setDateOn() {
        checkForToday();
        String d = date1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateOn.setText(d);
        if (dateOff.getText().length()>0) {
            checkDates();
        }
    }

    private void checkDates() {
        if (date1.getValue().compareTo(date2.getValue()) > -1) {
            date2.setValue(date1.getValue().plusDays(1));
            String d = date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dateOff.setText(d);
        }
    }

    @FXML
    private void setDateOff() {
        checkForTommorow();
        String d = date2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateOff.setText(d);
        if (dateOn.getText().length()>0) {
            checkDates();
        }

    }

    @FXML
    private void check() {
        if (dateOn.getText().length() > 0 && dateOff.getText().length() > 0) {
            int days = date2.getValue().compareTo(date1.getValue());
            int bill = days * price.get(chooseRoom.getValue());
            total.setText(String.valueOf(bill));
        }
    }

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        ObservableList<rooms> list = FXCollections.observableArrayList();
        list.addAll(rooms.values());
        chooseRoom.getItems().addAll(list);
        chooseRoom.getSelectionModel().select(rooms.chip);

        price.put(rooms.chip, 100);
        price.put(rooms.family, 150);
        price.put(rooms.penthouse, 500);
    }

}
