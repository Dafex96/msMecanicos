package cl.duoc.msMecanicos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import cl.duoc.msMecanicos.dto.MecanicoDTO;
import cl.duoc.msMecanicos.model.Especialidad;
import cl.duoc.msMecanicos.model.Mecanico;
import cl.duoc.msMecanicos.service.MecanicoService;

@WebMvcTest(MecanicoController.class)
public class MecanicoControllerTest {

    @MockitoBean
    private MecanicoService mecanicoService; 

    @Autowired
    private MockMvc llamadaFalsa; 

    private Mecanico mecanicoEjemplo;
    private MecanicoDTO mecanicoDTOEjemplo;
    
    @BeforeEach
    void setUp() {
        mecanicoEjemplo = new Mecanico();
        mecanicoEjemplo.setId(1);
        mecanicoEjemplo.setRut("12345678-9");
        mecanicoEjemplo.setNombre("Juan Perez");
        mecanicoEjemplo.setEspecialidad(new Especialidad(1, "Frenos"));

        mecanicoDTOEjemplo = new MecanicoDTO();
        mecanicoDTOEjemplo.setId(1);
        mecanicoDTOEjemplo.setNombre("Juan Perez");
    }

    @Test
    void listarMecanicos_retorna200() throws Exception {
        List<Mecanico> listaFalsa = new ArrayList<>();
        listaFalsa.add(mecanicoEjemplo);

        when(mecanicoService.listarMecanicos()).thenReturn(listaFalsa);

        llamadaFalsa.perform(get("/api/v1/mecanicos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].rut").value("12345678-9"));
    }

    @Test
    void listarMecanicos_retorna204() throws Exception {
        when(mecanicoService.listarMecanicos()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/mecanicos"))
            .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(mecanicoService.buscarPorId(1)).thenReturn(mecanicoEjemplo);

        llamadaFalsa.perform(get("/api/v1/mecanicos/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(mecanicoService.buscarPorId(99)).thenThrow(new RuntimeException("Mecanico no encontrado"));

        llamadaFalsa.perform(get("/api/v1/mecanicos/id/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void buscarDTOPorId_retorna200() throws Exception {
        when(mecanicoService.buscarMecanicoDTOPorId(1)).thenReturn(mecanicoDTOEjemplo);

        llamadaFalsa.perform(get("/api/v1/mecanicos/dto/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void buscarDTOPorId_retorna404() throws Exception {
        when(mecanicoService.buscarMecanicoDTOPorId(99)).thenThrow(new RuntimeException("Mecanico no encontrado"));

        llamadaFalsa.perform(get("/api/v1/mecanicos/dto/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorRut_retorna200() throws Exception {
        when(mecanicoService.buscarPorRut("12345678-9")).thenReturn(mecanicoEjemplo);

        llamadaFalsa.perform(get("/api/v1/mecanicos/rut/12345678-9"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void buscarPorRut_retorna404() throws Exception {
        when(mecanicoService.buscarPorRut("00000000-0")).thenThrow(new RuntimeException("Mecanico no encontrado"));

        llamadaFalsa.perform(get("/api/v1/mecanicos/rut/00000000-0"))
            .andExpect(status().isNotFound());
    }

    @Test
    void guardar_retorna200() throws Exception {
        when(mecanicoService.guardarMecanico(any(Mecanico.class))).thenReturn(mecanicoEjemplo);

        String jsonBody = "{\"rut\":\"12345678-9\",\"nombre\":\"Juan Perez\",\"especialidad\":{\"id\":1,\"nombre\":\"Frenos\"}}";

        llamadaFalsa.perform(post("/api/v1/mecanicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void actualizar_retorna200() throws Exception {
        when(mecanicoService.actualizarMecanico(any(Integer.class), any(Mecanico.class))).thenReturn(mecanicoEjemplo);

        String jsonBody = "{\"rut\":\"12345678-9\",\"nombre\":\"Juan Perez Modificado\"}";

        llamadaFalsa.perform(put("/api/v1/mecanicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void actualizar_retorna404() throws Exception {
        when(mecanicoService.actualizarMecanico(any(Integer.class), any(Mecanico.class)))
            .thenThrow(new RuntimeException("Mecanico no existe"));

        llamadaFalsa.perform(put("/api/v1/mecanicos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"No existo\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(mecanicoService).eliminarMecanico(1);

        llamadaFalsa.perform(delete("/api/v1/mecanicos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404() throws Exception {
        doThrow(new RuntimeException("Mecanico no existe")).when(mecanicoService).eliminarMecanico(99);

        llamadaFalsa.perform(delete("/api/v1/mecanicos/99"))
            .andExpect(status().isNotFound());
    }
}