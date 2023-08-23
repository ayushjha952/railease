package com.spring.railEase.controllertest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.spring.railEase.controller.TrainController;
import com.spring.railEase.model.TrainOutputModel;
import com.spring.railEase.service.TrainServiceImpl;

@WebMvcTest(TrainController.class)
public class TrainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainServiceImpl trainService;
    
    @Mock
    private TrainController trainController;

    @Test
    public void testGetTrainByTrainNo() throws Exception {
        TrainOutputModel trainOutputModel = new TrainOutputModel();
        trainOutputModel.setTrainNo("12008");
        trainOutputModel.setTrainName("Shatabdi Express");
        trainOutputModel.setSource("Chennai");
        trainOutputModel.setDestination("Mysuru");

        when(trainService.getTrainByTrainNo("12008")).thenReturn(trainOutputModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/searchtrainbytrainno/{trainNo}","12008")
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\r\n"
                		+ "  \"trainNo\": \"12008\",\r\n"
                		+ "  \"trainName\": \"Shatabdi Express\",\r\n"
                		+ "  \"source\": \"Chennai\",\r\n"
                		+ "  \"destination\": \"Mysuru\"\r\n"
                		+ "}"));
    }
    
    @Test
    public void testGetTrainByTrainName() throws Exception {
    	
    	TrainOutputModel trainOutputModel1 = new TrainOutputModel();
        trainOutputModel1.setTrainNo("12951");
        trainOutputModel1.setTrainName("Tejas Express");
        trainOutputModel1.setSource("Delhi");
        trainOutputModel1.setDestination("Mumbai");
        
        TrainOutputModel trainOutputModel2 = new TrainOutputModel();
        trainOutputModel2.setTrainNo("12309");
        trainOutputModel2.setTrainName("Tejas Express");
        trainOutputModel2.setSource("Patna");
        trainOutputModel2.setDestination("Delhi");
        
        List<TrainOutputModel> trainOutputModels = new ArrayList<TrainOutputModel>();
        trainOutputModels.add(trainOutputModel1);
        trainOutputModels.add(trainOutputModel2);
        

        when(trainService.getTrainByTrainName("Tejas Express")).thenReturn(trainOutputModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/searchtrainbytrainname/{trainName}","Tejas Express")
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainNo").value("12951"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainName").value("Tejas Express"))
                
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].trainNo").value("12309"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].trainName").value("Tejas Express"));
    }
    
    @Test
    public void testGetTrainBySouceAndDestination() throws Exception {
    	
    	TrainOutputModel trainOutputModel1 = new TrainOutputModel();
        trainOutputModel1.setTrainNo("12008");
        trainOutputModel1.setTrainName("Shatabdi Express");
        trainOutputModel1.setSource("Chennai");
        trainOutputModel1.setDestination("Mysuru");
        
        TrainOutputModel trainOutputModel2 = new TrainOutputModel();
        trainOutputModel2.setTrainNo("22437");
        trainOutputModel2.setTrainName("Vande Bharat");
        trainOutputModel2.setSource("Chennai");
        trainOutputModel2.setDestination("Mysuru");
        
        List<TrainOutputModel> trainOutputModels = new ArrayList<TrainOutputModel>();
        trainOutputModels.add(trainOutputModel1);
        trainOutputModels.add(trainOutputModel2);
        

        when(trainService.getTrainBySouceAndDestination("Chennai","Mysuru")).thenReturn(trainOutputModels);

        mockMvc.perform(MockMvcRequestBuilders.get("/searchtrainbysourceanddestination/{source}/{destination}","Chennai","Mysuru")
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainNo").value("12008"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainName").value("Shatabdi Express"))
                
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].trainNo").value("22437"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].trainName").value("Vande Bharat"));
    }
}


