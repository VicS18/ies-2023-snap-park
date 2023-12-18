package snappark.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Occupancy {

    @Id
    private Long parkId;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private Park park;

    @Column
    private int lotation;
}
    