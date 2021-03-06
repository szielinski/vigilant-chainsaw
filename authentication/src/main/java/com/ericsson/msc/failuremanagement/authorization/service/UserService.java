package com.ericsson.msc.failuremanagement.authorization.service;

import com.ericsson.msc.failuremanagement.authorization.business.UserDataBean;
import com.ericsson.msc.failuremanagement.user.data.UserEntity;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/")
public class UserService {

    @EJB
    private UserDataBean userServiceEJB;

    @POST
    @Path("/add/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserEntity user) {
        if (userServiceEJB.addUser(user.getUsername(), user.getPassword(), user.getRole())) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(user).build();
        }
    }

    @POST
    @Path("/add/defaultAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDefaultAdminAccount() {
        String username = "administrator";
        String password = "admin";
        String role = "administrator";
        userServiceEJB.addUser(username, password, role);
        return Response.ok().build();
    }

    @GET
    @Path("/get/username")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsername(@Context SecurityContext securityContext) {
        return Response.ok("{ \"username\" : \"" + securityContext.getUserPrincipal().getName() + "\" }").build();
    }

    @GET
    @Path("/get/userrole")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserrole(@Context SecurityContext securityContext) {
        String roles[] = {"administrator", "customer service rep", "network mgmt engineer", "support engineer"};
        for (String role : roles) {
            if (securityContext.isUserInRole(role)) {
                return Response.ok("{ \"role\" : \"" + role + "\" }").build();
            }
        }
        return Response.status(Response.Status.PRECONDITION_FAILED).build();
    }
}
