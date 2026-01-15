                      Design Patterns Folosite:
1. SINGLETON
Fișiere: MarketManager.java, UserManager.java, NotificationManager.java
Ce este: Un pattern care asigură că o clasă are o singură instanță în toată aplicația.
De ce l-am folosit:
MarketManager - trebuie să existe o singură piață cu aceleași active pentru toți
UserManager - toți utilizatorii sunt gestionați într-un singur loc
NotificationManager - un singur sistem de notificări

2. STRATEGY
Fișiere: TradingStrategy.java, DayTradingStrategy.java, LongTermStrategy.java, CryptoTradingStrategy.java, AutoTradeStrategy.java
Ce este: Un pattern care permite schimbarea algoritmului/comportamentului la runtime.
De ce l-am folosit:
Avem mai multe moduri de a tranzacționa
Fiecare strategie are reguli diferite (taxe, restricții de timp)
Utilizatorul poate schimba strategia oricând


3. TEMPLATE METHOD
Fișier: BaseTradingStrategy.java
Ce este: Un pattern care definește scheletul unui algoritm în clasa de bază, dar lasă anumiți pași să fie implementați de subclase.
De ce l-am folosit:
Toate strategiile au pași comuni: validare → verificare restricții → execuție
Doar regulile specifice diferă între strategii


4. OBSERVER
Fișiere: PriceObserver.java, WatchListObserver.java, NotificationManager.java
Ce este: Un pattern care permite obiectelor să fie notificate automat când se întâmplă ceva.
De ce l-am folosit:
Utilizatorii pot urmări active (watch list)
Când prețul se schimbă, primesc notificări automat
Nu trebuie să verifice manual prețul


5. SIMPLE FACTORY
Fișier: StrategyFactory.java
Ce este: Un pattern care creează obiecte fără a expune logica de creare.
De ce l-am folosit:
Centralizăm crearea strategiilor
Clientul cere doar tipul (ex: "day") și primește obiectul
Ușor de adăugat strategii noi


6. FACADE
Fișier: TradingFacade.java
Ce este: Un pattern care oferă o interfață simplificată către un subsistem complex.
De ce l-am folosit:
Sistemul are multe componente: manageri, strategii, observeri
Facade-ul oferă metode simple: buy(), sell(), login()
Main-ul interacționează doar cu Facade-ul
