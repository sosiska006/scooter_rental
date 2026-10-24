package api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {
}