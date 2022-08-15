import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private final Socket socket;
    DataOutputStream out;
    DataInputStream in;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
         /*   this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            clientHandlers.add(this);*/
           /* this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMassage("Server : " + clientUserName + " has entered tha chat");*/

            this.out = new DataOutputStream(socket.getOutputStream());
            this.in = new DataInputStream(socket.getInputStream());
            this.clientUserName = in.readUTF();
            clientHandlers.add(this);
            broadcastMassage("Server : " + clientUserName + " has entered the chat", 1);

        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(socket, in, out);
        }
    }

    private void broadcastMassage(String massageToSend, int number) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)) {
                    /*clientHandler.bufferedWriter.write(massageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();*/
                    clientHandler.out.writeUTF(massageToSend);
                    clientHandler.out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, in, out);

            }

        }
    }

    private void broadcastMassage(String massageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)) {
                    /*clientHandler.bufferedWriter.write(massageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();*/
                    clientHandler.out.writeUTF(clientUserName + " : " + massageToSend);
                    clientHandler.out.flush();

                }
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, in, out);

            }

        }
    }

    /* @Override
     public void run() {
         String massageFromClient;

         while (socket.isConnected()) {


             try {
                 int mode = new Mode().getMode();
                 System.out.println("mode "+mode);
                 Message<?> msg = (Message<?>)new Message<>();
                 if (mode == 1) {
                     System.out.println("image");
                     try {
                         int size = 0;
                         byte[] sizeAr = new byte[4];
                         in.read(sizeAr);
                         size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                         byte[] imageAr = new byte[size];
                         // in.read(imageAr);
                         broadcastMassage(imageAr);
                         new Mode().setMode(0);
                         break;

                     }catch (NegativeArraySizeException e){
                         e.printStackTrace();
                     }

                 } else if(mode == 0) {
                     if(new Mode().getMode()==0) {
                         System.out.println("massage");
                         if(in.readUTF().isEmpty()) {
                             massageFromClient = in.readUTF();
                             broadcastMassage(massageFromClient);

                         }else {
                             System.out.println("Image code");
                         }


                     }else if(new Mode().getMode()==1){
                         new Mode().setMode(1);
                     }

                 }
             } catch (IOException e) {
                 e.printStackTrace();
                 closeEverything(socket, in, out);
                 break;
             }

         }
     }
 */
    public void run() {
        String massageFromClient;

        while (socket.isConnected()) {
            try {
                massageFromClient = in.readUTF();
                broadcastMassage(massageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, in, out);
                break;
            }

        }
    }

    /* @Override
    public void run() {
        String massageFromClient;

        while (socket.isConnected()) {
                try {

                    int size = 0;
                    try {
                        byte[] sizeAr = new byte[4];
                        in.read(sizeAr);
                         size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                        byte[] imageAr = new byte[size];
                       // in.read(imageAr);
                        broadcastMassage(imageAr);

                        break;
                    }catch (NegativeArraySizeException e) {



                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    closeEverything(socket, in, out);
                    break;
                }
                //in.read(imageAr);


        }
    }*/
    private void broadcastMassage(byte[] imageAr) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)) {
                    /*clientHandler.bufferedWriter.write(massageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();*/
                    clientHandler.out.write(imageAr);

                    clientHandler.out.flush();

                }
            } catch (IOException e) {
                e.printStackTrace();
                closeEverything(socket, in, out);

            }

        }
    }

    private void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        removeClientHandler();
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

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMassage("Server : " + clientUserName + " has left the chat", 1);
    }
}