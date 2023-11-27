package snappark.backend.entity;

import java.io.Serializable;

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

    @Column
    private long date;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class OccupancyHistoryId implements Serializable{
        @ManyToOne
        @JoinColumn(name="park_id")
        private Park park;

        @ManyToOne
        @JoinColumn(name="sensor_id")
        private Sensor sensor;
    }

}
