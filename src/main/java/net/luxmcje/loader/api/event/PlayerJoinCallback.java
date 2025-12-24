package net.luxmcje.loader.api.event;

public interface PlayerJoinCallback {
    LuxEvent<PlayerJoinCallback> EVENT = new LuxEvent<>();

    void onJoin(String playerName);
}
