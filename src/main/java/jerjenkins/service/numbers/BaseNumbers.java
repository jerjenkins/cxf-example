package jerjenkins.service.numbers;

import com.google.common.base.Optional;

enum BaseNumbers {
    ZERO(0, ""),
    ONE(1, "one"),
    TWO(2, "two"),
    THREE(3, "three"),
    FOUR(4, "four"),
    FIVE(5, "five"),
    SIX(6, "six"),
    SEVEN(7, "seven"),
    EIGHT(8, "eight"),
    NINE(9, "nine"),
    TEN(10, "ten"),
    ELEVEN(11, "eleven"),
    TWELVE(12, "twelve"),
    THIRTEEN(13, "thirteen"),
    FOURTEEN(14, "fourteen"),
    FIFTEEN(15, "fifteen"),
    SIXTEEN(16, "sixteen"),
    SEVENTEEN(17, "seventeen"),
    EIGHTEEN(18, "eighteen"),
    NINETEEN(19, "nineteen"),
    TWENTY(20, "twenty"),
    THIRTY(30, "thirty"),
    FORTY(40, "forty"),
    FIFTY(50, "fifty"),
    SIXTY(60, "sixty"),
    SEVENTY(70, "seventy"),
    EIGHTY(80, "eighty"),
    NINETY(90, "ninety");

    BaseNumbers(int number, String string) {
        this.number = number;
        this.string = string;
    }

    private final int number;
    private final String string;

    public String string() {
        return string;
    }

    public Integer number() {
        return number;
    }

    public static Optional<BaseNumbers> forNumber(int number) {
        Optional<BaseNumbers> result = Optional.absent();

        for (BaseNumbers baseNumber : BaseNumbers.values()) {
            if (baseNumber.number == number) {
                result = Optional.of(baseNumber);
                break;
            }
        }

        return result;
    }

    public static Optional<BaseNumbers> forNumber(String number) {
        return forNumber(Integer.valueOf(number));
    }
}
