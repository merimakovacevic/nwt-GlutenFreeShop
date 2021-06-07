# Gluten Free Shop

Gluten Free Shop je aplikacija koja omogućava korisnicima pregled kao i narudžbu proizvoda bez glutena.

## Branch

Branch na koji se treba checkoutati za pokretanje aplikacije i koji sadrži sve posljednje izmjene na svim servisima jeste initial-branch.

## Instalacija i pokretanje

Prije samog pokretanja servisa za sljedeće servise
*  configuration-service
*  eureka-service
*  user-microservice
*  product-microservice
*  rating-microservice
*  order-microservice
*  payment-microservice

potrebno je odraditi
```bash
mvn clean install
```
Nakon toga servisi bi trebalo da se uspješno pokrenu na odgovarajućim portovima.

Za pokretanje: 
*  zuul-proxy
*  system-events

potrebno je prethodno izgenerisati klase iz proto fajla budući da smo gRPC implementirale upravo na zuul-proxy servisu. 
Moguće je da će biti potrebno zakomentarisati neke klase u kojima se nalazi kod za gRPC kako bi se uspješno odradio build, te nakon builda potrebno ih je ponovo odkomentarisati.
Obzirom da docker nije odrađen na projektu, servisi se moraju pokrenuti pojedinačno. Pored toga, treba voditi računa da password za bazu podataka u application.yml fajlovima servisa bude ispravan. 

Pokretanje je potrebno vršiti po sljedećem redoslijedu:
1. eureka-service
2. configuration-service
3. system-events
4. ostali servisi

Za pokretanje frontend aplikacije potrebno je odraditi:
```bash
npm install
```
```bash
npm start
```
Aplikacija se pokreće na portu 3000. 

## Centralna konfiguracija

Centralna konfiguracija nalazi se na branchu central-config. Za svaki mikroservis koji je povezan sa centralnom konfiguracijom se nalazi .yml fajl sa nazivom mikroservisa. Tu su smještene glavne konfiguracije kao što je port na kojem se servis vrti prilikom lokalnog pokretanja, port za eureka server, itd.



