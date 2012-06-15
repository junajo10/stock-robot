package model.robot;

import java.io.IOException;
import java.net.Socket;

public class ClientRunner {
		private Socket s;
		private boolean shouldRun = true;

		public ClientRunner(Socket socket) {
			this.s = socket;
			shouldRun = true;
		}

		public boolean disconnect() {
			shouldRun = false;
			try {
				s.close();
			} catch (IOException e) {
			}
			
			return true;
		}
}
