package cl.duoc.msMecanicos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msMecanicos.dto.MecanicoDTO;
import cl.duoc.msMecanicos.model.Mecanico;
import cl.duoc.msMecanicos.service.MecanicoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/mecanicos")
public class MecanicoController {

    @Autowired
    private MecanicoService service;

    @GetMapping
    public ResponseEntity<List<Mecanico>> listarMecanicos(){
        List<Mecanico> listaMecanicos = service.listarMecanicos();

        if (listaMecanicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaMecanicos);
        }
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Mecanico> obtenerPorId(@PathVariable Integer id){
        try {
            Mecanico mecanico = service.buscarPorId(id);
            return ResponseEntity.ok(mecanico);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<MecanicoDTO> obtenerMecanicoDTOPorId(@PathVariable Integer id){
        try {
            MecanicoDTO mecanicoDTO = service.buscarMecanicoDTOPorId(id);
            return ResponseEntity.ok(mecanicoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Mecanico> obtenerPorRut(@PathVariable String rut){
        try {
            return ResponseEntity.ok(service.buscarPorRut(rut));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Mecanico> guardarMecanico(@RequestBody Mecanico mecanico){
        Mecanico nuevoMecanico = service.guardarMecanico(mecanico);
        return ResponseEntity.ok(nuevoMecanico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> actualizarMecanico(@PathVariable Integer id, @RequestBody Mecanico mecanico) {
        try {
            Mecanico mecanicoActualizado = service.actualizarMecanico(id, mecanico);
            return ResponseEntity.ok(mecanicoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMecanico(@PathVariable Integer id) {
        try {
            service.eliminarMecanico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
