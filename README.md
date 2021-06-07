# Gluten Free Shop

Gluten Free Shop je aplikacija koja omogućava korisnicima pregled kao i narudžbu proizvoda bez glutena.

## Branch

Branch na koji se treba checkoutati za pokretanje aplikacije i koji sadrži sve posljednje izmjene na svim servisima jeste initial-branch.

## Instalacija i pokretanje

Za pokretanje sljedećih backend servisa:
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


Za pokretanje frontend aplikacije potrebno je odraditi:
```bash
npm install
```
```bash
npm start
```
Aplikacija se pokreće na portu 3000. 

