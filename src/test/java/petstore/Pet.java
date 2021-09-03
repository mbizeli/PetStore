// 1 - pacote contendo as classes que ser�o criados
package petstore;

// 2 - Bibliotecas
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

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
    // m�todo para alterar um pet - update
    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Thor"))
                .body("status", is("sold"))

        ;

    }
    @Test(priority = 4)
    public void excluirPet(){
        String petID = "1971110322";

        given()
                .contentType("application/json")
                .log().all()

        .when()
                .delete(uri + "/" + petID)

        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petID))

        ;

    }
    @Test // quando n�o indica a prioridade ele assume como sendo prioridade 0
    public void consultarPetPorStatus(){
        String status = "available";

        given() // Dado que - � minha pr�-condi��o
                .contentType("application/json")
                .log().all()
        .when() // Quando - a��o a ser feita
                .get(uri + "/findByStatus?status=" + status)
        .then() // Ent�o - resultado esperado
                .log().all()
                .statusCode(200)
                .body("name[]", everyItem(equalTo("Thor"))) //estrutura usada para buscar um elemento da lista

        ;

    }
}
