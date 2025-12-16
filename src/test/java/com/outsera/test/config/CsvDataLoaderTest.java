package com.outsera.test.config;

import com.outsera.test.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CsvDataLoaderTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private CsvDataLoader csvDataLoader;

    @Test
    @DisplayName("Retorna lista vazia quando entrada é nula")
    void testParseEmptyOrNull(){
        assertTrue(csvDataLoader.parseMultipleValues(null).isEmpty());
        assertTrue(csvDataLoader.parseMultipleValues("").isEmpty());
        assertTrue(csvDataLoader.parseMultipleValues("   ").isEmpty());
    }

    @Test
    @DisplayName("Deve retornar unico item quando não tiver separadores")
    void testParseSingleValue(){
        List<String> result = csvDataLoader.parseMultipleValues("Warner Bros");
        assertEquals(1, result.size());
        assertEquals("Warner Bros", result.get(0));
    }

    @Test
    @DisplayName("Deve separar multiplos itens por virgula")
    void testParseCommaSeparated(){
        List<String> result = csvDataLoader.parseMultipleValues("MGM, United Artists");
        assertEquals(2, result.size());
        assertEquals("MGM", result.get(0));
        assertEquals("United Artists", result.get(1));
    }

    @Test
    @DisplayName("Deve seprar multiplos itens por 'and'")
    void testParseAndSeparated(){
        List<String> result = csvDataLoader.parseMultipleValues("Yoram Globus and Menahem Golan");
        assertEquals(2, result.size());
        assertEquals("Yoram Globus", result.get(0));
        assertEquals("Menahem Golan", result.get(1));
    }

    @Test
    @DisplayName("Deve separar multiplos itens por virgula e 'and'")
    void testParseMixedSeparators(){
        List<String> result = csvDataLoader.parseMultipleValues("Bob Cavallo, Joe Ruffalo and Steve Fargnoli");
        assertEquals(3, result.size());
        assertEquals("Bob Cavallo", result.get(0));
        assertEquals("Joe Ruffalo", result.get(1));
        assertEquals("Steve Fargnoli", result.get(2));
    }

    @Test
    @DisplayName("Deve ignorar espaços extras nos nomes")
    void testParseExtraSpaces(){
        List<String> result = csvDataLoader.parseMultipleValues("Producer A ,  Producer B");
        assertEquals(2, result.size());
        assertEquals("Producer A", result.get(0));
        assertEquals("Producer B", result.get(1));
    }

    @Test
    @DisplayName("Deve extrair Matthew Vaughn corretamente da lista complexa de 2015")
    void testComplexStringWithMatthewVaughn() {
        String input = "Simon Kinberg, Matthew Vaughn, Hutch Parker, Robert Kulzer and Gregory Goodman";
        List<String> result = csvDataLoader.parseMultipleValues(input);
        System.out.println("Nomes extraídos: " + result);
        assertTrue(result.contains("Matthew Vaughn"), "Deveria conter Matthew Vaughn");
        assertTrue(result.contains("Simon Kinberg"), "Deveria conter Simon Kinberg");
        assertTrue(result.contains("Gregory Goodman"), "Deveria conter Gregory Goodman");
        assertEquals(5, result.size(), "Deveriam ser 5 produtores");
    }
}