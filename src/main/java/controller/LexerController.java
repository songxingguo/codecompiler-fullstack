package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import service.Lexer;

@RestController  
@SpringBootApplication  
@RequestMapping("/lexer")
public class LexerController { 
	
    @GetMapping("/readFile")
    public String read() {  
//        return lexerService.readFile();  
    	Lexer lexer = new Lexer();
    	lexer.scanner();
    	lexer.writeSym();
    	lexer.writeToken();
    	System.out.println(lexer.tokens.toString());
    	return lexer.tokens.toString();
    }  
      
    public static void main(String[] args) {  
        SpringApplication.run(LexerController.class, args);    
    }  
}  