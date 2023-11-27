package snappark.backend.entity;

import java.io.Serializable;

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
public class AirQuality {

    @EmbeddedId
    private AirQualityId id;

    @Column
    private int aqi;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class AirQualityId implements Serializable{

        @ManyToOne
        @JoinColumn(name="park_id")
        private Park park;
        
        @OneToOne
        @JoinColumn(name="sensor_id")
        private Sensor sensor;

    }

}
