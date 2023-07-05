package com.example.be.service.mail.impl;

import com.example.be.entity.user.User;
import com.example.be.service.mail.IEmailService;
import com.example.be.service.user.IUserService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private IUserService userService;
    @Override
    public void sendResetPasswordEmail(String email, String otp) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = null;

        try {
            message = new MimeMessageHelper(mimeMessage, true);
            message.setTo(email);
            message.setSubject("Xác Thực Mã OTP");
            message.setText( "<html>" +
                    "<body>" +
                    "<div style=\" font-size:15px;\">"+
                    "Kính gửi Quý khách hàng," + "<br>" + "<br>" +
                    "<div style =\" font-weight:bold \"> Đây là mã OTP của bạn: [" + otp +"] </div>" + "<br>" +
                    "Mã OTP này sẽ hết hạn trong vòng 1 phút kể từ khi bạn nhận được email này. " +
                    "Vui lòng không chia sẻ mã này với bất kỳ ai, " +
                    "vì nó được sử dụng để xác thực tài khoản của bạn." +
                    "<br>" +
                    "Nếu bạn không yêu cầu mã OTP, " +
                    "vui lòng bỏ qua email này hoặc liên hệ với chúng tôi để được hỗ trợ."
                    + "<br>"
                    + "<br>"
                    + "Trân trọng," +
                    "<div style =\"color:#183661; font-size:20px ; font-weight:bold\">Fashion Shop</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>", true);
            javaMailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateOtp(String otpCode, String email) {
        User user = userService.findByEmailEmployee(email);
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        int code = Integer.parseInt(otpCode);
        LocalDateTime expiryTime = user.getExpiryTime();
        if (expiryTime != null && LocalDateTime.now().isAfter(expiryTime)) {
            System.out.println("Mã OTP đã hết hạn");
            return false;
        }
        String secret = user.getOtpSecret();
        boolean isValid = gAuth.authorize(secret, code);
        if (Boolean.TRUE.equals(isValid)) {
            user.setOtpSecret(null);
            user.setExpiryTime(null);
            userService.updateOtp(user);
        }else {
            System.out.println("Mã OTP không chính xác");
        }
        return isValid;
    }

    @Override
    public String generateOtp(User user) {
        GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig
                .GoogleAuthenticatorConfigBuilder()
                .setCodeDigits(6)
                .build();
        GoogleAuthenticator gAuth = new GoogleAuthenticator(config);
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secret = key.getKey();
        user.setExpiryTime(LocalDateTime.now().plusMinutes(1));
        user.setOtpSecret(secret);
        userService.updateOtp(user);
        int code = gAuth.getTotpPassword(secret);
        return Integer.toString(code);
    }
}