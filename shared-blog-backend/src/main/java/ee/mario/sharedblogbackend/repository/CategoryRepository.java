package ee.mario.sharedblogbackend.repository;

import ee.mario.sharedblogbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
