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
public class Leader {
    String leaderID;
    
    public Leader(String name) {
        this.leaderID = name;
    }
    
    public String getLeaderID() {
        return leaderID;
    }
    
    @Override
    public String toString() {
        return leaderID;
    }
}
