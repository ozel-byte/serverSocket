package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import sample.model.Cliente;
import sample.model.ThreadServerInputData;
import sample.model.ThreadServerSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    @FXML
    private Pane areajuego;

    @FXML
    private ListView<?> listmsj;

    @FXML
    private TextField campomsj;

    @FXML
    private Text nameUser;


    @FXML
    private Text puntosuser;
    @FXML
    private Text turno;

    @FXML
    private Text ganadosServer;

    @FXML
    private Text valorO1;

    @FXML
    private Text valorX1;

    @FXML
    private Text valorO2;

    @FXML
    private Text valorO5;

    @FXML
    private Text valorO3;

    @FXML
    private Text valorO4;

    @FXML
    private Text valorO8;

    @FXML
    private Text valorO6;

    @FXML
    private Text valorO7;

    @FXML
    private Text valorO9;

    @FXML
    private Text valorX2;

    @FXML
    private Text valorX3;

    @FXML
    private Text valorX6;

    @FXML
    private Text valorX9;

    @FXML
    private Text valorX8;

    @FXML
    private Text valorX7;

    @FXML
    private Text valorX4;

    @FXML
    private Text valorX5;

    @FXML
    private CheckBox checkO;

    @FXML
    private CheckBox checkx;
    @FXML
    private Pane cuadricula1;

    @FXML
    private Pane cuadricula2;

    @FXML
    private Pane cuadricula3;

    @FXML
    private Pane cuadricula5;

    @FXML
    private Pane cuadricula4;

    @FXML
    private Pane cuadricula6;

    @FXML
    private Pane cuadricula7;

    @FXML
    private Pane cuadricula8;

    @FXML
    private Pane cuadricula9;

    @FXML
    private AnchorPane fondoloading;

    @FXML
    private Label msjTurno;



    ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    private void initialize(){

        try {
            checkx.setDisable(true);
            checkO.setDisable(true);
            ServerSocket server = new ServerSocket(3001);
            ThreadServerSocket threadServerSocket = new ThreadServerSocket(server,listaClientes,nameUser,puntosuser,fondoloading);

            new Thread(threadServerSocket).start();

            try {
                ThreadServerInputData threadServerInputData = new ThreadServerInputData(new ServerSocket(3003));
                threadServerInputData.addObserver(this);
                new Thread(threadServerInputData).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviar(){
        try {
            Socket socket =  new Socket("ip",3002);
            DataOutputStream data = new DataOutputStream(socket.getOutputStream());
            turno.setText("Turno del otro usuario");
            turno.setFill(Color.GREEN);
            data.writeUTF("true");
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("lelgio hasta qui update");
        String objectId= "0";
        try {
            Socket data = (Socket) o;
            DataInputStream inputStream = new DataInputStream(data.getInputStream());
            System.out.println(objectId);
            objectId = inputStream.readUTF();
            System.out.println(objectId);
            inputStream.close();
            listenerPane(objectId);
        } catch (IOException  e) {
            e.printStackTrace();
        }


    }


    public void listenerPane(String valor){
        Integer contador=0;
        for (int i=0; i<areajuego.getChildren().size(); i++){
            if (areajuego.getChildren().get(i) instanceof Text){
                if (valor.equals(areajuego.getChildren().get(i).getId())){
                    String nombreaux = "cuadricula"+valor.charAt(valor.length()-1);
                    System.out.println(nombreaux);
                    while (contador < areajuego.getChildren().size()){
                       if (areajuego.getChildren().get(contador) instanceof Pane){
                           if (areajuego.getChildren().get(contador).getId().equals(nombreaux)){
                               areajuego.getChildren().get(contador).setVisible(false);
                               areajuego.getChildren().get(i).setVisible(true);
                               System.out.println("holaaaaaaaaaaa");
                           }
                       }
                       contador++;
                    }

                }
            }
        }


    }

    public Boolean verificarValor(String valor ,String valorid){
        return valor.equals(valorid) ? true : false;
    }


    public void checkBoxListening(Text valorO, Text valorX,Pane cuadricula){
        if (checkO.isSelected() && !checkx.isSelected()){
            valorO.setVisible(true);
            valorX.setVisible(false);
            cuadricula.setVisible(false);
            try {
                Socket socket = new Socket("ip",3003);
                DataOutputStream dataPosicion = new DataOutputStream(socket.getOutputStream());
                dataPosicion.writeUTF(valorO.getId());
                dataPosicion.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (checkx.isSelected() && !checkO.isSelected()){
            System.out.println("segun if x");
            valorO.setVisible(false);
            valorX.setVisible(true);
            cuadricula.setVisible(false);
            try {
                Socket socket = new Socket("ip",3003);
                DataOutputStream dataPosicion = new DataOutputStream(socket.getOutputStream());
                dataPosicion.writeUTF(valorX.getId());
                dataPosicion.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("error");
        }
    }

}
