
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws Exception {
		boolean headless = System.getenv("HEADLESS") != null || (args.length > 0 && args[0].equalsIgnoreCase("headless"));
		Bunny bunny = new Bunny("");
		if (!headless) {
			Window window = new Window(bunny);
		} else {
			startHeadlessServer(bunny);
		}
	}

	private static void startHeadlessServer(Bunny bunny) throws IOException {
		int port = 8080;
		String portEnv = System.getenv("PORT");
		if (portEnv != null) {
			try { port = Integer.parseInt(portEnv); } catch (NumberFormatException ignored) {}
		}

		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/", exch -> handleRoot(exch));
		server.createContext("/status", exch -> handleStatus(exch, bunny));
		server.createContext("/feed", exch -> handleFeed(exch, bunny));
		server.createContext("/play", exch -> handlePlay(exch, bunny));
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();

		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(() -> {
			synchronized (bunny) { bunny.passTime(); }
		}, 1, 1, TimeUnit.SECONDS);

		System.out.println("Headless server running on port " + port);
	}

	private static void handleRoot(HttpExchange exch) throws IOException {
		String resp = "Bunny headless server";
		exch.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
		exch.sendResponseHeaders(200, resp.getBytes().length);
		try (OutputStream os = exch.getResponseBody()) { os.write(resp.getBytes()); }
	}

	private static void handleStatus(HttpExchange exch, Bunny bunny) throws IOException {
		synchronized (bunny) {
			String json = String.format("{\"name\":\"%s\",\"age\":%d,\"happiness\":%d,\"energy\":%d,\"hunger\":%d,\"state\":\"%s\"}",
				bunny.strName == null ? "" : bunny.strName,
				bunny.bytAge,
				bunny.bytHappiness,
				bunny.bytEnergy,
				bunny.bytHunger,
				bunny.strState == null ? "" : bunny.strState);
			exch.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
			exch.sendResponseHeaders(200, json.getBytes().length);
			try (OutputStream os = exch.getResponseBody()) { os.write(json.getBytes()); }
		}
	}

	private static void handleFeed(HttpExchange exch, Bunny bunny) throws IOException {
		synchronized (bunny) { bunny.feed(); }
		handleStatus(exch, bunny);
	}

	private static void handlePlay(HttpExchange exch, Bunny bunny) throws IOException {
		synchronized (bunny) { bunny.playHeadless(); }
		handleStatus(exch, bunny);
	}

}
