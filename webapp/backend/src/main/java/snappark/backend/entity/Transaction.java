package snappark.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "entrance_park_id", referencedColumnName = "park_id")
    @JoinColumn(name = "entrance_id", referencedColumnName = "id")
    private OccupancyHistory entrance;

    @ManyToOne
    @JoinColumn(name = "exit_park_id", referencedColumnName = "park_id")
    @JoinColumn(name = "exit_id", referencedColumnName = "id")
    private OccupancyHistory exit;

    @Column
    private Long timestamp;

    @Column
    private Double profit;


}
