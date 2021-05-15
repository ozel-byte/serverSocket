package sample.controller;

import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    @FXML
    private Pane areajuego;



    @FXML
    private Text nameUser;


    @FXML
    private Text puntosuser;


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

    Integer auxTurnos = 0;
    Boolean bandera = false;
    Boolean startBandera = false;

    ArrayList<String> casillas = new ArrayList<String>();
    ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    private void initialize(){

        enviar();

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



        for (int i=0; i<areajuego.getChildren().size(); i++){
            if (areajuego.getChildren().get(i) instanceof Pane){
                int finalI = i;
                areajuego.getChildren().get(i).setOnMouseClicked(mouseEvent -> {
                    System.out.println( areajuego.getChildren().get(finalI).getId());
                    areajuego.getChildren().get(finalI).setVisible(false);
                    int contador = 0;
                    if (checkx.isSelected() && !checkO.isSelected()){
                        String valorX = "valorX"+areajuego.getChildren().get(finalI).getId().charAt(areajuego.getChildren().get(finalI).getId().length()-1);
                        while(contador < areajuego.getChildren().size()){
                            if (areajuego.getChildren().get(contador) instanceof Text){
                                    if (areajuego.getChildren().get(contador).getId().equals(valorX)){
                                        areajuego.getChildren().get(contador).setVisible(true);
                                        sendDataCliente(areajuego.getChildren().get(contador).getId());
                                        bandera=true;
                                    }
                            }
                            contador++;
                        }
                    }else if(checkO.isSelected() && !checkx.isSelected()){
                        int contador2=0;
                        String valorO = "valorO"+areajuego.getChildren().get(finalI).getId().charAt(areajuego.getChildren().get(finalI).getId().length()-1);
                        while(contador2 < areajuego.getChildren().size()){
                            if (areajuego.getChildren().get(contador2) instanceof Text){
                                if (areajuego.getChildren().get(contador2).getId().equals(valorO)){
                                    areajuego.getChildren().get(contador2).setVisible(true);
                                    sendDataCliente(areajuego.getChildren().get(contador2).getId());
                                    bandera=false;
                                }
                            }
                            contador2++;
                        }
                    }else{
                        System.out.println("selecione el check correspondiente");
                    }

                });
            }
        }


    }

    public void sendDataCliente(String valor){
        try {
            Socket socket =  new Socket("ip",3004);
            DataOutputStream data = new DataOutputStream(socket.getOutputStream());
            msjTurno.setText("Tu turno Acabo");
            msjTurno.setStyle("-fx-background-color: #C70039");
            data.writeUTF(valor);
            data.close();
            startBandera=true;
            bloquearcheckBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void bloquearcheckBox(){
        checkx.setSelected(false);
        checkO.setSelected(false);
        checkO.setDisable(true);
        checkx.setDisable(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        String objectId= "0";
        try {
            Socket data = (Socket) o;
            DataInputStream inputStream = new DataInputStream(data.getInputStream());
            System.out.println(objectId);
            objectId = inputStream.readUTF();
            System.out.println(objectId);
            inputStream.close();
            String finalObjectId = objectId;
            Platform.runLater(() ->  listenerPane(finalObjectId));
        } catch (IOException  e) {
            e.printStackTrace();
        }


    }


    public void listenerPane(String valor){
        msjTurno.setText("Tu turno");
        msjTurno.setStyle("-fx-background-color: green");
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
                               msjTurno.setVisible(true);
                              if (startBandera){
                                  if (bandera){
                                      checkx.setDisable(false);
                                  }else{
                                      checkO.setDisable(false);
                                  }
                              }else {
                                  checkx.setDisable(false);
                                  checkO.setDisable(false);
                              }
                           }
                       }
                       contador++;
                    }

                }
            }
        }


    }





}
