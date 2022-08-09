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

    public void initialize() {
        System.out.println("Enter your username for the group chat : ");
        txtArea.appendText("Enter your username for the group chat : ");

        // getName();
        try {
            socket = new Socket("localhost",portNumber);
           /* bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            listenForMassage();

        } catch (IOException e) {
            closeEveryThing(socket,bufferedReader,bufferedWriter);
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
                        closeEveryThing(socket,bufferedReader,bufferedWriter);
                    }

                }
            }
        }).start();
    }

    private void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
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
               /* String massageToSend=txtMassage.getText();
                bufferedWriter.write(userName+" : "+ massageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();*/
                dataOutputStream.writeUTF(txtMassage.getText());
                txtArea.appendText("\nme : " + txtMassage.getText());
                dataOutputStream.flush();
                break;
            }
        } catch (IOException e) {
            closeEveryThing(socket,bufferedReader,bufferedWriter);
            e.printStackTrace();
        }
    }


}
