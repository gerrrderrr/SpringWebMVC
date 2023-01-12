package ru.netology.repository;

import ru.netology.model.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<PostDTO> all();

    Optional<PostDTO> getById(long id);

    PostDTO save(PostDTO post);

    Optional<PostDTO> update(long id, PostDTO postDTO);

    void removeById(long id);
}
