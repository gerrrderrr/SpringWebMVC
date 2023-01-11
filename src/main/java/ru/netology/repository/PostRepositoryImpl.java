package ru.netology.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private static final String SAVED = "Saved posts";
    private static final String DELETED = "Deleted posts";
    private static boolean SAVED_POSTS_INIT = false;

    private static final Map<String, ConcurrentHashMap<Long, Post>> SAVED_POSTS = new ConcurrentHashMap<>();
    private static final AtomicLong POST_COUNTER = new AtomicLong();

    @ExceptionHandler(NotFoundException.class)
    @Override
    public List<Post> all() {
        checkIfSavedPostsIsInit();
        return SAVED_POSTS.get(SAVED).values().stream().toList();
    }

    @ExceptionHandler(NotFoundException.class)
    @Override
    public Optional<Post> getById(long id) {
        checkIfSavedPostsIsInit();
        if (SAVED_POSTS.get(SAVED).containsKey(id)) {
            return Optional.of(SAVED_POSTS.get(SAVED).get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Post save(Post post) {
        if (!SAVED_POSTS_INIT) {
            initSavedPosts();
        }
        final long id = post.getId();
        if (id == 0) {
            return createNewId(post);
        } else {
            return updatePost(id, post);
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @Override
    public void removeById(long id) {
        checkIfSavedPostsIsInit();
        if (SAVED_POSTS.get(SAVED).containsKey(id)) {
            SAVED_POSTS.get(DELETED).put(id, SAVED_POSTS.get(SAVED).get(id));
            SAVED_POSTS.get(SAVED).remove(id);
        } else {
            throw new NotFoundException();
        }
    }

    private void initSavedPosts() {
        SAVED_POSTS_INIT = true;
        SAVED_POSTS.put(SAVED, new ConcurrentHashMap<>());
        SAVED_POSTS.put(DELETED, new ConcurrentHashMap<>());
    }

    private Post createNewId(Post post) {
        final long newId = POST_COUNTER.addAndGet(1);
        post.setId(newId);
        SAVED_POSTS.get(SAVED).put(newId, post);
        return SAVED_POSTS.get(SAVED).get(newId);
    }

    private Post updatePost(long id, Post post) {
        SAVED_POSTS.get(SAVED).replace(id, post);
        return SAVED_POSTS.get(SAVED).get(id);
    }

    private void checkIfSavedPostsIsInit() {
        if (!SAVED_POSTS_INIT) {
            throw new NotFoundException();
        }
    }
}
