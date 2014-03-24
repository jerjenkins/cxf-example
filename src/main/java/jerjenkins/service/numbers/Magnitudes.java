package jerjenkins.service.numbers;

import com.google.common.base.Optional;

enum Magnitudes {
    NONE(0, ""),
    HUNDRED(1, "hundred"),
    THOUSAND(2, "thousand"),
    MILLION(3, "million"),
    BILLION(4, "billion"),
    TRILLION(5, "trillion");

    Magnitudes(int magnitude, String string) {
        this.magnitude = magnitude;
        this.string = string;
    }

    private final int magnitude;
    private final String string;

    public String string() {
        return string;
    }

    public static Optional<Magnitudes> forMagnitude(int mag) {
        Optional<Magnitudes> result = Optional.absent();

        for (Magnitudes magnitude : values()) {
            if(mag == magnitude.magnitude) {
                result = Optional.of(magnitude);
                break;
            }
        }

        return result;
    }
}
