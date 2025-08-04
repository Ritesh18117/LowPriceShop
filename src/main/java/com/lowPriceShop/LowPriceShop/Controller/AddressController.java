package com.lowPriceShop.LowPriceShop.Controller;

import com.lowPriceShop.LowPriceShop.DTO.AddressDTO;
import com.lowPriceShop.LowPriceShop.Entities.Address;
import com.lowPriceShop.LowPriceShop.ErrorHandling.ErrorResponse;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.AddressException.AddressNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.DuplicateEmailException;
import com.lowPriceShop.LowPriceShop.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress( @RequestBody  AddressDTO addressDTO){
        try{
            Address address = addressService.addAddress(addressDTO);
            return ResponseEntity.ok(address);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }  catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(
            @PathVariable Integer id,
            @RequestBody AddressDTO addressDTO
    ) {
        try{
            Address address = addressService.updateAddress(id,addressDTO);
            return ResponseEntity.ok(address);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (AddressNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage(),HttpStatus.NOT_FOUND.value()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAddress(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        try{
            List<Address> addresses = addressService.getAllAddress(page,size);
            return ResponseEntity.ok(addresses);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }  catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Integer id){
        try{
            Address address = addressService.getAddressById(id);
            return ResponseEntity.ok(address);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (AddressNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id){
        try{
            addressService.deleteAddress(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (AddressNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
