package kringlooptilburg.nl.productimageservice.mappers;

import java.io.IOException;

public interface Mapper<A,B> {
    B mapTo(A a);
    A mapFrom(B b) throws IOException;
}