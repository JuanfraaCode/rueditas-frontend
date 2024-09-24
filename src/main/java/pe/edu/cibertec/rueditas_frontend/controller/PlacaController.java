package pe.edu.cibertec.rueditas_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.rueditas_frontend.viewmodel.PlacaModel;

@Controller
@RequestMapping("/placa")
public class PlacaController {

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

            //Prueba para ver si Funciona
            PlacaModel placaModel = new PlacaModel("00", "","232-323","KIA","Sportage","4","25000","Azul");
            model.addAttribute("placaModel", placaModel);
            return "principal";

    }

}
