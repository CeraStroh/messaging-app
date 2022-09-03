

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceras
 */
public class HomeWindow extends javax.swing.JFrame {
    private javax.swing.DefaultListModel unreadMessages;
    static int ADD_PORT = 2001;
    private javax.swing.JLabel IDLabel;
    ArrayList<String> sMessages;
    MessageDialog messageDiag;
    FollowDialog followDiag;
    SearchDialog searchDiag;
    SentDialog sentDiag;
    Runnable r;
    Thread listeningThread;
    String serverIP;
    
    /**
     * Creates new form HomeWindow
     */
    public HomeWindow() {
        initComponents();
        var listener = new CloseHomeWindow();
        this.addWindowListener(listener);
        sMessages = new ArrayList<>();
        unreadMessages = new javax.swing.DefaultListModel();
        jList1.setModel(unreadMessages);
        messageDiag = new MessageDialog(new javax.swing.JFrame(), false);
        followDiag = new FollowDialog(new javax.swing.JFrame(), false);
        searchDiag = new SearchDialog(new javax.swing.JFrame(), false);
        sentDiag = new SentDialog(new javax.swing.JFrame(), false);
        r = new NotificationRunnable(HomeWindow.this);
        listeningThread = new Thread(r);
    }
    
    
    public void makeIDLabel(String id) {
        IDLabel = new javax.swing.JLabel();
        IDLabel.setFont(new java.awt.Font("Tahoma", 0, 19));
        IDLabel.setText(id);
        setLayout(new BorderLayout());
        IDLabel.setHorizontalAlignment(JLabel.CENTER);
        add(IDLabel, BorderLayout.NORTH);
        listeningThread.start();
        System.out.println("Starting thread");
    }
    
    public void setIP(String ip) {
        serverIP = ip;
    }
    
    public String getIP() {
        return serverIP;
    }
    
    public String formatDate(LocalDateTime d) {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String formattedDate = d.format(formatDate);
        return formattedDate;
    }
    
