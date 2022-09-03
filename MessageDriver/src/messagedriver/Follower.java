/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

/**
 *
 * @author ceras
 */
public class Follower {
    String followerID;
    
    public Follower(String name) {
        this.followerID = name;
    }
    
    public String getFollowerID() {
        return followerID;
    }
    
    @Override
    public String toString() {
        return followerID;
    }
}
