package ies.snapPark.entity;

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
public class Manager{
    @Id
    @ManyToOne
    @JoinColumn(name="user_id")
    private User id;

    @Id
    @ManyToOne
    @JoinColumn(name="park_id")
    private Park park;

}
