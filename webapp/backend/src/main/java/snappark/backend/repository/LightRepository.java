package snappark.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import snappark.backend.entity.Light;
import snappark.backend.entity.Light.LightId;

public interface LightRepository extends JpaRepository<Light,LightId>{

}
