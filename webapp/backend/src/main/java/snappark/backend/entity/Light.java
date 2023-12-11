package snappark.backend.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
public class Light {
    
    @EmbeddedId
    private LightId id;

    @Column
    private Double intensity;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LightId implements Serializable{
        @ManyToOne
        @JoinColumn(name="park_id")
        private Park park;

        @OneToOne
        @JoinColumn(name="sensor_id")
        private Sensor sensor;
    }

    // Static factory method to create LightId instances
    public static LightId createLightId(Park park, Sensor sensor) {
        return new LightId(park, sensor);
    }
}
