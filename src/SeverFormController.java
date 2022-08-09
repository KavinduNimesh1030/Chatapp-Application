import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SeverFormController {
     ServerSocket serverSocket;
    public TextArea txtArea;
    public TextField txtMassage;
    int PORT = 5000;



    public void initialize() {
//        ServerSocket serverSocket = new ServerSocket(PORT);
//        SeverFormController sever = new SeverFormController(serverSocket);
//        sever.startServer();
        startServer();
    }





    public void startServer() {
        try {
            ServerSocket  serverSocket = new ServerSocket(PORT);

        try {
            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();

                System.out.println("A new client has connected");
                txtArea.appendText("A new client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendOnAction(ActionEvent actionEvent) {
    }

    /* public void sendOnAction(ActionEvent actionEvent) throws IOException {
       *//* dataOutputStream.writeUTF(txtMassage.getText());
        txtArea.appendText("\n me :"+txtMassage.getText());
        dataOutputStream.flush();*//*
    }*/
}
