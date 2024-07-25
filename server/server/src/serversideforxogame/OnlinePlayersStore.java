package serversideforxogame;

import serversideforxogame.DTO.OnlinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OnlinePlayersStore {

    private static OnlinePlayersStore store;

    private final Map<String, OnlinePlayer> onlinePlayers;

    private OnlinePlayersStore() {
        onlinePlayers = new ConcurrentHashMap<>();
    }

    public static OnlinePlayersStore claimStore() {
        if (store == null) {
            store = new OnlinePlayersStore();
        }

        return store;
    }

    public void addPlayer(OnlinePlayer player) {
        System.out.println(player);
        onlinePlayers.put(player.getUsername(), player);

    }

    public OnlinePlayer getPlayer(String username) {
        return onlinePlayers.get(username);
    }

    public int storeSize() {
        return onlinePlayers.size();
    }

    public List<OnlinePlayer> getAllPlayers() {
        return new ArrayList<>(onlinePlayers.values());
    }
}
