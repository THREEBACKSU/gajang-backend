package cuk.api.User.Entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="user")
@Table
@Data
@ToString
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
}
