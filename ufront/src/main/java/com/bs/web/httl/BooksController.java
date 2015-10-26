package com.bs.web.httl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bs.api.model.Book;
import com.bs.api.model.User;
import com.bs.api.service.BookService;
import com.bs.web.util.SessionUtil;

@Controller  
public class BooksController {

	@Autowired
	private BookService bookService;


	@RequestMapping("/p/books")
	public String books(
			/* Map<String, Object> context, */HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
//		context.put("books", bookService.findBooks());
		List<Book> lb = bookService.findBooks();
		
		
		/**
		 * 不同的模板配置属性不一样，加载不同的httl.properties
		 * 
		 * Engine engine = Engine.getEngine("httl.properties"); 
		 * Template template = engine.getTemplate("/books.httl"); 
		 * template.render(lb, response.getOutputStream());
		*/
		
		
		User user = SessionUtil.getUserFromSession(request);
		if(user != null){
			for(Book b:lb){
				b.setUser(user);
			}
		}
		model.addAttribute("books", lb);
		return "book/books";
	}

}
