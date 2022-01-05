package lv.bank.cards.soap;

public interface ErrorConstants {

    String invalidCardNumber = "Please provide valid card number";
    String cardIsHardBlocked = "Card is hard-blocked";
    String cantFindCard = "Card with given number couldn't be found";
    String cantFindCauseForActionCode = "Cause for given Action code couldn't be found";
    String manyCausesForActionCode = "There are too many Causes for this Action Code";
    String cantFindActionCodeForCause = "Action code for given cause couldn't be found";
    String manyActionCodesForCause = "There are too many Action Codes for this Cause";
    String cantFindCardAccount = "No cards found for specified account";
    String dbException = "Error working with database";
    String invalidBalance = "Balance provided with the query doesn't match the balance in account";
    String invalidCCY = "Currency provided with the query doesn't match the currency of account";
    String invalidAccNr = "Account number provided with the query doesn't exist";
    String noTransactonType = "Transaction type is not provided with the query";
    String invalidComp_Id = "Condition account for given comp_Id was not found";
    String inactiveAccount = "Account is not active";
}
