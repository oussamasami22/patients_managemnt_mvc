package ma.prd.patients_mvc;

import ma.prd.patients_mvc.entites.Patient;
import ma.prd.patients_mvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"Hassan",new Date(),false,12));
            patientRepository.save(new Patient(null,"Oussama",new Date(),true,14));
            patientRepository.save(new Patient(null,"Amina",new Date(),false,16));
            patientRepository.save(new Patient(null,"Walid",new Date(),true,18));
            patientRepository.save(new Patient(null,"Rachid",new Date(),false,20));
            patientRepository.save(new Patient(null,"Nassim",new Date(),true,22));
            patientRepository.save(new Patient(null,"Anouar",new Date(),false,24));
            patientRepository.save(new Patient(null,"Wiam",new Date(),true,26));
            patientRepository.save(new Patient(null,"Hassan",new Date(),false,12));
            patientRepository.save(new Patient(null,"Oussama",new Date(),true,14));
            patientRepository.save(new Patient(null,"Amina",new Date(),false,16));
            patientRepository.save(new Patient(null,"Walid",new Date(),true,18));
            patientRepository.save(new Patient(null,"Rachid",new Date(),false,20));
            patientRepository.save(new Patient(null,"Nassim",new Date(),true,22));
            patientRepository.save(new Patient(null,"Anouar",new Date(),false,24));
            patientRepository.save(new Patient(null,"Naima",new Date(),false,28));
            patientRepository.save(new Patient(null,"Samia",new Date(),true,30));
            patientRepository.save(new Patient(null,"Ahmed",new Date(),false,32));
            patientRepository.save(new Patient(null,"Amine",new Date(),true,34));

            patientRepository.findAll().forEach(patient -> {
                System.out.println(patient.getName());
            });
        };
    }
}
