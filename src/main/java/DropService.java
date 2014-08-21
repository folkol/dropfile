import org.apache.tika.Tika;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Path("")
public class DropService
{

    @GET
    @Path("{filename}")
    public Response hello(@PathParam("filename") String filename) throws Exception {
        java.nio.file.Path path = Paths.get("/tmp/dropservice/" + filename);
        String mimetype = new Tika().detect(path.toFile());
        return Response.ok(Files.newInputStream(path), mimetype).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response formPost(InputStream data) throws Exception {
        String filename = UUID.randomUUID().toString();
        Files.copy(data, Paths.get("/tmp/dropservice/" + filename));
        return Response.ok(filename).build();
    }

    @POST
    @Path("{filename}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response formPost(@PathParam("filename") String filename, InputStream data) throws Exception {
        java.nio.file.Path path = Paths.get("/tmp/dropservice/" + filename);
        if(Files.exists(path)) {
            path = Paths.get("/tmp/dropservice/" + UUID.randomUUID().toString() + "-" + filename);
        }
        Files.copy(data, path);
        java.nio.file.Path name = path.getName(path.getNameCount()-1);
        return Response.ok(name.toString()).build();
    }

}
