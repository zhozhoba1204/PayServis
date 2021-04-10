package ru.blinov.payservis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.blinov.payservis.dto.LoginRequestDto;
import ru.blinov.payservis.service.PaymentService;

import javax.servlet.http.HttpServletRequest;


@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){
        String response = paymentService.login(loginRequestDto, request);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/payment")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseEntity<String> payment(){
        String response = paymentService.payment();
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
