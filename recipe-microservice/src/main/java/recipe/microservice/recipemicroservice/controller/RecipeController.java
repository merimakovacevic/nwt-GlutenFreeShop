package recipe.microservice.recipemicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import recipe.microservice.recipemicroservice.exception.ApiRequestException;
import recipe.microservice.recipemicroservice.model.Product;
import recipe.microservice.recipemicroservice.model.Recipe;
import recipe.microservice.recipemicroservice.repository.RecipeRepository;
import recipe.microservice.recipemicroservice.service.RecipeService;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path="/recipe")
public class RecipeController {
    @Autowired
    RecipeService recipeService;


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Recipe> getAllRecipe(){
        return recipeService.getAllRecipe();
    }

    @GetMapping(path="/id={id}")
    public @ResponseBody Optional<Recipe> getRecipeDetails (@PathVariable Integer id){
        Optional<Recipe> recipe = recipeService.getRecipeDetails(id);
        if(recipe.isEmpty()) throw new ApiRequestException("Recipe with id: " + id.toString() + " is not found!");
        else return recipe;
    }

    @GetMapping(path="/productTypeId={productTypeId}")
    public @ResponseBody Iterable<Recipe> findRecipeByIngredients(@PathVariable String productTypeId) {
        ArrayList<Recipe> recipes = (ArrayList<Recipe>) recipeService.findRecipeByIngredients(productTypeId);
        if(recipes.size() == 0) throw new ApiRequestException("Recipe with productTypeId: " + productTypeId + " is not found!");
        else return recipes;
    }

    @GetMapping(path="/name={name}")
    public @ResponseBody Iterable<Recipe> findRecipeByName(@PathVariable String name) {
        ArrayList<Recipe> recipes = (ArrayList<Recipe>) recipeService.findRecipeByName(name);
        if(recipes.size() == 0) throw new ApiRequestException("Recipe with name: " + name + " is not found!");
        else return recipes;
    }
    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity addRecipe(@Valid @RequestBody Recipe recipe){
        return recipeService.addRecipe(recipe);
    }

    @PostMapping(path="/delete")
    public @ResponseBody String deleteRecipe(@RequestBody Recipe recipe){
        return recipeService.deleteRecipe(recipe);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

