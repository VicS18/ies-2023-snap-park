package snappark.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Park {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column
    private float latitude;

    @Column
    private float longitude;

    @Column(nullable = false)
    private double entranceFee;

    @Column
    private int maxLotation;

}
