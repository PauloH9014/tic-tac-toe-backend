package Infrastructure;

import Domain.Exceptions.JogadaInvalida;
import Domain.Exceptions.QuadradoOcupadoException;
import Domain.Service.JogoServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

public class GameServer {
    private JogoServer jogoServer;
    private HttpServer httpServer;

    public GameServer(JogoServer jogoServer) throws IOException{
        this.jogoServer = jogoServer;
        this.httpServer = HttpServer.create(new InetSocketAddress(8080),0);
    }

    public void startedRouter() {
        httpServer.createContext("/jogada", new JogadaHandler());
        httpServer.createContext("/status", new StatusHandler());
        httpServer.createContext("/reiniciar", new ReiniciarHandler());
        httpServer.start();
        System.out.println("Servidor rodando na porta 8080!");
    }

    class JogadaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String bodyReceived = new String(inputStream.readAllBytes());

            String columReceived = bodyReceived.split("\"coluna\":\"")[1].split("\"")[0];
            String lineReceived = bodyReceived.split("\"linha\":\"")[1].split("\"")[0];

            try {
                jogoServer.realizerJogadas(columReceived, lineReceived);

                String message  = "{\"status\": \"ok\"}";
                exchange.sendResponseHeaders(200, message.length());
                exchange.getResponseBody().write(message.getBytes());
                exchange.getResponseBody().close();

            } catch (JogadaInvalida e) {
                String message = "{\"erro\": \"" + e.getMessage() + "\"}";
                exchange.sendResponseHeaders(400, message.length());
                exchange.getResponseBody().write(message.getBytes());
                exchange.getResponseBody().close();

            } catch (QuadradoOcupadoException e) {
                String message = "{\"erro\": \"" + e.getMessage() + "\"}";
                exchange.sendResponseHeaders(400, message.length());
                exchange.getResponseBody().write(message.getBytes());
                exchange.getResponseBody().close();
            }

        }
    }

    class StatusHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            boolean playerWinner = jogoServer.verificarPlayerWin();
            boolean playerDraw = jogoServer.empatePlayer();

            String message = "{\"vencedor\": " + playerWinner + ", \"empate\": " +playerDraw+ "}";

            exchange.sendResponseHeaders(200,message.length());
            exchange.getResponseBody().write(message.getBytes());
            exchange.getResponseBody().close();
        }
    }

    class ReiniciarHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            jogoServer.restartGame();

            String message = "{\"status\": \"ok\"}";

            exchange.sendResponseHeaders(200,message.length());
            exchange.getResponseBody().write(message.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
