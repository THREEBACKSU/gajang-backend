package cuk.api.Bank.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="bank")
@Table
@Data
@NoArgsConstructor
@ToString
@NamedQueries({
        @NamedQuery(
                name = "Bank_getBankList",
                query = "SELECT b from bank b"
        )
})
public class Bank {
    @Id
    @Column(name = "b_id")
    private int b_id;

    @Column(name = "name")
    private String name;
}
