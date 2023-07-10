package spring.mail.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.mail.service.JavaMailService;

@RestController
@RequiredArgsConstructor
public class JavaMailController {

    private final JavaMailService mailService;


    //회원가입 싯 이메일 인증
    @PostMapping("/mailConfirm")
    public String emailConfirm(@RequestParam String email)throws Exception {
        String code = mailService.sendSimpleMessage(email);
        return code;
    }


}
