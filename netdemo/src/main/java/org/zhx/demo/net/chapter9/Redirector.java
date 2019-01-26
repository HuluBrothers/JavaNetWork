package org.zhx.demo.net.chapter9;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Redirector {
    private static final Logger logger = Logger.getLogger("Redirector");

    private String site;
    private int port;

    public Redirector(String site, int port) {
        this.site = site;
        this.port = port;
    }

    public void start() {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Redirecting connections on port "
                    + server.getLocalPort() + " to " + this.site);

            while (true) {
                try {
                    Socket connect = server.accept();
                    pool.submit(new HTTPHandler(connect));
                } catch (IOException ex) {
                    logger.warning("Exception accepting connection");
                } catch (RuntimeException ex) {
                    logger.log(Level.SEVERE, "Unexpected error", ex);
                }
            }
        } catch (BindException ex) {
            logger.log(Level.SEVERE, "Could not start server.", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error opening server socket", ex);
        }
    }

    private class HTTPHandler implements Callable<Void> {
        private final Socket connection;

        public HTTPHandler(Socket conn) {
            this.connection = conn;
        }

        @Override
        public Void call() throws Exception {
            try {
                Writer out = new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream()));
                Reader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));

                StringBuilder request = new StringBuilder();
                int c = -1;
                while (true) {
                    c = in.read();
                    if (c == '\r' || c == '\n') {
                        break;
                    }
                    request.append((char) c);
                }

                String get = request.toString();
                String pieces[] = get.split("\\w*");
                String theFile = pieces[1];

                if (get.indexOf("HTTP") != -1) {
                    out.write("HTTP/1.0 302 FOUND\r\n");
                    Date now = new Date();
                    out.write("Date: " + now + "\r\n");
                    out.write("Server: Redirector 1.1\r\n");
                    out.write("Location: " + site + theFile + "\r\n");
                    out.write("Content-type: text/html; charset=UTF-8\r\n\r\n");
                    out.flush();
                }
                out.write("<HTML><HEAD><TITLE>Document moved</TITLE></HEAD>\r\n");
                out.write("<BODY><H1>Document moved</H1>\r\n");
                out.write("The document " + theFile
                        + " has moved to\r\n<A HREF=\"" + site + theFile + "\">"
                        + site + theFile
                        + "</A>.\r\n Please update your bookmarks<P>");
                out.write("</BODY></HTML>\r\n");
                out.flush();
                logger.log(Level.INFO, "Redirected " + connection.getRemoteSocketAddress());
            } finally {
                connection.close();
            }
            return null;
        }
    }

    public static void main(String args[]) {
        int port = 8080;
        String site = "http://www.csdn.net";

        try {
            Redirector redirector = new Redirector(site, port);
            redirector.start();
        } catch (RuntimeException ex) {
            System.out.println("Usage: java Redirector http://www.newsite.com/ port");
        }
    }
}
