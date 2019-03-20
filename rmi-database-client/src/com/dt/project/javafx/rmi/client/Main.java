/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.client;

import com.dt.project.javafx.rmi.api.service.MsgService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class Main extends Application {

    private MsgService msgService;
    @Override
    public void start(Stage stage) throws Exception {
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Conectado ao servidor");
        System.out.println("=================");
        System.out.print("Digite o endereço IP do servidor : ");
        String ip = input.nextLine();
        System.out.print("Digite a porta do servidor : ");
        int port = input.nextInt();
        System.out.println("=================");
        
        Registry registry = LocateRegistry.getRegistry(ip, port);

        msgService = (MsgService) registry.lookup("service");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
        
        Parent root = loader.load();
        
        FormController controller = loader.getController();
        
        controller.setMain(this);
        
        stage.setScene(new Scene(root));
        
        stage.setTitle("Database RMI");
        
        stage.show();
    }
    
    public MsgService getMsgService() {
        return msgService;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
