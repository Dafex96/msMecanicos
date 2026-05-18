package cl.duoc.msMecanicos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cl.duoc.msMecanicos.model.Especialidad;
import cl.duoc.msMecanicos.model.Mecanico;
import cl.duoc.msMecanicos.repository.EspecialidadRepository;
import cl.duoc.msMecanicos.repository.MecanicoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(MecanicoRepository mecaRepo, EspecialidadRepository espRepo) {
        return args -> {

            if (mecaRepo.count() > 0 || espRepo.count() > 0) {
                System.out.println("Datos ya existen, no se cargan nuevamente...");
            }else{
                Especialidad esp1 = new Especialidad(null, "Bencineros");
                Especialidad esp2 = new Especialidad(null, "Petroleros");

                espRepo.save(esp1);
                espRepo.save(esp2);

                Mecanico mecanico1 = new Mecanico(null, "12345678-9", "Juan", "Perez", "987654321", esp1);
                Mecanico mecanico2 = new Mecanico(null, "98765432-1", "María", "Gómez", "123456789", esp2);

                mecaRepo.save(mecanico1);
                mecaRepo.save(mecanico2);

                System.out.println("Datos cargados con exito...");
            };
        };
    }
}
