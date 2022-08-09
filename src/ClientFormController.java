import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientFormController {

    final int portNumber = 5000;
    public TextArea txtArea;
    public TextField txtMassage;
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String userName;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    int count = 0;

    public void initialize() {
        System.out.println("Enter your username for the group chat : ");
        txtArea.appendText("Enter your username for the group chat : ");


        try {
            socket = new Socket("localhost",portNumber);
           /* bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            getName();
            listenForMassage();

        } catch (IOException e) {
            closeEveryThing(socket,dataInputStream,dataOutputStream);
        }

    }

    private void getName() {

        try {
            while (socket.isConnected()) {
                String name = ClientLoginFormController.getUserName();
                System.out.println(name);
                dataOutputStream.writeUTF(name);
                dataOutputStream.flush();
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void listenForMassage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                String massage;
                while(socket.isConnected()){
                    try {
                       /* msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                        txtArea.appendText(msgFromGroupChat);*/
                        massage = dataInputStream.readUTF();
                        txtArea.appendText("\n"+massage);
                        System.out.println(massage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEveryThing(socket,dataInputStream,dataOutputStream);
                    }

                }
            }
        }).start();
    }

    private void closeEveryThing(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        try {
            if (dataInputStream!=null){
                dataInputStream.close();
            }
            if(dataOutputStream!=null){
                dataOutputStream.close();
            }
            if(socket!=null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void sendOnAction(ActionEvent actionEvent) throws IOException {
     /*   out.writeUTF(txtMassage.getText());
        txtArea.appendText("\n me :" + txtMassage.getText());
        out.flush();*/
        try {


//            bufferedWriter.write(userName);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//            dataOutputStream.writeUTF(userName);
//            txtArea.appendText("\n Name ="+txtMassage.getText());
//            dataOutputStream.flush();
           /* userName = txtMassage.getText();
            dataOutputStream.writeUTF(userName);
            dataOutputStream.flush();*/


            while (socket.isConnected()){
               /* if(count==0){

                    String name = ClientLoginFormController.getUserName();
                    System.out.println(name);
                    dataOutputStream.writeUTF(name);
                    dataOutputStream.flush();
                    break;
                }else {*/
               /* String massageToSend=txtMassage.getText();
                bufferedWriter.write(userName+" : "+ massageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();*/
                    dataOutputStream.writeUTF(txtMassage.getText());
                    txtArea.appendText("\nme : " + txtMassage.getText());
                    dataOutputStream.flush();
                    break;
               // }
            }
            count++;
        } catch (IOException e) {
            closeEveryThing(socket,dataInputStream,dataOutputStream);
            e.printStackTrace();
        }
    }


}
