package spring.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;



@Service
@PropertySource("classpath:application-local.yaml")
@RequiredArgsConstructor
public class JavaMailService {

    private final JavaMailSender emailSender;

    private final String ePw = createKey();

    @Value("${spring.mail.username}")
    private String id;


    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); //보내는 대상
        message.setSubject("회원가입 이메일 인증"); //제목

        String msgg = "";
        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(id,"Admin")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random randomValue = new Random();

        for (int i = 0; i < 8; i++) {
            int index = randomValue.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (randomValue.nextInt(26)) + 97));
                    //a ~ z (ex. 1 + 79 = 98   =>  (char)89 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (randomValue.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((randomValue.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    //메일 발송
    //sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    //MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    //그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send

    public String sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
        return ePw;
    }

}
