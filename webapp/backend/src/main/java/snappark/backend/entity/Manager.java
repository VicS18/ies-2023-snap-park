package snappark.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "park"})
@EqualsAndHashCode(exclude = {"user", "park"})
@Entity
@IdClass(ManagerId.class)
public class Manager{

    @Id
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    User user;

    @Id
    @ManyToOne
    @JoinColumn(name="park_id", referencedColumnName = "id")
    Park park;
}
