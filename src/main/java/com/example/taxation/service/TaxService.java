package com.example.taxation.service;

import com.example.taxation.model.Incoming;
import com.example.taxation.model.Outgoing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class TaxService {

    @Value("${taxation.general.taxType}")
    private String generalTaxType;
    @Value("${taxation.winnings.taxType}")
    private String winningsTaxType;

    public static final BigDecimal TAX_AMOUNT_GENERAL = BigDecimal.valueOf(2);
    public static final BigDecimal TAX_RATE_GENERAL = BigDecimal.valueOf(0.1);

    public static final BigDecimal TAX_AMOUNT_WINNINGS = BigDecimal.valueOf(1);
    public static final BigDecimal TAX_RATE_WINNINGS = BigDecimal.valueOf(0.1);

    public Outgoing generalTax(final Incoming incoming) throws Exception {
        final BigDecimal beforeTax = incoming.getPlayedAmount().multiply(incoming.getOdd());
        final BigDecimal afterTax = applyGeneralTax(beforeTax, generalTaxType);

        final BigDecimal taxAmount;
        final BigDecimal taxRate;

        if ("RATE".equals(generalTaxType)) {
            taxAmount = calculateTaxAmount(beforeTax, afterTax);
            taxRate = TAX_RATE_GENERAL;
        } else if ("AMOUNT".equals(generalTaxType)) {
            taxAmount = TAX_AMOUNT_GENERAL;
            taxRate = calculateTaxRate(beforeTax, afterTax);
        } else {
            throw new Exception("Unsupported tax type, should use RATE or AMOUNT.");
        }


        return Outgoing.builder()
                .possibleReturnAmount(beforeTax)
                .possibleReturnAmountBefTax(beforeTax)
                .possibleReturnAmountAfterTax(afterTax)
                .taxAmount(taxAmount)
                .taxRate(taxRate)
                .build();
    }

    public BigDecimal applyGeneralTax(final BigDecimal beforeTax, final String taxType) throws Exception {
        if ("RATE".equals(taxType)) {
            return beforeTax.subtract(beforeTax.multiply(TAX_RATE_GENERAL));
        }
        if ("AMOUNT".equals(taxType)) {
            return beforeTax.subtract(TAX_AMOUNT_GENERAL);
        }
        throw new Exception("Unsupported tax type, should use RATE or AMOUNT");
    }

    public BigDecimal applyWinningsTax(final BigDecimal beforeTax, final String taxType) throws Exception {
        if ("RATE".equals(taxType)) {
            return beforeTax.subtract(beforeTax.multiply(TAX_RATE_WINNINGS));
        }
        if ("AMOUNT".equals(taxType)) {
            return beforeTax.subtract(TAX_AMOUNT_WINNINGS);
        }
        throw new Exception("Unsupported tax type, should use RATE or AMOUNT");
    }

    public Outgoing winningsTax(Incoming incoming) throws Exception {
        final BigDecimal beforeTax = incoming.getPlayedAmount().multiply(incoming.getOdd());
        final BigDecimal afterTax = applyWinningsTax(beforeTax, winningsTaxType);

        final BigDecimal taxAmount;
        final BigDecimal taxRate;

        if ("RATE".equals(winningsTaxType)) {
            taxAmount = calculateTaxAmount(beforeTax, afterTax);
            taxRate = TAX_RATE_WINNINGS;
        } else if ("AMOUNT".equals(winningsTaxType)) {
            taxAmount = TAX_RATE_WINNINGS;
            taxRate = calculateTaxRate(beforeTax, afterTax);
        } else {
            throw new Exception("Unsupported tax type, should use RATE or AMOUNT.");
        }


        return Outgoing.builder()
                .possibleReturnAmount(beforeTax)
                .possibleReturnAmountBefTax(beforeTax)
                .possibleReturnAmountAfterTax(afterTax)
                .taxAmount(taxAmount)
                .taxRate(taxRate)
                .build();
    }

    private BigDecimal calculateTaxAmount(final BigDecimal beforeTax, final BigDecimal afterTax) {
        return beforeTax.subtract(afterTax);
    }

    private BigDecimal calculateTaxRate(final BigDecimal beforeTax, final BigDecimal afterTax) {
        final BigDecimal taxAmount = beforeTax.subtract(afterTax);
        return taxAmount.divide(beforeTax, 10, RoundingMode.HALF_EVEN);
    }
}