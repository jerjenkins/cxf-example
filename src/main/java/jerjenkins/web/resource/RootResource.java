package jerjenkins.web.resource;

import javax.ws.rs.Path;

@Path("/")
public class RootResource {
    private final FooResource fooResource;
    private final BarResource barResource;

    public RootResource(FooResource fooResource, BarResource barResource) {
        this.fooResource = fooResource;
        this.barResource = barResource;
    }

    @Path("/foo")
    public FooResource fooResource() {
        return fooResource;
    }

    @Path("/bar")
    public BarResource barResource() {
        return barResource;
    }
}
