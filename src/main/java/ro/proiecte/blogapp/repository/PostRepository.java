package ro.proiecte.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.proiecte.blogapp.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
