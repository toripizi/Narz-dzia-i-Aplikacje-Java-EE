package com.toripizi.farmhub.farmer.controller;

import com.toripizi.farmhub.farmer.dto.CreateFarmerRequest;
import com.toripizi.farmhub.farmer.dto.GetFarmerResponse;
import com.toripizi.farmhub.farmer.dto.GetFarmersResponse;
import com.toripizi.farmhub.farmer.dto.UpdateFarmerRequest;
import com.toripizi.farmhub.machine.dto.GetMachineryResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.util.UUID;

@Path("farmers")
public interface FarmerController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    GetFarmersResponse getFarmers();

    @GET @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetFarmerResponse getFarmer(@PathParam("id") UUID id);

    @GET @Path("{id}/machinery")
    GetMachineryResponse getMachinery(@PathParam("id") UUID id);

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    void createFarmer(CreateFarmerRequest req);

    @PUT @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateFarmer(@PathParam("id")UUID id, UpdateFarmerRequest req);

    @DELETE  @Path("{id}")
    void deleteFarmer(@PathParam("id") UUID id);

    @GET @Path("{id}/avatar")
    @Produces("image/png")
    byte[] getFarmerAvatar(@PathParam("id") UUID id);

    @POST @Path("{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void putFarmerAvatar(@PathParam("id") UUID id, InputStream portrait);

    @DELETE @Path("{id}/avatar")
    void deleteFarmerAvatar(@PathParam("id") UUID id);
}
