import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("")
public class DropService
{

    @GET
    @Path("{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response hello(@PathParam("filename") String filename) throws Exception {
        InputStream file = Files.newInputStream(Paths.get("/tmp/dropservice/" + filename));
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"") //optional
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response formPost(InputStream data) throws Exception {
        File file = File.createTempFile("dropped", "", new File("/tmp/dropservice/"));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            int c;
            while ((c = data.read()) != -1) {
                fos.write(c);
            }
            return Response.ok(file.getName()).build();
        }
    }

}
