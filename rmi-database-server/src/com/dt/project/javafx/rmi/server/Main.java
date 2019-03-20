/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.server;

import com.dt.project.javafx.rmi.server.service.MsgServiceImpl;
import com.dt.project.javafx.rmi.server.utilities.DatabaseConnection;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        DatabaseConnection.getConnection();
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Configurando o servidor");
        System.out.println("==================");
        System.out.print("Digite o Endereço IP do servidor: ");
        String hostname = input.nextLine();
        System.out.print("Digite a porta : ");
        int port = input.nextInt();
        System.out.println("==================\n");
        
        //You can setting the hosname IP address on your source code
        System.setProperty("java.rmi.server.hostname", hostname);
        
        Registry registry = LocateRegistry.createRegistry(port);
        
        MsgServiceImpl msgServiceImpl = new MsgServiceImpl();
        
        // I think the problem may come from this command line code.
        // So, I'll use another way to use the UnicastRemoteObject class.
        //MsgService msgService = (MsgService) UnicastRemoteObject.exportObject(msgServiceImpl, 0);
        
        registry.bind("service", msgServiceImpl);
        
        System.out.println("Servidor está sendo executado");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
