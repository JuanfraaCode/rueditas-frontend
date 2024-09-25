package pe.edu.cibertec.rueditas_frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.rueditas_frontend.dto.PlacaRequestDTO;
import pe.edu.cibertec.rueditas_frontend.dto.PlacaResponseDTO;
import pe.edu.cibertec.rueditas_frontend.viewmodel.PlacaModel;

@Controller
@RequestMapping("/placa")
public class PlacaController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model){
        PlacaModel placaModel = new PlacaModel("00", "","","","","","","");
        model.addAttribute("placaModel", placaModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("numeroPlaca") String numeroPlaca, Model model){
        // Validar los campos de entrada
          // Que el campo no sea vacio
            if (numeroPlaca == null || numeroPlaca.trim().length() == 0){
                PlacaModel placaModel = new PlacaModel("01", "Error: Complete correctamente la placa","","","","","","");
                model.addAttribute("placaModel", placaModel);
                return "inicio";
            }
            // Validar que sea entre 7 y 8
            if (numeroPlaca.trim().length() < 7 || numeroPlaca.trim().length() > 8 ){
                PlacaModel placaModel = new PlacaModel("02", "Error: Complete la placa","","","","","","");
                model.addAttribute("placaModel", placaModel);
                return "inicio";
            }

            try {
                //Invocar API para validar la placa
                String endpoint = "http://localhost:8081/autenticacion/placa";
                PlacaRequestDTO placaRequestDTO = new PlacaRequestDTO(numeroPlaca);
                PlacaResponseDTO placaResponseDTO = restTemplate.postForObject(endpoint, placaRequestDTO, PlacaResponseDTO.class);

                if (placaResponseDTO.codigo().equals("00")){
                    //Si encontro la placa, inicie y muestre los datos
                    PlacaModel placaModel = new PlacaModel("00", "", placaRequestDTO.placa(), placaResponseDTO.marca(), placaResponseDTO.modelo(),placaResponseDTO.nroAsientos(),placaResponseDTO.precio(),placaResponseDTO.precio());
                    model.addAttribute("placaModel", placaModel);
                    return "principal";
                } else {
                    // Si no encontro la placa, se indica que no se mostro y vuelve al formulario
                    PlacaModel placaModel = new PlacaModel("02", "Error: No se encontro la placa ingresada","", "","","","","");
                    model.addAttribute("placaModel", placaModel);
                    return "inicio";
                }
            } catch (Exception e){
                // Para cuando el back no se haya iniciado o se haya caido
                PlacaModel placaModel = new PlacaModel("99", "Error: Ocurrio un problema con la Autenticación","", "","","","","");
                model.addAttribute("placaModel", placaModel);
                return "inicio";
            }

    }

}
