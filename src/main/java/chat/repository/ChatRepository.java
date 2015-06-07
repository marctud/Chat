package chat.repository;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

public class ChatRepository {

	private static final List<Session> sessions = new ArrayList<Session>();

	public static void addSession(Session session) {
		sessions.add(session);
	}

	public static List<Session> getSessions() {
		return sessions;
	}

}
