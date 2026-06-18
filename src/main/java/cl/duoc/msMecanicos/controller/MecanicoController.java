package cl.duoc.msMecanicos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.msMecanicos.dto.MecanicoDTO;
import cl.duoc.msMecanicos.model.Mecanico;
import cl.duoc.msMecanicos.service.MecanicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/mecanicos")
@Tag(name = "Mecanicos", description = "Operaciones relacionadas con los mecanicos")
public class MecanicoController {

    @Autowired
    private MecanicoService service;

    @GetMapping
    @Operation(
        summary = "Listar todos los mecanicos",
        description = "Retorna una lista de todos los mecanicos registrados en la base de datos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hay mecanicos registrados"),
        @ApiResponse(responseCode = "204", description = "No hay mecanicos registrados")
    })
    public ResponseEntity<List<Mecanico>> listarMecanicos(){
        List<Mecanico> listaMecanicos = service.listarMecanicos();

        if (listaMecanicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listaMecanicos);
        }
    }
    
    @GetMapping("/id/{id}")
    @Operation(
        summary = "Buscar un mecanico por su ID",
        description = "Retorna un mecanico segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mecanico encontrado"),
        @ApiResponse(responseCode = "404", description = "Mecanico no encontrado")
    })
    public ResponseEntity<Mecanico> obtenerPorId(@PathVariable Integer id){
        try {
            Mecanico mecanico = service.buscarPorId(id);
            return ResponseEntity.ok(mecanico);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(
        summary = "Buscar un mecanico por su ID y retornar como DTO",
        description = "Retorna un mecanico como DTO segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mecanico encontrado"),
        @ApiResponse(responseCode = "404", description = "Mecanico no encontrado")
    })
    public ResponseEntity<MecanicoDTO> obtenerMecanicoDTOPorId(@PathVariable Integer id){
        try {
            MecanicoDTO mecanicoDTO = service.buscarMecanicoDTOPorId(id);
            return ResponseEntity.ok(mecanicoDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(
        summary = "Buscar un mecanico por su RUT",
        description = "Retorna un mecanico segun el RUT proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mecanico encontrado"),
        @ApiResponse(responseCode = "404", description = "Mecanico no encontrado")
    })
    public ResponseEntity<Mecanico> obtenerPorRut(@PathVariable String rut){
        try {
            return ResponseEntity.ok(service.buscarPorRut(rut));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(
        summary = "Agregar un nuevo mecanico",
        description = "Crea un nuevo mecanico con los datos proporcionados en el cuerpo de la solicitud"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mecanico creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de mecanico invalidos")
    })
    public ResponseEntity<Mecanico> guardarMecanico(@RequestBody Mecanico mecanico){
        Mecanico nuevoMecanico = service.guardarMecanico(mecanico);
        return ResponseEntity.ok(nuevoMecanico);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un mecanico existente",
        description = "Actualiza los datos de un mecanico existente segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mecanico actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mecanico no encontrado")
    })
    public ResponseEntity<Mecanico> actualizarMecanico(@PathVariable Integer id, @RequestBody Mecanico mecanico) {
        try {
            Mecanico mecanicoActualizado = service.actualizarMecanico(id, mecanico);
            return ResponseEntity.ok(mecanicoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un mecanico",
        description = "Elimina un mecanico segun el ID proporcionado"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Mecanico eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mecanico no encontrado")
    })
    public ResponseEntity<Void> eliminarMecanico(@PathVariable Integer id) {
        try {
            service.eliminarMecanico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
