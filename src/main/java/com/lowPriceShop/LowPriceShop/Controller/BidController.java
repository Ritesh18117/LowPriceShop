package com.lowPriceShop.LowPriceShop.Controller;

import com.lowPriceShop.LowPriceShop.DTO.BidDTO;
import com.lowPriceShop.LowPriceShop.Entities.Bid;
import com.lowPriceShop.LowPriceShop.Entities.BidSeller;
import com.lowPriceShop.LowPriceShop.ErrorHandling.ErrorResponse;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidException.BidDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidException.BidNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerInactiveException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.UnauthorizedCustomerException;
import com.lowPriceShop.LowPriceShop.Services.BidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bid")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService){
        this.bidService = bidService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBid(@RequestBody BidDTO bidDTO){
        try{
            Bid bid = bidService.addBid(bidDTO);
            return ResponseEntity.ok(bid);
        } catch (IllegalArgumentException | CustomerInactiveException | CustomerDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST",e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (CustomerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBid(
            @PathVariable Integer id, @RequestBody BidDTO bidDTO
    ) {
        try{
            Bid bid = bidService.updateBid(id,bidDTO);
            return ResponseEntity.ok(bid);
        } catch (IllegalArgumentException | CustomerInactiveException | CustomerDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST",e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (BidNotFoundException | CustomerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (UnauthorizedCustomerException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("UNAUTHORIZED ACCESS",e.getMessage(),HttpStatus.UNAUTHORIZED.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBid(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try{
            List<Bid> bids = bidService.getAllBid(page,size);
            return ResponseEntity.ok(bids);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST",e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable Integer id){
        try{
            Bid bid = bidService.getBidById(id);
            return ResponseEntity.ok(bid);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST",e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (BidNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                  .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBid(@PathVariable Integer id){
        try{
            bidService.deleteBid(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | BidDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST",e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (BidNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getBidByCustomerId(@PathVariable Integer customerId){
        try{
            List<Bid> bids = bidService.getBidByCustomerId(customerId);
            return ResponseEntity.ok(bids);
        } catch (IllegalArgumentException | CustomerDeletedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST",e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        } catch (CustomerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND",e.getMessage(),HttpStatus.NOT_FOUND.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Server Error", "An unexpected exception occured",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
