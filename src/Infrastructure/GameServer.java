package Infrastructure;

import Domain.Exceptions.JogadaInvalida;
import Domain.Exceptions.QuadradoOcupadoException;
import Domain.Model.Partida;
import Domain.Service.JogoServer;
import Domain.Store.PartidaStore;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;

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
        httpServer.createContext("/historico", new HistoricoPartidaHandler());
        httpServer.start();
        System.out.println("Servidor rodando na porta 8080!");
    }

    class JogadaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }


            InputStream inputStream = exchange.getRequestBody();
            String bodyReceived = new String(inputStream.readAllBytes());

            System.out.printf("Jogada recebida: " + bodyReceived);

            String columReceived = bodyReceived.split("\"coluna\":\"")[1].split("\"")[0];
            String lineReceived = bodyReceived.split("\"linha\":\"")[1].split("\"")[0];

            try {
                jogoServer.realizerJogadas(columReceived, lineReceived);

                String simbolo = jogoServer.getUltimPlayer().getSimbolo().getValueSimbolo();
                String message  = "{\"status\": \"ok\", \"simbolo\": \"" + simbolo + "\"}";
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
            String playerWinner = jogoServer.verificarPlayerWin() ? jogoServer.getUltimPlayer().getNamePlayer():"";
            boolean playerDraw = jogoServer.empatePlayer();

            String message = "{\"vencedor\": \"" + playerWinner + "\", \"empate\": " + playerDraw + "}";
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
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

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200,message.length());
            exchange.getResponseBody().write(message.getBytes());
            exchange.getResponseBody().close();
        }
    }

    class HistoricoPartidaHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            PartidaStore listStorage = PartidaStore.getStoreWin();

            ArrayList<Partida> list = listStorage.getPartidas();

            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < list.size(); i++) {
                Partida p = list.get(i);
                json.append("{")
                        .append("\"vencedor\": \"").append(p.getNamePlay()).append("\",")
                        .append("\"duracao\": ").append(p.getDurationTime())
                        .append("}");

                if (i < list.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            String message = json.toString();

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, message.length());
            exchange.getResponseBody().write(message.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
