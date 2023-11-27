package snappark.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Light;
import snappark.backend.repository.LightRepository;

@Service
@AllArgsConstructor
public class LightServiceImpl implements LightService {
    private LightRepository rep;
    public Light getLight(Long id){
       return rep.findById(id);
    }

    public Light getLightBySensor(Long id){
        return rep.findBySensor(id);
    }

    public Light createLight(Light Light);

}
