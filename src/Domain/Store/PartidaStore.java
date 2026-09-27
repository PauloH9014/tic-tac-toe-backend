package Domain.Store;

import Domain.Model.Partida;
import Domain.Model.Player;
import Domain.Service.RoundRepository;

import java.util.ArrayList;

public class PartidaStore{
    private static PartidaStore storeWin; // statico é pertencer aquela classe!
    private ArrayList<Partida> partidas;

    private PartidaStore() {
        this.partidas = new ArrayList<>();
    }

    public void savePartida(Partida partida){
        partidas.add(partida);
    }

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }

    public static PartidaStore getStoreWin() {
        if (storeWin == null){
            storeWin = new PartidaStore();
        }
        return storeWin;
    }
}
