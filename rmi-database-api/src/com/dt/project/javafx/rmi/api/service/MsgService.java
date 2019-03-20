/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.api.service;

import com.dt.project.javafx.rmi.api.entity.Msg;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author dell
 */
public interface MsgService extends Remote {
    
    Msg insertMsg(Msg msg) throws RemoteException;
    
    void updateMsg(Msg msg) throws RemoteException;
    
    void deleteMsg(Long id) throws RemoteException;
    
    Msg getMsgById(Long id) throws RemoteException;
    
    List<Msg> getAllMsg() throws RemoteException;
}
