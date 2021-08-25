// 1 - pacote contendo as classes que ser�o criados
package petstore;

// 2 - Bibliotecas
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 - Classe
public class Pet {

    // 3.1 - atributos (como os adjetivos, caracter�sticas do objeto tais como, peso, cor, cpf, rg)
    String uri = "https://petstore.swagger.io/v2/pet"; //endere�o da entidade pet

    // 3.2 - m�todos (a��es que n�o retornam nenhum valor) e fun��es (a��es que devolvem um resultado)
    public String lerJson(String caminhoJson)  throws IOException {
       return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test(priority = 1) // identifica o m�todo ou fun��o como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //sint�xe Gherkin
        //Dado - Quando - Ent�o
        //Given - When - Then

        given()  // Dado
                .contentType("application/json") // comum em API REST - as antigas eram "text/xml"
                .log().all()
                .body(jsonBody) //corpo da requisi��o
        .when() // Quando
                .post(uri)
        .then() // Ent�o
                .log().all()
                .statusCode(200)
                .body("name", is("Thor")) //corpo da resposta
                .body("status",is("available"))
                .body("category.name", is ("ASD4535HGJ"))
                .body("tags.name", contains("data"))

        ;
    }

    //consulta um pet - metodo GET
    @Test(priority = 2)
    public void consultarPet(){
        String petID = "1971110322";
        String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petID)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Thor"))
                .body("category.name", is("ASD4535HGJ"))
                .body("status", is("available"))
        .extract()
                .path("category.name")

        ;
        System.out.println("O token � " + token);
    }
}
