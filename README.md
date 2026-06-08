# Gestion de Credits Bancaires

> **Rapport de projet**
> Le rapport technique complet (architecture, diagrammes, extraits de code, captures d'ecran) est disponible au format PDF :
> [Telecharger le rapport -- Gestion de Credits Bancaires (PDF)](./makhlouk_anass_exam_jee2.pdf)

Application Web full-stack de gestion de credits bancaires developpee dans le cadre du module Architecture Distribuee et Middleware (Master SDIA).

Le backend expose une API REST securisee par JWT avec Spring Boot 3. Le frontend est une Single Page Application Angular 17 avec Tailwind CSS.

---

## Table des matieres

- [Stack technique](#stack-technique)
- [Prerequis](#prerequis)
- [Demarrage rapide](#demarrage-rapide)
- [Architecture du projet](#architecture-du-projet)
- [Modele de donnees](#modele-de-donnees)
- [API REST](#api-rest)
- [Securite JWT](#securite-jwt)
- [Frontend Angular](#frontend-angular)
- [Comptes de test](#comptes-de-test)
- [Captures d'ecran](#captures-decran)
- [Structure des dossiers](#structure-des-dossiers)

---

## Stack technique

| Couche        | Technologie                                      |
|---------------|--------------------------------------------------|
| Backend       | Java 21, Spring Boot 3.3, Spring Web             |
| Persistance   | Spring Data JPA, Hibernate 6, H2 (in-memory)     |
| Securite      | Spring Security 6, JWT (jjwt 0.12.6), BCrypt     |
| Documentation | SpringDoc OpenAPI 2.5, Swagger UI                |
| Frontend      | Angular 17, TypeScript, Tailwind CSS 3           |
| Build         | Maven 3, npm, Angular CLI 17                     |

---

## Prerequis

- Java 21 ou superieur
- Maven 3.8 ou superieur
- Node.js 20.x
- npm 10.x

---

## Demarrage rapide

### 1. Cloner le depot

```bash
git clone https://github.com/makhlouk-anass/credit-bancaire.git
cd credit-bancaire
```

### 2. Lancer le backend

```bash
./mvnw spring-boot:run
```

Le serveur demarre sur `http://localhost:8080`.

La base de donnees H2 est initialisee automatiquement avec :
- 5 clients
- 6 credits (Personnel, Immobilier, Professionnel)
- 7 remboursements

### 3. Lancer le frontend

```bash
cd frontend
npm install --legacy-peer-deps
npx ng serve --port 4200
```

L'application est accessible sur `http://localhost:4200`.

### 4. Acceder a la documentation API

```
http://localhost:8080/swagger-ui.html
```

### 5. Acceder a la console H2

```
http://localhost:8080/h2-console
JDBC URL : jdbc:h2:mem:creditdb
User     : sa
Password : (vide)
```

---

## Architecture du projet

```
credit-bancaire/
├── src/main/java/com/makhlouk/anass/
│   ├── entities/                  Entites JPA (Credit, Client, Remboursement)
│   ├── repositories/              Interfaces Spring Data JPA
│   ├── dtos/                      Data Transfer Objects
│   ├── services/                  Logique metier (interfaces + implementations)
│   ├── web/                       REST Controllers
│   ├── security/                  JwtUtil, JwtAuthorizationFilter, UserDetailsServiceImpl
│   ├── config/                    SecurityConfig, OpenApiConfig
│   └── DataInitializer.java       Initialisation de la base au demarrage
├── src/main/resources/
│   └── application.properties
├── frontend/
│   └── src/app/
│       ├── models/                Interfaces TypeScript
│       ├── services/              AuthService, ClientService, CreditService
│       ├── interceptors/          JwtInterceptor
│       ├── guards/                AuthGuard, EmployeGuard
│       ├── shared/navbar/         Composant Navbar
│       └── pages/
│           ├── login/             Page de connexion
│           ├── credits/           Dashboard employe
│           ├── clients/           Gestion des clients
│           └── mes-credits/       Espace client
└── pom.xml
```

---

## Modele de donnees

### Heritage JPA : SINGLE_TABLE

Les trois types de credits heritent d'une classe abstraite `Credit`. La strategie d'heritage choisie est `SINGLE_TABLE` : une seule table `credits` en base de donnees contient toutes les colonnes, avec une colonne discriminante `TYPE_CREDIT`.

```
Credit (abstract)
├── CreditPersonnel      TYPE_CREDIT = 'PERSONNEL'
│     motif
├── CreditImmobilier     TYPE_CREDIT = 'IMMOBILIER'
│     typeBien (APPARTEMENT | MAISON | LOCAL_COMMERCIAL)
└── CreditProfessionnel  TYPE_CREDIT = 'PROFESSIONNEL'
      motif, raisonSociale
```

### Enumerations

| Enum               | Valeurs                                    |
|--------------------|--------------------------------------------|
| StatutCredit       | EN_COURS, ACCEPTE, REJETE                  |
| TypeBien           | APPARTEMENT, MAISON, LOCAL_COMMERCIAL      |
| TypeRemboursement  | MENSUALITE, REMBOURSEMENT_ANTICIPE         |

### Relations

- `Client` (1) ---- (0..*) `Credit`
- `Credit` (1) ---- (0..*) `Remboursement`

---

## API REST

### Authentification

| Methode | URL               | Acces  | Description                  |
|---------|-------------------|--------|------------------------------|
| POST    | /api/auth/login   | Public | Connexion et obtention du JWT|

### Clients

| Methode | URL                  | Role requis      | Description              |
|---------|----------------------|------------------|--------------------------|
| GET     | /api/clients         | EMPLOYE, ADMIN   | Lister tous les clients  |
| GET     | /api/clients/{id}    | EMPLOYE, ADMIN   | Obtenir un client par ID |
| POST    | /api/clients         | ADMIN            | Creer un client          |
| DELETE  | /api/clients/{id}    | ADMIN            | Supprimer un client      |

### Credits

| Methode | URL                           | Role requis      | Description                    |
|---------|-------------------------------|------------------|--------------------------------|
| GET     | /api/credits                  | EMPLOYE, ADMIN   | Lister tous les credits        |
| GET     | /api/credits/{id}             | Tous             | Obtenir un credit par ID       |
| GET     | /api/clients/{id}/credits     | Tous             | Credits d'un client            |
| GET     | /api/credits/statut/{statut}  | EMPLOYE, ADMIN   | Filtrer par statut             |
| POST    | /api/credits                  | Tous             | Creer une demande de credit    |
| PUT     | /api/credits/{id}/statut      | EMPLOYE, ADMIN   | Mettre a jour le statut        |

### Exemple de requete de connexion

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "employe1", "password": "secret1234"}'
```

Reponse :

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "employe1",
  "roles": ["ROLE_EMPLOYE"]
}
```

### Exemple de requete avec token

```bash
curl -X GET http://localhost:8080/api/credits \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## Securite JWT

L'authentification est entierement stateless. Aucune session n'est maintenue cote serveur.

### Flux d'authentification

```
Client                     Backend
  |                           |
  |-- POST /api/auth/login --> |
  |                           | Verification BCrypt
  |                           | Generation token JWT signe HMAC-SHA
  |<-- { token, roles } ----- |
  |                           |
  |-- GET /api/credits ------> |
  |   Authorization: Bearer   | JwtAuthorizationFilter
  |                           | Validation token
  |                           | Chargement SecurityContext
  |<-- 200 OK + donnees ------ |
```

### Configuration CORS

Le backend autorise les requetes depuis `http://localhost:4200` avec les methodes GET, POST, PUT, DELETE, OPTIONS.

---

## Frontend Angular

### Routage et protection

| Route          | Guard         | Role requis      |
|----------------|---------------|------------------|
| /login         | Aucun         | Public           |
| /credits       | employeGuard  | EMPLOYE, ADMIN   |
| /clients       | employeGuard  | EMPLOYE, ADMIN   |
| /mes-credits   | authGuard     | Tout utilisateur |

### Intercepteur JWT

Chaque requete HTTP sortante est automatiquement enrichie du header `Authorization: Bearer <token>` via le `jwtInterceptor` enregistre dans `app.config.ts`.

### Redirection apres connexion

- `ROLE_CLIENT` est redirige vers `/mes-credits`
- `ROLE_EMPLOYE` et `ROLE_ADMIN` sont rediriges vers `/credits`

---

## Comptes de test

| Utilisateur | Mot de passe | Role        | Acces                              |
|-------------|--------------|-------------|-------------------------------------|
| client1     | secret1234   | ROLE_CLIENT | Consultation de ses propres credits |
| employe1    | secret1234   | ROLE_EMPLOYE| Gestion des credits, validation     |
| admin       | secret1234   | ROLE_ADMIN  | Acces complet + suppression clients |

---

## Structure des dossiers detaillee

```
src/main/java/com/makhlouk/anass/
├── CreditBancaireApplication.java
├── DataInitializer.java
├── config/
│   ├── OpenApiConfig.java
│   └── SecurityConfig.java
├── dtos/
│   ├── ClientDTO.java
│   ├── CreditDTO.java
│   ├── CreditImmobilierDTO.java
│   ├── CreditPersonnelDTO.java
│   ├── CreditProfessionnelDTO.java
│   ├── LoginRequestDTO.java
│   ├── LoginResponseDTO.java
│   └── RemboursementDTO.java
├── entities/
│   ├── Client.java
│   ├── Credit.java
│   ├── CreditImmobilier.java
│   ├── CreditPersonnel.java
│   ├── CreditProfessionnel.java
│   ├── Remboursement.java
│   └── enums/
│       ├── StatutCredit.java
│       ├── TypeBien.java
│       └── TypeRemboursement.java
├── repositories/
│   ├── ClientRepository.java
│   ├── CreditRepository.java
│   └── RemboursementRepository.java
├── security/
│   ├── JwtAuthorizationFilter.java
│   ├── JwtUtil.java
│   └── UserDetailsServiceImpl.java
├── services/
│   ├── ClientService.java
│   ├── ClientServiceImpl.java
│   ├── CreditService.java
│   ├── CreditServiceImpl.java
│   └── mappers/
│       ├── ClientMapper.java
│       └── CreditMapper.java
└── web/
    ├── AuthController.java
    ├── ClientController.java
    └── CreditController.java
```

---

## Captures d'ecran

### Diagramme de classes

![Diagramme de classes UML](./screenshots/diagramme_classes_placeholder.png)

---

### Base de donnees H2

**Table clients**

![Table clients H2](./screenshots/h2consoleclient.png)

**Table credits -- Heritage SINGLE_TABLE avec colonne discriminante TYPE_CREDIT**

![Table credits H2](./screenshots/h2console%20credit.png)

**Table remboursements**

![Table remboursements H2](./screenshots/h2consoleremboursements.png)

---

### API REST -- Swagger UI

**Vue globale de tous les endpoints**

![Swagger endpoints](./screenshots/swagger%20list%20endpoints.png)

**Authentification -- Generation du token JWT**

![Swagger authentification JWT](./screenshots/swagger%20authentification%20jwt.png)

**Lister tous les clients**

![Swagger GET clients](./screenshots/swagger%20get%20client.png)

**Obtenir un client par ID**

![Swagger GET client by ID](./screenshots/swagger%20get%20client%20by%20id.png)

**Liste de tous les clients (reponse)**

![Swagger liste all clients](./screenshots/swagger-liste-all-clients.png)

**Creer un client**

![Swagger POST client](./screenshots/swagger%20create%20client.png)

**Supprimer un client**

![Swagger DELETE client](./screenshots/swagger%20delete%20client.png)

**Lister les credits d'un client**

![Swagger credits d'un client](./screenshots/swagger%20lister%20les%20credits%20dun%20client.png)

**Filtrer les credits par statut EN_COURS**

![Swagger credits EN_COURS](./screenshots/swagger%20get%20credits%20encours.png)

**Obtenir un credit par ID**

![Swagger GET credit by ID](./screenshots/swagger%20get%20credit%20by%20id.png)

**Mettre a jour le statut d'un credit**

![Mettre a jour statut credit](./screenshots/Mettre%20a%20jour%20le%20statut%20credit%20.png)

---

### Frontend Angular

**Page de connexion**

![Angular Login](./screenshots/angular_login_placeholder.png)

**Dashboard Employe -- Liste des credits avec filtres**

![Dashboard Employe](./screenshots/angular_dashboard_employe_placeholder.png)

**Dashboard Employe -- Actions Accepter / Rejeter**

![Dashboard Employe actions](./screenshots/angular_dashboard_employe_placeholder2.png)

**Dashboard Admin -- Vue globale**

![Dashboard Admin](./screenshots/angular_dashboard_admin_placeholder.png)

**Dashboard Admin -- Gestion des clients**

![Dashboard Admin clients](./screenshots/angular_dashboard_admin_placeholder2.png)

---

## Auteur

Anass MAKHLOUK  
Master SDIA -- Architecture Distribuee et Middleware  
Encadre par Prof. YOUSSFI  
Annee universitaire : 2025 -- 2026
