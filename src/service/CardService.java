package service;

import exception.CardNotInListException;
import exception.EmptyListException;
import model.Account;
import model.Card;

import java.util.List;

public interface CardService {

    public void addCard(Card card);
    public List<Card> getCards();
    public Card getCardById(String cardId) throws CardNotInListException;
    public List<Card> getAllCardsThatExpireBeforeSpecificDate(String date) throws EmptyListException;
    public void deleteCard(Card card) throws CardNotInListException;
    public List<Card>getAllCardsThatBelongToSpecificAccount(Account account) throws EmptyListException;
}