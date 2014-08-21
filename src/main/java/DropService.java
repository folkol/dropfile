import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Path("")
public class DropService
{
    @GET
    public Response hello() {
        return Response.ok("hello").build();
    }

    @POST
    @Consumes("application/octet-stream")
    public Response formPost(InputStream data) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(new File("/tmp/attachment"))) {
            int c;
            while ((c = data.read()) != -1) {
                fos.write(c);
            }
            return Response.ok("tnx").build();
        }
    }

}
