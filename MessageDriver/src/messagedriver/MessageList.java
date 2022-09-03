/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author ceras
 */
public class MessageList {
    ArrayList<PublicMessage> uMessages;
    PublicMessage[] uMessagesArray;
    
    public MessageList() {
        uMessages = new ArrayList<>();
        uMessagesArray = new PublicMessage[uMessages.size()];
    }
    
    public String addUnreadMessage(PublicMessage m) {
        uMessages.add(m);
        uMessagesArray = this.getUnreadMessagesAsArray();
        return "done";
    }
    
    public String removeUnreadMessage(String text, LocalDateTime time) {
        System.out.println("in messageList removeUnreadMessage");
        String thisText;
        LocalDateTime thisTime;
        int index = 0;
        for (PublicMessage p : uMessages) {
            thisText = p.getText();
            thisTime = p.getDate();
            if (thisText.equals(text) && thisTime.isEqual(time)) {
                index = uMessages.indexOf(p);
            }
        }
        uMessages.remove(index);
        return "done";
    }
    
    public PublicMessage[] getUnreadMessagesAsArray() {
        uMessagesArray = new PublicMessage[uMessages.size()];
        if (!uMessages.isEmpty()) {
            for (int i=0; i<uMessages.size(); i++) {
                PublicMessage p = uMessages.get(i);
                    uMessagesArray[i] = p;
            }
            return uMessagesArray;
        } else {
            return null;
        }
    }
}
