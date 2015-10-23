package com.bs.api.service;

import java.util.List;
import com.bs.api.model.Book;

public interface BookService {

	List<Book> findBooks() throws Exception;

}
