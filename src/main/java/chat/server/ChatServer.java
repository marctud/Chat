package chat.server;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import chat.camel.CamelRoutes;
import chat.repository.ChatRepository;

@ServerEndpoint(value = "/message")
public class ChatServer {

	@OnOpen
	public void onOpen(Session session) {
		System.out.println(session.getId() + " has opened a connection");
		ChatRepository.addSession(session);
		try {
			session.getBasicRemote().sendText("Connection Established");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		for (Session repSession : ChatRepository.getSessions()) {
			if (!session.getId().equals(repSession.getId())) {
				try {
					repSession.getBasicRemote().sendText("A new user has connected");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		for (Session repSession : ChatRepository.getSessions()) {
			if (!session.getId().equals(repSession.getId())) {
				try {
					repSession.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		CamelRoutes.sendMessage(message);
	}

	@OnClose
	public void onClose(Session session) {
		ChatRepository.getSessions().remove(session);
	}

}
