/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagedriver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ceras
 */
public class AllMessagesList {
    HashMap<String, ArrayList<PublicMessage>> allPublicMessages;
    ArrayList<PublicMessage> results;
    PublicMessage[] resultArray;
    
    public AllMessagesList() {
        allPublicMessages = new HashMap<>();
        results = new ArrayList<>();
        resultArray = new PublicMessage[results.size()];
    }
    
    public String addPublicMessage(String hashtag, String text, String sender, String recipient, LocalDateTime sentDate) {
        PublicMessage newMessage = new PublicMessage(hashtag, text, sender, recipient, sentDate);
        boolean included = allPublicMessages.containsKey(hashtag);
        if (included) {
            allPublicMessages.get(hashtag).add(newMessage);
            System.out.println("In AllMessagesList addPublicMessage included");
        } else {
            allPublicMessages.put(hashtag, new ArrayList<>());
            allPublicMessages.get(hashtag).add(newMessage);
            System.out.println("In AllMessagesList addPublicMessage not included");
        }
        return "Public added";
    }
    
    public PublicMessage[] searchMessages(String hashtag) {
        results = new ArrayList<>();
        boolean exists = allPublicMessages.containsKey(hashtag);
        if (exists) {
            ArrayList<PublicMessage> messages = allPublicMessages.get(hashtag);
            for (PublicMessage message : messages) {
                results.add(message);
            }
            resultArray = this.getResultsAsArray();
            return resultArray;
        } else {
            return null;
        }
    }
    
    public PublicMessage[] getResultsAsArray() {
        resultArray = new PublicMessage[results.size()];
        if (!results.isEmpty()) {
            for (int i=0; i<results.size(); i++) {
                PublicMessage p = results.get(i);
                resultArray[i] = p;
            }
            return resultArray;
        } else {
            return null;
        }
    }
}
