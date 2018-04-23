package controller;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.Symbol;
import service.Lexer;

@RestController  
@SpringBootApplication  
@RequestMapping("/lexer")
public class LexerController { 
	
    @PostMapping("/readFile")
    public List<Symbol> read(String code) {  
    	Lexer lexer = new Lexer();
    	lexer.scanner();
    	return lexer.getSymbols();
    }  
      
    public static void main(String[] args) {  
        SpringApplication.run(LexerController.class, args);    
    }  
}  