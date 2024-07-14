package serversideforxogame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerSideForXoGame extends Application {
    private ServerSocket myServerSocket;

    @Override
    public void start(Stage stage) throws Exception {
        StartStopServerBase serverBase = new StartStopServerBase();
        Parent root = serverBase;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // note that this code for test you may need some function to reach your logic
        Thread statusCheckThread = new Thread(() -> {
            while (true) {
                String checkState = StartStopServerBase.selectStatus;
                if ("Start".equals(checkState)) {
                    if (myServerSocket == null || myServerSocket.isClosed()) {
                        startServer();
                    }
                } else if ("Stop".equals(checkState)) {
                    if (myServerSocket != null && !myServerSocket.isClosed()) {
                        try {
                            myServerSocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(ServerSideForXoGame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                // Add a delay between checks to avoid high CPU usage
                try {
                    Thread.sleep(1000); // Adjust delay as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        statusCheckThread.setDaemon(true);
        statusCheckThread.start();
    }

    private void startServer() {
        try {
            myServerSocket = new ServerSocket(5013);
            System.out.println("Server listening on port 5013");

            while (true) {
                Socket mySocket = myServerSocket.accept();
                new ServerHandler(mySocket).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSideForXoGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    class ServerHandler extends Thread {
        private DataInputStream myDataInputStream;
        private DataOutputStream myDataOutStream;

        public ServerHandler(Socket socket) {
            try {
                myDataInputStream = new DataInputStream(socket.getInputStream());
                myDataOutStream = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            // you can call your function here to reach your logic for example you can make function for login and call it here 
            try {
                String username = myDataInputStream.readUTF();
                String password = myDataInputStream.readUTF();
                String myMassage = null;
                try {
                    myMassage = DAL.checkSignIn(username, password);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerSideForXoGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                myDataOutStream.writeUTF(username);

                if (myMassage.equals("Logged in successfully")) {
                    try {
                        DAL.login(username, password);
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerSideForXoGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } 
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    myDataInputStream.close();
                    myDataOutStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // For testing purposes, replace with your actual authentication logic
        private final String validUsername = "Ahmed";
        private final String validPassword = "123";

        private boolean authenticate(String username, String password) {
            return validUsername.equals(username) && validPassword.equals(password);
        }
    }
}
