package snappark.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
public class OccupancyHistory {

    @EmbeddedId
    private OccupancyHistoryId id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column 
    private Boolean type;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class OccupancyHistoryId{
        @Id
        @ManyToOne
        @JoinColumn(name="park_id")
        private Park park;

        @Id
        @Column
        private long date;

        @Id
        @ManyToOne
        @JoinColumn(name="sensor_id")
        private Sensor sensor;
    }

}
