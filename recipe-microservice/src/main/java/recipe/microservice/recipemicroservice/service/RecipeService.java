package recipe.microservice.recipemicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import recipe.microservice.recipemicroservice.exception.ApiRequestException;
import recipe.microservice.recipemicroservice.model.Product;
import recipe.microservice.recipemicroservice.model.Recipe;
import recipe.microservice.recipemicroservice.repository.ProductRepository;
import recipe.microservice.recipemicroservice.repository.RecipeRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Transactional
@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRepository productRepository;

    public @ResponseBody Iterable<Recipe> getAllRecipe(){
        return recipeRepository.findAll();
    }

    public @ResponseBody
    Optional<Recipe> getRecipeDetails (@PathVariable Integer id){
        return recipeRepository.findById(id);
    }

    public @ResponseBody
    Iterable<Recipe> findRecipeByIngredients(@PathVariable String productTypeId) {
        ArrayList<Recipe> recipesWithIngredient = new ArrayList<Recipe>();
        for (Recipe recipe : recipeRepository.findAll()) {
            if(recipe.getProductTypeId().equals(productTypeId)){
                recipesWithIngredient.add(recipe);
            }
        }
        return recipesWithIngredient;
    }

    public @ResponseBody Iterable<Recipe> findRecipeByName(@PathVariable String name) {
        ArrayList<Recipe> recipesWithName = new ArrayList<Recipe>();
        for (Recipe recipe : recipeRepository.findAll()) {
            if(recipe.getName().contains(name)){
                recipesWithName.add(recipe);
            }
        }
        return recipesWithName;
    }

    public @ResponseBody
    ResponseEntity addRecipe(@Valid @RequestBody Recipe recipe){
        if(recipe.getProducts() == null)
            throw new ApiRequestException("List of products cannot be empty!");
        ArrayList<Product> products = (ArrayList<Product>) recipe.getProducts();
        if (products.size() == 0)
            throw new ApiRequestException("List of products cannot be empty!");
        else {
            Boolean invalid = false;
            for (Product product : products) {
                Integer id = product.getId();
                Optional<Product> existingProduct = productRepository.findById(id);
                if (existingProduct.isEmpty()){
                    invalid = true;
                    throw new ApiRequestException("Product with id " + id + "does not exist!");
                }

            }
            if(invalid == false){
                recipeRepository.save(recipe);
                return ResponseEntity.ok("Recipe is valid and saved!");
            }
            else{
                return ResponseEntity.ok("Recipe is not valid!");
            }
        }
    }

    public @ResponseBody String deleteRecipe(@RequestBody Recipe recipe){
        Integer id = recipe.getId();
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if(existingRecipe.isEmpty()){
            throw new ApiRequestException("Recipe with id " + id + "does not exist!");
        }
        else{
            recipeRepository.delete(recipe);
            return "Deleted";
        }
    }
}
