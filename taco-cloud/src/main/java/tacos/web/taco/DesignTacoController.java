package tacos.web.taco;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.web.ingredient.Ingredient;
import tacos.web.ingredient.Ingredient.Type;
import tacos.web.ingredient.IngredientEditor;
import tacos.web.ingredient.IngredientRepository;
import tacos.web.order.Order;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	private final IngredientRepository ingredientRepository;
	private final TacoRepository tacoRepository;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));

		Stream.of(Ingredient.Type.values()).forEach(type -> {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		});

		model.addAttribute("taco", new Taco());

		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(ingredient -> ingredient.getType() == type).collect(Collectors.toList());
	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Ingredient.class, new IngredientEditor(ingredientRepository));
	}
	
	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			return "design";
		}
//		taco.setIngredients(Arrays.asList(ingredients));
		Taco saved = tacoRepository.save(taco);
		order.addTaco(saved);
		return "redirect:/orders/current";
	}

}
