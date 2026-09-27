🎮 Jogo da Velha — Fullstack Java 11 + Web

> Projeto fullstack de Jogo da Velha desenvolvido com Java 11 puro no backend e HTML, CSS e JavaScript no frontend. O objetivo principal foi colocar em prática conceitos fundamentais de Orientação a Objetos, Padrões de Projeto e integração entre sistemas via API REST.


📌 Sobre o Projeto

Este projeto nasceu como um exercício prático de aprendizado em Java. A proposta era simples: criar um Jogo da Velha funcional onde o backend fosse inteiramente responsável pelas regras de negócio, enquanto o frontend cuidasse apenas da interface e da comunicação com o servidor.

O grande diferencial foi a escolha de não utilizar nenhum framework como Spring Boot. Todo o servidor HTTP foi construído com as bibliotecas nativas do Java 11, tornando o projeto um exercício valioso para entender o que acontece "por baixo dos panos" em aplicações web.


🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Backend | Java 11 (puro, sem frameworks) |
| Servidor HTTP | `com.sun.net.httpserver` (nativo do JDK) |
| Frontend | HTML5, CSS3, JavaScript (ES6) |
| Comunicação | API REST com JSON |
| Estilização | CSS Grid para o tabuleiro |


🏗️ Arquitetura

O projeto segue uma arquitetura em camadas bem definida, separando as responsabilidades de forma clara.

Infrastructure é a camada mais externa, responsável por receber as requisições HTTP do navegador e traduzir as respostas para JSON.

Service contém toda a lógica e regras do jogo — quem jogou, se alguém venceu, se empatou e como alternar os turnos.

Domain é o coração do sistema, onde vivem as entidades, enumeradores e exceções que modelam o problema.

Store é responsável por armazenar o histórico das partidas durante a execução da aplicação.


💡 Conceitos de OOP Aplicados

Classe Abstrata e Herança
A entidade `Player` foi modelada como uma classe abstrata que define os atributos comuns a qualquer jogador (nome e símbolo) e um método abstrato que cada tipo de jogador deve implementar à sua maneira. O `PlayerReal` herda dessa classe, implementando o comportamento específico do jogador humano. Essa estrutura foi pensada para que um `PlayerIA` possa ser adicionado futuramente sem nenhuma alteração no restante do sistema.

Interface e Polimorfismo
O `JogoService` foi definido como uma interface que estabelece o contrato de ações do jogo — realizar jogada, verificar vencedor, verificar empate e reiniciar. O `JogoServer` implementa essa interface, e o polimorfismo entra em cena na lista de jogadores: o sistema não sabe se está lidando com um humano ou uma IA, apenas chama o método e cada tipo de jogador responde à sua maneira.

Enum com Comportamento
O `Simbolo` não é apenas uma lista de constantes — ele carrega um método `isEmpty()` que permite verificar se uma célula está vazia diretamente no enum, tornando o código mais expressivo e legível.

Exceções Hierárquicas
As exceções foram organizadas em uma hierarquia própria. A `ExceptionGame` é a classe base (checked), e dela derivam `JogadaInvalida` e `QuadradoOcupadoException`. A `PartidaFinalizada`, por representar um erro de fluxo que não deveria ocorrer em condições normais, herda de `RuntimeException` e é unchecked. Essa distinção foi uma decisão arquitetural consciente.


🏛️ Padrões de Projeto

Singleton — PartidaStore
O histórico de partidas precisa ser único durante toda a execução da aplicação. O padrão Singleton garante que, independentemente de quantas classes acessem o `PartidaStore`, todas estarão lendo e escrevendo no mesmo objeto em memória.

Facade — JogoServer
O `JogoServer` age como uma fachada que esconde toda a complexidade do jogo por trás de métodos simples. O `GameServer` não precisa saber como o tabuleiro funciona, como a vitória é calculada ou como as partidas são salvas — ele simplesmente delega ao `JogoServer` e recebe uma resposta.


📐 Princípios SOLID

O projeto foi desenvolvido com atenção aos princípios SOLID, aplicados de forma prática e não apenas teórica.

O Single Responsibility está presente em cada classe com uma única razão para mudar: o `Tabuleiro` só sabe sobre o grid, o `PartidaStore` só sabe sobre armazenamento, e o `JogoServer` só sabe sobre o fluxo do jogo.

O Open/Closed garante que novos tipos de jogador possam ser adicionados sem modificar nenhuma linha do `JogoServer`.

O Liskov Substitution permite que qualquer `Player` — seja `PlayerReal` ou um futuro `PlayerIA` — seja usado no lugar da classe base sem quebrar o sistema.


🌐 API REST

O backend expõe quatro endpoints que o frontend consome via Fetch API:

| Método | Rota | Função |
|---|---|---|
| POST | `/jogada` | Envia as coordenadas de uma jogada |
| GET | `/status` | Consulta o estado atual do jogo |
| POST | `/reiniciar` | Reinicia o jogo no servidor |
| GET | `/historico` | Retorna o histórico de partidas |

A comunicação utiliza JSON tanto nas requisições quanto nas respostas, e os cabeçalhos CORS foram configurados manualmente no servidor para permitir a comunicação entre o frontend (porta 5500) e o backend (porta 8080).


✅ Funcionalidades

- Tabuleiro 3x3 interativo com alternância de turnos
- Detecção de vitória em todas as 8 combinações possíveis (horizontais, verticais e diagonais)
- Detecção de empate quando o tabuleiro está completo sem vencedor
- Validação de jogada inválida no frontend e no backend (dupla camada de segurança)
- Bloqueio de interação após o fim da partida
- Reinício completo sincronizado entre frontend e backend
- Histórico de partidas com nome do vencedor e tempo de duração
- Tela dedicada para visualização do histórico


▶️ Como Executar

Pré-requisitos: Java 11 ou superior instalado e o **Live Server** do VS Code para o frontend.

1. Clone o repositório e abra a pasta do projeto na sua IDE.

2. Execute a classe `Main.java` localizada em `src/Application/`. O console exibirá a mensagem confirmando que o servidor está rodando na porta 8080.

3. Abra o arquivo `index.html` com o Live Server. O frontend estará disponível em `http://127.0.0.1:5500`.

4. Digite os nomes dos dois jogadores e clique em **Iniciar Jogo**.

🚀 Próximos Passos

- Adicionar jogador IA com algoritmo Minimax
- Persistência do histórico em arquivo JSON ou banco de dados
- Deploy do backend em um serviço de nuvem (Railway ou Render)
- Deploy do frontend na Vercel
- Modo multiplayer online com WebSocket


👨‍💻 Paulo Henique Q.

Desenvolvido como projeto de aprendizado de Java OOP, padrões de projeto e integração fullstack.
