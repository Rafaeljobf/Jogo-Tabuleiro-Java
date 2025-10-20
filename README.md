Jogo de Tabuleiro em Java
Um jogo de tabuleiro simples desenvolvido em Java. O projeto utiliza conceitos de Programação Orientada a Objetos para criar um jogo de tabuleiro com diferentes tipos de casas e jogadores.

Funcionalidades
Diferentes Tipos de Jogadores: Os jogadores podem escolher entre três tipos de personagens, cada um com habilidades únicas:

Jogador Normal: Rola os dados normalmente.

Jogador Azarado: A soma dos dados rolados nunca será maior que 6.

Jogador Sortudo: A soma dos dados rolados nunca será menor que 7.

Casas Especiais: O tabuleiro contém várias casas especiais que afetam a jogabilidade:

Casa Bloqueio: O jogador que parar nesta casa fica bloqueado por uma rodada.

Casa Sorte: O jogador avança 3 casas extras (a menos que seja um Jogador Azarado).

Casa Surpresa: Transforma o tipo do jogador aleatoriamente.

Casa Mágica: Troca de posição com o jogador que estiver mais atrás no tabuleiro.

Casa Escolhe Jogador: Permite ao jogador escolher outro para voltar ao início do tabuleiro.

Interfaces de Usuário: O jogo oferece duas opções de interface:

Interface Gráfica (GUI): Uma interface visual desenvolvida com Swing.

Interface de Console: Uma interface baseada em texto para jogar no terminal.

Modo Debug: Um modo de jogo especial no console que permite mover os jogadores para qualquer casa do tabuleiro.

Como Executar
Pré-requisitos:

JDK (Java Development Kit) instalado.

Compilação:

Navegue até a pasta src do projeto.

Compile os arquivos .java usando o seguinte comando:

javac br/com/jogotabuleiro/*.java br/com/jogotabuleiro/modelo/*.java br/com/jogotabuleiro/visao/*.java
Execução:

Após a compilação, execute a classe Main com o seguinte comando:

java br.com.jogotabuleiro.Main
Ao iniciar, o jogo oferecerá as seguintes opções no console:

1) Jogo Normal (Console): Inicia o jogo no modo de texto.

2) Modo DEBUG (Console): Inicia o jogo no modo de depuração.

3) Jogo Gráfico (GUI): Inicia a interface gráfica do jogo.

Tecnologias Utilizadas
Java: Linguagem de programação principal.

Swing: Para a criação da interface gráfica (GUI).
