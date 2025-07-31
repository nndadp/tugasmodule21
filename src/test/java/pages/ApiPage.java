package pages;

import helper.Endpoint;
import helper.Models;
import helper.Utility;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.File;
import java.util.List;

import static helper.Models.deleteUser;
import static org.assertj.core.api.Assertions.assertThat;
import static helper.Models.getListUsers;
import static org.assertj.core.api.InstanceOfAssertFactories.FILE;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiPage {

    String setURL, global_id;

    Response res;

    public void prepareUrlValidFor(String url) {
        switch (url) {
            case "GET_LIST_USERS":
                setURL = Endpoint.GET_LIST_USERS;
                break;
            case "CREATE_NEW_USERS":
                setURL = Endpoint.CREATE_NEW_USERS;
                break;
            case "DELETE_USERS":
                setURL = Endpoint.DELETE_USERS;
                break;
            default:
                System.out.println("input right url");
        }
        System.out.println("endpoint siap pakai adalah : " + setURL);
    }

    public void hitApiGetListUsers() {
        res = getListUsers(setURL);
        System.out.println(res.getBody().asString());
    }

    public void hitApiPostCreateUser() {
        res = Models.postCreateUser(setURL);
        System.out.println(res.getBody().asString());
    }

    public void validationStatusCodeIsEquals(int status_code) {
        assertThat(res.statusCode()).isEqualTo(status_code);
    }

    public void validationStatusCode(int status_code) {
        assertThat(res.statusCode()).isEqualTo(status_code);
    }

    public void validationResponseBodyGetListUsers() {
        List<Object> id = res.jsonPath().getList("id");
        List<Object> name = res.jsonPath().getList("name");
        List<Object> email = res.jsonPath().getList("email");
        List<Object> gender = res.jsonPath().getList("gender");
        List<Object> status = res.jsonPath().getList("status");

        assertThat(id.get(0)).isNotNull();
        assertThat(name.get(0)).isNotNull();
        assertThat(email.get(0)).isNotNull();
        assertThat(gender.get(0)).isIn("female", "male");
        assertThat(status.get(0)).isIn("active", "inactive");

        global_id = id.get(4).toString();
    }

    public void validationResponseJsonWithJSONSchema(String filename) {
        File JSONFile = Utility.getJSONSchemaFile(filename);
        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JSONFile));
    }

    public void validationResponseBodyCreateNewUser() {
        assertThat(res.statusCode()).isEqualTo(201);
        assertThat(res.jsonPath().getInt("id")).isGreaterThan(0);  // Pastikan ID valid
        assertThat(res.jsonPath().getString("email")).contains("@");
        assertThat(res.header("Location")).contains("/users/");  // Contoh: validasi URL
    }

    public void hitApiDeleteUser() {
        String deleteUrl = String.format(setURL, global_id);
        res = deleteUser(deleteUrl);
        System.out.println("Delete status: " + res.getStatusCode());
    }

    public void validateDeleteResponse() {
        assertThat(res.statusCode()).isEqualTo(204); // atau 200 tergantung API
        if (res.getStatusCode() == 200) {
            assertThat(res.getBody().asString()).contains("deleted");
        }
    }
}
