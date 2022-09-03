/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ceras
 */
public class PublicMessage {
    String hashtag;
    String text;
    String sender;
    String recipient;
    LocalDateTime sentDate;
    
    public PublicMessage(String h, String t, String s, String r, LocalDateTime d) {
        this.hashtag = h;
        this.text = t;
        this.sender = s;
        this.recipient = r;
        this.sentDate = d;
    }
    
    public String getText() {
        return text;
    }
    
    public LocalDateTime getDate() {
        return sentDate;
    }
    
    public String getStringDate() {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedDate = sentDate.format(formatDate);
        return formattedDate;
    }
    
    @Override
    public String toString() {
        return hashtag + ", " + sender + ", " + this.getStringDate() + ", " + text;
    }
}
