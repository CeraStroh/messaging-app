
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ceras
 */
public class FollowDialog extends javax.swing.JDialog {
    private javax.swing.DefaultListModel leaderNames;
    private javax.swing.DefaultListModel followerNames;
    static int ADD_PORT = 2001;
    private javax.swing.JLabel IDLabel;
    String serverIP;

    /**
     * Creates new form FollowDialog
     */
    public FollowDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        leaderNames = new javax.swing.DefaultListModel();
        followerNames = new javax.swing.DefaultListModel();
        jList1.setModel(followerNames);
        jList3.setModel(leaderNames);
    }

    /**
     *
     */
    public void getFollowers() {
        followerNames.removeAllElements();
        try {
            String host = serverIP;
            Socket s = new Socket(host, ADD_PORT);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            out.println("GET FOLLOWERS");
            System.out.println("Sent: GET FOLLOWERS");
            String result = in.nextLine();
            
            if (result.equals("Name?")) {
                String name = IDLabel.getText();
                System.out.println(name);
                out.println(name);
                result = in.nextLine();
                while (!result.equals("All followers done")) {
                    String follower = result;
                    String status = this.getStatus(follower);
                    followerNames.addElement(follower + status);
                    System.out.println(follower + " added");
                    result = in.nextLine();
                }
                System.out.println("All followers added");
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
    
    public void getLeaders() {
        leaderNames.removeAllElements();
        try {
            String host = serverIP;
            Socket s = new Socket(host, ADD_PORT);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             
            out.println("GET LEADERS");
            System.out.println("Sent: GET LEADERS");
            String result = in.nextLine();
            
            if (result.equals("Name?")) {
                String name = IDLabel.getText();
                System.out.println(name);
                out.println(name);
                result = in.nextLine();
                while (!result.equals("All leaders done")) {
                    String leader = result;
                    String status = this.getStatus(leader);
                    leaderNames.addElement(leader + status);
                    System.out.println(leader + " added");
                    result = in.nextLine();
                }
                System.out.println("All leaders added");
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
    
    public String getStatus(String id) {
        String status = "";
        try {
            String host = serverIP;
            Socket s = new Socket(host, ADD_PORT);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            out.println("GET STATUS");
            System.out.println("Sent: GET STATUS");
            String result = in.nextLine();
            
            if (result.equals("Name?")) {
                out.println(id);
                System.out.println("Sent: " + id);
                status = in.nextLine();
                System.out.println("Received: " + status);
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
        System.out.println("Status: " + status);
        return status;
    }
    
    public void getName(String id) {
        IDLabel = new javax.swing.JLabel();
        IDLabel.setFont(new java.awt.Font("Tahoma", 0, 19));
        IDLabel.setText(id);
        setLayout(new BorderLayout());
        IDLabel.setHorizontalAlignment(JLabel.CENTER);
        add(IDLabel, BorderLayout.NORTH);
    }
    
    public void setIP(String ip) {
        serverIP = ip;
    }
    public String getIP() {
        return serverIP;
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTextField3 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Followers");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Following");

        jScrollPane4.setPreferredSize(new java.awt.Dimension(250, 279));

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 275));

        jList1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jTextField3.setText("Search list...");

        jButton3.setText("Message");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendPrivateMessage(evt);
            }
        });

        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchFollowers(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField3))
                    .addComponent(jButton3))
                .addGap(0, 23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addGap(61, 61, 61)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(200, 250));

        jList3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jScrollPane3.setViewportView(jList3);

        jButton1.setText("Unfollow");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Unfollow(evt);
            }
        });

        jTextField2.setText("Search list...");

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchLeaders(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addGap(59, 59, 59)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addGap(12, 12, 12))
        );

        jScrollPane2.setViewportView(jPanel2);

        jTextField1.setText("Type screen name to follow...");

        jButton9.setText("Follow");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Follow(evt);
            }
        });

        jLabel4.setText("*select name & click button to unfollow");

        jButton5.setText("Reload");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reload(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(216, 216, 216)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton9)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Follow(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Follow
        // TODO add your handling code here:
        String leader = jTextField1.getText().trim();
        try {
            String host = serverIP;
            Socket s = new Socket(host, ADD_PORT);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            out.println("FOLLOW");
            System.out.println("Sent: FOLLOW");
            String result = in.nextLine();
            if (result.equals("Name?")) {
                String name = IDLabel.getText();
                out.println(name);
                System.out.println("Sent: " + name);
                result = in.nextLine();
                if (result.equals("Who?")) {
                    out.println(leader);
                    result = in.nextLine();
                    System.out.println("Sent: " + leader);
                }
                if (result.equals("Can't follow yourself")) {
                    JOptionPane.showMessageDialog(this, "You can't follow yourself");
                } else if (result.equals("LEADER ADDED")) {
                    System.out.println(result);
                    leaderNames.addElement(leader);
                    jList3.setSelectedIndex(leaderNames.size() - 1);
                    JOptionPane.showMessageDialog(this, "You are now following " + leader);
                } else if (result.equals("ALREADY FOLLOWING")) {
                    System.out.println(result);
                    JOptionPane.showMessageDialog(this, "You are already following " + leader);
                } else if (result.equals("NOT FOUND")) {
                    System.out.println(result);
                    JOptionPane.showMessageDialog(this, "Screen name not found");
                } else if (result.equals("TYPE NAME")) {
                    System.out.println(result);
                    JOptionPane.showMessageDialog(this, "Please type a screen name in order to follow");
                } else {
                    System.out.println(result);
                    JOptionPane.showMessageDialog(this, "Follow Error");
                }
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
    }//GEN-LAST:event_Follow

    private void Unfollow(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Unfollow
        // TODO add your handling code here:
        String leader = jList3.getSelectedValue();
        try {
            String host = serverIP;
            Socket s = new Socket(host, ADD_PORT);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            
            out.println("UNFOLLOW");
            System.out.println("Sent: UNFOLLOW");
            String result = in.nextLine();
            if (result.equals("Name?")) {
                String name = IDLabel.getText();
                out.println(name);
                System.out.println("Sent: " + name);
                result = in.nextLine();
                if (result.equals("Who?")) {
                    String arr[] = leader.split(" ", 2);
                    String l = arr[0];
                    out.println(l);
                    result = in.nextLine();
                    System.out.println("Sent: " + leader);
                }
                if (result.equals("LEADER REMOVED")) {
                    System.out.println(result);
                    int index = jList3.getSelectedIndex();
                    leaderNames.remove(index);
                    jTextField1.setText("");
                    JOptionPane.showMessageDialog(this, "You are no longer following " + leader);
                } else if (result.equals("NOT FOLLOWING")) {
                    System.out.println(result);
                    JOptionPane.showMessageDialog(this, "You are not following " + leader);
                } else {
                    System.out.println(result);
                    JOptionPane.showMessageDialog(this, "Unfollow Error");
                }
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
    }//GEN-LAST:event_Unfollow

    private void SearchLeaders(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchLeaders
        // TODO add your handling code here:
        String searchName = jTextField2.getText().trim();
        String status = this.getStatus(searchName);
        if (!searchName.equals("")) {
            if (leaderNames.contains(searchName)) {
                System.out.println("Contains searchName");
                int index = leaderNames.indexOf(searchName);
                jList3.setSelectedIndex(index);
            } else if (leaderNames.contains(searchName + status)) {
                System.out.println("Contains searchName + status");
                int index = leaderNames.indexOf(searchName + status);
                jList3.setSelectedIndex(index);
            } else {
                JOptionPane.showMessageDialog(this, "Leader not found");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please type a screen name in order to search leaders");
        }
    }//GEN-LAST:event_SearchLeaders

    private void SendPrivateMessage(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendPrivateMessage
        // TODO add your handling code here:
        int index = jList1.getSelectedIndex();
        String recipient = followerNames.get(index).toString();
        PrivateDialog privateMessage = new PrivateDialog(new javax.swing.JFrame(), true);
        String id = IDLabel.getText();
        privateMessage.getName(id);
        privateMessage.setIP(serverIP);
        String arr[] = recipient.split(" ", 2);
        String r = arr[0];
        privateMessage.getRecipient(r);
        privateMessage.setVisible(true);
    }//GEN-LAST:event_SendPrivateMessage

    private void SearchFollowers(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchFollowers
        // TODO add your handling code here:
        String searchName = jTextField3.getText().trim();
        String status = this.getStatus(searchName);
        if (!searchName.equals("")) {
            if (followerNames.contains(searchName)) {
                int index = followerNames.indexOf(searchName);
                jList1.setSelectedIndex(index);
            } else if (followerNames.contains(searchName + status)) {
                int index = followerNames.indexOf(searchName + status);
                jList1.setSelectedIndex(index);
            } else {
                JOptionPane.showMessageDialog(this, "Follower not found");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please type a screen name in order to search followers");
        }
    }//GEN-LAST:event_SearchFollowers

    private void Reload(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reload
        // TODO add your handling code here:
        jTextField1.setText("Type screen name to follow...");
        leaderNames.removeAllElements();
        followerNames.removeAllElements();
        this.getFollowers();
        this.getLeaders();
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
            java.util.logging.Logger.getLogger(FollowDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FollowDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FollowDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FollowDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FollowDialog dialog = new FollowDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
