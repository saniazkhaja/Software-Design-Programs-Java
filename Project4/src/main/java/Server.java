import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server extends Controller{
    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<MessageInfo> callback;
    // ArrayList<String> allClients = new ArrayList<>();
    Server() {

    }

    Server(Consumer<MessageInfo> call){
        callback = call;
        server = new TheServer();
        server.start();
    }

    public class TheServer extends Thread{
        public void run() {
            try(ServerSocket mysocket = new ServerSocket(5555);){
                System.out.println("Server is waiting for a client!");

                while(true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    synchronized (allClients) {
//                        allClients.add("Client #" + count);
                        allClients.add(count);
                    }
                    MessageInfo newClient = new MessageInfo("client has connected to server: " + "client #" + count, allClients, allClients);
//                    newClient.allClientList.addAll(allClients);
                    System.out.println("client has connected to server: " + "client #" + count);
                    synchronized (callback) {
                        callback.accept(newClient);
                    }
                    synchronized (clients) {
                        clients.add(c);
                    }
                    c.start();
                    count++;
                }
            }//end of try
            catch(Exception e) {

            }
        }//end of while
    }

    class ClientThread extends Thread{
        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        public synchronized void updateClients(MessageInfo message) {
            for(int i = 0; i < clients.size(); i++) {
                int temp = allClients.get(i);
                //only messages clients in arraylist
                if (message.whichClients.contains(temp)) {
                    ClientThread t = clients.get(i);
                    try {
                        System.out.println("Updating Client #" + i + " size is: " + message.allClientList.size());
                        t.out.writeObject(message);
                    }
                    catch(Exception e) {}
                }
            }
        }

        public void run(){
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            MessageInfo message = new MessageInfo("new client on server: client #"+count, allClients, allClients);
            System.out.println("size of allClients: " + allClients.size());
            System.out.println("size of message allClients: " + message.allClientList.size());
//            message.allClientList.addAll(allClients);
            System.out.println("new client on server: client #"+count);
            updateClients(message);

            while(true) {
                try {
                    MessageInfo data;
                    synchronized (in) {
                        data = (MessageInfo) in.readObject();
                    }
                    synchronized (callback) {
                        callback.accept(data);
                    }
                    String temp = data.message;
                    data.message = "client: " + count + " sent: " + temp;
                    data.allClientList = allClients;
                    System.out.println(data.message + data.allClientList.size());
                    updateClients(data);
                }
                catch(Exception e) {
                    synchronized (allClients) {
//                        for (int i = 0; i < allClients.size(); i++) {
//                            String temp = allClients.get(i);
//                            String clientName = "Client #" + count;
//                            if (temp.equals(clientName)) {
//                                allClients.remove(i);
//                                break;
//                            }
//                        }
                        for (int i = 0; i < allClients.size(); i++) {
                            int temp = allClients.get(i);
                            if (temp == count) {
                                allClients.remove(i);
                            }
                        }
                        System.out.println("size before " + allClients.size());
//                        allClients.remove(count-1);
                        System.out.println("size after " + allClients.size());
                    }
                    synchronized (clients) {
                        clients.remove(this);
                    }
                    MessageInfo clientLeft = new MessageInfo("Client #"+count+" has left the server!", allClients, allClients);
//                    clientLeft.allClientList = allClients;
                    System.out.println("Client #"+count+" has left the server!");
                    synchronized (callback) {
                        callback.accept(clientLeft);
                    }
                    updateClients(clientLeft);

                    break;
                }
            }
        }//end of run
    }//end of client thread
}














