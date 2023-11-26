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
public class TemperatureHistory {

    @EmbeddedId
    private TemperatureHistoryId id;

    @Column
    private int temperature;

    @Column
    private long date;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class TemperatureHistoryId{
        @Id
        @ManyToOne
        @JoinColumn(name="park_id")
        private Park park;

        @Id
        @OneToOne
        @JoinColumn(name="sensor_id")
        private Sensor sensor;
    }

}
