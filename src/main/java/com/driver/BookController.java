package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    HashMap<Integer,Book> hm;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
        hm = new HashMap<>();
    }

    // post request /create-book
    // pass book as request body
//    http://localhost:8080/create-book
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody(required = true) Book book){
        // Your code goes here.
        int ID = getId();
        book.setId(ID);
        List<Book> list = getBookList();
        list.add(book);
        setBookList(list);
        hm.put(ID,book);
        ID++;
        setId(ID);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()

    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int ID){
        Book book = hm.get(ID);
        return new ResponseEntity<>(book,HttpStatus.FOUND);
    }

    // delete request
    // pass id as path variable
    // deleteBookById()

    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id")int ID){
        Book book = hm.get(ID);
        bookList.remove(book);
        hm.remove(ID);
        return  new ResponseEntity<>("Success",HttpStatus.OK);
    }

    // get request /get-all-books
    // getAllBooks()

    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(getBookList(),HttpStatus.FOUND);
    }

    // delete request /delete-all-books
    // deleteAllBooks()

    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        List<Book> books = new ArrayList<>();
        setBookList(books);
        hm.clear();
        return new ResponseEntity<>("Done",HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()

    @GetMapping("/get-books-by-author")
    public ResponseEntity<Book> getBooksByAuthor(@RequestParam("author") String author){
        int n = bookList.size();
        for(int i = 0 ; i < n ; i++){
            if(bookList.get(i).getAuthor().equals(author)){
                return new ResponseEntity<>(bookList.get(i),HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()


    @GetMapping("/get-books-by-genre")
    public ResponseEntity<Book> getBooksByGenre(@RequestParam("Genre") String genre){
        int n = bookList.size();
        for(int i = 0 ; i < n ; i++){
            if(bookList.get(i).getGenre().equals(genre)){
                return new ResponseEntity<>(bookList.get(i),HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }
}
