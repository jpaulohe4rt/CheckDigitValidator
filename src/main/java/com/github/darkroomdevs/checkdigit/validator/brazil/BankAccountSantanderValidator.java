package com.github.darkroomdevs.checkdigit.validator.brazil;

import com.github.darkroomdevs.checkdigit.util.ModuloUtil;
import com.github.darkroomdevs.checkdigit.validator.DigitValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * This class checks whether a specific string represents a valid bank account number of Banco Santander.
 * <p/>
 * Reference: <a href="http://hostmarcasite.com.br/fluir/vendor/eduardokum/laravel-boleto/manuais/Regras%20Validacao%20Conta%20Corrente%20VI_EPS.pdf">Banco Santander.</a>
 */
public final class BankAccountSantanderValidator implements DigitValidator {

    public static final BankAccountSantanderValidator INSTANCE = new BankAccountSantanderValidator();

    private BankAccountSantanderValidator() {
    }

    /**
     * Checks whether the specified string represents a valid agency and bank account number of Banco Santander
     *
     * @param bankAccount The sequence to verify.
     * @return {@code true} if the sequence is a valid bank account number; {@code false} otherwise.
     */
    public boolean valid(String bankAccount) {
        if (StringUtils.isBlank(bankAccount)
                || (StringUtils.length(bankAccount) > 16)
                || !bankAccount.matches("\\d{2,}")
                || bankAccount.matches("0+|1+|2+|3+|4+|5+|6+|7+|8+|9+")) {
            return false;
        }

        String pivot = "97310097131973";
        int bankAccountSize = bankAccount.length();
        char digit = bankAccount.charAt(bankAccountSize - 1);
        return ModuloUtil.computeModulo10WithPivot(bankAccount.substring(0, bankAccountSize - 1), pivot).orElse("")
                .equals(String.valueOf(digit));
    }
}
