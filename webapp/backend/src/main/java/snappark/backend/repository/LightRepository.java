package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import snappark.backend.entity.Light;
import snappark.backend.entity.Park;
import snappark.backend.entity.Light.LightId;

public interface LightRepository extends JpaRepository<Light,LightId>{

    @Query("SELECT AVG(l.intensity) FROM Light l WHERE l.id.park = ?1")
    Double getAvgLightLevel(Park park);
}
