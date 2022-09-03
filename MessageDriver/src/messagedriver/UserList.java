/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author ceras
 */
public class UserList {
    HashMap<String, User> allUsers;
    AllMessagesList messages;
    
    public UserList() {
        allUsers = new HashMap<>();
        messages = new AllMessagesList();
    }
    
    public String checkUserStatusString(String user) {
        boolean exists = allUsers.containsKey(user);
        boolean status = false;
        if (exists) {
            User u = allUsers.get(user);
            status = u.checkStatus();
        }
        if (status) {
            return " (online)";
        } else {
            return " (offline)";
        }
    }
    
    public boolean checkUserStatus(String user) {
        boolean exists = allUsers.containsKey(user);
        boolean status;
        if (exists) {
            User u = allUsers.get(user);
            status = u.checkStatus();
            return status;
        } else {
            System.out.println("checkUserStatus user doesn't exist");
            return false;
        }
    }
    
    public boolean register(String name1, String name2, String email, String id, String password) {
        boolean registered = allUsers.containsKey(id);
        if (registered) {
            System.out.println("Already registered");
            return false;
        } else {
            User newUser = new User(name1, name2, email, id, password);
            allUsers.put(id, newUser);
            System.out.println("You are now registered.");
            return true;
        }
    }
    
    public String logOnUser(String id, String password, String IP) {
        System.out.println("Parameter: " + id);
        System.out.println("Parameter: " + password);
        boolean registered = allUsers.containsKey(id);
        System.out.println(registered);
        if (registered) {
            User currentUser = allUsers.get(id);
            String userID = currentUser.getUserID();
            System.out.println("userID: " + userID);
            String userPassword = currentUser.getPassword();
            System.out.println("userPassword: " + userPassword);
            boolean online = currentUser.checkStatus();
            if (id.equals(userID) && online) {
                System.out.println("Online? " + online);
                return "Already logged on";
            } else {
                if (id.equals(userID) && password.equals(userPassword)) {
                    System.out.println("Online? " + currentUser.checkStatus());
                    currentUser.setStatus();
                    currentUser.setIPAddress(IP);
                    System.out.println("You are now logged on");
                    System.out.println("Online? " + currentUser.checkStatus());
                    this.prepareLogInNotification(id);
                    return "You are now logged on";
                } else if (!id.equals(userID) && password.equals(userPassword)) {
                    System.out.println("Incorrect screen name");
                    return "Incorrect screen name";
                } else {
                    System.out.println("Incorrect password");
                    return "Incorrect password";
                }
            }
        } else {
            System.out.println("Not registered yet");
            return "You are not registered yet";
        }
    }
    
