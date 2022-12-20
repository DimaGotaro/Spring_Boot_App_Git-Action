package com.example.demo.integration;

import com.example.demo.controllers.Demo_controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.xml.sax.SAXParseException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Dima")
@TestPropertySource("/applicationTest.properties")
@Sql(value = {"/create-before.sql", "/mess-bef.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/mess-after.sql", "/create-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DemoTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Demo_controller demo_controller;

    @Test
    public void demoAll1() throws Exception {
        this.mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/div[1]/div[2]/h1").string("Hello Dima!"));
    }

    @Test
    public void demoAll2List() throws Exception {
        this.mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"mess-list\"]").nodeCount(4));
    }

    @Test
    public void demoAll3List() throws Exception {
        this.mockMvc.perform(get("/all").param("fil", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"mess-list\"]").nodeCount(2))
                .andExpect(xpath("//*[@id=\"1\"]").exists())
                .andExpect(xpath("//*[@id=\"3\"]").exists());
    }

    @Test
    public void demoAll4Multi() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "Hello, World!".getBytes()
        );

        MockHttpServletRequestBuilder fil = multipart("/all")
                .file(file)
                .param("text", "Goky")
                .param("tag", "vaib")
                .with(csrf());

        this.mockMvc.perform(fil)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/all"));

        this.mockMvc.perform(get("/all"))
                .andDo(print())
                .andExpect(xpath("//*[@id=\"mess-list\"]").nodeCount(5))
                .andExpect(xpath("//*[@id=\"5\"]").exists())
                .andExpect(xpath("//*[@id=\"5\"]/p[2]").string("Text: Goky"))
                .andExpect(xpath("//*[@id=\"5\"]/p[3]").string("Tag: vaib"))
                .andExpect(xpath("//*[@id=\"mess-list\"]/div[2]/img").exists());
    }
}
