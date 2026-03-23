package Domain.Enums;

public enum Simbolo {

    X("X"),
    O("O"),
    EMPTY(" ");

    private final String valueSimbolo;

    Simbolo(String valueSimbolo) {
        this.valueSimbolo = valueSimbolo;
    }

    public boolean isEmpty(){
        return this == EMPTY;
    }

    public String getValueSimbolo() {
        return valueSimbolo;
    }
}
