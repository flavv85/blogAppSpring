package ro.proiecte.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.proiecte.blogapp.dto.PostDto;
import ro.proiecte.blogapp.exception.PostNotFoundException;
import ro.proiecte.blogapp.model.Post;
import ro.proiecte.blogapp.repository.PostRepository;
import ro.proiecte.blogapp.service.AuthService;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {
    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public PostService(AuthService authService, PostRepository postRepository) {
        this.authService = authService;
        this.postRepository = postRepository;
    }

    @Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        // loop posts lists
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUserName());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUserName(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }
}
