package tacos.web.ingredient;

public interface IngredientRepository {

	Iterable<Ingredient> findAll();
	Ingredient findOne(String id);
	Ingredient save(Ingredient ingredient);
}
