package product.microservice.productmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Image;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findImageByUrl(String url);
    List<Image> findAllByProductId(Long productId);
    @Transactional
    void deleteAllByProductId(Long productId);
}
