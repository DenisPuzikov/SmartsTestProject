package ru.puzikov.SmartsTestProject.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.puzikov.SmartsTestProject.backend.entity.CreditOffer;
import ru.puzikov.SmartsTestProject.backend.entity.Payment;
import ru.puzikov.SmartsTestProject.backend.repository.PaymentRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> findAll(Long creditOfferId) {
        return paymentRepository.findAllByOffer_Id(creditOfferId);
    }

    public Double getCurrentRepaymentSum(Long creditOfferId){
        List<Payment> payments = paymentRepository.findAllByOffer_Id(creditOfferId);
        List<Double> repayments = new ArrayList<>();
        for (Payment payment : payments){
            repayments.add(payment.getInterestRepayment());
        }
        return repayments.stream().mapToDouble(i -> i).sum();
    }

    public void createPaymentList(CreditOffer creditOffer) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.valueOf(localDateTime.toLocalDate());
        double scale = Math.pow(10, 2);

        double remainder = creditOffer.getCreditSum() * 0.8;
        double interest = creditOffer.getCredit().getInterestRate();
        Long period = creditOffer.getCreditPeriod() * 12;
        double paymentBody = Math.ceil(remainder / period * scale) / scale;

        for (int i = 0; i < period; i++) {
            double interestRepayment = Math.ceil(((remainder * (interest / 100)) / period) * scale) / scale;
            double paymentAmountPerMonth = Math.ceil((paymentBody + interestRepayment) * scale) / scale;

            if (interestRepayment < 0) {
                interestRepayment = 0;
            }

            Payment newPayment = new Payment(date, paymentAmountPerMonth, paymentBody, interestRepayment, creditOffer);
            remainder -= paymentAmountPerMonth;
            localDateTime = localDateTime.plusMonths(1);
            date = Date.valueOf(localDateTime.toLocalDate());

            paymentRepository.save(newPayment);
        }
    }
}
