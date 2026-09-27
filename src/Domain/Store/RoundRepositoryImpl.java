package Domain.Store;

import Domain.Model.Player;
import Domain.Service.JogoServer;
import Domain.Service.RoundRepository;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoundRepositoryImpl implements RoundRepository{
    private Connection connection;

    public RoundRepositoryImpl() throws SQLException{
        try {
            this.connection = DriverManager.getConnection(
                    DataBaseConfig.get("db.url"),
                    DataBaseConfig.get("db.user"),
                    DataBaseConfig.get("db.password"));
        } catch (SQLException e) {
            throw new RuntimeException("Error of Conective: "+ e.getMessage());
        }
    }
    @Override
    public Player findById(long idRound) {
        return null;
    }

    @Override
    public Player saveinfoRound(String namePlayer, long durationRoundSeconds) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO PartidaStore(namePlayer, secondTimePartida) VALUES (?, ?)"
            );
            ps.setString(1, namePlayer);
            ps.setLong(2, durationRoundSeconds);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar partida: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Player updadeinfoRound(JogoServer jogoServer) {
        return null;
    }

    @Override
    public Player deleteinfoRound(long idRound) {
        return null;
    }
}
