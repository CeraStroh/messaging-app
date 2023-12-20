/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ceras
 */
public class MessageDriver {
    UserList users;

    /*
     * @param args the command line arguments
     */
    
    public boolean register(String name1, String name2, String email, String id, String password) {
        boolean result = users.register(name1, name2, email, id, password);
        return result == true;
    }
    
    public String logOnUser(String id, String password, String IP) {
        String logIn = users.logOnUser(id, password, IP);
        if (logIn.equals("You are now logged on")) {
            System.out.println("You are now logged on");
            return "You are now logged on";
        } else if (logIn.equals("Incorrect screen name")){
            System.out.println("Incorrect screen name");
            return "Incorrect screen name";
        } else if (logIn.equals("Incorrect password")) {
            System.out.println("Incorrect password");
            return "Incorrect password";
        } else {
            System.out.println("You are not registered yet");
            return "You are not registered yet";
        }
    }
    
    public String logOffUser(String id) {
        String logOff = users.logOffUser(id);
        if (logOff.equals("You are now logged off")) {
            return "You are now logged off";
        } else {
            return "Error";
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        UserList users = new UserList();
        String id;
        String password;
        String first = "Cera";
        String last = "Stroh";
        String mail = "demo@central.edu";
        String screen1 = "demo";
        String pass = "example";
        users.register(first, last, mail, screen1, pass);
        String screen2 = "CeraStroh";
        users.register(first, last, mail, screen2, pass);
        String screen3 = "CS";
        users.register(first, last, mail, screen3, pass);
        try {
            ServerSocket listen = new ServerSocket(2001);
            System.out.println("Listening on port: " + listen.getLocalPort());
            System.out.println("Listening on address: "  + InetAddress.getLocalHost());
            
            while (true) {
                Socket client = listen.accept();
                
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                Scanner in = new Scanner(client.getInputStream());
                
                String line = in.nextLine();
                System.out.println(line);
                
                if (line.equals("REGISTER")) {
                    out.println("First name?");
                    String name1 = in.nextLine();
                    System.out.println(name1);
                    out.println("Last name?");
                    String name2 = in.nextLine();
                    System.out.println(name2);
                    out.println("Email?");
                    String email = in.nextLine();
                    System.out.println(email);
                    out.println("Screenname?");
                    id = in.nextLine();
                    System.out.println(id);
                    out.println("Password?");
                    password = in.nextLine();
                    System.out.println(password);
                    boolean done = users.register(name1, name2, email, id, password);
                    if (done) {
                        out.println("DONE");
                        System.out.println("DONE");
                    } else {
                        out.println("THIS SCREEN NAME IS ALREADY REGISTERED");
                        System.out.println("THIS SCREEN NAME IS ALREADY REGISTERED");
                    }
                } else if (line.equals("LOGIN")) {
                    out.println("Screenname?");
                    id = in.nextLine();
                    System.out.println(id);
                    out.println("Password?");
                    password = in.nextLine();
                    System.out.println(password);
                    out.println("Address?");
                    String userIP = in.nextLine();
                    System.out.println(userIP);
                    String logon = users.logOnUser(id, password, userIP);
                    if (logon.equals("Already logged on")) {
                        System.out.println("Sent: Already logged on");
                        out.print("ALREADY LOGGED ON");
                    } else if (logon.equals("You are now logged on")) {
                        System.out.println("Sent " + id);
                        out.println(id);
                        
                    } else if (logon.equals("Incorrect screen name")) {
                        System.out.println("Sent: Incorrect screen name");
                        out.println("INCORRECT SCREEN NAME");
                    } else if (logon.equals("Incorrect password")) {
                        System.out.println("Sent: Incorrect password");
                        out.println("INCORRECT PASSWORD");
                    } else {
                        System.out.println("Sent: Not registered yet");
                        out.print("NOT REGISTERED YET");
                    }
                } else if (line.equals("YES LOGOUT")) {
                    out.println("Name?");
                    id = in.nextLine();
                    System.out.println(id);
                    String logoff = users.logOffUser(id);
                    if (logoff.equals("You are now logged off")) {
                        System.out.println("Logged off");
                        out.print("LOGGED OFF");
                    } else {
                        out.print("Error");
                        System.out.println("Error");
                    }
                } else if (line.equals("FOLLOW")) {
                    System.out.println("Received: " + line);
                    out.println("Name?");
                    id = in.nextLine();
                    out.println("Who?");
                    String leader = in.nextLine();
                    System.out.println(leader);
                    if (id.equals(leader)) {
                        out.println("Can't follow yourself");
                    } else if (!leader.equals("")) {
                        String follow = users.follow(id, leader);
                        if (follow.equals("Leader added")) {
                            System.out.println("Leader added");
                            out.println("LEADER ADDED");
                        } else if (follow.equals("Already following this user")) {
                            System.out.println("Already following");
                            out.println("ALREADY FOLLOWING");
                        } else if (follow.equals("This screen name doesn't exist")) {
                            System.out.println("Leader not found");
                            out.println("NOT FOUND");
                        } else {
                            System.out.println("Following error");
                            out.println("FOLLOW ERROR");
                        }
                    } else {
                        System.out.println("Type name");
                        out.println("TYPE NAME");
                    }
                } else if (line.equals("UNFOLLOW")) {
                    out.println("Name?");
                    id = in.nextLine();
                    System.out.println("ID: " + id);
                    out.println("Who?");
                    String leader = in.nextLine();
                    System.out.println(leader);
                    String unfollow = users.unfollow(id, leader);
                    if (unfollow.equals("Leader removed")) {
                        System.out.println("Leader removed");
                        out.println("LEADER REMOVED");
                    } else if (unfollow.equals("Not following this user")) {
                        System.out.println("Not following");
                        out.println("NOT FOLLOWING");
                    } else {
                        System.out.println("Unfollowing error");
                        out.println("UNFOLLOW ERROR");
                    }
                } else if (line.equals("GET FOLLOWERS")) {
                    out.println("Name?");
                    id = in.nextLine();
                    System.out.println(id);
                    Follower[] followers = users.getFollowers(id);//WithStatus
                    if (followers == null) {
                        System.out.println("No followers");
                        out.println("All followers done");
                    } else {
                        for (Follower follower1 : followers) {
                            String follower = follower1.toString();
                            out.println(follower);
                        }
                    }
                    System.out.println("All followers done");
                    out.println("All followers done");
                } else if (line.equals("GET LEADERS")) {
                    out.println("Name?");
                    id = in.nextLine();
                    System.out.println(id);
                    Leader[] leaders = users.getLeaders(id);//WithStatus
                    if (leaders == null) {
                        System.out.println("No leaders");
                        out.println("All leaders done");
                    } else {
                        for (Leader leader1 : leaders) {
                            String leader = leader1.toString();
                            out.println(leader);
                        }
                    }
                    System.out.println("All leaders done");
                    out.println("All leaders done");
                } else if (line.equals("GET STATUS")) {
                    out.println("Name?");
                    System.out.println("Sent: Name?");
                    String name = in.nextLine();
                    String status = users.checkUserStatusString(name);
                    out.println(status);
                    System.out.println("Sent: " + status);
                } else if (line.equals("GET UNREAD")) {
                    out.println("Name?");
                    System.out.println("Sent: Name?");
                    id = in.nextLine();
                    System.out.println(id);
                    PublicMessage[] uMessages = users.getUnreadMessages(id);
                    if (uMessages == null) {
                        System.out.println("No unread messages");
                        out.println("All unread done");
                    } else {
                        for (PublicMessage uMessage1 : uMessages) {
                            String unread = uMessage1.toString();
                            out.println(unread);
                        }
                    }
                    System.out.println("All unread done");
                    out.println("All unread done");
                } else if (line.equals("SEND MESSAGE")) {
                    out.println("Name?");
                    String sender = in.nextLine();
                    System.out.println(sender);
                    out.println("Hashtag?");
                    String hashtag = in.nextLine();
                    System.out.println(hashtag);
                    out.println("Text?");
                    String text = in.nextLine();
                    System.out.println(text);
                    out.println("Recipient?");
                    String recipient = in.nextLine();
                    System.out.println(recipient);
                    out.println("Date?");
                    String sentDate = in.nextLine();
                    System.out.println(sentDate);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
                    LocalDateTime date = LocalDateTime.parse(sentDate, formatter);
                    if (recipient.equals("All followers")) {
                        String done = users.sendMessage(hashtag, text, sender, recipient, date);
                        if (done.equals("done")) {
                            out.println("Message sent");
                            System.out.println("Sent: Message sent");
                        } else if (done.equals("No followers")) {
                            out.println("No followers");
                            System.out.println("Sent: No followers");
                        } else if (done.equals("Missing fields")) {
                            out.println("Missing fields");
                            System.out.println("Sent: Missing fields");
                        } else {
                            out.println("Sending error");
                            System.out.println("Sending error");
                        }
                    } else {
                        out.println("Not a public message");
                        System.out.println("Sent: Not a public message");
                    }
                } else if (line.equals("SEARCH MESSAGES")) {
                    out.println("Hashtag?");
                    System.out.println("Sent: Hashtag?");
                    String hashtag = in.nextLine();
                    System.out.println(hashtag);
                    PublicMessage[] results = users.searchMessages(hashtag);
                    if (results == null) {
                        System.out.println("Hashtag not found");
                        out.println("Hashtag not found");
                    } else {
                        for (PublicMessage result1 : results) {
                            String result = result1.toString();
                            out.println(result);
                        }
                    }
                    System.out.println("All results done");
                    out.println("All results done");
                } else if (line.equals("READ MESSAGE")) {
                    out.println("Name?");
                    id = in.nextLine();
                    System.out.println("ID: " + id);
                    out.println("Text?");
                    String text = in.nextLine();
                    System.out.println(text);
                    out.println("Time?");
                    String time = in.nextLine();
                    System.out.println(time);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
                    LocalDateTime formattedTime = LocalDateTime.parse(time, formatter);
                    String readMessage = users.removeUnreadMessage(id, text, formattedTime);
                    if (readMessage.equals("Unread removed")) {
                        System.out.println("Unread removed");
                        out.println("MESSAGE REMOVED");
                    } else if (readMessage.equals("Unread not found")) {
                        System.out.println("Unread not found");
                        out.println("UNREAD NOT FOUND");
                    } else {
                        System.out.println("Removing unread error");
                        out.println("REMOVING UNREAD ERROR");
                    }
                } else if (line.equals("SEND PRIVATE")) {
                    out.println("Name?");
                    id = in.nextLine();
                    System.out.println("ID: " + id);
                    out.println("Recipient?");
                    String recipient = in.nextLine();
                    System.out.println("Recipient: " + recipient);
                    String rIP = users.getAddress(id, recipient);
                    out.println(rIP);
                    System.out.println("Sent: " + rIP);
                }
                out.close();
                client.close();
            }
            
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
