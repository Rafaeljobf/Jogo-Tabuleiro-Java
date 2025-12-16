package br.com.jogotabuleiro.modelo;

public class CasaFactory {

    public static Casa criarCasa(String tipo) {
        switch (tipo) {
            case "Bloqueio": return new CasaBloqueio();
            case "Sorte":    return new CasaSorte();
            case "Surpresa": return new CasaSurpresa();
            case "Magica":   return new CasaMagica();
            case "Escolhe Jogador": return new CasaEscolheJogador();
            default:         return new CasaNormal();
        }
    }
}