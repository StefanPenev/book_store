package com.example.book_store.controller;

import com.example.book_store.model.BookPicture;
import com.example.book_store.service.BookPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/books")
public class BookPictureController {

    private final BookPictureService bookPictureService;

    @Autowired
    public BookPictureController(BookPictureService bookPictureService) {
        this.bookPictureService = bookPictureService;
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CLIENT')")
    @PostMapping("/upload")
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            bookPictureService.storePicture(file);

            return ResponseEntity.ok(HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN', 'CLIENT')")
    @GetMapping("/pictures/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        BookPicture bookPicture = bookPictureService.getPicture(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bookPicture.getName() + "\"")
                .body(bookPicture.getData());
    }
}
