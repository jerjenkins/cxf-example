package jerjenkins.web.resource;

import jerjenkins.service.SimpleService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Produces("text/plain")
public class BarResource {
    private final SimpleService simpleService;
    private final Integer repetitions;

    public BarResource(SimpleService simpleService, Integer repetitions) {
        this.simpleService = simpleService;
        this.repetitions = repetitions;
    }

    @GET
    @Path("{id}")
    public String getBar(@PathParam("id") String id) {
        StringBuilder sb = new StringBuilder(id.length() * repetitions);

        for(int i = 0; i < repetitions; i++) {
            sb.append(id);
        }

        return sb.toString();
    }
}
