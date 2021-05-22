package ro.proiecte.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.proiecte.blogapp.dto.PostDto;
import ro.proiecte.blogapp.security.PostService;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    //end point to test daca jwt validation filter functioneaza
    @PostMapping
        public ResponseEntity createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

}
