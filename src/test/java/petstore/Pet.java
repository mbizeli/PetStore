// 1 - pacote contendo as classes que serão criados
package petstore;

// 2 - Bibliotecas
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;

// 3 - Classe
public class Pet {

    // 3.1 - atributos (como os adjetivos, características do objeto tais como, peso, cor, cpf, rg)
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade pet

    // 3.2 - métodos (ações que não retornam nenhum valor) e funções (ações que devolvem um resultado)

    public String lerJson(String caminhoJson)  throws IOException {
       return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test // identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //sintáxe Gherkin
        //Dado - Quando - Então
        //Given - When - Then

        given()  // Dado
                .contentType("application/json") // comum em API REST - as antigas eram "text/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() // Então
                .log().all()
                .statusCode(200)
        ;
    }
}
