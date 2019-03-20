/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.client;

import com.dt.project.javafx.rmi.api.entity.Msg;
import com.dt.project.javafx.rmi.api.service.MsgService;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class FormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNum;
    @FXML
    private TableView<Msg> tableView;
    @FXML
    private TableColumn<Msg, Long> colId;
    @FXML
    private TableColumn<Msg, String> colNum;

    private Main main;

    private MsgService msgService;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<Msg, Long>("id"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Msg>() {
            @Override
            public void changed(ObservableValue<? extends Msg> ov, Msg oldMsg, Msg newMsg) {
                if(newMsg != null) {
                    txtId.setText(Long.toString(newMsg.getId()));
                    txtNum.setText(newMsg.getNum());
                }else {
                    clearField();
                }
            }
        });
    }

    @FXML
    private void onInsert(ActionEvent event) {
        if (isFieldValid()) {
            try {
                Msg msg = new Msg();
                msg.setNum(txtNum.getText());
                //msg.setNum((int)msg.getNum());
                //msg.setNum(Long.valueOf(txtNum.getLength()));
                //msg.setNum(Integer.parseInt(txtNum.getText()));
                //msg.setNum(Long.parseLong(txtNum.getText()));
                //msg.setNum(Integer.valueOf(txtNum.getText()));
                //msg.setNum(Long.valueOf(txtNum.getText()));
                //msg.setNum(Long.getLong(txtNum.getText()));

                msg = msgService.insertMsg(msg);

                tableView.getItems().add(msg);

                clearField();

            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    private void onUpdate(ActionEvent event) {
        int index = tableView.getSelectionModel().getSelectedIndex();
        
        if (index == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Update");
            alert.setHeaderText("Nenhuma Msg selecionada");
            alert.setContentText("Por favor selecione uma msg na tabela");
            alert.showAndWait();
            return;
        }
        
        if (isFieldValid()) {
            try {
                Msg msg = new Msg();
                msg.setId(Long.valueOf(txtId.getText()));
                msg.setNum(txtNum.getText());
                
                msgService.updateMsg(msg);
                
                tableView.getItems().set(index, msg);
                
                clearField();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        try {
            Msg msg = tableView.getSelectionModel().getSelectedItem();
            if(msg == null) {
                return;
            }
            
            msgService.deleteMsg(msg.getId());
            
            tableView.getItems().remove(msg);
            
            clearField();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onRefresh(ActionEvent event) {
        try {
            tableView.getItems().setAll(msgService.getAllMsg());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        
        clearField();
    }

    public void setMain(Main main) {
        this.main = main;
        this.msgService = main.getMsgService();

        try {
            tableView.getItems().setAll(msgService.getAllMsg());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private void clearField() {
        txtId.setText("");
        txtNum.setText("");
    }

    private boolean isFieldValid() {
        String errorMessage = "";
        if (txtNum.getText() == null || txtNum.getText().isEmpty()) {
            errorMessage += "Nenhum numero válido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("");
            alert.setHeaderText("Por favor corrija campos inválidos");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
