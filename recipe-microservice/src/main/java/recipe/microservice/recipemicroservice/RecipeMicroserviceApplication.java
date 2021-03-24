package recipe.microservice.recipemicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import recipe.microservice.recipemicroservice.model.Product;
import recipe.microservice.recipemicroservice.model.Recipe;
import recipe.microservice.recipemicroservice.repository.ProductRepository;
import recipe.microservice.recipemicroservice.repository.RecipeRepository;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class RecipeMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeMicroserviceApplication.class, args);
	}
}

@Component
class DemoCommandLineRunner implements CommandLineRunner {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	RecipeRepository recipeRepository;

	@Override
	public void run(String... args) throws Exception {

		Product product1 = productRepository.save(new Product("productTypeId1", "product1"));
		Product product2 = productRepository.save(new Product("productTypeId2", "product2"));
		Product product3 = productRepository.save(new Product("productTypeId3", "product3"));

		ArrayList<Product> productsForRecipe1 = new ArrayList<Product>();
		productsForRecipe1.add(product1);
		productsForRecipe1.add(product2);
		productsForRecipe1.add(product3);

		recipeRepository.save(new Recipe("voce", "Recipe1", "Ingredients and instructions for recipe 1", productsForRecipe1));

		Product product4 = productRepository.save(new Product("productTypeId1", "product1"));
		Product product5 = productRepository.save(new Product("productTypeId2", "product2"));
		Product product6 = productRepository.save(new Product("productTypeId3", "product3"));

		ArrayList<Product> productsForRecipe2 = new ArrayList<Product>();
		productsForRecipe2.add(product4);
		productsForRecipe2.add(product5);
		productsForRecipe2.add(product6);

		recipeRepository.save(new Recipe("povrce", "Recipe2", "Ingredients and instructions for recipe 2", productsForRecipe2));

		Product product7 = productRepository.save(new Product("productTypeId1", "product1"));
		Product product8 = productRepository.save(new Product("productTypeId2", "product2"));
		Product product9 = productRepository.save(new Product("productTypeId3", "product3"));

		ArrayList<Product> productsForRecipe3 = new ArrayList<Product>();
		productsForRecipe3.add(product7);
		productsForRecipe3.add(product8);
		productsForRecipe3.add(product9);

		recipeRepository.save(new Recipe("brasno", "Recipe3", "Ingredients and instructions for recipe 3", productsForRecipe3));
	}


}
