package tacos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
		public final IngredientRepository ingredientRepo;
		
		@ModelAttribute(name = "taco")
		public Taco taco() {
			return new Taco();
		}
	 
		@Autowired
		public DesignTacoController(IngredientRepository ingredientRepo) {
			this.ingredientRepo = ingredientRepo;
		}
	
	    @GetMapping
		public String showDesignForm(Model model) {
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			ingredientRepo.findAll().forEach(e -> ingredients.add(e));
			
			Type[] types = Ingredient.Type.values();
			for(Type type : types) {
				model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients,type));
			}
						
			return "design";
		}
	   
	   @PostMapping
	   public String processDesign(@Valid Taco design,Errors errors) {
		   if(errors.hasErrors()) {
			   return "design";
		   }
		   log.info("Process design: " + design);
		   
		   return "redirect:/orders/current";
	   }
	   
	   private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
			return ingredients.stream()
					.filter(x -> x.getType().equals(type))
					.collect(Collectors.toList());
	   }
}
