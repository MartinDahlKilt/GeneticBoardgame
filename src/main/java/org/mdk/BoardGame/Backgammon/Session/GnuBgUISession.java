package org.mdk.BoardGame.Backgammon.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.Player.BackgammonPlayer;
import org.mdk.BoardGame.Backgammon.Player.PubEvalPlayer;

public class GnuBgUISession extends BackgammonSession {
	private Socket mSocket;
    private BufferedReader mReader;
    private Writer mWriter;
    
	public GnuBgUISession(int portnumber, boolean verbose) throws Exception {
		super(verbose);
		ServerSocket serverSocket = null;
		try {
            serverSocket = new ServerSocket(portnumber);
            serverSocket.setSoTimeout(3000);

            final ServerSocket locServerSocket = serverSocket;
            while (mSocket==null) {
                try {
                    mSocket = locServerSocket.accept();
                } catch (SocketTimeoutException ex) {
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            serverSocket.close();
            
            if (mSocket == null) {
                throw new InterruptedException(
                        "Server interrupted or no connection setup possible");
            }
            init();
        } catch (Exception ex) {
            if (serverSocket != null) {
                serverSocket.close();
            }
            close(ex.toString());
            throw ex;
        }

	}

    public void close(String message) {
        if (mSocket != null) {
            try {
                mWriter.write("0 CLOSING");
                if (message != null) {
                    mWriter.write(" " + message);
                }
                mWriter.write("\n");
                mWriter.flush();
                mSocket.close();
                mSocket = null;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }	
	
    private void init() throws IOException {

        mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(), "UTF-8"), 1);
        mWriter = new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8");

        mWriter.write("SERVUS JGAM 04 - This is JGammon Version " + "0.1" + "\n");
        mWriter.flush();
    }
    
    
	@Override
	public SessionResult play(BackgammonPlayer player, int numGames) {
		PubEvalPlayer opponent = new PubEvalPlayer();
		return play(player,opponent, numGames);
	}

}
