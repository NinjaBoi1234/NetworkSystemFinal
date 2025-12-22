import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class MessageDispatcher {

    private final Map<String, ClientSession> clients = new ConcurrentHashMap<>();

    public void register(ClientSession session) {
        clients.put(session.getUsername(), session);
    }

    public void unregister(String username) {
        clients.remove(username);
    }

    public void dispatch(String from, String message) {
        if (message.startsWith("@")) {
            int space = message.indexOf(' ');
            if (space > 1) {
                String to = message.substring(1, space);
                String body = message.substring(space + 1);
                sendDirect(from, to, body);
                return;
            }
        }
        broadcast(from, message);
    }

    private void sendDirect(String from, String to, String body) {
        ClientSession target = clients.get(to);
        if (target != null) {
            target.send("[DM from " + from + "] " + body);
        } else {
            ClientSession sender = clients.get(from);
            if (sender != null) {
                sender.send("User not found: " + to);
            }
        }
    }

    private void broadcast(String from, String message) {
        for (ClientSession session : clients.values()) {
            if (!session.getUsername().equals(from)) {
                session.send("[" + from + "] " + message);
            }
        }
    }
}