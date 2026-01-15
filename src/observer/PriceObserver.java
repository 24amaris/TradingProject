package observer;

import model.Asset;

/**
 * PATTERN: OBSERVER - Interfata Observer
 *
 * Observer =  pattern care permite obiectelor sa fie notificate
 * automat cand se intampla ceva (in cazul nostru, schimbarea pretului).
 *
 * Aceasta interfata defineste metoda pe care trebuie sa o implementeze
 * oricine vrea sa primeasca notificari.
 */
public interface PriceObserver {

    void onPriceChange(Asset asset, double oldPrice, double newPrice);
}