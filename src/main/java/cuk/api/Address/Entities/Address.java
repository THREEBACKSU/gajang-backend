package cuk.api.Address.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="address")
@Table
@Data
@ToString
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Address_getProvince",
                query = "SELECT distinct a.province from address a"
        ),
        @NamedQuery(
                name = "Address_getCityByProvince",
                query = "SELECT distinct a.city from address a where a.province = :province"
        ),
        @NamedQuery(
                name = "Address_getTownByCityAndProvince",
                query = "SELECT distinct a.town from address a where a.province = :province and a.city = :city"
        ),
        @NamedQuery(
                name = "Address_getTownByProvinceAndCity",
                query = "SELECT distinct a.town from address a where a.province = :province and a.city = :city"
        ),
        @NamedQuery(
                name = "Address_getAddressId",
                query = "SELECT a.id from address a where a.province = :province and a.city = :city and a.town = :town"
        )
})
public class Address {
    @Id
    @Column(name = "a_id")
    private int a_id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    @Column(name="town")
    private String town;
}
