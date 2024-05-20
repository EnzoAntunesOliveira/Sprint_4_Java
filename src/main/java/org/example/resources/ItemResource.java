package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Item;
import org.example.repositories.ItemRepository;

import java.util.List;

@Path("salesforcedirections")
public class ItemResource {
    private ItemRepository itemRepository;

    public ItemResource() {
        this.itemRepository = new ItemRepository();
    }
    @GET
    @Path("produto")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAllProdutos() {
        return itemRepository.ReadAll();
    }

    @POST
    @Path("produto/{clienteId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduto(@PathParam("clienteId") int clienteId, Item item) {
        itemRepository.Create(item, clienteId);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @PUT
    @Path("produto/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduto(@PathParam("id") int id, Item item) {
        if (itemRepository.ReadById(id) != null){

            itemRepository.UpdateById(item, id);
            return Response.status(Response.Status.OK).entity(item).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("produto/{id}")
    public Response deleteProduto(@PathParam("id") int id) {
        if (itemRepository.ReadById(id) != null){
            itemRepository.DeleteById(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("produto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduto(@PathParam("id") int id) {
        Item item = itemRepository.ReadById(id);
        if (item != null) {
            return Response.status(Response.Status.OK).entity(item).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
