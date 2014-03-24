package jerjenkins.service;

import com.google.common.base.Optional;
import jerjenkins.service.numbers.NumberStringifier;

import java.math.BigInteger;

public class SimpleService {

    public Optional<String> stringify(Integer num) {
        return stringify(BigInteger.valueOf(num));
    }

    public Optional<String> stringify(Long num) {
        return stringify(BigInteger.valueOf(num));
    }

    public Optional<String> stringify(BigInteger num) {
        return NumberStringifier
                .forNumber(num);
    }
}
