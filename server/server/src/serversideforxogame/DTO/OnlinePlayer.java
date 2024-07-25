package serversideforxogame.DTO;

import serversideforxogame.DAL;
import serversideforxogame.OnlinePlayersStore;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class OnlinePlayer {

    private String username;
    private final Connector connector;
    private final Map<String, GameRequest> gameRequestMap;

    public OnlinePlayer(Socket socket) {
        connector = new Connector(socket);
        gameRequestMap = new HashMap<>();
    }

    public void handleRequest() {
        String request = connector.readMessage();
        dispatchRequest(request);
    }

    public void dispatchRequest(String request) {
        switch (request) {
            case "Login":
                String username = connector.readMessage();
                String password = connector.readMessage();
                String loginMessage = DAL.checkSignIn(username, password);

                if ("Logged in successfully".equals(loginMessage)) {
                    setUsername(username); 
                    OnlinePlayersStore.claimStore().addPlayer(this);
                }
                connector.sendMessage(loginMessage);
                break;
            case "SignUp":
                String singUpMessage = signUp();
                connector.sendMessage(singUpMessage);
                break;
            case "GetActivePlayers":
                connector.sendNumber(OnlinePlayersStore.claimStore().storeSize() - 1);

                for (OnlinePlayer player : OnlinePlayersStore.claimStore().getAllPlayers()) {
                    if (!player.getUsername().equals(getUsername())) {
                        connector.sendMessage(player.getUsername());
                    }
                }
                break;
            case "SendRequest":
                String opponentUsername = connector.readMessage();
                OnlinePlayer opponentPlayer = OnlinePlayersStore.claimStore().getPlayer(opponentUsername);
                opponentPlayer.addGameRequest(getUsername(), GameRequest.against(this));
                connector.sendMessage(getUsername());
                break;
            case "GetGameRequests":
                connector.sendNumber(gameRequestMap.size());
                for (GameRequest gameRequest : gameRequestMap.values()) {
                    connector.sendMessage(gameRequest.getOpponentPlayer().getUsername());
                }
                break;
            case "AcceptRequest":
                String acceptAgainst = connector.readMessage();
                OnlinePlayer acceptAgainstPlayer = getGameRequest(acceptAgainst).getOpponentPlayer();
                acceptAgainstPlayer.connect().sendMessage(request);
                GameManager.startNewGame(this, acceptAgainstPlayer);
                break;
            case "RefuseRequest":
                String refuseAgainst = connector.readMessage();
                System.out.println(getUsername() + " Refuse to play with" + refuseAgainst);
                getGameRequest(refuseAgainst).getOpponentPlayer().connect().sendMessage(request);
                break;
        }
    }

    private String signUp() {
        String username = connector.readMessage();
        String email = connector.readMessage();
        String password = connector.readMessage();

        return DAL.checkSignUp(username, email, password);
    }

    public Connector connect() {
        return this.connector;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void addGameRequest(String opponentUsername, GameRequest gameRequest) {
        gameRequestMap.put(opponentUsername, gameRequest);
    }

    public GameRequest getGameRequest(String username) {
        return gameRequestMap.remove(username);
    }

    @Override
    public String toString() {
        return "Player -> username: " + username;
    }

    public static class GameRequest {

        private final OnlinePlayer opponentPlayer;

        private GameRequest(OnlinePlayer opponentPlayer) {
            this.opponentPlayer = opponentPlayer;
        }

        public static GameRequest against(OnlinePlayer opponentPlayer) {
            return new GameRequest(opponentPlayer);
        }

        public OnlinePlayer getOpponentPlayer() {
            return this.opponentPlayer;
        }
    }
}
