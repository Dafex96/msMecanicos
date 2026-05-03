package cl.duoc.msMecanicos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.msMecanicos.model.Especialidad;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer>{

    Optional<Especialidad> findByNombre(String nombre);
}
