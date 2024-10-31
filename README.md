# Java-Socket-Http-Server
A socket http server implemented in Java with url mapping feature , which helps rapid development of web project.

This branch contains a single java file the Runner.java that creates server and handlers user newer java approach.

```
package com;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Runner {
	public static void main(String[] args) throws IOException {
		int PORT = 8080;
		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
		Map<String, String> handlers = new HashMap<String, String>();
		handlers.put("/hello", "htmls/test.html");
		for (String key : handlers.keySet()) {
			String filePath = handlers.get(key);
			System.out.println("Defining handler for "+key+" at "+filePath);
			server.createContext(key, (exchange) -> {
				String content = "";
				try {
					content = Files.lines(Paths.get(filePath)).collect(Collectors.joining(System.lineSeparator()));
				} catch (Exception e) {
					System.out.println(e);
				}
				exchange.getResponseHeaders().add("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, content.getBytes().length);
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(content.getBytes());
				}
			});
			server.setExecutor(null); // Creates a default executor
			System.out.println("application running at http://localhost:"+PORT);
			server.start();
		}
	}
}

```
