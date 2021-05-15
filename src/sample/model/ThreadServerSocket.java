package sample.model;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ThreadServerSocket  implements  Runnable{

    ServerSocket serverSocket;
    ObservableList<Cliente> listaClientes;
    Text nameUser;
    Text points;
    AnchorPane anchorPane;
    public ThreadServerSocket(ServerSocket _servServerSocket,  ObservableList<Cliente> listaClientes, Text _nameUser,Text _points,AnchorPane anchor){
        this.serverSocket=_servServerSocket;
        this.listaClientes = listaClientes;
        this.nameUser=_nameUser;
        this.points=_points;
        this.anchorPane=anchor;
    }



    @Override
    public void run() {
        Cliente cliente;
        while (!serverSocket.isClosed()){
            try {
                Socket socket = serverSocket.accept();
                ObjectInputStream data = new ObjectInputStream(socket.getInputStream());
                cliente = (Cliente) data.readObject();
                Cliente finalCliente = cliente;
                Platform.runLater(() -> {
                    nameUser.setText(finalCliente.getName());
                    anchorPane.setVisible(false);
                });

                data.close();
                System.out.println("entro en el hilo secundario");

            }catch (Exception e){
                System.out.println(e);
            }

        }
    }



}
