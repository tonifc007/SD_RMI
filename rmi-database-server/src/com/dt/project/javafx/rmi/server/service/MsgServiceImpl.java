/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dt.project.javafx.rmi.server.service;

import com.dt.project.javafx.rmi.api.entity.Msg;
import com.dt.project.javafx.rmi.api.service.MsgService;
import com.dt.project.javafx.rmi.server.utilities.DatabaseConnection;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author dell
 */
public class MsgServiceImpl extends UnicastRemoteObject implements MsgService {
    

    public MsgServiceImpl() throws RemoteException {
    }

    @Override
    public Msg insertMsg(Msg msg) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request insertMsg() method...");
        } catch (ServerNotActiveException ex) {
            ex.printStackTrace();
        }
        
        PreparedStatement statement = null;

        String sql = "insert into msg(id, numero) values (null, ?)";
        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, msg.getNum());
            //statement.setInt(1, (int) msg.getNum());
            //statement.setLong(1, Long.valueOf(msg.getNum()));

            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                msg.setId(result.getLong(1));
            }

            result.close();
            System.out.println("[sucesso]");
            return msg;

        } catch (SQLException ex) {
            System.out.println("[falhou]");
            ex.printStackTrace();
            return null;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                //Sorry, I forgot to close the connection to the database :D
                DatabaseConnection.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void updateMsg(Msg msg) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request updateMsg() method...");
        } catch (ServerNotActiveException ex) {
            ex.printStackTrace();
        }
        
        PreparedStatement statement = null;

        String sql = "update msg set numero = ?"
                + " where id = ?";

        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, msg.getNum());
            statement.setLong(2, msg.getId());

            statement.executeUpdate();
            System.out.println("[sucesso]");
        } catch (SQLException ex) {
            System.out.println("[falhou]");
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                //Sorry, I forgot to close the connection to the database :D
                DatabaseConnection.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void deleteMsg(Long id) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request deleteMsg() method...");
        } catch (ServerNotActiveException ex) {
            ex.printStackTrace();
        }
        
        PreparedStatement statement = null;

        String sql = "delete from msg where id = ?";

        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);

            statement.setLong(1, id);

            statement.executeUpdate();
            System.out.println("[sucesso]");
        } catch (SQLException ex) {
            System.out.println("[falhou]");
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                //Sorry, I forgot to close the connection to the database :D
                DatabaseConnection.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Msg getMsgById(Long id) throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request getMsgById() method...");
        } catch (ServerNotActiveException ex) {
            ex.printStackTrace();
        }
        
        PreparedStatement statement = null;

        String sql = "select * from msg where id = ?";

        try {
            statement = DatabaseConnection.getConnection().prepareStatement(sql);

            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            Msg msg = null;
            if (result.next()) {
                msg = new Msg();
                msg.setId(result.getLong("id"));
                msg.setNum(result.getString("numero"));
            }

            result.close();
            System.out.println("[sucesso]");
            return msg;
        } catch (SQLException ex) {
            System.out.println("[falhou]");
            ex.printStackTrace();
            return null;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                //Sorry, I forgot to close the connection to the database :D
                DatabaseConnection.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<Msg> getAllMsg() throws RemoteException {
        try {
            System.out.print("\nClient " + getClientHost() + " request getAllMsg() method...");
        } catch (ServerNotActiveException ex) {
            ex.printStackTrace();
        }
        
        Statement statement = null;

        String sql = "select * from msg";

        try {
            statement = DatabaseConnection.getConnection().createStatement();

            ResultSet result = statement.executeQuery(sql);

            List<Msg> list = new ArrayList<Msg>();

            while (result.next()) {
                Msg msg = new Msg();
                msg.setId(result.getLong("id"));
                msg.setNum(result.getString("numero"));
                list.add(msg);
            }

            result.close();
            System.out.println("[sucesso]");
            return list;
        } catch (SQLException ex) {
            System.out.println("[falhou]");
            ex.printStackTrace();
            return null;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                //Sorry, I forgot to close the connection to the database :D
                DatabaseConnection.getConnection().close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

}