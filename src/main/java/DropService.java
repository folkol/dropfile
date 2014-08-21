import org.apache.tika.Tika;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Path("{filename}")
    public Response formPost(@PathParam("filename") String filename, InputStream data) throws Exception {
        if(new File("/tmp/dropservice/" + filename).exists()) {
            filename = UUID.randomUUID().toString() + "-" +  filename;
        }
        Files.copy(data, Paths.get("/tmp/dropservice/" + filename));
        return Response.ok(filename).build();
    }

}
