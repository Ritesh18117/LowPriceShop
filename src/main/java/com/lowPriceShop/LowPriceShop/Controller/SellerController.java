package com.lowPriceShop.LowPriceShop.Controller;

import com.lowPriceShop.LowPriceShop.DTO.SellerDTO;
import com.lowPriceShop.LowPriceShop.Entities.Seller;
import com.lowPriceShop.LowPriceShop.ErrorHandling.ErrorResponse;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.SellerException.DuplicateGstNumberException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.SellerException.SellerDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.SellerException.SellerNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.DuplicateEmailException;
import com.lowPriceShop.LowPriceShop.Services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService){
        this.sellerService = sellerService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addSeller(@RequestBody SellerDTO sellerDTO){
        try{
            Seller seller = sellerService.addSeller(sellerDTO);
            return ResponseEntity.ok(seller);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (DuplicateEmailException | DuplicateGstNumberException e){
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getSellerById(@PathVariable Integer id){
        try{
            Seller seller = sellerService.getSellerById(id);
            return ResponseEntity.ok(seller);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (SellerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSeller(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try{
            List<Seller> sellers = sellerService.getAllSeller(page,size);
            return ResponseEntity.ok(sellers);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(
            @PathVariable Integer id,
            @RequestBody SellerDTO sellerDTO
    ) {
        try{
            Seller seller = sellerService.updateSeller(id,sellerDTO);
            return ResponseEntity.ok(seller);
        } catch (IllegalArgumentException | SellerDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (SellerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeller(@PathVariable Integer id){
        try{
            sellerService.deleteSeller(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | SellerDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (SellerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


}
