import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class client2 implements Runnable, ActionListener {
    JTextField tf;
    JButton b;
    JFrame f;
    JTextArea ta;
    JLabel l;
    ServerSocket ss;
    Socket s;
    DataInputStream din;
    DataOutputStream dout;
    Thread chat;

    client2() {
        tf = new JTextField();
        b = new JButton("Send");
        f = new JFrame("Messenger");
        s = new Socket();
        ta = new JTextArea(100, 100);
        l = new JLabel("Message");
        try {
            s = new Socket("localhost", 1000);

            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

        } catch (Exception e) {
            System.out.println(e);
        }
        f.setSize(800, 1000);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(b);
        f.add(tf);
        f.add(ta);
        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();
        f.setLayout(null);
        tf.setBounds(210, 100, 400, 20);
        ta.setBounds(200, 150, 300, 300);
        b.setBounds(100, 100, 100, 20);
        b.addActionListener(this);
        f.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        String msg = tf.getText();
        ta.append("user2: " + msg + "\n");
        tf.setText("");
        try {
            dout.writeUTF(msg);
            dout.flush();// send immediatly
        } catch (Exception r) {
            System.out.println(r);
        }

    }

    public static void main(String args[]) {
        client2 c = new client2();
    }

    public void run() {
        while (true) {
            try {
                String msg = din.readUTF();
                ta.append("user1: " + msg + "\n");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
