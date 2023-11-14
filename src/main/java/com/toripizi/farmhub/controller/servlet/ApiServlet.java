package com.toripizi.farmhub.controller.servlet;

import java.io.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.toripizi.farmhub.category.controller.CategoryController;
import com.toripizi.farmhub.category.dto.CreateCategoryRequest;
import com.toripizi.farmhub.category.dto.UpdateCategoryRequest;
import com.toripizi.farmhub.farmer.controller.FarmerController;
import com.toripizi.farmhub.farmer.dto.CreateFarmerRequest;
import com.toripizi.farmhub.farmer.dto.UpdateFarmerRequest;
import com.toripizi.farmhub.machine.controller.MachineController;
import com.toripizi.farmhub.machine.dto.CreateMachineRequest;
import com.toripizi.farmhub.machine.dto.UpdateMachineRequest;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@MultipartConfig(maxFileSize = 200 * 1024)
@WebServlet(name = "farmHubServlet", urlPatterns = ApiServlet.API_PATH + "/*")
public class ApiServlet extends HttpServlet {

    public static final String API_PATH = "/api";

    private final FarmerController farmerController;
    private final CategoryController categoryController;
    private final MachineController machineController;

    @Inject
    public ApiServlet(FarmerController farmerController, CategoryController categoryController, MachineController machineController) {
        this.farmerController = farmerController;
        this.categoryController = categoryController;
        this.machineController = machineController;
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern USERS = Pattern.compile("/farmers/?");
        public static final Pattern USER = Pattern.compile(String.format("/farmer/(%s)", UUID.pattern()));
        public static final Pattern USER_AVATAR = Pattern.compile(String.format("/farmer/(%s)/avatar", UUID.pattern()));

        public static final Pattern CATEGORIES = Pattern.compile("/categories/?");
        public static final Pattern CATEGORY = Pattern.compile(String.format("/category/(%s)", UUID.pattern()));

        public static final Pattern MACHINERY = Pattern.compile("/machinery/?");
        public static final Pattern MACHINE = Pattern.compile(String.format("/machine/(%s)", UUID.pattern()));

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo(); /* returns the path after '/api' */
        String servletPath = request.getServletPath(); /* returns whole path */
        if (API_PATH.equals(servletPath)) {
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(farmerController.getFarmers()));
                return;
            }
            else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(farmerController.getFarmer(uuid)));
                return;
            }
            else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                byte[] file = farmerController.getFarmerAvatar(uuid);
                response.setContentLength(file.length);
                response.getOutputStream().write(file);
                return;
            }
            else if (path.matches(Patterns.CATEGORY.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                response.getWriter().write(jsonb.toJson(categoryController.getCategory(uuid)));
                return;
            }
            else if (path.matches(Patterns.CATEGORIES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(categoryController.getCategories()));
                return;
            }
            else if (path.matches(Patterns.MACHINE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.MACHINE, path);
                response.getWriter().write(jsonb.toJson(machineController.getMachine(uuid)));
                return;
            }
            else if (path.matches(Patterns.MACHINERY.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(machineController.getMachinery()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String servletPath = request.getServletPath();
        if (API_PATH.equals(servletPath)) {
            if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                farmerController.putFarmerAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
            else if (path.matches(Patterns.USERS.pattern())) {
                farmerController.createFarmer(jsonb.fromJson(request.getReader(), CreateFarmerRequest.class));
                return;
            }
            else if (path.matches(Patterns.CATEGORIES.pattern())) {
                categoryController.createCategory(jsonb.fromJson(request.getReader(), CreateCategoryRequest.class));
                return;
            }
            else if (path.matches(Patterns.MACHINERY.pattern())) {
                machineController.createMachine(jsonb.fromJson(request.getReader(), CreateMachineRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String servletPath = request.getServletPath();
        if (API_PATH.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                farmerController.updateFarmer(uuid, jsonb.fromJson(request.getReader(), UpdateFarmerRequest.class));
                return;
            }
            else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                farmerController.putFarmerAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
            else if (path.matches(Patterns.CATEGORY.pattern())) {
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                categoryController.updateCategory(uuid, jsonb.fromJson(request.getReader(), UpdateCategoryRequest.class));
                return;
            }
            else if (path.matches(Patterns.MACHINE.pattern())) {
                UUID uuid = extractUuid(Patterns.MACHINE, path);
                machineController.updateMachine(uuid, jsonb.fromJson(request.getReader(), UpdateMachineRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String servletPath = request.getServletPath();
        if (API_PATH.equals(servletPath)) {
            if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                farmerController.deleteFarmerAvatar(uuid);
                return;
            }
            else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                farmerController.deleteFarmer(uuid);
                return;
            }
            else if (path.matches(Patterns.CATEGORY.pattern())) {
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                categoryController.deleteCategory(uuid);
                return;
            }
            else if (path.matches(Patterns.MACHINE.pattern())) {
                UUID uuid = extractUuid(Patterns.MACHINE, path);
                machineController.deleteMachine(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

}