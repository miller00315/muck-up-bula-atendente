package br.com.miller.farmaciaatendente.domain;

public enum PayMode {

    Dinheiro(1), Cartao(2);

    private int value;

    PayMode(int value) {
        this.value = value;
    }
}
