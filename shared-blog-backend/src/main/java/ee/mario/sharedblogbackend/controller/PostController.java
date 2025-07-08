package ee.mario.sharedblogbackend.controller;

import ee.mario.sharedblogbackend.entity.Post;
import ee.mario.sharedblogbackend.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class PostController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostRepository postRepository;

    @GetMapping("posts")
    public ResponseEntity<Page<Post>> getPost(Pageable pageable) {
        return ResponseEntity.ok().body(postRepository.findAll(pageable));
    }

    @GetMapping("public-posts")
    public ResponseEntity<Page<Post>> getPublicPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findByPublishedTrue(pageable);
        System.out.println(modelMapper);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("search-posts")
    public ResponseEntity<Page<Post>> searchPublicPosts(@RequestParam String searchTerm, Pageable pageable) {
        Page<Post> posts = postRepository.searchPublishedPosts(searchTerm, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("pending-posts")
    public ResponseEntity<Page<Post>> getPrivatePosts(Pageable pageable) {
        Page<Post> posts = postRepository.findByPublishedFalse(pageable);
        System.out.println(modelMapper);
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping("posts")
    public Post addPost(@RequestBody Post post) {
        //TODO: KUI ON OLEMAS ID, siis viskab errori
        if (post.getId() != null) {
            throw new RuntimeException("Cannot add with ID!");
        }
        post.setTimestamp(new Date());
        return postRepository.save(post);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(postRepository.findById(id).orElseThrow());
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<List<Post>> deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().body(postRepository.findAll());
    }

    @PutMapping("posts")
    public ResponseEntity<Post> editPost(@RequestBody Post post) {
        //TODO: KUI ON PUUDU ID, siis viskab errori
        if (post.getId() == null) {
            throw new RuntimeException("Cannot add without ID!");
        }
        postRepository.save(post);
        return ResponseEntity.ok().body(postRepository.findById(post.getId()).orElseThrow());
    }






}
