package pau.pau5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddEmployee() throws Exception
    {
        String employeeJson =
                "{\n" +
                "  \"name\": \"John\",\n" +
                "  \"surname\": \"Doe\",\n" +
                "  \"employeeCondition\": \"OBECNY\",\n" +
                "  \"birth_year\": 1985,\n" +
                "  \"salary\": 15.0\n" +
                //"  \"classEmployeeId\": 31\n" +
                "}" +
                "\n";

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteEmployee() throws Exception
    {
        int id = 38;
        mockMvc.perform(delete("/api/employee/:" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddGroup() throws Exception
    {
        String groupJSON =
                "{\n" +
                "  \"workgroup\": \"TEST WORKGROUP\",\n" +
                "  \"maxEmployees\": 15\n" +
                "}" +
                "\n";

        mockMvc.perform(post("/api/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(groupJSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteGroup() throws Exception
    {
        int id = 41;
        mockMvc.perform(delete("/api/group/:" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddRateSuccess() throws Exception
    {
        String JSON =
                "{\n" +
                        "  \"rating\": 3,\n" +
                        "  \"classEmployeeId\": 31,\n" +
                        "  \"comment\": \"very goofy\"\n" +
                        "}" +
                        "\n";

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddRateFail() throws Exception
    {
        String JSON =
                "{\n" +
                 "  \"rating\": 7,\n" +
                 "  \"classEmployeeId\": 31,\n" +
                 "  \"comment\": \"very goofy\"\n" +
                 "}" +
                 "\n";

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON))
                        .andExpect(status().isBadRequest());
    }
}
