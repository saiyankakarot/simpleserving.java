import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class simpleserving {
    public static void main(String[] args) throws IOException {
        int port = 8000; // localhost:8000
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("Server started at http://localhost:" + port);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String htmlResponse = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <title>Java Localhost Server</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f4f9;
                            color: #333;
                            margin: 0;
                            padding: 20px;
                            display: flex;
                            flex-direction: column;
                            align-items: center;
                        }
                        h1 {
                            color: #5a67d8;
                        }
                        button {
                            background-color: #5a67d8;
                            color: white;
                            border: none;
                            padding: 10px 20px;
                            font-size: 16px;
                            border-radius: 5px;
                            cursor: pointer;
                            margin-top: 20px;
                            transition: background-color 0.3s ease;
                        }
                        button:hover {
                            background-color: #434190;
                        }
                        #message {
                            margin-top: 20px;
                            font-weight: bold;
                            color: #2d3748;
                        }
                    </style>
                </head>
                <body>
                    <h1>Welcome to Java Localhost Server!</h1>
                    <p>This page is served by your Java HTTP server.</p>
                    <button onclick="showMessage()">Click Me!</button>
                    <div id="message"></div>

                    <script>
                        function showMessage() {
                            document.getElementById('message').textContent = 'Button clicked! 🎉';
                        }
                    </script>
                </body>
                </html>
                """;

            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, htmlResponse.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(htmlResponse.getBytes());
            os.close();
        }
    }
}
