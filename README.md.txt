🕹️ Game-of-Old (Tic-Tac-Toe Full-stack)
Este é um projeto de Jogo da Velha robusto, desenvolvido para demonstrar a integração entre um servidor Java e uma interface Web moderna. A aplicação utiliza princípios de design de software avançados para garantir organização e escalabilidade.

🚀 Tecnologias Utilizadas
Back-end: Java 11 (Sem frameworks pesados, utilizando com.sun.net.httpserver).

Front-end: HTML5, CSS3 e JavaScript Vanilla (Fetch API).

Comunicação: API REST com troca de dados via JSON.

🏗️ Arquitetura e Design Patterns
O projeto foi construído seguindo o Domain-Driven Design (DDD), separando claramente as responsabilidades:

Domain: Contém as regras de negócio, entidades (Player, Tabuleiro) e exceções personalizadas (JogadaInvalida).

Infrastructure: Responsável pela camada de rede e servidor HTTP (GameServer).

Service: Coordena as operações do jogo.

Singleton Pattern: Utilizado para garantir que o estado do jogo (JogoServer ou PartidaStore) seja único durante a execução do servidor, evitando conflitos de dados.

🕹️ Funcionalidades
Identificação de Jogadores: Cadastro de nomes antes de iniciar a partida.

Validação em Tempo Real: O servidor valida se a casa está ocupada ou se a jogada é válida.

Verificação de Vitória/Empate: Lógica processada no Back-end que retorna o nome do vencedor.

Histórico de Partidas: Estrutura preparada para armazenar resultados via PartidaStore.

🛠️ Como Executar
Back-end:

Abra o projeto no IntelliJ IDEA.

Execute a classe Main localizada em src/Application.

O servidor iniciará na porta 8080.

Front-end:

Navegue até a pasta frontend.

Abra o arquivo index.html em qualquer navegador moderno.