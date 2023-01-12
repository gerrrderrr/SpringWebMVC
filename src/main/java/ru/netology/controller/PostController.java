package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.model.PostDTO;
import ru.netology.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<PostDTO> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public PostDTO getById(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public PostDTO save(@RequestBody PostDTO postDTO) {
        return service.save(postDTO);
    }

    @PostMapping("/{id}")
    public PostDTO update(@PathVariable long id, @RequestBody PostDTO postDTO) {
        return service.update(id, postDTO);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}