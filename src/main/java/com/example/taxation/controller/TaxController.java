package com.example.taxation.controller;

import com.example.taxation.model.Incoming;
import com.example.taxation.model.Outgoing;
import com.example.taxation.service.TaxService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
class TaxController {

    private TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @PostMapping("/taxes/general")
    public Outgoing taxGeneral(@RequestBody Incoming incoming) throws Exception {
        return taxService.generalTax(incoming);
    }

    @PostMapping("/taxes/winnings")
    public Outgoing taxWinnings(@RequestBody Incoming incoming) throws Exception {
        return taxService.winningsTax(incoming);
    }

}