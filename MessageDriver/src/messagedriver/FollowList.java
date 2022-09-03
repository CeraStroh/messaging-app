/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

import java.util.ArrayList;

/**
 *
 * @author ceras
 */
public class FollowList {
    ArrayList<Follower> followers;
    ArrayList<Leader> leaders;
    Leader[] leaderArray;
    Follower[] followerArray;
    
    public FollowList() {
        followers = new ArrayList<>();
        leaders = new ArrayList<>();
        followerArray = new Follower[followers.size()];
        leaderArray = new Leader[leaders.size()];
    }
    
    public boolean isFollowing(String leaderID) {
        for (Leader l : leaders) {
            String lID = l.getLeaderID();
            if (lID.equals(leaderID)) {
                System.out.println(lID + " = " + leaderID);
                return true;
            }
        }
        return false;
    }
    
    public String follow(Leader leader) {
        leaders.add(leader);
        return "done";
    }
    
    public String addFollower(Follower follower) {
        followers.add(follower);
        return "done";
    }
    
    public String unfollow(String leaderID) {
        String thisID;
        int index = 0;
        for (Leader l : leaders) {
            thisID = l.getLeaderID();
            if (thisID.equals(leaderID)) {
                index = leaders.indexOf(l);
            }
        }
        leaders.remove(index);
        System.out.println("unfollowed");
        leaderArray = this.getLeadersAsArray();
        return "done";
    }
    
    public String removeFollower(String followerID) {
        String thisID;
        int index = 0;
        for (Follower f : followers) {
            thisID = f.getFollowerID();
            if (thisID.equals(followerID)) {
                index = followers.indexOf(f);
            }
        }
        followers.remove(index);
        followerArray = this.getFollowersAsArray();
        return "done";
    }
    
    public Follower[] getFollowersAsArray() {
        followerArray = new Follower[followers.size()];
        if (!followers.isEmpty()) {
            for (int i=0; i<followers.size(); i++) {
                Follower f = followers.get(i);
                followerArray[i] = f;
            }
            return followerArray;
        } else {
            return null;
        }
    }
    
    public Leader[] getLeadersAsArray() {
        leaderArray = new Leader[leaders.size()];
        if (!leaders.isEmpty()) {
            for (int i=0; i<leaders.size(); i++) {
                Leader f = leaders.get(i);
                leaderArray[i] = f;
            }
            return leaderArray;
        } else {
            return null;
        }
    }
}
