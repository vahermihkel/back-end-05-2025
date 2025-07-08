package ee.mihkel.webshop.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class CacheService {

    @Autowired
    ProductRepository productRepository;

    LoadingCache<Long, Product> productCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<>() {
                        @Override
                        public Product load(Long key) {
                            log.info("Getting product from DB");
                            return productRepository.findById(key).orElseThrow();
                        }
                    });


    public Product getProduct(Long id) throws ExecutionException {
        log.info("Getting product...");
        return productCache.get(id);
    }

    public void deleteFromCache(Long id) {
        productCache.invalidate(id);
    }

    public void updateCache(Long id, Product product) {
        productCache.put(id, product);
    }
}
