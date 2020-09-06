
import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class SendEmailSMTP {

    private final String SMTP_SERVER = "smtp.gmail.com";
    private final String USERNAME = "cryptofile8@gmail.com";
    private final String PASSWORD = "cryptofile1234";

    private final String EMAIL_FROM = "cryptofile8@gmail.com";
    private String EMAIL_TO = "edendavi1997@gmail.com";
    private static final String EMAIL_TO_CC = "";

    private String EMAIL_SUBJECT = "Encryption file";
    private String EMAIL_TEXT = "Thanks for using in our program \n EDF";

    public SendEmailSMTP(String UserEmail){
        EMAIL_TO=UserEmail;
    }

public void SendMail(File file, String messageKey) throws MessagingException {
        Properties prop = System.getProperties();
        prop.put("mail.stmp.starttls.enable","true");
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.user",USERNAME);
        prop.put("mail.smtp.password",PASSWORD);
        prop.put("mail.smtp.port", "465"); // default port 25
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);


            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO, false));


            // subject
            msg.setSubject(EMAIL_SUBJECT);

            // content
            msg.setText(EMAIL_TEXT);
            MimeBodyPart messageBodyPart1 = new MimeBodyPart();

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            messageBodyPart1.setText("The key is: "+messageKey);
            DataSource source = new FileDataSource(file);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName("EncryptionFile.pdf");

            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            msg.setContent(multipart );

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();




    }
}