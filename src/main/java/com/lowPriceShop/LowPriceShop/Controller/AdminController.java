package com.lowPriceShop.LowPriceShop.Controller;

import com.lowPriceShop.LowPriceShop.DTO.AdminDTO;
import com.lowPriceShop.LowPriceShop.Entities.Admin;
import com.lowPriceShop.LowPriceShop.ErrorHandling.ErrorResponse;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.AdminException.AdminDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.AdminException.AdminNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.DuplicateEmailException;
import com.lowPriceShop.LowPriceShop.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody AdminDTO adminDTO){
        try {
            Admin admin = adminService.addAdmin(adminDTO);
            return ResponseEntity.ok(admin);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (DuplicateEmailException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("CONFLICT", e.getMessage(),HttpStatus.CONTINUE.value()));
        } catch (RoleNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage(), HttpStatus.NO_CONTENT.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmin(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try{
            List<Admin> adminList = adminService.getAllAdmin(page,size);
            return ResponseEntity.ok(adminList);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Integer id){
        try{
            Admin admin = adminService.getAdminById(id);
            return ResponseEntity.ok(admin);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (AdminNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        try{
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | AdminDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (AdminNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdmin(
            @PathVariable Integer id,
            @RequestBody AdminDTO adminDTO
    ) {
        try{
            Admin admin = adminService.updateAdmin(id,adminDTO);
            return ResponseEntity.ok(admin);
        } catch (IllegalArgumentException | AdminDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (AdminNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
