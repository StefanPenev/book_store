package com.example.book_store.repository;

import com.example.book_store.model.BookPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPictureRepository extends JpaRepository<BookPicture, Long> {
}
