package com.projects.praticandoAPI.controller;

import com.projects.praticandoAPI.modelo.Curso;
import com.projects.praticandoAPI.modelo.StatusTopico;
import com.projects.praticandoAPI.modelo.Topico;
import com.projects.praticandoAPI.modelo.Usuario;
import com.projects.praticandoAPI.repository.CursoRepository;
import com.projects.praticandoAPI.repository.TopicoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TopicosJPAController.class)
public class TopicosJPAControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicoRepository topicoRepository;

    @MockBean
    private CursoRepository cursoRepository;

    @Test
    public void deveRetornarListaComStatusOk() throws Exception {
        List<Topico> topicos = Collections.singletonList(getTopicoMock("Titulo 1", "Mensagem de teste 1"));
        Mockito.when(topicoRepository.findAll()).thenReturn(topicos);

        mockMvc.perform(MockMvcRequestBuilders.get("/topic/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void deveInserirTopico() throws Exception {
        Mockito.when(cursoRepository.findByNome(Mockito.anyString())).thenReturn(getCursoMock());

        mockMvc.perform(MockMvcRequestBuilders.post("/topic")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("{" +
                        "    \"titulo\": \"Título 42\"," +
                        "    \"mensagem\": \"A vida, o Universo e tudo mais\"," +
                        "    \"nomeCurso\": \"Sci-fy Books\"" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private Topico getTopicoMock(String titulo, String mensagem) {
        Topico t1 = new Topico();
        t1.setId(383L);
        t1.setTitulo(titulo);
        t1.setMensagem(mensagem);
        t1.setStatus(StatusTopico.NAO_RESPONDIDO);
        t1.setAutor(getUsuarioMock());
        t1.setCurso(getCursoMock());

        return t1;
    }

    private Usuario getUsuarioMock() {
        Usuario user1 = new Usuario();
        user1.setEmail("user@provedor.com");
        user1.setId(12342L);
        user1.setNome("Userson Teste da Silva");
        user1.setSenha("*******");

        return user1;
    }

    private Curso getCursoMock() {
        Curso curso = new Curso();
        curso.setCategoria("Cat 42");
        curso.setNome("Sci-fy Books");
        curso.setId(27463129L);

        return curso;
    }
}