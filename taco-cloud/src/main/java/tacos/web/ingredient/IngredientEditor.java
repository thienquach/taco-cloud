package tacos.web.ingredient;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class IngredientEditor extends PropertyEditorSupport{

	private IngredientRepository ingredientRepository;
	
	public IngredientEditor(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public String getAsText() {
		Ingredient ingredient = (Ingredient) getValue();
		return ingredient.getId();
	}

	@Override
    public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
            setValue(null);
        }else {
        	setValue(ingredientRepository.findById(text));
        }
	}
}
