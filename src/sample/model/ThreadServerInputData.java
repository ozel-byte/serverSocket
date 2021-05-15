package sample.model;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class ThreadServerInputData extends Observable implements Runnable{
    ServerSocket serverSocket;

    public ThreadServerInputData(ServerSocket _servServerSocket){
        this.serverSocket=_servServerSocket;

    }


    @Override
    public void run() {
        while (!serverSocket.isClosed()){
            try {
                Socket socket = serverSocket.accept();
                setChanged();
                notifyObservers(socket);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
