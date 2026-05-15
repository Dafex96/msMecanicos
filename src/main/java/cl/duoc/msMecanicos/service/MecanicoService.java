package cl.duoc.msMecanicos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msMecanicos.dto.MecanicoDTO;
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

    public MecanicoDTO buscarMecanicoDTOPorId(Integer id) {
        Mecanico mecanico = buscarPorId(id);
        return new MecanicoDTO(mecanico.getId(), mecanico.getNombre(), mecanico.getEspecialidad().getNombre());
    }
}
