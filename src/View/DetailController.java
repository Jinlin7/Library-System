package View;

import File.Adapter;
import Model.Article;
import Model.ArticleList;
import Model.Book;
import Model.BookList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DetailController {

    private ArrayList<String> stringsX;
    private Adapter adapter;
    private Adapter adapter1,adapter2;

    @FXML
    private TextField title;

    @FXML
    private TextField writer;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField identify;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private Button submit;

    @FXML
    private Button clear;

    private Book book;
    public Article article;

    private BookList bookList;

    private ArticleList articleList;

    public void init(){
        adapter = new Adapter("library.bin");
        adapter1 = new Adapter("Logs.bin");
        adapter2 = new Adapter("Article.bin");
        bookList = adapter.getBookList();
        articleList = adapter2.getArticleList();
        stringsX = adapter1.getString();


        Platform.runLater(()->{
            title.setText(book.getTitle());
            writer.setText(book.getWriter());
            name.setText(book.getName());
            email.setText(book.getEmail());
            identify.setText(book.getIdentity());
            startTime.setText(book.getBorrowTime());
            endTime.setText(book.getReturnTime());
        });

    }


    public void setBook(Book book){
        this.book=book;
    }

    public void setArticle(Article article){this.article = article;}

    public void clearAll()
    {
        name.setText("");
        email.setText("");
        identify.setText("");
        startTime.setText("");
        endTime.setText("");
    }

    public void setBorrowerInformation() throws IOException
    {
        for (int i = 0; i < bookList.getBooks().size(); i++) {
            if (bookList.getBooks().get(i).getTitle().equals(book.getTitle()) &&
                    bookList.getBooks().get(i).getWriter().equals(book.getWriter()))
            {
                bookList.removeBook(bookList.getBooks().get(i));
                Book newBook = new Book(book.getTitle(),book.getWriter(),book.getReleaseTime(),book.getStatus(),
                        name.getText(),email.getText(),identify.getText(),
                        startTime.getText(),endTime.getText(),book.getISBN());

                bookList.addBook(newBook);
                adapter.addBookList(bookList);
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                stringsX.add(" "+dateTime.format(formatter)+":     "+"Manager changed borrower information: "+book.getTitle());
                adapter1.addStrings(stringsX);
                System.out.println("Successfully changed");
                JOptionPane.showMessageDialog(null,"Successfully changed");
                break;
            }
        }

        for (int i = 0; i < articleList.getArticles().size(); i++) {
            if (articleList.getArticles().get(i).getTitle().equals(article.getTitle()) &&
                    articleList.getArticles().get(i).getWriter().equals(article.getWriter()))
            {
                articleList.remove(articleList.getArticles().get(i));
                Article newArticle = new Article(article.getTitle(),article.getWriter(),article.getReleaseTime(),article.getStatus(),
                        name.getText(),email.getText(),identify.getText(),
                        startTime.getText(),endTime.getText());

                articleList.add(newArticle);
                adapter2.addArticleList(articleList);
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                stringsX.add(" "+dateTime.format(formatter)+":     "+"Manager changed borrower information: "+article.getTitle());
                adapter1.addStrings(stringsX);
                System.out.println("Successfully changed");
                JOptionPane.showMessageDialog(null,"Successfully changed");
                break;
            }
        }
    }
}