package chuirer.utilitarios;

import com.myapp.struts.Propiedades;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author fferegrino
 */
public class Correo {

    private Properties propiedades;
    private String servidorCorreo;
    private String correoOrigen;
    private String passOrigen;
    private boolean auth;
    private int puerto;

    public Correo() {
        propiedades = new Properties();
        Properties p = new Propiedades().getProperties();
        servidorCorreo = p.getProperty("mail.host");
        correoOrigen = p.getProperty("mail.sender");
        passOrigen = p.getProperty("mail.pass");
        auth = Boolean.parseBoolean(p.getProperty("mail.auth"));
        puerto = Integer.parseInt(p.getProperty("mail.port"));

        propiedades.put("mail.smtp.host", servidorCorreo);
        propiedades.put("mail.smtp.starttls.enable", String.valueOf(auth));
        propiedades.put("mail.smtp.port", String.valueOf(puerto));
        propiedades.put("mail.smtp.user", correoOrigen);
    }

    public boolean enviar(String destinatario, String asunto, String mensaje) {
        String[] destinatarios = {destinatario};
        return enviar(destinatarios, asunto, mensaje);
    }

    public boolean enviar(String[] destinatarios, String asunto, String mensaje) {
        try {
            Session mailSession = Session.getDefaultInstance(propiedades);
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(correoOrigen));
            message.setSubject(asunto);
            message.setSentDate(new Date());
            message.setText(mensaje);
            if (destinatarios.length == 0) {
                return false;
            }
            for (String destinatario : destinatarios) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            }
            Transport t = mailSession.getTransport("smtp");
            t.connect(correoOrigen, passOrigen);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
        } catch (AddressException ex) {
            return false;
        } catch (MessagingException ex) {
            return false;
        }
    }
}
