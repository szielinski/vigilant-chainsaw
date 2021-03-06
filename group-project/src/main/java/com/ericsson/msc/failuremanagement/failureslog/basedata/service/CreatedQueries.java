package com.ericsson.msc.failuremanagement.failureslog.basedata.service;

import com.ericsson.msc.failuremanagement.network.data.business.FailureTraceDataBean;
import com.ericsson.msc.failuremanagement.network.data.FailureClassEntity;
import com.ericsson.msc.failuremanagement.network.data.FailureTraceEntity;
import com.ericsson.msc.failuremanagement.network.error.ErrorLog;
import org.json.simple.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Path("/query")
public class CreatedQueries {

    @EJB
    private FailureTraceDataBean failureTraceEJB;

    @POST
    @Path("/eventCausePerImsi")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventCauseCombinations(String imsi) {
        return Response.ok().status(200).entity(failureTraceEJB.getEventCauseCombinations(imsi)).build();
    }

    @POST
    @Path("/causeCodesPerImsi")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCauseCodeByImsi(String imsi) {
        return Response.ok().status(200).entity(failureTraceEJB.getCauseCodesForImsi(imsi)).build();
    }

    @GET
    @Path("/getErrorLogByImportDate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ErrorLog> getErrorLogByImportDate(@QueryParam("importDate") String importDate) {
        System.out.println("here");
        String[] splitDate = importDate.split("_");
        return failureTraceEJB.getErrorLogByImportDate(splitDate[0] + " " + splitDate[1]);
    }

    @POST
    @Path("/imsiByTimePeriod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImsiByTimePeriod(JSONObject JSONDateObject) {

        String startDate = JSONDateObject.get("Date1").toString();
        String endDate = JSONDateObject.get("Date2").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = sdf.parse(startDate);
            dateTwo = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Response.ok().status(200).entity(failureTraceEJB.getImsiOfFailureByTimePeriod(dateOne, dateTwo)).build();
    }

    @POST
    @Path("/givenImsiByTimePeriod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGivenImsiByTimePeriod(JSONObject JSONImsiDateObject) {
        String startDate = JSONImsiDateObject.get("Date1").toString();
        System.out.println(startDate);
        String endDate = JSONImsiDateObject.get("Date2").toString();
        System.out.println(endDate);
        String Imsi = JSONImsiDateObject.get("Imsi").toString();
        System.out.println(Imsi);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = sdf.parse(startDate);
            dateTwo = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Response.ok().status(200).entity(failureTraceEJB.getGivenImsiOfFailureWithinTimePeriod(dateOne, dateTwo, Imsi)).build();
    }

    @POST
    @Path("/imsiAffectedByFailureClass")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImsiOfFailureTraceByFailureClass(Integer failureClass) {
        return Response.ok().status(200).entity(failureTraceEJB.getImsiOfFailureTraceByFailureClass(failureClass)).build();

    }

    @POST
    @Path("/eventCauseAndCountOfOccurencesForModel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventCauseCombinationsForModel(String model) {
        return Response.ok().status(200).entity(failureTraceEJB.getEventCauseCombinationsForModel(model)).build();
    }

    @POST
    @Path("/givenModelByTimePeriod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response givenModelByTimePeriod(JSONObject JSONModelDateObject) {
        String startDate = JSONModelDateObject.get("Date1").toString();
        String endDate = JSONModelDateObject.get("Date2").toString();
        String model = JSONModelDateObject.get("Model").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = sdf.parse(startDate);
            dateTwo = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(model);
        return Response.ok().status(200).entity(failureTraceEJB.getCountFailsForModelWithinTimePeriod(model, dateOne, dateTwo)).build();
    }

    @POST
    @Path("/top10MarketOperatorCellIdCombinations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response top10MarketOperatorCellIdCombinations(JSONObject JSONDateObject) {
        String startDate = JSONDateObject.get("Date1").toString();
        String endDate = JSONDateObject.get("Date2").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = sdf.parse(startDate);
            dateTwo = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Response.ok().status(200).entity(failureTraceEJB.getTop10MarketOperatorCellIdCombinations(dateOne, dateTwo)).build();
    }

    @POST
    @Path("/givenImsiAndTimePeriodReturnNumberOfFailures")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response givenImsiAndTimePeriodReturnNumberOfFailures(JSONObject JSONImsiDateObject) {
        String Imsi = JSONImsiDateObject.get("Imsi").toString();
        String startDate = JSONImsiDateObject.get("DateOne").toString();
        String endDate = JSONImsiDateObject.get("DateTwo").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = sdf.parse(startDate);
            dateTwo = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(Imsi);
        return Response.ok().status(200).entity(failureTraceEJB.givenImsiAndTimePeriodReturnNumberOfFailures(Imsi, dateOne, dateTwo)).build();
    }

    @POST
    @Path("/topTenIMSIsWithFailures")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response topTenIMSIsWithFailures(JSONObject JSONDateObject) {

        String startDate = JSONDateObject.get("DateOne").toString();
        String endDate = JSONDateObject.get("DateTwo").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateOne = null;
        Date dateTwo = null;
        try {
            dateOne = sdf.parse(startDate);
            dateTwo = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Response.ok().status(200).entity(failureTraceEJB.topTenIMSIsWithFailures(dateOne, dateTwo)).build();
    }

    @GET
    @Path("/getAllFailureTraces")
    public Collection<FailureTraceEntity> getAllFailureTraces() {
        return failureTraceEJB.getAllFailureTraces();
    }

    @GET
    @Path("/getAllIMSIs")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<String> getAllIMSIs() {
        return failureTraceEJB.getAllIMSIs();
    }

    @GET
    @Path("/getAllModels")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<String> getAllModels() {
        return failureTraceEJB.getAllModels();
    }

    @GET
    @Path("/getAllFailureClasses")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<FailureClassEntity> getAllFailureClasses() {
        return failureTraceEJB.getAllFailureClasses();
    }
}
