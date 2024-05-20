package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Endereco;
import org.example.repositories.EnderecoRepository;


import java.util.List;


@Path("salesforcedirections")
public class EnderecoResource {

    private EnderecoRepository enderecoRepository;

    public EnderecoResource() {
        this.enderecoRepository = new EnderecoRepository();
    }

    @GET
    @Path("endereco")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Endereco> getAllClientes() {

        return enderecoRepository.ReadAll();

    }

    @POST
    @Path("endereco")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCliente(Endereco endereco) {
        enderecoRepository.Create(endereco);
        return Response.status(Response.Status.CREATED).entity(endereco).build();
    }

    @PUT
    @Path("endereco/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCliente(@PathParam("id") int id, Endereco endereco) {
        if (enderecoRepository.ReadById(id) != null) {
            enderecoRepository.UpdateById(endereco, id);
            return Response.status(Response.Status.OK).entity(endereco).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("endereco/{id}")
    public Response deleteCliente(@PathParam("id") int id) {
        if (enderecoRepository.ReadById(id) != null) {
            enderecoRepository.DeleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("endereco/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("id") int id) {
        Endereco endereco = enderecoRepository.ReadById(id);
        if (endereco != null) {
            return Response.status(Response.Status.OK).entity(endereco).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
