package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.model.PostDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final Map<Long, Post> SAVED_POSTS = new ConcurrentHashMap<>();
    private final AtomicLong POST_COUNTER = new AtomicLong();

    @Override
    public List<PostDTO> all() {
        return SAVED_POSTS.values()
                .stream()
                .filter(post -> !post.isDeleted())
                .map(Post::postToPostDTO)
                .toList();
    }

    @Override
    public Optional<PostDTO> getById(long id) {
        if (postIsAvailable(id)) {
            return Optional.of(SAVED_POSTS.get(id).postToPostDTO());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public PostDTO save(PostDTO postDTO) {
        final long id = POST_COUNTER.addAndGet(1);
        Post post = new Post(id, postDTO.getContent());
        SAVED_POSTS.put(id, post);
        return SAVED_POSTS.get(id).postToPostDTO();
    }

    @Override
    public Optional<PostDTO> update(long id, PostDTO postDTO) {
        if (postIsAvailable(id)) {
            SAVED_POSTS.get(id).setContent(postDTO.getContent());
            return Optional.of(SAVED_POSTS.get(id).postToPostDTO());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void removeById(long id) {
        if (postIsAvailable(id)) {
            SAVED_POSTS.get(id).setDeleted(true);
        } else {
            throw new NotFoundException();
        }
    }

    private boolean postIsAvailable(long id) {
        return SAVED_POSTS.containsKey(id) && !SAVED_POSTS.get(id).isDeleted();
    }
}
