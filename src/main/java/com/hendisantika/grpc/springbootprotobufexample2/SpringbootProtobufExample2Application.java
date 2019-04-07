package com.hendisantika.grpc.springbootprotobufexample2;

import com.hendisantika.grpc.springbootprotobufexample2.proto.CustomerProtos;
import com.hendisantika.grpc.springbootprotobufexample2.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@SpringBootApplication
public class SpringbootProtobufExample2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProtobufExample2Application.class, args);
    }

    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    private CustomerProtos.Customer customer(int id, String firstname, String lastname, Collection<String> emails) {

        Collection<CustomerProtos.Customer.EmailAddress> emailAddresses =
                emails.stream().map(e -> CustomerProtos.Customer.EmailAddress.newBuilder()
                        .setType(CustomerProtos.Customer.EmailType.PROFESSIONAL)
                        .setEmail(e).build())
                        .collect(Collectors.toList());

        return CustomerProtos.Customer.newBuilder()
                .setFirstName(firstname)
                .setLastName(lastname)
                .setId(id)
                .addAllEmail(emailAddresses)
                .build();
    }

    @Bean
    CustomerRepository customerRepository() {

        Map<Integer, CustomerProtos.Customer> customers = new ConcurrentHashMap<>();
        // populate with some dummy data
        asList(
                customer(1, "Uzumaki", "Naruto", asList("uzumaki_naruto@konohagakure.com")),
                customer(2, "Uchiha", "Sasuke", asList("uchiha_sasuke@konohagakure.com")),
                customer(3, "Haruno", "Sakura", asList("haruno_sakura@konohagakure.com")),
                customer(4, "Hatake", "Kakashi", asList("hatake_kakashi@konohagakure.com"))
        ).forEach(c -> customers.put(c.getId(), c));

        return new CustomerRepository() {

            @Override
            public CustomerProtos.Customer findById(int id) {
                return customers.get(id);
            }

            @Override
            public CustomerProtos.CustomerList findAll() {
                return CustomerProtos.CustomerList.newBuilder() //
                        .addAllCustomer(customers.values()) //
                        .build();
            }
        };
    }

}
