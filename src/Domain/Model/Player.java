package Domain.Model;

import Domain.Enums.Simbolo;

public abstract class Player{
    private final String namePlayer;
    private final Simbolo simbolo;

    public Player(String namePlayer, Simbolo simbolo) {
        this.namePlayer = namePlayer;
        this.simbolo = simbolo;
    }

    public abstract void jogadaPlayer(String colum, String line);

    public String getNamePlayer() {
        return namePlayer;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }
}
