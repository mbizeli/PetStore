// 1 - pacote contendo as classes que serão criados
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

    // 3.1 - atributos (como os adjetivos, características do objeto tais como, peso, cor, cpf, rg)
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade pet

    // 3.2 - métodos (ações que não retornam nenhum valor) e funções (ações que devolvem um resultado)
    public String lerJson(String caminhoJson)  throws IOException {
       return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test(priority = 1) // identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //sintáxe Gherkin
        //Dado - Quando - Então
        //Given - When - Then

        given()  // Dado
                .contentType("application/json") // comum em API REST - as antigas eram "text/xml"
                .log().all()
                .body(jsonBody) //corpo da requisição
        .when() // Quando
                .post(uri)
        .then() // Então
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
        System.out.println("O token é " + token);
    }
    // método para alterar um pet - update
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
    @Test // quando não indica a prioridade ele assume como sendo prioridade 0
    public void consultarPetPorStatus(){
        String status = "available";

        given() // Dado que - é minha pré-condição
                .contentType("application/json")
                .log().all()
        .when() // Quando - ação a ser feita
                .get(uri + "/findByStatus?status=" + status)
        .then() // Então - resultado esperado
                .log().all()
                .statusCode(200)
                .body("name[]", everyItem(equalTo("Thor"))) //estrutura usada para buscar um elemento da lista

        ;

    }
}
