package swing.SocketServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import swing.Setting;
import swing.SocketServer.InfoDTO.Info;

public class ServerOpen extends Thread {
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private Socket socket;
    InfoDTO infoDTO;

    public ServerOpen() {

    }

    @Override
    public void run() {
        try {
            int i = 0;
            if (i == 0) {
                Thread.sleep(3000);
                infoDTO = new InfoDTO();
                this.socket = new Socket(new Setting().getHost(), 80);
                this.writer = new ObjectOutputStream(this.socket.getOutputStream());
                this.reader = new ObjectInputStream(this.socket.getInputStream());

                infoDTO.setCommand(Info.DontStopServer);
                writer.writeObject(infoDTO);
                writer.flush();
            }

            while (true) {
                infoDTO = (InfoDTO) reader.readObject();
                if (infoDTO.getCommand() == Info.DontStopServer) {
                    Thread.sleep(10000);
                    System.out.println("서버유지중" + ++i * 10 + "초");
                    infoDTO.setCommand(Info.DontStopServer);
                    writer.writeObject(infoDTO);
                    writer.flush();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.run();
    }
}
