package snappark.backend.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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


    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("userId")
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("parkId")
    @JoinColumn(name="park_id")
    private Park park;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(id, manager.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManagerId implements Serializable{
        @ManyToOne
        @JoinColumn(name = "user_id")
         private User userId;

        @ManyToOne
        @JoinColumn(name="park_id")
        private Park parkId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ManagerId managerId = (ManagerId) o;
            return Objects.equals(userId, managerId.userId) &&
                   Objects.equals(parkId, managerId.parkId);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(userId, parkId);
        }
        
    }

}
