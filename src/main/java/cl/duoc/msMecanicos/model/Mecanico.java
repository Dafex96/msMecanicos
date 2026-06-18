package cl.duoc.msMecanicos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mecanicos")
@Schema(description = "Representa un mecanico en el sistema")
public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del mecanico")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "RUT del mecanico")
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Nombre del mecanico")
    private String nombre;
    
    @Column(nullable = false)
    @Schema(description = "Apellido del mecanico")
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Teléfono del mecanico")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    @Schema(description = "Especialidad del mecanico")
    private Especialidad especialidad;
}
