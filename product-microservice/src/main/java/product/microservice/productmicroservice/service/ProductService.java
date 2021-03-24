package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public Iterable<Product> getAll () {
        return productRepository.findAll();
    }

    public String addNew (Product product){
        if (product.getProductType() == null) throw new ApiRequestException("Product type not assigned");
        Integer typeId = product.getProductType().getId();
        if (typeId == null) throw new ApiRequestException("Product type not assigned");
        Optional<ProductType> productType = productTypeRepository.findById(typeId);
        if (productType.isEmpty()) throw new ApiRequestException("Product type with id " + typeId + " does not exist!");
        if (product.getName().equals("") || product.getName() == null) throw new ApiRequestException("Name is not valid");
        if (product.getDescription().equals("") || product.getDescription() == null) throw new ApiRequestException("Description is not valid");
        if (product.getTotalRating() < 0 || product.getName() == null) throw new ApiRequestException("Total rating is not valid");
        if (product.getNumberOfRatings() < 0 || product.getNumberOfRatings() == null) throw new ApiRequestException("Number of ratings is not valid");
        product.setProductType(productType.get());
        Product savedProduct = productRepository.save(product);
        return "Saved";
    }

    public Product getById (Integer id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ApiRequestException("Product with id "+id+" does not exist");
        return product.get();
    }

    public String deleteProductById (Integer id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ApiRequestException("Product with id "+id+" does not exist");
        productRepository.deleteById(id);
        return "Deleted";
    }

    public String updateProduct(Product newProduct,Integer id){
        Integer productId = newProduct.getId();
        if (productId == null) throw new ApiRequestException("Id in object is not valid!");
        if (productId != id) throw new ApiRequestException("Id in url is not equal id in object!");
        if (newProduct.getName() == "" || newProduct.getName() == null) throw new ApiRequestException("Name is not valid!");
        Product product = productRepository.findById(id).get();
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setNumberOfRatings(newProduct.getNumberOfRatings());
        product.setTotalRating(newProduct.getTotalRating());
        productRepository.save(product);
        return "Updated";
    }
}
