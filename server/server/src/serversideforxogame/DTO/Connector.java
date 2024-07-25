package serversideforxogame.DTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {
    private final Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;

    public Connector(Socket socket) {
        this.socket = socket;
        try {
            this.reader = new DataInputStream(socket.getInputStream());
            this.writer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, exception);
        }
    }

    public String readMessage() {
        String message = null;
        try {
            message = reader.readUTF();
        } catch (IOException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, exception);
        }

        return message;
    }

    public void sendMessage(String message) {
        try {
            writer.writeUTF(message);
        } catch (IOException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, exception);
        }
    }

    public void sendNumber(Integer number) {
        try {
            writer.writeInt(number);
        } catch (IOException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, exception);
        }
    }

    public void sendBoolean(Boolean value) {
        try {
            writer.writeBoolean(value);
        } catch (IOException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, exception);
        }
    }

    public boolean isConnected() {
        return this.socket.isConnected();
    }
}