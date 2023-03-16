package com.epsi.pierrefeuilleciseaux.Controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epsi.pierrefeuilleciseaux.model.Score;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void resetScore() throws Exception{
        mockMvc.perform(put("/game/score/1/4/5"))
                .andReturn();

        mockMvc.perform(post("/game/restart"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var result = mockMvc.perform(get("/game/score"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("win:0,lose:0,tie:0", content);
    }

    @Test
    public void jouerPierreEtRobotCiseaux() throws Exception{
        MvcResult result = mockMvc.perform(post("/game/play/PIERRE"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        var expectedMessage = "Vous avez joué PIERRE l'ordinateur à joué CISEAUX, vous avez gagné";
        assertEquals(expectedMessage, content);
    }


    @Test
    public void updateScore() throws Exception{
        mockMvc.perform(put("/game/score/1/4/5"))
                .andReturn();

        var result = mockMvc.perform(get("/game/score"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("win:1,lose:4,tie:5", content);
    }
}
