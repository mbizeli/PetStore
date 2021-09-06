package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class Data {

    //funçao para ler um arquivo JSON
    public String lerJson(String caminhoJson)  throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //função para ler arquivo CSV
    //public Collection<String[]> lerCSV(String caminhoCSV) throws IOException {
    public List<String[]> lerCSV(String caminhoCSV) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(caminhoCSV)); // ler um texto
        CSVReader csvReader =  new CSVReaderBuilder(reader).withSkipLines(1).build(); // abre como um CSV, pega o conteúdo, ignora a 1ª linha e contrói o arquivo
        List<String[]> users = csvReader.readAll(); // lê todos os dados do CSV
        return users;

    }

}
