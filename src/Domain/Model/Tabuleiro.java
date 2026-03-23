package Domain.Model;

import Domain.Enums.Simbolo;
import Domain.Exceptions.JogadaInvalida;
import Domain.Exceptions.QuadradoOcupadoException;

public class Tabuleiro {
    Simbolo[][] gradeTabuleiro;

    public Tabuleiro() {
        this.gradeTabuleiro = new Simbolo[3][3];

        for (int l= 0; l < 3; l++){
            for (int c = 0; c < 3; c++){
                gradeTabuleiro[l][c] = Simbolo.EMPTY;
            }
        }
    }

    private void validationPosicao(int colum, int line)throws JogadaInvalida, QuadradoOcupadoException {
        if (colum < 0 || colum > 2){
            throw new JogadaInvalida("Colum dont can be empty");
        }
        if (line < 0 || line > 2){
            throw new JogadaInvalida("Line dont can be empty");
        }
        if (!gradeTabuleiro[line][colum].isEmpty()){
            throw new QuadradoOcupadoException("Jogada dont can be empty");
        }
    }

    public void marcaJogada(int colum, int line, Simbolo simbolo) throws JogadaInvalida, QuadradoOcupadoException {
        validationPosicao(colum,line);
        gradeTabuleiro[line][colum] = simbolo;

    }

    public boolean tabuleiroFull(){
        for (int l = 0; l < 3; l++) {
            for (int c = 0; c < 3; c++) {
                if (gradeTabuleiro[l][c].isEmpty()){
                    return false; // se ele achar um valor vazio return false!
                }
            }
        }
        return true; // se o tabuleiro tiver cheio, ele vai me retornar verdadeiro!
    }

    public int conversePosition(String letra)throws JogadaInvalida {
        int colum;
        switch (letra){
            case "A": colum = 0; break;
            case "B": colum = 1; break;
            case "C": colum = 2; break;
            default:
                throw new JogadaInvalida("Colnum invalid!");
     }
     return colum;
    }

    public int corveseLine(String number)throws JogadaInvalida{
        int linha;
        switch (number){
            case "1": linha = 0; break;
            case "2": linha = 1; break;
            case "3": linha = 2; break;
            default:
                throw new JogadaInvalida("line invalid!");
        }
        return linha;
    }

    public Simbolo[][] getGradeTabuleiro() {
        return gradeTabuleiro;
    }
}
