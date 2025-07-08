package ee.mario.sharedblogbackend.repository;

import ee.mario.sharedblogbackend.dto.PostDTO;
import ee.mario.sharedblogbackend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainsIgnoreCase(String searchTerm);

    Page<Post> findByPublishedTrue(Pageable pageable);
    Page<Post> findByPublishedFalse(Pageable pageable);


    @Query("""
    SELECT p FROM Post p
    WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
       OR LOWER(p.subTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
       OR LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
""")
    Page<Post> searchPosts(@Param("searchTerm") String searchTerm, Pageable pageable);


    @Query("""
    SELECT p FROM Post p
    WHERE p.published = true
    AND 
    (LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) 
    OR LOWER(p.subTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%')) 
    OR LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
""")
    Page<Post> searchPublishedPosts(@Param("searchTerm") String searchTerm, Pageable pageable);


    @Query("SELECT new ee.mario.sharedblogbackend.dto.PostDTO(p.id, p.title, p.subTitle, p.content, p.published) " +
            "FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<PostDTO> searchPostSummaries(@Param("searchTerm") String searchTerm);
}
