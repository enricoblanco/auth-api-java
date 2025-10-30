package auth.auth_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Email Verification - Auth API";
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;

        String message = "Hello,\n\n"
                + "Thank you for registering with our service.\n"
                + "Please click the link below to verify your email address:\n\n"
                + verificationUrl + "\n\n"
                + "This link will expire in 24 hours.\n\n"
                + "If you didn't create an account, please ignore this email.\n\n"
                + "Best regards,\n"
                + "Auth API Team";

        sendEmail(toEmail, subject, message);
    }

    public void sendPasswordResetEmail(String toEmail, String token) {
        String subject = "Password Reset Request - Auth API";
        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;

        String message = "Hello,\n\n"
                + "We received a request to reset your password.\n"
                + "Please click the link below to reset your password:\n\n"
                + resetUrl + "\n\n"
                + "This link will expire in 1 hour.\n\n"
                + "If you didn't request a password reset, please ignore this email.\n\n"
                + "Best regards,\n"
                + "Auth API Team";

        sendEmail(toEmail, subject, message);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}