package cuk.api.User.Entities;

import cuk.api.User.Request.SignUpRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Entity(name="user")
@Table
@Data
@ToString
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "User_signIn",
                query = "SELECT u from user u WHERE u.u_id = :u_id AND u.password = :password"
        ),
        @NamedQuery(
                name = "User_loadUserByU_id",
                query = "SELECT u from user u WHERE u.u_id = :u_id"
        ),
        @NamedQuery(
                name = "User_isDuplicatedName",
                query = "SELECT u from user u WHERE u.name = :name"
        )
})
public class User implements Serializable{
    private static final long serialVersionUID = 12345L;
    @Id
    @Column(name = "u_id")
    private String u_id;

    @Column(name = "password")
    private String password;

    @Column (name = "name")
    private String name;

    @Column(name = "address_id")
    private int address_id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "bank_id")
    private int bank_id;

    @Column(name = "account_number")
    private String account_number;

    @Column(name = "join_date")
    private LocalDateTime join_date;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "rating")
    private float rating;

    public User(SignUpRequest signUpRequest, int address_id) {
        this.u_id = signUpRequest.getU_id();
        this.password = signUpRequest.getPassword();
        this.name = signUpRequest.getName();
        this.address_id = address_id;
        this.phone = signUpRequest.getPhone();
        this.bank_id = signUpRequest.getBank_id();
        this.account_number = signUpRequest.getAccount_number();
        this.join_date = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.role = Role.MEMBER;
        this.rating = 36.5f;
    }
}
