/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chuirer.utilitarios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;

/**
 *
 * @author fferegrino
 */
public class Validaciones {

     public Validaciones() {
    }

     
    public boolean validaEmail(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);

    }

    public boolean validaUrl(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }
}
