
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceras
 */
public class NotificationRunnable implements Runnable {
    HomeWindow home;
    boolean keepGoing;
    
    public NotificationRunnable(HomeWindow home) {
        this.home = home;
        keepGoing = true;
    }
    
    public void run() {
        try {
            System.out.println("About to listen");
            ServerSocket listen = new ServerSocket(2002);
            System.out.println("Thread listening on port: " + listen.getLocalPort());
            System.out.println("Thread listening on address: "  + InetAddress.getLocalHost());
            String address = InetAddress.getLocalHost().toString();
            String arr[] = address.split("/", 2);
            String ip = home.getIP();
            
            while (keepGoing) {
                Socket threadClient = listen.accept();
                
                PrintWriter out = new PrintWriter(threadClient.getOutputStream(), true);
                Scanner in = new Scanner(threadClient.getInputStream());
                
                String line = in.nextLine();
                System.out.println(line);
                if (line.equals("SEND PRIVATE")) {
                    out.println("Name?");
                    String sender = in.nextLine();
                    out.println("Text?");
                    String text = in.nextLine();
                    out.println("Date?");
                    String date = in.nextLine();
                    String privateMessage = "Private: " + sender + ", " + date + ", " + text;
                    if (home.isVisible()) {
                        home.addUnreadMessage(privateMessage);
                    }
                    System.out.println(privateMessage);
                    out.println("Private sent");
                } else if (line.equals("LEADER IN")) {
                    out.println("Leader?");
                    String leader = in.nextLine();
                    String notification = leader + " is now online.";
                    if (home.isVisible()) {
                        home.showNotification(notification);
                    }
                } else if (line.equals("NEW MESSAGE")) {
                    out.println("Leader?");
                    String leader = in.nextLine();
                    String notification = leader + " has posted a new message.";
                    if (home.isVisible()) {
                        home.showNotification(notification);
                    }
                } else if (line.equals("LOG OFF")) {
                    System.out.println(line);
                    out.println("Confirm?");
                    System.out.println("Sent: Confirm?");
                    String confirm = in.nextLine();
                    if (confirm.equals("yes")) {
                        keepGoing = false;
                        System.out.println("keepGoing = false");
                    } else {
                        System.out.println("Log out not confirmed");
                    }
                }
                out.close();
                threadClient.close();
            }
            listen.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
