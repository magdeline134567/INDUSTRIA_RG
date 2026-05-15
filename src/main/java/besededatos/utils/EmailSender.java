package besededatos.utils;

import besededatos.config.Conexion;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class EmailSender {

    private static String getParam(String clave) {
        String sql = "SELECT valor FROM Configuracion WHERE clave = ?";
        try (Connection conn = Conexion.getInstance().establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clave);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("valor");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener parámetro " + clave + ": " + e.getMessage());
        }
        return null;
    }

    public static boolean enviarCorreo(String destinatario, String asunto, String contenidoHTML) {
        final String host = getParam("SMTP_HOST");
        final String puerto = getParam("SMTP_PORT");
        final String usuario = getParam("SMTP_USER");
        final String password = getParam("SMTP_PASS");
        final String auth = getParam("SMTP_AUTH"); // "true"
        final String tls = getParam("SMTP_TLS");   // "true"

        if (host == null || usuario == null || password == null) {
            System.err.println("❌ Configuración SMTP incompleta en la tabla Configuracion.");
            return false;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", auth != null ? auth : "true");
        props.put("mail.smtp.starttls.enable", tls != null ? tls : "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", puerto != null ? puerto : "587");
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, password);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(usuario));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setContent(contenidoHTML, "text/html; charset=utf-8");

            Transport.send(mensaje);
            System.out.println("✅ Correo enviado exitosamente a: " + destinatario);
            return true;

        } catch (MessagingException e) {
            System.err.println("❌ Error al enviar correo: " + e.getMessage());
            return false;
        }
    }

    public static void enviarCorreoBienvenida(String destinatario, String nombreUsuario, String passwordTemporal) {
        String asunto = "¡Bienvenido al sistema de INDUSTRIA_RG!";
        String contenidoHTML = "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px;'>"
                + "<h1 style='color: #0a3d62; text-align: center;'>INDUSTRIA R.G.</h1>"
                + "<h2>Hola " + nombreUsuario + ",</h2>"
                + "<p>Tu cuenta ha sido creada exitosamente en nuestro sistema de gestión integral.</p>"
                + "<div style='background-color: #f4f4f4; padding: 15px; border-radius: 5px; text-align: center;'>"
                + "<p style='margin-bottom: 5px;'><b>Tu contraseña temporal es:</b></p>"
                + "<h2 style='color: #d35400; margin: 0;'>" + passwordTemporal + "</h2>"
                + "</div>"
                + "<p>Por favor, ingresa al sistema y cambia tu contraseña lo antes posible por motivos de seguridad.</p>"
                + "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>"
                + "<p style='font-size: 12px; color: #777; text-align: center;'>Este es un correo automático, por favor no respondas a este mensaje.</p>"
                + "</div>";

        enviarCorreo(destinatario, asunto, contenidoHTML);
    }

    public static boolean enviarCorreoPrueba(String destinatario) {
        String asunto = "Prueba de Configuración SMTP - INDUSTRIA_RG";
        String contenidoHTML = "<h1>Prueba Exitosa</h1>"
                + "<p>Si estás leyendo esto, la configuración de correo en <b>INDUSTRIA_RG</b> funciona correctamente.</p>"
                + "<p>Fecha y hora: " + new java.util.Date().toString() + "</p>";
        return enviarCorreo(destinatario, asunto, contenidoHTML);
    }
}

