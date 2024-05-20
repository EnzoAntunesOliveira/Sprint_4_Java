package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Usuario;
import org.example.repositories.UsuarioRepository;

import java.util.List;

@Path("salesforcedirections")
public class UsuarioResource {

    private UsuarioRepository usuarioRepository;

    public UsuarioResource() {
        this.usuarioRepository = new UsuarioRepository();
    }


    @GET
    @Path("cliente")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getAllClientes() {
        return usuarioRepository.ReadAll();

    }

    @POST
    @Path("cliente")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCliente(Usuario usuario) {
        usuarioRepository.Create(usuario);
        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @PUT
    @Path("cliente/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCliente(@PathParam("id") int id, Usuario usuario) {
        if (usuarioRepository.ReadById(id) != null) {
            usuarioRepository.UpdateById(usuario, id);
            return Response.status(Response.Status.OK).entity(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("cliente/{id}")
    public Response deleteCliente(@PathParam("id") int id) {
        if (usuarioRepository.ReadById(id) != null) {
            usuarioRepository.DeleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("cliente/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("id") int id) {
        Usuario usuario = usuarioRepository.ReadById(id);
        if (usuario != null) {
            return Response.status(Response.Status.OK).entity(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
