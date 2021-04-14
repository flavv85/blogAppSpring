package ro.proiecte.blogapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.proiecte.blogapp.dto.PostDto;
import ro.proiecte.blogapp.security.PostService;

@RestController
@RequestMapping("/api/posts/")
public class PostController {
    @Autowired
    private PostService postService;

    //end point to test daca jwt validation filter functioneaza
    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
    postService.createPost(postDto);
    return new ResponseEntity(HttpStatus.OK);
    }
}
