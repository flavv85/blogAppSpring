package ro.proiecte.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ro.proiecte.blogapp.dto.PostDto;
import ro.proiecte.blogapp.model.Post;
import ro.proiecte.blogapp.repository.PostRepository;
import ro.proiecte.blogapp.service.AuthService;

import java.time.Instant;

@Service
public class PostService {

    private final AuthService authService;

    public PostService(AuthService authService) {
        this.authService = authService;
    }
    @Autowired
    PostRepository postRepository;

    public void createPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        // user name va fi preliat din AuthService (first Autowire)
        User username = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No user logged"));
        post.setUserName(username.getUsername());
        post.setCreatedOn(Instant.now());
        postRepository.save(post);
    }
}