    public void prepareLogInNotification(String user) {
        boolean registered = allUsers.containsKey(user);
        if (registered) {
            User u = allUsers.get(user);
            boolean online = u.checkStatus();
            if (online) {
                Follower[] followers = this.getFollowers(user);
                if (null != followers) {
                    for (Follower f : followers) {
                        String followerID = f.getFollowerID();
                        boolean exists = allUsers.containsKey(followerID);
                        if (exists) {
                            String rAddress = this.getAddress(user, followerID);
                            if (!rAddress.equals("Recipient offline") && !rAddress.equals("Recipient not found") && !rAddress.equals("IP retrieval error")) {
                                this.sendLogInNotification(user, followerID, rAddress);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void sendLogInNotification(String leader, String follower, String rAddress) {
        try {
            String host = rAddress;
            Socket s = new Socket(host, 2002);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            out.println("LEADER IN");
            System.out.println("Sent: LEADER IN");
            String result = in.nextLine();
            if (result.equals("Leader?")) {
                out.println(leader);
                System.out.println("Sent: " + leader);
            } else {
                System.out.println("Error in protocol");
            }
            in.close();
            s.close();
        }
        catch (UnknownHostException e) {
            System.err.println("Add Protocol: no such host");
        }
        catch (IOException e) {
            System.err.println("IOEXCEPTION");
            System.err.println(e.getMessage());
        }
    }
    
    public String logOffUser(String id) {
        System.out.println(id);
        boolean registered = allUsers.containsKey(id);
        if (registered) {
            User leavingUser = allUsers.get(id);
            boolean online = leavingUser.checkStatus();
            if (online) {
                String userID = leavingUser.getUserID();
                System.out.println(userID);
                if (userID.equals(id)) {
                    leavingUser.setStatus();
                    System.out.println("Online? " + online);
                    this.sendLogOffMessage(userID);
                    return "You are now logged off";
                }
                else {
                    return "Incorrect screen name";
                }
            } else {
                return "Offline";
            }
        } else {
            return "Error";
        }
    }
    
    public void sendLogOffMessage(String user) {
        boolean registered = allUsers.containsKey(user);
        if (registered) {
            User leavingUser = allUsers.get(user);
            String ip = leavingUser.getIPAddress();
            System.out.println(ip);
            try {
                String host = ip;
                Socket s = new Socket(host, 2002);
                Scanner in = new Scanner(s.getInputStream());
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                out.println("LOG OFF");
                System.out.println("Sent: LOG OFF");
                String result = in.nextLine();
                if (result.equals("Confirm?")) {
                    out.println("yes");
                    System.out.println("Sent: yes");
                } else {
                    System.out.println("Error in protocol");
                }
                in.close();
                s.close();
            }
            catch (UnknownHostException e) {
                System.err.println("Add Protocol: no such host");
            }
            catch (IOException e) {
                System.err.println("IOEXCEPTION");
                System.err.println(e.getMessage());
            }
        }
    }
    
    public String follow(String userID, String leaderID) {
        boolean registered = allUsers.containsKey(userID);
        if (registered) {
            User currentUser = allUsers.get(userID);
            boolean online = currentUser.checkStatus();
            if (online) {
                boolean exists = allUsers.containsKey(leaderID);
                if (exists) {
                    boolean following = currentUser.isFollowing(leaderID);
                    System.out.println("UserList following: " + following);
                    if (!following) {
                        String complete = currentUser.follow(leaderID);
                        User leader = allUsers.get(leaderID);
                        String done = leader.addFollower(userID);
                        if (complete.equals("Leader added") && done.equals("Follower added")) {
                            System.out.println(userID + " is now a follower of " + leaderID);
                            return "Leader added";
                        } else {
                            return "Following error";
                        }
                    } else {
                        return "Already following this user";
                    }
                } else {
                    return "This screen name doesn't exist";
                }
            } else {
                return "User not online";
            }
        } else {
            return "Error";
        }
    }
    
    public String unfollow(String userID, String leaderID) {
        boolean registered = allUsers.containsKey(userID);
        if (registered) {
            User currentUser = allUsers.get(userID);
            boolean online = currentUser.checkStatus();
            if (online) {
                boolean exists = allUsers.containsKey(leaderID);
                if (exists) {
                    boolean following = currentUser.isFollowing(leaderID);
                    System.out.println("UserList following: " + following);
                    if (following) {
                        System.out.println("following");
                        String complete = currentUser.unfollow(leaderID);
                        User leader = allUsers.get(leaderID);
                        String done = leader.removeFollower(userID);
                        if (complete.equals("Leader removed") && done.equals("Follower removed")) {
                            return "Leader removed";
                        } else {
                            return "Unfollowing error";
                        }
                    } else {
                        System.out.println("not following in userList");
                        return "Not following this user";
                    }
                } else {
                    return "This screen name doesn't exist";
                }
            } else {
                return "User not online";
            }
        } else {
            return "Error";
        }
    }
    
    public Follower[] getFollowers(String userID) {
        boolean exists = allUsers.containsKey(userID);
        if (exists) {
            User currentUser = allUsers.get(userID);
            Follower[] followers = currentUser.getFollowersAsArray();
            return followers;
        } else {
            return null;
        }
    }
    
    public Leader[] getLeaders(String userID) {
        boolean exists = allUsers.containsKey(userID);
        if (exists) {
            User currentUser = allUsers.get(userID);
            Leader[] leaders = currentUser.getLeadersAsArray();
            return leaders;
        } else {
            return null;
        }
    }
    
    public String sendMessage(String hashtag, String text, String sender, String recipient, LocalDateTime sentDate) {
        if (hashtag.equals("") || text.equals("") || recipient.equals("")) {
            return "Missing fields";
        }
        String done = "";
        String complete = "";
        boolean registered = allUsers.containsKey(sender);
        if (registered) {
            User u = allUsers.get(sender);
            boolean online = u.checkStatus();
            if (online) {
                Follower[] followers = this.getFollowers(sender);
                if (null == followers) {
                    return "No followers";
                } else {
                    for (Follower f : followers) {
                        String followerID = f.getFollowerID();
                        boolean exists = allUsers.containsKey(followerID);
                        recipient = followerID;
                        System.out.println("Recipient: " + recipient);
                        if (exists) {
                            User follower = allUsers.get(followerID);
                            done = follower.addUnreadMessage(hashtag, text, sender, recipient, sentDate);
                        }
                    }
                    complete = messages.addPublicMessage(hashtag, text, sender, recipient, sentDate);
                }
            }
        }
        if (done.equals("Unread added") && complete.equals("Public added")) {
            this.prepareNewMessageNotification(sender);
            return "done";
        } else {
            return "error";
        }
    }
    
    public void prepareNewMessageNotification(String user) {
        boolean registered = allUsers.containsKey(user);
        if (registered) {
            User u = allUsers.get(user);
            boolean online = u.checkStatus();
            if (online) {
                Follower[] followers = this.getFollowers(user);
                if (null != followers) {
                    for (Follower f : followers) {
                        String followerID = f.getFollowerID();
                        boolean exists = allUsers.containsKey(followerID);
                        if (exists) {
                            String rAddress = this.getAddress(user, followerID);
                            if (!rAddress.equals("Recipient offline") && !rAddress.equals("Recipient not found") && !rAddress.equals("IP retrieval error")) {
                                this.sendNewMessageNotification(user, followerID, rAddress);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void sendNewMessageNotification(String leader, String follower, String rAddress) {
        try {
            String host = rAddress;
            Socket s = new Socket(host, 2002);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            out.println("NEW MESSAGE");
            System.out.println("Sent: NEW MESSAGE");
            String result = in.nextLine();
            if (result.equals("Leader?")) {
                out.println(leader);
                System.out.println("Sent: " + leader);
            } else {
                System.out.println("Error in protocol");
            }
            in.close();
            s.close();
        }
        catch (UnknownHostException e) {
            System.err.println("Add Protocol: no such host");
        }
        catch (IOException e) {
            System.err.println("IOEXCEPTION");
            System.err.println(e.getMessage());
        }
    }
    
    public PublicMessage[] getUnreadMessages(String userID) {
        boolean exists = allUsers.containsKey(userID);
        if (exists) {
            User currentUser = allUsers.get(userID);
            PublicMessage[] unreadMessages = currentUser.getUnreadMessagesAsArray();
            return unreadMessages;
        } else {
            return null;
        }
    }
    
    public String removeUnreadMessage(String recipient, String text, LocalDateTime time) {
        boolean registered = allUsers.containsKey(recipient);
        if (registered) {
            User currentUser = allUsers.get(recipient);
            String done = currentUser.removeUnreadMessage(text, time);
            if (done.equals("done")) {
                return "Unread removed";
            } else if (done.equals("not found")) {
                return "Unread not found";
            } else {
                return "Error";
            }
        } else {
            return "Removing unread error";
        }
    }
    
    public PublicMessage[] searchMessages(String hashtag) {
        PublicMessage[] results = messages.searchMessages(hashtag);
        return results;
    }
    
    public String getAddress(String sender, String recipient) {
        boolean registered = allUsers.containsKey(sender);
        if (registered) {
            User u = allUsers.get(sender);
            boolean online = u.checkStatus();
            if (online) {
                boolean rRegistered = allUsers.containsKey(recipient);
                if (rRegistered) {
                    User r = allUsers.get(recipient);
                    boolean rOnline = r.checkStatus();
                    if (rOnline) {
                        String ip = r.getIPAddress();
                        return ip;
                    } else {
                        return "Recipient offline";
                    }
                } else {
                    return "Recipient not found";
                }
            }
        }
        return "IP retrieval error";
    }
}
