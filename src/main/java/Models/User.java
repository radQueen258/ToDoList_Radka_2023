package Models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {
    private long UserId;
    private String UserEmail;
    private String UserPassword;
    private String UserNickname;
    private Date UserRegistration;
    private Boolean UserEmailVerification;
}
