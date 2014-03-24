package jerjenkins.functional;

import com.google.common.base.Optional;

import java.util.LinkedList;
import java.util.List;

public class Transforms {
    public static <T> Optional<List<T>>allPresent(List<Optional<T>> list) {
        List<T> resultList = new LinkedList<T>();
        for (Optional<T> optional : list) {
            if(optional.isPresent()) {
                resultList.add(optional.get());
            } else {
                return Optional.absent();
            }
        }

        return Optional.of(resultList);
    }
}
