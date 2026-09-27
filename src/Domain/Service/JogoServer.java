package Domain.Service;

import Domain.Enums.Simbolo;
import Domain.Exceptions.JogadaInvalida;
import Domain.Exceptions.QuadradoOcupadoException;
import Domain.Model.Partida;
import Domain.Model.Player;
import Domain.Model.Tabuleiro;
import Domain.Store.PartidaStore;
import Domain.Store.RoundRepositoryImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class JogoServer implements JogoService{
    private Tabuleiro tabuleiro;
    private List<Player> players;
    private Player playerAtual;
    private Player ultimPlayer;
    private PartidaStore partidaStore;
    private LocalDateTime inicioPartida;
    private RoundRepositoryImpl roundRepository;

    public JogoServer(Player playerOne, Player playerTwo, RoundRepositoryImpl roundRepository) {
        this.tabuleiro = new Tabuleiro();
        this.players = new ArrayList<>();
        this.players.add(playerOne);
        this.players.add(playerTwo);
        this.playerAtual = playerOne;
        this.partidaStore = PartidaStore.getStoreWin();
        this.inicioPartida = LocalDateTime.now();
        this.roundRepository = roundRepository;
    }

    @Override
    public void realizerJogadas(String colum, String line) throws JogadaInvalida, QuadradoOcupadoException {
        int columJogada = tabuleiro.conversePosition(colum);
        int lineJogada = tabuleiro.corveseLine(line);
        int indicePlayerAtual = players.indexOf(playerAtual);
        int indiceProximoPlayer = (indicePlayerAtual + 1) % 2;  // logica do impar/par

        tabuleiro.marcaJogada(columJogada,lineJogada, playerAtual.getSimbolo());
        this.ultimPlayer = playerAtual;
        playerAtual = players.get(indiceProximoPlayer);
    }

    private boolean verificadorCombinationHorizontal(Simbolo[][] gradeshorizon){
        if (gradeshorizon[0][0] == gradeshorizon[0][1] && gradeshorizon[0][1] == gradeshorizon[0][2] && !gradeshorizon[0][0].isEmpty()){
            return true;
        }
        if (gradeshorizon[1][0] == gradeshorizon[1][1] && gradeshorizon[1][1] == gradeshorizon[1][2] && !gradeshorizon[1][0].isEmpty()){
            return true;
        }
        if (gradeshorizon[2][0] == gradeshorizon[2][1] && gradeshorizon[2][1] == gradeshorizon[2][2] && !gradeshorizon[2][0].isEmpty()){
            return true;
        }
        return false;
    }

    private boolean verificadorCombinationVertical(Simbolo[][] gradeshorizon){
        if (gradeshorizon[0][0] == gradeshorizon[1][0] && gradeshorizon[1][0] == gradeshorizon[2][0] && !gradeshorizon[0][0].isEmpty()){
            return true;
        }
        if (gradeshorizon[0][1] == gradeshorizon[1][1] && gradeshorizon[1][1] == gradeshorizon[2][1] && !gradeshorizon[0][1].isEmpty()){
            return true;
        }
        if (gradeshorizon[0][2] == gradeshorizon[1][2] && gradeshorizon[1][2] == gradeshorizon[2][2] && !gradeshorizon[0][2].isEmpty()){
            return true;
        }
        return false;
    }

    private boolean verificadorCombinationDiagonal(Simbolo[][] gradeshorizon){
        if (gradeshorizon[0][0] == gradeshorizon[1][1] && gradeshorizon[1][1] == gradeshorizon[2][2] && !gradeshorizon[0][0].isEmpty()){
            return true;
        }
        if (gradeshorizon[0][2] == gradeshorizon[1][1] && gradeshorizon[1][1] == gradeshorizon[2][0] && !gradeshorizon[0][2].isEmpty()){
            return true;
        }
        return false;
    }


    @Override
    public boolean verificarPlayerWin() {
        Simbolo[][] gradesVarifications = tabuleiro.getGradeTabuleiro();
        boolean winnerEnd = verificadorCombinationHorizontal(gradesVarifications) || verificadorCombinationVertical(gradesVarifications) || verificadorCombinationDiagonal(gradesVarifications);

        if (winnerEnd){
            savePartida();
        }
        return winnerEnd;
    }

    private void savePartida(){
        String namePlayerWin = ultimPlayer.getNamePlayer();
        long infoTimePartida = ChronoUnit.SECONDS.between(inicioPartida, LocalDateTime.now());

        Partida saveinfoUser = new Partida(namePlayerWin, LocalDateTime.now(), infoTimePartida);

        partidaStore.savePartida(saveinfoUser);
        roundRepository.saveinfoRound(namePlayerWin, infoTimePartida);
    }

    @Override
    public boolean empatePlayer() {
        return tabuleiro.tabuleiroFull() && !verificarPlayerWin(); // me retorna se está com valores cheios e se é diferente method Win!
    }

    @Override
    public void restartGame() {
            this.tabuleiro = new Tabuleiro();   // seria o novo tabuleiro!
            this.playerAtual = players.get(0);  // volta a ser 0 pois vai passar se method realizar jogada!
            this.inicioPartida = LocalDateTime.now();   // reseta o tempo de jogo!
    }

    public Player getUltimPlayer() {
        return ultimPlayer;
    }
}
