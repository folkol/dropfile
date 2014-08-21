import org.apache.tika.Tika;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.lang.String.format;

@javax.ws.rs.Path("{filename}")
public class DropService
{

    private static final String STORAGE_DIR = "/tmp/dropservice/";

    @GET
    public Response download(@PathParam("filename") String filename) throws Exception {
        Path path = Paths.get(STORAGE_DIR + filename);
        String mimetype = new Tika().detect(path.toFile());
        return Response.ok(Files.newInputStream(path), mimetype).build();
    }

    @POST
    public Response upload(@PathParam("filename") String filename, InputStream data) throws Exception {
        if (Files.exists(Paths.get(STORAGE_DIR + filename))) {
            filename = format("%s-%s", UUID.randomUUID().toString(), filename);
        }
        Files.copy(data, Paths.get(STORAGE_DIR + filename));
        return Response.ok(filename).build();
    }

}
