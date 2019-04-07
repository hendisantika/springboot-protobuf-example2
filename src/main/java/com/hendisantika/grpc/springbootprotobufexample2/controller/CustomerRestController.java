package com.hendisantika.grpc.springbootprotobufexample2.controller;

import com.hendisantika.grpc.springbootprotobufexample2.proto.CustomerProtos;
import com.hendisantika.grpc.springbootprotobufexample2.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-protobuf-example2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-07
 * Time: 14:17
 */
@RestController
@RequiredArgsConstructor
class CustomerRestController {

    private final CustomerRepository customerRepository;

    /**
     * {@code curl -v http://localhost:8080/customers/1}
     */
    @GetMapping("/customers/{id}")
    CustomerProtos.Customer customer(@PathVariable Integer id) {
        return this.customerRepository.findById(id);
    }

    /**
     * {@code curl -v http://localhost:8080/customers}
     */
    @GetMapping("/customers")
    CustomerProtos.CustomerList findAll() {
        return this.customerRepository.findAll();
    }
}