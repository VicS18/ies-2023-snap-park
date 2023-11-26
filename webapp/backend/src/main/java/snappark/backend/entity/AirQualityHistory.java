package snappark.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class AirQualityHistory {

    @EmbeddedId
    private AirQualityHistoryId id;

    @Column
    private int humidity;

    @Column
    private long date;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AirQualityHistoryId{

        @ManyToOne
        @JoinColumn(name="park_id")
        private Park park;
        
        @OneToOne
        @JoinColumn(name="sensor_id")
        private Sensor sensor;

        private Long id;
    }

}
