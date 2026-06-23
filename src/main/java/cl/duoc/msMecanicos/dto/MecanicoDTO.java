package cl.duoc.msMecanicos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa a un mecánico")
public class MecanicoDTO {

    @Schema(description = "ID del mecánico")
    private Integer id;
    
    @Schema(description = "Nombre del mecánico")
    private String nombre;
}
