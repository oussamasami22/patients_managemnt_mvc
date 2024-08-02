package ma.prd.patients_mvc.repositories;

import ma.prd.patients_mvc.entites.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
  Page<Patient> findByNameContains(String Kw, Pageable pageable);
}
