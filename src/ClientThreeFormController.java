import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClientThreeFormController {

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
                        byte[] sizeAr = new byte[4];
                        dataInputStream.read(sizeAr);
                        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                        byte[] imageAr = new byte[size];
                        dataInputStream.read(imageAr);
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                        //System.out.println("Received " + image.getHeight() + "x" + image.getWidth());
                        ImageView imageView = new ImageView();
                        imageView.setImage(Image.impl_fromPlatformImage(image));
                       // txtArea.getChildrenUnmodifiable().addAll(imageView);
                       txtArea.appendText(String.valueOf(dataInputStream.read(imageAr)));
                       break;

                    /*    byte[] sizeAr = new byte[4];
                        dataInputStream.read(sizeAr);
                        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                        byte[] imageAr = new byte[size];
                        dataInputStream.read(imageAr);

                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                       // System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());

                        ImageView imageView = new ImageView();
                        imageView.setImage(Image.impl_fromPlatformImage(image));
                        txtArea.appendText(String.valueOf(imageAr));

                        //ImageIO.write(image, "jpg", new File("C:\\Users\\User\\Downloads\\Kavindu.JPG"));*/
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
    private void getName() {

        try {
            while (socket.isConnected()) {
                String name = ClientThreeLoginFromController.getUserName();
                System.out.println(name);
                dataOutputStream.writeUTF(name);
                dataOutputStream.flush();
                break;
            }
        } catch (IOException e) {
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

           /* dataOutputStream.writeUTF(userName);
           txtArea.appendText("\n Name ="+txtMassage.getText());
           dataOutputStream.flush();
*/
            while (socket.isConnected()){
               /* String massageToSend=txtMassage.getText();
                bufferedWriterc.write(userName+" : "+ massageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();*/
                dataOutputStream.writeUTF(txtMassage.getText());
                txtArea.appendText("\nme : " + txtMassage.getText());
                dataOutputStream.flush();
                break;
            }
        } catch (IOException e) {
            closeEveryThing(socket,dataInputStream,dataOutputStream);
            e.printStackTrace();
        }
    }


}
