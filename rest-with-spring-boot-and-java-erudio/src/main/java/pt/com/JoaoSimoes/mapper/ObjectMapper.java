package pt.com.JoaoSimoes.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin,  Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {

        List<D> destinationList = new ArrayList<>();
        for (Object o : origin){
            destinationList.add(mapper.map(o, destination));
        }

        return destinationList;
    }
}
