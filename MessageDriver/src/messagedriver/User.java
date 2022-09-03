/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

import java.time.LocalDateTime;

/**
 *
 * @author ceras
 */
public class User {
    String userID;
    String password;
    boolean onlineStatus;
    String firstName;
    String lastName;
    String email;
    FollowList myFollowers;
    FollowList myLeaders;
    MessageList unreadMessages;
    String ipAddress;
    
    public User(String name1, String name2, String email, String id, String password) {
        this.firstName = name1;
        this.lastName = name2;
        this.email = email;
        this.userID = id;
        this.password = password;
        myFollowers = new FollowList();
        myLeaders = new FollowList();
        unreadMessages = new MessageList();
    }
    
    public String getUserID() {
        return userID;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setStatus() {
        if (onlineStatus == false) {
            onlineStatus = true;
        } else {
            onlineStatus = false;
        }
    }
    
    public boolean checkStatus() {
        return onlineStatus;
    }
    
    public void setIPAddress(String address) {
        String arr[] = address.split("/", 2);
        ipAddress = arr[1];
    }
    
    public String getIPAddress() {
        return ipAddress;
    }
    
    public boolean isFollowing(String leaderID) {
        System.out.println("in User isFollowing()");
        boolean following = myLeaders.isFollowing(leaderID);
        System.out.println("User isFollowing: " + following);
        return following;
    }
    
    public String follow(String leaderID) {
        Leader l = new Leader(leaderID);
        String done = myLeaders.follow(l);
        if (done.equals("done")) {
            return "Leader added";
        }
        return "error";
    }
    
    public String addFollower(String followerID) {
        Follower f = new Follower(followerID);
        String done = myFollowers.addFollower(f);
        if (done.equals("done")) {
            return "Follower added";
        } else {
            return "error";
        }
    }
    
    public String unfollow(String leaderID) {
        String done = myLeaders.unfollow(leaderID);
        if (done.equals("done")) {
            System.out.println("Leader removed");
            return "Leader removed";
        }
        return "error";
    }
    
    public String removeFollower(String followerID) {
        String done = myFollowers.removeFollower(followerID);
        if (done.equals("done")) {
            System.out.println("Follower removed");
            return "Follower removed";
        } else {
            return "error";
        }
    }
    
    public Follower[] getFollowersAsArray() {
        return myFollowers.getFollowersAsArray();
    }
    
    public Leader[] getLeadersAsArray() {
        return myLeaders.getLeadersAsArray();
    }
    
    public String addUnreadMessage(String hashtag, String text, String sender, String recipient, LocalDateTime sentDate) {
        PublicMessage message = new PublicMessage(hashtag, text, sender, recipient, sentDate);
        String done = unreadMessages.addUnreadMessage(message);
        if (done.equals("done")) {
            return "Unread added";
        } else {
            return "error";
        }
    }
    
    public PublicMessage[] getUnreadMessagesAsArray() {
        return unreadMessages.getUnreadMessagesAsArray();
    }
    
    public String removeUnreadMessage(String text, LocalDateTime time) {
        String done = unreadMessages.removeUnreadMessage(text, time);
        if (done.equals("done")) {
            System.out.println("Unread removed");
            return "done";
        } else if (done.equals("not found")) {
            System.out.println("not found");
            return "not found";
        } else {
            return "error";
        }
    }
}
