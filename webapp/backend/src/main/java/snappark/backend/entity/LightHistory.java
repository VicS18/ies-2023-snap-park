package snappark.backend.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class LightHistory {

    @EmbeddedId
    private Long id;

    @Column
    private int intensity;

    @Column
    private long date;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class LightHistoryId implements Serializable{
        @ManyToOne
        @JoinColumn(name="park_id")
        @JsonIgnore
        private Park park;

        @OneToOne
        @JoinColumn(name="sensor_id")
        private Sensor sensor;
    }

}
