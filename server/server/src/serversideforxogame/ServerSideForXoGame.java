package serversideforxogame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import serversideforxogame.DTO.OnlinePlayer;

public class ServerSideForXoGame extends Application {
    private ServerSocket myServerSocket;
    private Thread thread;
    private final List<String> gameRequests = new ArrayList<>();

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
                    if ((myServerSocket != null) && (!myServerSocket.isClosed())) {
                        try {

                            myServerSocket.close();
                            if (thread != null) {
                                thread.interrupt();
                            }
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
        thread = new Thread(() -> {
            try {
                myServerSocket = new ServerSocket(5013);
                System.out.println("Server listening on port 5013");

                while (!myServerSocket.isClosed()) {
                    try {
                        Socket mySocket = myServerSocket.accept();
                        new ServerHandler(mySocket).start();
                    } catch (IOException e) {
                        if (myServerSocket.isClosed()) {
                            System.out.println("Server stopped.");
                        } else {
                            Logger.getLogger(ServerSideForXoGame.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerSideForXoGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    class ServerHandler extends Thread {
        private final OnlinePlayer player;

        public ServerHandler(Socket socket) {
            player = new OnlinePlayer(socket);
        }

        @Override
        public void run() {
            do {
                player.handleRequest();
            } while (player.connect().isConnected());
        }

        private void sendGameRequestsToClient(DataOutputStream output) {
            synchronized (gameRequests) {
                try {
                    if (gameRequests.isEmpty()) {
                        System.out.println("No game requests to send.");
                        output.writeInt(0);
                        output.flush();
                        return;
                    }

                    int requestSize = gameRequests.size();
                    output.writeInt(requestSize);
                    System.out.println("Sending " + requestSize + " game requests to client.");

                    for (String request : gameRequests) {
                        output.writeUTF(request);
                        System.out.println("Sent request: " + request);
                    }

                    output.flush();
                    System.out.println("All game requests sent successfully.");

                } catch (IOException e) {
                    System.err.println("Error sending game requests to client: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // For testing purposes, replace with your actual authentication logic
//        private final String validUsername = "Ahmed";
//        private final String validPassword = "123";
//
//        private boolean authenticate(String username, String password) {
//            return validUsername.equals(username) && validPassword.equals(password);
//        }
        }
    }
}