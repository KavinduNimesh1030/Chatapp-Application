import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class ClientTwoFormController {

    static BufferedImage bimg;
    final int portNumber = 5000;
    public TextArea txtArea;
    public TextField txtMassage;
    public Button btnSend;
    public ImageView imgvImageView;
    public Hyperlink hyperLink;
    public Button btnImage;
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String userName;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    TextFlow textFlow;
    Text text;
    Image newimg;
    byte[] bytes;

    int count;
    String Path;
    File file1;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    String path;
    BufferedImage image1 ;

    static int mode = 0;

    public void initialize() {


        try {
            socket = new Socket("localhost", portNumber);
           /* bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            getName();
            listenForMassage();


        } catch (IOException e) {
            closeEveryThing(socket, dataInputStream, dataOutputStream);
        }

    }

    public void listenForMassage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                String massage;
                while (socket.isConnected()) {
                    try {
                       /* msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                        txtArea.appendText(msgFromGroupChat);*/
                        massage = dataInputStream.readUTF();
                        txtArea.appendText("\n" + massage);
                        System.out.println(massage);

                       /* Text text;
                        if(textFlow.getChildren().size()==0){
                            text = new Text(txtMassage.getText());
                        } else {
                            // Add new line if not the first child
                            text = new Text("\n" + txtMassage.getText());
                        }
                        if(txtMassage.getText().contains(":)")) {
                            ImageView imageView = new ImageView("C:\\Users\\User\\Downloads\\Kavindu.JPG");
                            // Remove :) from text
                            text.setText(text.getText().replace(":)"," "));
                            textFlow.getChildren().addAll(text, imageView);
                            txtArea.getChildrenUnmodifiable().addAll(text,imageView);
                        } else {
                            textFlow.getChildren().add(text);

                        }
                        txtMassage.clear();
                        txtMassage.requestFocus();
*/
                       /* byte[] sizeAr = new byte[4];
                        dataInputStream.read(sizeAr);
                        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                        byte[] imageAr = new byte[size];
                        dataInputStream.read(imageAr);

                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
                        txtArea.appendText(String.valueOf(image));
                        ImageIO.write(image, "jpg", new File("C:\\Users\\User\\Downloads\\Kavindu.JPG"));*/
                    } catch (IOException EOFException) {

                        closeEveryThing(socket, dataInputStream, dataOutputStream);
                    }

                }
            }
        }).start();
    }

    private void closeEveryThing(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        try {
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getName() {

        try {
            while (socket.isConnected()) {
                String name = ClientTwoLoginFromController.getUserName();
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

            while (socket.isConnected()) {

               /* String massageToSend=txtMassage.getText();
                bufferedWriterc.write(userName+" : "+ massageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();*/
                System.out.println("count "+count);
                if (count>0) {


                    new Mode().setMode(1);
                    System.out.println("mode value "+new Mode().getMode());
                    System.out.println(file1);
                    BufferedImage image = ImageIO.read(new File(file1.getAbsolutePath()));
                    //Image image1 =new Image("assert/Kavindu.JPG");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", byteArrayOutputStream);

                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                    dataOutputStream.write(size);
                    dataOutputStream.write(byteArrayOutputStream.toByteArray());
                    // imgvImageView.setImage(image1);
                    txtArea.appendText(String.valueOf(image));
                    System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
                    dataOutputStream.flush();

                    count = 0;
                    break;

                } else {
                    System.out.println("Massage");
                    // oos.writeObject(new Message<String>(txtMassage.getText()));
                    new Message<String>().setPayload(txtMassage.getText());
                    dataOutputStream.writeUTF(txtMassage.getText());
                    txtArea.appendText("\nme : " + txtMassage.getText());
                    dataOutputStream.flush();

                    break;
                }

              /*  BufferedImage image = ImageIO.read(new File("C:\\Users\\User\\Downloads\\Kavindu.JPG"));

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);

                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                dataOutputStream.write(size);
                dataOutputStream.write(byteArrayOutputStream.toByteArray());*/
                //----------------------------------
               /* textFlow = new TextFlow();
                textFlow.setPadding(new Insets(10));
                textFlow.setLineSpacing(10);

                VBox container = new VBox();
                container.getChildren().addAll(textFlow, new HBox(txtArea, btnSend));
                VBox.setVgrow(txtArea, Priority.ALWAYS);

                // Textfield re-sizes according to VBox
                txtArea.prefWidthProperty().bind(container.widthProperty().subtract(btnSend.prefWidthProperty()));*/

                //dataOutputStream.flush();
               /* if(textFlow.getChildren().size()==0){
                    text = new Text(txtMassage.getText());
                } else {
                    // Add new line if not the first child
                    text = new Text("\n" + txtMassage.getText());
                }
                // Remove :) from text
                text.setText(text.getText().replace(":)"," "));
                txtArea.getChildrenUnmodifiable().add(imageView);
                //---------------------------------
                dataOutputStream.flush();
                System.out.println("Flushed: " + System.currentTimeMillis());
                txtArea.requestFocus();*/


            }
        } catch (IOException e) {
            closeEveryThing(socket, dataInputStream, dataOutputStream);
            e.printStackTrace();
        }
    }

    public void btnImageSendOnAction(ActionEvent actionEvent) {

        new Message<BufferedImage>().setPayload(image1);
       /* try {
            //String appData = System.getenv("APPDATA");
            //File appDataDir = new File(appData);
            // Get a sub-directory named 'texture'
            File textureDir = new File(appDataDir, "texture");
            Desktop.getDesktop().open(textureDir.getAbsoluteFile());
            String path1 = textureDir.getAbsolutePath();
            Desktop.getDesktop().open(new File("C:\\Users\\User\\AppData\\Roaming\\texture"));
            path =(path1+"\\Kavindu.jpg");
            txtMassage.setText(path1+"\\Kavindu.jpg");
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //File file1 = new File(path+"\\Kavindu.jpg");
        try {
            socket.setKeepAlive(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (socket.isConnected()) {
            JFileChooser fileChooser = new JFileChooser();
            int res = fileChooser.showSaveDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
                file1 = file;
                txtMassage.setText(String.valueOf(file));
                count++;
            }

        } else {
            closeEveryThing(socket, dataInputStream, dataOutputStream);
        }

        //socket = new Socket("localhost", portNumber);
       /* try {
            while (socket.isConnected()) {

                BufferedImage image = ImageIO.read(new File("C:\\Users\\User\\AppData\\Roaming\\texture\\Kavindu.jpg"));
                //Image image1 =new Image("assert/Kavindu.JPG");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", byteArrayOutputStream);

                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                dataOutputStream.write(size);
                dataOutputStream.write(byteArrayOutputStream.toByteArray());
               // imgvImageView.setImage(image1);
                txtArea.appendText(String.valueOf(image));
                dataOutputStream.flush();
                break;
            }
        } catch (IOException e) {
            closeEveryThing(socket, dataInputStream, dataOutputStream);
            e.printStackTrace();
        }*/
    }
    public static int getMode(){
        return mode;
    }
    public static void setMode(){
        mode =0;
    }
}
