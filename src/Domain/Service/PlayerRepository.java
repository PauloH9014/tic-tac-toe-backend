package Domain.Service;

import Domain.Model.Player;

public interface PlayerRepository {
    Player findById(long idPlayer);
    Player saveinfoPlayer(Player player);
    Player updadeinfoPlayer(Player player);
    Player deleteinfoPlayer(long idPlayer);
}
