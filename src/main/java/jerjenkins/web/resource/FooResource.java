package jerjenkins.web.resource;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import jerjenkins.service.SimpleService;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

public class FooResource {
    private final SimpleService simpleService;

    public FooResource(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/stringify")
    public Response stringify(@QueryParam("num") BigInteger numInt) {
        return simpleService.stringify(numInt).transform(new Function<String, Response>() {
            @Override
            public Response apply(String input) {
                return new ResponseBuilderImpl().entity(input).status(Response.Status.OK).build();
            }
        }).or(new Supplier<Response>() {
            @Override
            public Response get() {
                return new ResponseBuilderImpl().status(Response.Status.NOT_FOUND).build();
            }
        });
    }
}
