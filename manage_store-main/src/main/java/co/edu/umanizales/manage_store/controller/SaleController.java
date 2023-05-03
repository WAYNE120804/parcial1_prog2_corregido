package co.edu.umanizales.manage_store.controller;

import co.edu.umanizales.manage_store.controller.dto.ResponseDTO;
import co.edu.umanizales.manage_store.controller.dto.SaleDTO;
import co.edu.umanizales.manage_store.model.Sale;
import co.edu.umanizales.manage_store.model.Seller;
import co.edu.umanizales.manage_store.model.Store;
import co.edu.umanizales.manage_store.service.SaleService;
import co.edu.umanizales.manage_store.service.SellerService;
import co.edu.umanizales.manage_store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "sale")
public class SaleController {
    @Autowired
    private SaleService saleService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getSales() {
        return new ResponseEntity<>(
                new ResponseDTO(200,
                        saleService.getSales(),
                        null),
                HttpStatus.OK);
    }

    //crear venta
    @PostMapping
    public ResponseEntity<ResponseDTO> createSale(@RequestBody SaleDTO saleDTO) {
        Seller findSeller = sellerService.getSellerById(saleDTO.getSellerId());
        if (findSeller == null) {
            return new ResponseEntity<>(new ResponseDTO(409,
                    "El vendedor ingresado no existe", null),
                    HttpStatus.BAD_REQUEST);
        }
        Store findStore = storeService.getStoreById(saleDTO.getStoreId());
        if (findStore == null) {
            return new ResponseEntity<>(new ResponseDTO(409,
                    "La tienda ingresada no existe", null),
                    HttpStatus.BAD_REQUEST);
        }
        saleService.addSale(new Sale(findStore, findSeller,
                saleDTO.getQuantity()));
        return new ResponseEntity<>(new ResponseDTO(200,
                "Venta adicionada", null),
                HttpStatus.OK);
    }

    //total ventas
    @GetMapping(path = "/total")
    public ResponseEntity<ResponseDTO> getTotalsales() {
        if (saleService.getTotalSales() != 0) {
            return new ResponseEntity<>(new ResponseDTO(200, saleService.getTotalSales(), null),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDTO(409, "No hay ventas", null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //total de ventas del vendedor
    @GetMapping(path = "/totalseller/{code}")
    public ResponseEntity<ResponseDTO> getTotalsalesBySeller(@PathVariable String code) {

            return new ResponseEntity<>(new ResponseDTO(200, saleService.getTotalBySeller(code), null),
                    HttpStatus.OK);

    }

    @GetMapping(path = "/totalstore/{code}")
    public ResponseEntity<ResponseDTO> getTotalsalesByStore(@PathVariable String code) {
        return new ResponseEntity<>(new ResponseDTO(200, saleService.getTotalByStore(code), null),
                HttpStatus.OK);
    }


    @GetMapping(path = "/bestseller")
    public ResponseEntity<ResponseDTO> getBestSeller() {
        if (saleService.getTotalSales() != 0) {
            return new ResponseEntity<>(new ResponseDTO(200,
                    saleService.getBestSeller(sellerService.getSellers()), null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(409, "No hay ventas", null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/beststore")
    public ResponseEntity<ResponseDTO> getBestStore() {
        if (saleService.getTotalSales() != 0) {
            return new ResponseEntity<>(new ResponseDTO(200,
                    saleService.getBestStore(storeService.getStores()), null), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ResponseDTO(409, "No hay ventas", null),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/promedioseller")
    public ResponseEntity<ResponseDTO> getAverageBySeller() {
        int findsale = saleService.getTotalSales();
        while (findsale != 0) {
            return new ResponseEntity<>(new ResponseDTO(200, saleService.getTotalSales() / (float) sellerService.getSellers().size(),
                    null), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseDTO(409, "no se puede calcular el promedio",
                null), HttpStatus.BAD_REQUEST);
    }


    @GetMapping(path = "promediostore")
    public ResponseEntity<ResponseDTO> getAverageByStore() {
        int findsale = saleService.getTotalSales();
        while (findsale != 0) {
            return new ResponseEntity<>(new ResponseDTO(200, saleService.getTotalSales() / (float) storeService.getStores().size(),
                    null), HttpStatus.OK);

        }
        return new ResponseEntity<>(new ResponseDTO(409, "no se puede calcular el promedio",
                null), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "cantidadmayorstore/{quant}")
    public ResponseEntity<ResponseDTO> getStoreByQuant(@PathVariable int quant){
        List<Store> beststores=new ArrayList<>();
        for(Sale i:saleService.getSales()){
            if(saleService.getTotalByStore(i.getStore().getCode())>quant){
                beststores.add(i.getStore());
            }
        }
        return  new ResponseEntity<>(new ResponseDTO(200,beststores,null),
        HttpStatus.OK);
    }
}
