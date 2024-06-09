package cuk.api.User.Entities;

import cuk.api.User.Request.SignUpRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="user")
@Table
@Data
@ToString
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "User_signIn",
                query = "SELECT u from user u WHERE u.u_id = :u_id AND u.password = :password"
        )
})
public class User {
    @Id
    @Column(name = "u_id")
    private String u_id;

    @Column(name = "password")
    private String password;

    @Column(name = "address_id")
    private int address_id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "bank_id")
    private int bank_id;

    @Column(name = "account_number")
    private String account_number;

    @Column(name = "rating")
    private float rating;

    public User(SignUpRequest signUpRequest) {
        this.u_id = signUpRequest.getU_id();
        this.password = signUpRequest.getPassword();
        this.address_id = signUpRequest.getAddress_id();
        this.phone = signUpRequest.getPhone();
        this.bank_id = signUpRequest.getBank_id();
        this.account_number = signUpRequest.getAccount_number();
        this.rating = 36.5f;
    }
}
