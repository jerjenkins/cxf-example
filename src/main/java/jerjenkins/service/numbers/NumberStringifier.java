package jerjenkins.service.numbers;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import jerjenkins.functional.Transforms;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NumberStringifier {

    public static Optional<String> forNumber(BigInteger number) {
        return forNumber(number.toString());
    }

    public static Optional<String> forNumber(String strInt) {
        final String[] buckets = bucketIntoHundredGroupings(strInt);

        final List<Optional<String>> list = new ArrayList<>();

        for (int i = 0, j = buckets.length; i < buckets.length; i++, j--) {
            List<Optional<String>> strRepresentation = hundredsRepresentation(buckets[i]);
            list.addAll(strRepresentation);

            if(j > 1 && !strRepresentation.isEmpty()) {
                list.add(Magnitudes.forMagnitude(j).transform(new Function<Magnitudes, String>() {
                    @Override
                    public String apply(Magnitudes input) {
                        return input.string();
                    }
                }));
            }
        }

        return Transforms.allPresent(list)
                .transform(new Function<List<String>, String>() {
            @Override
            public String apply(List<String> input) {
                StringBuilder sb = new StringBuilder();
                for (String s : input) {
                    if(!s.isEmpty()) sb.append(s).append(" ");
                }
                sb.setLength(sb.length() - 1);

                return sb.toString();
            }
        });
    }

    private static List<Optional<String>> hundredsRepresentation(String strInt) {
        final List<Optional<String>> result;
        switch(strInt.length()) {
            case 0:
                result = new ArrayList<>(0);
                break;
            case 1:
                result = for1digit(strInt);
                break;
            case 2:
                result = for2digit(strInt);
                break;
            case 3:
                result = for3digit(strInt);
                break;
            default: {
                result = new ArrayList<>(1);
                result.add(Optional.<String>absent());
            }
        }

        return result;
    }

    private static List<Optional<String>> for1digit(String number) {
        final List<Optional<String>> result = new ArrayList<>(1);
        result.add(BaseNumbers.forNumber(number).transform(new Function<BaseNumbers, String>() {
            @Override
            public String apply(BaseNumbers input) {
                return input.string();
            }
        }));

        return result;
    }

    private static List<Optional<String>> for2digit(String number) {
        Optional<BaseNumbers> tens = BaseNumbers.forNumber(number);

        final List<Optional<String>> result;
        if(tens.isPresent()) {
            result = new ArrayList<>();
            result.add(tens.transform(new Function<BaseNumbers, String>() {
                @Override
                public String apply(BaseNumbers input) {
                    return input.string();
                }
            }));

            if(result.contains(Optional.of(BaseNumbers.ZERO.string()))) result.clear();
        } else {
            result = derive2digitNumber(number);
        }

        return result;
    }

    private static List<Optional<String>> derive2digitNumber(String number) {
        List<Optional<BaseNumbers>> list = new ArrayList<>();

        list.add(BaseNumbers.forNumber(Integer.valueOf(number.substring(0, 1)) * 10));
        list.add(BaseNumbers.forNumber(Integer.valueOf(number.substring(1, 2))));

        return Lists.transform(list, new Function<Optional<BaseNumbers>, Optional<String>>() {
            @Override
            public Optional<String> apply(Optional<BaseNumbers> input) {
                return input.transform(new Function<BaseNumbers, String>() {
                    @Override
                    public String apply(BaseNumbers input) {
                        return input.string();
                    }
                });
            }
        });
    }

    private static List<Optional<String>> for3digit(String number) {
        Optional<BaseNumbers> hundreds = BaseNumbers.forNumber(number.substring(0, 1));

        final List<Optional<String>> result = new ArrayList<>(3);
        result.add(hundreds.transform(new Function<BaseNumbers, String>() {
            @Override
            public String apply(BaseNumbers input) {
                return input.string();
            }
        }));
        if(result.contains(Optional.of(BaseNumbers.ZERO.string()))) result.clear();

        if(!result.isEmpty()) result.add(Optional.of(Magnitudes.HUNDRED.string()));

        result.addAll(for2digit(number.substring(1, 3)));

        return result;
    }

    private static String[] bucketIntoHundredGroupings(String strInt) {
        int highestOrderDigits = strInt.length() % 3;

        final String[] buckets;
        if(highestOrderDigits == 0) {
            buckets = new String[strInt.length() / 3];
            buckets[0] = strInt.substring(0, 3);
        } else {
            buckets = new String[strInt.length() / 3 + 1];
            buckets[0] = strInt.substring(0, highestOrderDigits);
        }

        for(int i = 1; i < buckets.length; i++) {
            int begin = highestOrderDigits + ((i-1)*3);
            buckets[i] = strInt.substring(begin, begin+3);
        }

        return buckets;
    }
}
