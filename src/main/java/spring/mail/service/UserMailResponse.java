package spring.mail.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserMailResponse {

    private String ePw;
    private String code;

}
