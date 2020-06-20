package com.syntaxphoenix.syntaxapi.net.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.syntaxphoenix.syntaxapi.net.AsyncSocketServer;
import com.syntaxphoenix.syntaxapi.threading.SynThreadFactory;

public class HttpServer extends AsyncSocketServer {

	private RequestGate gate;
	private RequestHandler handler;
	private RequestSerializer serializer;

	public HttpServer() {
		super();
	}

	public HttpServer(SynThreadFactory factory) {
		super(factory);
	}

	public HttpServer(ExecutorService service) {
		super(service);
	}

	public HttpServer(SynThreadFactory factory, ExecutorService service) {
		super(factory, service);
	}

	public HttpServer(int port) {
		super(port);
	}

	public HttpServer(int port, SynThreadFactory factory) {
		super(port, factory);
	}

	public HttpServer(int port, ExecutorService service) {
		super(port, service);
	}

	public HttpServer(int port, SynThreadFactory factory, ExecutorService service) {
		super(port, factory, service);
	}

	public HttpServer(int port, InetAddress address) {
		super(port, address);
	}

	public HttpServer(int port, InetAddress address, SynThreadFactory factory) {
		super(port, address, factory);
	}

	public HttpServer(int port, InetAddress address, ExecutorService service) {
		super(port, address, service);
	}

	public HttpServer(int port, InetAddress address, SynThreadFactory factory, ExecutorService service) {
		super(port, address, factory, service);
	}

	/*
	 * 
	 */

	public void setHandler(RequestHandler handler) {
		this.handler = handler;
	}

	public RequestHandler getHandler() {
		return handler;
	}

	/*
	 * 
	 */

	public void setGate(RequestGate gate) {
		this.gate = gate;
	}

	public RequestGate getGate() {
		return gate;
	}

	/*
	 * 
	 */

	public void setSerializer(RequestSerializer serializer) {
		this.serializer = serializer;
	}

	public RequestSerializer getSerializer() {
		return serializer;
	}

	/*
	 * 
	 */

	@Override
	protected void handleClientAsync(Socket socket) throws Throwable {

		HttpWriter writer = new HttpWriter(new PrintStream(socket.getOutputStream()));

		if (serializer == null) {
			Answer.SERVER_ERROR.respond("error", "No message serializer was registered, Sorry!").write(writer)
					.clearResponse();
			writer.close();
			socket.close();
			throw new IllegalStateException("Serializer can't be null!");
		}

		if (handler == null) {
			Answer.SERVER_ERROR.respond("error", "No message handler was registered, Sorry!").write(writer)
					.clearResponse();
			writer.close();
			socket.close();
			throw new IllegalStateException("Handler can't be null!");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String line = reader.readLine();

		if (line == null) {
			Answer.NO_CONTENT.write(writer);
			socket.close();
			return;
		}

		String[] info = line.split(" ");

		ReceivedRequest request = new ReceivedRequest(RequestType.fromString(info[0]),
				info[1].replaceFirst("/", "").split("/"));

		while ((line = reader.readLine()) != null) {
			request.parseHeader(line);
			if (line.isEmpty())
				break;
		}

		/*
		 * 
		 */

		if (gate != null) {
			RequestState state = gate.acceptRequest(writer, request);
			if (state.accepted()) {
				if (request.hasHeader("expect")) {
					if (((String) request.getHeader("expect")).contains("100-continue"))
						Answer.CONTINUE.write(writer);
				}
			} else {
				if (!state.message())
					new Answer(ContentType.JSON).code(ResponseCode.BAD_REQUEST)
							.respond("error", "method or contenttype is not supported").write(writer);
				reader.close();
				writer.close();
				socket.close();
				return;
			}
		} else {
			if (request.hasHeader("expect")) {
				if (((String) request.getHeader("expect")).contains("100-continue"))
					Answer.CONTINUE.write(writer);
			}
		}

		/*
		 * 
		 */

		if (!request.hasHeader("Content-Length")) {
			new Answer(ContentType.JSON).code(ResponseCode.LENGTH_REQUIRED).respond("error", "no content length given")
					.write(writer);
			reader.close();
			writer.close();
			socket.close();
			return;
		}

		int length = ((Number) request.getHeader("Content-Length")).intValue();

		StringBuilder builder = new StringBuilder();

		if (length != 0) {
			char[] buffer;
			if (length <= 1024) {
				buffer = new char[length];
				reader.read(buffer, 0, length);
				builder.append(buffer);
			} else {
				buffer = new char[1024];
				while (reader.read(buffer, 0, buffer.length) != -1) {
					builder.append(buffer);
				}
			}
		}

		request.setData(serializer.serialize(builder.toString()));

		if (handler.handleRequest(socket, writer, request)) {
			reader.close();
			writer.close();
			socket.close();
		}

	}

}
