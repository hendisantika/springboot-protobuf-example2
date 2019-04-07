package com.hendisantika.grpc.springbootprotobufexample2;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-protobuf-example2
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-04-07
 * Time: 09:56
 */
public interface CustomerRepository {
    CustomerProtos.Customer findById(int id);

    CustomerProtos.CustomerList findAll();
}
