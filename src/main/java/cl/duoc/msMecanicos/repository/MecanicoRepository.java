package cl.duoc.msMecanicos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.msMecanicos.model.Mecanico;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Integer> {

    Optional<Mecanico> findByRut(String rut);

    List<Mecanico> findByDisponibleTrue();
}
