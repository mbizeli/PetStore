// 1 - pacote
package datadriven;

// 2 - bibliotecas
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

// 3 - classe
public class UserDD {
    // 3.1 - atributos
    String uri = "https://petstore.swagger.io/v2/user";
    Data data; // objeto que representa a classe utils.Data - neste ponto já fizemos a Declaração e a importação da classe

    // 3.2 - métodos e funções
    @BeforeMethod // antes do método de teste - ver imagem da hierarquia de teste para o TestNG
    public void setup(){
        data = new Data(); // neste ponto nós fizemos a inicialização (ou instância) da classe

    }

    @Test
    public void incluirUsuario() throws IOException{
        String jsonBody = data.lerJson("db/user1.json"); // neste ponto já estou usando a classe. Lembrar de DIIU (Declara, Importa, Instancia, Usa)

        String userID =
                given()
                        .contentType("application/json")
                        .log().all()
                        .body(jsonBody)
                .when()
                        .post(uri)
                .then()
                        .log().all()
                        .statusCode(200)
                        .body("code", is (200))
                        .body("type", is("unknown"))
                .extract()
                        .path("message")
                ;
        System.out.println("O userID é " + userID);
    }
}
