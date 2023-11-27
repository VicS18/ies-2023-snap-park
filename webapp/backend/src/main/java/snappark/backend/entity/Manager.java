package snappark.backend.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager{

    @EmbeddedId
    private ManagerId id;


    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User user;


    @ManyToOne
    @MapsId("parkId")
    @JoinColumn(name="park_id")
    private Park park;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ManagerId implements Serializable{
        @ManyToOne
        @JoinColumn(name = "user_id")
         private User userId;

        @ManyToOne
        @JoinColumn(name="park_id")
        private Park parkId;
    }

}
