package cl.duoc.msMecanicos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.msMecanicos.model.Mecanico;
import cl.duoc.msMecanicos.repository.MecanicoRepository;

@Service
public class MecanicoService {

    @Autowired
    private MecanicoRepository repo;

    public List<Mecanico> listarMecanicos() {
        return repo.findAll();
    }

    public Mecanico buscarPorId(Integer id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Mecánico no encontrado..."));
    }

    public Mecanico buscarPorRut(String rut){
        return repo.findByRut(rut).orElseThrow(() -> new RuntimeException("Mecánico no encontrado..."));
    }

    public Mecanico guardarMecanico(Mecanico mecanico){
        return repo.save(mecanico);
    }

    public Mecanico actualizarMecanico(Integer id, Mecanico mecanicoActualizado){
        Mecanico mecanicoAnt = repo.findById(id).orElseThrow(() -> new RuntimeException("Mecánico no encontrado..."));

        mecanicoAnt.setNombre(mecanicoActualizado.getNombre());
        mecanicoAnt.setRut(mecanicoActualizado.getRut());
        mecanicoAnt.setEspecialidad(mecanicoActualizado.getEspecialidad());
        return repo.save(mecanicoAnt);
    }

    public void eliminarMecanico(Integer id){
        repo.deleteById(id);
    }

    public List<Mecanico> obtenerMecanicosDisponibles() {
        List<Mecanico> disponibles = repo.findByDisponibleTrue();

        if (disponibles.isEmpty()) {
            throw new RuntimeException("No hay mecánicos disponibles en este momento.");
        }
        return disponibles;
    }

    public Mecanico cambiarDisponibilidad(Integer id, boolean nuevoEstado) {
        Mecanico mecanico = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Mecánico no encontrado"));
        mecanico.setDisponiblilidad(nuevoEstado);
        return repo.save(mecanico);
    }
}
