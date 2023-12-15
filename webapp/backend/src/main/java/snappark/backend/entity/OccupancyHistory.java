package snappark.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class OccupancyHistory {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="park_id")
    private Park park;  
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column 
    private Boolean type;

    @Column
    private int lotation;

    @Column
    private long date;

    

}
