import java.io.Serializable;
import java.util.ArrayList;

public class MessageInfo implements Serializable {
    String message;
//    ArrayList<String> allClientList = new ArrayList<>();
    ArrayList<Integer> allClientList = new ArrayList<>();
    ArrayList<Integer> whichClients = new ArrayList<>();

//    Integer whichClient;
//    MessageInfo(String message) {
//        this.message = message;
//    }

    MessageInfo(String message, ArrayList<Integer> whichClients) {
        this.message = message;
        this.whichClients.addAll(whichClients);
    }

    MessageInfo(String message, ArrayList<Integer> allClients, ArrayList<Integer> whichClients) {
        this.message = message;
        this.allClientList.addAll(allClients);
        this.whichClients.addAll(whichClients);
    }

}
