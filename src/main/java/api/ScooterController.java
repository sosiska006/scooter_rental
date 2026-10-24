package api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScooterController {

    private final ScooterRepository repository;

    public ScooterController(ScooterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/health")
    public String health() {
        return "hello world)";
    }

    // Read: все самокаты
    @GetMapping("/scooters")
    public List<Scooter> getAll() {
        return repository.findAll();
    }

    // Read: один самокат
    @GetMapping("/scooters/{id}")
    public ResponseEntity<Scooter> getOne(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create
    @PostMapping("/scooters")
    @ResponseStatus(HttpStatus.CREATED)
    public Scooter create(@RequestBody Scooter scooter) {
        scooter.setId(null);
        return repository.save(scooter);
    }

    // Update
    @PutMapping("/scooters/{id}")
    public ResponseEntity<Scooter> update(@PathVariable Long id, @RequestBody Scooter scooter) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        scooter.setId(id);
        return ResponseEntity.ok(repository.save(scooter));
    }

    // Delete
    @DeleteMapping("/scooters/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}