package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class Data {

    //funçao para ler um arquivo JSON
    public String lerJson(String caminhoJson)  throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //função para ler arquivo CSV
    public Collection<String[]> lerCSV(String caminhoCSV){


        return null;

    }

}
