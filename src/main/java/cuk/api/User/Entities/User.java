package cuk.api.User.Entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name="user")
@Table
@Data
@ToString
public class User {
    @Id
    @Column(name = "u_id")
    @Size(min=3)
    private String u_id;

    @Column(name = "password")
    @Size(min=8)
    private String password;

    @Column(name = "address_id")
    @NotNull
    private int address_id;

    @Column(name = "phone")
    @NotBlank
    private String phone;

    @Column(name = "bank_id")
    @NotNull
    private int bank_id;

    @Column(name = "account_number")
    @NotBlank
    private String account_number;

    @Column(name = "rating")
    private float rating;
}