    private void logOff() {
        LogOutDialog logOut = new LogOutDialog(this, true);
        String name = IDLabel.getText();
        logOut.getName(name);
        logOut.setIP(serverIP);
        logOut.getWindows(this, messageDiag, followDiag, searchDiag, sentDiag);
        logOut.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                logOut.dispose();
            }
        });
        logOut.setVisible(true);
    }
    
    public void addUnreadMessage(String message) {
        unreadMessages.addElement(message);
    }
    
    public void getUnread() {
        unreadMessages.removeAllElements();
        try {
            String host = serverIP;
            Socket s = new Socket(host, ADD_PORT);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            out.println("GET UNREAD");
            System.out.println("Sent: GET UNREAD");
            String result = in.nextLine();
            
            if (result.equals("Name?")) {
                String name = IDLabel.getText();
                System.out.println(name);
                out.println(name);
                result = in.nextLine();
                while (!result.equals("All unread done")) {
                    String uMessage = result;
                    unreadMessages.addElement(uMessage);
                    System.out.println(uMessage + " added");
                    result = in.nextLine();
                }
                System.out.println("All unread added");
            } else {
                System.out.println("Error in protocol");
            }
            in.close();
            System.out.println("Closing in getUnread");
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
    
    public void showNotification(String notification) {
        if (HomeWindow.this.isVisible()) {
            HomeWindow.this.setVisible(true);
            JOptionPane.showMessageDialog(this, notification);
            this.getUnread();
        }
        JDialog[] dialogs = {messageDiag, sentDiag, searchDiag, followDiag};
        for (JDialog dialog : dialogs) {
            if (dialog.isVisible()) {
                dialog.setVisible(true);
                JOptionPane.showMessageDialog(dialog, notification);
            }
        }
        if (followDiag.isVisible()) {
            followDiag.getFollowers();
            followDiag.getLeaders();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        jLabel1.setText("Welcome,");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Unread Messages");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "UnreadMessage 1", "UnreadMessage 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ViewUnreadMessage(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jButton2.setText("Send Public Message");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendPublicMessage(evt);
            }
        });

        jButton4.setText("Follow");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FollowHandler(evt);
            }
        });

        jButton5.setText("Log out");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOffHandler(evt);
            }
        });

        jButton3.setText("Search Messages");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchMessages(evt);
            }
        });

        jButton1.setText("View Sent Messages");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewSent(evt);
            }
        });

        jButton6.setText("Reload");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reload(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addGap(28, 28, 28)
                        .addComponent(jButton2)
                        .addGap(12, 12, 12)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton4)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private class CloseHomeWindow extends WindowAdapter {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            HomeWindow.this.logOff();
        }
    }
    
    private void LogOffHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOffHandler
        // TODO add your handling code here:
        this.logOff();
    }//GEN-LAST:event_LogOffHandler

    private void FollowHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FollowHandler
        // TODO add your handling code here:
        if (followDiag.isVisible()) {
            followDiag.setVisible(true);
        } else {
            followDiag = new FollowDialog(new javax.swing.JFrame(), false);
            String name = IDLabel.getText();
            followDiag.getName(name);
            followDiag.setIP(serverIP);
            followDiag.getFollowers();
            followDiag.getLeaders();
            followDiag.setVisible(true);
        }
    }//GEN-LAST:event_FollowHandler

    private void SendPublicMessage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendPublicMessage
        // TODO add your handling code here:
        if (messageDiag.isVisible()) {
            messageDiag.setVisible(true);
        } else {
            messageDiag = new MessageDialog(new javax.swing.JFrame(), false);
            String name = IDLabel.getText();
            messageDiag.getName(name);
            messageDiag.setIP(serverIP);
            messageDiag.setVisible(true);
        }
    }//GEN-LAST:event_SendPublicMessage

    private void ViewUnreadMessage(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ViewUnreadMessage
        // TODO add your handling code here:
        if (evt.getClickCount() >= 2) {
            String message = jList1.getSelectedValue();
            JOptionPane.showMessageDialog(this, message);
            int index = jList1.getSelectedIndex();
            unreadMessages.remove(index);
            if (!message.startsWith("Private")) {
                try {
                    String host = serverIP;
                    Socket s = new Socket(host, ADD_PORT);
                    Scanner in = new Scanner(s.getInputStream());
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                    out.println("READ MESSAGE");
                    System.out.println("Sent: READ MESSAGE");
                    String result = in.nextLine();
                    if (result.equals("Name?")) {
                        String name = IDLabel.getText();
                        out.println(name);
                        System.out.println("Sent: " + name);
                        result = in.nextLine();
                        String arr[] = message.split(", ", 4);
                        String text = arr[3];
                        String time = arr[2];
                        if (result.equals("Text?")) {
                            System.out.println(result);
                            out.println(text);
                            System.out.println("Sent: " + text);
                            result = in.nextLine();
                        }
                        if (result.equals("Time?")) {
                            System.out.println(result);
                            out.println(time);
                            System.out.println("Sent: " + time);
                            result = in.nextLine();
                        }
                        if (result.equals("MESSAGE REMOVED")) {
                            System.out.println(result);
                            //JOptionPane.showMessageDialog(this, "Message removed on server");
                        } else if (result.equals("UNREAD NOT FOUND")) {
                            System.out.println(result);
                            JOptionPane.showMessageDialog(this, "Unread message not found");
                        } else {
                            System.out.println(result);
                            JOptionPane.showMessageDialog(this, "Removing unread message error");
                        }
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
    }//GEN-LAST:event_ViewUnreadMessage

    private void SearchMessages(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchMessages
        // TODO add your handling code here:
        if (searchDiag.isVisible()) {
            searchDiag.setVisible(true);
        } else {
            searchDiag = new SearchDialog(new javax.swing.JFrame(), false);
            searchDiag.setIP(serverIP);
            searchDiag.setVisible(true);
        }
    }//GEN-LAST:event_SearchMessages

    private void ViewSent(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewSent
        // TODO add your handling code here:
        if (sentDiag.isVisible()) {
            sentDiag.setVisible(true);
        } else {
            sentDiag = new SentDialog(new javax.swing.JFrame(), false);
            sMessages = messageDiag.giveSent();
            sentDiag.getSent(sMessages);
            sentDiag.setVisible(true);
        }
    }//GEN-LAST:event_ViewSent

    private void Reload(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reload
        // TODO add your handling code here:
        this.getUnread();
    }//GEN-LAST:event_Reload

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
