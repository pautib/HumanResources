package org.pautib.services;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
public class PayrollService {

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public long fibonacci(long number) {

        if ( (number == 0) || (number == 1)) return number;
        else return fibonacci(number - 1) + fibonacci(number -2);

    }

    public void computePayroll() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
