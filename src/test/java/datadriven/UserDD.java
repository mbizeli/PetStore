// 1 - pacote
package datadriven;

// 2 - bibliotecas
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

// 3 - classe
public class UserDD {
    // 3.1 - atributos
    String uri = "https://petstore.swagger.io/v2/user";
    Data data; // objeto que representa a classe utils.Data - neste ponto j� fizemos a Declara��o e a importa��o da classe
    int contador; // contar o numero de testes realizados
    double soma; // somat�ria dos valores dos registros (brincandeira somando o valor das senhas)

    // 3.2 - m�todos e fun��es

    @DataProvider // provedor de dados para os testes
    public Iterator<Object[]> provider() throws IOException {
        List<Object[]> testCases = new ArrayList<>(); //guarda toda a lista do CSV
        //List<String[]> testCases = new ArrayList<>();
        String[] testCase; // retorna 1 linha do CSV
        String linha;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("db/usersPairwise.csv"));
        while ((linha = bufferedReader.readLine()) != null){
            testCase = linha.split(",");
            testCases.add(testCase);
        }
        return testCases.iterator();
    }


    @BeforeMethod // antes do m�todo de teste - ver imagem da hierarquia de teste para o TestNG
    public void setup(){
        data = new Data(); // neste ponto n�s fizemos a inicializa��o (ou inst�ncia) da classe
        }

    @AfterClass // depois que a classe terminar de excecutar todos os seus testes
    public void tearDown(){
        System.out.println("TOTAL DE REGISTROS = " + contador);
        System.out.println("SOMA TOTAL = " + soma);
    }

    @Test (dataProvider = "provider")
    public void incluirUsuario(
            String id,
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String userStatus ) {

        String jsonBody = new JSONObject()
            .put("id", id)
            .put("username", username)
            .put("firstName", firstName)
            .put("lastName", lastName)
            .put("email", email)
            .put("password", password)
            .put("phone", phone)
            .put("userStatus", userStatus)
            .toString();


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
        contador += 1;
        System.out.println("O userID � " + userID);
        System.out.println("Essa � a linha n� " + contador);

        soma = soma + Double.parseDouble(password);
    }
}
