import org.apache.tika.Tika;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@javax.ws.rs.Path("")
public class DropService
{
    private static final String STORAGE_DIR = "/tmp/dropservice/";
    private static final Tika TIKA = new Tika();

    @GET
    @javax.ws.rs.Path("{filename}")
    public Response download(@PathParam("filename") String filename) throws Exception {
        Path path = Paths.get(STORAGE_DIR + filename);
        return Response.ok(Files.newInputStream(path), TIKA.detect(path.toFile())).build();
    }

    @POST
    public Response upload(InputStream data) throws Exception {
        return upload(UUID.randomUUID().toString(), data);
    }

    @PUT
    @javax.ws.rs.Path("{filename}")
    public Response upload(@PathParam("filename") String filename, InputStream data) throws Exception {
        Files.copy(data, Paths.get(STORAGE_DIR + filename), REPLACE_EXISTING);
        return Response.ok(filename).build();
    }
}
