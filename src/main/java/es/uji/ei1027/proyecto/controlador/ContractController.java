package es.uji.ei1027.proyecto.controlador;

import es.uji.ei1027.proyecto.Validator.ContractValidator;
import es.uji.ei1027.proyecto.dao.ContractDAO;
import es.uji.ei1027.proyecto.modelo.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.Binding;

@Controller
@RequestMapping("/contract")
public class ContractController {

   ContractDAO cDAO;

   @Autowired
   public void setContractDAO(ContractDAO cDAO){
      this.cDAO = cDAO;
   }

   @RequestMapping("/list")
   public String listContracts (Model model){
      model.addAttribute("contracts", cDAO.getContracts());
      return "contract/list";
   }
   @RequestMapping(value = "/add", method = RequestMethod.GET)
   public String addContract(Model model) {
      model.addAttribute("contract", new Contract());
      return "contract/add";
   }

   @RequestMapping(value = "/add", method = RequestMethod.POST)
   public String processAddSubmit(@ModelAttribute("contract") Contract contract,
                                  BindingResult bindingResult) {
      ContractValidator validator = new ContractValidator();
      validator.validate(contract, bindingResult);
      if (bindingResult.hasErrors())
         return "contract/add";
      cDAO.addContract(contract);
      return "redirect:list";
   }

   @RequestMapping(value = "/update/{idContract}", method = RequestMethod.GET)
   public String editContract(@PathVariable String idContract, Model model) {
      Contract contract = cDAO.getContractById(idContract);
      model.addAttribute("contract", contract);
      return "contract/update";
   }

   @RequestMapping(value = "/update", method = RequestMethod.POST)
   public String processUpdateSubmit(@ModelAttribute("contract") Contract contract,
                                     BindingResult bindingResult) {
      ContractValidator validator = new ContractValidator();
      validator.validate(contract, bindingResult);
      if (bindingResult.hasErrors())
         return "contract/update";
      cDAO.updateContract(contract);
      return "redirect:list";
   }

   @RequestMapping(value = "/delete/{idContract}")
   public String processDelete(@PathVariable int idContract) {
      cDAO.deleteContract(idContract);
      return "redirect:../list";
   }

   @RequestMapping(value = "/Contrato/{DNICand}", method = RequestMethod.GET)
   public String mandarContrato(@PathVariable String DNICand, Model model){

      Contract c = cDAO.getContractsByPATI(DNICand);

      if (c == null){
         model.addAttribute("error", "No existe un contrato para el profesional con DNI " + DNICand);
         return "contract/error";
      }

      model.addAttribute("contract", c);
      return "contract/info";
   }
}
