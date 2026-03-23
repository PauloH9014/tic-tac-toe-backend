package Domain.Service;

import Domain.Exceptions.JogadaInvalida;
import Domain.Exceptions.QuadradoOcupadoException;

public interface JogoService {
    void realizerJogadas(String colum, String line)throws JogadaInvalida, QuadradoOcupadoException;
    boolean  verificarPlayerWin();
    boolean empatePlayer();
    void restartGame();
}
