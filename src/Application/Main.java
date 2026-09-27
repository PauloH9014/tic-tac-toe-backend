package Application;

import Domain.Enums.Simbolo;
import Domain.Model.Player;
import Domain.Model.PlayerReal;
import Domain.Service.JogoServer;
import Domain.Service.RoundRepository;
import Domain.Store.RoundRepositoryImpl;
import Infrastructure.GameServer;

import java.io.IOException;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        Player playerOne = new PlayerReal("Paulo", Simbolo.X);
        Player playerTwo = new PlayerReal("Pedro", Simbolo.O);

        try {
            RoundRepositoryImpl roundRepository = new RoundRepositoryImpl();
            JogoServer jogoServer = new JogoServer(playerOne,playerTwo, roundRepository);
            GameServer gameServer = new GameServer(jogoServer);
            gameServer.startedRouter();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar servidor: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}