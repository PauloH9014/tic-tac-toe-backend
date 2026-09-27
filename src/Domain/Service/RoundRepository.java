package Domain.Service;

import Domain.Model.Player;
import Domain.Store.PartidaStore;

import java.sql.SQLException;
import java.time.LocalTime;

public interface RoundRepository {
    Player findById(long idRound);
    Player saveinfoRound(String namePlayer, long durationRoundSeconds);
    Player updadeinfoRound(JogoServer jogoServer);
    Player deleteinfoRound(long idRound);
}
