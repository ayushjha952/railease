package com.spring.railEase.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.railEase.controller.ReservationController;
import com.spring.railEase.model.CancellationOutputModel;
import com.spring.railEase.model.ReservationInputModel;
import com.spring.railEase.model.ReservationOutputModel;
import com.spring.railEase.service.ReservationService;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;


    @Test
    public void testMakeReservation() throws Exception {
        
        ReservationInputModel reservationInputModel = new ReservationInputModel();

        ReservationOutputModel reservationOutputModel = new ReservationOutputModel();

        when(reservationService.makeReservation(any(ReservationInputModel.class)))
                .thenReturn(reservationOutputModel);

        ResultActions resultActions = mockMvc.perform(post("/addreservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(reservationInputModel)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCancelReservation() throws Exception {

        Integer reservationId = 1;
        CancellationOutputModel cancellationOutputModel = new CancellationOutputModel();

        when(reservationService.cancelReservation((reservationId))).thenReturn(cancellationOutputModel);

        ResultActions resultActions = mockMvc.perform(put("/cancelreservation/{reservationId}", reservationId));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void testGetAllReservationsByCustomerId() throws Exception {

        Integer customerId = 1;
        List<CancellationOutputModel> cancellationOutputModelList = new ArrayList<>();

        when(reservationService.getAllReservationsByCustomerId(customerId)).thenReturn(cancellationOutputModelList);

        ResultActions resultActions = mockMvc.perform(get("/getallreservationsbycustomerid/{customerId}", customerId));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    public void testGetTotalNoOfReservations() throws Exception {

        String trainNo = "ABC123";
        String travelDate = "2023-07-01";
        String seatType = "AC";

        Integer totalNoOfReservations = 5;

        when(reservationService.getTotalNoOfReservationsByDateAndSeatType("trainNo", "travelDate", "seatType"))
                .thenReturn(totalNoOfReservations);

        ResultActions resultActions = mockMvc.perform(get("/totalnoofreservations/{trainNo}/{travelDate}/{seatType}",
                trainNo, travelDate, seatType));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }



}
