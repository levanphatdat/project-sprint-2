package com.example.be.service.vnpay.impl;

import com.example.be.DTO.payment.PaymentSendEmailDTO;
import com.example.be.service.vnpay.IVNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class VNPayServiceImpl implements IVNPayService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmail(PaymentSendEmailDTO paymentSendEmailDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message;
        try {
            message = new MimeMessageHelper(mimeMessage, true);
            message.setTo(paymentSendEmailDTO.getEmail());
            message.setSubject("Thanh toán thành công");
            message.setText( "<html>" +
                    "<body>" +
                    "<div style=\" font-size:15px;\">"+
                    "Kính gửi Quý khách hàng: " + paymentSendEmailDTO.getCustomerName() +"" + "<br>" + "<br>" +
                    "<div style =\" font-weight:bold \"> Đơn hàng: "
                    + paymentSendEmailDTO.getCode()+ " đã được thanh toán thành công"+"</div>" + "<br>" +
                    "Hy vọng quý khách hàng có trải nghiệm tốt khi sử dụng sản phẩm của DDrink, "
                    +"<br>"
                    + "<br>" +
                    "Nếu hài lòng với sản phẩm, mong quý khách tiếp tục ủng hộ chúng tôi!."
                    + "<br>"
                    + "<br>"
                    + "Trân trọng," +
                    "<div style =\"color:#12ac4c; font-size:20px ; font-weight:bold\">ChanhViet Garden</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>", true);
            javaMailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}