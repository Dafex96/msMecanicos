package cl.duoc.msMecanicos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import cl.duoc.msMecanicos.dto.MecanicoDTO;
import cl.duoc.msMecanicos.model.Especialidad;
import cl.duoc.msMecanicos.model.Mecanico;
import cl.duoc.msMecanicos.repository.MecanicoRepository;

@ExtendWith(MockitoExtension.class)
public class MecanicoServiceTest {

    @Mock
    private MecanicoRepository repo;

    @InjectMocks
    private MecanicoService mecanicoService;

    private Mecanico mecanicoEjemplo;

    @BeforeEach
    void setup() {
        mecanicoEjemplo = new Mecanico();
        mecanicoEjemplo.setId(1);
        mecanicoEjemplo.setRut("12345678-9");
        mecanicoEjemplo.setNombre("Juan");
        mecanicoEjemplo.setApellido("Perez");
        mecanicoEjemplo.setEspecialidad(new Especialidad(1, "Frenos"));
    }

    @Test
    void listarMecanicos_retornaLista() {
        List<Mecanico> listaFalsa = new ArrayList<>();
        listaFalsa.add(mecanicoEjemplo);
        when(repo.findAll()).thenReturn(listaFalsa);
        
        List<Mecanico> resultado = mecanicoService.listarMecanicos();

        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRut());
    }

    @Test
    void buscarPorId_encontrado() {
        when(repo.findById(1)).thenReturn(Optional.of(mecanicoEjemplo));
        
        Mecanico resultado = mecanicoService.buscarPorId(1);
        
        assertEquals(1, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            mecanicoService.buscarPorId(99);
        });
        
        assertEquals("Mecánico no encontrado...", error.getMessage());
    }

    @Test
    void buscarPorRut_encontrado() {
        when(repo.findByRut("12345678-9")).thenReturn(Optional.of(mecanicoEjemplo));
        
        Mecanico resultado = mecanicoService.buscarPorRut("12345678-9");
        
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void buscarPorRut_noEncontrado() {
        when(repo.findByRut("00000000-0")).thenReturn(Optional.empty());
        
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            mecanicoService.buscarPorRut("00000000-0");
        });
        
        assertEquals("Mecánico no encontrado...", error.getMessage());
    }

    @Test
    void guardarMecanico_exitoso() {
        when(repo.save(any(Mecanico.class))).thenReturn(mecanicoEjemplo);
        
        Mecanico resultado = mecanicoService.guardarMecanico(new Mecanico());
        
        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    void actualizarMecanico_exitoso() {

        Mecanico mecanicoActualizado = new Mecanico();
        mecanicoActualizado.setNombre("Carlos");
        mecanicoActualizado.setRut("98765432-1");
        
        when(repo.findById(1)).thenReturn(Optional.of(mecanicoEjemplo));
        when(repo.save(any(Mecanico.class))).thenReturn(mecanicoEjemplo);

        Mecanico resultado = mecanicoService.actualizarMecanico(1, mecanicoActualizado);

        assertEquals("Carlos", resultado.getNombre());
        assertEquals("98765432-1", resultado.getRut());
    }

    @Test
    void actualizarMecanico_noEncontrado() {
        when(repo.findById(99)).thenReturn(Optional.empty());
        
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            mecanicoService.actualizarMecanico(99, new Mecanico());
        });
        
        assertEquals("Mecánico no encontrado...", error.getMessage());
    }

    @Test
    void eliminarMecanico_exitoso() {
        mecanicoService.eliminarMecanico(1);
        
        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void buscarMecanicoDTOPorId_exitoso() {
        when(repo.findById(1)).thenReturn(Optional.of(mecanicoEjemplo));

        MecanicoDTO resultado = mecanicoService.buscarMecanicoDTOPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }
}