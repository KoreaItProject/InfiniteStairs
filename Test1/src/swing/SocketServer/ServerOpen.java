package swing.SocketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerOpen extends Thread {
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private Socket socket;

    public ServerOpen() {
        try {
            this.socket = new Socket("localhost", 80);
            this.reader = new ObjectInputStream(this.socket.getInputStream());
            this.writer = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
    }
}
