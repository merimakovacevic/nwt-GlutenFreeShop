package recipe.microservice.recipemicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import recipe.microservice.recipemicroservice.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

}

