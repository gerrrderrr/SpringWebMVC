package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.PostDTO;
import ru.netology.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<PostDTO> all() {
        return repository.all();
    }

    public PostDTO getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public PostDTO save(PostDTO postDTO) {
        return repository.save(postDTO);
    }

    public PostDTO update(long id, PostDTO postDTO) {
        return repository.update(id, postDTO).orElseThrow(NotFoundException::new);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}