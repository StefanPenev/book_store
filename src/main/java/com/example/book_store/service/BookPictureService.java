package com.example.book_store.service;

import com.example.book_store.model.BookPicture;
import com.example.book_store.repository.BookPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class BookPictureService {

    private final BookPictureRepository bookPictureRepository;

    @Autowired
    public BookPictureService(BookPictureRepository bookPictureRepository) {
        this.bookPictureRepository = bookPictureRepository;
    }

    public BookPicture storePicture(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        BookPicture bookPicture = new BookPicture(fileName, file.getContentType(), file.getBytes());

        return bookPictureRepository.save(bookPicture);
    }

    public BookPicture getPicture(Long id) {
        return bookPictureRepository.getById(id);
    }

    public Stream<BookPicture> getAllFiles() {
        return bookPictureRepository.findAll().stream();
    }
}
