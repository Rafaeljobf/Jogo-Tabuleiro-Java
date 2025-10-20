# Jogo de Tabuleiro em Java

## Descrição do Projeto

### Visão Geral e Objetivo do Jogo

* Este projeto é uma simulação de um jogo de tabuleiro para computador, desenvolvido para comportar de 1 a 6 participantes simultaneamente.
* Cada jogador é identificado por uma cor única e tem sua posição rastreada no tabuleiro.
* O tabuleiro é composto por 40 casas, e todos os competidores iniciam sua jornada a partir da casa 0.
* O objetivo principal para vencer o jogo é ser o primeiro competidor a alcançar a casa 40.

### Tipos de Jogadores e Regras de Jogada

* O jogo é construído sobre os conceitos de herança e polimorfismo, apresentando três tipos distintos de jogadores que o usuário pode escolher.
* A partida deve ser configurada com pelo menos dois jogadores.
* Os tipos são:
    * **Jogador Sortudo**: A soma dos valores rolados nos dados é sempre maior ou igual a 7.
    * **Jogador Azarado**: A soma dos valores rolados nos dados é sempre menor ou igual a 6.
    * **Jogador Normal**: Pode obter tanto valores altos como baixos para a soma dos dados, sem restrições.
* A movimentação a cada rodada é definida pela soma dos valores de dois dados.
* Uma regra especial estipula que, caso um jogador obtenha dois resultados iguais nos dados, ele ganha o direito de jogar novamente.

### Tabuleiro Interativo e Casas Especiais

* O tabuleiro possui casas com efeitos que alteram a dinâmica da partida:
    * **Casas 10, 25, 38 (Casa Bloqueio)**: Fazem com que o jogador que parar nelas não jogue na próxima rodada.
    * **Casas 5, 15, 30 (Casa da Sorte)**: Permitem ao jogador avançar 3 casas extras, desde que ele não seja um **Jogador Azarado**.
    * **Casas 17, 27 (Casa Escolhe Jogador)**: O jogador pode escolher um adversário para voltar ao início do jogo.
    * **Casas 20, 35 (Casas Mágicas)**: O jogador troca de lugar com o competidor que está mais atrás no tabuleiro. Caso ele seja o último, nada acontece.
    * **Casa 13 (Casa Surpresa)**: O jogador tem seu tipo (sortudo, azarado ou normal) alterado aleatoriamente para um tipo diferente do atual.

### Interface e Recursos Técnicos

* A cada rodada, a interface do jogo exibe a posição atual de todos os jogadores no tabuleiro e indica de quem é a vez de jogar.
* O jogo oferece múltiplas formas de interação:
    * **Interface Gráfica (GUI)**: Uma interface visual e amigável criada com Java Swing.
    * **Interface de Console**: Permite jogar diretamente pelo terminal.
* Ao final da partida, o sistema declara o vencedor e apresenta um pódio com a posição final e a quantidade total de jogadas de cada competidor.
* O projeto inclui um **Modo Debug** via console, um recurso que permite ao usuário, em vez de rolar os dados, inserir manualmente o número da casa para a qual o jogador deverá se mover, facilitando a realização de testes.
