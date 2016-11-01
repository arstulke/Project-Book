package arstulke.projectbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

@SuppressWarnings("WeakerAccess")
public class ProxyFactory {

    private static String host = "";
    private static String port = "";
    private static String username = "";
    private static String password = "";

    private static boolean build() {
        try {
            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port);
            final String authUser = username;
            final String authPassword = password;
            Authenticator.setDefault(
                    new Authenticator() {
                        @Override
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    authUser, authPassword.toCharArray());
                        }
                    }
            );
            System.setProperty("http.proxyUser", authUser);
            System.setProperty("http.proxyPassword", authPassword);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean buildProxy(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("http.proxyHost")) {
                    host = line.split("=")[1];
                } else if (line.startsWith("http.proxyPort")) {
                    port = line.split("=")[1];
                } else if (line.startsWith("http.proxyUser")) {
                    username = line.split("=")[1];
                } else if (line.startsWith("http.proxyPassword")) {
                    password = line.split("=")[1];
                }
            }
            return build();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean buildProxy(String host, String port, String username, String passwort) {
        ProxyFactory.host = host;
        ProxyFactory.port = port;
        ProxyFactory.username = username;
        ProxyFactory.password = passwort;
        return build();
    }
}