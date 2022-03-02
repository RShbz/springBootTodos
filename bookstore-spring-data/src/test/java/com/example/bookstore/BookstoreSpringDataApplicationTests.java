package com.example.bookstore;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.exception.RestExceptionBase;
import com.example.bookstore.service.business.StandardBookCatalogService;
import com.example.bookstore.service.business.StandardPurchaseOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = BookstoreSpringDataApplication.class, webEnvironment = WebEnvironment.MOCK)

@AutoConfigureMockMvc
class BookstoreSpringDataApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	StandardBookCatalogService standardBookCatalogService;

	@MockBean
	StandardPurchaseOrderService standardPurchaseOrderService;

	@DisplayName("get request with isbn should return status ok")
	@ParameterizedTest
	@CsvFileSource(resources = "books.csv")
	void getBookByIsbnShoudlReturnOk(Long id, String isbn, String author, String title, int pages, int year,
			double price, String cover) throws Throwable {

		var bookResponse = new BookResponse(id, isbn, author, title, pages, year, price, cover);

		Mockito.when(standardBookCatalogService.findBookByIsbn(isbn)).thenReturn(bookResponse);

		mockMvc.perform(get("/books/" + isbn).accept(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk())
				.andExpect(jsonPath("$.isbn", is(isbn)))
				.andExpect(jsonPath("$.author", is(author)))
				.andExpect(jsonPath("$.title", is(title)))
				.andExpect(jsonPath("$.pages", is(pages)))
				.andExpect(jsonPath("$.year", is(year)))
				.andExpect(jsonPath("$.price", is(price)))

		;

	}

	@ParameterizedTest
	@DisplayName(" add book should return ok !")
	@CsvFileSource(resources = "books.csv")
	void addBookShouldReturnOk(Long id, String isbn, String author, String title, int pages, int year, double price,
			String cover) throws JsonProcessingException, Exception {

		var request = new BookRequest();

		request.setAuthor(author);
		 request.setCover(cover);
		request.setIsbn(isbn);
		request.setPages(pages);
		request.setTitle(title);
		request.setYear(year);
		request.setPrice(price);

		var response = modelMapper.map(request, BookResponse.class);

		Mockito.when(standardBookCatalogService.addBook(request))
		.thenReturn(response);

		mockMvc.perform(
				post("/books")
				
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				
			//	.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.isbn", is(isbn)))
				.andExpect(jsonPath("$.author", is(author)))
				.andExpect(jsonPath("$.title", is(title)))
				.andExpect(jsonPath("$.pages", is(pages)))
				.andExpect(jsonPath("$.year", is(year)))
				.andExpect(jsonPath("$.price", is(price)))
				.andExpect(jsonPath("$.cover", is(cover)))
				
				;

	}

	@DisplayName("get response with identity should return status ok")

	void getBookByIsbnShoudlReturnNotFound(

			) throws Throwable {
		// 1. Test Setup
		String isbn="9";
		var errorMessage = new RestExceptionBase("Cannot find the book!", "unknown.book", "1");

		Mockito.when(standardBookCatalogService.findBookByIsbn(isbn)).thenThrow(errorMessage);
		// 2. Call exercise method
		mockMvc.perform(get("/customers/" + isbn).accept(MediaType.APPLICATION_JSON))
				// 3. Verification
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.messageId", is("unknown.book")))
				.andExpect(jsonPath("$.debugId", is("1")))

		;
		// 4. Tear-down
	}

	@DisplayName("get request with identity should return status ok")
	@ParameterizedTest
	@CsvFileSource(resources = "books.csv")
	void removeCustomerByIdentityShoudlReturnOk(
			Long id, 
			String isbn,
			String author,
			String title,
			int pages,
			int year, 
			double price,
			String cover
			) throws Throwable {
		// 1. Test Setup
		var response = new BookResponse();

		response.setId(id);
		response.setAuthor(author);
		response.setCover(cover);
		response.setIsbn(isbn);
		response.setPages(pages);
		response.setTitle(title);
		response.setYear(year);
		response.setPrice(price);
		
		Mockito.when(standardBookCatalogService.deleteBook(isbn)).thenReturn(response);
		// 2. Call exercise method
        mockMvc.perform(
        	delete("/books/"+isbn)
        			.accept(MediaType.APPLICATION_JSON)
        )
        // 3. Verification
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.author", is(author)))
		.andExpect(jsonPath("$.isbn", is(isbn)))
		.andExpect(jsonPath("$.title", is(title)))
		.andExpect(jsonPath("$.pages", is(pages)))
		.andExpect(jsonPath("$.year", is(year)))
		.andExpect(jsonPath("$.price", is(price)));
		// 4. Tear-down
	}

}
