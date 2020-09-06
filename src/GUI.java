import Rsa.EncryptionRSA;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

public class GUI extends JFrame implements ActionListener {
    private JFrame frame;
    private JPanel pane,panel1,panel2,firstPanel;
    private JButton EncryptButton;
    private JButton DecryptButton;
    private JLabel label1;
    private JLabel label2;
    private File file=null;
    private JTextField Key;
    private JTextField Email;
    private EncryptionRSA rsa = new EncryptionRSA("");
    ;
    private String str;
    private JComponent component;

    public GUI() throws Exception {
        this.frame = new JFrame("EDF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 600));
        this.setContentPane(Menu());
        frame.add(pane);
        frame.pack();
        frame.setVisible(true);

    }

    public JPanel Menu(){
        firstPanel=new JPanel();
        this.pane = new JPanel(new BorderLayout());
        pane.setPreferredSize(new Dimension(700, 600));
        panel1=new JPanel();
        this.panel1 = new JPanel(new BorderLayout());
        panel1.setPreferredSize(new Dimension(400, 100));
        EncryptButton=new JButton("Encryption");
        EncryptButton.setPreferredSize(new Dimension(100,18));
        this.EncryptButton.addActionListener(this);
        DecryptButton=new JButton("Decryption");

        DecryptButton.setPreferredSize(new Dimension(100,18));
        this.DecryptButton.addActionListener(this);
        panel2=new JPanel(new BorderLayout());
        panel2.setPreferredSize(new Dimension(400, 100));

        firstPanel.add(EncryptButton, BorderLayout.EAST);
        firstPanel.add(DecryptButton, BorderLayout.EAST);

        pane.add(firstPanel);
        return pane;
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Encryption":
                FrameEncryption();
                break;

            case "Decryption":
                FrameDeccryption();
                break;

            case "Mail":
                if (file != null) {
                    String textKey = Key.getText();
                    if (!textKey.equals("")) {
                        String textEmail = Email.getText();
                        if (!textEmail.equals("")) {
                            try {
                                rsa.setInputEncrypt(textKey);
                                str = rsa.sign(textKey, rsa.getPrivateKey());
                                String EncryptKey = rsa.encrypt(rsa.getPublicKey());
                                Encrypt encrypt = new Encrypt();
                                File fileEncrypt = encrypt.EncryptionMethod(file, textKey);
                                SendEmailSMTP sending = new SendEmailSMTP(textEmail);
                                sending.SendMail(fileEncrypt, EncryptKey);

                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Can't create a file of decryption", "Message"
                                        , JOptionPane.ERROR_MESSAGE);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "The key must contain hex digits", "Message"
                                        , JOptionPane.ERROR_MESSAGE);
                            } catch (MessagingException ex) {
                                JOptionPane.showMessageDialog(null, "There is a connection problem to the Internet or in the email that you have entered", "Message"
                                        , JOptionPane.ERROR_MESSAGE);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "CANT ENCRYPT THIS FILE!\n TRY AGAIN", "Message"
                                        , JOptionPane.ERROR_MESSAGE);
                            }
                        }else {
                            JOptionPane.showMessageDialog(this, "Invalid input.\nPlease enter e-mail");
                            return;
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Invalid input.\nPlease enter key");
                        return;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Invalid input.\nPlease choose pdf file");
                    return;
                }
                break;

            case "CHOOSE FILE":
                file = null;
                file = FileChoose();
                break;

            case "Submit":
                if (file != null) {
                    String textKey = Key.getText();
                    if (!textKey.equals("")) {
                        String textEmail = Email.getText();
                        if (!textEmail.equals("")) {
                            try {
                                rsa.setInputEncrypt(textKey);
                                String ans = rsa.decrypt(rsa.getPrivateKey());
                                rsa.setInputEncrypt(ans);
                                if (rsa.verify(str, rsa.getPublicKey())) {
                                    Encrypt encrypt = new Encrypt();
                                    File fileDecrypt = encrypt.DecryptionMethod(file, ans);
                                    SendEmailSMTP sending = new SendEmailSMTP(textEmail);
                                    sending.SendMail(fileDecrypt, ans);
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Can't create a file of decryption", "Message"
                                        , JOptionPane.ERROR_MESSAGE);
                            } catch (MessagingException ex) {
                                JOptionPane.showMessageDialog(null, "There is a connection problem to the Internet or in the email that you have entered", "Message"
                                        , JOptionPane.ERROR_MESSAGE);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "CANT ENCRYPT THIS FILE!\n TRY AGAIN.", "Message"
                                        , JOptionPane.ERROR_MESSAGE);
                            }
                        }else {
                            JOptionPane.showMessageDialog(this, "Invalid input.\nPlease enter e-mail");
                            return;
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(this, "Invalid input.\nPlease enter key");
                        return;
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Invalid input.\nPlease choose pdf file");
                    return;
                }
                break;
        }
    }

    private void FrameEncryption() {
        JFrame frameEncrypt=new JFrame("Encryption File");
        frameEncrypt.setSize(new Dimension(600, 100));
        JPanel EncryptPanel=new JPanel();
        JPanel EncryptBack=new JPanel();
        Email=new JTextField("Enter Email");
        Key=new JTextField("Enter Key");
        Key.setPreferredSize(new Dimension(140,20));
        Email.setPreferredSize(new Dimension(140,20));
        JButton ChooseFile=new JButton("CHOOSE FILE");
        JButton SendMail=new JButton(new ShowWaitAction("Mail"));
        ChooseFile.addActionListener(this);
        SendMail.addActionListener(this);
        EncryptPanel.setPreferredSize(new Dimension(600, 100));
        EncryptBack.add(Key);
        EncryptBack.add(Email);
        EncryptBack.add(SendMail);
        EncryptBack.add(ChooseFile);
        EncryptPanel.add(EncryptBack);
        frameEncrypt.add(EncryptPanel);
        frameEncrypt.pack();
        frameEncrypt.setVisible(true);

    }

    private void FrameDeccryption() {
        JFrame frameEncrypt=new JFrame("Decryption File");
        frameEncrypt.setSize(new Dimension(600, 100));
        JPanel EncryptPanel=new JPanel();
        JPanel EncryptBack=new JPanel();
        Email=new JTextField("Enter Email");
        Key=new JTextField("Enter Key");
        Email.setPreferredSize(new Dimension(140,20));

        Key.setPreferredSize(new Dimension(140,20));
        JButton ChooseFile=new JButton("CHOOSE FILE");
        JButton SendMail=new JButton(new ShowWaitAction("Submit"));
        ChooseFile.addActionListener(this);
        SendMail.addActionListener(this);
        EncryptPanel.setPreferredSize(new Dimension(600, 100));
        EncryptBack.add(Key);
        EncryptBack.add(Email);
        EncryptBack.add(SendMail);
        EncryptBack.add(ChooseFile);
        EncryptPanel.add(EncryptBack);
        frameEncrypt.add(EncryptPanel);
        frameEncrypt.pack();
        frameEncrypt.setVisible(true);

    }

    public static String base16to64(String hex){
        return Base64.getEncoder().encodeToString(new BigInteger(hex, 16).toByteArray());
    }

    public File FileChoose(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            return selectedFile;

        }
        return null;
    }
    public static void main(String[] args) throws Exception {

        GUI d=new GUI();


    }
}
