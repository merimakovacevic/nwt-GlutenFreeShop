package recipe.microservice.recipemicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipe.microservice.recipemicroservice.model.Product;
import recipe.microservice.recipemicroservice.model.Recipe;
import recipe.microservice.recipemicroservice.repository.RecipeRepository;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping(path="/recipe")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Recipe> getAllRecipe(){
        return recipeRepository.findAll();
    }

    @GetMapping(path="/id={id}")
    public @ResponseBody Optional<Recipe> getRecipeDetails (@PathVariable Integer id){
        return recipeRepository.findById(id);
    }

    @GetMapping(path="/productTypeId={productTypeId}")
    public @ResponseBody Iterable<Recipe> findRecipeByIngredients(@PathVariable String productTypeId) {
        ArrayList<Recipe> recipesWithIngredient = new ArrayList<Recipe>();
        for (Recipe recipe : recipeRepository.findAll()) {
            if(recipe.getProductTypeId().equals(productTypeId)){
                recipesWithIngredient.add(recipe);
            }
        }
        return recipesWithIngredient;
    }

    @GetMapping(path="/name={name}")
    public @ResponseBody Iterable<Recipe> findRecipeByName(@PathVariable String name) {
        ArrayList<Recipe> recipesWithName = new ArrayList<Recipe>();
        for (Recipe recipe : recipeRepository.findAll()) {
            if(recipe.getName().equals(name)){
                recipesWithName.add(recipe);
            }
        }
        return recipesWithName;
    }
    @PostMapping(path="/add")
    public @ResponseBody String addRecipe(@RequestBody Recipe recipe){
        recipeRepository.save(recipe);
        return "Saved";
    }

    @PostMapping(path="/delete")
    public @ResponseBody String deleteRecipe(@RequestBody Recipe recipe){
        recipeRepository.delete(recipe);
        return "Deleted";
    }
}

