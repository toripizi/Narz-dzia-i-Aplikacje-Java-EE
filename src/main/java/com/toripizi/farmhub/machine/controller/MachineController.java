package com.toripizi.farmhub.machine.controller;

import com.toripizi.farmhub.machine.dto.CreateMachineRequest;
import com.toripizi.farmhub.machine.dto.GetMachineryResponse;
import com.toripizi.farmhub.machine.dto.GetMachineResponse;
import com.toripizi.farmhub.machine.dto.UpdateMachineRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("categories/{categoryId}/machinery")
public interface MachineController {
    @PathParam("categoryId")
    String categoryId = null;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    GetMachineryResponse getMachinery();

    @GET @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMachineResponse getMachine(@PathParam("id") UUID id);

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    void createMachine(CreateMachineRequest req);

    @PUT @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void updateMachine(@PathParam("id") UUID id, UpdateMachineRequest req);

    @DELETE @Path("{id}")
    void deleteMachine(@PathParam("id") UUID id);

}
