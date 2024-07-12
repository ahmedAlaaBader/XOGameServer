/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversideforxogame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ahmed alaa
 */
public class ServerSideForXoGame extends Application {
    //ServerSocket myServerSocket;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new StartStopServerBase();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

//    public ServerSideForXoGame() {
//        try 
//        {
//            myServerSocket= new ServerSocket(5005);
//        } catch (IOException ex) 
//        {
//            Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        while(true)
//    {
//            try
//            {
//                Socket mySocket = myServerSocket.accept();
//                 new ChatHandeler(mySocket);
//                
//            } catch (IOException ex)
//            {
//                Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
//            }
//    }
//    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       // new ServerSideForXoGame();
    }
    
}
