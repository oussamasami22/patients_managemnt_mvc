package ma.prd.patients_mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.prd.patients_mvc.entites.Patient;
import ma.prd.patients_mvc.repositories.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private final PatientRepository patientRepository; //CONNECTER A LA BD
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "Keyword", defaultValue = "") String Keyword)
    {
        logger.debug("Index endpoint accessed with page: {}, size: {}, Keyword: {}", page, size, Keyword);
        Page<Patient> pagePatients = patientRepository.findByNameContains(Keyword, PageRequest.of(page, size));
        model.addAttribute("ListPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("Keyword",Keyword);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id, String Keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&Keyword=" + Keyword;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/formPatients")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping(path = "/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult, @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String Keyword) {
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/index?page=" + page + "&Keyword=" + Keyword;
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id, String Keyword, int page) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient Not Found");
        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("Keyword", Keyword);
        return "editPatient";
    }

    // Add login method
    @GetMapping("/login")
    public String login() {
        return "login"; // This will return the login.html page
    }
}
